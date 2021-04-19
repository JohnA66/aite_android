package com.haoniu.quchat.pay;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.alibaba.fastjson.JSON;
import com.ehking.sdk.wepay.interfaces.WalletPay;
import com.ehking.sdk.wepay.net.bean.AuthType;
import com.haoniu.quchat.activity.BankActivity;
import com.haoniu.quchat.activity.InputPasswordActivity;
import com.haoniu.quchat.activity.TxRecordActivity;
import com.haoniu.quchat.activity.VerifyingPayPasswordPhoneNumberActivity;
import com.haoniu.quchat.activity.WithdrawActivity;
import com.haoniu.quchat.aop.SingleClick;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.LoginInfo;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.widget.CommonDialog;
import com.haoniu.quchat.widget.CustomerKeyboard;
import com.haoniu.quchat.widget.PasswordEditText;
import com.zds.base.json.FastJsonUtil;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class WithdrawNewActivity extends BaseActivity {

    @BindView(R.id.tv_new_bank_card_submit)
    TextView mNewBankCard;
    @BindView(R.id.et_withdraw_money)
    EditText mWithdrawMoney;
    @BindView(R.id.tv_hand_rate)
    TextView mTvHand_rate;
    @BindView(R.id.tv_my_money_hint)
    TextView mTvMoneyHint;
    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubtitle;
    @BindView(R.id.view_bottom)
    View mViewBottom;
    @BindView(R.id.tv_tixian)
    TextView tv_tixian;

    private boolean isSelectBalance = false;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_withdraw_new);
    }

    @Override
    protected void initLogic() {
        mViewBottom.setVisibility(View.GONE);
        mToolbarSubtitle.setVisibility(View.VISIBLE);
        mToolbarSubtitle.setText("提现记录");
        mToolbarSubtitle.setTextColor(ContextCompat.getColor(this, R.color.text_color_black));
        mToolbarSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TxRecordActivity.class);
            }
        });
        setTitle("提现");
        initUI();
        MyApplication.getInstance().UpUserInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initUI();
    }

    private void initUI() {
        Map<String, Object> map = new HashMap<>();
        ApiClient.requestNetHandle(this, AppConfig.USER_INFO, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (json != null) {
                    LoginInfo loginInfo = JSON.parseObject(json, LoginInfo.class);
//                    mTvHand_rate.setText("提现金额（当前手续费率为" + loginInfo.getHandRate() + ")");
                    mTvHand_rate.setText("提现金额");
                    mTvMoneyHint.setText("我的余额：" + loginInfo.getMoney() + "元，最低100.00元起提");
                }
            }

            @Override
            public void onFailure(String msg) {
            }
        });

        Map<String, Object> map1 = new HashMap<>();
        ApiClient.requestNetHandle(this, AppConfig.getWithdrawExplain, "", map1, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (json != null) {
                    //{"withdrawExplain":"银行卡\r\n费率:0.6%+1元/笔银行付款费\r\n单日最多提现3次单笔最高10000元,单日最高30000元。每个账户只能同时进行一笔提现\r\n个别订单有可能被银行风控系统拦截,会延迟到账,我们会与相关机构沟通,在1-2个工作日内处理\r\n如您使用的银行卡多次出现打款失败,通常为卡片兼容问题,请更换银行卡后再试。\r\n注意:部分银行小额打款时不会发送短信通知,到账情况请以银行流水为准。"}
                    tv_tixian.setText(FastJsonUtil.getString(json,"withdrawExplain"));
                }
            }

            @Override
            public void onFailure(String msg) {
            }
        });

    }


    @OnClick({R.id.tv_new_bank_card_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_new_bank_card_submit:
                doWithdrawClick();
                break;
            default:
                break;
        }
    }

    private boolean isRequest;

    /**
     * 提现
     */
    private void doWithdrawClick() {
        try {
            double moey = Double.valueOf(mWithdrawMoney.getText().toString());
            if (moey <= 0) {
                toast("请输入金额");
                return;
            }

            if (moey > 10000) {
                toast("提现金额单次最高不得超过10000元");
                return;
            }

        } catch (Exception e) {
            e.getStackTrace();
            toast("请输入正确金额");
            return;
        }
        if (isRequest) {
            toast("加载中，请勿重复提交");
            return;
        }
        isRequest = true;
        Map<String, Object> map = new HashMap<>();
        map.put("withdrawMoney", mWithdrawMoney.getText().toString());
        map.put("proceMoney", 0);// TODO: 2021/3/22
        ApiClient.requestNetHandle(this, AppConfig.walletWithdraw, "正在提现...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (json != null && json.length() > 0) {
                    WalletWithdrawBean walletWithdrawBean = FastJsonUtil.getObject(json, WalletWithdrawBean.class);

                    WalletPay walletPay = WalletPay.Companion.getInstance();
                    walletPay.init(WithdrawNewActivity.this);
                    walletPay.walletPayCallback = new WalletPay.WalletPayCallback() {
                        @Override
                        public void callback(@Nullable String source, @Nullable String status, @Nullable String errorMessage) {
                            if(status == "SUCCESS" || status == "PROCESS"){
                                queryResult(walletWithdrawBean.requestId);
                            }
                        }
                    };
                    //调起sdk的提现
                    walletPay.evoke(Constant.MERCHANT_ID,UserComm.getUserInfo().ncountUserId,
                            walletWithdrawBean.token, AuthType.WITHHOLDING.name());
                }else {
                    toast("服务器开小差，请稍后重试");
                }
            }

            @Override
            public void onFinsh() {
                super.onFinsh();
                isRequest = false;
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }

    private void queryResult(String requestId) {
        Map<String, Object> map = new HashMap<>();
        map.put("requestId", requestId);
        ApiClient.requestNetHandleByGet(this, AppConfig.walletWithdrawQuery, "请稍等...",
                map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        if (json != null && json.length() > 0) {
                            WalletRechargeQueryBean walletRechargeQueryBean = FastJsonUtil.getObject(json, WalletRechargeQueryBean.class);
                            switch (walletRechargeQueryBean.orderStatus){
                                case "SUCCESS":
                                case "PROCESS":
                                    toast("提现申请成功，等待银行处理");
                                    startActivity(TxRecordActivity.class);
                                    break;
                                default:
                                    toast("提现失败");
                                    break;
                            }

                        }else {
                            toast("服务器开小差，请稍后重试");
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        toast(msg);
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
