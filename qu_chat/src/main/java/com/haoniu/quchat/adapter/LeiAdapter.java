package com.haoniu.quchat.adapter;

import android.support.v4.content.ContextCompat;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置雷点
 */
public class LeiAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    List<String> list = new ArrayList<>();

    public LeiAdapter(List<String> list) {
        super(R.layout.adapter_lei, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, helper.getAdapterPosition() + "");
        if (list.contains(helper.getAdapterPosition() + "")) {
            helper.setTextColor(R.id.tv_name, ContextCompat.getColor(mContext, R.color.white));
            helper.setBackgroundRes(R.id.tv_name, R.drawable.bor_lei_red_6);
        } else {
            helper.setBackgroundRes(R.id.tv_name, R.drawable.bor_lei_gray_6);
            helper.setTextColor(R.id.tv_name, ContextCompat.getColor(mContext, R.color.lei_color));
        }
    }

    public void setClick(int position) {
        if (list.contains(position + "")) {
            list.remove(position + "");
        } else {
            if (list.size() < 9) {
                list.add(position + "");
            }
        }
        notifyDataSetChanged();
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}