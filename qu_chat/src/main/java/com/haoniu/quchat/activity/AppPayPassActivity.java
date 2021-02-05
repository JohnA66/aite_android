package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.view.pay_pass_view.OnPasswordInputFinish;
import com.haoniu.quchat.view.pay_pass_view.PasswordView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * @author lhb
 * 支付密码
 */
public class AppPayPassActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.pwd_view)
    PasswordView mPwdView;
    private String mFrom;
    private String authCode;
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_pay_pass_app);
    }

    @Override
    protected void initLogic() {
        if (mFrom.equals("1")) {
            mToolbarTitle.setText("支付密码");
        } else if (mFrom.equals("2")) {
            mToolbarTitle.setText("修改支付密码");
            mPwdView.getTvSetTitle().setText("请输入旧的支付密码");
        } else if (mFrom.equals("3")) {
            mToolbarTitle.setText("忘记支付密码");
        }
        mPwdView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(String pass) {
                Bundle bundle = new Bundle();
                bundle.putString("from", mFrom);
                bundle.putString("pass", pass);
                bundle.putString("authCode", authCode);
                startActivity(PayPassSureActivity.class, bundle);
                finish();
            }

            //取消支付
            @Override
            public void outfo() {
                finish();
            }

            //忘记密码回调事件
            @Override
            public void forgetPwd() {

            }
        });
    }

    @Override
    protected void onEventComing(EventCenter center) {
        if (center.getEventCode() == EventUtil.CLOSE1) {
            finish();
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        mFrom = extras.getString("from");
        authCode = extras.getString("authCode");
    }


    /**
     * 添加支付密码
     *
     * @param password
     */
    private void toAddpassword(String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("payPassword", password);
        ApiClient.requestNetHandle(this, AppConfig.addPayPassword, "支付密码生成中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                MyApplication.getInstance().UpUserInfo();
                startActivity(WalletActivity.class);
                finish();
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
                finish();
            }
        });
    }

}
