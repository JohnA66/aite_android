package com.haoniu.quchat.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.aite.chat.R;
import com.haoniu.quchat.entity.ChongzhiInfo;
import com.haoniu.quchat.entity.RedWaterInfo;
import com.haoniu.quchat.entity.TiXianInfo;
import com.haoniu.quchat.entity.TransferRInfo;
import com.zds.base.util.StringUtil;

import java.util.List;

/**
 * 流水记录
 */

public class FundAecordAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public FundAecordAdapter(List<T> list) {
        super(R.layout.item_fund_aecord, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        helper.setGone(R.id.tv_nick,false);
        //提现
        if (item instanceof TiXianInfo) {
            TiXianInfo info = (TiXianInfo) item;
            helper.setText(R.id.tv_time, com.haoniu.quchat.utils.StringUtil.formatDateMinute2(info.getCreateTime()));
                helper.setText(R.id.tv_money,  StringUtil.getFormatValue2(info.getMoney()));
                if (info.getStatus() == 1) {
                    helper.setText(R.id.tv_state, "审核通过");
                } else if (info.getStatus() == 2) {
                    helper.setText(R.id.tv_state, "审核失败");
                } else if (info.getStatus() == 0) {
                    helper.setText(R.id.tv_state, "待审核");
                }
                //充值
        } else if (item instanceof ChongzhiInfo){
            ChongzhiInfo info = (ChongzhiInfo) item;
            helper.setText(R.id.tv_time, com.haoniu.quchat.utils.StringUtil.formatDateMinute2(info.getCreateTime()));
                helper.setText(R.id.tv_state, "充值");
                helper.setText(R.id.tv_money, "+" + StringUtil.getFormatValue2(info.getMoney()));
        //转账
        }else if (item instanceof TransferRInfo) {
            TransferRInfo info = (TransferRInfo) item;
            helper.setGone(R.id.tv_nick,true);
            helper.setText(R.id.tv_nick,info.getTransferNickName());
            helper.setText(R.id.tv_time, com.haoniu.quchat.utils.StringUtil.formatDateMinute2(info.getCreateTime()));
            if (info.getTransferType() == 1) {
                helper.setText(R.id.tv_state, "转入");
                helper.setText(R.id.tv_money, "+" + StringUtil.getFormatValue2(info.getMoney()) + mContext.getResources().getString(R.string.glod));
            } else {
                helper.setText(R.id.tv_state, "转出");
                helper.setText(R.id.tv_money, "-" + StringUtil.getFormatValue2(info.getMoney()) + mContext.getResources().getString(R.string.glod));
            }
            //红包流水
        } else if (item instanceof RedWaterInfo) {
            RedWaterInfo info = (RedWaterInfo) item;
            helper.setText(R.id.tv_state, info.getDescription());
            helper.setText(R.id.tv_money, StringUtil.getFormatValue2(info.getMoney()));
            helper.setText(R.id.tv_time, com.haoniu.quchat.utils.StringUtil.formatDateMinute2(info.getCreateTime()));
        }
    }
}
