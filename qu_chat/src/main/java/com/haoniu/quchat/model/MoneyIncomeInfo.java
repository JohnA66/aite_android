package com.haoniu.quchat.model;

/**
 * 作   者：赵大帅
 * 描   述: 收入
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/12/13 14:06
 * 更新日期: 2017/12/13
 */
public class MoneyIncomeInfo {
    private String nameMessage;
    private String money;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MoneyIncomeInfo(String nameMessage, String money, String type) {
        this.nameMessage = nameMessage;
        this.type = type;
        this.money = money;
    }

    public String getNameMessage() {
        return nameMessage;
    }

    public void setNameMessage(String nameMessage) {
        this.nameMessage = nameMessage;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
