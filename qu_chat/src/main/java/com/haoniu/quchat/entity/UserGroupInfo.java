package com.haoniu.quchat.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/28.
 */

public class UserGroupInfo implements Serializable {

    /**
     * alipay :
     * bankName :
     * bankNumber :
     * delFlag : 0
     * freezeMoney : 0
     * id : 1
     * money : 871.75
     * payPassword : APW9lip7WOoxqD8PsFHTIpW1uzZugOZWyD5GnDuTcN/jpnA2ZxJG1qRjtsoa1dqMrc1c10uiDuTRLsjJCKwBj3Y=
     * paySalt : zYeLVXvr/DU1uURwwsx8SqbAVc9Ll8pFmQdW0BFoRgw=
     * roomId : 141
     * updateTime : 1.553666508E12
     * userId : 5147
     */

    private String alipay;
    private String bankName;
    private String bankNumber;
    private int delFlag;
    private int freezeMoney;
    private int id;
    private double money;
    private String payPassword;
    private String paySalt;
    private int roomId;
    private double updateTime;
    private int userId;

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public int getFreezeMoney() {
        return freezeMoney;
    }

    public void setFreezeMoney(int freezeMoney) {
        this.freezeMoney = freezeMoney;
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

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getPaySalt() {
        return paySalt;
    }

    public void setPaySalt(String paySalt) {
        this.paySalt = paySalt;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public double getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(double updateTime) {
        this.updateTime = updateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
