package com.haoniu.quchat.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.haoniu.quchat.activity.MainActivity;
import com.haoniu.quchat.alipay.MyALipayUtils;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.utils.payUtil.WXPay;

import com.zds.base.json.FastJsonUtil;

import org.greenrobot.eventbus.EventBus;

public class JsMethod {
    private WebView mWebView;
    private Activity mContext;
    JsMethod.DataListener mDataListener;

    public JsMethod(WebView webView, Activity context) {
        mWebView = webView;
        mContext = context;
    }

    public void setDataListener(DataListener dataListener) {
        mDataListener = dataListener;
    }

    //返回
    @JavascriptInterface
    public void appGoBack() {
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    mContext.finish();
                }
            }
        });
    }

    //跳转到首页
    @JavascriptInterface
    public void appToIndex() {
        Bundle bundle = new Bundle();
        bundle.putString("from", "0");
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
        EventBus.getDefault().post(new EventCenter<>(0));
        mContext.finish();
    }

    //跳转到个人中心
    @JavascriptInterface
    public void appGoUserCenter() {
        Bundle bundle = new Bundle();
        bundle.putString("from", "3");
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtras(bundle);
        EventBus.getDefault().post(new EventCenter<>(3));
        mContext.startActivity(intent);
        mContext.finish();
    }

    //工人认证成功
    @JavascriptInterface
    public void authSuccess(String type) {
        if (type.equals("worker")) {
        } else if (type.equals("shop")) {
        }
    }

    //复制
    @JavascriptInterface
    public void appCopyText(String str) {
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(str);
    }


    //获取tooken
    @JavascriptInterface
    public String appGetUserToken() {
        return UserComm.getToken();
    }






    //微信支付
    @JavascriptInterface
    public void appDoWechatPayment(String order) {
        WXPay mInfo = FastJsonUtil.getObject(order.toString(), WXPay.class);
//        WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
//        builder.setAppId(mInfo.getAppid())
//                .setPartnerId(mInfo.getPartnerid())
//                .setPrepayId(mInfo.getPrepayid())
//                .setPackageValue(mInfo.getPackageX())
//                .setNonceStr(mInfo.getNoncestr())
//                .setTimeStamp(mInfo.getTimestamp())
//                .setSign(mInfo.getSign())
//                .build().toWXPayNotSign(mContext, mInfo.getAppid());
    }

    //打电话
    @JavascriptInterface
    public void appCallPhone(final String phone) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(mContext);
        normalDialog.setTitle("联系方式：");
        normalDialog.setMessage("电话：" + phone + "\n\n是否拨打电话？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + phone);
                        intent.setData(data);
                        mContext.startActivity(intent);

                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

    /**
     * 支付宝支付
     */
    @JavascriptInterface
    public void appDoAlipayPayment(String order) {
        MyALipayUtils.ALiPayBuilder builder = new MyALipayUtils.ALiPayBuilder();
        builder.build().toALiPay(mContext, order);
    }


    /**
     *
     */
    public interface DataListener {
        /**
         * uploadPhotoClick  回调打开相册到事件到另外一个页面处理（工人认证正反面）
         * openMap 打开地图Mul
         * uploadPhotoMul  回调打开相册到事件到另外一个页面处理（多传）
         */
        void uploadPhotoClick(boolean isFront, String method);

        void uploadPhotoMul(int num, int method);

        void openMap(int type);


    }

    /**
     * 多单图片（工人认证）
     */
    @JavascriptInterface
    public void uploadCardImage(boolean isFront) {
        mDataListener.uploadPhotoClick(isFront, "1");
    }

    //调取相册（多传）
    @JavascriptInterface
    public void uploadImage(String json) {
        int num = FastJsonUtil.getInt(json, "num");
        int type = FastJsonUtil.getInt(json, "type");

//        mDataListener.uploadPhotoClick(false, num);
        mDataListener.uploadPhotoMul(num, type);

    }

    /**
     * 调用原生地图
     */
    @JavascriptInterface
    public void appGoSelectAddress(int type) {
        mDataListener.openMap(type);
    }

}
