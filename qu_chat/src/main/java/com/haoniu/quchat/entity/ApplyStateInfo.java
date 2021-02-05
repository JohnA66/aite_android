package com.haoniu.quchat.entity;

/**
 * @author：赵大帅 描   述: 审核消息状态
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/12/30 14:38
 * 更新日期: 2017/12/30
 */
public class ApplyStateInfo {
    private String apply_is_read;
    private String apply_type;
    private String apply_id;

    public ApplyStateInfo(String apply_id) {
        this.apply_id = apply_id;
    }

    public String getApply_is_read() {
        return apply_is_read;
    }

    public void setApply_is_read(String apply_is_read) {
        this.apply_is_read = apply_is_read;
    }

    public String getApply_type() {
        return apply_type;
    }

    public void setApply_type(String apply_type) {
        this.apply_type = apply_type;
    }

    public String getApply_id() {
        return apply_id;
    }

    public void setApply_id(String apply_id) {
        this.apply_id = apply_id;
    }
}
