package com.haoniu.quchat.entity;

/**
 * 作   者：赵大帅
 * 描   述:
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/12/13 16:42
 * 更新日期: 2017/12/13
 */
public class TransferRecordInfo {


    /**
     * createTime : 2019-07-22 19:04:05
     * updateTime : null
     * currenPage : 1
     * pageNum : null
     * pageSize : 10
     * start : 0
     * length : null
     * draw : null
     * pageFlag : null
     * excelDataMax : null
     * totalFlag : null
     * transferId : 6bfabbbcac7011e9a9af00163e0f701a
     * userId : 2db9f5d9a95611e9a9af00163e0f701a
     * fromUser : 50e55df0a95611e9a9af00163e0f701a
     * transferType : 2
     * money : 11
     * afterMoney : 538
     * remark :
     * userHead : null
     * nickName : 艾特新用户8367
     */

    private Long createTime;
    private Object updateTime;
    private int currenPage;
    private Object pageNum;
    private int pageSize;
    private int start;
    private int sureStatus;
    private Object length;
    private Object draw;
    private Object pageFlag;
    private Object excelDataMax;
    private Object totalFlag;
    private String transferId;
    private String userId;
    private String fromUser;
    private String transferType;
    private double money;
    private double afterMoney;
    private String remark;
    private Object userHead;
    private String nickName;

    public int getSureStatus() {
        return sureStatus;
    }

    public void setSureStatus(int sureStatus) {
        this.sureStatus = sureStatus;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public int getCurrenPage() {
        return currenPage;
    }

    public void setCurrenPage(int currenPage) {
        this.currenPage = currenPage;
    }

    public Object getPageNum() {
        return pageNum;
    }

    public void setPageNum(Object pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public Object getLength() {
        return length;
    }

    public void setLength(Object length) {
        this.length = length;
    }

    public Object getDraw() {
        return draw;
    }

    public void setDraw(Object draw) {
        this.draw = draw;
    }

    public Object getPageFlag() {
        return pageFlag;
    }

    public void setPageFlag(Object pageFlag) {
        this.pageFlag = pageFlag;
    }

    public Object getExcelDataMax() {
        return excelDataMax;
    }

    public void setExcelDataMax(Object excelDataMax) {
        this.excelDataMax = excelDataMax;
    }

    public Object getTotalFlag() {
        return totalFlag;
    }

    public void setTotalFlag(Object totalFlag) {
        this.totalFlag = totalFlag;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getAfterMoney() {
        return afterMoney;
    }

    public void setAfterMoney(double afterMoney) {
        this.afterMoney = afterMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Object getUserHead() {
        return userHead;
    }

    public void setUserHead(Object userHead) {
        this.userHead = userHead;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
