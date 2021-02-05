package com.haoniu.quchat.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class CollectInfo {

    /**
     * pageNum : 1
     * pageSize : 2
     * size : 0
     * startRow : 0
     * endRow : 0
     * total : 2
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
     * recordsTotal : 2
     * recordsFiltered : 2
     * data : [{"createTime":"2019-07-02 15:28:42.000","updateTime":"2019-07-02 15:28:42.000","collectId":"04e9200c9c9b11e9828a7cd30a5a2048","userId":"0948dc8e8f1811e999de00ffbd97fd96","linkType":"1","linkId":null,"linkContent":"22"},{"createTime":"2019-07-02 15:28:54.000","updateTime":"2019-07-02 15:28:54.000","collectId":"0bf40d409c9b11e9828a7cd30a5a2048","userId":"0948dc8e8f1811e999de00ffbd97fd96","linkType":"1","linkId":null,"linkContent":"22"}]
     * clazz : null
     * all : null
     * extendData : null
     * lastPage : 0
     * firstPage : 0
     */

    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private int total;
    private int pages;
    private Object list;
    private int prePage;
    private int nextPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int navigatePages;
    private Object navigatepageNums;
    private int navigateFirstPage;
    private int navigateLastPage;
    private Object draw;
    private int recordsTotal;
    private int recordsFiltered;
    private Object clazz;
    private Object all;
    private Object extendData;
    private int lastPage;
    private int firstPage;
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

    public Object getList() {
        return list;
    }

    public void setList(Object list) {
        this.list = list;
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

    public Object getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(Object navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
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

    public Object getDraw() {
        return draw;
    }

    public void setDraw(Object draw) {
        this.draw = draw;
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

    public Object getClazz() {
        return clazz;
    }

    public void setClazz(Object clazz) {
        this.clazz = clazz;
    }

    public Object getAll() {
        return all;
    }

    public void setAll(Object all) {
        this.all = all;
    }

    public Object getExtendData() {
        return extendData;
    }

    public void setExtendData(Object extendData) {
        this.extendData = extendData;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements MultiItemEntity {
        /**
         * createTime : 2019-07-02 15:28:42.000
         * updateTime : 2019-07-02 15:28:42.000
         * collectId : 04e9200c9c9b11e9828a7cd30a5a2048
         * userId : 0948dc8e8f1811e999de00ffbd97fd96
         * linkType : 1
         * linkId : null
         * linkContent : 22
         */

        private Long createTime;
        private String updateTime;
        private String collectId;
        private String userId;
        private String linkType;
        private Object linkId;
        private String linkContent;

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

        public String getCollectId() {
            return collectId;
        }

        public void setCollectId(String collectId) {
            this.collectId = collectId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getLinkType() {
            return linkType;
        }

        public void setLinkType(String linkType) {
            this.linkType = linkType;
        }

        public Object getLinkId() {
            return linkId;
        }

        public void setLinkId(Object linkId) {
            this.linkId = linkId;
        }

        public String getLinkContent() {
            return linkContent;
        }

        public void setLinkContent(String linkContent) {
            this.linkContent = linkContent;
        }

        @Override
        public int getItemType() {
            return Integer.valueOf(linkType);
        }
    }
}