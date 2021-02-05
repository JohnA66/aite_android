package com.haoniu.quchat.model;

/**
 * @author lhb
 * 好艾特息
 */
public class FriendInfo {
    /**
     * userId : 1a5435ee8f2311e999de00ffbd97fd96
     * nickName : 大同
     * userHead : http://47.107.131.59:8763/chatinte/profile/20190624/40efec3d702d6762518225965229eed8.jpg
     * userCode : 1324564654
     */

    private String userId;
    private String nickName;
    private String userHead;
    private String userCode = "";
    private String friendFlag;
    private String blackStatus;
    private String friendNickName;

    public String getFriendNickName() {
        return friendNickName;
    }

    public void setFriendNickName(String friendNickName) {
        this.friendNickName = friendNickName;
    }

    public String getBlackStatus() {
        return blackStatus;
    }

    public void setBlackStatus(String blackStatus) {
        this.blackStatus = blackStatus;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    private String line;

    public String getFriendFlag() {
        return friendFlag;
    }

    public void setFriendFlag(String friendFlag) {
        this.friendFlag = friendFlag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
