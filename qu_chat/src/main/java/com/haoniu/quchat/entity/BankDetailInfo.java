package com.haoniu.quchat.entity;

/**
 * 银行卡和支付宝信息
 */
public class BankDetailInfo {
    //支付宝
    private String alipay;
    //
    private String bankName;
    private String bankNumber;

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
}
