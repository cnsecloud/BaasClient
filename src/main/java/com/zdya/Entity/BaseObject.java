package com.zdya.Entity;

import java.util.HashMap;
import java.util.Map;

public class BaseObject {
    private String code;// 结果码
    private String reason;// 错误原因
    private String timestamp;// 时间戳(上链函数返回的是达成共识的时间戳)

    public static void main(String[] args) {
        Map newMap = new HashMap();
        newMap.put("d", "dddd");
        System.out.println(newMap);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "BaseObject{" +
                "code='" + code + '\'' +
                ", reason='" + reason + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
