package com.haoniu.quchat.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.aite.chat.R;
import com.haoniu.quchat.entity.BalancePaymentInfo;
import com.zds.base.util.StringUtil;

import java.util.List;

/**
 * 收支明细
 */
public class IncomeExpenditureDetailsAdapter extends BaseQuickAdapter<BalancePaymentInfo, BaseViewHolder> {

    public IncomeExpenditureDetailsAdapter(List<BalancePaymentInfo> list) {
        super(R.layout.adapter_income, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, BalancePaymentInfo item) {
        helper.setText(R.id.tv_time1,StringUtil.formatDate(item.getCreateTime()));
        helper.setText(R.id.tv_time2,StringUtil.formatDateMinute(item.getCreateTime()));
        if (item.getType()==0){
            helper.setImageResource(R.id.img_shouru,R.mipmap.img_expenditure);
            helper.setText(R.id.tv_name_type,"充值");
            helper.setText(R.id.tv_state,"");
            helper.setText(R.id.tv_reason,"");
            helper.setGone(R.id.tv_reason,false);
            helper.setText(R.id.tv_money,"+"+StringUtil.getFormatValue2(item.getMoney()));
        }else {
            helper.setText(R.id.tv_money,"-"+StringUtil.getFormatValue2(item.getMoney()));
            helper.setImageResource(R.id.img_shouru,R.mipmap.img_income);
            helper.setText(R.id.tv_name_type,"提现");
            helper.setText(R.id.tv_reason,"");
            helper.setGone(R.id.tv_reason,false);
            if (item.getStatus()==1){
                helper.setText(R.id.tv_state,"审核通过");
            }else if (item.getStatus()==2){
                helper.setText(R.id.tv_state,"失败");
                helper.setGone(R.id.tv_reason,true);
                helper.setText(R.id.tv_reason,item.getDescription());
            }else if (item.getStatus()==3){
                helper.setText(R.id.tv_state,"待审核");
            }
        }



    }

}