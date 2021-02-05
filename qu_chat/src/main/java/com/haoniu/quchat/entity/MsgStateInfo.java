package com.haoniu.quchat.entity;

/**
 * @author：赵大帅 描   述: 消息
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/12/30 14:38
 * 更新日期: 2017/12/30
 */
public class MsgStateInfo {
    private String msg_is_read;
    private String msg_type;
    private String msg_id;

    public MsgStateInfo(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getMsg_is_read() {
        return msg_is_read;
    }

    public void setMsg_is_read(String msg_is_read) {
        this.msg_is_read = msg_is_read;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }
}
