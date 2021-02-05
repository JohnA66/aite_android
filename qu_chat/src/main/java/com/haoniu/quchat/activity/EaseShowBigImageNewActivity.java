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

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.aite.chat.R;
import com.haoniu.quchat.EaseShowBigImageNewItem;
import com.haoniu.quchat.adapter.FragmentAdapter;
import com.haoniu.quchat.base.EaseBaseActivity;
import com.haoniu.quchat.entity.EventCenter;

import java.util.ArrayList;
import java.util.List;

/**
 * download and show original image
 *
 */
public class EaseShowBigImageNewActivity extends EaseBaseActivity {

    private static final String TAG = "EaseShowBigImageNewActivity";
    private ViewPager mBigShowVp;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.ease_activity_show_big_image_new);
        isTransparency(true);
        super.onCreate(savedInstanceState);

        mBigShowVp = findViewById(R.id.ac_easy_big_image_vp);
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        ArrayList<EaseShowBigImageNewItem> images = getIntent().getParcelableArrayListExtra("images");
        int position = getIntent().getIntExtra("position", 0);
        for (EaseShowBigImageNewItem item : images) {
            Log.e("image item ", "url=" + item.toString());
            fragments.add(EaseShowBigImageNewFragment.newInstance(item));
            titles.add("");
        }
        mBigShowVp.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments, titles));
        mBigShowVp.setOnClickListener(v -> finish());
        if (position != 0)
            mBigShowVp.setCurrentItem(position);
    }

    /**
     * 替代onCreate的使用
     *
     * @param bundle
     */
    @Override
    protected void initContentView(Bundle bundle) {

    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {

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
}
