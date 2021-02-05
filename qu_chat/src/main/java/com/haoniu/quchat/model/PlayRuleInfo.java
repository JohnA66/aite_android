package com.haoniu.quchat.model;

public class PlayRuleInfo {
    /**
     * id : 3
     * type : 1
     * description : <p>恶趣味</p>
     * createTime : 1.556262091E12
     * createBy : admin
     * delFlag : 0
     */

    private int id;
    private int type;
    private String description;
    private double createTime;
    private String createBy;
    private int delFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCreateTime() {
        return createTime;
    }

    public void setCreateTime(double createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}
