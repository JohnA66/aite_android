package com.haoniu.quchat.utils;

import android.text.TextUtils;

import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.operate.UserOperateManager;
import com.hyphenate.chat.EMMessage;

import java.util.Map;

public class ProjectUtil {
    public static String transformId(String id) {
        if (id.contains(Constant.ID_REDPROJECT)){
            return id.split("-")[0];
        }else {
            return id;
        }
    }

    public static String getFriendOrigin(String type,String name) {
        if (TextUtils.isEmpty(type)) {
            return "";
        }
        switch (type) {
            case Constant.ADD_USER_ORIGIN_TYPE_QRCODE:
                return "通过扫一扫";
            case Constant. ADD_USER_ORIGIN_TYPE_SEARCH:
                return "通过手机号/艾特号搜索";
            case Constant.ADD_USER_ORIGIN_TYPE_GROUPCHAT:
                if (TextUtils.isEmpty(name))
                    return "";
                return "通过 "+name+"群组添加";
            case Constant.ADD_USER_ORIGIN_TYPE_ADDRESS_BOOK:
                return "通过通讯录列表添加";
            case Constant.ADD_USER_ORIGIN_TYPE_RECOMMEND:
                if (TextUtils.isEmpty(name))
                    return "";
                return "通过 "+name+" 用户的名片推荐";
            default:
                return "";
        }
    }

    public static String getWalletMessageTips(String msgType, String status) {
        switch (msgType) {
            case "1":
                return "充值成功";
            case "2":
                if ("1".equals(status)) {
                    return "提现成功";
                } else if ("0".equals(status)) {
                    return "发起提现";
                }else if ("0".equals(status)) {
                    return "提现失败";
                }
            case "3":
                return "红包退回";
            case "4":
                return "支付密码修改成功";

            default:
                return "";
        }
    }

    public static String rabPackageMessageTip(EMMessage message){
        Map map = message.ext();
        String robUserId;
        String robUserName;
        String sendRedPackageUserId;
        String sendRedPackageNickName;
        if (Constant.RAB.equals(map.get("msgType"))){
            sendRedPackageUserId = (String) map.get("sendid");
            sendRedPackageNickName = (String) map.get("sendnickname");
            if (UserComm.getUserId().equals(sendRedPackageUserId)) {
                return "你领取了自己的红包";
            }
            if (UserOperateManager.getInstance().hasUserName(sendRedPackageUserId))
                sendRedPackageNickName = UserOperateManager.getInstance().getUserName(sendRedPackageUserId);

            return "你领取了" + sendRedPackageNickName + "的红包";
        }else if (Constant.RAB_SELF.equals(map.get("msgType"))){
            robUserId = (String) map.get("sendid");
            robUserName = (String) map.get("sendnickname");
            if (UserOperateManager.getInstance().hasUserName(robUserId))
                robUserName = UserOperateManager.getInstance().getUserName(robUserId);
            return robUserName + "领取了你的红包" ;
        }
        return "";
    }
}
