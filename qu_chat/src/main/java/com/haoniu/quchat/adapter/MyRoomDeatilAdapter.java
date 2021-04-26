package com.haoniu.quchat.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.aite.chat.R;
import com.haoniu.quchat.activity.ContactActivity;
import com.haoniu.quchat.activity.GroupUserDelActivity;
import com.haoniu.quchat.activity.UserInfoDetailActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.EaseConstant;
import com.haoniu.quchat.domain.EaseUser;
import com.haoniu.quchat.entity.GroupDetailInfo;
import com.haoniu.quchat.global.Global;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.operate.GroupOperateManager;
import com.haoniu.quchat.utils.EaseUserUtils;
import com.zds.base.ImageLoad.GlideUtils;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.util.StringUtil;

import java.util.List;

/**
 * 房间成员
 *
 * @author lhb
 */
public class MyRoomDeatilAdapter extends BaseQuickAdapter<GroupDetailInfo.GroupUserDetailVoListBean, BaseViewHolder> {
    private String groupName = "";
    private boolean seeFriendFlag = false;//是否能允许群成员之间互相查看详情
    private String emGroupId;
    private int currentUserRank;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setCurrentUserRank(int currentUserRank) {
        this.currentUserRank = currentUserRank;
    }

    public String getEmGroupId() {
        return emGroupId;
    }

    public void setEmGroupId(String emGroupId) {
        this.emGroupId = emGroupId;
    }

    public OnDelClickListener getOnDelClickListener() {
        return mOnDelClickListener;
    }

    public MyRoomDeatilAdapter(List<GroupDetailInfo.GroupUserDetailVoListBean> list) {
        super(R.layout.room_grid, list);
    }

    private int mode = 0;
    private OnDelClickListener mOnDelClickListener;


    @Override
    protected void convert(BaseViewHolder helper,
                           GroupDetailInfo.GroupUserDetailVoListBean item) {
        helper.convertView.setVisibility(View.VISIBLE);
        helper.setGone(R.id.tv_room_mine, false);

        if (item.getUserId().equals("add")) {
            helper.setText(R.id.tv_name, "");
            helper.setImageResource(R.id.iv_avatar, R.mipmap.img_group_member_add);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext,
                            ContactActivity.class)
                            .putExtra("from", "2")
                            .putExtra("groupName", groupName)
                            .putExtra("groupId", item.getGroupId())
                    );

                }
            });

        } else if (item.getUserId().equals("del")) {
            helper.setText(R.id.tv_name, "");
            helper.setImageResource(R.id.iv_avatar, R.mipmap.img_group_member_del);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext,
                            GroupUserDelActivity.class)
                            .putExtra("from", "2")
                            .putExtra("groupName", groupName)
                            .putExtra("groupId", item.getGroupId())
                            .putExtra(Constant.PARAM_EM_CHAT_ID, emGroupId)
                    );
                }
            });

        } else {
            EaseUser userInfo =
                    EaseUserUtils.getUserInfo(item.getUserNickName());

            if (mode == 1) {
                helper.setGone(R.id.badge_delete, true);
            } else {
                helper.setGone(R.id.badge_delete, false);
            }

//            if (!TextUtils.isEmpty(userInfo.getNickname())) {
//                helper.setText(R.id.tv_name, userInfo.getNickname());
//            }

            if (!TextUtils.isEmpty(item.getFriendNickName())) {
                helper.setText(R.id.tv_name, item.getFriendNickName());
            } else if (!TextUtils.isEmpty(userInfo.getNickname())) {
                helper.setText(R.id.tv_name, userInfo.getNickname());
            }


        GlideUtils.GlideLoadCircleErrorImageUtils(mContext,
                AppConfig.checkimg(item.getUserHead()),
                (ImageView) helper.getView(R.id.iv_avatar),
                R.mipmap.img_default_avatar);


        helper.setOnClickListener(R.id.badge_delete,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnDelClickListener != null) {
                            mOnDelClickListener.delUser(helper.getPosition());
                        }

                    }
                });

        if (item.getUserRank().equals("1")) {
            helper.setGone(R.id.tv_room_mine, true);
            helper.setText(R.id.tv_room_mine, "管理员");
        }
        if (item.getUserRank().equals("2")) {
            helper.setGone(R.id.tv_room_mine, true);
            helper.setText(R.id.tv_room_mine, "群主");
        }

        if (helper.getPosition() == 0) {
            helper.setGone(R.id.badge_delete, false);
//            if (StringUtil.isEmpty(userInfo.getNickname())) {
//                helper.setText(R.id.tv_name, userInfo.getUsername());
//            }
        }

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!seeFriendFlag) {
                    ToastUtil.toast("暂未开启查看群成员详情功能");
                    return;
                }
                Global.addUserOriginType = Constant.ADD_USER_ORIGIN_TYPE_GROUPCHAT;
                Global.addUserOriginName = groupName;
                Global.addUserOriginId = GroupOperateManager.getInstance().getGroupId(emGroupId);
                Intent intent = new Intent(mContext,
                        UserInfoDetailActivity.class)
                        .putExtra("from", "1")
                        .putExtra("friendUserId", item.getUserId())
                        .putExtra("entryUserId", item.getEntryUserId())
                        .putExtra("chatType", EaseConstant.CHATTYPE_GROUP);

                if (currentUserRank == 2 || (currentUserRank == 1 && item.getUserRank().equals("0"))) {
                    intent.putExtra(Constant.PARAM_GROUP_ID, item.getGroupId())
                            .putExtra(Constant.PARAM_EM_GROUP_ID, emGroupId);
                }

                mContext.startActivity(intent);
            }
        });

    }

//        //0-普通用户 1-管理员 2-群主
//        helper.setGone(R.id.badge_delete, !"2".equals(item.getUserRank())
//                && "1".equals(item.getUserRank())
//                && helper.getPosition() != mData.size() - 1
//                && helper.getAdapterPosition() != 0);

}

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }


    public void setOnDelClickListener(OnDelClickListener onDelClickListener) {
        mOnDelClickListener = onDelClickListener;
    }

    public void setUserReadDetail(boolean seeFriendFlag) {
        this.seeFriendFlag = seeFriendFlag;
    }

public interface OnDelClickListener {
    void delUser(int pos);
}

}