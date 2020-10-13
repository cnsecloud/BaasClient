package com.zdya.Entity;

public class DownloadObject extends BaseObject {
    private String src;

    public DownloadObject(String code, String reason,String src) {
        this.setCode(code);
        this.setReason(reason);
        this.src = src;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return "DownloadObject{" +
                "src='" + src + '\'' +
                '}' + super.toString();
    }
}
