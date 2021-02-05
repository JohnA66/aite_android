package com.haoniu.quchat.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.entity.CollectInfo;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.utils.StringUtil;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.List;

/**
 * @author lhb
 * 收藏
 */
public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnDeleteCollectListener mDeleteCollectListener;

    public void setDeleteCollectListener(OnDeleteCollectListener mDeleteCollectListener) {
        this.mDeleteCollectListener = mDeleteCollectListener;
    }

    private final int SelectTextView = 1;
    private final int SelectImg = 2;
    private final int SelectVideo = 3;
    private Context mContext;
    private List<CollectInfo.DataBean> dataBean;

    public CollectAdapter(Context mContext, List<CollectInfo.DataBean> dataBean) {
        this.mContext = mContext;
        this.dataBean = dataBean;
    }

    public void setData(List<CollectInfo.DataBean> dataBean) {
        this.dataBean = dataBean;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder myHolder = null;
        switch (i) {
            case SelectTextView:
                myHolder = new TextViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_collect_tv, viewGroup, false));
                break;
            case SelectImg:
                myHolder = new ImgHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_collect_img, viewGroup, false));
                break;
            case SelectVideo:
                myHolder = new VideoHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_collect_video, viewGroup, false));
                break;
            default:
                break;

        }
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()) {
            case SelectTextView:
                TextViewHolder textViewHolder = (TextViewHolder) viewHolder;
                CollectInfo.DataBean bean = dataBean.get(i);

                textViewHolder.mTvCollectChar.setText(bean.getLinkContent());
                textViewHolder.mTvCollectNameTime.setText(StringUtil.formatDateMinute(bean.getCreateTime()));
                if (mDeleteCollectListener != null) {
                    textViewHolder.mTvDel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDeleteCollectListener.delCollect(bean.getCollectId());
                        }
                    });
                }
                break;
            case SelectImg:
                ImgHolder imgHolder = (ImgHolder) viewHolder;
                CollectInfo.DataBean bean1 = dataBean.get(i);

                GlideUtils.loadImageViewLoding(AppConfig.checkimg(bean1.getLinkContent()), imgHolder.mIvCollectImg, R.mipmap.img_default_avatar);
                imgHolder.mTvCollectNameTimeImg.setText(StringUtil.formatDateMinute(bean1.getCreateTime()));
                if (mDeleteCollectListener != null) {
                    imgHolder.mTvDel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDeleteCollectListener.delCollect(bean1.getCollectId());
                        }
                    });
                }

                break;
            default:
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(dataBean.get(position).getLinkType());
    }

    @Override
    public int getItemCount() {
        return dataBean.size();
    }

    class TextViewHolder extends RecyclerView.ViewHolder {
        TextView mTvCollectChar;
        TextView mTvCollectNameTime;
        TextView mTvDel;


        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvCollectChar = itemView.findViewById(R.id.tv_collect_char);
            mTvCollectNameTime = itemView.findViewById(R.id.tv_collect_name_time);
            mTvDel = itemView.findViewById(R.id.tv_del);
        }

    }

    class ImgHolder extends RecyclerView.ViewHolder {
        TextView mTvCollectNameTimeImg;
        ImageView mIvCollectImg;
        TextView mTvDel;

        public ImgHolder(@NonNull View itemView) {
            super(itemView);
            mTvCollectNameTimeImg = itemView.findViewById(R.id.tv_collect_name_time_img);
            mIvCollectImg = itemView.findViewById(R.id.iv_collect_img);
            mTvDel = itemView.findViewById(R.id.tv_del);
        }
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        public VideoHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    /**
     * 取消群管理员接口回调
     */
    public interface OnDeleteCollectListener {
        void delCollect(String collectId);
    }
}
