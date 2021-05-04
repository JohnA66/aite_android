package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.utils.EventUtil;
import com.zds.base.ImageLoad.GlideUtils;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.util.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描   述: 忘记支付密码修改手机号
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/11/17 10:19
 * 更新日期: 2017/11/17
 *
 * @author lhb
 */
public class VerifyingPayPasswordPhoneNumberActivity extends BaseActivity {
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
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_step)
    TextView mTvStep;
    @BindView(R.id.tv_code)
    TextView mTvCode;
    @BindView(R.id.llayout_title_1)
    RelativeLayout llayoutTitle1;
    @BindView(R.id.et_tuxing)
    EditText etTuxing;
    @BindView(R.id.img_code)
    ImageView imgCode;

    private CountDownTimer timer;
    private int flag;

    /**
     * 替代onCreate的使用
     *
     * @param bundle
     */
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_verify_pay_password_phone_number);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        setTitle("忘记支付密码");
        mEtPassword.addTextChangedListener(watcher);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }

    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }
    };

    private void countDown() {
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvCode.setText(millisUntilFinished / 1000 + "s后重新获取");
                mTvCode.setEnabled(false);
                mTvCode.setBackgroundResource(R.drawable.shap_gray_5);
            }

            @Override
            public void onFinish() {
                mTvCode.setText("获取验证码");
                mTvCode.setBackgroundResource(R.drawable.shap_red_5);
                mTvCode.setEnabled(true);
            }
        };
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        if (StringUtil.isEmpty(etTuxing)) {
            ToastUtil.toast("图形验证码不能为空");
            etTuxing.requestFocus();
            return;
        }
        final String phone = UserComm.getUserInfo().getPhone();
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("random", flag + "");
        map.put("imgCode", etTuxing.getText().toString());
        ApiClient.requestNetHandle(VerifyingPayPasswordPhoneNumberActivity.this, AppConfig.sendForgetPayPwdCode, "获取验证码...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (json != null) {
                    toast(msg);
                    timer.start();
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    /**
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
    @Override
    protected void onEventComing(EventCenter center) {
        if (center.getEventCode() == EventUtil.CLOSE2) {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_code, R.id.img_code, R.id.tv_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_code:
                flushTy();
                break;
            case R.id.tv_code:
                getCode();
                break;
            case R.id.tv_step:
                if (StringUtil.isEmpty(mEtPassword.getText().toString())) {
                    ToastUtil.toast("验证码不能为空");
                    return;
                }
                startActivity(new Intent(VerifyingPayPasswordPhoneNumberActivity.this, AppPayPassActivity.class)
                        .putExtra("authCode", mEtPassword.getText().toString())
                        .putExtra("from","3"));
                finish();
                break;
            default:
                break;
        }
    }
}
