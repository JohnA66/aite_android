package com.haoniu.quchat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.aop.SingleClick;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.LoginInfo;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.utils.StringUtil;
import com.haoniu.quchat.widget.CommonDialog;
import com.haoniu.quchat.widget.CustomerKeyboard;
import com.haoniu.quchat.widget.PasswordEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lhb
 * 发送个人红包
 */
public class SendPersonRedPackageActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.et_red_amount)
    EditText mEtRedAmount;
    @BindView(R.id.et_remark)
    EditText mEtRemark;
    @BindView(R.id.tv_red_amount)
    TextView mTvRedAmount;
    @BindView(R.id.tv_send_red)
    TextView mTvSendRed;

    private String toChatUsername;
    private boolean isSelectBalance = true;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_send_person_red_package);

    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("发送红包");
        mEtRedAmount.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        mEtRedAmount.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start
                            , int end, Spanned dest, int dstart, int dend) {
                        if (source.equals(".") && dest.toString().length() == 0) {
                            return "0.";
                        }

                        if (dest.toString().contains(".")) {
                            int index = dest.toString().indexOf(".");
                            int length =
                                    dest.toString().substring(index).length();
                            if (length == 3) {
                                return "";
                            }
                        }

                        return null;
                    }
                }
        });

        mEtRedAmount.addTextChangedListener(new TextWatcher() {
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
                    mTvRedAmount.setText("￥" + mEtRedAmount.getText().toString().trim());
                    mTvSendRed.setBackgroundResource(R.drawable.bor_login_sel);
                } else {
                    mTvRedAmount.setText("￥0.00");
                    mTvSendRed.setBackgroundResource(R.drawable.bor_login);
                }
            }
        });

    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        toChatUsername = extras.getString("username", "");
    }
    private long mLastClickTime = 0;
    public static final long TIME_INTERVAL = 1000L;

    /**
     * 发红包
     */
    private void sendRedPacket(String password) {

        long nowTime = System.currentTimeMillis();

        if (nowTime - mLastClickTime > TIME_INTERVAL) {
            // do something
            mLastClickTime = nowTime;
        }else {
            return;
        }

        Map<String, Object> map = new HashMap<>(1);
        map.put("money", mEtRedAmount.getText().toString().trim());
        map.put("payPassword", password);
        map.put("toUserId", toChatUsername.split("-")[0]);
//        map.put("payType", isSelectBalance ? 0 : 1);
        String remark =
                StringUtil.isEmpty(mEtRemark.getText().toString().trim()) ?
                        "恭喜发财，大吉大利！" : mEtRemark.getText().toString().trim();
        map.put("remark", remark);
        ApiClient.requestNetHandle(this, AppConfig.CREATE_PERSON_RED_PACKE,
                "正在发红包.." +
                        ".", map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        Intent intent = new Intent();
                        intent.putExtra("money",
                                mEtRedAmount.getText().toString().trim());
                        intent.putExtra("remark",
                                remark);
                        intent.putExtra("redId", json);
                        setResult(Activity.RESULT_OK, intent);
                        toast(msg);
                        finish();
                    }

                    @Override
                    public void onFailure(String msg) {
                        toast(msg);
                        mLastClickTime = 0;
                    }
                });
    }

    @OnClick(R.id.tv_send_red)
    public void onViewClicked() {
        PayPassword();
    }

    /**
     * 支付密码
     */
    private void PayPassword() {
        if (mEtRedAmount.getText().length() <= 0 || mEtRedAmount.getText().toString().equals("") || mEtRedAmount.getText().toString().equals("0.") || mEtRedAmount.getText().toString().equals("0.0")
                || mEtRedAmount.getText().toString().equals("0.00")) {
            toast("请填写正确的金额");
            return;
        }

        double price =
                Double.parseDouble(mEtRedAmount.getText().toString().trim());
        if (price > 10000) {
            toast("金额不得超过10000元");
            return;
        }

        LoginInfo userInfo = UserComm.getUserInfo();
        if (userInfo.getPayPwdFlag() == 0) {
            startActivity(new Intent(this,
                    InputPasswordActivity.class));
            return;
        }
        final CommonDialog.Builder builder =
                new CommonDialog.Builder(this).fullWidth().fromBottom()
                        .setView(R.layout.dialog_customer_keyboard);
        builder.setOnClickListener(R.id.delete_dialog,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });
        builder.create().show();
        final CustomerKeyboard mCustomerKeyboard =
                builder.getView(R.id.custom_key_board);
        final PasswordEditText mPasswordEditText =
                builder.getView(R.id.password_edit_text);

        LinearLayout mLlaySelectMode = builder.getView(R.id.llay_select_mode);
        mLlaySelectMode.setVisibility(View.GONE);

        RelativeLayout mLlayBalanceSelect =
                builder.getView(R.id.llay_balance_select);
        ImageView mImgBalanceSelect = builder.getView(R.id.img_balance_select);

        RelativeLayout mLlayBankCarSelect =
                builder.getView(R.id.llay_bank_car_select);
        ImageView mImgBankCarSelect = builder.getView(R.id.img_bank_car_select);

        mLlayBalanceSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectBalance = true;
                mImgBalanceSelect.setVisibility(View.VISIBLE);
                mImgBankCarSelect.setVisibility(View.GONE);
            }
        });

        mLlayBankCarSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectBalance = false;
                mImgBalanceSelect.setVisibility(View.GONE);
                mImgBankCarSelect.setVisibility(View.VISIBLE);
            }
        });

        mCustomerKeyboard.setOnCustomerKeyboardClickListener(new CustomerKeyboard.CustomerKeyboardClickListener() {
            @Override
            public void click(String number) {
                if ("返回".equals(number)) {
                    builder.dismiss();
                } else if ("忘记密码？".equals(number)) {
                    startActivity(new Intent(SendPersonRedPackageActivity.this, VerifyingPayPasswordPhoneNumberActivity.class));
                } else {
                    mPasswordEditText.addPassword(number);
                }
            }

            @Override
            public void delete() {
                mPasswordEditText.deleteLastPassword();
            }
        });

        mPasswordEditText.setOnPasswordFullListener(new PasswordEditText.PasswordFullListener() {
            @Override
            @SingleClick(1500)
            public void passwordFull(String password) {
                sendRedPacket(password);
                builder.dismiss();
            }
        });

    }

}
