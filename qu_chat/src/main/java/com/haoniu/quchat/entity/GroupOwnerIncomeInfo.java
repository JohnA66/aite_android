package com.haoniu.quchat.entity;

import java.util.List;

/**
 * Created by Administrator on 2019/3/29.
 */

public class GroupOwnerIncomeInfo {

    /**
     * red : [{"date":"2019-03-29","freeRed":80,"type":"1","ptRed":"0"},{"date":"2019-03-29","freeRed":9.56,"type":"0","ptRed":2.39},{"date":"2019-03-29","freeRed":28,"type":"1","ptRed":"0"},{"date":"2019-03-29","freeRed":2.76,"type":"0","ptRed":0.69}]
     * sumMoney : 336.32
     * tran : [{"date":"2019-03-27","money":12,"type":"0"},{"date":"2019-03-28","money":17,"type":"0"},{"date":"2019-03-28","money":2,"type":"1"},{"date":"2019-03-28","money":2,"type":"0"}]
     * todayMoney : 336.32
     */

    private double sumMoney;
    private double todayMoney;
    private double threeMoney;
    private double queryToDayMoney;
    private double queryYesterDayMoney;
    private double queryFrontDayMoney;

    private List<RedBean> red;
    private List<TranBean> tran;

    public double getThreeMoney() {
        return threeMoney;
    }

    public void setThreeMoney(double threeMoney) {
        this.threeMoney = threeMoney;
    }

    public double getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(double sumMoney) {
        this.sumMoney = sumMoney;
    }

    public double getTodayMoney() {
        return todayMoney;
    }

    public void setTodayMoney(double todayMoney) {
        this.todayMoney = todayMoney;
    }

    public double getQueryToDayMoney() {
        return queryToDayMoney;
    }

    public void setQueryToDayMoney(double queryToDayMoney) {
        this.queryToDayMoney = queryToDayMoney;
    }

    public double getQueryYesterDayMoney() {
        return queryYesterDayMoney;
    }

    public void setQueryYesterDayMoney(double queryYesterDayMoney) {
        this.queryYesterDayMoney = queryYesterDayMoney;
    }

    public double getQueryFrontDayMoney() {
        return queryFrontDayMoney;
    }

    public void setQueryFrontDayMoney(double queryFrontDayMoney) {
        this.queryFrontDayMoney = queryFrontDayMoney;
    }

    public List<RedBean> getRed() {
        return red;
    }

    public void setRed(List<RedBean> red) {
        this.red = red;
    }

    public List<TranBean> getTran() {
        return tran;
    }

    public void setTran(List<TranBean> tran) {
        this.tran = tran;
    }

    public static class RedBean {
        /**
         * date : 2019-03-29
         * freeRed : 80
         * type : 1
         * ptRed : 0
         */

        private String date;
        private double freeRed;
        private String type;
        private double ptRed;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public double getFreeRed() {
            return freeRed;
        }

        public void setFreeRed(double freeRed) {
            this.freeRed = freeRed;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getPtRed() {
            return ptRed;
        }

        public void setPtRed(double ptRed) {
            this.ptRed = ptRed;
        }
    }

    public static class TranBean {
        /**
         * date : 2019-03-27
         * money : 12
         * type : 0
         */

        private String date;
        private int money;
        private String type;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
