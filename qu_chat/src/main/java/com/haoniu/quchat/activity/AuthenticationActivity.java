package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.chat.R;
import com.alibaba.fastjson.JSON;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.constant.SP;
import com.haoniu.quchat.db.DemoDBManager;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.LoginInfo;
import com.haoniu.quchat.global.Global;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.utils.DeviceIdUtil;
import com.haoniu.quchat.utils.EaseCommonUtils;
import com.haoniu.quchat.utils.PreferenceManager;
import com.haoniu.quchat.view.AuthenticationTextView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zds.base.ImageLoad.GlideUtils;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.util.StringUtil;
import com.zds.base.util.SystemUtil;
import com.zds.base.widget.SwitchButton;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class AuthenticationActivity extends BaseActivity {
    //这两个状态用来判断要展示什么界面

    private String authenticationStatus = Constant.AUTHENTICATION_STATUS_LOGIN;
    private String authenticationLoginStatus = Constant.AUTHENTICATION_LOGIN_PASSWORD;

    @BindView(R.id.switch_login)
    AuthenticationTextView switchLogin;
    @BindView(R.id.switch_register)
    AuthenticationTextView switchRegister;


    //登录展示界面
    @BindView(R.id.ly_login)
    LinearLayout lyLogin;
    @BindView(R.id.login_phone)
    EditText loginPhone;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.ly_login_code)
    LinearLayout lyLoginCode;
    @BindView(R.id.login_code)
    EditText loginCode;
    @BindView(R.id.login_get_code)
    TextView loginGetCode;
    @BindView(R.id.login_status)
    TextView loginStatus;

    //注册展示界面
    @BindView(R.id.ly_register)
    LinearLayout lyRegister;
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


    private CountDownTimer loginTimer;
    private CountDownTimer registerTimer;

    private int randomSign;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_authentication);
    }

    @Override
    protected void initLogic() {
        switchLogin.setSelect(true);
        String landedOnLoginPhone = (String) PreferenceManager.getInstance().getParam(SP.SP_LANDED_ON_LOGIN,"");
        if (!TextUtils.isEmpty(landedOnLoginPhone)) {
            loginPhone.setText(landedOnLoginPhone);
        }
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    public void switchoverAuthenticationPageStatus() {
        if (authenticationStatus.equals(Constant.AUTHENTICATION_STATUS_LOGIN)){
            if (!lyLogin.isShown()) {
                lyLogin.setVisibility(View.VISIBLE);
                lyRegister.setVisibility(View.GONE);
                switchRegister.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                switchLogin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                switchLogin.setSelect(true);
                switchRegister.setSelect(false);
            }
        }else {
            if (!lyRegister.isShown()) {
                lyRegister.setVisibility(View.VISIBLE);
                lyLogin.setVisibility(View.GONE);
                switchRegister.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                switchLogin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                switchLogin.setSelect(false);
                switchRegister.setSelect(true);
            }
        }

    }

    public void switchoverLoginPageStatus(){
        if (authenticationLoginStatus.equals(Constant.AUTHENTICATION_LOGIN_PASSWORD)){
            authenticationLoginStatus = Constant.AUTHENTICATION_LOGIN_SMSCODE;
        }else {
            authenticationLoginStatus = Constant.AUTHENTICATION_LOGIN_PASSWORD;
        }

        if (authenticationLoginStatus.equals(Constant.AUTHENTICATION_LOGIN_PASSWORD)){
            loginPassword.setVisibility(View.VISIBLE);
            lyLoginCode.setVisibility(View.GONE);
            loginStatus.setText("验证码登录");

        }else {
            loginPassword.setVisibility(View.GONE);
            lyLoginCode.setVisibility(View.VISIBLE);
            loginStatus.setText("密码登录");
        }
    }

    private void initLoginCountTimer() {
        if (loginTimer == null)
            loginTimer = new CountDownTimer(60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    loginGetCode.setText(millisUntilFinished / 1000 + "s后重新获取");
                    loginGetCode.setEnabled(false);
                    //   tvCode.setBackgroundResource(R.drawable.shap_gray_5);
                }

                @Override
                public void onFinish() {
                    loginGetCode.setText("获取验证码");
                    //   tvCode.setBackgroundResource(R.drawable.border_redgray5);
                    loginGetCode.setEnabled(true);
                }
            };
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
     * =========================================用户密码登录=================================================
     */
    public void passwordLogin() {
//        startActivity(MainActivity.class);
//        if (true) return;
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        String currentUsername = loginPhone.getText().toString().trim();
        String currentPassword = loginPassword.getText().toString().trim();

        if (TextUtils.isEmpty(currentUsername)) {
            ToastUtil.toast(getString(R.string.User_name_cannot_be_empty));
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            ToastUtil.toast(getString(R.string.Password_cannot_be_empty));
            Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        showLoading(getString(R.string.Is_landing));
        DemoDBManager.getInstance().closeDB();
        Map<String, Object> map = new HashMap<>();
        map.put("phone", currentUsername);
        map.put("password", currentPassword);
        map.put("deviceId", DeviceIdUtil.getDeviceId(this));
        map.put("os", "Android");
        map.put("version", Global.loginVersion);
        map.put("deviceName", SystemUtil.getDeviceManufacturer() + " " + SystemUtil.getSystemModel());
        ApiClient.requestNetHandle(AuthenticationActivity.this, AppConfig.multiLogin, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (json != null) {
                    LoginInfo loginInfo = JSON.parseObject(json, LoginInfo.class);
                    loginInfo.setPassword(currentPassword);
                    PreferenceManager.getInstance().setParam(SP.SP_LANDED_ON_LOGIN,currentUsername);

                    if (loginInfo != null) {
                        UserComm.saveUsersInfo(loginInfo);
//                        startActivity(MainActivity.class);
                        HXlogin();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                dismissLoading();
                ToastUtil.toast(msg);
            }
        });
    }



    /**
     * * =========================================用户验证码登录=================================================
     */

    private void getLoginSMSCode() {
        final String phone = loginPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.toast("手机号不能为空");
            loginPhone.requestFocus();
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);

        ApiClient.requestNetHandle(this, AppConfig.getSMSCodeForLogin, "获取验证码", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast("发送成功");
                loginTimer.start();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    public void smsCodeLogin() {
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        String currentUsername = loginPhone.getText().toString().trim();
        String loginCodeStr = loginCode.getText().toString().trim();

        if (TextUtils.isEmpty(currentUsername)) {
            ToastUtil.toast(getString(R.string.User_name_cannot_be_empty));
            return;
        }
        if (TextUtils.isEmpty(loginCodeStr)) {
            Toast.makeText(this, R.string.smscode_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        showLoading(getString(R.string.Is_landing));
        DemoDBManager.getInstance().closeDB();
        Map<String, Object> map = new HashMap<>();
        map.put("phone", currentUsername);
        map.put("authCode", loginCodeStr);
        map.put("deviceId", DeviceIdUtil.getDeviceId(this));
        map.put("os", "Android");
        map.put("version", Global.loginVersion);
        map.put("deviceName", SystemUtil.getDeviceManufacturer() + " " + SystemUtil.getSystemModel());

        ApiClient.requestNetHandle(AuthenticationActivity.this, AppConfig.toSMSMultiLoginUrl, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (json != null) {
                    LoginInfo loginInfo = JSON.parseObject(json, LoginInfo.class);

                    PreferenceManager.getInstance().setParam(SP.SP_LANDED_ON_LOGIN,currentUsername);

                    if (loginInfo != null) {
                        UserComm.saveUsersInfo(loginInfo);
                        HXlogin();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                dismissLoading();
                ToastUtil.toast(msg);
            }
        });
    }

    /**
     * 环信登录
     */
    private void HXlogin() {
        final LoginInfo loginInfo = UserComm.getUserInfo();

        EMClient.getInstance().login(loginInfo.getIdh() + "", "123456", new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                // update current user's display name for APNs
                EMClient.getInstance().pushManager().updatePushNickname(loginInfo.getNickName());
                // get user's info (this should be get from App's server or 3rd party service)

                startActivity(MainActivity.class);
                dismissLoading();
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(final int code, final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (code == 200) {
                            EMClient.getInstance().logout(false);
                        }
                        dismissLoading();
                        toast("登录失败 code=" + code + " " + message);
                    }
                });
            }
        });
    }


    /**
     * ===============================注册=======================================
     */

    private void getRandom() {
        randomSign = new Random().nextInt(99999);
        if (randomSign < 10000) {
            randomSign += 10000;
        }
    }

    /**
     * 刷新图形验证码
     */
    private void getGraphCode() {
        getRandom();
        GlideUtils.loadImageViewLoding(AppConfig.tuxingCode + "?random=" + randomSign, ivGraphCode);
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
                loginPhone.setText(phone);
                authenticationStatus = Constant.AUTHENTICATION_STATUS_LOGIN;
                switchoverAuthenticationPageStatus();

                registerPhone.setText("");
                registerGraph.setText("");
                registerCode.setText("");
                registerPassword.setText("");
                registerNickName.setText("");

            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    @OnClick({R.id.switch_login,R.id.img_register_graph_code,R.id.switch_register,R.id.login_get_code,
            R.id.submit_login,R.id.login_status,R.id.forget,R.id.freeze,R.id.thaw,R.id.submit_register,
            R.id.tv_register_agreement,R.id.tv_user_agreement,R.id.tv_register_code})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.switch_login:
                authenticationStatus = Constant.AUTHENTICATION_STATUS_LOGIN;
                switchoverAuthenticationPageStatus();
                break;
            case R.id.switch_register:
                authenticationStatus = Constant.AUTHENTICATION_STATUS_REGISTER;
                getGraphCode();
                switchoverAuthenticationPageStatus();
                break;
            case R.id.login_get_code:
                initLoginCountTimer();
                getLoginSMSCode();
                break;
            case R.id.submit_login:
                if (authenticationLoginStatus.equals(Constant.AUTHENTICATION_LOGIN_PASSWORD)) {
                    passwordLogin();
                }else {
                    smsCodeLogin();
                }
                break;
            case R.id.login_status:
                switchoverLoginPageStatus();
                break;
            case R.id.forget:
                startActivity(new Intent(AuthenticationActivity.this, UpDataPasswordActivity.class));
                break;
            case R.id.freeze:
                startActivity
                        (new Intent(AuthenticationActivity.this, AccountManageActivity.class)
                                .putExtra(Constant.PARAM_STATUS,Constant.ACCOUNT_FREEZE)
                        );
                break;
            case R.id.thaw:
                startActivity
                        (new Intent(AuthenticationActivity.this, AccountManageActivity.class)
                                .putExtra(Constant.PARAM_STATUS,Constant.ACCOUNT_THAW)
                        );
                break;
            case R.id.img_register_graph_code:
                getGraphCode();
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
            case R.id.tv_register_code:
                initRegisterTimerCountTimer();
                getRegisterSMSCode();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loginTimer!= null)
            loginTimer.cancel();
        if (registerTimer!= null)
            registerTimer.cancel();
    }
}
