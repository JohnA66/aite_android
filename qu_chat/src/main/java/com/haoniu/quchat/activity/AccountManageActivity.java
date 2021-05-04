package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.zds.base.Toast.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AccountManageActivity extends BaseActivity {
    public String operaterStatus = Constant.ACCOUNT_FREEZE;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    private CountDownTimer timer;
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_account_manage);
    }

    @Override
    protected void initLogic() {
        if (operaterStatus.equals(Constant.ACCOUNT_FREEZE)) {
            tvTitle.setText("冻结账户");
        }else {
            tvTitle.setText("解冻账户");
        }
        countDown();
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        operaterStatus = extras.getString(Constant.PARAM_STATUS);
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        final String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.toast("手机号不能为空");
            etPhone.requestFocus();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);

        String url = operaterStatus.equals(Constant.ACCOUNT_FREEZE)?AppConfig.getAccountFrozenSMSCode:AppConfig.getAccountThawSMSCode;
        ApiClient.requestNetHandle(this, url, "获取验证码", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast("发送成功");
                timer.start();

            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    private void operateAccount() {

        final String phone = etPhone.getText().toString().trim();
        final String smsCode = etCode.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.toast("手机号不能为空");
            etPhone.requestFocus();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("authCode", smsCode);

        String url = operaterStatus.equals(Constant.ACCOUNT_FREEZE)?AppConfig.frozenAccount:AppConfig.thawAccount;
        ApiClient.requestNetHandle(this, url, "请稍等", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (json != null) {
                    ToastUtil.toast(msg);
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }
    private void countDown() {
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCode.setText(millisUntilFinished / 1000 + "s后重新获取");
                tvCode.setEnabled(false);
                //   tvCode.setBackgroundResource(R.drawable.shap_gray_5);
            }

            @Override
            public void onFinish() {
                tvCode.setText("获取验证码");
                //   tvCode.setBackgroundResource(R.drawable.border_redgray5);
                tvCode.setEnabled(true);
            }
        };
    }
    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    @OnClick({ R.id.iv_back, R.id.tv_code, R.id.tv_tosubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_code:
                getCode();
                break;
            case R.id.tv_tosubmit:
                operateAccount();
                break;

            default:
                break;
        }
    }
}
