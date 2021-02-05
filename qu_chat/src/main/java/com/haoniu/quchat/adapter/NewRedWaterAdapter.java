package com.haoniu.quchat.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.aite.chat.R;
import com.haoniu.quchat.entity.NewRedRecordInfo;
import com.haoniu.quchat.utils.StringUtil;

import java.math.BigDecimal;
import java.util.List;


/**
 * 红包流水
 *
 * @author lhb
 */
public class NewRedWaterAdapter extends BaseQuickAdapter<NewRedRecordInfo.ListBean, BaseViewHolder> {

    public NewRedWaterAdapter(List<NewRedRecordInfo.ListBean> list) {
        super(R.layout.item_red_record, list);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, NewRedRecordInfo.ListBean item) {
        helper.setText(R.id.tv_title, item.getDescription());
        BigDecimal bd = new BigDecimal(item.getCreateTime());
        helper.setText(R.id.tv_time, StringUtil.formatDateMinute2(Long.valueOf(bd.toPlainString())));
        helper.setText(R.id.tv_money, StringUtil.getFormatValue2(item.getMoney()));

        if (String.valueOf(item.getMoney()).contains("-")) {
            helper.setTextColor(R.id.tv_money, mContext.getResources().getColor(R.color.btn_logout_normal));
        } else {
            helper.setTextColor(R.id.tv_money, mContext.getResources().getColor(R.color.text_color_red_record_green));
            helper.setText(R.id.tv_money, "+" + StringUtil.getFormatValue2(item.getMoney()));

        }

    }
}