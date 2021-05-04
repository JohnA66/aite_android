package com.haoniu.quchat.activity;

import android.os.Bundle;

import com.aite.chat.R;
import com.haoniu.quchat.activity.fragment.FriendApplyFragment;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.constant.SP;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.utils.PreferenceManager;
/**
 * @author lhb
 * 审核消息
 */
public class AuditMsgActivity extends BaseActivity {
    FriendApplyFragment friendApplyFragment;
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_audit_msg);
    }

    @Override
    protected void initLogic() {
        setTitle("新朋友");

        friendApplyFragment = new FriendApplyFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.container,
                friendApplyFragment).commit();

        PreferenceManager.getInstance().setParam(SP.APPLY_ADD_USER_NUM,0);

    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}
