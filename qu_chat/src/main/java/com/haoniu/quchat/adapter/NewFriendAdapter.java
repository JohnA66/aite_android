package com.haoniu.quchat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.activity.AuditUserActivity;
import com.haoniu.quchat.activity.UserInfoDetailActivity;
import com.haoniu.quchat.entity.ApplyFriendData;
import com.haoniu.quchat.http.AppConfig;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 新的朋友adapter
 *
 * @author lhb
 */
public class NewFriendAdapter extends RecyclerView.Adapter<NewFriendAdapter.ViewHolder> implements Filterable {

    private List<ApplyFriendData> mInfoList = new ArrayList<>();
    private List<ApplyFriendData> copyUserList;

    private Context mContext;
    private HashMap<String, Integer> lettes = new HashMap<>();
    private MyFilter myFilter;

    private OnAgreeListener mOnAgreeListener;
    private OnDelClickListener mOnDelClickListener;

    public void setOnAgreeListener(OnAgreeListener onAgreeListener) {
        mOnAgreeListener = onAgreeListener;
    }

    public NewFriendAdapter(List<ApplyFriendData> infoList, Context context, HashMap<String, Integer> lettes) {
        this.mInfoList = infoList;
        if (copyUserList == null) {
            //只取值一次，用于无检索条件时， 返回全部数据
            this.copyUserList = infoList;
        }
        mContext = context;
        this.lettes = lettes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_new_friend, parent, false), false);
            case 1:
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_new_friend, parent, false), true);
            default:

                return null;
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ApplyFriendData info = mInfoList.get(position);
        holder.mTvName.setText(info.getUserNickName());

        holder.mTvTop.setText(info.getTopName());
        GlideUtils.GlideLoadCircleErrorImageUtils(mContext, AppConfig.checkimg(info.getUserHead()), holder.imgHead, R.mipmap.img_default_avatar);

        if (info.getApplyStatus().equals("0")) {
            holder.mTvAgree.setBackgroundResource(R.drawable.agree_friend_bg_selector);
            holder.mTvAgree.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            holder.mTvAgree.setText("通过");

        } else if (info.getApplyStatus().equals("1")) {
            holder.mTvAgree.setBackgroundResource(R.drawable.bor_white_8);
            holder.mTvAgree.setTextColor(ContextCompat.getColor(mContext, R.color.grey_2));
            holder.mTvAgree.setText("通过");
        } else if (info.getApplyStatus().equals("2")) {
            holder.mTvAgree.setBackgroundResource(R.drawable.bor_white_8);
            holder.mTvAgree.setTextColor(ContextCompat.getColor(mContext, R.color.grey_2));
            holder.mTvAgree.setText("已拒绝");
        }

        //同意
        holder.mTvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.getApplyStatus().equals("0")) {
                    if (mOnAgreeListener != null) {
                        mOnAgreeListener.agree(info.getApplyId(), 1);
                    }
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (info.getApplyStatus().equals("0")) {
                    mContext.startActivity(new Intent(mContext,
                            AuditUserActivity.class)
                            .putExtra("applyFriendData", info)
                    );
                } else {
                    mContext.startActivity(new Intent(mContext,
                            UserInfoDetailActivity.class)
                            .putExtra("friendUserId", info.getUserId())
                            .putExtra("from", "1"));
                }
            }
        });
        holder.mTvDel.setTag(position);
        holder.mTvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDelClickListener != null) {
                    mOnDelClickListener.delUser((Integer) holder.mTvDel.getTag());
                }

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        //根据每个字母下第一个联系人在数据中的位置，来显示headView
        ApplyFriendData contant = mInfoList.get(position);
        if (lettes != null && lettes.size() > 0 && lettes.get(contant.getIsTop()) == position) {
            return 1;
        }
        return 0;

    }

    @Override
    public int getItemCount() {
        if (mInfoList != null) {
            return mInfoList.size();
        }

        return 0;
    }


    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter(mInfoList);
        }
        return myFilter;
    }

    protected class MyFilter extends Filter {
        List<ApplyFriendData> mOriginalList = null;

        public MyFilter(List<ApplyFriendData> myList) {
            this.mOriginalList = myList;
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mOriginalList == null) {
                mOriginalList = new ArrayList<>();
            }

            String charString = prefix.toString();
            if (TextUtils.isEmpty(charString)) {
                //没有过滤的内容，则使用源数据
                results.values = copyUserList;
                results.count = copyUserList.size();
            } else {
                if (copyUserList.size() > mOriginalList.size()) {
                    mOriginalList = copyUserList;
                }

                String prefixString = prefix.toString();
                final int count = mOriginalList.size();
                final ArrayList<ApplyFriendData> newValues = new ArrayList<ApplyFriendData>();
                for (int i = 0; i < count; i++) {
                    final ApplyFriendData bean = mOriginalList.get(i);
                    String username = bean.getUserNickName();

                    if (username != null) {
                        if (username.contains(prefixString)) {
                            newValues.add(bean);
                        } else {
                            final String[] words = username.split(" ");
                            final int wordCount = words.length;

                            // Start at index 0, in case valueText starts with space(s)
                            for (String word : words) {
                                if (word.contains(prefixString)) {
                                    newValues.add(bean);
                                    break;
                                }
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //把过滤后的值返回出来
            mInfoList = ((List<ApplyFriendData>) results.values);
            notifyDataSetChanged();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private TextView mTvTop;
        private ImageView imgHead;
        private TextView mTvAgree;
        private TextView mTvDel;
//        private TextView mTvRefuse;
//        private TextView tvOrigin;


        public ViewHolder(View itemView, boolean show) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            mTvTop = (TextView) itemView.findViewById(R.id.tv_top);
            imgHead = (ImageView) itemView.findViewById(R.id.img_head);
            mTvAgree = (TextView) itemView.findViewById(R.id.tv_agree);
            mTvDel = (TextView) itemView.findViewById(R.id.tv_del);
//            mTvRefuse = (TextView) itemView.findViewById(R.id.tv_refuse);
//            tvOrigin = (TextView) itemView.findViewById(R.id.tv_origin);
            if (!show) {
                mTvTop.setVisibility(View.GONE);
            } else {
                mTvTop.setVisibility(View.VISIBLE);
            }
        }
    }


    public interface OnAgreeListener {
        void agree(String applyId, int type);
    }

    public void setOnDelClickListener(OnDelClickListener onDelClickListener) {
        mOnDelClickListener = onDelClickListener;
    }

    public interface OnDelClickListener {
        void delUser(int pos);
    }

}