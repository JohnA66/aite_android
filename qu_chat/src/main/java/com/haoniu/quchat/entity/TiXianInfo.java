package com.haoniu.quchat.entity;

public class TiXianInfo {

    //支付宝
    private String alipay;

    //时间
    private long createTime;

    //
    private int id;

    //提现金额
    private double money;

    //提现手续费
    private double proceMoney;

    //真实姓名
    private String realName;

    //审核状态
    private String reason;

    //审核原因
    private int status;

    //更新时间
    private long updateTime;

    //用户id
    private int userId;

    //提现方式
    private int way;

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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

    public double getProceMoney() {
        return proceMoney;
    }

    public void setProceMoney(double proceMoney) {
        this.proceMoney = proceMoney;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWay() {
        return way;
    }

    public void setWay(int way) {
        this.way = way;
    }
}
