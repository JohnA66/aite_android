package com.haoniu.quchat.utils.payUtil;

import android.app.Activity;
import android.content.Context;

import com.alipay.sdk.app.PayTask;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.utils.EventUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;


/**
 * 支付宝
 */

public class AliPayUtil {


    public static void AliPay(final Context context, final String orderInfo) {
//        ToastUtils.showTextToast(context, "暂未开通");
//        return;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) context);
                final Map<String, String> result = alipay.payV2(orderInfo, true);
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventCenter<Map<String, String>>(EventUtil.PAYSUCCESS, result));
                    }
                });

            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


}
