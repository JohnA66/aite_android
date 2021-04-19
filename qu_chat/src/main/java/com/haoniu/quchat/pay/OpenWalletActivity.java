package com.haoniu.quchat.pay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aite.chat.R;
import com.ehking.sdk.wepay.interfaces.WalletPay;
import com.ehking.sdk.wepay.net.bean.AuthType;
import com.haoniu.quchat.aop.SingleClick;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.LoginInfo;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.zds.base.json.FastJsonUtil;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class OpenWalletActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.et_real_name)
    EditText et_real_name;
    @BindView(R.id.et_id_card_no)
    EditText et_id_card_no;
    @BindView(R.id.et_phone_num)
    EditText et_phone_num;

    public static void start(Context context){
        Intent intent = new Intent(context, OpenWalletActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_open_wallet);
    }

    @Override
    protected void initLogic() {
        setTitle("开通钱包账户");
    }

    @SingleClick(1500)
    @OnClick({R.id.tv_open})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_open:
                doOpenClick();
                break;
        }
    }

    private void doOpenClick() {
        String realName = et_real_name.getText().toString().trim();
        if(TextUtils.isEmpty(realName)){
            toast("请输入真实姓名");
            return;
        }
        String idCardNo = et_id_card_no.getText().toString().trim();
        if(TextUtils.isEmpty(idCardNo)){
            toast("请输入身份证号");
            return;
        }
        String phoneNum = et_phone_num.getText().toString().trim();
        if(TextUtils.isEmpty(phoneNum)){
            toast("请输入手机号");
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("realName", realName);
        map.put("idCard", idCardNo);
        map.put("phone", phoneNum);
        ApiClient.requestNetHandle(this, AppConfig.openWalletAccount, "请稍等...",
                map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        if (json != null && json.length() > 0) {
                            /*groupDetailInfo = FastJsonUtil.getObject(json, GroupDetailInfo.class);
                            setGroupMemberData();
                            GroupOperateManager.getInstance().saveGroupMemberList(emChatId, groupDetailInfo, json);*/
                            OpenWalletAccountBean openWalletAccountBean = FastJsonUtil.getObject(json, OpenWalletAccountBean.class);

                            //开户成功后保存钱包id到用户信息中
                            LoginInfo loginInfo= UserComm.getUserInfo();
                            loginInfo.ncountUserId = openWalletAccountBean.walletId;
                            UserComm.saveUsersInfo(loginInfo);

                            //开户成功后执行自动安装证书业务
                            WalletPay.Companion.getInstance().destroy();
                            WalletPay walletPay = WalletPay.Companion.getInstance();
                            //walletPay.setEnvironment(Constants.environment)
                            walletPay.init(OpenWalletActivity.this);
                            walletPay.walletPayCallback = new WalletPay.WalletPayCallback() {
                                @Override
                                public void callback(@Nullable String source, @Nullable String status, @Nullable String errorMessage) {
                                    finish();
                                }
                            };

                            walletPay.evoke(openWalletAccountBean.merchantId, openWalletAccountBean.walletId,
                                    openWalletAccountBean.secretKey, AuthType.AUTO_CHECK_CER.name());

                        }else {
                            toast("服务器开小差，请稍后重试");
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                });
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}
