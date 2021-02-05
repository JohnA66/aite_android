package com.haoniu.quchat.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/3/28.
 */

public class NewRedRecordInfo implements Serializable {

    /**
     * endRow : 10
     * firstPage : 1
     * hasNextPage : true
     * hasPreviousPage : false
     * isFirstPage : true
     * isLastPage : false
     * lastPage : 5
     * list : [{"createTime":1.55375668E12,"delFlag":0,"description":"退回红包","id":986,"money":20.15,"redPacketId":305,"roomId":0,"type":3,"userId":5147},{"createTime":1.553756514E12,"delFlag":0,"description":"领取红包","id":983,"money":16.52,"redPacketId":305,"roomId":0,"type":2,"userId":5147},{"createTime":1.553756514E12,"delFlag":0,"description":"踩雷","id":984,"money":-150,"redPacketId":305,"roomId":0,"type":5,"userId":5147},{"createTime":1.553756514E12,"delFlag":0,"description":"收到雷包","id":985,"money":150,"redPacketId":305,"roomId":0,"type":4,"userId":5147},{"createTime":1.5537565E12,"delFlag":0,"description":"(扫雷)红包发布","id":981,"money":-75,"redPacketId":305,"roomId":0,"type":1,"userId":5147},{"createTime":1.553689442E12,"delFlag":0,"description":"退回红包","id":980,"money":4.51,"redPacketId":304,"roomId":0,"type":3,"userId":5147},{"createTime":1.553689266E12,"delFlag":0,"description":"领取红包","id":979,"money":6.83,"redPacketId":304,"roomId":0,"type":2,"userId":5147},{"createTime":1.553689262E12,"delFlag":0,"description":"(扫雷)红包发布","id":977,"money":-20,"redPacketId":304,"roomId":0,"type":1,"userId":5147},{"createTime":1.553685518E12,"delFlag":0,"description":"退回红包","id":915,"money":11.68,"redPacketId":275,"roomId":0,"type":3,"userId":5147},{"createTime":1.553685337E12,"delFlag":0,"description":"(扫雷)红包发布","id":878,"money":-20,"redPacketId":275,"roomId":0,"type":1,"userId":5147}]
     * navigateFirstPage : 1
     * navigateLastPage : 5
     * navigatePages : 8
     * navigatepageNums : [1,2,3,4,5]
     * nextPage : 2
     * orderBy : create_time desc
     * pageNum : 1
     * pageSize : 10
     * pages : 5
     * prePage : 0
     * size : 10
     * startRow : 1
     * total : 44
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
    private String orderBy;
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

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
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
         * createTime : 1.55375668E12
         * delFlag : 0
         * description : 退回红包
         * id : 986
         * money : 20.15
         * redPacketId : 305
         * roomId : 0
         * type : 3
         * userId : 5147
         */

        private double createTime;
        private int delFlag;
        private String description;
        private int id;
        private double money;
        private int redPacketId;
        private int roomId;
        private int type;
        private int userId;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public int getRedPacketId() {
            return redPacketId;
        }

        public void setRedPacketId(int redPacketId) {
            this.redPacketId = redPacketId;
        }

        public int getRoomId() {
            return roomId;
        }

        public void setRoomId(int roomId) {
            this.roomId = roomId;
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
