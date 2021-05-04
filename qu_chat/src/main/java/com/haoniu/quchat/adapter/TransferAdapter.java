package com.haoniu.quchat.adapter;

import android.view.View;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.entity.TransferRecordInfo;
import com.zds.base.util.StringUtil;

import java.util.List;

/**
 * 转账记录
 *
 * @author lhb
 */
public class TransferAdapter extends BaseQuickAdapter<TransferRecordInfo, BaseViewHolder> {

    public TransferAdapter(List<TransferRecordInfo> list) {
        super(R.layout.adapter_transfer_record, list);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, TransferRecordInfo item) {
        //transferType 1:转出， 2：收入tv_tag
        if (item.getTransferType().equals("1")) {
            helper.setText(R.id.tv_name, "转账给" + item.getNickName());
            helper.setText(R.id.tv_money, "" +StringUtil.getFormatValue2(item.getMoney()));
        } else {
            helper.setText(R.id.tv_name, "收入来自" + item.getNickName());
            helper.setText(R.id.tv_money, "+" + StringUtil.getFormatValue2(item.getMoney()));
        }

//        helper.setText(R.id.tv_time, StringUtil.formatDateMinute(item.getCreateTime()));
        helper.setText(R.id.tv_time, com.zds.base.util.StringUtil.formatDateMinute(item.getCreateTime()));
        if (item.getSureStatus()==2){
            helper.getView(R.id.tv_tag).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.tv_tag).setVisibility(View.GONE);
        }
    }



}