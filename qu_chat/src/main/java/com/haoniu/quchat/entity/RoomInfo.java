package com.haoniu.quchat.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 作   者：赵大帅
 * 描   述: 房间
 * 日   期: 2017/11/15 10:45
 * 更新日期: 2017/11/15
 *
 * @author Administrator
 */

public class RoomInfo implements Serializable {


    /**
     * amount : 0
     * createTime : 1548659691000
     * delFlag : 0
     * huanxinGroupId : 72625726488577
     * id : 131
     * isWelfare : 0
     * name : 多雷房间
     * notice :
     * odds : 0
     * personal : 0
     * roomImg : /room/1548665695935.jpg
     * roomLeiList : [{"amount":7,"createTime":1548659691000,"delFlag":0,"eightLei":0,"fiveLei":0,"fourLei":1.02,"id":10,"moneyMax":20,"moneyMin":10,"nineLei":0,"oneLei":1.6,"roomId":131,"sevenLei":0,"sixLei":0,"threeLei":0,"twoLei":0,"type":"1","updateTime":1548659996000},{"amount":9,"createTime":1548659691000,"delFlag":0,"eightLei":2.2,"fiveLei":2.2,"fourLei":1.7,"id":11,"moneyMax":20,"moneyMin":10,"nineLei":2.2,"oneLei":1.6,"roomId":131,"sevenLei":2.2,"sixLei":2.2,"threeLei":1.25,"twoLei":1,"type":"2","updateTime":1548659998000}]
     * roomUserNum : 14
     * type : 2
     * updateTime : 1548659987000
     * userId : 0
     * welfareType :
     */

    private int amount;
    private long createTime;
    private int delFlag;
    private String huanxinGroupId;
    private int id;
    private int isWelfare;
    private String name;
    private String groupNumber;
    private String notice;
    private double odds;
    private int personal;
    private String roomImg;
    private int roomUserNum;
    private int type;
    private long updateTime;
    private int userId;
    private String welfareType;
    private List<RoomLeiListBean> roomLeiList;
    private String groupOwner;
    private double money;
    private String expirationDate;
    private String groupUrl;
    private String groupName;
    private double moneyMin;
    private double moneyMax;
    private int autoRob;

    public int getAutoRob() {
        return autoRob;
    }

    public void setAutoRob(int autoRob) {
        this.autoRob = autoRob;
    }

    public double getMoneyMin() {
        return moneyMin;
    }

    public void setMoneyMin(double moneyMin) {
        this.moneyMin = moneyMin;
    }

    public double getMoneyMax() {
        return moneyMax;
    }

    public void setMoneyMax(double moneyMax) {
        this.moneyMax = moneyMax;
    }

    public String getGroupUrl() {
        return groupUrl;
    }

    public void setGroupUrl(String groupUrl) {
        this.groupUrl = groupUrl;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getGroupOwner() {
        return groupOwner;
    }

    public void setGroupOwner(String groupOwner) {
        this.groupOwner = groupOwner;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getHuanxinGroupId() {
        return huanxinGroupId;
    }

    public void setHuanxinGroupId(String huanxinGroupId) {
        this.huanxinGroupId = huanxinGroupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsWelfare() {
        return isWelfare;
    }

    public void setIsWelfare(int isWelfare) {
        this.isWelfare = isWelfare;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }

    public int getPersonal() {
        return personal;
    }

    public void setPersonal(int personal) {
        this.personal = personal;
    }

    public String getRoomImg() {
        return roomImg;
    }

    public void setRoomImg(String roomImg) {
        this.roomImg = roomImg;
    }

    public int getRoomUserNum() {
        return roomUserNum;
    }

    public void setRoomUserNum(int roomUserNum) {
        this.roomUserNum = roomUserNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getWelfareType() {
        return welfareType;
    }

    public void setWelfareType(String welfareType) {
        this.welfareType = welfareType;
    }

    public List<RoomLeiListBean> getRoomLeiList() {
        return roomLeiList;
    }

    public void setRoomLeiList(List<RoomLeiListBean> roomLeiList) {
        this.roomLeiList = roomLeiList;
    }

    public static class RoomLeiListBean implements Serializable {
        /**
         * amount : 7
         * createTime : 1548659691000
         * delFlag : 0
         * eightLei : 0.0
         * fiveLei : 0.0
         * fourLei : 1.02
         * id : 10
         * moneyMax : 20.0
         * moneyMin : 10.0
         * nineLei : 0.0
         * oneLei : 1.6
         * roomId : 131
         * sevenLei : 0.0
         * sixLei : 0.0
         * threeLei : 0.0
         * twoLei : 0.0
         * type : 1
         * updateTime : 1548659996000
         */

        private int amount;
        private long createTime;
        private int delFlag;
        private double eightLei;
        private double fiveLei;
        private double fourLei;
        private int id;
        private double moneyMax;
        private double moneyMin;
        private double nineLei;
        private double oneLei;
        private int roomId;
        private double sevenLei;
        private double sixLei;
        private double threeLei;
        private double twoLei;
        private String type;
        private long updateTime;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public double getEightLei() {
            return eightLei;
        }

        public void setEightLei(double eightLei) {
            this.eightLei = eightLei;
        }

        public double getFiveLei() {
            return fiveLei;
        }

        public void setFiveLei(double fiveLei) {
            this.fiveLei = fiveLei;
        }

        public double getFourLei() {
            return fourLei;
        }

        public void setFourLei(double fourLei) {
            this.fourLei = fourLei;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getMoneyMax() {
            return moneyMax;
        }

        public void setMoneyMax(double moneyMax) {
            this.moneyMax = moneyMax;
        }

        public double getMoneyMin() {
            return moneyMin;
        }

        public void setMoneyMin(double moneyMin) {
            this.moneyMin = moneyMin;
        }

        public double getNineLei() {
            return nineLei;
        }

        public void setNineLei(double nineLei) {
            this.nineLei = nineLei;
        }

        public double getOneLei() {
            return oneLei;
        }

        public void setOneLei(double oneLei) {
            this.oneLei = oneLei;
        }

        public int getRoomId() {
            return roomId;
        }

        public void setRoomId(int roomId) {
            this.roomId = roomId;
        }

        public double getSevenLei() {
            return sevenLei;
        }

        public void setSevenLei(double sevenLei) {
            this.sevenLei = sevenLei;
        }

        public double getSixLei() {
            return sixLei;
        }

        public void setSixLei(double sixLei) {
            this.sixLei = sixLei;
        }

        public double getThreeLei() {
            return threeLei;
        }

        public void setThreeLei(double threeLei) {
            this.threeLei = threeLei;
        }

        public double getTwoLei() {
            return twoLei;
        }

        public void setTwoLei(double twoLei) {
            this.twoLei = twoLei;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }

    private List<UserListBean> userList;

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    public static class UserListBean implements Serializable {
        /**
         * area :
         * createTime : 1.553484993E12
         * delFlag : 0
         * disRobLeiCount : 0
         * disRobLeiPercent : 0
         * disRobMoreFiveLeiPercent : 0
         * disRobMoreFourLeiPercent : 0
         * disRobMoreLeiPercent : 0
         * disRobMoreSixLeiPercent : 0
         * disRobMoreThreeLeiPercent : 0
         * headImg :
         * id : 5140
         * inviteCode :
         * leiPercent : 0
         * leiType : 0
         * nickName : 测试群主
         * oneLeiPercent : 0
         * openId :
         * password : AM0lRyKwuOk216ixtAVmhDuYRyNzGY2ecxjIyQzHJfAXsi1XSVvnoIteqL3KqIxi7nqkO49l2YvKsj2U+7GJXmA=
         * phone : 17681828908
         * realName : 测试群主
         * recommendUserId : 0
         * robotType : 0
         * salt : 5Yihzo0MnnQXw/kVrVx8ZoxzQXfEfOKMn+Z+3mrmhhI=
         * sex : 0
         * threeLeiPercent : 0
         * tokenId : afd7d68bf4f3e084aeb083342a986040
         * twoLeiPercent : 0
         * updateTime : 1.55348502E12
         * userType : 4
         */

        private String area;
        private double createTime;
        private int delFlag;
        private int disRobLeiCount;
        private int disRobLeiPercent;
        private int disRobMoreFiveLeiPercent;
        private int disRobMoreFourLeiPercent;
        private int disRobMoreLeiPercent;
        private int disRobMoreSixLeiPercent;
        private int disRobMoreThreeLeiPercent;
        private String headImg;
        private int id;
        private String inviteCode;
        private int leiPercent;
        private int leiType;
        private String nickName;
        private int oneLeiPercent;
        private String openId;
        private String password;
        private String phone;
        private String realName;
        private int recommendUserId;
        private int robotType;
        private String salt;
        private int sex;
        private int threeLeiPercent;
        private String tokenId;
        private int twoLeiPercent;
        private double updateTime;
        private int userType;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public double getCreateTime() {
            return createTime;
        }

        public void setCreateTime(double createTime) {
            this.createTime = createTime;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public int getDisRobLeiCount() {
            return disRobLeiCount;
        }

        public void setDisRobLeiCount(int disRobLeiCount) {
            this.disRobLeiCount = disRobLeiCount;
        }

        public int getDisRobLeiPercent() {
            return disRobLeiPercent;
        }

        public void setDisRobLeiPercent(int disRobLeiPercent) {
            this.disRobLeiPercent = disRobLeiPercent;
        }

        public int getDisRobMoreFiveLeiPercent() {
            return disRobMoreFiveLeiPercent;
        }

        public void setDisRobMoreFiveLeiPercent(int disRobMoreFiveLeiPercent) {
            this.disRobMoreFiveLeiPercent = disRobMoreFiveLeiPercent;
        }

        public int getDisRobMoreFourLeiPercent() {
            return disRobMoreFourLeiPercent;
        }

        public void setDisRobMoreFourLeiPercent(int disRobMoreFourLeiPercent) {
            this.disRobMoreFourLeiPercent = disRobMoreFourLeiPercent;
        }

        public int getDisRobMoreLeiPercent() {
            return disRobMoreLeiPercent;
        }

        public void setDisRobMoreLeiPercent(int disRobMoreLeiPercent) {
            this.disRobMoreLeiPercent = disRobMoreLeiPercent;
        }

        public int getDisRobMoreSixLeiPercent() {
            return disRobMoreSixLeiPercent;
        }

        public void setDisRobMoreSixLeiPercent(int disRobMoreSixLeiPercent) {
            this.disRobMoreSixLeiPercent = disRobMoreSixLeiPercent;
        }

        public int getDisRobMoreThreeLeiPercent() {
            return disRobMoreThreeLeiPercent;
        }

        public void setDisRobMoreThreeLeiPercent(int disRobMoreThreeLeiPercent) {
            this.disRobMoreThreeLeiPercent = disRobMoreThreeLeiPercent;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public int getLeiPercent() {
            return leiPercent;
        }

        public void setLeiPercent(int leiPercent) {
            this.leiPercent = leiPercent;
        }

        public int getLeiType() {
            return leiType;
        }

        public void setLeiType(int leiType) {
            this.leiType = leiType;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getOneLeiPercent() {
            return oneLeiPercent;
        }

        public void setOneLeiPercent(int oneLeiPercent) {
            this.oneLeiPercent = oneLeiPercent;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public int getRecommendUserId() {
            return recommendUserId;
        }

        public void setRecommendUserId(int recommendUserId) {
            this.recommendUserId = recommendUserId;
        }

        public int getRobotType() {
            return robotType;
        }

        public void setRobotType(int robotType) {
            this.robotType = robotType;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getThreeLeiPercent() {
            return threeLeiPercent;
        }

        public void setThreeLeiPercent(int threeLeiPercent) {
            this.threeLeiPercent = threeLeiPercent;
        }

        public String getTokenId() {
            return tokenId;
        }

        public void setTokenId(String tokenId) {
            this.tokenId = tokenId;
        }

        public int getTwoLeiPercent() {
            return twoLeiPercent;
        }

        public void setTwoLeiPercent(int twoLeiPercent) {
            this.twoLeiPercent = twoLeiPercent;
        }

        public double getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(double updateTime) {
            this.updateTime = updateTime;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }
    }

}
