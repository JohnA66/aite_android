package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.widget.PasswordInputEdt;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 作   者：赵大帅
 * 描   述:
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/11/17 9:28
 * 更新日期: 2017/11/17
 */
public class InputPasswordActivity extends BaseActivity {


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
    @BindView(R.id.edittext)
    PasswordInputEdt mEdittext;


    private String oldPassword;
    private String code;

    /**
     * 替代onCreate的使用
     *
     * @param bundle
     */
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_input_password);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        setTitle("支付密码");
        mEdittext.setOnInputOverListener(new PasswordInputEdt.onInputOverListener() {
            @Override
            public void onInputOver(String text) {
                if (oldPassword != null && !"".equals(oldPassword)) {
                    toUppassword(text);
                } else if (code != null && !"".equals(code)) {
                    forgetPassword(text);
                } else {
                    toAddpassword(text);
                }
            }
        });

    }

    /**
     * 修改密码
     *
     * @param password
     */
    private void toUppassword(String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("newPayPassword", password);
        map.put("payPassword", oldPassword);
        ApiClient.requestNetHandle(this, AppConfig.upPassword, "正在提交修改...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                EventBus.getDefault().post(new EventCenter(EventUtil.CLOSE1));
                finish();
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
                finish();
            }
        });
    }

    /**
     * 添加支付密码
     *
     * @param password
     */
    private void toAddpassword(String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("payPassword", password);
        ApiClient.requestNetHandle(this, AppConfig.addPayPassword, "正在提交...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                MyApplication.getInstance().UpUserInfo();
                finish();
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
                finish();
            }
        });
    }

    /**
     * 忘记支付密码
     *
     * @param password
     */
    private void forgetPassword(String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("newPayPwd", password);
        map.put("authCode", code);
        map.put("phone", UserComm.getUserInfo().getPhone());
        ApiClient.requestNetHandle(this, AppConfig.forgetPassword, "正在提交...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                MyApplication.getInstance().UpUserInfo();
                EventBus.getDefault().post(new EventCenter(EventUtil.CLOSE2));
                finish();
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
                finish();
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

    }

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    @Override
    protected void getBundleExtras(Bundle extras) {
        oldPassword = extras.getString("password", "");
        code = extras.getString("code", "");
    }

}
