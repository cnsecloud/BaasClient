package com.zdya.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class JsonUtil {

    public static void main(String[] args) {
        System.out.println("aa".compareTo("aabb"));
        Map<String,String> aa = new TreeMap<>();
        aa.put("aa","1");
        aa.put("aabb","1");
        aa.put("aa_","1");
        aa.put("aacc","1");
        aa.put("aaaa","1");
        System.out.println(aa);
        System.out.println(toJsonString(aa));
    }

    public static String toJsonString(Object object) {
        return JSONObject.toJSONString(object);
    }

    public static String toASCIIJsonString(Object object){
        return toJsonString(jsonToASCIIMap(toJsonString(object)));
    }

    public static Map<String, Object> jsonToASCIIMap(String jsonStr) {
        JSONObject json = JSONObject.parseObject(jsonStr);
        return (Map<String, Object>) parseJSON2ASCIIMap2(json);
    }

    public static List<Object> parseToASCIIList(String jsonStr) {
        JSONArray json = JSONArray.parseArray(jsonStr);
        return (List<Object>) parseJSON2ASCIIMap2(json);
    }


    /**
     * 将Json字符串反序列化成Map，最外层为对象时使用此序列化
     *
     * @param jsonStr 字符串
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseToMap(String jsonStr) {
        JSONObject json = JSONObject.parseObject(jsonStr);
        return (Map<String, Object>) parseJSON2Map2(json);
    }

    /**
     * 将Json字符串反序列化成List,最外层为数组时使用此序列化
     *
     * @param jsonStr 字符串
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Object> parseToList(String jsonStr) {
        JSONArray json = JSONArray.parseArray(jsonStr);
        return (List<Object>) parseJSON2Map2(json);
    }

    /**
     * 根据对象class反序列化，单个对象时用
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T toObjBean(String json, Class<T> clazz) {
        return JSONObject.parseObject(json, clazz);
    }

    /**
     * 根据对象class反序列化，数组时使用
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> toListObjBean(String json, Class<T> clazz) {
        return JSONObject.parseArray(json, clazz);
    }


    private static Object parseJSON2Map2(Object json) {
        if (json == null) {
            return null;
        }
        if (json instanceof JSONObject) {
            Map<String, Object> map = new TreeMap<>();
            for (Object k : ((JSONObject) json).keySet()) {
                Object v = ((JSONObject) json).get(k);
                if (v instanceof JSONObject || v instanceof JSONArray) {
                    map.put(k.toString(), parseJSON2Map2(v));
                } else {
                    map.put(k.toString(), v);
                }
            }
            return map;
        } else if (json instanceof JSONArray) {
            List<Object> list = new ArrayList<Object>();
            Iterator<Object> it = ((JSONArray) json).iterator();
            while (it.hasNext()) {
                Object json2 = it.next();
                list.add(parseJSON2Map2(json2));
            }
            return list;
        } else {
            return json;
        }

    }

    private static Object parseJSON2ASCIIMap2(Object json) {
        if (json == null) {
            return null;
        }
        if (json instanceof JSONObject) {
            Map<String, Object> map = new TreeMap<>();
            for (Object k : ((JSONObject) json).keySet()) {
                Object v = ((JSONObject) json).get(k);
                if (v instanceof JSONObject || v instanceof JSONArray) {
                    map.put(k.toString(), parseJSON2ASCIIMap2(v));
                } else {
                    map.put(k.toString(), v);
                }
            }
            return map;
        } else if (json instanceof JSONArray) {
            List<Object> list = new ArrayList<Object>();
            Iterator<Object> it = ((JSONArray) json).iterator();
            while (it.hasNext()) {
                Object json2 = it.next();
                list.add(parseJSON2ASCIIMap2(json2));
            }
            return list;
        } else {
            return json;
        }

    }
}
