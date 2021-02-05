package com.haoniu.quchat.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;



    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, WeChatConstans.APP_ID, false);
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
        WXEntryActivity.this.finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    SendAuth.Resp authResp = (SendAuth.Resp) resp;
                    WeiXin weiXin = new WeiXin(1, resp.errCode, authResp.code);
                    EventBus.getDefault().post(weiXin);
                    //获取用户信息
//                getAccessToken(code);
                    finish();
                    break;
                //用户拒绝授权
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    finish();
                    break;
                //用户取消
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    finish();
                    break;
                default:
                    finish();
                    break;
            }

        }

    }
}