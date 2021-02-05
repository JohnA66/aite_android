package com.haoniu.quchat.model;

/**
 * 作   者：赵大帅
 * 描   述: 积分兑换
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/12/8 10:24
 * 更新日期: 2017/12/8
 */
public class CreditInfo {


    /**
     * createTime : 2017-12-07 10:31:46
     * credit : 5000
     * id : 2
     * money : 5
     * name : 5000积分5趣币
     * status : 0
     */

    private String createTime;
    private int credit;
    private int id;
    private double money;
    private String name;
    private int status;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
