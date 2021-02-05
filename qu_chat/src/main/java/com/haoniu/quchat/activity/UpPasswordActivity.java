package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.entity.LoginInfo;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.utils.EventUtil;
import com.zds.base.Toast.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作   者：赵大帅
 * 描   述: 修改密码
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/11/17 10:19
 * 更新日期: 2017/11/17
 */
public class UpPasswordActivity extends BaseActivity {


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
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_old_password)
    EditText mEtOldPassword;
    @BindView(R.id.et_new_password)
    EditText mEtNewPassword;
    @BindView(R.id.lie_tjr)
    View mLieTjr;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_tosubmit)
    TextView mTvTosubmit;

    /**
     * 替代onCreate的使用
     *
     * @param bundle
     */
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_uppassword);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        setTitle("修改密码");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
    @Override
    protected void onEventComing(EventCenter center) {
        if (center.getEventCode() == EventUtil.CLOSE1) {
            finish();
        }
    }

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    @Override
    protected void getBundleExtras(Bundle extras) {

    }


    /**
     * 修改密码
     */
    private void upPassword() {
        if (mEtOldPassword.getText().toString() != null && "".equals(mEtOldPassword.getText().toString())) {
            mEtOldPassword.requestFocus();
            toast("请输入原密码");
            return;
        } else if (mEtNewPassword.getText().toString() != null && "".equals(mEtNewPassword.getText().toString())) {
            mEtNewPassword.requestFocus();
            toast("请输入新密码");
            return;
        } else if ((mEtPassword.getText().toString() != null && "".equals(mEtPassword.getText().toString())) || !mEtPassword.getText().toString().equals(mEtNewPassword.getText().toString())) {
            mEtPassword.requestFocus();
            toast("密码输入不一致");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("oldPwd", mEtOldPassword.getText().toString());
        map.put("newPwd", mEtPassword.getText().toString());
        ApiClient.requestNetHandle(this, AppConfig.upDataPassword, "正在更新...", map, new ResultListener() {
            /**
             * 请求成功
             *
             * @param json
             * @param msg
             */
            @Override
            public void onSuccess(String json, String msg) {
                LoginInfo loginInfo = UserComm.getUserInfo();
                loginInfo.setMyPassword(mEtPassword.getText().toString());
                UserComm.saveUsersInfo(loginInfo);
                toast(msg);
                finish();
            }

            /**
             * 请求失败
             *
             * @param msg
             */
            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.tv_tosubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tosubmit:
                upPassword();
                break;
            default:
        }
    }
}
