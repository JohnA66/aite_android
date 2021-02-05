package com.haoniu.quchat.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.aite.chat.R;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.entity.TransferRInfo;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.utils.ImageUtil;
import com.haoniu.quchat.widget.EaseImageView;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.List;

/**
 * 新——转账记录
 *
 * @author lhb
 */
public class NewTransferAdapter extends BaseQuickAdapter<TransferRInfo, BaseViewHolder> {

    public NewTransferAdapter(List<TransferRInfo> list) {
        super(R.layout.item_transfer_record, list);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, TransferRInfo item) {
        ImageUtil.setAvatar((EaseImageView) helper.getView(R.id.img_head));

        helper.setText(R.id.tv_time, com.haoniu.quchat.utils.StringUtil.formatDateMinute2(item.getCreateTime()));

        if (item.getTransferType() == 2) {
            helper.setText(R.id.tv_title, "转账-转账给" + item.getTransferNickName());
            helper.setTextColor(R.id.tv_money, mContext.getResources().getColor(R.color.black));
            helper.setText(R.id.tv_money, "-" + com.haoniu.quchat.utils.StringUtil.getFormatValue2(item.getMoney()));
        } else {
            helper.setText(R.id.tv_title, "转账-来自" + item.getTransferNickName());
            helper.setTextColor(R.id.tv_money, mContext.getResources().getColor(R.color.text_color_trans_record_gold));
            helper.setText(R.id.tv_money, "+" + com.haoniu.quchat.utils.StringUtil.getFormatValue2(item.getMoney()));
        }


        GlideUtils.loadImageViewLoding(AppConfig.ImageMainUrl + item.getHeadImg(), (EaseImageView) helper.getView(R.id.img_head), R.mipmap.img_default_avatar);

    }


}