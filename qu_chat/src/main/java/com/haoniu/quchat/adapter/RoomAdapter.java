package com.haoniu.quchat.adapter;

import android.widget.ImageView;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.entity.RoomInfo;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.utils.ImageUtil;
import com.haoniu.quchat.widget.EaseImageView;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.List;

/**
 * 房间适配器
 */
public class RoomAdapter extends BaseQuickAdapter<RoomInfo, BaseViewHolder> {

    public RoomAdapter(List<RoomInfo> list) {
        super(R.layout.item_group, list);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, RoomInfo item) {

        ImageUtil.setAvatar((EaseImageView) helper.getView(R.id.avatar));
        helper.setTextColor(R.id.name, mContext.getResources().getColor(R.color.black_deep));
        helper.setText(R.id.name, item.getName()+"");
        GlideUtils.loadImageViewLoding(AppConfig.ImageMainUrl + item.getRoomImg(), (ImageView) helper.getView(R.id.avatar), R.mipmap.ic_launcher);
    }
}