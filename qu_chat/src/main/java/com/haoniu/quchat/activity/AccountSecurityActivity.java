package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.LoginInfo;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.widget.CommonDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 帐号安全
 *
 * @author Administrator
 */

public class AccountSecurityActivity extends BaseActivity {


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
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.ll_up_password)
    LinearLayout llUpPassword;

    /**
     * 替代onCreate的使用
     *
     * @param bundle
     */
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_account_security);
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        setTitle("账号与安全");
        LoginInfo userInfo = UserComm.getUserInfo();
        if (TextUtils.isEmpty(userInfo.getPhone())){
            tvPhone.setText(userInfo.getPhone());
        }

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

    @OnClick({R.id.ll_up_password, R.id.ll_pay_password})
    public void onClick(View view) {
        switch (view.getId()) {
            //修改密码
            case R.id.ll_up_password:
                startActivity(PayPasswordActivity.class);
                break;
            //支付密码
            case R.id.ll_pay_password:
//                startActivity(PayPasswordActivity.class);
                if (UserComm.getUserInfo().getOpenAccountFlag() == 0) {
                    showAuthDialog();
                    return;
                }
                payPassword();
                break;
            default:
        }
    }


    private boolean isRequest;
    /**
     * 支付密码
     */
    private void payPassword(){
        if (isRequest){
            toast("加载中，请勿重复提交");
            return;
        }
        isRequest=true;
        Map<String,Object> map =new HashMap<>();
        ApiClient.requestNetHandle(this, AppConfig.updateYeepayPayPwd, "请求中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                startActivity(new Intent(AccountSecurityActivity.this,WebViewActivity.class).putExtra("url",json).putExtra("title","充值密码"));
            }

            @Override
            public void onFinsh() {
                super.onFinsh();
                isRequest=false;
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }


    private CommonDialog.Builder builder;
    private EditText etName, etCard, etPhone;

    /**
     * 实名认证弹窗
     */
    private void showAuthDialog() {
        if (builder != null) {
            builder.dismiss();
        }
        builder = new CommonDialog.Builder(this).fullWidth().center()
                .setView(R.layout.dialog_custinfo);

        builder.setOnClickListener(R.id.tv_sure, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确定
                if (etName.getText().toString().trim().length() <= 0) {
                    toast("请填写姓名");
                    return;
                } else if (etCard.getText().toString().trim().length() <= 0) {
                    toast("请填写身份证号码");
                    return;
                } else if (etPhone.getText().toString().trim().length() <= 0) {
                    toast("请填写手机号");
                    return;
                }
                openAccount( etName.getText().toString().trim(), etCard.getText().toString().trim(), etPhone.getText().toString().trim());

            }
        });
        builder.setOnClickListener(R.id.img_close, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        CommonDialog dialog = builder.create();
        etName = (EditText) dialog.getView(R.id.et_name);
        etCard = (EditText) dialog.getView(R.id.et_card);
        etPhone = (EditText) dialog.getView(R.id.et_phone);
        dialog.show();
    }

    private boolean isrenzheng;

    /**
     * 认证
     */
    private void openAccount(String name,String certificateNo,String mobile){
        Map<String,Object> map =new HashMap<>();
        map.put("name",name);
        map.put("certificateNo",certificateNo);
        map.put("mobile",mobile);
        if (isrenzheng==true){
            toast("认证中，请勿重复提交");
            return;
        }
        isrenzheng=true;
        ApiClient.requestNetHandle(this, AppConfig.openAccount, "认证中", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                LoginInfo loginInfo=UserComm.getUserInfo();
                loginInfo.setOpenAccountFlag(1);
                UserComm.saveUsersInfo(loginInfo);
                toast(msg);
                if (builder!=null){
                    builder.dismiss();
                }
            }

            @Override
            public void onFinsh() {
                super.onFinsh();
                isrenzheng=false;
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);

            }
        });
    }

}
