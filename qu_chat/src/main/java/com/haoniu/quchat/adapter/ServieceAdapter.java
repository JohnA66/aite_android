package com.haoniu.quchat.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.aite.chat.R;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.entity.ServersInfo;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.utils.ImageUtil;
import com.haoniu.quchat.widget.EaseImageView;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.List;

/**
 * 客服
 */
public class ServieceAdapter extends BaseQuickAdapter<ServersInfo, BaseViewHolder> {

    public ServieceAdapter(List<ServersInfo> list) {
        super(R.layout.adapter_service, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServersInfo item) {
        helper.setText(R.id.tv_name, item.getNickName());
        ImageUtil.setAvatar((EaseImageView) helper.getView(R.id.img_head));
        GlideUtils.loadImageViewLoding(AppConfig.ImageMainUrl + item.getHeadImg(), helper.getView(R.id.img_head), R.mipmap.img_default_avatar);

    }


}