package com.haoniu.quchat.utils.payUtil;

import android.annotation.SuppressLint;
import android.content.Context;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zds.base.Toast.ToastUtil;



/**
 * 创建人 : skyCracks<br>
 * 创建时间 : 2016-7-18上午11:02:34<br>
 * 版本 : [v1.0]<br>
 * 类描述 : 微信支付实现服务端操作及后续调起支付<br>
 */
@SuppressLint("DefaultLocale")
public class WeChatPayService {

    private static final String TAG = WeChatPayService.class.getSimpleName();

    private IWXAPI wxApi;
    private Context context;
    /**
     * 订单类型
     */
    private int type;
    /**
     * 内部订单
     */
    private String out_trade_no;
    /**
     * 商品描述
     */
    private String body;
    /**
     * 商品金额费用, 单位是分
     */
    private String total_fee;

    /**
     * //     * @param context      上下文环境
     * //     * @param out_trade_no 内部订单
     * //     * @param type         订单类型(不同订单类型区分) 只有一种类型的订单时可去掉
     * //     * @param body         商品描述
     * //     * @param total_fee    商品金额费用, 单位是分
     */
    public WeChatPayService(Context context, WXPay pay) {
        this.context = context;
        this.wxPay = pay;
        this.wxApi = WXAPIFactory.createWXAPI(context, pay.getAppid(),
                false);
    }

    private WXPay wxPay;

    public void pay() {
        // 检测是否安装了微信
        boolean isWeChat = wxApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (isWeChat) {
//            new GetPrepayIdTask().execute();
            sendPayReq();
        } else {
            ToastUtil.toast("打开微信失败");
        }
    }

    /**
     * 发送支付请求
     */
    private void sendPayReq() {
//        wxApi = WXAPIFactory.createWXAPI(context, wxPay.getAppid(),
//                false);
        PayReq req = new PayReq();
        req.appId = wxPay.getAppid();
        req.partnerId = wxPay.getPartnerid();
        req.prepayId = wxPay.getPrepayid();
        req.nonceStr = wxPay.getNoncestr();
        req.timeStamp = wxPay.getTimestamp();
        req.packageValue = "Sign=WXPay";
        req.sign = wxPay.getSign();
        boolean b1 = wxApi.registerApp(wxPay.getAppid());
        boolean b = wxApi.sendReq(req);

    }
    /**
     * 发送支付请求
     * @param prepayId 预付Id
     */
//    private void sendPayReq(String prepayId) {
//        PayReq req = new PayReq();
//        req.appId = wxPay.getAppid();
//        req.partnerId = wxPay.getPartnerid();
//        req.prepayId = prepayId;
//        req.nonceStr = wxPay.getNoncestr();
//        req.timeStamp =wxPay.getTimestamp();
//        req.packageValue = wxPay.getPackageX();
////        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
////        signParams.add(new BasicNameValuePair("appid", req.appId));
////        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
////        signParams.add(new BasicNameValuePair("package", req.packageValue));
////        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
////        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
////        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
//        req.sign = pay.getSign();
//        // 传递的额外信息,字符串信息,自定义格式
//        // req.extData = type +"#" + out_trade_no + "#" +total_fee;
//        // 微信支付结果界面对调起支付Activity的处理
//        // APPCache.payActivity.put("调起支付的Activity",(调起支付的Activity)context);
//        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
//        boolean b1 = wxApi.registerApp(pay.getAppid());
//        boolean b = wxApi.sendReq(req);
//        L.d("TAG",b+"api.sendReq(req)"+b1);
//        // 支付完成后微信会回调 wxapi包下 WXPayEntryActivity 的public void onResp(BaseResp
//        // resp)方法，所以后续操作，放在这个回调函数中操作就可以了
//    }

}
