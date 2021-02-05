package com.haoniu.quchat.entity;

/**
 * 收支明细
 */

public class BalancePaymentInfo {

    /**
     * alipay :
     * createTime : 2018-01-24 15:45:35
     * description :
     * id : 5
     * money : 100
     * realname :
     * status : 1
     * type : 0
     * userId : 7
     */

    /**
     * 支付宝账号
     */
    private String alipay;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 描述
     */
    private String description;
    /**
     * id
     */
    private int id;
    /**
     * 充值提现金额
     */
    private String money;
    /**
     * 真实姓名
     */
    private String realname;
    /**
     * 状态
     */
    private int status;
    /**
     *  类型
     */
    private int type;
    /**
     * 用户id
     */
    private int userId;

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
