/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.global.Global;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.code.activity.CaptureActivity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE;
import static com.zds.base.code.activity.CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN;

/**
 * @author lhb
 * 添加联系人
 */
public class AddContactActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.fl_query)
    FrameLayout mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 替代onCreate的使用
     *
     * @param bundle
     */
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.em_activity_add_contact);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        mToolbarTitle.setText("添加联系人");
        mQuery.setFocusable(false);
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
    public void back(View v) {
        finish();
    }

    @OnClick({R.id.rl_scan, R.id.rl_add_wx_friend, R.id.rl_add_contact, R.id.rl_my_qr, R.id.fl_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_scan:
                //扫一扫
                Global.addUserOriginType = Constant.ADD_USER_ORIGIN_TYPE_QRCODE;
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.rl_add_wx_friend:
                //添加微信好友
                break;
            case R.id.rl_add_contact:
                //手机联系人
                startActivity(LocalPhoneActivity.class);
                break;
            case R.id.rl_my_qr:
                //我的二维码
                Bundle bundle = new Bundle();
                bundle.putString("from", "1");
                startActivity(MyQrActivity.class, bundle);
                break;
            case R.id.fl_query:
                //添加好友
                startActivity(AddUserActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                String result = bundle.getString(INTENT_EXTRA_KEY_QR_SCAN);
                if (result.contains("person") || result.contains("group")) {
                    if ("person".equals(result.split("_")[1])) {
                        startActivity(new Intent(this, UserInfoDetailActivity.class).putExtra("friendUserId", result.split("_")[0]));
                    } else {
//                    addUser(result);
                        inviteContact(result.split("_")[0]);

                    }
                }
            }
        }
    }

    /**
     * 扫码进群
     *
     * @param
     */
    private void inviteContact(String groupId) {

        List mIdList = new ArrayList<>();
        mIdList.add(UserComm.getUserInfo().getUserId());
        Map<String, Object> map = new HashMap<>(2);
        map.put("groupId", groupId);
        map.put("type", 1);
        map.put("userId", UserComm.getUserInfo().getUserId());
        ApiClient.requestNetHandle(this, AppConfig.SAVE_GROUP_USER, "正在加入...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }

}
