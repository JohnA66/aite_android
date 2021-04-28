package com.haoniu.quchat.entity;


import com.zds.base.upDated.model.LibraryUpdateEntity;

/**
 * Created by 帅
 * on 2017/7/6.
 */

public class VersionInfo implements LibraryUpdateEntity {


    /**
     * createTime : 1.542183952E12
     * delFlag : 0.0
     * id : 2.0
     * updateAddress : http://zcdoe.com:8088/ddlred.apk
     * updateContent : 飞鱼红包更新
     * updateState : 2.0
     * updateTime : 1.541410999E12
     * verInformat : 1.2
     * verNumber : 3.0
     * verRemark : Android
     */

    private double createTime;
    private double delFlag;
    private double id;
    private String updateAddress;
    private String updateContent;
    private double updateState;
    private double updateTime;
    private String verInformat;
    private double verNumber;
    private String verRemark;

    private int isGrade;
    @Override
    public int getVersionCodes() {
        return new Double(verNumber).intValue();
    }

    @Override
    public int getIsForceUpdates() {
        return updateState == 1 ? 2 : 0;
    }

    @Override
    public int getPreBaselineCodes() {
        return 0;
    }

    @Override
    public String getVersionNames() {
        return verInformat;
    }

    @Override
    public String getDownurls() {
        return updateAddress;
    }

    @Override
    public String getUpdateLogs() {
        return updateContent == null ? ("新版本，欢迎更新") : (updateContent.equals("null") ? "新版本，欢迎更新" : updateContent);
    }

    @Override
    public String getApkSizes() {
        return "100";
    }

    @Override
    public String getHasAffectCodess() {
        return "";
//        0 != 0 ? v() : ""
    }

    @Override
    public int isGrade() {
        return isGrade;
    }


    public double getCreateTime() {
        return createTime;
    }

    public void setCreateTime(double createTime) {
        this.createTime = createTime;
    }

    public double getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(double delFlag) {
        this.delFlag = delFlag;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getUpdateAddress() {
        return updateAddress;
    }

    public void setUpdateAddress(String updateAddress) {
        this.updateAddress = updateAddress;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public double getUpdateState() {
        return updateState;
    }

    public void setUpdateState(double updateState) {
        this.updateState = updateState;
    }

    public double getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(double updateTime) {
        this.updateTime = updateTime;
    }

    public String getVerInformat() {
        return verInformat;
    }

    public void setVerInformat(String verInformat) {
        this.verInformat = verInformat;
    }

    public double getVerNumber() {
        return verNumber;
    }

    public void setVerNumber(double verNumber) {
        this.verNumber = verNumber;
    }

    public String getVerRemark() {
        return verRemark;
    }

    public void setVerRemark(String verRemark) {
        this.verRemark = verRemark;
    }

    public int getIsGrade() {
        return isGrade;
    }

    public void setIsGrade(int isGrade) {
        this.isGrade = isGrade;
    }
}
