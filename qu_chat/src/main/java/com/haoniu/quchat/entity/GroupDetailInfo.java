package com.haoniu.quchat.entity;

import java.io.Serializable;
import java.util.List;

public class GroupDetailInfo implements Serializable {

    private String groupId;
    private String huanxinGroupId;
    private String groupName;
    private String groupHead;
    private String groupNotice;
    private String groupUserNickName;
    private Long updateGroupNoticeTime;
    private int groupUsers;
    private int groupUserRank;
    private int seeFriendFlag;
    private String groupSayFlag;

    private String sayStatus;
    private int groupVersion;

    public int getGroupVersion() {
        return groupVersion;
    }

    public void setGroupVersion(int groupVersion) {
        this.groupVersion = groupVersion;
    }

    public int getSeeFriendFlag() {
        return seeFriendFlag;
    }

    public void setSeeFriendFlag(int seeFriendFlag) {
        this.seeFriendFlag = seeFriendFlag;
    }

    public String getGroupSayFlag() {
        return groupSayFlag;
    }

    public void setGroupSayFlag(String groupSayFlag) {
        this.groupSayFlag = groupSayFlag;
    }

    private List<GroupUserDetailVoListBean> groupUserDetailVoList;

    public int getGroupUserRank() {
        return groupUserRank;
    }

    public void setGroupUserRank(int groupUserRank) {
        this.groupUserRank = groupUserRank;
    }

    public Long getUpdateGroupNoticeTime() {
        return updateGroupNoticeTime;
    }

    public void setUpdateGroupNoticeTime(Long updateGroupNoticeTime) {
        this.updateGroupNoticeTime = updateGroupNoticeTime;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getHuanxinGroupId() {
        return huanxinGroupId;
    }

    public void setHuanxinGroupId(String huanxinGroupId) {
        this.huanxinGroupId = huanxinGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupHead() {
        return groupHead;
    }

    public void setGroupHead(String groupHead) {
        this.groupHead = groupHead;
    }

    public String getGroupNotice() {
        return groupNotice;
    }

    public void setGroupNotice(String groupNotice) {
        this.groupNotice = groupNotice;
    }

    public String getGroupUserNickName() {
        return groupUserNickName;
    }

    public void setGroupUserNickName(String groupUserNickName) {
        this.groupUserNickName = groupUserNickName;
    }

    public int getGroupUsers() {
        return groupUsers;
    }

    public void setGroupUsers(int groupUsers) {
        this.groupUsers = groupUsers;
    }

    public List<GroupUserDetailVoListBean> getGroupUserDetailVoList() {
        return groupUserDetailVoList;
    }

    public void setGroupUserDetailVoList(List<GroupUserDetailVoListBean> groupUserDetailVoList) {
        this.groupUserDetailVoList = groupUserDetailVoList;
    }

    public String getSayStatus() {
        return sayStatus;
    }

    public void setSayStatus(String sayStatus) {
        this.sayStatus = sayStatus;
    }

    public static class GroupUserDetailVoListBean implements Serializable {
        /**
         * groupUserId : 55e489cf89594fcc8371529d50e28677
         * groupId : 97098ed393f611e9828a7cd30a5a2048
         * userId : ad17f83791b311e9828a7cd30a5a2048
         * userNickName : 15385136772
         * userHead : http://dingyue.nosdn.127.net/0xLZWhWdfwnKpHgRUjpQCokz9ddDTm6LYWctCaXrxzuMG1548687047116.png
         * userRank : 0
         * entryUserId : 1a5435ee8f2311e999de00ffbd97fd96
         * entryType : 0
         * sayStatus : 0
         */

        private String groupUserId;
        private String groupId;
        private String userId;
        private String userNickName;
        private String userHead;
        private String userRank;
        private String entryUserId;
        private String entryType;
        private String sayStatus;
        private String friendNickName;
        private String nickName;

        public String getFriendNickName() {
            return friendNickName;
        }

        public void setFriendNickName(String friendNickName) {
            this.friendNickName = friendNickName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getTop() {
            return top;
        }

        public void setTop(String top) {
            this.top = top;
        }

        private String top;


        public String getGroupUserId() {
            return groupUserId;
        }

        public void setGroupUserId(String groupUserId) {
            this.groupUserId = groupUserId;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
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

        public String getUserRank() {
            return userRank;
        }

        public void setUserRank(String userRank) {
            this.userRank = userRank;
        }

        public String getEntryUserId() {
            return entryUserId;
        }

        public void setEntryUserId(String entryUserId) {
            this.entryUserId = entryUserId;
        }

        public String getEntryType() {
            return entryType;
        }

        public void setEntryType(String entryType) {
            this.entryType = entryType;
        }

        public String getSayStatus() {
            return sayStatus;
        }

        public void setSayStatus(String sayStatus) {
            this.sayStatus = sayStatus;
        }
    }
}
