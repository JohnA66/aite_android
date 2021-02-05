package com.haoniu.quchat.model;

/**
 * 作   者：赵大帅
 * 描   述: 积分兑换
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/12/8 10:24
 * 更新日期: 2017/12/8
 */
public class CreditRecordsInfo {

    /**
     * createTime : 2017-12-08 11:07:37
     * credit : 10
     * exchangeId : 5
     * id : 6
     * money : 1
     * userId : 7
     */

    private Long createTime;
    private int credit;
    private int exchangeId;
    private int id;
    private double money;
    private int userId;

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(int exchangeId) {
        this.exchangeId = exchangeId;
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

    public void setMoney(int money) {
        this.money = money;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
