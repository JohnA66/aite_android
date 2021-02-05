package com.haoniu.quchat.entity;

import java.util.List;

/**
 * @author lhb
 * 描   述: 客服列表实体类
 */
public class ServiceInfo {


    /**
     * pageNum : 1
     * pageSize : 15
     * size : 0
     * startRow : 0
     * endRow : 0
     * total : 1
     * pages : 1
     * list : null
     * prePage : 0
     * nextPage : 0
     * isFirstPage : false
     * isLastPage : false
     * hasPreviousPage : false
     * hasNextPage : false
     * navigatePages : 0
     * navigatepageNums : null
     * navigateFirstPage : 0
     * navigateLastPage : 0
     * draw : null
     * recordsTotal : 1
     * recordsFiltered : 1
     * data : [{"createTime":"2019-06-18 18:27:30","updateTime":"2019-07-04 14:57:00.000","userId":"ad17f83791b311e9828a7cd30a5a2048","phone":null,"imgCode":null,"random":null,"authCode":null,"password":null,"nickName":"15385136772","userHead":"20190704/4939697835e16d20e4e530a95801d316.jpg","userCode":"5201314","sign":"男的女的那就","newPassword":null,"realName":null,"idCard":null,"friendFlag":null}]
     * clazz : null
     * all : null
     * extendData : null
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

        private String userId;
        private String nickName;
        private String userHead;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserHead() {
            return userHead;
        }

        public void setUserHead(String userHead) {
            this.userHead = userHead;
        }

    }
}
