package com.zdya.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HTTPUtil {

    public static void main(String[] args) throws IOException {
        // GET
        String url = "http://v.juhe.cn/weather/index?cityname=%E9%83%91%E5%B7%9E&key=be0e422e7ef555953ba89ad91ffe3ec8";
        System.out.println(httpGET(url));

        // POST
        /*Map param = new HashMap();
        param.put("row",8);
        param.put("ye",1);
        System.out.println(httpPost("http://xcx.cnsecloud.com/zdyaMPTP5/public/index/Block",param));*/

    }

    /**
     * http 请求,POST格式为: key1=value1&key2=value2&key3=value3..
     *
     * @param url
     *            地址
     * @param map
     *            内容
     * @return
     * @throws IOException
     */
    public static String httpPost(String url, Map<Object, Object> map) throws IOException {

        StringBuffer sb = new StringBuffer();

        Set<Object> set = map.keySet();
        Object[] osObjects = set.toArray();
        for (int i = 0; i < osObjects.length; i++) {
            Object object = osObjects[i];
            sb.append(object.toString());
            sb.append("=");
            sb.append(URLEncoder.encode(map.get(object).toString(), "UTF-8"));
            if(i != osObjects.length -1){
                sb.append("&");
            }
        }
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headerMap.put("accept", "*/*");
        //System.out.println(url);
        //System.out.println(sb);
        return httpPost(url, headerMap, sb.toString());
    }

    /**
     * HTTP post请求
     *
     * @param url
     *            地址
     * @param content
     *            内容
     * @return
     * @throws IOException
     */
    public static String httpPost(String url, Map<String, String> header, String content) throws IOException {

        URL u = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        if(header != null){
            for (String s : header.keySet()) {
                conn.setRequestProperty(s, header.get(s));
            }
        }
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(20*1000);
        conn.setReadTimeout(20*1000);

        conn.connect();

        OutputStream out = conn.getOutputStream();
        out.write(content.getBytes("UTF-8"));

        out.flush();
        out.close();

        StringBuffer sb = new StringBuffer();
        innerHandle(conn,sb);
        return sb.toString();
    }

    /**
     *
     * http GET请求
     *
     * @param url
     *            地址
     * @return
     * @throws IOException
     */
    public static String httpGET(String url) throws IOException {

        System.out.println("URL:" + url);
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        conn.setRequestMethod("GET");
//		conn.setRequestProperty("Content-Type", "text/html");
        conn.setDoInput(true);
        conn.setConnectTimeout(20*1000);
        conn.setReadTimeout(20*1000);

        conn.connect();

        StringBuffer sb = new StringBuffer();

        innerHandle(conn,sb);

        return sb.toString();
    }

    private static void innerHandle(HttpURLConnection connection, StringBuffer sb) throws IOException {
        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

            String buf = null;

            while ((buf = reader.readLine()) != null) {
                sb.append(buf);
            }

        } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "UTF-8"));

            String buf = null;

            while ((buf = reader.readLine()) != null) {
                sb.append(buf);
            }
            connection.disconnect();
            // logger.error("错误码：" + code + ",错误内容：" + sb.toString());
            throw new IOException("错误码：" + code + ",错误内容：" + sb.toString());
        }

        connection.disconnect();
    }


    public static String httpget(String getUrl) throws IOException {
        StringBuffer sb = new StringBuffer();
        InputStreamReader isr = null;
        BufferedReader br = null;
        try
        {
            URL url = new URL(getUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setAllowUserInteraction(false);
            isr = new InputStreamReader(url.openStream());
            br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null)
            {
                sb.append(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            isr.close();
            br.close();
        }
        return sb.toString();
    }
}
