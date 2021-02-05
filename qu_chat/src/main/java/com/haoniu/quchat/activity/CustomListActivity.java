package com.haoniu.quchat.activity;

import android.os.Bundle;

import com.aite.chat.R;
import com.haoniu.quchat.activity.fragment.CustomServiceFragment;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;

/**
 * @author lhb
 * 客服列表
 */
public class CustomListActivity extends BaseActivity {
    private CustomServiceFragment mCustomServiceFragment;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_chat);

    }

    @Override
    protected void initLogic() {
        mCustomServiceFragment = new CustomServiceFragment();
        //pass parameters to chat fragment
        getSupportFragmentManager().beginTransaction().add(R.id.container, mCustomServiceFragment).commit();
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}
