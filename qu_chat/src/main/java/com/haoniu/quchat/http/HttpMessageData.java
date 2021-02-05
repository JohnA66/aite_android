package com.haoniu.quchat.http;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/21.
 */

public class HttpMessageData implements Serializable {


    /**
     * result : true
     * message : 发送成功
     */

    private boolean result;
    private String message;

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "HttpMessageData{" +
                "result=" + result +
                ", message='" + message + '\'' +
                '}';
    }
}
