package com.haoniu.quchat.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.entity.JsonBankCardList;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.widget.EaseAlertDialog;
import com.zds.base.Toast.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by created chen cloudy 2018/10/22 16:22
 **/

public class BankCardAdapter extends RecyclerView.Adapter<BankCardAdapter.MyViewHolder> {
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        /**
         * 单击的点击事件
         *
         * @param view     画层
         * @param position 点击的条目
         */
        void onItemClick(View view, int position);

        /**
         * 长按的点击事件
         *
         * @param view     画层
         * @param position 点击的条目
         */
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private List<JsonBankCardList.DataBean> dataBean;
    private Context mContext;

    public void setData(List<JsonBankCardList.DataBean> dataBean) {
        this.dataBean = dataBean;
    }

    public BankCardAdapter(Context mContext, List<JsonBankCardList.DataBean> dataBean) {
        this.mContext = mContext;
        this.dataBean = dataBean;
    }

    public void removeData(int position) {
        dataBean.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public BankCardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BankCardAdapter.MyViewHolder holder = new BankCardAdapter.MyViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_bank_card_recycler, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BankCardAdapter.MyViewHolder holder, int position) {
        holder.mTvClassify.setText(dataBean.get(position).getBankName());
        holder.mTvCardNumber.setText(dataBean.get(position).getBankCard());
        if (mOnItemClickListener != null) {
            holder.cl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.cl, pos);
                }
            });
            holder.cl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.cl, pos);
                    return false;
                }
            });
            holder.mIvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new EaseAlertDialog(mContext, "确认删除该银行卡？", null, "删除", new EaseAlertDialog.AlertDialogUser() {
                        @Override
                        public void onResult(boolean confirmed, Bundle bundle) {
                            if (confirmed) {
                                int pos = holder.getLayoutPosition();
                                Map<String, Object> map = new HashMap<>();
                                map.put("cardId", dataBean.get(position).getCardId());
                                ApiClient.requestNetHandle(mContext, AppConfig.removeBankCardList, "", map, new ResultListener() {
                                    @Override
                                    public void onSuccess(String json, String msg) {
                                        ToastUtil.toast(msg);
                                        mOnItemClickListener.onItemClick(holder.mIvDelete, pos);
                                    }

                                    @Override
                                    public void onFailure(String msg) {
                                        ToastUtil.toast(msg);
                                    }
                                });
                            }
                        }
                    }).show();


                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataBean.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mLogo;
        ImageView mIvDelete;
        TextView mTvClassify;
        TextView mTvCardType;
        TextView mTvCardNumber;
        ConstraintLayout cl;

        public MyViewHolder(View view) {
            super(view);
            mLogo = view.findViewById(R.id.iv_bank_image_logo);
            mTvClassify = view.findViewById(R.id.tv_bank_classify);
            mTvCardType = view.findViewById(R.id.tv_bank_card_type);
            mTvCardNumber = view.findViewById(R.id.tv_bank_card_number);
            mIvDelete = view.findViewById(R.id.iv_delete);
            cl = view.findViewById(R.id.cl_item_card);
        }
    }
}
