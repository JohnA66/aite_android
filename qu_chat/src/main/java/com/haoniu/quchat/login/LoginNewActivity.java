package com.haoniu.quchat.login;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.chat.R;
import com.alibaba.fastjson.JSON;
import com.haoniu.quchat.activity.AccountManageActivity;
import com.haoniu.quchat.activity.AuthenticationActivity;
import com.haoniu.quchat.activity.MainActivity;
import com.haoniu.quchat.activity.UpDataPasswordActivity;
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
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.util.SystemUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginNewActivity extends BaseActivity {

    private String authenticationLoginStatus = Constant.AUTHENTICATION_LOGIN_PASSWORD;

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

    private CountDownTimer loginTimer;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_login_new);
    }

    @Override
    protected void initLogic() {
        String landedOnLoginPhone = (String) PreferenceManager.getInstance().getParam(SP.SP_LANDED_ON_LOGIN,"");
        if (!TextUtils.isEmpty(landedOnLoginPhone)) {
            loginPhone.setText(landedOnLoginPhone);
        }
    }

    @OnClick({R.id.tv_register_entry,R.id.login_get_code,R.id.submit_login,R.id.login_status,R.id.forget,R.id.freeze,R.id.thaw})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register_entry:
                startActivity(new Intent(LoginNewActivity.this, RegisterNewActivity.class));
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
                startActivity(new Intent(LoginNewActivity.this, UpDataPasswordActivity.class));
                break;
            case R.id.freeze:
                startActivity
                        (new Intent(LoginNewActivity.this, AccountManageActivity.class)
                                .putExtra(Constant.PARAM_STATUS,Constant.ACCOUNT_FREEZE)
                        );
                break;
            case R.id.thaw:
                startActivity
                        (new Intent(LoginNewActivity.this, AccountManageActivity.class)
                                .putExtra(Constant.PARAM_STATUS,Constant.ACCOUNT_THAW)
                        );
                break;
        }
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
        ApiClient.requestNetHandle(LoginNewActivity.this, AppConfig.multiLogin, "", map, new ResultListener() {
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

        ApiClient.requestNetHandle(LoginNewActivity.this, AppConfig.toSMSMultiLoginUrl, "", map, new ResultListener() {
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

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loginTimer!= null)
            loginTimer.cancel();
    }
}
