package com.haoniu.quchat.adapter;

import android.view.View;
import android.widget.ImageView;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.domain.EaseUser;
import com.haoniu.quchat.entity.RoomInfo;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.utils.EaseUserUtils;
import com.zds.base.ImageLoad.GlideUtils;
import com.zds.base.util.StringUtil;

import java.util.List;

/**
 * 房间成员
 */
public class NewRoomDeatilAdapter extends BaseQuickAdapter<RoomInfo.UserListBean, BaseViewHolder> {

    public NewRoomDeatilAdapter(List<RoomInfo.UserListBean> list) {
        super(R.layout.room_grid, list);
    }

    private int mode = 0;

    @Override
    protected void convert(BaseViewHolder helper, RoomInfo.UserListBean item) {
        helper.convertView.setVisibility(View.VISIBLE);
        helper.setGone(R.id.tv_room_mine, false);
        EaseUser userInfo = EaseUserUtils.getUserInfo(item.getNickName());
        if ("-1".equals(item)) {
            helper.setImageResource(R.id.iv_avatar, R.drawable.em_smiley_minus_btn);
            helper.setText(R.id.tv_name, "");
            if (mode != 0) {
                helper.convertView.setVisibility(View.GONE);
            }
            helper.setGone(R.id.badge_delete, false);
        } else if ("-2".equals(item)) {
            helper.setText(R.id.tv_name, "");
            helper.setGone(R.id.badge_delete, false);
            if (mode != 0) {
                helper.convertView.setVisibility(View.GONE);
            }
            helper.setImageResource(R.id.iv_avatar, R.drawable.em_smiley_add_btn);
        } else {
            if (mode == 1) {
                helper.setGone(R.id.badge_delete, true);
            } else {
                helper.setGone(R.id.badge_delete, false);
            }
            helper.setText(R.id.tv_name, userInfo.getNickname());
            if ("admin".equals(item)) {
                helper.setImageResource(R.id.iv_avatar, R.mipmap.ic_launcher);
            } else {
                GlideUtils.loadImageViewLoding(AppConfig.checkimg(item.getHeadImg()) , (ImageView) helper.getView(R.id.iv_avatar), R.mipmap.img_default_avatar);

            }
            helper.addOnClickListener(R.id.badge_delete);
            if (helper.getPosition() == 0) {
                helper.setGone(R.id.badge_delete, false);
                if (StringUtil.isEmpty(userInfo.getNickname())) {
                    if (userInfo.getUsername().equals("admin")) {

                    }
                    helper.setText(R.id.tv_name, userInfo.getUsername());
                }
                helper.setGone(R.id.tv_room_mine, true);
            } else {
//                helper.setGone(R.id.badge_delete, true);
            }
        }
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}