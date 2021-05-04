package com.haoniu.quchat.adapter;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.entity.RedWaterInfo;
import com.haoniu.quchat.utils.StringUtil;

import java.util.List;


/**
 * 红包流水
 *
 * @author lhb
 */
public class RedWaterAdapter extends BaseQuickAdapter<RedWaterInfo, BaseViewHolder> {

    public RedWaterAdapter(List<RedWaterInfo> list) {
        super(R.layout.item_red_record, list);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, RedWaterInfo item) {
        helper.setText(R.id.tv_title, item.getDescription());
        helper.setText(R.id.tv_time, StringUtil.formatDateMinute2(item.getUpdateTime()));
        helper.setText(R.id.tv_money, StringUtil.getFormatValue2(item.getMoney()));

    }
}