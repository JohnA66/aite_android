//package com.haoniu.quchat.activity;
//
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.aite.chat.R;
//import com.haoniu.quchat.base.BaseActivity;
//import com.haoniu.quchat.base.MyApplication;
//import com.haoniu.quchat.entity.EventCenter;
//import com.haoniu.quchat.http.ApiClient;
//import com.haoniu.quchat.http.AppConfig;
//import com.haoniu.quchat.http.ResultListener;
//import com.haoniu.quchat.paysdk.Common;
//import com.haoniu.quchat.paysdk.TradeSession;
//import com.haoniu.quchat.utils.PhoneFormatUtil;
//import com.haoniu.quchat.widget.CommonDialog;
//import com.kft.pay.core.BaseReq;
//import com.kft.pay.core.BaseResp;
//import com.kft.pay.core.IKftApiCallback;
//import com.kft.pay.core.KFTPaySDK;
//import com.kft.pay.core.KFTPaySDKManager;
//import com.kft.pay.entity.req.PayParams;
//import com.kft.pay.entity.res.AccountInfo;
//import com.kft.pay.utils.PreferencesUtils;
//import com.zds.base.Toast.ToastUtil;
//import com.zds.base.log.XLog;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
///**
// * @author lhb
// * 支付管理
// */
//public class PayManageActivity extends BaseActivity {
//    @BindView(R.id.toolbar_title)
//    TextView toolbarTitle;
//    private KFTPaySDKManager router;
//    private BaseReq req;
//
//
//    @Override
//    protected void initContentView(Bundle bundle) {
//        setContentView(R.layout.activity_pay_manage);
//    }
//
//    @Override
//    protected void initLogic() {
//        toolbarTitle.setText("支付管理");
//        getAccessToken();
//    }
//
//    @Override
//    protected void onEventComing(EventCenter center) {
//
//    }
//
//    @Override
//    protected void getBundleExtras(Bundle extras) {
//
//    }
//
//    @OnClick({R.id.tv_auth_name, R.id.tv_upload_id, R.id.tv_bank_card, R.id.tv_set_password, R.id.tv_forget_password, R.id.tv_update_pwd})
//    public void onViewClicked(View view) {
//        router = KFTPaySDK.getKftPayManager();
//        if (TextUtils.isEmpty(TradeSession.getAccessToken())) {
//            toast("支付参数token值获取失败，请返回重试！");
//            return;
//        }
//        switch (view.getId()) {
//            case R.id.tv_auth_name:
//                //实名认证
//                if (UserComm.getUserInfo().getCustId() == null) {
//                    showAuthDialog();
//                }
//                break;
//            case R.id.tv_upload_id:
//                //上传身份证
//                if (UserComm.getUserInfo().getAuthStatus() != null && UserComm.getUserInfo().getAuthStatus().length() > 0) {
//                    toast("您已上传身份证");
//                    return;
//                }
//                req = new BaseReq(TradeSession.getAccessToken());
//                router.uploadIdCard(this, req);
//                break;
//            case R.id.tv_bank_card:
//                //银行卡
//                req = new BaseReq(TradeSession.getAccessToken());
//                router.manageBankCard(this, req);
//                break;
//            case R.id.tv_set_password:
//                //设置密码
//                //是否设置支付密码 0 没有 1 有
//                if (UserComm.getUserInfo().getPayPwdFlag() == 1) {
//                    toast("您已设置密码");
//                    return;
//                }
//                req = new BaseReq(TradeSession.getAccessToken());
//                router.setPassword(this, req);
//                break;
//            case R.id.tv_forget_password:
//                //忘记密码
//                req = new BaseReq(TradeSession.getAccessToken());
//                router.resetPassword(this, req);
//                break;
//            case R.id.tv_update_pwd:
//                //修改密码
//                BaseReq req = new BaseReq(TradeSession.getAccessToken());
//                router.modifyPassword(this, req);
//                break;
//            default:
//                break;
//        }
//    }
//
//
//    private CommonDialog.Builder builder;
//    private EditText etName, etCard, etPhone;
//
//    /**
//     * 实名认证弹窗
//     */
//    private void showAuthDialog() {
//        router = KFTPaySDK.getKftPayManager();
//        if (builder != null) {
//            builder.dismiss();
//        }
//        builder = new CommonDialog.Builder(this).fullWidth().center()
//                .setView(R.layout.dialog_custinfo);
//
//        builder.setOnClickListener(R.id.tv_sure, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //确定
//                if (etName.getText().toString().trim().length() <= 0) {
//                    toast("请填写姓名");
//                    return;
//                } else if (etCard.getText().toString().trim().length() <= 0) {
//                    toast("请填写身份证号码");
//                    return;
//                } else if (etPhone.getText().toString().trim().length() <= 0) {
//                    toast("请填写手机号");
//                    return;
//                } else if (!PhoneFormatUtil.isPhoneNumberValid(etPhone.getText().toString().trim())) {
//                    toast("请填写正确的手机号");
//                    return;
//                }
//                getCommonTrade(router, etName.getText().toString().trim(), etCard.getText().toString().trim(), etPhone.getText().toString().trim());
//                builder.dismiss();
//
//            }
//        });
//
//        CommonDialog dialog = builder.create();
//        etName = (EditText) dialog.getView(R.id.et_name);
//        etCard = (EditText) dialog.getView(R.id.et_card);
//        etPhone = (EditText) dialog.getView(R.id.et_phone);
//        dialog.show();
//    }
//
//
//    /**
//     * 获取快付通accessToken
//     */
//    private void getAccessToken() {
//        Map<String, Object> map = new HashMap<>();
//        ApiClient.requestNetHandle(this, AppConfig.accessTokenKft, "", map, new ResultListener() {
//            @Override
//            public void onSuccess(String json, String msg) {
//                XLog.d("json", json);
//                ToastUtil.toast(msg);
//                TradeSession.setAccessToken(json);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                ToastUtil.toast(msg);
//            }
//        });
//    }
//
//    /**
//     * 获取快付通预下单
//     */
//    private void getCommonTrade(KFTPaySDKManager router, String custName, String certNo, String phone) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("merchantId", Common.MERCHANT_ID);
//        params.put("orderType", "6");
//        params.put("body", "测试获取预支付订单");
//        params.put("tradeName", "充值测试");
//        params.put("orderNo", String.valueOf(System.currentTimeMillis()));
//        params.put("certificateType", "0");
//        if (!TextUtils.isEmpty(custName)) {
//            params.put("custName", custName);
//        }
//        if (!TextUtils.isEmpty(certNo)) {
//            params.put("certificateNo", certNo);
//        }
//        if (!TextUtils.isEmpty(phone)) {
//            params.put("mobile", phone);
//        }
//        KFTPaySDK.getKftPayManager();
//        ApiClient.requestNetHandle(this, AppConfig.accessCommonTradeKft, "", params, new ResultListener() {
//            @Override
//            public void onSuccess(String json, String msg) {
//                doNameAuth(router, json);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                ToastUtil.toast(msg);
//            }
//        });
//    }
//
//
//    /**
//     * 实名认证
//     *
//     * @param router     支付管理
//     * @param preOrderNo 订单号
//     */
//    private void doNameAuth(KFTPaySDKManager router, String preOrderNo) {
//        PayParams params = new PayParams();
//        params.prepayOrderNo = preOrderNo;
//
//        BaseReq req = new BaseReq(TradeSession.getAccessToken());
//        req.data = params;
//        router.createAccount(this, req, new IKftApiCallback<AccountInfo>() {
//            @Override
//            public void onResponse(BaseResp<AccountInfo> resp) {
//                AccountInfo info = resp.data;
//                if (resp.status == Common.Status.SUCCESS) {
//                    PreferencesUtils.putString(MyApplication.getInstance().getApplicationContext(), Common.PrefKey.LAST_CUST_ID, info.custId);
//                    Common.CUST_ID = info.custId;
//                    TradeSession.setCustId(info.custId);
//                    KFTPaySDK.setCustId(info.custId);
//                    startActivity(WalletActivity.class);
//                }
//            }
//        });
//    }
//
//}
