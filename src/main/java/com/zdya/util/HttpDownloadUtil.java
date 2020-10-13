package com.zdya.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpDownloadUtil {

    /**
     * 下载到字符串
     * @param url
     * @return
     * @throws Exception
     */
    public String httpDownloadFileToString(String url) throws Exception {
        return new String(httpDownloadFileToByte(url), "UTF-8");
    }

    /**
     * 下载到路径
     * @param url
     * @param path
     * @throws IOException
     */
    public static void httpDownloadFileToPath(String url,String path)throws IOException{
        try(FileOutputStream fos = new FileOutputStream(path)){
            httpDownloadFile(url,fos);
        }
    }

    /**
     * 下载到输出流
     * @param url
     * @param out
     * @throws IOException
     */
    public static void httpDownloadFile(String url,OutputStream out)throws IOException{
        URL u = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        Map<String, String> header = new HashMap<>();
        header.put("Accept", "*/*");
        if (header != null) {
            for (String s : header.keySet()) {
                conn.setRequestProperty(s, header.get(s));
            }
        }

        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setInstanceFollowRedirects(true);
        conn.setDoOutput(false);
        conn.connect();

        int code = conn.getResponseCode();
        StringBuffer sb = new StringBuffer();
        if (code == HttpURLConnection.HTTP_OK) {
            InputStream is = conn.getInputStream();

            byte[] buf = new byte[8192];
            int len = -1;
            while ((len = is.read(buf)) != -1) {
                out.write(buf, 0, len);
            }

        } else{
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));

            String buf = null;

            while ((buf = reader.readLine()) != null) {
                sb.append(buf);
            }
            conn.disconnect();
            throw new IOException("错误码：" + code + ",错误内容：" + sb.toString());
        }
        conn.disconnect();
    }

    /**
     * 下载到字节数组
     * @param url
     * @return
     * @throws Exception
     */
    public byte[] httpDownloadFileToByte(String url) throws Exception {
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            httpDownloadFile(url,outputStream);
            return outputStream.toByteArray();
        }
    }


}