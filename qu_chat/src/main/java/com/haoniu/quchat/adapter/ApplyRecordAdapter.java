package com.haoniu.quchat.adapter;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.entity.ApplyRecordInfo;

import java.util.List;

/**
 * 我的申请列表
 */
public class ApplyRecordAdapter extends BaseQuickAdapter<ApplyRecordInfo, BaseViewHolder> {

    public ApplyRecordAdapter(List<ApplyRecordInfo> list) {
        super(R.layout.adapter_apply_record, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApplyRecordInfo item) {
        helper.setText(R.id.tv_time, "申请时间：" + item.getCreateTime());
        helper.setText(R.id.tv_type, "申请级别：" + (item.getApplyProxy() == 1 ? "一" : "二") + "级代理");
        // 审核通过
        if (item.getStatus() == Constant.AUDITSTATEPASSED) {
            helper.setText(R.id.tv_state, "审核通过");
            helper.setGone(R.id.tv_content, false);
            //拒绝
        } else if (item.getStatus() == Constant.AUDITSTATEBEREJECTED) {
            helper.setText(R.id.tv_state, "被拒绝");
            helper.setGone(R.id.tv_content, true);
            //审核中
        } else {
            helper.setText(R.id.tv_state, "申请中");
            helper.setGone(R.id.tv_content, false);
        }
        helper.setText(R.id.tv_content, "拒绝理由：" + item.getContent());
    }

}