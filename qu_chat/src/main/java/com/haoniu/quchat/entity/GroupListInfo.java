package com.haoniu.quchat.entity;

import java.util.List;

/**
 * Created by lhb on 2019/3/29.
 * 群主收益 群列表
 */

public class GroupListInfo {

    /**
     * endRow : 3
     * firstPage : 1
     * hasNextPage : false
     * hasPreviousPage : false
     * isFirstPage : true
     * isLastPage : true
     * lastPage : 1
     * list : [{"amount":7,"createTime":1.555400243E12,"delFlag":0,"expirationDate":"2019-05-18 05:00:00","groupNumber":"6712","huanxinGroupId":"79693708591105","id":167,"moneyMax":200,"moneyMin":10,"name":"沐风群","odds":1.3,"roomImg":"http://weiyou.ps1rww.cn:8083/user/room/7b46a4ec8023f801fc3e06b2fd0b8b14.jpg","type":2,"userId":5217},{"amount":7,"createTime":1.555475156E12,"delFlag":0,"expirationDate":"2019-04-19 00:00:00","groupNumber":"6429","huanxinGroupId":"79772259516417","id":168,"name":"qqweqweqw","odds":3,"roomImg":"http://weiyou.ps1rww.cn:8083/user/room/aeb0696482eac3455f84975c0b5264dc.jpg","type":2,"userId":5217},{"amount":6,"createTime":1.555475194E12,"delFlag":0,"expirationDate":"2019-04-19 00:00:00","groupNumber":"6995","huanxinGroupId":"79772300410881","id":169,"name":"沐沨群11","odds":5,"roomImg":"http://weiyou.ps1rww.cn:8083/user/room/fe7b6131e77c0ed33002e975d720c439.jpg","type":2,"userId":5217}]
     * navigateFirstPage : 1
     * navigateLastPage : 1
     * navigatePages : 8
     * navigatepageNums : [1]
     * nextPage : 0
     * pageNum : 1
     * pageSize : 10
     * pages : 1
     * prePage : 0
     * size : 3
     * startRow : 1
     * total : 3
     */

    private int endRow;
    private int firstPage;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private int lastPage;
    private int navigateFirstPage;
    private int navigateLastPage;
    private int navigatePages;
    private int nextPage;
    private int pageNum;
    private int pageSize;
    private int pages;
    private int prePage;
    private int size;
    private int startRow;
    private int total;
    private List<ListBean> list;
    private List<Integer> navigatepageNums;

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
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

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
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

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public static class ListBean {
        /**
         * amount : 7
         * createTime : 1.555400243E12
         * delFlag : 0
         * expirationDate : 2019-05-18 05:00:00
         * groupNumber : 6712
         * huanxinGroupId : 79693708591105
         * id : 167
         * moneyMax : 200
         * moneyMin : 10
         * name : 沐风群
         * odds : 1.3
         * roomImg : http://weiyou.ps1rww.cn:8083/user/room/7b46a4ec8023f801fc3e06b2fd0b8b14.jpg
         * type : 2
         * userId : 5217
         */

        private int amount;
        private double createTime;
        private int delFlag;
        private String expirationDate;
        private String groupNumber;
        private String huanxinGroupId;
        private int id;
        private int moneyMax;
        private int moneyMin;
        private String name;
        private double odds;
        private String roomImg;
        private int type;
        private int userId;

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

        public String getGroupNumber() {
            return groupNumber;
        }

        public void setGroupNumber(String groupNumber) {
            this.groupNumber = groupNumber;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getOdds() {
            return odds;
        }

        public void setOdds(double odds) {
            this.odds = odds;
        }

        public String getRoomImg() {
            return roomImg;
        }

        public void setRoomImg(String roomImg) {
            this.roomImg = roomImg;
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
    }
}
