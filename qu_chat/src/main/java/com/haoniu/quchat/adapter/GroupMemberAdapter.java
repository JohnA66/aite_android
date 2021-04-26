package com.haoniu.quchat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.haoniu.quchat.entity.GroupDetailInfo;
import com.haoniu.quchat.http.AppConfig;
import com.zds.base.ImageLoad.GlideUtils;
import com.zds.base.Toast.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 群成员adapter
 *
 * @author lhb
 */
public class GroupMemberAdapter extends RecyclerView.Adapter<GroupMemberAdapter.ViewHolder> implements Filterable {

    private List<GroupDetailInfo.GroupUserDetailVoListBean> mInfoList;
    private List<GroupDetailInfo.GroupUserDetailVoListBean> copyUserList;

    private Context mContext;
    private HashMap<String, Integer> lettes = new HashMap<>();
    private MyFilter myFilter;
    private boolean isSeeUserDetail = false;//是否能查看群成员详情

    private int groupUserRank = 0;

    public int getGroupUserRank() {
        return groupUserRank;
    }

    public void setGroupUserRank(int groupUserRank) {
        this.groupUserRank = groupUserRank;
    }

    private OnDelClickListener mOnDelClickListener;
    private OnClickAtUserListener onClickAtUserListener;
    private String emGroupId;

    public GroupMemberAdapter(List<GroupDetailInfo.GroupUserDetailVoListBean> infoList, Context context, HashMap<String, Integer> lettes, String emGroupId) {
        this.mInfoList = infoList;
        if (copyUserList == null) {
            //只取值一次，用于无检索条件时， 返回全部数据
            this.copyUserList = infoList;
        }
        mContext = context;
        this.lettes = lettes;
        this.emGroupId = emGroupId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        switch (viewType) {
            case 0:
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_group_member, parent, false), false);
            case 1:
                return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_group_member, parent, false), false);
            default:

                return null;
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final GroupDetailInfo.GroupUserDetailVoListBean info =
                mInfoList.get(position);
        if (!TextUtils.isEmpty(info.getFriendNickName())) {
            holder.mTvName.setText(info.getFriendNickName());
        } else {
            holder.mTvName.setText(info.getUserNickName());
        }

        if (info.getUserRank().equals("1")) {
            holder.mTvMange.setText("管理员");
            holder.mTvMange.setVisibility(View.VISIBLE);

        } else if (info.getUserRank().equals("2")) {
            holder.mTvMange.setText("群主");
            holder.mTvMange.setVisibility(View.VISIBLE);
        } else {
            holder.mTvMange.setVisibility(View.GONE);
        }

        if (groupUserRank == 2) {
            if (info.getUserRank().equals("1")) {
                holder.mTvDel.setVisibility(View.VISIBLE);
            } else if (info.getUserRank().equals("2")) {
                holder.mTvDel.setVisibility(View.GONE);
            } else {
                holder.mTvDel.setVisibility(View.VISIBLE);
            }
        } else if (groupUserRank == 1) {
            if (info.getUserRank().equals("1")) {
                holder.mTvDel.setVisibility(View.GONE);
            } else if (info.getUserRank().equals("2")) {
                holder.mTvDel.setVisibility(View.GONE);
            } else {
                holder.mTvDel.setVisibility(View.VISIBLE);
            }
        } else {
            holder.mTvDel.setVisibility(View.GONE);
        }


        holder.mTvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDelClickListener != null) {
                    mOnDelClickListener.delUser(position);
                }

            }
        });

        //到用户详情
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onClickAtUserListener != null) {
                    onClickAtUserListener.atUser(info.getUserNickName(), info.getUserId());
                    return;
                }
                if (!isSeeUserDetail) {
                    ToastUtil.toast("暂未开启查看群成员详情功能");
                    return;
                }
                Intent intent = new Intent(mContext,
                        UserInfoDetailActivity.class)
                        .putExtra("friendUserId", info.getUserId())
                        .putExtra("entryUserId", info.getEntryUserId())
                        .putExtra("from", "1");

                if (groupUserRank == 2 || (groupUserRank == 1 && info.getUserRank().equals("0"))) {
                    intent.putExtra(Constant.PARAM_GROUP_ID, info.getGroupId())
                            .putExtra(Constant.PARAM_EM_GROUP_ID, emGroupId);

                }
                mContext.startActivity(intent);
            }
        });


        GlideUtils.GlideLoadCircleErrorImageUtils(mContext,
                AppConfig.checkimg(info.getUserHead()), holder.imgHead,
                R.mipmap.img_default_avatar);


    }

    @Override
    public int getItemViewType(int position) {
        //根据每个字母下第一个联系人在数据中的位置，来显示headView
        GroupDetailInfo.GroupUserDetailVoListBean contant =
                mInfoList.get(position);
        if (lettes != null && lettes.size() > 0 && lettes.get(contant.getTop()) == position) {
            return 1;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return mInfoList.size();
    }


    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter(mInfoList);
        }
        return myFilter;
    }

    public void setUserReadDetail(boolean b) {
        this.isSeeUserDetail = b;
    }

    protected class MyFilter extends Filter {
        List<GroupDetailInfo.GroupUserDetailVoListBean> mOriginalList = null;

        public MyFilter(List<GroupDetailInfo.GroupUserDetailVoListBean> myList) {
            this.mOriginalList = myList;
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mOriginalList == null) {
                mOriginalList =
                        new ArrayList<GroupDetailInfo.GroupUserDetailVoListBean>();
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
                final ArrayList<GroupDetailInfo.GroupUserDetailVoListBean> newValues = new ArrayList<GroupDetailInfo.GroupUserDetailVoListBean>();
                for (int i = 0; i < count; i++) {
                    final GroupDetailInfo.GroupUserDetailVoListBean bean =
                            mOriginalList.get(i);
                    String username = bean.getUserNickName();

                    if (username.contains(prefixString)) {
                        newValues.add(bean);
                    } else {
                        final String[] words = username.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with
                        // space(s)
                        for (String word : words) {
                            if (word.contains(prefixString)) {
                                newValues.add(bean);
                                break;
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
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            //把过滤后的值返回出来
            mInfoList =
                    ((List<GroupDetailInfo.GroupUserDetailVoListBean>) results.values);
            notifyDataSetChanged();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private ImageView imgHead;
        private TextView mTvMange;
        private TextView mTvDel;


        public ViewHolder(View itemView, boolean show) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            imgHead = (ImageView) itemView.findViewById(R.id.img_head);
            mTvMange = (TextView) itemView.findViewById(R.id.tv_manage);
            mTvDel = (TextView) itemView.findViewById(R.id.tv_del);

        }
    }


    public void setOnDelClickListener(OnDelClickListener onDelClickListener) {
        mOnDelClickListener = onDelClickListener;
    }

    public void setOnClickAtUserListener(OnClickAtUserListener onClickAtUserListener) {
        this.onClickAtUserListener = onClickAtUserListener;
    }

    public interface OnDelClickListener {
        void delUser(int pos);
    }

    public interface OnClickAtUserListener {
        void atUser(String atUserName, String atUserId);
    }


}