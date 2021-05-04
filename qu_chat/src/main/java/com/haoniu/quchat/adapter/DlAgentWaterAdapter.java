package com.haoniu.quchat.adapter;


import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.entity.GroupOwnerIncomeInfo;
import com.zds.base.util.StringUtil;

import java.util.List;

/**
 * 代理收益流水
 *
 * @author Administrator
 */
public class DlAgentWaterAdapter extends BaseQuickAdapter<GroupOwnerIncomeInfo.RedBean, BaseViewHolder> {

    public DlAgentWaterAdapter(List<GroupOwnerIncomeInfo.RedBean> list) {
        super(R.layout.adapter_dl, list);
    }


    @Override
    protected void convert(BaseViewHolder helper, GroupOwnerIncomeInfo.RedBean item) {
        if (helper.getAdapterPosition() == 0) {
            helper.setGone(R.id.ll_tiltes, true);
        } else {
            helper.setGone(R.id.ll_tiltes, false);
        }
        //类型
        if (item.getType().equals("0")) {
            helper.setText(R.id.tv_nick, "免死抢包");
        } else if (item.getType().equals("1")) {
            helper.setText(R.id.tv_nick, "发包");
        } else if (item.getType().equals("3")) {
            helper.setText(R.id.tv_nick, "福利包群主扣除");
        } else if (item.getType().equals("4")) {
            helper.setText(R.id.tv_nick, "收到雷包");
        }
        //金额
        helper.setText(R.id.tv_money, StringUtil.getFormatValue2(item.getFreeRed()));
        //服务费
        helper.setText(R.id.tv_lv, StringUtil.getFormatValue4(item.getPtRed()));
        //时间
        helper.setText(R.id.tv_time, item.getDate());

    }

}