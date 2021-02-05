package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.RoomInfo;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.widget.EaseImageView;
import com.zds.base.ImageLoad.GlideUtils;
import com.zds.base.json.FastJsonUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lhb
 * 申请加入群组
 */
public class UpdateRoomInfoActivity extends BaseActivity {
    @BindView(R.id.et_search_group)
    EditText mEtSearchGroup;
    @BindView(R.id.img_group)
    EaseImageView mImgGroup;
    @BindView(R.id.tv_group_name)
    TextView mTvGroupName;
    @BindView(R.id.tv_group_number)
    TextView mTvGroupNumber;
    @BindView(R.id.ll_layout)
    RelativeLayout mLlLayout;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    private String groupHxId;

//    @BindView(R.id.fl_content)
//    FrameLayout mFlContent;
//    private GroupListFragment mGroupListFragment;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_apply_add_group);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("群添加");
        mEtSearchGroup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    getData();
                }
            }
        });
//        mGroupListFragment = new GroupListFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, mGroupListFragment).commit();
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_apply_add_group)
    public void onViewClicked() {
        applyAddGroup();
    }


    //获取数据
    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "1");
        map.put("groupNumber", mEtSearchGroup.getText().toString().trim());
        ApiClient.requestNetHandle(this, AppConfig.searchGroup, "", map, new ResultListener() {
            /**
             * 请求成功
             * @param json
             * @param msg
             */
            @Override
            public void onSuccess(String json, String msg) {
                if (json != null) {
                    RoomInfo roomInfo = FastJsonUtil.getObject(json, RoomInfo.class);
                    groupHxId = roomInfo.getHuanxinGroupId();
                    mLlLayout.setVisibility(View.VISIBLE);
                    GlideUtils.loadImageViewLodingByCircle(AppConfig.baseService + roomInfo.getRoomImg(), mImgGroup, R.mipmap.ic_launcher);
                    mTvGroupName.setText("群名称: " + roomInfo.getName());
                    mTvGroupNumber.setText("群号: " + roomInfo.getGroupNumber());
                }
            }


            @Override
            public void onFinsh() {
                super.onFinsh();
            }

            /**
             * 请求失败
             *
             * @param msg
             */
            @Override
            public void onFailure(String msg) {
                mLlLayout.setVisibility(View.GONE);
            }
        });
    }


    /**
     * 申请加群
     */
    private void applyAddGroup() {
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", groupHxId);
        ApiClient.requestNetHandle(this, AppConfig.applyToRoom, "正在申请...", map, new ResultListener() {
            /**
             * 请求成功
             * @param json
             * @param msg
             */
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
            }


            @Override
            public void onFinsh() {
                super.onFinsh();
            }

            /**
             * 请求失败
             *
             * @param msg
             */
            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }


}
