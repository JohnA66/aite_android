package com.haoniu.quchat.model;

public class RecommandInfo {
    /**
     * createTime : 1563175404000
     * updateTime : 2019-07-17 15:31:40.090
     * newsId : 6e42ff24a6d111e9a9af00163e0f701a
     * title : 测试4
     * coverImage : http://tianyuancname.tianyuanlife.club/coverImage/2019/07/15/e158a185a9a34e3486d2e0848b1de82b.png
     * pushUser : 32232
     * pushTime : 1563175404000
     * newsDetails : <p><img src="http://tianyuancname.tianyuanlife.club/uText/2019/07/16/5d342302965241e6a2b03c907cb1aded.jpg" width="100%" height="auto">323222</p>
     * newsConnect : null
     * readNum : 12
     */

    private long createTime;
    private String updateTime;
    private String newsId;
    private String title;
    private String coverImage;
    private String pushUser;
    private long pushTime;
    private String newsDetails;
    private Object newsConnect;
    private int readNum;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getPushUser() {
        return pushUser;
    }

    public void setPushUser(String pushUser) {
        this.pushUser = pushUser;
    }

    public long getPushTime() {
        return pushTime;
    }

    public void setPushTime(long pushTime) {
        this.pushTime = pushTime;
    }

    public String getNewsDetails() {
        return newsDetails;
    }

    public void setNewsDetails(String newsDetails) {
        this.newsDetails = newsDetails;
    }

    public Object getNewsConnect() {
        return newsConnect;
    }

    public void setNewsConnect(Object newsConnect) {
        this.newsConnect = newsConnect;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }
}
