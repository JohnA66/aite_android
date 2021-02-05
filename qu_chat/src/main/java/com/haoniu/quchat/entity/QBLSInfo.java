package com.haoniu.quchat.entity;

/**
 * 作   者：赵大帅
 * 描   述: 趣币流水
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/11/28 18:06
 * 更新日期: 2017/11/28
 */
public class QBLSInfo {

    /**
     * createTime : 2017-11-28 18:02:36
     * description : 抢红包
     * id : 162
     * money : 0.82
     * objectId : 101
     * objectType : 2
     * type : 0
     * userId : 7
     */

    private Long createTime;
    private String description;
    private int id;
    private double money;
    private int objectId;
    private int objectType;
    private int type;
    private int userId;

    private String recomUserName;
    private String recomUserPhone;
    private String nickname;
    private String avatarUrl;
    private String phone;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRecomUserName() {
        return recomUserName;
    }

    public void setRecomUserName(String recomUserName) {
        this.recomUserName = recomUserName;
    }

    public String getRecomUserPhone() {
        return recomUserPhone;
    }

    public void setRecomUserPhone(String recomUserPhone) {
        this.recomUserPhone = recomUserPhone;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
