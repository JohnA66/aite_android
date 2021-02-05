package com.haoniu.quchat.entity;

/**
 * 作   者：赵大帅
 * 描   述:
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/11/28 11:58
 * 更新日期: 2017/11/28
 */
public class SingInInfo {

    /**
     * createTime : 2017-11-28 11:50:33
     * id : 13
     * seriesCount : 1
     * signCredit : 100
     * userId : 7
     */

    private String createTime;
    private int id;
    private int seriesCount;
    private int signCredit;
    private int userId;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeriesCount() {
        return seriesCount;
    }

    public void setSeriesCount(int seriesCount) {
        this.seriesCount = seriesCount;
    }

    public int getSignCredit() {
        return signCredit;
    }

    public void setSignCredit(int signCredit) {
        this.signCredit = signCredit;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
