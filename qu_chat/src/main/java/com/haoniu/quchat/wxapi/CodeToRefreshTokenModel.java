package com.haoniu.quchat.wxapi;

/**
 * Created by lhb on 2019/1/23.
 * 通过code 获取 access_token  openid unionid等
 */

public class CodeToRefreshTokenModel {


    /**
     * access_token : 17_t4r04-vWokcWlJdxvpR_AAmwsxUz-Bw41bLsoOJqIeSvWo6Q9VAsE4efg8L2yaggfW1cqRXVsWIiwJA6EQAZuJ7b8_BxuCRfqgXaaAZly_w
     * expires_in : 7200
     * refresh_token : 17_APGPiKIofEaOfNXQMXxN2WOu4K62KVgS1EsxOgbxh1Mw7UdcayeiiZTcR07yIYGbzO8yWSGE3gTUWvRXE0z1QruoBmn9VaxJjydfEkznzOU
     * openid : oEApN1CqFna4cq5y2W9X7N7R9KZw
     * scope : snsapi_userinfo
     * unionid : okXON1cemVRM5VoiJWLe49wiX6qA
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
