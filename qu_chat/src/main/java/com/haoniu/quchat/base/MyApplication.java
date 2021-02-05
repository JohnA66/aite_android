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
package com.haoniu.quchat.base;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.aite.chat.BuildConfig;
import com.aite.chat.R;
import com.haoniu.quchat.activity.AuthenticationActivity;
import com.haoniu.quchat.activity.LoginActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.LoginInfo;
import com.haoniu.quchat.entity.VersionInfo;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.utils.CretinAutoUpdateUtils;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.widget.CommonDialog;
import com.hyphenate.chat.EMClient;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zds.base.SelfAppContext;
import com.zds.base.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class MyApplication extends SelfAppContext {

    public static Context applicationContext;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        applicationContext = this;
        instance = this;

//        KFTPaySDK.configApp(this, Common.MERCHANT_ID, Environment.DEVELOP);
        //Environment.DEVELOP 为开发环境

        MyHelper.getInstance().init(applicationContext);

        //初始化
        // EMClient.getInstance().init(applicationContext, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(BuildConfig.DEBUG);

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                ClassicsHeader header = new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Scale);
                header.setPrimaryColors(ContextCompat.getColor(context, R.color.white), ContextCompat.getColor(context, R.color.colorPrimary));
                return header;//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                layout.setEnableLoadmoreWhenContentNotFull(true);
                ClassicsFooter footer = new ClassicsFooter(context);
                footer.setBackgroundResource(android.R.color.white);
                footer.setSpinnerStyle(SpinnerStyle.Scale);//设置为拉伸模式
                return footer;//指定为经典Footer，默认是 BallPulseFooter
            }
        });
        registerWx();
        CretinAutoUpdateUtils.Builder builder = new CretinAutoUpdateUtils.Builder()
                //设置更新api
                .setBaseUrl(AppConfig.checkVersion)
                //设置是否显示忽略此版本
                .setIgnoreThisVersion(true)
                //设置下载显示形式 对话框或者通知栏显示 二选一
                .setShowType(CretinAutoUpdateUtils.Builder.TYPE_DIALOG_WITH_PROGRESS)
                //设置下载时展示的图标
                .setIconRes(R.mipmap.ic_launcher)
                //设置是否打印log日志
                .showLog(true)
                //设置请求方式
                .setRequestMethod(CretinAutoUpdateUtils.Builder.METHOD_GET)
                //设置下载时展示的应用名称
                .setAppName(getResources().getString(R.string.app_name))
                //设置自定义的Model类
                .setTransition(new VersionInfo())
                .build();
        CretinAutoUpdateUtils.init(builder);

        UserComm.init();
    }

    private IWXAPI mIWXAPI;

    public IWXAPI registerWx() {
        mIWXAPI = WXAPIFactory.createWXAPI(this, Constant.WXAPPID, true);
        mIWXAPI.registerApp(Constant.WXAPPID);
        return mIWXAPI;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    private CommonDialog commonDialog;

    /**
     * 清除缓存用户信息
     *
     * @param
     */
    public void cleanUserInfo() {
        UserComm.clearUserInfo();
    }


    /**
     * 更新用户信息
     */
    public void UpUserInfo() {
        Map<String, Object> map = new HashMap<>();
        ApiClient.requestNetHandle(this, AppConfig.USER_INFO, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (json != null) {
                    LoginInfo loginInfo = JSON.parseObject(json, LoginInfo.class);
                    loginInfo.setPassword(UserComm.getUserInfo().getPassword());
                    if (loginInfo != null) {
                        UserComm.saveUsersInfo(loginInfo);
                        EventBus.getDefault().post(new EventCenter(EventUtil.FLUSHUSERINFO));
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
            }
        });
    }


    /**
     * 用户存在是ture 否则是false
     *
     * @return
     */
    public boolean checkUser() {
        if (StringUtil.isEmpty(getUserLoginInfo().getTokenId())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 用户存在是ture 否则是false
     *
     * @return
     */
    public boolean checkUserToLogin(Context context) {
        if (StringUtil.isEmpty(getUserLoginInfo().getTokenId())) {
            Intent intent = new Intent(context, AuthenticationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return false;
        } else {
            return true;
        }
    }



    /**
     * 获取缓存用户登录信息
     *
     * @return
     */
    public LoginInfo getUserLoginInfo() {
        return UserComm.getUserInfo();
    }




    /**
     * 分享
     */
    public void shareDialog(Context context, final View.OnClickListener onClickListener) {
        //分享弹窗
        if (commonDialog != null && commonDialog.isShowing()) {
            commonDialog.dismiss();
        }
        commonDialog = new CommonDialog.Builder(context).setView(R.layout.share_dialog).fromBottom().fullWidth().loadAniamtion()
                .setOnClickListener(R.id.wx_chat, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickListener.onClick(view);
                        if (commonDialog != null && commonDialog.isShowing()) {
                            commonDialog.dismiss();
                        }
                    }
                })
                .setOnClickListener(R.id.wx_qun, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickListener.onClick(view);
                        if (commonDialog != null && commonDialog.isShowing()) {
                            commonDialog.dismiss();
                        }
                    }
                }).setOnClickListener(R.id.tv_cell, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (commonDialog != null && commonDialog.isShowing()) {
                            commonDialog.dismiss();
                        }
                    }
                }).create();
        commonDialog.show();
    }




    /**
     * 检测网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

}
