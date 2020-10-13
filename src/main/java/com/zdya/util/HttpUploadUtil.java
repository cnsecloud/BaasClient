package com.zdya.util;

import com.zdya.util.em.http.HttpClient;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpUploadUtil {
    private String filePath;
    private final static String errorcode = "1";

    /**
     * 上传文件的方法
     * @param filePath 文件路径
     * @return 上传返回的结果json字符串
     */
    public static Map uploadFile(String filePath, FaceDigest faceDigest) throws Exception{

        File file = new File(filePath);
        if (!file.exists()){
            return Resp.resp(errorcode, "无此文件,请检查文件路径");
        }

        String fileType = "";
        if (filePath.contains(".")&& filePath.lastIndexOf(".")!=(filePath.length()-1)){
            fileType = filePath.substring(filePath.lastIndexOf(".")+1,filePath.length());
        }

        String reqHash = faceDigest.digestFileToBase64(filePath);

        Map<String, String> textMap = new HashMap<String, String>();
        textMap.put("extName", fileType);
        textMap.put("hash", reqHash);
        Map<String, String> fileMap = new HashMap<String, String>();
        fileMap.put("file", filePath);
        String ret = HttpClient.multipartFormDataUpload(UrlUtil.api_uploadFile, textMap, fileMap);
        // {"path":"group1/M00/00/00/wKgDfFyTN6uAONzaAAC2qbTagGY55.docx","code":"0","hash":"qoaK3yL23CTj/x0Xhfx24ANrj+w="}
        System.out.println("上传文件返回结果:"+ret);

        Map ret_map = JsonUtil.parseToMap(ret);
        String code = (String) ret_map.get("code");
        if (code==null)
            return Resp.resp(errorcode, "返回code为空");
        if (!code.equals("0"))
            return ret_map;

        String retHash = (String) ret_map.get("hash");
        if (!reqHash.equals(retHash)){
            return Resp.resp(errorcode,"baas返回hash不匹配");
        }

        return ret_map;
    }


    public static void main(String[] args) throws IOException {
         // HttpUploadUtil.uploadFile("/Users/apple/Desktop/国家知识产权局软科学研究项目.docx");
    }


    public static String multipartFormDataUpload(String url, Map<String, String> formDataMap,
                                                 Map<String, String> filePathMap) throws IOException {
        String res = "";
        HttpURLConnection conn = null;
        String BOUNDARY = "-----------HoronBaaS" + System.currentTimeMillis();
        String charset = "UTF-8";
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text
            if (formDataMap != null) {
                StringBuffer strBuf = new StringBuffer();
                formDataMap.forEach((k, v) -> {
                    if (v != null) {
                        strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\"" + k + "\"\r\n\r\n");
                        strBuf.append(v);
                    }
                });
                out.write(strBuf.toString().getBytes(charset));
            }
            // file
            if (filePathMap != null) {

                for (String k : filePathMap.keySet()) {
                    String v = filePathMap.get(k);
                    File file = new File(v);
                    String filename = file.getName();
                    String contentType = new MimetypesFileTypeMap().getContentType(file);
                    if (contentType == null || "".equals(contentType)) {
                        contentType = "application/octet-stream";
                    }
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + k + "\"; filename=\"" + filename + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes(charset));
                    try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
                        int bytes = 0;
                        byte[] bufferOut = new byte[8192];
                        while ((bytes = in.read(bufferOut)) != -1) {
                            out.write(bufferOut, 0, bytes);
                        }
                    }
                }
            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();
            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        }  finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }

}
