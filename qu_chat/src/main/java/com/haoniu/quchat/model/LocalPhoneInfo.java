package com.haoniu.quchat.model;

/**
 * @author lhb
 * 手机通讯录
 */
public class LocalPhoneInfo {
    /**
     * 联系人姓名
     * 电话号码
     */
    private String name;
    private String telPhone;

    public LocalPhoneInfo(String name, String telPhone) {
        this.name = name;
        this.telPhone = telPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }
}

