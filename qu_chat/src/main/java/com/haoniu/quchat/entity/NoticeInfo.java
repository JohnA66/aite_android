package com.haoniu.quchat.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作   者：赵大帅
 * 描   述:
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/12/30 12:02
 * 更新日期: 2017/12/30
 *
 * @author 赵大帅
 */
public class NoticeInfo implements Parcelable {


    /**
     * createBy : admin
     * createTime : 1.52117118E12
     * noticeContent : 新版本内容
     * noticeId : 1.0
     * noticeTitle : 温馨提醒：2018-07-01 若依新版本发布啦
     * noticeType : 2
     * remark : 管理员
     * status : 0
     * updateBy : ry
     * updateTime : 1.52117118E12
     */

    private String createBy;
    private long createTime;
    private String noticeContent;
    private int noticeId;
    private String noticeTitle;
    private String noticeType;
    private String remark;
    private String status;
    private String updateBy;
    private long updateTime;


    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createBy);
        dest.writeLong(this.createTime);
        dest.writeString(this.noticeContent);
        dest.writeInt(this.noticeId);
        dest.writeString(this.noticeTitle);
        dest.writeString(this.noticeType);
        dest.writeString(this.remark);
        dest.writeString(this.status);
        dest.writeString(this.updateBy);
        dest.writeLong(this.updateTime);
    }

    public NoticeInfo() {
    }

    protected NoticeInfo(Parcel in) {
        this.createBy = in.readString();
        this.createTime = in.readLong();
        this.noticeContent = in.readString();
        this.noticeId = in.readInt();
        this.noticeTitle = in.readString();
        this.noticeType = in.readString();
        this.remark = in.readString();
        this.status = in.readString();
        this.updateBy = in.readString();
        this.updateTime = in.readLong();
    }

    public static final Parcelable.Creator<NoticeInfo> CREATOR = new Parcelable.Creator<NoticeInfo>() {
        @Override
        public NoticeInfo createFromParcel(Parcel source) {
            return new NoticeInfo(source);
        }

        @Override
        public NoticeInfo[] newArray(int size) {
            return new NoticeInfo[size];
        }
    };
}
