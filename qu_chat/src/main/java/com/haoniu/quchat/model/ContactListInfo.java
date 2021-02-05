package com.haoniu.quchat.model;

import java.io.Serializable;
import java.util.List;

public class ContactListInfo implements Serializable {

    private int cacheVersion;

    public void setAppUserFriendVoList(List<DataBean> appUserFriendVoList) {
        this.appUserFriendVoList = appUserFriendVoList;
    }

    private List<DataBean> appUserFriendVoList;

    public List<DataBean> getData() {
        return appUserFriendVoList;
    }
    public List<DataBean> getAppUserFriendVoList() {
        return appUserFriendVoList;
    }

    public int getCacheVersion() {
        return cacheVersion;
    }

    public void setCacheVersion(int cacheVersion) {
        this.cacheVersion = cacheVersion;
    }

    public static class DataBean implements Serializable {

        private String userId;
        private String friendUserId;
        private String blackStatus;
        private String friendNickName;
        private String friendUserHead;
        private String addGroupFlag;

        public String getFriendUserCode() {
            return friendUserCode;
        }

        public void setFriendUserCode(String friendUserCode) {
            this.friendUserCode = friendUserCode;
        }

        private String friendUserCode;

        public String getAddGroupFlag() {
            return addGroupFlag;
        }

        public void setAddGroupFlag(String addGroupFlag) {
            this.addGroupFlag = addGroupFlag;
        }

        public String getFriendUserHead() {
            return friendUserHead;
        }

        public void setFriendUserHead(String friendUserHead) {
            this.friendUserHead = friendUserHead;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFriendUserId() {
            return friendUserId;
        }

        public void setFriendUserId(String friendUserId) {
            this.friendUserId = friendUserId;
        }

        public String getBlackStatus() {
            return blackStatus;
        }

        public void setBlackStatus(String blackStatus) {
            this.blackStatus = blackStatus;
        }

        public String getFriendNickName() {
            return friendNickName;
        }

        public void setFriendNickName(String friendNickName) {
            this.friendNickName = friendNickName;
        }
    }
}
