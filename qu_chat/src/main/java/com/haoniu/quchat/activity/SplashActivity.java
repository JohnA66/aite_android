package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.aite.chat.R;
import com.gyf.immersionbar.ImmersionBar;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.MyHelper;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.login.LoginNewActivity;
import com.haoniu.quchat.view.CommonConfirmDialog;
import com.haoniu.quchat.view.UserProtocolDialog;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zds.base.global.BaseConstant;
import com.zds.base.util.Preference;

import butterknife.BindView;

/**
 * 作   者：赵大帅
 * 描   述: 启动页
 * 日   期: 2017/11/13 9:57
 * 更新日期: 2017/11/13
 *
 * @author Administrator
 */
public class SplashActivity extends BaseActivity {

    private static final int sleepTime = 1500;
    /*@BindView(R.id.img_logo)
    ImageView mImgLogo;
    @BindView(R.id.splash_root)
    RelativeLayout mSplashRoot;*/
    @BindView(R.id.view_status_bar_bg)
    View view_status_bar_bg;

    @Override
    protected void initContentView(Bundle bundle) {
//        setContentView(R.layout.activity_splash);
//        isTransparency(false);
//        initImmersionBar(true);
//        ImmersionBar.setStatusBarView(this,view_status_bar_bg);
        //ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        //setTheme(R.style.AppTheme);//还原回正常的Theme
        super.onCreate(arg0);
    }

    @Override
    protected void transparencyBar() {

    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        isTransparency(true);
        MyHelper.getInstance().initHandler(this.getMainLooper());
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

    long start;

    @Override
    protected void onStart() {
        super.onStart();
        //1.用户协议弹窗
        if(Preference.getBoolPreferences(SplashActivity.this, BaseConstant.SP.KEY_IS_AGREE_USER_PROTOCOL,false)){
            //已同意
            toApp();
        }else {
            //未同意
            showUserProtocolDialog();
        }

    }


    /**
     * 环信登录
     */
    private void HXlogin() {
        EMClient.getInstance().login("101-youxin", "123456", new EMCallBack() {
            @Override
            public void onSuccess() {
                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                // update current user's display name for APNs
//                if (mEdPhone.getText().toString().trim().equals("58000-youxin")) {
//                    EMClient.getInstance().pushManager().updatePushNickname("梁宏棒");
//                } else {
//                    EMClient.getInstance().pushManager().updatePushNickname("复制_梁宏棒");
//                }
                // get user's info (this should be get from App's server or 3rd party service)

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
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
                        toast("登录失败" + message);
                    }
                });
            }
        });
    }


    private void toApp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MyHelper.getInstance().isLoggedIn() && UserComm.IsOnLine()) {
                    start = System.currentTimeMillis();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    EMClient.getInstance().groupManager().loadAllGroups();
                    startActivity(MainActivity.class);
                    finish();
                } else {
                    handler.sendEmptyMessageDelayed(0, sleepTime);
                }
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            EMClient.getInstance().logout(true);
            startActivity(/*AuthenticationActivity.class*/LoginNewActivity.class);
            finish();
        }
    };


    private UserProtocolDialog mUserProtocolDialog;
    private void showUserProtocolDialog(){
        if(mUserProtocolDialog == null){
            mUserProtocolDialog = new UserProtocolDialog(this);

            mUserProtocolDialog.setOnAgreeClickListener(new UserProtocolDialog.OnAgreeClickListener() {
                @Override
                public void onAgreeClick() {
                    Preference.saveBoolPreferences(SplashActivity.this, BaseConstant.SP.KEY_IS_AGREE_USER_PROTOCOL,true);
                    toApp();
                }
            });

            mUserProtocolDialog.setOnNotAgreeClickListener(new UserProtocolDialog.OnNotAgreeClickListener() {
                @Override
                public void onNotAgreeClick() {
                    //弹出再次提醒弹窗
                    showCommonConfirmDialog();
                }
            });
        }
        mUserProtocolDialog.show();
    }

    private CommonConfirmDialog mCommonConfirmDialog;
    private void showCommonConfirmDialog() {
        if(mCommonConfirmDialog == null){
            mCommonConfirmDialog = new CommonConfirmDialog(this);

            mCommonConfirmDialog.setOnConfirmClickListener(new CommonConfirmDialog.OnConfirmClickListener() {
                @Override
                public void onConfirmClick(View view) {
                    showUserProtocolDialog();
                }
            });
            mCommonConfirmDialog.setOnCancelClickListener(new CommonConfirmDialog.OnCancelClickListener() {
                @Override
                public void onCancelClick(View view) {
                    //退出App
                    //MobclickAgent.onKillProcess(activity);
                    finish();
                }
            });
        }
        mCommonConfirmDialog.show();

        mCommonConfirmDialog.setCancelable(false);
        mCommonConfirmDialog.setCanceledOnTouchOutside(false);

        mCommonConfirmDialog.setTitle("您需要同意本隐私协议才能继续使用艾特");
        mCommonConfirmDialog.setContent("若您不同意本隐私协议，很遗憾我们将无法为您提供服务");
        mCommonConfirmDialog.setButtonText("仍不同意","查看协议");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void initImmersionBar(boolean isStatusBarDartFont) {
        //初始化，默认透明状态栏和黑色导航栏。
        ImmersionBar.with(this)
                .keyboardEnable(true)
                //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .statusBarDarkFont(isStatusBarDartFont, 0.2f)
                //.navigationBarColor("#E9E9E9")
                //采用系统默认导航栏颜色
                .navigationBarEnable(false)
                .init();//有时需要直接由子类实现该功能
    }
}
