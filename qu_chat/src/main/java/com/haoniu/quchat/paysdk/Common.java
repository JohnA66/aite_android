package com.haoniu.quchat.paysdk;


import com.haoniu.quchat.global.UserComm;

/**
 * @author:lisc
 * @date:2019-07-01
 */

public final class Common {

    public static final String PAY_AMOUNT = "pay_amount";

    public static String MERCHANT_ID = "2019082000100350";
    public static String CUST_ID = UserComm.getUserInfo().getCustId();

    public static  String BASE_URL = "https://test.kftpay.com.cn/";
    public static String URL_PREFIX = "paspmock05";
    public static final class URL
    {
        public static String GET_ACCESS_TOKEN = "/testAccessToken";
        public static String GET_PREORDER_ID_FOR_PAY = "/testPreTrade";
        public static String GET_PREORDER_ID = "/testPreRegist";
        public static String CONFIRM_TRANSFER = "/testconfirmCollect";
        public static String RECEIVE_RED_PACKET = "/testBatchUnfreeze";
    }

    public static final class Status
    {
        public static final int SUCCESS = 1;
    }

    public static final class OrderType
    {
        public static final int RECHARGE  = 1;//充值
        public static final int TRANSFER = 2;//转账
        public static final int TRANSFER_CONFIRM = 3;//转账(需确认)
        public static final int WITHDRAW  = 4;//提现
        public static final int RED_PACKET  = 5;//发红包
    }

    public  static final class PrefKey
    {
        public static final String LAST_MERCHANT_ID = "last_merchant_id";
        public static final String LAST_CUST_ID = "last_cust_id";
        public static final String LAST_HOST_URL = "last_host_url";
        public static final String CUST_IDS = "cust_ids";
    }

}
