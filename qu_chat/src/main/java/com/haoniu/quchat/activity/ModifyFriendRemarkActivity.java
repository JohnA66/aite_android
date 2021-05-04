package com.haoniu.quchat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.operate.UserOperateManager;
import com.haoniu.quchat.utils.EventUtil;
import com.zds.base.util.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ModifyFriendRemarkActivity extends BaseActivity {

    private static final String KEY_INTENT_FRIEND_USER_ID = "friend_user_id";
    private static final String KEY_INTENT_FRIEND_REMARK = "friend_remark";

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubTitle;
    @BindView(R.id.et_remark)
    EditText mEtRemark;
    @BindView(R.id.tv_confirm)
    TextView mTvConfirm;



    private String mFriendUserId = "";
    private String mFriendRemark = "";

    public static void start(Context context, String friendUserId,String friendRemark){
        Intent intent = new Intent(context,ModifyFriendRemarkActivity.class);
        intent.putExtra(KEY_INTENT_FRIEND_USER_ID,friendUserId);
        intent.putExtra(KEY_INTENT_FRIEND_REMARK,friendRemark);
        context.startActivity(intent);
    }

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_modify_friend_remark);
    }

    @Override
    protected void initLogic() {
        getIntentData();
        mToolbarTitle.setText("修改备注");
        //mToolbarSubTitle.setText("完成");
        mEtRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mTvConfirm.setEnabled(true);
                    mTvConfirm.setBackgroundResource(R.drawable.wallet_tx_bg_sel);
                } else {
                    mTvConfirm.setEnabled(false);
                    mTvConfirm.setBackgroundResource(R.drawable.report_bg_nor_sel);
                }

            }
        });
        mEtRemark.setText(mFriendRemark);
        mEtRemark.setSelection(StringUtil.isEmpty(mFriendRemark) ? 0 :
                mFriendRemark.length());
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mFriendUserId = intent.getStringExtra(KEY_INTENT_FRIEND_USER_ID);
        mFriendRemark = intent.getStringExtra(KEY_INTENT_FRIEND_REMARK);
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @OnClick({R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                addRemark();
                break;
            default:
                break;
        }
    }

    private void addRemark() {
        Map<String, Object> map = new HashMap<>();
        if (mFriendUserId.contains(Constant.ID_REDPROJECT)) {
            mFriendUserId = mFriendUserId.split("-")[0];
        }
        map.put("friendUserId", mFriendUserId);
        map.put("friendNickName", mEtRemark.getText().toString().trim());
        ApiClient.requestNetHandle(this, AppConfig.ADD_GOODS_FRIEND_REMARK,
                "请稍后...", map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        String name = mEtRemark.getText().toString().trim();
                        UserOperateManager.getInstance().updateUserName(mFriendUserId,name);

                        EventBus.getDefault().post(new EventCenter<>(EventUtil.REFRESH_REMARK));
                        toast("设置成功");

                        finish();
                    }

                    @Override
                    public void onFailure(String msg) {
                        toast(msg);
                    }
                });
    }
}
