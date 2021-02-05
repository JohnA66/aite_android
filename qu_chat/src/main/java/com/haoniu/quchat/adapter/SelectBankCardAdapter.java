package com.haoniu.quchat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.entity.JsonBankCardList;

import java.util.List;

/**
 * @author by created chen cloudy 2018/10/22 16:22
 **/

public class SelectBankCardAdapter extends RecyclerView.Adapter<SelectBankCardAdapter.MyViewHolder>{
    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener {
        /**
         * 单击的点击事件
         * @param view 画层
         * @param position 点击的条目
         */
        void onItemClick(View view, int position);

        /**
         * 长按的点击事件
         * @param view 画层
         * @param position 点击的条目
         */
        void onItemLongClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener=mOnItemClickListener;
    }
    private List<JsonBankCardList.DataBean> dataBean;
    private Context mContext;
    private int mSelectCardIndex = 0;
    public SelectBankCardAdapter(Context mContext, List<JsonBankCardList.DataBean> dataBean,int mSelectCardIndex){
        this.mContext=mContext;
        this.dataBean =dataBean;
        this.mSelectCardIndex = mSelectCardIndex;
    }
    public void removeData(int position){
        dataBean.remove(position);
        notifyItemRemoved(position);
    }
    @NonNull
    @Override
    public SelectBankCardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SelectBankCardAdapter.MyViewHolder holder =new SelectBankCardAdapter.MyViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_select_bank_card_recycler,parent,false));
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull final SelectBankCardAdapter.MyViewHolder holder, int position) {
        holder.mTvClassify.setText(dataBean.get(position).getBankName()+" "+dataBean.get(position).getBankCard());
        if(mSelectCardIndex == position){
            holder.mCheckbox.setChecked(true);
        } else {
            holder.mCheckbox.setChecked(false);
        }
        if(mOnItemClickListener!=null){
            holder.cl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos= holder.getLayoutPosition();
                    mSelectCardIndex = position;
                    notifyDataSetChanged();
                    mOnItemClickListener.onItemClick(holder.cl,pos);
                }
            });
            holder.cl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos=holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.cl,pos);
                    return false;
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return dataBean.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mLogo;
        TextView mTvClassify;
        CheckBox mCheckbox;
        ConstraintLayout cl;
        public MyViewHolder(View view){
            super(view);
            mLogo = view.findViewById(R.id.iv_bank_image_logo);
            mTvClassify = view.findViewById(R.id.tv_bank_classify);
            mCheckbox = view.findViewById(R.id.checkbox);
            cl = view.findViewById(R.id.cl_item_card);
        }
    }
}
