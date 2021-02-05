package com.haoniu.quchat.entity;

public class Custom1BaseInfo {

    private String code;
    private String msg;

    private boolean isOpen;

    public Custom1BaseInfo(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}