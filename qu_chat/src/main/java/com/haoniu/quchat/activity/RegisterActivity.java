package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.zds.base.ImageLoad.GlideUtils;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.util.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作   者：赵大帅
 * 描   述: 注册、忘记密码
 * 日   期: 2017/11/13 15:10
 * 更新日期: 2017/11/13
 *
 * @author Administrator
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.tv_code)
    TextView mTvCode;
    @BindView(R.id.et_tuxing)
    EditText etTuxing;
    @BindView(R.id.img_code)
    ImageView imgCode;
    @BindView(R.id.checkbox)
    CheckBox cKXieYi;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.et_nick_name)
    EditText mEtNickName;
    private CountDownTimer timer;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 替代onCreate的使用
     *
     * @param bundle
     */
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_register);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        setTitle("注册");
        cKXieYi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mTvRegister.setBackgroundResource(R.drawable.login_bg_selector);
                } else {
                    mTvRegister.setBackgroundResource(R.drawable.login_bg_selector_nor);
                }
            }
        });
        countDown();
        flushTy();
    }

    /**
     * 刷新图形验证码
     */
    private void flushTy() {
        getRandom();
        GlideUtils.loadImageViewLoding(AppConfig.tuxingCode + "?random=" + flag, imgCode);
    }

    /**
     *
     */
    private void getRandom() {
        flag = new Random().nextInt(99999);
        if (flag < 10000) {
            flag += 10000;
        }
    }

    /**
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    @Override
    protected void getBundleExtras(Bundle extras) {
    }

    private void countDown() {
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvCode.setText(millisUntilFinished / 1000 + "s后重新获取");
                mTvCode.setEnabled(false);
            }

            @Override
            public void onFinish() {
                mTvCode.setText("获取验证码");
                mTvCode.setEnabled(true);
            }
        };
    }

    /**
     * 注册
     */
    public void register() {
        final String phone = mEtPhone.getText().toString();
        final String pwd = mEtPassword.getText().toString();
        String code = mEtCode.getText().toString();

        if (StringUtil.isEmpty(phone)) {
            ToastUtil.toast("手机号不能为空");
            mEtPhone.requestFocus();
            return;
        } else if (StringUtil.isEmpty(code)) {
            ToastUtil.toast("验证码不能为空");
            mEtCode.requestFocus();
            return;
        } else if (StringUtil.isEmpty(pwd)) {
            ToastUtil.toast("密码不能为空");
            mEtPassword.requestFocus();
            return;
        } else if (pwd.length() > 16 || pwd.length() < 6) {
            ToastUtil.toast("请输入6-16位密码");
            mEtPassword.requestFocus();
            return;
        } else if (StringUtil.isEmpty(mEtNickName)) {
            ToastUtil.toast("昵称不能为空");
            mEtNickName.requestFocus();
            return;
        }

        final Map<String, Object> map = new HashMap<>();
        map.put("password", pwd);
        map.put("phone", phone);
        map.put("authCode", code);
        map.put("nickName", mEtNickName.getText().toString().trim());

        ApiClient.requestNetHandle(this, AppConfig.toRegister, "正在注册...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast("注册成功");
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    /**
     * 获取图形验证码
     */
    private void getTXCode() {
        final String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.toast("手机号不能为空");
            mEtPhone.requestFocus();
            return;
        } else if (StringUtil.isEmpty(etTuxing)) {
            ToastUtil.toast("图形验证码不能为空");
            etTuxing.requestFocus();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
//        map.put("type", "register");
        map.put("random", flag + "");
        map.put("imgCode", etTuxing.getText().toString());
        ApiClient.requestNetHandle(this, AppConfig.getPhoneCodeUrl, "获取验证码...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (json != null) {
                    ToastUtil.toast(msg);
                    timer.start();
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    @OnClick({R.id.iv_back, R.id.img_code, R.id.tv_code, R.id.tv_register, R.id.tv_register_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register_agreement:
                //注册协议
                startActivity(new Intent(this, WebViewActivity.class).putExtra("title", "lan").putExtra("url", AppConfig.register_agree));
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.img_code:
                flushTy();
                break;
            case R.id.tv_code:
                getTXCode();
                break;
            case R.id.tv_register:
                if (cKXieYi.isChecked()) {
                    register();
                } else {
                    toast("请先勾选注册协议!");
                }
                break;
            default:
        }
    }
}
