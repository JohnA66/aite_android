package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.haoniu.quchat.base.Constant;
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
public class SendGroupRedPackageActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.et_red_amount)
    EditText mEtRedAmount;
    @BindView(R.id.tv_red_amount)
    TextView mTvRedAmount;
    @BindView(R.id.et_red_num)
    EditText mEtRedNum;
    @BindView(R.id.et_remark)
    EditText mEtRedMark;
    @BindView(R.id.bar)
    View mBar;
    @BindView(R.id.llayout_title_1)
    RelativeLayout mLlayoutTitle1;
    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubtitle;
    @BindView(R.id.tv_send_red)
    TextView mTvSendRed;

    private int isSelectBalance=0;
    private TextView tv_bank_name;
    private String bankId = "";
    private String groupId;
    private String emGroupId;
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_send_group_red_package);

    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("发送红包");
        isTransparency(false);
        mToolbarTitle.setTextColor(ContextCompat.getColor(this,
                R.color.black_text));
        mToolbarSubtitle.setVisibility(View.GONE);
        mToolbarSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
        emGroupId = extras.getString(Constant.PARAM_EM_GROUP_ID);
        groupId = extras.getString("groupId");
    }

    private long mLastClickTime = 0;
    public static final long TIME_INTERVAL = 1000L;
    /**
     * 发红包
     */
    private void sendRedPacket(String password) {
        long nowTime = System.currentTimeMillis();

        if (nowTime - mLastClickTime > TIME_INTERVAL) {
            mLastClickTime = nowTime;
        }else {
            return;
        }

        Map<String, Object> map = new HashMap<>(1);
        map.put("money", mEtRedAmount.getText().toString().trim());
        map.put("payPassword", password);
        map.put("groupId", groupId);
        map.put("cardId", bankId);
        map.put("packetAmount", mEtRedNum.getText().toString().trim());
        map.put("payType", isSelectBalance);
        map.put("huanxinGroupId", emGroupId);
        String remark =
                StringUtil.isEmpty(mEtRedMark.getText().toString().trim()) ?
                        "恭喜发财，大吉大利！" : mEtRedMark.getText().toString().trim();
        map.put("remark", remark);

        ApiClient.requestNetHandle(this, AppConfig.CREATE_RED_PACKE, "正在发送红包." +
                "..", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
//                if (isSelectBalance) {
                toast(msg);
                if (json.length()>1500){
                    startActivity(new Intent(SendGroupRedPackageActivity.this, WebViewActivity.class).putExtra("url", json).putExtra("title", "充值"));
                }
                finish();
//                } else {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("url", json);
//                    bundle.putString("title", "银行卡支付");
//                    bundle.putBoolean("isRedPage", true);
//                    bundle.putString("groupId", userName);
//                    startActivity(WebViewActivity.class, bundle);
//                }
                bankId = "";
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
                bankId = "";
                mLastClickTime = 0;
            }
        });
    }

    @OnClick(R.id.tv_send_red)
    public void onViewClicked() {
        //发红包
        PayPassword();
//        sendRedPacket();
    }


    /**
     * 支付密码
     */
    private void PayPassword() {
        isSelectBalance = 0;
        if (mEtRedAmount.getText().length() <= 0
                || mEtRedAmount.getText().toString().equals("")
                || mEtRedAmount.getText().toString().equals("0.")
                || mEtRedAmount.getText().toString().equals("0.0")
                || mEtRedAmount.getText().toString().equals("0.00")) {
            toast("请填写正确的金额");
            return;
        }

        if (mEtRedNum.getText().toString().length() <= 0) {
            toast("请填写红包个数");
            return;
        }
        LoginInfo userInfo = UserComm.getUserInfo();
        if (userInfo.getPayPwdFlag() == 0) {
            startActivity(new Intent(SendGroupRedPackageActivity.this,
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

        LinearLayout mLlaySelectMode = builder.getView(R.id.llay_select_mode);
        mLlaySelectMode.setVisibility(View.VISIBLE);

        RelativeLayout mLlayBalanceSelect = builder.getView(R.id.llay_balance_select);
        ImageView mImgBalanceSelect = builder.getView(R.id.img_balance_select);

        RelativeLayout mLlayBankCarSelect = builder.getView(R.id.llay_bank_car_select);
        mLlayBankCarSelect.setVisibility(View.GONE);
        ImageView mImgBankCarSelect = builder.getView(R.id.img_bank_car_select);

        RelativeLayout mLlayZfbCarSelect = builder.getView(R.id.llay_zfb_car_select);
        ImageView mImgZfbCarSelect = builder.getView(R.id.img_zfb_car_select);


        tv_bank_name = builder.getView(R.id.tv_bank_name);
        mLlayBalanceSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectBalance = 0;
                mImgBalanceSelect.setVisibility(View.VISIBLE);
                mImgBankCarSelect.setVisibility(View.GONE);
                mImgZfbCarSelect.setVisibility(View.GONE);
            }
        });

        mLlayBankCarSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectBalance = 1;
                mImgBalanceSelect.setVisibility(View.GONE);
                mImgBankCarSelect.setVisibility(View.VISIBLE);
                mImgZfbCarSelect.setVisibility(View.GONE);
                startActivityForResult(new Intent(SendGroupRedPackageActivity.this, BankActivity.class), 66);
            }
        });
        mLlayZfbCarSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSelectBalance = 2;
                mImgBalanceSelect.setVisibility(View.GONE);
                mImgBankCarSelect.setVisibility(View.GONE);
                mImgZfbCarSelect.setVisibility(View.VISIBLE);
            }
        });


        final CustomerKeyboard mCustomerKeyboard =
                builder.getView(R.id.custom_key_board);
        final PasswordEditText mPasswordEditText =
                builder.getView(R.id.password_edit_text);
        mCustomerKeyboard.setOnCustomerKeyboardClickListener(new CustomerKeyboard.CustomerKeyboardClickListener() {
            @Override
            public void click(String number) {
                if ("返回".equals(number)) {
                    builder.dismiss();
                } else if ("忘记密码？".equals(number)) {
                    startActivity(new Intent(SendGroupRedPackageActivity.this, VerifyingPayPasswordPhoneNumberActivity.class));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 66 && resultCode == 1111) {
            bankId = data.getStringExtra("id");
            String name = data.getStringExtra("name");
            if (tv_bank_name != null) {
                tv_bank_name.setText(name);
            }
        }
    }
}

