package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.global.UserComm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描   述: 支付密码
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/11/17 9:48
 * 更新日期: 2017/11/17
 *
 * @author lhb
 */
public class PayPasswordActivity extends BaseActivity {
    @BindView(R.id.ll_up_password)
    LinearLayout mLlUpPassword;
    @BindView(R.id.ll_forget_password)
    LinearLayout mLlForgetPassword;

    /**
     * 替代onCreate的使用
     *
     * @param bundle
     */
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_pay_password);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        setTitle("支付密码");
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


    @OnClick({R.id.ll_up_password, R.id.ll_forget_password})
    public void onViewClicked(View view) {

        if (UserComm.getUserInfo().getPayPwdFlag() == 0) {
            Bundle bundle = new Bundle();
            bundle.putString("from", "1");
            startActivity(AppPayPassActivity.class, bundle);
//            startActivity(InputPasswordActivity.class);
            return;
        }
        switch (view.getId()) {
            case R.id.ll_up_password:
                //修改支付密码
                Bundle bundle = new Bundle();
                bundle.putString("from", "2");
                startActivity(AppPayPassActivity.class, bundle);

//                startActivity(VerifyingPayPasswordActivity.class);
                break;
            case R.id.ll_forget_password:
                //忘记支付密码
                startActivity(VerifyingPayPasswordPhoneNumberActivity.class);
                break;
            default:
        }
    }
}
