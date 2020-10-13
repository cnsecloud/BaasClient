package com.zdya.util;

import com.zdya.Entity.*;

import java.util.HashMap;
import java.util.Map;

public class Resp {

    private static final String CODE_KEY = "code";
    private static final String REASON_KEY = "reason";

    public static Map resp(String code, String reason)
    {
        Map<String,String> map = new HashMap<String, String>();
        map.put(CODE_KEY,code);
        map.put(REASON_KEY,reason);
        map.put("timestamp",String.valueOf(System.currentTimeMillis()));
        return map;
    }

    public static BaseObject respBaseObject(String code, String reason){
        BaseObject object = new BaseObject();
        object.setCode(code);
        object.setReason(reason);
        object.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return object;
    }

    public static AUObject respAUObject(String code, String reason,String _id, String txid, String timestamp){
        if (timestamp==null||timestamp.length()==0)
            timestamp = String.valueOf(System.currentTimeMillis());

        return new AUObject(code, reason, _id, txid, timestamp);
    }

    public static InjectObject respInjectObject(String code, String reason, String txid, String timestamp){
        if (timestamp==null||timestamp.length()==0)
            timestamp = String.valueOf(System.currentTimeMillis());

        return new InjectObject(code, reason, txid, timestamp);
    }

    public static DownloadObject respDownloadObject(String code, String reason, String src){
        return new DownloadObject(code, reason, src);
    }

    public static QueryObject respQueryObject(String code, String reason, Map block, Map transaction, Map assetUser){
        return new QueryObject(code, reason, block, transaction, assetUser);
    }



}
