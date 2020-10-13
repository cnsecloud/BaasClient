package com.zdya.util;
public class UrlUtil {
    public static String base = "";
    public static String api_inject             = base + "api/save";
    public static String api_uploadFile         = base + "uploadFile";
    public static String api_queryBlock         = base + "api/search/block";
    public static String api_queryTransaction   = base + "api/search/transaction";
    public static String api_verify             = base + "api/verify";
    public static void updateAllUrls(String baseUrl){
        UrlUtil.base = baseUrl;
        System.out.println("newBase:"+base);
        api_inject             = base + "api/save";
        api_uploadFile         = base + "uploadFile";
        api_queryBlock         = base + "api/search/block";
        api_queryTransaction   = base + "api/search/transaction";
        api_verify             = base + "api/verify";
    }
}
