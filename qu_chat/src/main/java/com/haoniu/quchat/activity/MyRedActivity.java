package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.aite.chat.R;
import com.androidkun.xtablayout.XTabLayout;
import com.haoniu.quchat.activity.fragment.MyRedFragment;
import com.haoniu.quchat.adapter.FragmentAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author lhb
 * 我的红包
 */
public class MyRedActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.xTablayout)
    XTabLayout mXTablayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_my_red);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("我的红包");
        initViewPager();
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    private void initViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add("收到的红包");
        titles.add("发出的红包");
        // 100-发红包 101-领取红包
        fragments.add(MyRedFragment.newInstance("101"));
        fragments.add(MyRedFragment.newInstance("100"));

        mXTablayout.setxTabDisplayNum(3);
        FragmentAdapter mAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        //将TabLayout和ViewPager关联起来。
        mXTablayout.setupWithViewPager(mViewPager);
    }
}
