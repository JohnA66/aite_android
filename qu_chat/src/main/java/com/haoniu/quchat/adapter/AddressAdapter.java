package com.haoniu.quchat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.chat.R;

import java.util.ArrayList;

/**
 * @author by created chen cloudy 2018/10/22 16:22
 **/

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {
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

    private ArrayList<String> address;
    private ArrayList<String> AddDetail;
    private Context mContext;
    private int mSelectIndex = 0;

    public AddressAdapter(Context mContext, ArrayList<String> address, ArrayList<String> AddDetail, int mSelectIndex) {
        this.mContext = mContext;
        this.AddDetail = AddDetail;
        this.mSelectIndex = mSelectIndex;
        this.address = address;
    }

    public void removeData(int position) {
        AddDetail.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public AddressAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AddressAdapter.MyViewHolder holder = new AddressAdapter.MyViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_add_recycler, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AddressAdapter.MyViewHolder holder, int position) {
        holder.mTvAddress.setText(address.get(position));
        holder.mTvAddressDetail.setText(AddDetail.get(position));
        if(position == mSelectIndex){
            holder.mIvSelect.setVisibility(View.VISIBLE);
        }else{
            holder.mIvSelect.setVisibility(View.GONE);
        }
        if (mOnItemClickListener != null) {
            holder.cl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mSelectIndex = pos;
                    mOnItemClickListener.onItemClick(holder.cl, pos);
                    notifyDataSetChanged();
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
        }
    }

    @Override
    public int getItemCount() {
        return address.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mIvSelect;
        TextView mTvAddress;
        TextView mTvAddressDetail;
        ConstraintLayout cl;

        public MyViewHolder(View view) {
            super(view);
            mIvSelect = view.findViewById(R.id.iv_select);
            mTvAddress = view.findViewById(R.id.tv_address);
            mTvAddressDetail = view.findViewById(R.id.tv_address_detail);
            cl = view.findViewById(R.id.cl_item_add);
        }
    }
}
