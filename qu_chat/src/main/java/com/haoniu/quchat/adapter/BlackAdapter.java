package com.haoniu.quchat.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.activity.UserInfoDetailActivity;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.model.ContactInfo;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.List;

public class BlackAdapter extends BaseQuickAdapter<ContactInfo, BaseViewHolder> {
    public BlackAdapter(@Nullable List<ContactInfo> data) {
        super(R.layout.adapter_my_group, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactInfo item) {
        helper.setText(R.id.tv_group_name, item.getFriendNickName());
        GlideUtils.GlideLoadCircleErrorImageUtils(mContext, AppConfig.checkimg(item.getFriendUserHead()), helper.getView(R.id.img_group), R.mipmap.img_default_avatar);

        helper.setOnClickListener(R.id.ll_item, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, UserInfoDetailActivity.class)
                        .putExtra("friendUserId", item.getFriendUserId())
                        .putExtra("from", "3"));
            }
        });
    }
}
