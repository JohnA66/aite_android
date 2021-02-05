package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.aite.chat.R;
import com.haoniu.quchat.activity.fragment.ShareCardIdContactListFragment;
import com.haoniu.quchat.activity.fragment.ShareCardIdConversationListFragment;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lhb
 * 分享名片
 */
public class ShareCardIdActivity extends BaseActivity {

    @BindView(R.id.segmenttab)
    SegmentTabLayout mSegmentTab;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    private Bundle mBundle;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"会话", "联系人"};

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_share_card_id);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("会话");
        mSegmentTab.setIndicatorCornerRadius(0);
        mFragments.add(ShareCardIdConversationListFragment.newInstance(mBundle));
        mFragments.add(ShareCardIdContactListFragment.newInstance(mBundle));
        mSegmentTab.setTabData(mTitles);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        mSegmentTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
                if (position == 1) {
                    mToolbarTitle.setText("联系人");
                } else if (position == 0) {
                    mToolbarTitle.setText("会话");
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mSegmentTab.setCurrentTab(position);
                if (position == 1) {
                    mToolbarTitle.setText("联系人");
                } else if (position == 0) {
                    mToolbarTitle.setText("会话");
                }
                //切换传值 类型 1 垫付任务 2收藏任务
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        mBundle = extras;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
