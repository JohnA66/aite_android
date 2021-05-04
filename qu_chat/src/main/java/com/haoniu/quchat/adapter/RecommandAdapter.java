package com.haoniu.quchat.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.activity.FindDetailActivity;
import com.haoniu.quchat.model.RecommandInfo;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.List;

/**
 * @author lhb
 * 发现详情推荐
 */
public class RecommandAdapter extends BaseQuickAdapter<RecommandInfo, BaseViewHolder> {
    public RecommandAdapter(@Nullable List<RecommandInfo> data) {
        super(R.layout.adapter_recommand, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommandInfo item) {
        //来源
        helper.setText(R.id.tv_origion, item.getPushUser());
        helper.setText(R.id.tv_title, item.getTitle());
        GlideUtils.loadImageViewLoding(item.getCoverImage(), helper.getView(R.id.img));

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, FindDetailActivity.class).putExtra("id", item.getNewsId()));
            }
        });
    }
}
