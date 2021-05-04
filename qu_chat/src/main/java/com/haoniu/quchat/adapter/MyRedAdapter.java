package com.haoniu.quchat.adapter;

import android.support.annotation.Nullable;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.model.MyRedInfo;

import java.util.List;

/**
 * @author lhb
 * 我的红包adapter
 */
public class MyRedAdapter extends BaseQuickAdapter<MyRedInfo.DataBean, BaseViewHolder> {

    private String type;

    public MyRedAdapter(@Nullable List<MyRedInfo.DataBean> data,String type) {
        super(R.layout.item_my_red, data);
        this.type=type;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyRedInfo.DataBean item) {


        helper.setText(R.id.tv_time, com.zds.base.util.StringUtil.formatDateMinute(item.getCreateTime()));


        if (type!=null&&type.equals("100")){
            helper.setText(R.id.tv_money, "-"+item.getMoney() );
        }else  if (type!=null&&type.equals("101")){
            helper.setText(R.id.tv_money, "+"+item.getMoney() );
        }else {
            helper.setText(R.id.tv_money, "-"+item.getMoney() );
        }


    }
}
