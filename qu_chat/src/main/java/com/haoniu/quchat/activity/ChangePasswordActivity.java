package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoniu.quchat.entity.LoginInfo;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.base.MyHelper;
import com.haoniu.quchat.entity.EventCenter;
import com.hyphenate.EMCallBack;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作   者：赵大帅
 * 描   述:
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/11/17 17:10
 * 更新日期: 2017/11/17
 */
public class ChangePasswordActivity extends BaseActivity {
    @BindView(R.id.bar)
    View mBar;
    @BindView(R.id.ll_back)
    LinearLayout mLlBack;
    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubtitle;
    @BindView(R.id.img_right)
    ImageView mImgRight;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.old_password)
    EditText mOldPassword;
    @BindView(R.id.new_password)
    EditText mNewPassword;
    @BindView(R.id.new_or_password)
    EditText mNewOrPassword;
    @BindView(R.id.tv_tosubmit)
    TextView mTvTosubmit;

    /**
     * 替代onCreate的使用
     *
     * @param bundle
     */
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_change_password);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        setTitle("修改密码");
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_tosubmit)
    public void onViewClicked() {
        toSubmit();
    }

    private void toSubmit() {
        LoginInfo userInfo = UserComm.getUserInfo();
        String txtoldPassword = mOldPassword.getText().toString().trim();
        final String txtNewPassword = mNewPassword.getText().toString().trim();
        String txtNewTwoPassword = mNewOrPassword.getText().toString().trim();
        if (TextUtils.isEmpty(txtoldPassword)) {
            toast("原密码不能为空");
            mOldPassword.requestFocus();
            return;
        } else if (!userInfo.getMyPassword().equals(txtoldPassword)) {
            toast("原密码不正确");
            mOldPassword.requestFocus();
            return;
        } else if (TextUtils.isEmpty(txtNewPassword)) {
            toast("新密码不能为空");
            mNewPassword.requestFocus();
            return;
        } else if (txtNewPassword.length() < 6) {
            toast("密码太短");
            mNewPassword.requestFocus();
            return;
        } else if (TextUtils.isEmpty(txtNewTwoPassword) || !txtNewTwoPassword.equals(txtNewPassword)) {
            toast("密码输入不一致");
            mNewOrPassword.requestFocus();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("oldPwd", txtoldPassword);
        map.put("newPwd", txtNewPassword);
        ApiClient.requestNetHandle(this, AppConfig.upDataPassword, "正在提交...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (json != null) {
                    toast(msg);
                    MyApplication.getInstance().cleanUserInfo();
                    MyHelper.getInstance().logout(false, new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            startActivity(AuthenticationActivity.class);
                            finish();
                        }

                        @Override
                        public void onError(int i, String s) {

                        }

                        @Override
                        public void onProgress(int i, String s) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }
}
