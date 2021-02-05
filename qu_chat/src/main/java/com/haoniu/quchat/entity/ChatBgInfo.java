package com.haoniu.quchat.entity;

public class ChatBgInfo {
    private int id;
    private int bg;

    private boolean isSel = false;

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public ChatBgInfo(int id, int bg, boolean isSel) {
        this.id = id;
        this.bg = bg;
        this.isSel = isSel;
    }
}
