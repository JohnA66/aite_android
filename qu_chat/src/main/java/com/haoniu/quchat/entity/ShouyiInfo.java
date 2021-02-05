package com.haoniu.quchat.entity;

/**
 * @author Administrator
 *         日期 2018/8/10
 *         描述 收益
 */

public class ShouyiInfo {


    /**
     * awardMoney : 0.3864
     * awardType : 1.0
     * createTime : 1.548033094E12
     * dealStatus : 2.0
     * delFlag : 0.0
     * fromUser : 15.0
     * fromUserName :
     * id : 102.0
     * level : 0.0
     * relatePacket : 836.0
     * toUser : 14.0
     */

    private double awardMoney;
    private int awardType;
    private long createTime;
    private int dealStatus;
    private int delFlag;
    private int fromUser;
    private String fromUserName;
    private int id;
    private int level;
    private int relatePacket;
    private int toUser;

    public double getAwardMoney() {
        return awardMoney;
    }

    public void setAwardMoney(double awardMoney) {
        this.awardMoney = awardMoney;
    }

    public int getAwardType() {
        return awardType;
    }

    public void setAwardType(int awardType) {
        this.awardType = awardType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(int dealStatus) {
        this.dealStatus = dealStatus;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public int getFromUser() {
        return fromUser;
    }

    public void setFromUser(int fromUser) {
        this.fromUser = fromUser;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRelatePacket() {
        return relatePacket;
    }

    public void setRelatePacket(int relatePacket) {
        this.relatePacket = relatePacket;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }
}
