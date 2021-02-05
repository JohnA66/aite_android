package com.haoniu.quchat.activity.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.activity.FindDetailActivity;
import com.haoniu.quchat.entity.FindInfo;
import com.haoniu.quchat.entity.NewFindInfo;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.List;

public class FindFragmentNewAdapter extends BaseQuickAdapter<NewFindInfo.NewFindInfoItem, BaseViewHolder> {
    public FindFragmentNewAdapter(@Nullable List<NewFindInfo.NewFindInfoItem> data) {
        super(R.layout.adapter_find1, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewFindInfo.NewFindInfoItem item) {
        helper.setText(R.id.text, item.getName());
//        helper.setText(R.id.tv_content, item.getTitle());
//        helper.setText(R.id.tv_time, com.zds.base.util.StringUtil.formatDateMinute(item.getCreateTime()));
        GlideUtils.loadImageViewLoding(item.getPicUrl(), helper.getView(R.id.iv));
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(item.redirectUrl);
                intent.setData(content_url);
                mContext.startActivity(intent);
//                mContext.startActivity(new Intent(mContext, FindDetailActivity.class).putExtra("id", item.getNewsId()));
            }
        });

    }
}
