package com.haoniu.quchat.entity;

import java.io.Serializable;

/**
 * 作   者：赵大帅
 * 描   述:
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/11/28 17:14
 * 更新日期: 2017/11/28
 */
public class PZInfo implements Serializable {

private double minWithdrawMoney;//提现最低额度
private String rechargeUrl;//充值地址
private String shareUrl;//分享地址
private String withdrawTime;//提现时间
private double withdrawHandRate;//提现手续费比例

    public double getMinWithdrawMoney() {
        return minWithdrawMoney;
    }

    public void setMinWithdrawMoney(double minWithdrawMoney) {
        this.minWithdrawMoney = minWithdrawMoney;
    }

    public String getRechargeUrl() {
        return rechargeUrl;
    }

    public void setRechargeUrl(String rechargeUrl) {
        this.rechargeUrl = rechargeUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getWithdrawTime() {
        return withdrawTime;
    }

    public void setWithdrawTime(String withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    public double getWithdrawHandRate() {
        return withdrawHandRate;
    }

    public void setWithdrawHandRate(double withdrawHandRate) {
        this.withdrawHandRate = withdrawHandRate;
    }
}
