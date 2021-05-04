package com.haoniu.quchat.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import com.aite.chat.R;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.haoniu.quchat.activity.MainActivity;
import com.haoniu.quchat.alipay.MyALipayUtils;
import com.haoniu.quchat.share.ShareInfo;
import com.haoniu.quchat.share.ShareUtil;
import com.haoniu.quchat.utils.payUtil.WXPay;
import com.haoniu.quchat.utils.payUtil.WeChatPayService;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.aes.AESCipher;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.SystemUtil;

/**
 * Created by zlw on 2018/08/06/上午 9:11
 * 与H5交互的Js方法基类
 */

public class BaseAndroidJs extends Object {

    public Activity mActivity;
    public WebView mWeb;

    public BaseAndroidJs(Activity mActivity, WebView mWeb) {
        this.mActivity = mActivity;
        this.mWeb = mWeb;
    }

    /**
     * 1.	js调用原生
     *  <> ①	页面跳转  </>
     */

    /**
     * 跳转到首页
     */
    @JavascriptInterface
    public void appGoHome() {
        mActivity.finish();
        mActivity.startActivity(new Intent(mActivity, MainActivity.class));
    }

    /**
     * 跳转到登录页面
     */
    @JavascriptInterface
    public void appLogin() {
        MyApplication.getInstance().cleanUserInfo();
        MyApplication.getInstance().checkUserToLogin(mActivity);
    }

    /**
     * 跳转到个人中心
     */
    @JavascriptInterface
    public void appGoUserCenterk() {
        mActivity.finish();
    }

    /**
     * 加密
     *
     * @param json
     */
    @JavascriptInterface
    public void appAESEncryption(final String json) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mWeb.evaluateJavascript("javascript:jsAESEncryption(" + AESCipher.encrypt(json) + ")", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            //此处为 js 返回的结果
                        }
                    });
                }
            }
        });

    }

    /**
     * 跳转到购物车
     */
    @JavascriptInterface
    public void appGoShoppingCar() {

    }

    /**
     * 返回上一页
     */
    @JavascriptInterface
    public void appGoBack() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mWeb.canGoBack()) {
                    mWeb.goBack();
                } else {
                    mActivity.finish();
                }
            }
        });
    }

    /**
     * 返回上一页
     */
    @JavascriptInterface
    public void appGoBackTO() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mActivity instanceof MainActivity) {

                        } else {
                            mActivity.finish();
                        }
                        //EventBus.getDefault().post(new EventCenter<Integer>(EventUtil.FLUSHBUTTON, 0));
                    }
                });
            }
        });
    }

    /**
     * <> ②	功能函数 </>
     */

    /**
     * 获取用户信息
     */
    @JavascriptInterface
    public void appGetUserToken() {

    }

    /**
     * 个人中心
     */
    @JavascriptInterface
    public void appReturnPersonal() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mActivity instanceof MainActivity) {

                } else {
                    mActivity.finish();
                }
               // EventBus.getDefault().post(new EventCenter<Integer>(EventUtil.FLUSHBUTTON, 4));
            }
        });
    }


    /**
     * 微信支付
     */
    @JavascriptInterface
    @SuppressLint("SetJavaScriptEnabled")
    public void appDoWechatPayment(String order) {
        payWX(order.toString());
    }

    private void payWX(String msg) {
        WXPay wxPay = FastJsonUtil.getObject(msg, WXPay.class);
        if (wxPay != null) {
            WeChatPayService weChatPayService = new WeChatPayService(mActivity, wxPay);
            weChatPayService.pay();
        } else {
            ToastUtil.toast("支付失败");
        }
    }


    /**
     * 支付宝支付
     */
    @JavascriptInterface
    public void appDoAlipayPayment(String order) {
        MyALipayUtils.ALiPayBuilder builder = new MyALipayUtils.ALiPayBuilder();
        builder.build().toALiPay(mActivity, order);
    }


    /**
     * 拨打电话
     */
    @JavascriptInterface
    public void appCallPhone(final String phone) {
        PermissionsUtil.requestPermission(mActivity.getApplication(), new PermissionListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void permissionGranted(@NonNull String[] permissions) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                mActivity.startActivity(intent);
            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {
                ToastUtil.toast("请打开手机拨号权限");
            }
        }, Manifest.permission.CALL_PHONE);
    }

    /**
     * 退出
     */
    @JavascriptInterface
    public void appLogout() {
        MyApplication.getInstance().cleanUserInfo();
        mActivity.finish();
    }

    /**
     * 复制文字到剪贴板
     */
    @JavascriptInterface
    public void appCopyText(String text) {

    }

    /**
     * 弹提示信息
     */
    @JavascriptInterface
    public void appShowMessage(String text) {
        ToastUtil.toast(text);
    }

    /**
     * 版本名
     */
    @JavascriptInterface
    public String appGetVersionName() {
        return "v" + SystemUtil.getAppVersionName();
    }

    /**
     * 版本号
     */
    @JavascriptInterface
    public String appGetVersionCode() {
        return SystemUtil.getAppVersionNumber() + "";
    }
    /**
     * 2.	原生调用js
     * <> 	(1)	常用函数  </>
     */

    /**
     * 支付成功
     *
     * @return
     */
    public static String jsPaySuccess() {
        return "javascript:jsPaySuccess()";
    }

    @JavascriptInterface
    public void appGetShare(String str) {
        ShareInfo shareInfo = FastJsonUtil.getObject(str, ShareInfo.class);
        if (shareInfo != null) {
            share(shareInfo);
        } else {
            ToastUtil.toast("数据异常");
        }
    }

    @JavascriptInterface
    public String jsAESEncryption(String str) {
        return AESCipher.encrypt(str);
    }


    /**
     * 分享
     */
    private void share(final ShareInfo shareInfo) {
        MyApplication.getInstance().shareDialog(mActivity, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    /**
                     * 微信好友
                     */
                    case R.id.wx_chat:
                        ShareUtil.shareUrl(mActivity, shareInfo.getUrl(), shareInfo.getTitle(), shareInfo.getPrice(), shareInfo.getThumb(), SendMessageToWX.Req.WXSceneSession);
                        break;
                    /**
                     * 微信朋友圈
                     */
                    case R.id.wx_qun:
                        ShareUtil.shareUrl(mActivity, shareInfo.getUrl(), shareInfo.getTitle(), shareInfo.getPrice(), shareInfo.getThumb(), SendMessageToWX.Req.WXSceneTimeline);
                        break;
                    default:
                }
            }
        });
    }

}
