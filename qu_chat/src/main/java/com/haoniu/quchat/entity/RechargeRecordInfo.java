package com.haoniu.quchat.entity;

import java.util.List;

public class RechargeRecordInfo {
    /**
     * pageNum : 1
     * pageSize : 15
     * size : 0
     * startRow : 0
     * endRow : 0
     * total : 2
     * pages : 1
     * prePage : 0
     * nextPage : 0
     * isFirstPage : false
     * isLastPage : false
     * hasPreviousPage : false
     * hasNextPage : false
     * navigatePages : 0
     * navigateFirstPage : 0
     * navigateLastPage : 0
     * recordsTotal : 2
     * recordsFiltered : 2
     * data : [{"createTime":"2019-06-26 09:50:38.000","rechargeId":"45645616","userId":"1a5435ee8f2311e999de00ffbd97fd96","cardId":"46512123132","orderCode":"651516516516","rechargeMoney":32134,"rechargeStatus":"2"},{"createTime":"2019-07-13 15:19:31.000","updateTime":"2019-07-13 15:19:31.000","rechargeId":"8e79e839a53e11e9828a7cd30a5a2048","userId":"1a5435ee8f2311e999de00ffbd97fd96","cardId":"02ba1d88a45211e9828a7cd30a5a2048","rechargeMoney":100,"rechargeStatus":"0"}]
     * firstPage : 0
     * lastPage : 0
     */

    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private int total;
    private int pages;
    private int prePage;
    private int nextPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int navigatePages;
    private int navigateFirstPage;
    private int navigateLastPage;
    private int recordsTotal;
    private int recordsFiltered;
    private int firstPage;
    private int lastPage;
    private List<DataBean> data;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public void setNavigateFirstPage(int navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public int getNavigateLastPage() {
        return navigateLastPage;
    }

    public void setNavigateLastPage(int navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createTime : 2019-06-26 09:50:38.000
         * rechargeId : 45645616
         * userId : 1a5435ee8f2311e999de00ffbd97fd96
         * cardId : 46512123132
         * orderCode : 651516516516
         * rechargeMoney : 32134
         * rechargeStatus : 2
         * updateTime : 2019-07-13 15:19:31.000
         */

        private long createTime;
        private String rechargeId;
        private String userId;
        private String cardId;
        private String orderCode;
        private double rechargeMoney;
        private String rechargeStatus;
        private String updateTime;
        private double withdrawMoney;
        private String withdrawStatus;
        private String reason;
        private String withdrawId;

        public String getWithdrawId() {
            return withdrawId;
        }

        public void setWithdrawId(String withdrawId) {
            this.withdrawId = withdrawId;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getWithdrawStatus() {
            return withdrawStatus;
        }

        public void setWithdrawStatus(String withdrawStatus) {
            this.withdrawStatus = withdrawStatus;
        }

        public double getWithdrawMoney() {
            return withdrawMoney;
        }

        public void setWithdrawMoney(double withdrawMoney) {
            this.withdrawMoney = withdrawMoney;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getRechargeId() {
            return rechargeId;
        }

        public void setRechargeId(String rechargeId) {
            this.rechargeId = rechargeId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public double getRechargeMoney() {
            return rechargeMoney;
        }

        public void setRechargeMoney(double rechargeMoney) {
            this.rechargeMoney = rechargeMoney;
        }

        public String getRechargeStatus() {
            return rechargeStatus;
        }

        public void setRechargeStatus(String rechargeStatus) {
            this.rechargeStatus = rechargeStatus;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
