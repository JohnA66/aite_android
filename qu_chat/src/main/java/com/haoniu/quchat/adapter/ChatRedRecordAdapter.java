package com.haoniu.quchat.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.aite.chat.R;
import com.haoniu.quchat.model.ChatRedRecordInfo;
import com.haoniu.quchat.model.MyRedInfo;
import com.haoniu.quchat.utils.StringUtil;

import java.util.List;

/**
 * @author lhb
 * 我的红包记录adapter
 */
public class ChatRedRecordAdapter extends BaseQuickAdapter<MyRedInfo.DataBean
        , BaseViewHolder> {

    public ChatRedRecordAdapter(@Nullable List<MyRedInfo.DataBean> data) {
        super(R.layout.adapter_red_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyRedInfo.DataBean item) {
        try {
            helper.setText(R.id.tv_time,
                    com.zds.base.util.StringUtil.formatTime(item.getCreateTime(),
                            ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //type 100-发红包 101-领取红包
        switch (item.getType()) {
            case 101:
                helper.setText(R.id.tv_money,
                        "+" + StringUtil.getFormatValue2(item.getMoney()));
                helper.setText(R.id.tv_red, "抢红包收益");
                helper.setTextColor(R.id.tv_money,
                        ContextCompat.getColor(mContext, R.color.blue5));
                break;
            case 100:
                helper.setText(R.id.tv_money,
                        "-" + StringUtil.getFormatValue2(item.getMoney()));
                helper.setText(R.id.tv_red, "发红包");
                helper.setTextColor(R.id.tv_money,
                        ContextCompat.getColor(mContext,
                                R.color.text_color_red));

                break;
            case 102:
                helper.setText(R.id.tv_money,
                        "+" + StringUtil.getFormatValue2(item.getMoney()));
                helper.setText(R.id.tv_red, "红包退回");
                helper.setTextColor(R.id.tv_money,
                        ContextCompat.getColor(mContext, R.color.blue5));
                break;
        }

    }
}
