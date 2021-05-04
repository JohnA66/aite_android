package com.haoniu.quchat.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.activity.FindDetailActivity;
import com.haoniu.quchat.entity.FindInfo;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.List;

/**
 * @author lhb
 * 发现
 */
public class FindAdapter extends BaseQuickAdapter<FindInfo.DataBean, BaseViewHolder> {
    public FindAdapter(@Nullable List<FindInfo.DataBean> data) {
        super(R.layout.adapter_find, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FindInfo.DataBean item) {
        helper.setText(R.id.tv_title, item.getPushUser());
        helper.setText(R.id.tv_content, item.getTitle());
        helper.setText(R.id.tv_time, com.zds.base.util.StringUtil.formatDateMinute(item.getCreateTime()));
        GlideUtils.loadImageViewLoding(item.getCoverImage(), helper.getView(R.id.img));
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, FindDetailActivity.class).putExtra("id", item.getNewsId()));
            }
        });

    }
}
