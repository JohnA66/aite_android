package com.haoniu.quchat.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.aite.chat.R;
import com.haoniu.quchat.model.MoneyIncomeInfo;

import java.util.List;

/**
 * 代理收益
 */
public class AgentAdapter extends BaseQuickAdapter<MoneyIncomeInfo, BaseViewHolder> {

    public AgentAdapter(List<MoneyIncomeInfo> list) {
        super(R.layout.adapter_agent_income, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, MoneyIncomeInfo item) {
        helper.setText(R.id.tv_name, (helper.getPosition()) + "." + item.getNameMessage() + item.getMoney());
    }

}