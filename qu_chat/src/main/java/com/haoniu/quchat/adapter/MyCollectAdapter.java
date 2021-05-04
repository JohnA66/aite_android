package com.haoniu.quchat.adapter;

import android.view.View;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.entity.CollectInfo;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.utils.EaseSmileUtils;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.List;

public class MyCollectAdapter extends BaseMultiItemQuickAdapter<CollectInfo.DataBean, BaseViewHolder> {

    private OnDeleteCollectListener mDeleteCollectListener;

    public void setDeleteCollectListener(OnDeleteCollectListener mDeleteCollectListener) {
        this.mDeleteCollectListener = mDeleteCollectListener;
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MyCollectAdapter(List<CollectInfo.DataBean> data) {
        super(data);
        addItemType(1, R.layout.adapter_collect_text);
        addItemType(2, R.layout.adapter_collect_image);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectInfo.DataBean item) {
        switch (helper.getItemViewType()) {
            case 1:
                helper.setText(R.id.tv_collect, EaseSmileUtils.getSmiledText(mContext, item.getLinkContent()));
                helper.setText(R.id.tv_time, com.zds.base.util.StringUtil.formatDateMinute(item.getCreateTime()));

                if (mDeleteCollectListener != null) {
                    helper.setOnClickListener(R.id.tv_del, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDeleteCollectListener.delCollect(item.getCollectId());
                        }
                    });

                    helper.setOnClickListener(R.id.llay_small, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDeleteCollectListener.collect(item.getCollectId(),helper.getPosition());
                        }
                    });
                }
                break;
            case 2:
                GlideUtils.loadImageViewLoding(AppConfig.checkimg(item.getLinkContent()), helper.getView(R.id.img_collect));
                helper.setText(R.id.tv_time, com.zds.base.util.StringUtil.formatDateMinute(item.getCreateTime()));

                if (mDeleteCollectListener != null) {
                    helper.setOnClickListener(R.id.tv_del, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDeleteCollectListener.delCollect(item.getCollectId());
                        }
                    });

                    helper.setOnClickListener(R.id.llay_more, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDeleteCollectListener.collect(item.getCollectId(),helper.getPosition());
                        }
                    });
                }

                break;
            default:
                break;
        }
    }

    /**
     * 取消群管理员接口回调
     */
    public interface OnDeleteCollectListener {
        void delCollect(String collectId);
        void collect(String collectId,int position);
    }

}


