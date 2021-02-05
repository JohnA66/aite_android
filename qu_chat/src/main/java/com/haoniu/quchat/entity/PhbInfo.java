package com.haoniu.quchat.entity;

import com.haoniu.quchat.base.Constant;

/**
 * 作   者：赵大帅
 * 描   述:
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/11/29 11:21
 * 更新日期: 2017/11/29
 */
public class PhbInfo {

    /**
     * credit : 0
     * freezeMoney : 0
     * funMoney : 1000
     * id : 20
     * nickname : 会员0012
     * payPassword :
     * phone : 15500000012
     * proxy : 0
     * roomCard : 0
     * rownum : 20
     * userId : 25
     */

    private int credit;
    private int freezeMoney;
    private double funMoney;
    private int id;
    private String nickname;
    private String payPassword;
    private String phone;
    private int proxy;
    private int roomCard;
    private int rownum;
    private int userId;
    private String avatarUrl;


    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getFreezeMoney() {
        return freezeMoney;
    }

    public void setFreezeMoney(int freezeMoney) {
        this.freezeMoney = freezeMoney;
    }

    public double getFunMoney() {
        return funMoney;
    }

    public void setFunMoney(double funMoney) {
        this.funMoney = funMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getProxy() {
        return proxy;
    }

    public void setProxy(int proxy) {
        this.proxy = proxy;
    }

    public int getRoomCard() {
        return roomCard;
    }

    public void setRoomCard(int roomCard) {
        this.roomCard = roomCard;
    }

    public int getRownum() {
        return rownum;
    }

    public void setRownum(int rownum) {
        this.rownum = rownum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUserIdH() {
        return userId + Constant.ID_REDPROJECT;
    }
}
