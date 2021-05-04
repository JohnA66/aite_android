package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.MyHelper;
import com.haoniu.quchat.base.MyModel;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.utils.CretinAutoUpdateUtils;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.widget.EaseAlertDialog;
import com.hyphenate.EMCallBack;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.util.DataCleanManager;
import com.zds.base.util.StringUtil;
import com.zds.base.util.SystemUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 设置
 *
 * @author Administrator
 */

public class SetActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_anquan)
    LinearLayout llAnquan;
    @BindView(R.id.tv_banben)
    TextView tvBanben;
    @BindView(R.id.ll_banben)
    LinearLayout llBanben;
    @BindView(R.id.switch_btn)
    Switch switchBtn;
    @BindView(R.id.ll_notices)
    LinearLayout llNotices;
    @BindView(R.id.logout)
    Button logout;
    @BindView(R.id.tv_m)
    TextView mTvM;
    @BindView(R.id.ll_clear)
    LinearLayout mLlClear;
    private MyModel settingsModel;

    /**
     * 替代onCreate的使用
     *
     * @param bundle
     */
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_set);
    }


    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        settingsModel = MyHelper.getInstance().getModel();
        setTitle("设置");
        if (settingsModel.getSettingMsgNotification()) {
            switchBtn.setChecked(true);
        } else {
            switchBtn.setChecked(false);
        }
        try {
            mTvM.setText(StringUtil.isEmpty(DataCleanManager.getTotalCacheSize(this)) ? "" : DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvBanben.setText("当前版本：v" + SystemUtil.getAppVersionName());

        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    settingsModel.setSettingMsgNotification(true);
                    settingsModel.setSettingMsgVibrate(true);
                    settingsModel.setSettingMsgSound(true);

                } else {
                    settingsModel.setSettingMsgNotification(false);
                    settingsModel.setSettingMsgVibrate(false);
                    settingsModel.setSettingMsgSound(false);
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CretinAutoUpdateUtils.getInstance(this).destroy();
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
    protected void onResume() {
        super.onResume();
        // initUserInfo();
    }

    @OnClick({R.id.tv_black, R.id.ll_chat_bg, R.id.ll_banben, R.id.logout, R.id.ll_clear, R.id.ll_anquan,R.id.tv_logoff,R.id.tv_register_agreement,R.id.tv_user_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_banben:
//                AppConfig.checkVersion(SetActivity.this, false);
                break;
            //帐号安全
            case R.id.ll_anquan:
                startActivity(AccountSecurityActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("from", "2");
//                startActivity(AppPayPassActivity.class, bundle);
                break;
            //退出
            case R.id.logout:
                logout();
                break;
            case R.id.ll_clear:
                DataCleanManager.clearAllCache(this);
                toast("清理完成");
                mTvM.setText("");
                break;
            case R.id.ll_chat_bg:
                //聊天设置
                startActivity(ChatSetActivity.class);
                break;
            case R.id.tv_black:
                //黑名单
                startActivity(BlackListActivity.class);
                break;

            case R.id.tv_logoff:
                //注销账号
                new EaseAlertDialog(this, "确定注销帐号？", null, null, new EaseAlertDialog.AlertDialogUser() {
                    @Override
                    public void onResult(boolean confirmed, Bundle bundle) {
                        if (confirmed) {
                            logoff();
                        }
                    }
                }).show();
                break;
            case R.id.tv_user_agreement:
                startActivity(new Intent(this, WebViewActivity.class).putExtra("title", "lan").putExtra("url", AppConfig.user_agree));
                break;
            case R.id.tv_register_agreement:
                startActivity(new Intent(this, WebViewActivity.class).putExtra("title", "lan").putExtra("url", AppConfig.register_agree));
                break;
            default:
        }
    }

    private void logoff() {
        Map<String,Object> map =new HashMap<>();
        map.put("userId",UserComm.getUserInfo().getUserId());
        ApiClient.requestNetHandle(SetActivity.this, AppConfig.toLogoff, "请稍候...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                //关闭当前所有页面并跳转到登录页面
                ToastUtil.toast("注销成功");
                EventBus.getDefault().post(new EventCenter(EventUtil.LOSETOKEN, "关闭"));

                MyHelper.getInstance().logout(false, new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        /*runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SetActivity.this.finish();
                            }
                        });*/
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                //dismissLoading();
                                Toast.makeText(SetActivity.this, "退出环信失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);

            }
        });
    }

    private void logout() {
        String st = getResources().getString(R.string.Are_logged_out);
        showLoading(st);
        MyHelper.getInstance().logout(false, new EMCallBack() {

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissLoading();

                        // show login screen
                        SetActivity.this.finish();
                        EventBus.getDefault().post(new EventCenter(EventUtil.LOSETOKEN, "关闭"));
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        dismissLoading();
                        Toast.makeText(SetActivity.this, "退出失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
