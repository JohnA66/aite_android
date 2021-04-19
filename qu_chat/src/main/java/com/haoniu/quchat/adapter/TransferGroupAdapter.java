package com.haoniu.quchat.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.entity.GroupDetailInfo;
import com.haoniu.quchat.http.AppConfig;
import com.zds.base.ImageLoad.GlideUtils;

public class TransferGroupAdapter extends BaseQuickAdapter<GroupDetailInfo.GroupUserDetailVoListBean, BaseViewHolder> {

    public TransferGroupAdapter() {
        super(R.layout.item_transfer_group);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupDetailInfo.GroupUserDetailVoListBean item) {
        ImageView img_head = helper.getView(R.id.img_head);
        GlideUtils.GlideLoadCircleErrorImageUtils(mContext,
                AppConfig.checkimg(item.getUserHead()), img_head,
                R.mipmap.img_default_avatar);
        helper.setText(R.id.tv_name,item.getUserNickName());
        TextView tv_manage = helper.getView(R.id.tv_manage);

        if (item.getUserRank().equals("1")) {
            helper.setText(R.id.tv_manage,"管理员");
            tv_manage.setVisibility(View.VISIBLE);
        } else if (item.getUserRank().equals("2")) {
            helper.setText(R.id.tv_manage,"群主");
            tv_manage.setVisibility(View.VISIBLE);
        } else {
            tv_manage.setVisibility(View.GONE);
        }

    }
}
