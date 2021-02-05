/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.base.MyHelper;
import com.haoniu.quchat.base.Storage;
import com.haoniu.quchat.constant.SP;
import com.haoniu.quchat.db.DemoDBManager;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.LoginInfo;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.utils.CretinAutoUpdateUtils;
import com.haoniu.quchat.utils.EaseCommonUtils;
import com.haoniu.quchat.utils.PreferenceManager;
import com.haoniu.quchat.utils.payUtil.WeChatConstans;
import com.haoniu.quchat.widget.EaseImageView;
import com.haoniu.quchat.wxapi.WeiXin;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zds.base.Toast.ToastUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作   者：赵大帅
 * 描   述: 登录
 * 日   期: 2017/11/13 11:46
 * 更新日期: 2017/11/13
 *
 * @author Administrator
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.tv_update_pwd)
    TextView mTvUpdatePwd;
    @BindView(R.id.img_logo)
    EaseImageView mImgLogo;
    private IWXAPI wxAPI;

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
        setContentView(R.layout.activity_login);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        wxAPI = WXAPIFactory.createWXAPI(this, WeChatConstans.APP_ID, true);
        wxAPI.registerApp(WeChatConstans.APP_ID);
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEtPwd.setText(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        String landedOnLoginPhone = (String) PreferenceManager.getInstance().getParam(SP.SP_LANDED_ON_LOGIN,"");
        if (!TextUtils.isEmpty(landedOnLoginPhone)) {
            mEtPhone.setText(landedOnLoginPhone);
        }
//        AppConfig.checkVersion(this, true);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CretinAutoUpdateUtils.getInstance(this).destroy();
    }

    /**
     * 用户登录
     */
    public void login() {
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        String currentUsername = mEtPhone.getText().toString().trim();
        final String currentPassword = mEtPwd.getText().toString().trim();

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
        String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
        Map<String, Object> map = new HashMap<>();
        map.put("phone", currentUsername);
        map.put("password", currentPassword);
        map.put("deviceId", ANDROID_ID);
        ApiClient.requestNetHandle(LoginActivity.this, AppConfig.toLoginUrl, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (json != null) {
                    LoginInfo loginInfo = JSON.parseObject(json, LoginInfo.class);
                    loginInfo.setPassword(currentPassword);
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
                        toast("登录失败" + message);
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.tv_update_pwd, R.id.tvLogin, R.id.tv_register,R.id.freeze,R.id.thaw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_update_pwd:
                startActivity(new Intent(LoginActivity.this, UpDataPasswordActivity.class));
                break;
            case R.id.tvLogin:
                login();
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.freeze:
                startActivity
                        (new Intent(LoginActivity.this, AccountManageActivity.class)
                                .putExtra(Constant.PARAM_STATUS,Constant.ACCOUNT_FREEZE)
                        );
                break;
            case R.id.thaw:
                startActivity
                        (new Intent(LoginActivity.this, AccountManageActivity.class)
                                .putExtra(Constant.PARAM_STATUS,Constant.ACCOUNT_THAW)
                        );
                break;
            default:
        }
    }


    /**
     * 这里用到的了EventBus框架
     *
     * @param weiXin
     */
    @Subscribe
    public void onEventMainThread(WeiXin weiXin) {
        //登录
      if (weiXin.getType() == 2) {
            //分享
            switch (weiXin.getErrCode()) {
                case BaseResp.ErrCode.ERR_OK:

                    break;
                //分享取消
                case BaseResp.ErrCode.ERR_USER_CANCEL:

                    break;
                //分享被拒绝
                case BaseResp.ErrCode.ERR_AUTH_DENIED:

                    break;
                default:
                    break;
            }
        } else if (weiXin.getType() == 3) {//微信支付
            //成功
            if (weiXin.getErrCode() == BaseResp.ErrCode.ERR_OK) {

            }
        }
    }
}
