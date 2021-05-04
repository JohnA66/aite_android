package com.haoniu.quchat.adapter;

import android.support.annotation.Nullable;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.entity.RechargeRecordInfo;
import com.haoniu.quchat.utils.StringUtil;

import java.util.List;

public class RechargeAdapter extends BaseQuickAdapter<RechargeRecordInfo.DataBean, BaseViewHolder> {
    public RechargeAdapter(@Nullable List<RechargeRecordInfo.DataBean> data) {
        super(R.layout.adapter_recharge_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeRecordInfo.DataBean item) {
        helper.setText(R.id.tv_time, com.zds.base.util.StringUtil.formatDateMinute(item.getCreateTime()));

        helper.setText(R.id.tv_money, item.getWithdrawMoney() + "");
        helper.setText(R.id.tv_recharge_type,"提现");
        helper.setVisible(R.id.ll_statue,false);
        // 0未审核 1 审核成功 2拒绝审核 3提现中 4提现成功 5提现失败
        if (!StringUtil.isEmpty(item.getWithdrawStatus())&&item.getWithdrawStatus().equals("0")){
            helper.setVisible(R.id.ll_statue,true);
        helper.setText(R.id.tv_state,"审核中");
        helper.setText(R.id.tv_state_message,"");
        helper.setVisible(R.id.tv_yhk,false);
        }else if (!StringUtil.isEmpty(item.getWithdrawStatus())&&item.getWithdrawStatus().equals("1")){
            helper.setVisible(R.id.ll_statue,true);
            helper.setText(R.id.tv_state,"审核通过");
            helper.setText(R.id.tv_state_message,"");
            helper.setVisible(R.id.tv_yhk,true);
        }else if (!StringUtil.isEmpty(item.getWithdrawStatus())&&item.getWithdrawStatus().equals("2")){
            helper.setVisible(R.id.ll_statue,true);
            helper.setText(R.id.tv_state,"审核未通过");
            helper.setText(R.id.tv_state_message,item.getReason());
            helper.setVisible(R.id.tv_yhk,false);
        }else if (!StringUtil.isEmpty(item.getWithdrawStatus())&&item.getWithdrawStatus().equals("3")){
            helper.setVisible(R.id.ll_statue,true);
            helper.setText(R.id.tv_state,"提现中");
            helper.setText(R.id.tv_state_message,"");
            helper.setVisible(R.id.tv_yhk,false);
        }else if (!StringUtil.isEmpty(item.getWithdrawStatus())&&item.getWithdrawStatus().equals("4")){
            helper.setVisible(R.id.ll_statue,true);
            helper.setText(R.id.tv_state,"提现成功");
            helper.setText(R.id.tv_state_message,"");
            helper.setVisible(R.id.tv_yhk,false);
        }else if (!StringUtil.isEmpty(item.getWithdrawStatus())&&item.getWithdrawStatus().equals("5")){
            helper.setVisible(R.id.ll_statue,true);
            helper.setText(R.id.tv_state,"提现失败");
            helper.setText(R.id.tv_state_message,"");
            helper.setVisible(R.id.tv_yhk,false);
        }
        helper.addOnClickListener(R.id.tv_yhk);

    }
}
