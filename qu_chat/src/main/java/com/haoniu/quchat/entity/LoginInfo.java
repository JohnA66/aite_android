package com.haoniu.quchat.entity;

import com.haoniu.quchat.base.Constant;

import java.io.Serializable;

/**
 * @author lhb
 * 用户登录信息
 */
public class LoginInfo implements Serializable {


    /**
     * createTime : 2019-06-15 12:05:56.000
     * updateTime : 2019-06-15 14:54:30.883
     * userId : 1a5435ee8f2311e999de00ffbd97fd96
     * nickName : 18895397923
     * userHead : null
     * userCode : null
     * phone : 18895397923
     * password : null
     * passwordSalt : null
     * sign : null
     * userType : 0
     * authStatus : null
     * tokenId : 66e5c77479164186a2b81449f0e57b5c
     * money : 0
     */

    private String createTime;
    private String updateTime;
    private String userId;
    private String nickName;
    private String userHead;
    private String userCode;
    private String phone;
    private String password;
    private String passwordSalt;
    private String sign;
    private String userType;
    private String authStatus;
    private String tokenId;
    private String handRate;
    private double money;
    private int payPwdFlag;
    private String custId;
    private String sessionKey;
    private String sessionValue;
    private int openAccountFlag;
    private String myPassword;

    public String getMyPassword() {
        return myPassword;
    }

    public void setMyPassword(String myPassword) {
        this.myPassword = myPassword;
    }

    public int getOpenAccountFlag() {
        return openAccountFlag;
    }

    public void setOpenAccountFlag(int openAccountFlag) {
        this.openAccountFlag = openAccountFlag;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSessionValue() {
        return sessionValue;
    }

    public void setSessionValue(String sessionValue) {
        this.sessionValue = sessionValue;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getIdh() {
        return userId + Constant.ID_REDPROJECT;
    }

    public int getPayPwdFlag() {
        return payPwdFlag;
    }

    public void setPayPwdFlag(int payPwdFlag) {
        this.payPwdFlag = payPwdFlag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getHandRate() {
        return handRate;
    }

    public void setHandRate(String handRate) {
        this.handRate = handRate;
    }
}
