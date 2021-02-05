package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;

import butterknife.BindView;

/**
 * @author lhb
 * 红包记录
 */
public class RedRecordActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rv_red_record)
    RecyclerView mRvRedRecord;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_red_record);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("红包记录");
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

}
