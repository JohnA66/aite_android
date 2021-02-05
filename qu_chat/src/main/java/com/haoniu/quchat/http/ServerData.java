package com.haoniu.quchat.http;

import java.io.Serializable;

/**
 * 作   者：赵大帅
 * 描   述: 网络请求 基层数据封装
 * 日   期: 2017/11/13 17:50
 * 更新日期: 2017/11/13
 */
public class ServerData implements Serializable {
    private static final long serialVersionUID = 1L;
    private int code;//-1失败 0成功
    private Object data;//服务器数据
    private String message;//服务器给客户的消息

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
