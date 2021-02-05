package com.haoniu.quchat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.chat.R;

import java.util.ArrayList;

/**
 * @author by created chen cloudy 2018/10/22 16:22
 **/

public class SelectMoneyAdapter extends RecyclerView.Adapter<SelectMoneyAdapter.MyViewHolder> {
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

    private ArrayList<String> mMoneyList;
    private Context mContext;
    private int mSelectMoneyIndex = 0;

    public SelectMoneyAdapter(Context mContext, ArrayList<String> mClassifyList, int mSelectMoneyIndex) {
        this.mSelectMoneyIndex = mSelectMoneyIndex;
        this.mContext = mContext;
        this.mMoneyList = mClassifyList;
    }

    public void removeData(int position) {
        mMoneyList.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public SelectMoneyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SelectMoneyAdapter.MyViewHolder holder = new SelectMoneyAdapter.MyViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_select_money_recycler, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectMoneyAdapter.MyViewHolder holder, int position) {
        holder.mTvSelectMoney.setText(mMoneyList.get(position));
        if (mSelectMoneyIndex == position) {
            holder.ll.setBackgroundResource(R.drawable.shape_selected_money_bg);
            holder.mTvSelectMoney.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            holder.ll.setBackgroundResource(R.drawable.shape_select_money_bg);
            holder.mTvSelectMoney.setTextColor(ContextCompat.getColor(mContext, R.color.blue5));
        }

        if (mOnItemClickListener != null) {
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mSelectMoneyIndex = position;
                    notifyDataSetChanged();
                    mOnItemClickListener.onItemClick(holder.ll, pos);
                }
            });
            holder.ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.ll, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mMoneyList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTvSelectMoney;
        LinearLayout ll;

        public MyViewHolder(View view) {
            super(view);
            mTvSelectMoney = view.findViewById(R.id.tv_select_money);
            ll = view.findViewById(R.id.ll_item_money);
        }
    }
}
