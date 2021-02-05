package com.haoniu.quchat.http;

/**
 * 网络请求回调接口
 **/
public abstract class ResultListener {
    /**
     * 请求成功
     *
     * @param json
     */
    public abstract void onSuccess(String json, String msg);
    /**
     * 请求失败
     */
    public abstract void onFailure(String msg);

    public void onFinsh() {

    }

}