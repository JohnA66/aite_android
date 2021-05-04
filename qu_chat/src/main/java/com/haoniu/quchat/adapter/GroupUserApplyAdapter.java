package com.haoniu.quchat.adapter;

import android.annotation.SuppressLint;
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
import com.haoniu.quchat.activity.UserInfoDetailActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.entity.GroupUserAuditInfo;
import com.haoniu.quchat.global.Global;
import com.haoniu.quchat.http.AppConfig;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 加群申请adapter
 *
 * @author lhb
 */
public class GroupUserApplyAdapter extends RecyclerView.Adapter<GroupUserApplyAdapter.ViewHolder> implements Filterable {

    private List<GroupUserAuditInfo.DataBean> mInfoList = new ArrayList<>();
    private List<GroupUserAuditInfo.DataBean> copyUserList;

    private Context mContext;
    private HashMap<String, Integer> lettes = new HashMap<>();
    private MyFilter myFilter;

    private OnAgreeListener mOnAgreeListener;
    private GroupMemberAdapter.OnDelClickListener mOnDelClickListener;

    public void setOnAgreeListener(OnAgreeListener onAgreeListener) {
        mOnAgreeListener = onAgreeListener;
    }

    public GroupUserApplyAdapter(List<GroupUserAuditInfo.DataBean> infoList, Context context, HashMap<String, Integer> lettes) {
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
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_apply_join_group, parent, false), false);
            case 1:
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_apply_join_group, parent, false), true);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final GroupUserAuditInfo.DataBean info = mInfoList.get(position);
        holder.mTvName.setText(info.getNickName());
        holder.mTvGroupName.setText("申请加入群组:"+info.getGroupName());
        holder.mTvTop.setText(info.getTopName());
        if (TextUtils.isEmpty(info.getInviterName())){
            holder.tvInviter.setVisibility(View.GONE);
        }else {
            holder.tvInviter.setVisibility(View.VISIBLE);
            holder.tvInviter.setText("邀请者:"+info.getInviterName());

        }


        GlideUtils.GlideLoadCircleErrorImageUtils(mContext, AppConfig.checkimg(info.getUserHead()), holder.imgHead, R.mipmap.img_default_avatar);

        if (info.getApplyStatus().equals("0")) {
            holder.mTvAgree.setBackgroundResource(R.drawable.agree_friend_bg_selector);
            holder.mTvAgree.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            holder.mTvRefuse.setVisibility(View.VISIBLE);
            holder.mTvAgree.setText("通过");

        } else if (info.getApplyStatus().equals("1")) {
            holder.mTvAgree.setBackgroundResource(R.drawable.bor_white_8);
            holder.mTvAgree.setTextColor(ContextCompat.getColor(mContext, R.color.grey_2));
            holder.mTvRefuse.setVisibility(View.INVISIBLE);
            holder.mTvAgree.setText("通过");
        } else if (info.getApplyStatus().equals("2")) {
            holder.mTvAgree.setBackgroundResource(R.drawable.bor_white_8);
            holder.mTvAgree.setTextColor(ContextCompat.getColor(mContext, R.color.grey_2));
            holder.mTvRefuse.setVisibility(View.INVISIBLE);
            holder.mTvAgree.setText("已拒绝");
        }

        //同意
        holder.mTvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.getApplyStatus().equals("0")) {
                    if (mOnAgreeListener != null) {
                        mOnAgreeListener.agree(info.getApplyId(), 1,info.getGroupId(),info.getUserId());
                    }
                }
            }
        });

        //拒绝
        holder.mTvRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.getApplyStatus().equals("0")) {
                    if (mOnAgreeListener != null) {
                        mOnAgreeListener.agree(info.getApplyId(), 2,info.getGroupId(),info.getUserId());
                    }
                }
            }
        });

        holder.mTvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDelClickListener != null) {
                    mOnDelClickListener.delUser(position);
                }

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Global.addUserOriginType = Constant.ADD_USER_ORIGIN_TYPE_GROUPCHAT;
                Global.addUserOriginName = info.getGroupName();
                Global.addUserOriginId   = info.getGroupId();


                mContext.startActivity(new Intent(mContext,
                        UserInfoDetailActivity.class)
                        .putExtra("friendUserId", info.getUserId())
                        .putExtra(Constant.PARAM_GROUP_ID, info.getGroupId())
                        .putExtra("from", "1"));
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        //根据每个字母下第一个联系人在数据中的位置，来显示headView
        GroupUserAuditInfo.DataBean contant = mInfoList.get(position);
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

    public void setOnDelClickListener(GroupMemberAdapter.OnDelClickListener onDelClickListener) {
        mOnDelClickListener = onDelClickListener;
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter(mInfoList);
        }
        return myFilter;
    }

    protected class MyFilter extends Filter {
        List<GroupUserAuditInfo.DataBean> mOriginalList = null;

        public MyFilter(List<GroupUserAuditInfo.DataBean> myList) {
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
                final ArrayList<GroupUserAuditInfo.DataBean> newValues = new ArrayList<GroupUserAuditInfo.DataBean>();
                for (int i = 0; i < count; i++) {
                    final GroupUserAuditInfo.DataBean bean = mOriginalList.get(i);
                    String username = bean.getNickName();

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
            mInfoList = ((List<GroupUserAuditInfo.DataBean>) results.values);
            notifyDataSetChanged();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private TextView mTvTop;
        private TextView mTvGroupName;
        private ImageView imgHead;
        private TextView mTvAgree;
        private TextView mTvRefuse;
        private TextView mTvDel;
        private TextView tvInviter;
        public ViewHolder(View itemView, boolean show) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            mTvTop = (TextView) itemView.findViewById(R.id.tv_top);
            mTvGroupName = (TextView) itemView.findViewById(R.id.tv_group_name);
            imgHead = (ImageView) itemView.findViewById(R.id.img_head);
            mTvAgree = (TextView) itemView.findViewById(R.id.tv_agree);
            mTvRefuse = (TextView) itemView.findViewById(R.id.tv_refuse);
            mTvDel = (TextView) itemView.findViewById(R.id.tv_del);
            tvInviter = (TextView) itemView.findViewById(R.id.tv_inviter);
            if (!show) {
                mTvTop.setVisibility(View.GONE);
            } else {
                mTvTop.setVisibility(View.VISIBLE);
            }
        }
    }


    public interface OnAgreeListener {
        void agree(String applyId, int type,String groupId,String userId);
    }


}