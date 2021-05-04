package com.haoniu.quchat.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.activity.WebViewActivity;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.zds.base.ImageLoad.GlideUtils;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.util.StringUtil;
import com.zds.base.widget.SwitchButton;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterNewActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.et_register_phone)
    EditText registerPhone;
    @BindView(R.id.img_register_graph_code)
    ImageView ivGraphCode;
    @BindView(R.id.et_register_code)
    EditText registerCode;
    @BindView(R.id.tv_register_code)
    TextView registerGetCode;
    @BindView(R.id.et_register_graph)
    EditText registerGraph;
    @BindView(R.id.et_register_password)
    EditText registerPassword;
    @BindView(R.id.register_nick_name)
    EditText registerNickName;
    @BindView(R.id.sb_verify)
    SwitchButton sb_verify;
    @BindView(R.id.rg_sex)
    RadioGroup rg_sex;
    @BindView(R.id.rb_male)
    RadioButton rb_male;
    @BindView(R.id.rb_female)
    RadioButton rb_female;
    @BindView(R.id.checkbox)
    CheckBox agreeCheck;

    private CountDownTimer registerTimer;
    private int randomSign;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_register_new);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("注册");
        getGraphCode();
    }

    @OnClick({R.id.img_register_graph_code,R.id.tv_register_code,R.id.submit_register,
            R.id.tv_register_agreement,R.id.tv_user_agreement})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.img_register_graph_code:
                getGraphCode();
                break;
            case R.id.tv_register_code:
                initRegisterTimerCountTimer();
                getRegisterSMSCode();
                break;
            case R.id.submit_register:
                if (agreeCheck.isChecked()) {
                    register();
                } else {
                    toast("请先勾选注册协议!");
                }
                break;
            case R.id.tv_user_agreement:
                startActivity(new Intent(this, WebViewActivity.class).putExtra("title", "lan").putExtra("url", AppConfig.user_agree));
                break;
            case R.id.tv_register_agreement:
                startActivity(new Intent(this, WebViewActivity.class).putExtra("title", "lan").putExtra("url", AppConfig.register_agree));
                break;
        }
    }

    /**
     * 刷新图形验证码
     */
    private void getGraphCode() {
        getRandom();
        GlideUtils.loadImageViewLoding(AppConfig.tuxingCode + "?random=" + randomSign, ivGraphCode);
    }

    private void getRandom() {
        randomSign = new Random().nextInt(99999);
        if (randomSign < 10000) {
            randomSign += 10000;
        }
    }

    private void initRegisterTimerCountTimer() {
        if (registerTimer == null)
            registerTimer = new CountDownTimer(60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    registerGetCode.setText(millisUntilFinished / 1000 + "s后重新获取");
                    registerGetCode.setEnabled(false);
                    //   tvCode.setBackgroundResource(R.drawable.shap_gray_5);
                }

                @Override
                public void onFinish() {
                    registerGetCode.setText("获取验证码");
                    //   tvCode.setBackgroundResource(R.drawable.border_redgray5);
                    registerGetCode.setEnabled(true);
                }
            };
    }

    /**
     * 获取图形验证码
     */
    private void getRegisterSMSCode() {
        final String phone = registerPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.toast("手机号不能为空");
            registerPhone.requestFocus();
            return;
        } else if (StringUtil.isEmpty(registerGraph)) {
            ToastUtil.toast("图形验证码不能为空");
            registerGraph.requestFocus();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("imgCode", registerGraph.getText().toString());
        map.put("random", randomSign + "");
        ApiClient.requestNetHandle(this, AppConfig.getPhoneCodeUrl, "获取验证码", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
                registerTimer.start();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    /**
     * 注册
     */
    public void register() {
        final String phone = registerPhone.getText().toString();
        final String pwd = registerPassword.getText().toString();
        String code = registerCode.getText().toString();

        if (StringUtil.isEmpty(phone)) {
            ToastUtil.toast("手机号不能为空");
            registerPhone.requestFocus();
            return;
        } else if (StringUtil.isEmpty(code)) {
            ToastUtil.toast("验证码不能为空");
            registerCode.requestFocus();
            return;
        } else if (StringUtil.isEmpty(pwd)) {
            ToastUtil.toast("密码不能为空");
            registerPassword.requestFocus();
            return;
        } else if (pwd.length() > 16 || pwd.length() < 6) {
            ToastUtil.toast("请输入6-16位密码");
            registerPassword.requestFocus();
            return;
        } else if (StringUtil.isEmpty(registerNickName)) {
            ToastUtil.toast("昵称不能为空");
            registerNickName.requestFocus();
            return;
        }

        final Map<String, Object> map = new HashMap<>();
        map.put("password", pwd);
        map.put("phone", phone);
        map.put("authCode", code);
        map.put("nickName", registerNickName.getText().toString().trim());
        map.put("addWay", sb_verify.isChecked() ? 0 : 1);//0-需要 1-不需要 默认为1
        map.put("sex", rg_sex.getCheckedRadioButtonId() == R.id.rb_male ? 0 : 1);//0-男 1-女 默认为0

        ApiClient.requestNetHandle(this, AppConfig.toRegister, "正在注册...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast("注册成功，请登录");
                /*loginPhone.setText(phone);
                authenticationStatus = Constant.AUTHENTICATION_STATUS_LOGIN;
                switchoverAuthenticationPageStatus();*/

                registerPhone.setText("");
                registerGraph.setText("");
                registerCode.setText("");
                registerPassword.setText("");
                registerNickName.setText("");

                finish();

            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (registerTimer!= null)
            registerTimer.cancel();
    }
}
