package com.haoniu.quchat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.model.GroupManageListInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lhb
 * 设置群管理
 */
public class SetGroupManageSAdapter extends RecyclerView.Adapter<SetGroupManageSAdapter.SetGroupManageSViewHolder> {
    private List<GroupManageListInfo> mList = new ArrayList<>();
    private Context mContext;

    public SetGroupManageSAdapter(Context context, List<GroupManageListInfo> list) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public SetGroupManageSAdapter.SetGroupManageSViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.adapter_set_group_manage, viewGroup, false);
        return new SetGroupManageSViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final SetGroupManageSAdapter.SetGroupManageSViewHolder slideViewHolder, int i) {
        slideViewHolder.mGroupUser.setText(mList.get(i).getUserNickName());
        if (!slideViewHolder.mDeleteTv.hasOnClickListeners()) {
            slideViewHolder.mDeleteTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(slideViewHolder.getAdapterPosition());
                    notifyItemRemoved(slideViewHolder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SetGroupManageSViewHolder extends RecyclerView.ViewHolder {
        private TextView mDeleteTv;
        private TextView mGroupUser;

        private SetGroupManageSViewHolder(@NonNull View itemView) {
            super(itemView);
            mDeleteTv = itemView.findViewById(R.id.tv_del);
            mGroupUser = itemView.findViewById(R.id.tv_group_user);

        }
    }
}
