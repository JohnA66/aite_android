package com.haoniu.quchat.entity;

import java.util.List;

public class NewFindInfo {

    public String message;
    public int code;
    public List<NewFindInfoItem> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<NewFindInfoItem> getData() {
        return data;
    }

    public void setData(List<NewFindInfoItem> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NewFindInfo{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }

    public static class NewFindInfoItem {
        public long createTime;
        public long updateTime;
        public int id;
        public String name;
        public String picUrl;
        public String redirectUrl;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getRedirectUrl() {
            return redirectUrl;
        }

        public void setRedirectUrl(String redirectUrl) {
            this.redirectUrl = redirectUrl;
        }

        @Override
        public String toString() {
            return "NewFindInfoItem{" +
                    "createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", picUrl='" + picUrl + '\'' +
                    ", redirectUrl='" + redirectUrl + '\'' +
                    '}';
        }
    }

}
