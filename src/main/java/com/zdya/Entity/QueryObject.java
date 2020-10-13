package com.zdya.Entity;

import java.util.Map;

public class QueryObject extends BaseObject {
    private Map block;
    private Map transaction;
    private Map assetUser;

    public QueryObject(String code, String reason, Map block, Map transaction, Map assetUser) {
        this.setCode(code);
        this.setReason(reason);
        this.setTimestamp(String.valueOf(System.currentTimeMillis()));
        this.block = block;
        this.transaction = transaction;
        this.assetUser = assetUser;
    }

    public Map getBlock() {
        return block;
    }

    public void setBlock(Map block) {
        this.block = block;
    }

    public Map getTransaction() {
        return transaction;
    }

    public void setTransaction(Map transaction) {
        this.transaction = transaction;
    }

    public Map getAssetUser() {
        return assetUser;
    }

    public void setAssetUser(Map assetUser) {
        this.assetUser = assetUser;
    }

    @Override
    public String toString() {
        return "QueryObject{" +
                "block=" + block +
                ", transaction=" + transaction +
                ", assetUser=" + assetUser +
                '}'+super.toString();
    }
}
