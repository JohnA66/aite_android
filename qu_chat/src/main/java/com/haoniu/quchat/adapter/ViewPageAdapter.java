package com.haoniu.quchat.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zzx on 2018/04/23/下午 4:33
 */

public class ViewPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> mListData;
    private String[] title;

    public ViewPageAdapter(FragmentManager fm, List<Fragment> mListData) {
        super(fm);
        this.mListData = mListData;
    }

    public ViewPageAdapter(FragmentManager fm, List<Fragment> mListData, String[] title) {
        super(fm);
        this.mListData = mListData;
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title != null && title.length > 0 ? title[position] : super.getPageTitle(position);
    }

}
