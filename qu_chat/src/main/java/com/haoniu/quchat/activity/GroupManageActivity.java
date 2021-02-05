package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.MyHelper;
import com.haoniu.quchat.domain.EaseGroupInfo;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.GroupDetailInfo;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.operate.GroupOperateManager;
import com.haoniu.quchat.utils.EventUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lhb
 * 群管理
 */
public class GroupManageActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.switch_shut_up)
    Switch mSwitchShutUp;
    @BindView(R.id.switch_group_qr)
    Switch mSwitchGroupQr;

    private String groupId;
    private String emChatId;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_group_manage);
    }

    @Override
    protected void initLogic() {
        GroupDetailInfo groupDetailInfo = GroupOperateManager.getInstance().getGroupData(emChatId);

        if (groupDetailInfo.getGroupSayFlag().equals("0")) {
            mSwitchShutUp.setChecked(false);
        } else {
            mSwitchShutUp.setChecked(true);
        }

        mToolbarTitle.setText("群管理");
        mSwitchShutUp.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                modifyGroupUserSayStatus("1");

            } else {
                modifyGroupUserSayStatus("0");

            }
        });
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        groupId = extras.getString("groupId");
        emChatId = extras.getString("username");

    }


    /**
     * 禁言/取消禁言
     *
     * @param
     */
    private void modifyGroupUserSayStatus(String sayStatus) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("groupId", groupId);
        //0 - 取消禁言 1 - 禁言
        map.put("sayStatus", sayStatus);

        ApiClient.requestNetHandle(this, AppConfig.MODIFY_GROUP_ALL_USER_SAY_STATUS, "...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {

                EventBus.getDefault().post(new EventCenter<>(EventUtil.ROOM_INFO));

                toast(msg);
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }


    @OnClick({R.id.switch_shut_up, R.id.tv_set_group_manage, R.id.tv_report})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_report:
                startActivity(new Intent(this, ReportActivity.class));
                break;
            case R.id.tv_set_group_manage:
                if (groupId == null) {
                    return;
                }
                //设置群管理员
                Bundle bundle = new Bundle();
                bundle.putString("groupId", groupId);
                bundle.putString(Constant.PARAM_EM_CHAT_ID,emChatId);
                startActivity(SetGroupManageActivity.class, bundle);
                break;
            default:
                break;
        }
    }
}
