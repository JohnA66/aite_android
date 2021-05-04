package com.haoniu.quchat.adapter;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.model.CreditInfo;
import com.zds.base.util.StringUtil;

import java.util.List;

/**
 * 积分兑换
 */
public class RedeemAdapter extends BaseQuickAdapter<CreditInfo, BaseViewHolder> {

    public RedeemAdapter(List<CreditInfo> list) {
        super(R.layout.adapter_redeem, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, CreditInfo item) {
        helper.setText(R.id.tv_show, StringUtil.getFormatValue(item.getMoney()) + mContext.getResources().getString(R.string.glod));
        helper.setText(R.id.tv_jifen, item.getCredit() + "积分");
    }


}