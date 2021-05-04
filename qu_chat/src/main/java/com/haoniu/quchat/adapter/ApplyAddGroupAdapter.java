package com.haoniu.quchat.adapter;

import android.view.View;
import android.widget.ImageView;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.entity.ApplyListInfo;
import com.haoniu.quchat.http.AppConfig;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.List;

/**
 * 房间适配器
 */
public class ApplyAddGroupAdapter extends BaseQuickAdapter<ApplyListInfo, BaseViewHolder> {

    public ApplyAddGroupAdapter(List<ApplyListInfo> list) {
        super(R.layout.item_apply_add_group, list);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, ApplyListInfo item) {
        helper.setText(R.id.tv_nick_name, item.getApplyPerson() + "");
        helper.setText(R.id.tv_time, item.getApplyTime() + "");
        GlideUtils.loadImageViewLoding(AppConfig.checkimg(item.getHeadImg()), (ImageView) helper.getView(R.id.img_group), R.mipmap.img_default_avatar);
        helper.setOnClickListener(R.id.tv_apply_no_agree, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnClickAgreeOrListener) {
                    mOnClickAgreeOrListener.onClick(helper.getPosition(), 2);
                }
            }
        });

        helper.setOnClickListener(R.id.tv_apply_agree, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickAgreeOrListener.onClick(helper.getPosition(), 1);
            }
        });
    }


    private OnClickAgreeOrListener mOnClickAgreeOrListener;

    public interface OnClickAgreeOrListener {
        void onClick(int pos, int type);
    }

    public void setOnClickLisener(OnClickAgreeOrListener onClickLisener) {
        mOnClickAgreeOrListener = onClickLisener;
    }
}