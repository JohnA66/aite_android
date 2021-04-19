package com.haoniu.quchat.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aite.chat.R;
import com.ehking.sdk.wepay.interfaces.WalletPay;
import com.ehking.sdk.wepay.net.bean.AuthType;
import com.haoniu.quchat.activity.InputPasswordActivity;
import com.haoniu.quchat.activity.TransferActivity;
import com.haoniu.quchat.activity.VerifyingPayPasswordPhoneNumberActivity;
import com.haoniu.quchat.aop.SingleClick;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.LoginInfo;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.operate.UserOperateManager;
import com.haoniu.quchat.utils.CashierInputFilter;
import com.haoniu.quchat.utils.ImageUtil;
import com.haoniu.quchat.utils.StringUtil;
import com.haoniu.quchat.widget.CommonDialog;
import com.haoniu.quchat.widget.CustomerKeyboard;
import com.haoniu.quchat.widget.EaseImageView;
import com.haoniu.quchat.widget.PasswordEditText;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.zds.base.ImageLoad.GlideUtils;
import com.zds.base.json.FastJsonUtil;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TransferNewActivity extends BaseActivity {

    @BindView(R.id.img_head)
    EaseImageView mImgHead;
    @BindView(R.id.nickName)
    TextView mNickName;
    @BindView(R.id.et_money)
    EditText mEtMoney;
    @BindView(R.id.et_remark)
    EditText mEtRemark;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_transfer)
    TextView mTvTransfer;
    private String emChatId;

    //结果返回最多重新查询次数
    private int maxCount = 15;
    private Handler handler = new Handler();

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_transfer);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("转账");
        mEtMoney.setFilters(new InputFilter[]{new CashierInputFilter()});
        mEtMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mTvTransfer.setBackgroundResource(R.drawable.bg_border_green);
                } else {
                    mTvTransfer.setBackgroundResource(R.drawable.shape_transter_nor);
                }

            }
        });
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        emChatId = extras.getString("emChatId");
        mNickName.setText(UserOperateManager.getInstance().getUserName(emChatId));
        ImageUtil.setAvatar(mImgHead);
        GlideUtils.loadImageViewLoding(UserOperateManager.getInstance().getUserAvatar(emChatId), mImgHead, R.mipmap.img_default_avatar);

    }

    /**
     * 转账
     */
    private void transfer(String password) {
        if (StringUtil.isEmpty(mEtMoney)) {
            toast("请输入转账金额");
            return;
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("money", mEtMoney.getText().toString().trim());
        map.put("payPassword", password);
        map.put("remark", mEtRemark.getText().toString());
        map.put("userId", emChatId.contains(Constant.ID_REDPROJECT) ?
                emChatId.split("-")[0] : emChatId);
        ApiClient.requestNetHandle(this, AppConfig.transfer, "正在转账...", map,
                new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        //发送名片
                        Intent intent = new Intent();
                        intent.putExtra("money",
                                mEtMoney.getText().toString().trim());
                        intent.putExtra("remark",
                                mEtRemark.getText().toString().trim());
                        intent.putExtra("turnId",
                                json);
                        setResult(Activity.RESULT_OK, intent);
                        toast("转账成功");
                        finish();
                    }

                    @Override
                    public void onFailure(String msg) {
                        toast(msg);
                    }
                });
    }




    @OnClick({R.id.tv_transfer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_transfer:
                //转账
                doTransferClick();
                break;
            default:
                break;
        }
    }

    private void doTransferClick() {
        maxCount = 15;
        if (mEtMoney.getText().length() <= 0 || mEtMoney.getText().toString().equals("") || mEtMoney.getText().toString().equals("0.") || mEtMoney.getText().toString().equals("0.0")
                || mEtMoney.getText().toString().equals("0.00")) {
            toast("请填写正确的金额");
            return;
        }

        double price =
                Double.parseDouble(mEtMoney.getText().toString().trim());
        if (price > 10000) {
            toast("金额不得超过10000元");
            return;
        }

        Map<String, Object> map = new HashMap<>(1);
        map.put("money", mEtMoney.getText().toString().trim());
        map.put("userId", emChatId.contains(Constant.ID_REDPROJECT) ?
                emChatId.split("-")[0] : emChatId);
        ApiClient.requestNetHandle(this, AppConfig.walletTransfer, "正在转账...", map,
                new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        if (json != null && json.length() > 0) {
                            WalletTransferBean walletTransferBean = FastJsonUtil.getObject(json, WalletTransferBean.class);

                            WalletPay walletPay = WalletPay.Companion.getInstance();
                            walletPay.init(TransferNewActivity.this);
                            walletPay.walletPayCallback = new WalletPay.WalletPayCallback() {
                                @Override
                                public void callback(@Nullable String source, @Nullable String status, @Nullable String errorMessage) {
                                    if(status == "SUCCESS" || status == "PROCESS"){
                                        queryResult(walletTransferBean.requestId);
                                    }
                                }
                            };
                            //调起SDK的转账
                            walletPay.evoke(Constant.MERCHANT_ID, UserComm.getUserInfo().ncountUserId,
                                    walletTransferBean.token, AuthType.TRANSFER.name());

                        }else {
                            toast("服务器开小差，请稍后重试");
                        }
                        /*//发送名片
                        Intent intent = new Intent();
                        intent.putExtra("money",
                                mEtMoney.getText().toString().trim());
                        intent.putExtra("remark",
                                mEtRemark.getText().toString().trim());
                        intent.putExtra("turnId",
                                json);
                        setResult(Activity.RESULT_OK, intent);
                        toast("转账成功");
                        finish();*/
                    }

                    @Override
                    public void onFailure(String msg) {
                        toast(msg);
                    }
                });
    }

    private void queryResult(String requestId) {
        Map<String, Object> map = new HashMap<>();
        map.put("requestId", requestId);
        ApiClient.requestNetHandleByGet(this, AppConfig.walletTransferQuery, "请稍等...",
                map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        if (json != null && json.length() > 0) {
                            WalletTransferQueryBean walletTransferQueryBean = FastJsonUtil.getObject(json, WalletTransferQueryBean.class);
                            switch (walletTransferQueryBean.orderStatus){
                                case "SEND":
                                    toast("转账成功");
                                    //发送名片
                                    Intent intent = new Intent();
                                    intent.putExtra("money",
                                            mEtMoney.getText().toString().trim());
                                    intent.putExtra("remark",
                                            mEtRemark.getText().toString().trim());
                                    intent.putExtra("turnId",
                                            walletTransferQueryBean.transferId);
                                    intent.putExtra("serialNumber",
                                            walletTransferQueryBean.serialNumber);
                                    intent.putExtra("requestId",
                                            walletTransferQueryBean.requestId);
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();
                                    break;
                                case "PROCESS":
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            maxCount--;
                                            if (maxCount<=0){
                                                toast("转账处理中");
                                                return;
                                            }
                                            queryResult(requestId);
                                        }
                                    },2000);
                                    break;
                                default:
                                    toast("转账失败");
                                    break;
                            }

                        }else {
                            toast("服务器开小差，请稍后重试");
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                });
    }
}
