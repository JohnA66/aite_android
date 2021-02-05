package com.haoniu.quchat.model;

import java.util.List;

/**
 * Created by Administrator on 2019/3/27.
 */

public class Test {

    /**
     * amount : 1
     * createTime : 1.553651076E12
     * delFlag : 0
     * expirationDate :
     * huanxinGroupId : 77859574054913
     * id : 141
     * isWelfare : 0
     * name : 微友测试群组
     * notice :
     * odds : 2
     * personal : 0
     * roomImg : /uploadFile/admin.png
     * roomLeiList : [{"amount":5,"createTime":1.553651076E12,"delFlag":0,"eightLei":0,"fiveLei":0,"fourLei":0,"id":23,"moneyMax":100,"moneyMin":10,"nineLei":0,"oneLei":2,"roomId":141,"sevenLei":0,"sixLei":0,"threeLei":0,"twoLei":0,"type":"1"}]
     * roomUserNum : 5
     * type : 2
     * userId : 5140
     * userList : [{"area":"","createTime":1.553484993E12,"delFlag":0,"disRobLeiCount":0,"disRobLeiPercent":0,"disRobMoreFiveLeiPercent":0,"disRobMoreFourLeiPercent":0,"disRobMoreLeiPercent":0,"disRobMoreSixLeiPercent":0,"disRobMoreThreeLeiPercent":0,"headImg":"","id":5140,"inviteCode":"","leiPercent":0,"leiType":0,"nickName":"测试群主","oneLeiPercent":0,"openId":"","password":"AM0lRyKwuOk216ixtAVmhDuYRyNzGY2ecxjIyQzHJfAXsi1XSVvnoIteqL3KqIxi7nqkO49l2YvKsj2U+7GJXmA=","phone":"17681828908","realName":"测试群主","recommendUserId":0,"robotType":0,"salt":"5Yihzo0MnnQXw/kVrVx8ZoxzQXfEfOKMn+Z+3mrmhhI=","sex":0,"threeLeiPercent":0,"tokenId":"afd7d68bf4f3e084aeb083342a986040","twoLeiPercent":0,"updateTime":1.55348502E12,"userType":4},{"area":"","createTime":1.553652364E12,"delFlag":0,"disRobLeiCount":0,"disRobLeiPercent":0,"disRobMoreFiveLeiPercent":0,"disRobMoreFourLeiPercent":0,"disRobMoreLeiPercent":0,"disRobMoreSixLeiPercent":0,"disRobMoreThreeLeiPercent":0,"headImg":"","id":5147,"inviteCode":"","leiPercent":0,"leiType":0,"nickName":"非群主测试用户","oneLeiPercent":0,"openId":"","password":"AM0lRyKwuOk216ixtAVmhDuYRyNzGY2ecxjIyQzHJfAXsi1XSVvnoIteqL3KqIxi7nqkO49l2YvKsj2U+7GJXmA=","phone":"17681828906","realName":"非群主测试用户","recommendUserId":0,"robotType":0,"salt":"5Yihzo0MnnQXw/kVrVx8ZoxzQXfEfOKMn+Z+3mrmhhI=","sex":0,"threeLeiPercent":0,"tokenId":"8d0c835e7fd6e54338e5f9befadecac9","twoLeiPercent":0,"userType":0},{"area":"","createTime":1.5536782E12,"delFlag":0,"disRobLeiCount":0,"disRobLeiPercent":0,"disRobMoreFiveLeiPercent":0,"disRobMoreFourLeiPercent":0,"disRobMoreLeiPercent":0,"disRobMoreSixLeiPercent":0,"disRobMoreThreeLeiPercent":0,"headImg":"","id":5150,"inviteCode":"0005150","leiPercent":0,"leiType":1,"nickName":"机器人1号","oneLeiPercent":0,"openId":"","password":"","phone":"123","realName":"","recommendUserId":0,"robotType":1,"salt":"","sex":0,"threeLeiPercent":0,"tokenId":"","twoLeiPercent":0,"updateTime":1.5536782E12,"userType":2},{"area":"","createTime":1.553678227E12,"delFlag":0,"disRobLeiCount":0,"disRobLeiPercent":0,"disRobMoreFiveLeiPercent":0,"disRobMoreFourLeiPercent":0,"disRobMoreLeiPercent":0,"disRobMoreSixLeiPercent":0,"disRobMoreThreeLeiPercent":0,"headImg":"","id":5151,"inviteCode":"0005151","leiPercent":0,"leiType":0,"nickName":"机器人2号","oneLeiPercent":0,"openId":"","password":"","phone":"456","realName":"","recommendUserId":0,"robotType":2,"salt":"","sex":0,"threeLeiPercent":0,"tokenId":"","twoLeiPercent":0,"updateTime":1.553678227E12,"userType":2},{"area":"","createTime":1.553678249E12,"delFlag":0,"disRobLeiCount":0,"disRobLeiPercent":0,"disRobMoreFiveLeiPercent":0,"disRobMoreFourLeiPercent":0,"disRobMoreLeiPercent":0,"disRobMoreSixLeiPercent":0,"disRobMoreThreeLeiPercent":0,"headImg":"","id":5152,"inviteCode":"0005152","leiPercent":0,"leiType":0,"nickName":"机器人3号","oneLeiPercent":0,"openId":"","password":"","phone":"789","realName":"","recommendUserId":0,"robotType":1,"salt":"","sex":0,"threeLeiPercent":0,"tokenId":"","twoLeiPercent":0,"updateTime":1.553678249E12,"userType":2}]
     * welfareType :
     */

    private int amount;
    private double createTime;
    private int delFlag;
    private String expirationDate;
    private String huanxinGroupId;
    private int id;
    private int isWelfare;
    private String name;
    private String notice;
    private int odds;
    private int personal;
    private String roomImg;
    private int roomUserNum;
    private int type;
    private int userId;
    private String welfareType;
    private List<RoomLeiListBean> roomLeiList;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
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

    public int getOdds() {
        return odds;
    }

    public void setOdds(int odds) {
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



    public static class RoomLeiListBean {
        /**
         * amount : 5
         * createTime : 1.553651076E12
         * delFlag : 0
         * eightLei : 0
         * fiveLei : 0
         * fourLei : 0
         * id : 23
         * moneyMax : 100
         * moneyMin : 10
         * nineLei : 0
         * oneLei : 2
         * roomId : 141
         * sevenLei : 0
         * sixLei : 0
         * threeLei : 0
         * twoLei : 0
         * type : 1
         */

        private int amount;
        private double createTime;
        private int delFlag;
        private int eightLei;
        private int fiveLei;
        private int fourLei;
        private int id;
        private int moneyMax;
        private int moneyMin;
        private int nineLei;
        private int oneLei;
        private int roomId;
        private int sevenLei;
        private int sixLei;
        private int threeLei;
        private int twoLei;
        private String type;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
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

        public int getEightLei() {
            return eightLei;
        }

        public void setEightLei(int eightLei) {
            this.eightLei = eightLei;
        }

        public int getFiveLei() {
            return fiveLei;
        }

        public void setFiveLei(int fiveLei) {
            this.fiveLei = fiveLei;
        }

        public int getFourLei() {
            return fourLei;
        }

        public void setFourLei(int fourLei) {
            this.fourLei = fourLei;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMoneyMax() {
            return moneyMax;
        }

        public void setMoneyMax(int moneyMax) {
            this.moneyMax = moneyMax;
        }

        public int getMoneyMin() {
            return moneyMin;
        }

        public void setMoneyMin(int moneyMin) {
            this.moneyMin = moneyMin;
        }

        public int getNineLei() {
            return nineLei;
        }

        public void setNineLei(int nineLei) {
            this.nineLei = nineLei;
        }

        public int getOneLei() {
            return oneLei;
        }

        public void setOneLei(int oneLei) {
            this.oneLei = oneLei;
        }

        public int getRoomId() {
            return roomId;
        }

        public void setRoomId(int roomId) {
            this.roomId = roomId;
        }

        public int getSevenLei() {
            return sevenLei;
        }

        public void setSevenLei(int sevenLei) {
            this.sevenLei = sevenLei;
        }

        public int getSixLei() {
            return sixLei;
        }

        public void setSixLei(int sixLei) {
            this.sixLei = sixLei;
        }

        public int getThreeLei() {
            return threeLei;
        }

        public void setThreeLei(int threeLei) {
            this.threeLei = threeLei;
        }

        public int getTwoLei() {
            return twoLei;
        }

        public void setTwoLei(int twoLei) {
            this.twoLei = twoLei;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
