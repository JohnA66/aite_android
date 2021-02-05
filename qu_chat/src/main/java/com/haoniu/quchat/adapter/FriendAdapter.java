package com.haoniu.quchat.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.aite.chat.R;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.entity.ZhiTuiNumberInfo;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.utils.ImageUtil;
import com.zds.base.ImageLoad.GlideUtils;
import com.zds.base.util.StringUtil;

import java.util.List;

/**
 * 代理收益
 */
public class FriendAdapter extends BaseQuickAdapter<ZhiTuiNumberInfo, BaseViewHolder> {

    public FriendAdapter(List<ZhiTuiNumberInfo> list) {
        super(R.layout.adapter_friend, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZhiTuiNumberInfo item) {
        ImageUtil.setAvatar(helper.getView(R.id.img_head));
        helper.setText(R.id.tv_name, StringUtil.formateUserPhone(item.getPhone()));
        GlideUtils.loadImageViewLoding(AppConfig.checkimg(item.getUserImg())  , helper.getView(R.id.img_head), R.mipmap.img_default_avatar);

    }

}