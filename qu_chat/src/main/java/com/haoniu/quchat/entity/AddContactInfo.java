package com.haoniu.quchat.entity;

/**
 * Created by lhb on 2019/3/7.
 * 添加好艾特息实体类
 */

public class AddContactInfo {
    /**
     * friendFlag : 0
     * nickName : A张晓辉
     * phone : 007856
     * userId : 7856
     * userImg : http://thirdwx.qlogo.cn/mmopen/vi_32/gWlLTgnfq2CnsHRiabsjBnv2wk7ZCsicOxMYjMnzgmYVV6PaIx3mnDBfCF82iaCufUa5nUZdttRG5aeCiaN0Z3VViaw/132
     */

    private int friendFlag;
    private String nickName;
    private String phone;
    private String userId;
    private String userImg;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public int getFriendFlag() {
        return friendFlag;
    }

    public void setFriendFlag(int friendFlag) {
        this.friendFlag = friendFlag;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
}
