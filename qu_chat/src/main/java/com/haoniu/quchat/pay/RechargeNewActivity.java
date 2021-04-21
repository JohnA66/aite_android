package com.haoniu.quchat.pay;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aite.chat.R;
import com.ehking.sdk.wepay.interfaces.WalletPay;
import com.ehking.sdk.wepay.net.bean.AuthType;
import com.haoniu.quchat.activity.RechargeRecordActivity;
import com.haoniu.quchat.aop.SingleClick;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.wrapper.TextWatcherWrapper;
import com.zds.base.json.FastJsonUtil;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RechargeNewActivity extends BaseActivity {

    @BindView(R.id.toolbar_subtitle)
    TextView toolbar_subtitle;
    @BindView(R.id.et_amount)
    EditText et_amount;
    @BindView(R.id.tv_recharge)
    TextView tv_recharge;

    //结果返回最多重新查询次数
    private int maxCount = 5;
    private Handler handler = new Handler();

    public static void start(Context context){
        Intent intent = new Intent(context, RechargeNewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_recharge_new);
    }

    @Override
    protected void initLogic() {
        setTitle("零钱充值");
        toolbar_subtitle.setText("充值记录");
        toolbar_subtitle.setTextColor(ContextCompat.getColor(this, R.color.text_color_black));
        toolbar_subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RechargeRecordActivity.class);
            }
        });

        et_amount.addTextChangedListener(new TextWatcherWrapper() {
            @Override
            public void afterTextChanged(Editable editable) {
                String afterStr = editable.toString().trim();

                if (TextUtils.isEmpty(afterStr)) {
                    return;
                }

                if (!reviseEditTextInput(et_amount, 2)) {
                    return;
                }
            }
        });
    }

    @SingleClick(1500)
    @OnClick({R.id.tv_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_recharge:
                doRechargeClick();
                break;
        }
    }

    private void doRechargeClick() {
        maxCount = 5;
        String inputAmount = et_amount.getText().toString().trim();
        if (TextUtils.isEmpty(inputAmount)) {
            toast("请输入充值金额");
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("rechargeMoney", Double.parseDouble(inputAmount));
        map.put("cardId", "xxx");
        //map.put("payPassword", psw);
        map.put("payType", 1);
        ApiClient.requestNetHandle(this, AppConfig.rechargeUrl, "充值中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
//                startActivity(new Intent(RechargeActivity.this, WebViewActivity.class).putExtra("url", json).putExtra("title", "充值"));

                if (json != null && json.length() > 0) {
                    WalletRechargeBean walletRechargeBean = FastJsonUtil.getObject(json, WalletRechargeBean.class);

                    WalletPay walletPay = WalletPay.Companion.getInstance();
                    walletPay.init(RechargeNewActivity.this);
                    walletPay.walletPayCallback = new WalletPay.WalletPayCallback() {
                        @Override
                        public void callback(@Nullable String source, @Nullable String status, @Nullable String errorMessage) {
                            if(status == "SUCCESS" || status == "PROCESS"){
                                //queryResult(walletRechargeBean.requestId);
                                toast("充值成功");
                            }
                            finish();
                        }
                    };
                    //调起SDK的支付
                    /*ArrayList<String> list = new ArrayList<>();
                    list.add(AuthType.APP_PAY.name());
                    walletPay.setOnlySupportBalance(true,list);*/
                    walletPay.evoke(Constant.MERCHANT_ID, UserComm.getUserInfo().ncountUserId,
                            walletRechargeBean.token,AuthType.APP_PAY.name());

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

    private void doNewRechargeClick() {
        maxCount = 5;
        String inputAmount = et_amount.getText().toString().trim();
        if (TextUtils.isEmpty(inputAmount)) {
            toast("请输入充值金额");
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("rechargeMoney", Double.parseDouble(inputAmount));
        ApiClient.requestNetHandle(this, AppConfig.walletRecharge, "请稍等...",
                map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        if (json != null && json.length() > 0) {
                            WalletRechargeBean walletRechargeBean = FastJsonUtil.getObject(json, WalletRechargeBean.class);

                            WalletPay walletPay = WalletPay.Companion.getInstance();
                            walletPay.init(RechargeNewActivity.this);
                            walletPay.walletPayCallback = new WalletPay.WalletPayCallback() {
                                @Override
                                public void callback(@Nullable String source, @Nullable String status, @Nullable String errorMessage) {
                                    if(status == "SUCCESS" || status == "PROCESS"){
                                        queryResult(walletRechargeBean.requestId);
                                    }
                                }
                            };
                            //调起SDK的充值
                            walletPay.evoke(Constant.MERCHANT_ID, UserComm.getUserInfo().ncountUserId,
                                    walletRechargeBean.token,AuthType.RECHARGE.name());

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

    private void queryResult(String requestId) {
        Map<String, Object> map = new HashMap<>();
        map.put("requestId", requestId);
        ApiClient.requestNetHandleByGet(this, AppConfig.walletRechargeQuery, "请稍等...",
                map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        if (json != null && json.length() > 0) {
                            WalletRechargeQueryBean walletRechargeQueryBean = FastJsonUtil.getObject(json, WalletRechargeQueryBean.class);
                            switch (walletRechargeQueryBean.orderStatus){
                                case "SUCCESS":
                                    // TODO: 2021/3/22 关闭当前页面，并刷新钱包余额
                                    toast("充值成功");
                                    finish();
                                    break;
                                case "PROCESS":
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            maxCount--;
                                            if (maxCount<=0){
                                                toast("充值处理中");
                                                return;
                                            }
                                            queryResult(requestId);
                                        }
                                    },2000);
                                    break;
                                default:
                                    toast("充值失败");
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

    /**
     * 修正输入框输入内容
     *
     * @param editText
     * @param bitCount 小数点位数
     * @return 返回false表示不能继续输入
     */
    private boolean reviseEditTextInput(EditText editText, int bitCount) {
        boolean canContinueInput = true;

        String text = editText.getText().toString().trim();
        //删除“.”后面超过2位后的数据
        if (text.contains(".")) {
            if (text.length() - 1 - text.indexOf(".") > bitCount) {
                text = text.substring(0, text.indexOf(".") + bitCount + 1);
                editText.setText(text);
                editText.setSelection(text.length()); //光标移到最后
            }
        }
        //如果"."在起始位置,则起始位置自动补0，同时return false拦截后续的操作，
        // 否则Double.parseDouble(afterStr)会报数字格式异常
        if (text.substring(0, 1).equals(".")) {
            text = "0" + text;
            editText.setText(text);
            editText.setSelection(2);
            return false;//
        }
        //如果起始位置为0,且第二位跟的不是".",则无法后续输入
        if (text.startsWith("0") && text.length() > 1) {
            if (!text.substring(1, 2).equals(".")) {
                editText.setText(text.substring(0, 1));
                editText.setSelection(1);
                return false;
            }
        }

        return canContinueInput;
    }
}
