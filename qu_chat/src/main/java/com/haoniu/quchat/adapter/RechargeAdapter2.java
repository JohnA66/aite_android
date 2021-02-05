package com.haoniu.quchat.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.aite.chat.R;
import com.haoniu.quchat.entity.RechargeRecordInfo;
import com.zds.base.util.StringUtil;

import java.util.List;

public class RechargeAdapter2 extends BaseQuickAdapter<RechargeRecordInfo.DataBean, BaseViewHolder> {
    public RechargeAdapter2(@Nullable List<RechargeRecordInfo.DataBean> data) {
        super(R.layout.adapter_recharge_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeRecordInfo.DataBean item) {
        helper.setText(R.id.tv_time, StringUtil.formatDateMinute(item.getCreateTime()));
        helper.setText(R.id.tv_money, item.getRechargeMoney() + "");
    }
}
