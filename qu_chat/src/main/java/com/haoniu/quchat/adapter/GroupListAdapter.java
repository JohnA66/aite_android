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
package com.haoniu.quchat.adapter;

import android.view.LayoutInflater;
import android.view.View;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.entity.GroupListInfo;

import java.util.List;

/**
 * 群主收益 群列表
 *
 * @author lhb
 */
public class GroupListAdapter extends BaseQuickAdapter<GroupListInfo.ListBean, BaseViewHolder> {

    private LayoutInflater inflater;
    private OnSelGroupListener mOnSelGroupListener;

    public GroupListAdapter(List<GroupListInfo.ListBean> groups) {
        super(R.layout.item_list_group, groups);
    }

    public void setOnSelGroupListener(OnSelGroupListener onClickListener) {
        this.mOnSelGroupListener = onClickListener;
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, GroupListInfo.ListBean item) {
        helper.setText(R.id.tv_group_name, item.getName());

        helper.setOnClickListener(R.id.tv_group_name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnSelGroupListener) {
                    mOnSelGroupListener.info(helper.getPosition());
                }
            }
        });
    }

    public interface OnSelGroupListener {
        void info(int pos);
    }


}
