package com.haoniu.quchat.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.haoniu.quchat.utils.payUtil.WeChatConstans;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zds.base.Toast.ToastUtil;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    /**
     * 支付回调
     */
    private static final int PAY_CALLBACK_REQUESTCODE = 101;
    /**
     * 支付类型
     */
    private String type;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 支付费用
     */
    private String fee;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, WeChatConstans.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        // 支付结果回调...
        if (req.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (req.getType() == 0) {//支付成功
                //   L.d("TAG", "RESP:" + "成功");
                ToastUtil.toast("支付成功!");
                //  EventBus.getDefault().post(new CommonEventBusEnity("wxpaysuccess", null));
                finish();
            } else {
                WXPayEntryActivity.this.finish();
                ToastUtil.toast("支付失败!");
            }
        } else {
            WXPayEntryActivity.this.finish();
            // L.d("TAG", "RESP:" + "失败111");
        }
    }

    @Override
    public void onResp(BaseResp resp) {
        // 支付结果回调...
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {//支付成功
                //L.d("TAG", "RESP:" + "成功");
                ToastUtil.toast("支付成功!");
                //   EventBus.getDefault().post(new CommonEventBusEnity("wxpaysuccess", null));
                finish();
            } else {
                WXPayEntryActivity.this.finish();
                ToastUtil.toast("支付失败!");
                // L.d("TAG", "RESP:" + "失败");
            }
        } else {
            WXPayEntryActivity.this.finish();
            // L.d("TAG", "RESP:" + "失败111");
        }
    }

}