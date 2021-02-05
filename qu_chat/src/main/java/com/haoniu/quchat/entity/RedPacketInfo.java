package com.haoniu.quchat.entity;

import java.util.List;

/**
 * 描   述:红包详情实体类
 * 更新日期: 2019/06/25
 *
 * @author lhb
 */
public class RedPacketInfo {


    /**
     * createTime : 2019-06-25 16:16:50.362
     * updateTime : 2019-06-25 16:16:50.362
     * redPacketId : 952989df972111e9828a7cd30a5a2048
     * groupId : 9e55942093f611e9828a7cd30a5a2048
     * userId : ad17f83791b311e9828a7cd30a5a2048
     * packetAmount : 3
     * money : 14
     * type : 1
     * status : 0
     * remark : null
     * userNickName : 15385136772
     * userHead : http://dingyue.nosdn.127.net/0xLZWhWdfwnKpHgRUjpQCokz9ddDTm6LYWctCaXrxzuMG1548687047116.png
     * hxGroupId : 85672752906241
     * redPacketDetailList : [{"createTime":"2019-06-25 16:16:50.000","updateTime":"2019-06-25 16:16:55.083","detailId":"286c7831deee48fd9ab3b2dc12eaffde","redPacketId":"952989df972111e9828a7cd30a5a2048","userId":"ad17f83791b311e9828a7cd30a5a2048","money":6.48,"status":"1","luckFlag":"0","robTime":1561450615083,"robUserName":"15385136772","robUserHead":"http://47.107.131.59:8763/chatinte/profile/http://dingyue.nosdn.127.net/0xLZWhWdfwnKpHgRUjpQCokz9ddDTm6LYWctCaXrxzuMG1548687047116.png"}]
     */

    private String createTime;
    private String updateTime;
    private String redPacketId;
    private String groupId;
    private String userId;
    private int packetAmount;
    private double money;
    private String type;
    private String status;
    private Object remark;
    private String userNickName;
    private String userHead;
    private String hxGroupId;
    private String robFinishTime;

    public String getRobFinishTime() {
        return robFinishTime;
    }

    public void setRobFinishTime(String robFinishTime) {
        this.robFinishTime = robFinishTime;
    }

    private List<RedPacketDetailListBean> redPacketDetailList;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRedPacketId() {
        return redPacketId;
    }

    public void setRedPacketId(String redPacketId) {
        this.redPacketId = redPacketId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPacketAmount() {
        return packetAmount;
    }

    public void setPacketAmount(int packetAmount) {
        this.packetAmount = packetAmount;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getHxGroupId() {
        return hxGroupId;
    }

    public void setHxGroupId(String hxGroupId) {
        this.hxGroupId = hxGroupId;
    }

    public List<RedPacketDetailListBean> getRedPacketDetailList() {
        return redPacketDetailList;
    }

    public void setRedPacketDetailList(List<RedPacketDetailListBean> redPacketDetailList) {
        this.redPacketDetailList = redPacketDetailList;
    }

    public static class RedPacketDetailListBean {
        /**
         * createTime : 2019-06-25 16:16:50.000
         * updateTime : 2019-06-25 16:16:55.083
         * detailId : 286c7831deee48fd9ab3b2dc12eaffde
         * redPacketId : 952989df972111e9828a7cd30a5a2048
         * userId : ad17f83791b311e9828a7cd30a5a2048
         * money : 6.48
         * status : 1
         * luckFlag : 0
         * robTime : 1561450615083
         * robUserName : 15385136772
         * robUserHead : http://47.107.131.59:8763/chatinte/profile/http://dingyue.nosdn.127.net/0xLZWhWdfwnKpHgRUjpQCokz9ddDTm6LYWctCaXrxzuMG1548687047116.png
         */

        private Long createTime;
        private String updateTime;
        private String detailId;
        private String redPacketId;
        private String userId;
        private double money;
        private String status;
        private String luckFlag;
        private long robTime;
        private String robUserName;
        private String robUserHead;

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getDetailId() {
            return detailId;
        }

        public void setDetailId(String detailId) {
            this.detailId = detailId;
        }

        public String getRedPacketId() {
            return redPacketId;
        }

        public void setRedPacketId(String redPacketId) {
            this.redPacketId = redPacketId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLuckFlag() {
            return luckFlag;
        }

        public void setLuckFlag(String luckFlag) {
            this.luckFlag = luckFlag;
        }

        public long getRobTime() {
            return robTime;
        }

        public void setRobTime(long robTime) {
            this.robTime = robTime;
        }

        public String getRobUserName() {
            return robUserName;
        }

        public void setRobUserName(String robUserName) {
            this.robUserName = robUserName;
        }

        public String getRobUserHead() {
            return robUserHead;
        }

        public void setRobUserHead(String robUserHead) {
            this.robUserHead = robUserHead;
        }
    }
}
