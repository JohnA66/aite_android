package com.haoniu.quchat.entity;

import java.util.List;

public class GroupUserAuditInfo {
    /**
     * pageNum : 1
     * pageSize : 10
     * size : 0
     * startRow : 0
     * endRow : 0
     * total : 4
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
     * recordsTotal : 4
     * recordsFiltered : 4
     * data : [{"createTime":"2019-08-26 12:21:52","updateTime":"2019-08-26 12:21:52.000","currenPage":1,"pageNum":null,"pageSize":10,"start":0,"length":null,"draw":null,"pageFlag":null,"excelDataMax":null,"totalFlag":null,"applyId":"07d5c543c7b911e9a9af00163e0f701a","userId":"2bd46322c00511e9a9af00163e0f701a","nickName":"tiguug","userHead":"http://47.105.129.90:8082/liaoquApi/profile/20190823/871156623a02c0654810e826ef2347b1.jpg","groupId":"10aef469c56d11e9a9af00163e0f701a","applyStatus":"0","groupIds":null},{"createTime":"2019-08-26 12:29:29","updateTime":"2019-08-26 12:29:29.000","currenPage":1,"pageNum":null,"pageSize":10,"start":0,"length":null,"draw":null,"pageFlag":null,"excelDataMax":null,"totalFlag":null,"applyId":"1813c2fec7ba11e9a9af00163e0f701a","userId":"2bd46322c00511e9a9af00163e0f701a","nickName":"tiguug","userHead":"http://47.105.129.90:8082/liaoquApi/profile/20190823/871156623a02c0654810e826ef2347b1.jpg","groupId":"70f5fe03c56d11e9a9af00163e0f701a","applyStatus":"0","groupIds":null},{"createTime":"2019-08-26 12:03:26","updateTime":"2019-08-26 12:03:26.000","currenPage":1,"pageNum":null,"pageSize":10,"start":0,"length":null,"draw":null,"pageFlag":null,"excelDataMax":null,"totalFlag":null,"applyId":"747947dac7b611e9a9af00163e0f701a","userId":"2bd46322c00511e9a9af00163e0f701a","nickName":"tiguug","userHead":"http://47.105.129.90:8082/liaoquApi/profile/20190823/871156623a02c0654810e826ef2347b1.jpg","groupId":"99bd58aec7b011e9a9af00163e0f701a","applyStatus":"0","groupIds":null},{"createTime":"2019-08-26 12:14:00","updateTime":"2019-08-26 12:14:00.000","currenPage":1,"pageNum":null,"pageSize":10,"start":0,"length":null,"draw":null,"pageFlag":null,"excelDataMax":null,"totalFlag":null,"applyId":"ee6d8475c7b711e9a9af00163e0f701a","userId":"2bd46322c00511e9a9af00163e0f701a","nickName":"tiguug","userHead":"http://47.105.129.90:8082/liaoquApi/profile/20190823/871156623a02c0654810e826ef2347b1.jpg","groupId":"788b086bc7ad11e9a9af00163e0f701a","applyStatus":"0","groupIds":null}]
     * clazz : null
     * all : null
     * extendData : null
     * lastPage : 0
     * firstPage : 0
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String createTime;
        private String updateTime;
        private int currenPage;
        private int pageSize;
        private int start;
        private String applyId;
        private String userId;
        private String nickName;
        private String userHead;
        private String groupId;
        private String applyStatus;
        private Object groupIds;
        private String groupName;
        private String isTop;
        private String topName;
        private String inviterName;
        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getTopName() {
            return topName;
        }

        public void setTopName(String topName) {
            this.topName = topName;
        }

        public String getInviterName() {
            return inviterName;
        }

        public void setInviterName(String inviterName) {
            this.inviterName = inviterName;
        }

        public String getIsTop() {
            return isTop;
        }

        public void setIsTop(String isTop) {
            this.isTop = isTop;
        }

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

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public String getApplyId() {
            return applyId;
        }

        public void setApplyId(String applyId) {
            this.applyId = applyId;
        }

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

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(String applyStatus) {
            this.applyStatus = applyStatus;
        }

        public Object getGroupIds() {
            return groupIds;
        }

        public void setGroupIds(Object groupIds) {
            this.groupIds = groupIds;
        }
    }
}
