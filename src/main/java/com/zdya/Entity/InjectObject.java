package com.zdya.Entity;

public class InjectObject extends BaseObject{
    private String txid;

    public InjectObject(String code, String reason, String txid, String timestamp) {
        this.setCode(code);
        this.setReason(reason);
        this.setTimestamp(timestamp);
        this.txid = txid;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    @Override
    public String toString() {
        return "InjectObject{" +
                "txid='" + txid + '\'' +
                '}'+super.toString();
    }
}
