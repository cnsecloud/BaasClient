package com.zdya.Entity;

import java.util.TreeMap;

public class ResponseEntity extends Object {
    private String version;
    private String sign;
    private TreeMap content;

    public ResponseEntity(String version, String sign, TreeMap content) {
        this.version = version;
        this.sign = sign;
        this.content = content;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public TreeMap getContent() {
        return content;
    }

    public void setContent(TreeMap content) {
        this.content = content;
    }
}
