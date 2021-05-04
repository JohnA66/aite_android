package com.haoniu.quchat.adapter;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.model.UnGetRedInfo;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.List;

/**
 * 未领取红包adapter
 *
 * @author Administrator
 */
public class UngetRedAdapter extends BaseQuickAdapter<UnGetRedInfo.DataBean, BaseViewHolder> {

    public UngetRedAdapter(List<UnGetRedInfo.DataBean> list) {
        super(R.layout.adapter_un_red, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, UnGetRedInfo.DataBean info) {

        GlideUtils.GlideLoadCircleErrorImageUtils(mContext, info.getUserHead(), helper.getView(R.id.img_head), R.mipmap.img_default_avatar);

        helper.setText(R.id.tv_time, info.getCreateTime());
        helper.setText(R.id.tv_name, info.getUserNickName());

    }

}