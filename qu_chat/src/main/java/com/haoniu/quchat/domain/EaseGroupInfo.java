package com.haoniu.quchat.domain;

/**
 * 作   者：赵大帅
 * 描   述:
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/12/2 9:44
 * 更新日期: 2017/12/2
 */
public class EaseGroupInfo {

    private String username;
    private String groupName;
    private String head;
    private String type;
    private int groupType;
    private String sayFlag;
    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSayFlag() {
        return sayFlag;
    }

    public void setSayFlag(String sayFlag) {
        this.sayFlag = sayFlag;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public EaseGroupInfo(String username) {
        this.username = username;
    }

    public EaseGroupInfo() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
