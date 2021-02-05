package com.haoniu.quchat.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.LocalPhoneAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.model.LocalPhoneInfo;
import com.haoniu.quchat.model.LocalPhoneMatchFreindInfo;
import com.haoniu.quchat.utils.PhoneUtil;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.json.FastJsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lhb
 * 本地通讯录
 */
public class LocalPhoneActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.query)
    EditText mQuery;
    @BindView(R.id.search_clear)
    ImageButton mSearchClear;
    @BindView(R.id.rv_phone)
    RecyclerView mRvPhone;
    private List<LocalPhoneInfo> mInfoList = new ArrayList<>();
    private List<LocalPhoneMatchFreindInfo> mFreindInfoList = new ArrayList<>();
    private LocalPhoneAdapter mLocalPhoneAdapter;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_local_phone);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("通讯录好友");
        check();
        mLocalPhoneAdapter = new LocalPhoneAdapter(mFreindInfoList);
        RclViewHelp.initRcLmVertical(this, mRvPhone, mLocalPhoneAdapter);

        mQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLocalPhoneAdapter.getFilter().filter(s.toString());


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mSearchClear.setVisibility(View.VISIBLE);
                } else {
                    mSearchClear.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    protected void onEventComing(EventCenter center) {
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
    }

    /**
     * 保存本地通讯录
     */
    private void queryPhoneFriend() {
        Map<String, Object> map = new HashMap<>();
        String mPhone = "";
        if (mInfoList.size() > 0) {
            for (LocalPhoneInfo info : mInfoList) {
                mPhone += info.getTelPhone().toString().replaceAll(" ", "") + ",";
            }
        } else {
            return;
        }
        map.put("phone", mPhone.substring(0, mPhone.length() - 1));
        ApiClient.requestNetHandle(this, AppConfig.LIST_USER_PHONE, "正在加载...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (FastJsonUtil.getList(json, LocalPhoneMatchFreindInfo.class) != null && FastJsonUtil.getList(json, LocalPhoneMatchFreindInfo.class).size() > 0) {
                    mFreindInfoList.addAll(FastJsonUtil.getList(json, LocalPhoneMatchFreindInfo.class));
                    mLocalPhoneAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }


    /**
     * 检查权限
     */
    private void check() {
        //判断是否有权限
        if (ContextCompat.checkSelfPermission(LocalPhoneActivity.this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LocalPhoneActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 201);
        } else {
            PhoneUtil phoneUtil = new PhoneUtil(this);
            mInfoList = phoneUtil.getPhone();
            queryPhoneFriend();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 201) {
            PhoneUtil phoneUtil = new PhoneUtil(this);
            mInfoList = phoneUtil.getPhone();
            queryPhoneFriend();

        } else {
            return;
        }
    }


    @OnClick({R.id.query, R.id.search_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.query:
                break;
            case R.id.search_clear:
                mQuery.getText().clear();
                break;
            default:
        }
    }
}
