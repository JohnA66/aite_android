package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.utils.PhoneFormatUtil;
import com.haoniu.quchat.utils.StringUtil;
import com.haoniu.quchat.widget.CommonDialog;
import com.haoniu.quchat.widget.VerifyCodeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lhb
 * 钱包
 */
public class WalletActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_amount)
    TextView mTvAmount;

    private String url;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_wallet);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("零钱");
        mTvAmount.setText(StringUtil.getFormatValue2(UserComm.getUserInfo().getMoney()));
    }


    @Override
    protected void getBundleExtras(Bundle extras) {

    }


    @OnClick({R.id.tv_wallet_lock,R.id.tv_recharge, R.id.tv_withdraw, R.id.tv_pay_manage, R.id.tv_my_redpack_record, R.id.tv_my_transfer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_recharge:
                //充值
//                if (UserComm.getUserInfo().getOpenAccountFlag() == 0) {
//                    showAuthDialog();
//                    return;
//                }
                startActivity(RechargeActivity.class);
                break;
            case R.id.tv_withdraw:
                //提现
//                if (UserComm.getUserInfo().getOpenAccountFlag() == 0) {
//                    showAuthDialog();
//                    return;
//                }
                startActivity(WithdrawActivity.class);
                break;
            case R.id.tv_pay_manage:
//                if (UserComm.getUserInfo().getOpenAccountFlag() == 0) {
//                    showAuthDialog();
//                    return;
//                }
//                if (url==null){
//                    return;
//                }
//                startActivity(new Intent(WalletActivity.this,WebViewActivity.class).putExtra("url",url).putExtra("title","银行卡"));
                startActivity(BankActivity.class);
                break;
            case R.id.tv_my_redpack_record:
                //我的红包记录
                startActivity(MyRedRecordActivity.class);
                break;
            case R.id.tv_my_transfer:
                //我的转账记录
                startActivity(TransferRecordActivity.class);
                break;
            case R.id.tv_wallet_lock:
                //我的转账记录
                startActivity(WalletLockActivity.class);

                break;
            default:
                break;
        }
    }

    /**
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
    @Override
    protected void onEventComing(EventCenter center) {
        if (center.getEventCode() == EventUtil.FLUSHUSERINFO) {
            mTvAmount.setText(StringUtil.getFormatValue2(UserComm.getUserInfo().getMoney()));
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().UpUserInfo();
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
                LoginInfo loginInfo= UserComm.getUserInfo();
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
