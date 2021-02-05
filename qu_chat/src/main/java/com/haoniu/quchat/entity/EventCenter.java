package com.haoniu.quchat.entity;

/**
 * 作   者：赵大帅
 * 描   述: 消息公共类
 * 日   期: 2017/9/6 12:31
 * 更新日期: 2017/9/6
 */
public class EventCenter<T> {

    private int eventCode = -1;

    private T data;

    public EventCenter(int eventCode) {
        this.eventCode = eventCode;
    }

    public EventCenter(int eventCode, T data) {
        this.eventCode = eventCode;
        this.data = data;
    }

    public int getEventCode() {
        return eventCode;
    }

    public T getData() {
        return data;
    }
}
