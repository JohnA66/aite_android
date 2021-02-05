package com.haoniu.quchat.paysdk;

/**
 * @author lhb
 * ewewe
 */

public enum TradeSession {
    INSTANCE;

    TradeSession() {
    }

    /**
     * custId 客户id
     */
    private static String custId;
    /**
     * accessToken为重要参数，不要泄露给其他应用
     */
    private static String accessToken;

    private static String prePayOrderNo;

    public static String getCustId() {
        return custId;
    }

    public static void setCustId(String custId) {
        TradeSession.custId = custId;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        TradeSession.accessToken = accessToken;
    }

    public static String getPrePayOrderNo() {
        return prePayOrderNo;
    }

    public static void setPrePayOrderNo(String prePayOrderNo) {
        TradeSession.prePayOrderNo = prePayOrderNo;
    }

    public static String testRedPacketNo;

    public static int lastRedPacketAmount;
    public static String lastTransferAmount;
}
