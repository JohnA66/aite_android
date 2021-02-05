package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.aite.chat.R;
import com.haoniu.quchat.activity.fragment.GroupUserApplyFragment;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.constant.SP;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.utils.PreferenceManager;

public class ApplyJoinGroupActivity extends BaseActivity {
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_audit_msg);
    }

    @Override
    protected void initLogic() {
        setTitle("群通知");
        getSupportFragmentManager().beginTransaction().add(R.id.container,
                new GroupUserApplyFragment()).commit();
        PreferenceManager.getInstance().setParam(SP.APPLY_JOIN_GROUP_NUM,0);
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    private void addFragment(FragmentManager fm, Fragment fragment, String tag) {
        if (!fragment.isAdded()&&null == fm.findFragmentByTag( tag )) {
            FragmentTransaction ft = fm.beginTransaction();
            fm.executePendingTransactions();
            ft.add( R.id.container, fragment, tag );
            ft.commitAllowingStateLoss();
        }
    }
}
