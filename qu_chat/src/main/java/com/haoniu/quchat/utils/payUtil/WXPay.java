package com.haoniu.quchat.utils.payUtil;


/**
 * Created by Administrator on 2017/3/4.
 */

public class WXPay {

    /**
     * appid : wx7071e89271c2a9a9
     * noncestr : wsNzm4b0hjMDYgmo
     * package : Sign=WXPay
     * partnerid : 1430501802
     * prepayid : wx2017030410282737f669b8f90577347835
     * sign : 305415134DAB298CD959CEAE020FF09E
     * timestamp : 1488594507
     */

    private String appid;
    private String noncestr;
    private String partnerid;
    private String prepayid;
    private String sign;
    private String timestamp;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
