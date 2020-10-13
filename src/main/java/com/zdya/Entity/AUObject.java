package com.zdya.Entity;

/**
 * 资产用户返回对象
 */
public class AUObject extends BaseObject {
    private String _id;// 记录主键值
    private String txid;// 记录实时受理号

    public AUObject(String code, String reason,String _id, String txid, String timestamp) {
        this.setCode(code);
        this.setReason(reason);
        this.setTimestamp(timestamp);
        this._id = _id;
        this.txid = txid;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "AUObject{" +
                "_id='" + _id + '\'' +
                ", txid='" + txid + '\'' +
                '}'+super.toString();
    }
}
