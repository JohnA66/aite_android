package com.haoniu.quchat.entity;

/**
 * @author 赵大帅
 * 日期 2018/12/27
 * 描述
 */
public class ZhiTuiNumberInfo {


    /**
     * nickName : xy
     * oneLevelNum : 1
     * phone : 15556508253
     * userId : 14
     * userImg : /upload/img/default_avatar.png
     */

    private String nickName;
    private int oneLevelNum;
    private String phone;
    private int userId;
    private String userImg;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getOneLevelNum() {
        return oneLevelNum;
    }

    public void setOneLevelNum(int oneLevelNum) {
        this.oneLevelNum = oneLevelNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
}
