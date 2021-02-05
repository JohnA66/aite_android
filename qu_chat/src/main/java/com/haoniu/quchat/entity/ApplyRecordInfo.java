package com.haoniu.quchat.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作   者：赵大帅
 * 描   述:
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/12/28 15:06
 * 更新日期: 2017/12/28
 */
public class ApplyRecordInfo implements Parcelable {


    /**
     * applyBeforeProxy : 0.0
     * applyProxy : 2.0
     * applyUserId : 69.0
     * approverUserId : 7.0
     * content :
     * createTime : 2017-12-27 18:38:24
     * id : 10.0
     * nickname : zhangsan
     * phone : 13245465
     * remark : asdfas
     * status : 3.0
     */


    /**
     * 申请前等级
     */
    private int applyBeforeProxy;
    /**
     * 申请等级
     */
    private int applyProxy;
    /**
     * 申请用户id
     */
    private int applyUserId;
    /**
     * 审批人
     */
    private int approverUserId;
    /**
     * 审批理由
     */
    private String content;
    /**
     * 申请时间
     */
    private Long createTime;
    /**
     * id
     */
    private int id;
    /**
     * 申请人姓名
     */
    private String nickname;
    /**
     * 申请手机号
     */
    private String phone;
    /**
     * 申请理由
     */
    private String remark;
    /**
     * 审批状态(3申请中，2失败，1成功)
     */
    private int status;
    /**
     * applyBeforeProxy : 0.0
     * applyProxy : 2.0
     * applyUserId : 133.0
     * approverUserId : 7.0
     * id : 55.0
     * status : 1.0
     * weixinNum :
     */

    /**
     * 微信号
     */
    private String weixinNum;


    public double getApplyBeforeProxy() {
        return applyBeforeProxy;
    }

    public void setApplyBeforeProxy(int applyBeforeProxy) {
        this.applyBeforeProxy = applyBeforeProxy;
    }

    public int getApplyProxy() {
        return applyProxy;
    }

    public void setApplyProxy(int applyProxy) {
        this.applyProxy = applyProxy;
    }

    public int getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(int applyUserId) {
        this.applyUserId = applyUserId;
    }

    public int getApproverUserId() {
        return approverUserId;
    }

    public void setApproverUserId(int approverUserId) {
        this.approverUserId = approverUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ApplyRecordInfo() {
    }

    public String getWeixinNum() {
        return weixinNum;
    }

    public void setWeixinNum(String weixinNum) {
        this.weixinNum = weixinNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.applyBeforeProxy);
        dest.writeInt(this.applyProxy);
        dest.writeInt(this.applyUserId);
        dest.writeInt(this.approverUserId);
        dest.writeString(this.content);
        dest.writeLong(this.createTime);
        dest.writeInt(this.id);
        dest.writeString(this.nickname);
        dest.writeString(this.phone);
        dest.writeString(this.remark);
        dest.writeInt(this.status);
        dest.writeString(this.weixinNum);
    }

    protected ApplyRecordInfo(Parcel in) {
        this.applyBeforeProxy = in.readInt();
        this.applyProxy = in.readInt();
        this.applyUserId = in.readInt();
        this.approverUserId = in.readInt();
        this.content = in.readString();
        this.createTime = in.readLong();
        this.id = in.readInt();
        this.nickname = in.readString();
        this.phone = in.readString();
        this.remark = in.readString();
        this.status = in.readInt();
        this.weixinNum = in.readString();
    }

    public static final Creator<ApplyRecordInfo> CREATOR = new Creator<ApplyRecordInfo>() {
        @Override
        public ApplyRecordInfo createFromParcel(Parcel source) {
            return new ApplyRecordInfo(source);
        }

        @Override
        public ApplyRecordInfo[] newArray(int size) {
            return new ApplyRecordInfo[size];
        }
    };
}
