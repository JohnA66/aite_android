package com.haoniu.quchat.model;

public class FriendApplyListInfo {
    /**
     * toUserId : ec0e9c988cea11e999de00ffbd97fd96
     * userId : e6e7e8df8cd911e999de00ffbd97fd96
     * userNickName : 18226655973
     * userHead : null
     * applyStatus : 0
     * applyId : 8006368f8da511e999de00ffbd97fd96
     */

    private String toUserId;
    private String userId;
    private String userNickName;
    private String userHead;
    private String applyStatus;
    private String applyId;

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }
}
