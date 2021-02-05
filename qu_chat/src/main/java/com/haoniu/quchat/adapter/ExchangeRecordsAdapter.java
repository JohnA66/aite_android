package com.haoniu.quchat.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.aite.chat.R;
import com.haoniu.quchat.model.CreditRecordsInfo;
import com.zds.base.util.StringUtil;

import java.util.List;

/**
 * 兑换记录
 */
public class ExchangeRecordsAdapter extends BaseQuickAdapter<CreditRecordsInfo, BaseViewHolder> {

    public ExchangeRecordsAdapter(List<CreditRecordsInfo> list) {
        super(R.layout.exchange_records_adapter, list);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, CreditRecordsInfo item) {
        helper.setText(R.id.name, "积分兑换");
        helper.setText(R.id.tv_money, StringUtil.getFormatValue(item.getMoney()) + mContext.getResources().getString(R.string.glod));
        helper.setText(R.id.tv_time, com.zds.base.util.StringUtil.formatDateMinute(item.getCreateTime()));
        helper.setText(R.id.tv_choushui, "积分" + item.getCredit());
    }
}