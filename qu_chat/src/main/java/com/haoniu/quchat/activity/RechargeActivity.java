package com.haoniu.quchat.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.SelectMoneyAdapter;
import com.haoniu.quchat.aop.SingleClick;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.JsonBankCardList;
import com.haoniu.quchat.entity.LoginInfo;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.widget.CommonDialog;
import com.haoniu.quchat.widget.CustomerKeyboard;
import com.haoniu.quchat.widget.PasswordEditText;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描   述: 充值
 * 日   期: 2019/07/14 16:32
 * 更新日期: 2017/11/28
 *
 * @author lhb
 */
public class RechargeActivity extends BaseActivity {

    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubtitle;
    private JsonBankCardList jsonBankCardList;
    private List<JsonBankCardList.DataBean> dataBean;
    @BindView(R.id.rv_recycler_money)
    RecyclerView mSelectRecyclerMoney;
    @BindView(R.id.rv_recycler_card)
    RecyclerView mSelectRecyclerCard;
    @BindView(R.id.tv_new_bank_card_submit)
    TextView mNewBankCard;
    @BindView(R.id.tv_yhk)
    TextView tv_yhk;
    @BindView(R.id.tv_zfb)
    TextView tv_zfb;

    private int mSelectMoneyIndex; //10  50  100   500  1000 2000
    private ArrayList<String> mMoneyList = new ArrayList<String>(Arrays.asList("10元", "50元", "100元", "500元", "1000元","2000元"));

    /**
     * 替代onCreate的使用
     *
     * @param bundle
     */
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.pay_main);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        setTitle("零钱充值");
        mToolbarSubtitle.setText("充值记录");
        mToolbarSubtitle.setTextColor(ContextCompat.getColor(this, R.color.text_color_black));
        mToolbarSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RechargeRecordActivity.class);
            }
        });
        initUI();
        click();
    }

    private void click() {
        tv_yhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showType(1);
            }
        });
        tv_zfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showType(2);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void initUI() {
        mSelectMoneyIndex = 0;
        //设置显示布局
        mSelectRecyclerMoney.setLayoutManager(new LinearLayoutManager(this));
        //设置删除与加入的动画
        mSelectRecyclerMoney.setItemAnimator(new DefaultItemAnimator());
        final SelectMoneyAdapter mSelectMoneyAdapter = new SelectMoneyAdapter
                (this, mMoneyList, mSelectMoneyIndex);
        mSelectRecyclerMoney.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mSelectRecyclerMoney.setAdapter(mSelectMoneyAdapter);
        mSelectMoneyAdapter.setOnItemClickListener(new SelectMoneyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mSelectMoneyIndex = position;
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });



    }
    int types=1;
    //设置图标
    // type 1 银行卡 2支付宝
    public void showType(int type){
        if (type==1){
            types=1;
            //设置银行卡显示
            Drawable yhkdrawableLeft = getResources().getDrawable(R.mipmap.yhka);
            Drawable yhkdrawableRight = getResources().getDrawable(R.mipmap.xuanz_green);
            tv_yhk.setCompoundDrawablesWithIntrinsicBounds(yhkdrawableLeft, null, yhkdrawableRight, null);
            //取消设置支付宝显示
            Drawable drawableLeft = getResources().getDrawable(R.mipmap.zfba1);
            tv_zfb.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
        }else {
            types=2;
            //设置银行卡显示
            Drawable zfbdrawableLeft = getResources().getDrawable(R.mipmap.zfba1);
            Drawable zfbdrawableRight = getResources().getDrawable(R.mipmap.xuanz_green);
            tv_zfb.setCompoundDrawablesWithIntrinsicBounds(zfbdrawableLeft, null, zfbdrawableRight, null);
            //取消设置支付宝显示
            Drawable drawableLeft = getResources().getDrawable(R.mipmap.yhka);
            tv_yhk.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_new_bank_card_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_new_bank_card_submit:
                if (types==1) {
                    startActivityForResult(new Intent(this, BankActivity.class), 66);
                }else {
                    PayPassword("");
                }
                break;
            default:
                break;
        }
    }




    private boolean isRequest;

    /**
     * 充值
     */
    private void recharge(String id, String psw) {
        if (isRequest) {
            toast("加载中，请勿重复提交");
            return;
        }
        isRequest = true;
        Map<String, Object> map = new HashMap<>();
        map.put("rechargeMoney", mMoneyList.get(mSelectMoneyIndex).replace("元", ""));
        map.put("cardId", id);
        map.put("payPassword", psw);
        map.put("payType", 1);
        ApiClient.requestNetHandle(this, AppConfig.rechargeUrl, "充值中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
//                startActivity(new Intent(RechargeActivity.this, WebViewActivity.class).putExtra("url", json).putExtra("title", "充值"));
                toast(msg);
            }

            @Override
            public void onFinsh() {
                super.onFinsh();
                isRequest = false;
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }
    /**
     * 充值
     */
    private void recharge(String psw) {
        if (isRequest) {
            toast("加载中，请勿重复提交");
            return;
        }
        isRequest = true;
        Map<String, Object> map = new HashMap<>();
        map.put("rechargeMoney", mMoneyList.get(mSelectMoneyIndex).replace("元", ""));
        map.put("payPassword", psw);
        map.put("payType", 2);
        ApiClient.requestNetHandle(this, AppConfig.rechargeUrl, "充值中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                startActivity(new Intent(RechargeActivity.this, WebViewActivity.class).putExtra("url", json).putExtra("title", "充值"));
            }

            @Override
            public void onFinsh() {
                super.onFinsh();
                isRequest = false;
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 66 && resultCode == 1111) {
            String id = data.getStringExtra("id");
            PayPassword(id);
        }
    }

    /**
     * 支付密码
     */
    private void PayPassword(String id) {

        LoginInfo userInfo = UserComm.getUserInfo();
        if (userInfo.getPayPwdFlag() == 0) {
            startActivity(new Intent(RechargeActivity.this,
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
                mImgBalanceSelect.setVisibility(View.VISIBLE);
                mImgBankCarSelect.setVisibility(View.GONE);
            }
        });

        mLlayBankCarSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImgBalanceSelect.setVisibility(View.GONE);
                mImgBankCarSelect.setVisibility(View.VISIBLE);
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
                    startActivity(new Intent(RechargeActivity.this, VerifyingPayPasswordPhoneNumberActivity.class));
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
                if (types==1) {
                    recharge(id, password);
                }else {
                    recharge(password);
                }
                builder.dismiss();
            }
        });

    }
}
