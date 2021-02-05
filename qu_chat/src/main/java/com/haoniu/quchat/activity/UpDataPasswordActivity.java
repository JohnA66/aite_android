package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
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
 * 描   述: 忘记密码
 * 日   期: 2017/11/13 15:10
 * 更新日期: 2017/11/13
 *
 * @author lhb
 */
public class UpDataPasswordActivity extends BaseActivity {


    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password2)
    EditText etPassword2;
    @BindView(R.id.tv_tosubmit)
    TextView tvTosubmit;
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_tuxing)
    EditText etTuxing;
    @BindView(R.id.img_code)
    ImageView imgCode;
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
        setContentView(R.layout.activity_up_password);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        setTitle("忘记密码");
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

    /**
     * 注册
     */
    public void register() {
        final String phone = etPhone.getText().toString();
        final String pwd = etPassword.getText().toString();
        final String pwd2 = etPassword2.getText().toString();
        String code = etCode.getText().toString();

        if (StringUtil.isEmpty(phone)) {
            ToastUtil.toast("手机号不能为空");
            etPhone.requestFocus();
            return;
        } else if (StringUtil.isEmpty(code)) {
            ToastUtil.toast("验证码不能为空");
            etCode.requestFocus();
            return;
        } else if (StringUtil.isEmpty(pwd)) {
            ToastUtil.toast("密码不能为空");
            etPassword.requestFocus();
            return;
        } else if (pwd.length() > 16 || pwd.length() < 6) {
            ToastUtil.toast("请输入6-16位密码");
            etPassword.requestFocus();
            return;
        } else if (StringUtil.isEmpty(pwd2) || !pwd.equals(pwd2)) {
            ToastUtil.toast("密码输入不一致");
            etPassword2.requestFocus();
            return;
        }

        final Map<String, Object> map = new HashMap<>();
        map.put("password", pwd);
        map.put("phone", phone);
        map.put("authCode", code);
        ApiClient.requestNetHandle(this, AppConfig.forgetpasswordUrl, "正在提交...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast("修改成功");
                finish();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
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
        } else if (StringUtil.isEmpty(etTuxing)) {
            ToastUtil.toast("图形验证码不能为空");
            etTuxing.requestFocus();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("type", "updatePwd");
        map.put("random", flag + "");
        map.put("imgCode", etTuxing.getText().toString());

        ApiClient.requestNetHandle(this, AppConfig.getForgetPhoneCodeUrl, "获取验证码...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (json != null) {
                    ToastUtil.toast("发送成功");
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

    @OnClick({R.id.img_code, R.id.iv_back, R.id.tv_code, R.id.tv_tosubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_code:
                flushTy();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_code:
                getCode();
                break;
            case R.id.tv_tosubmit:
                register();
                break;
            default:
                break;
        }
    }
}
