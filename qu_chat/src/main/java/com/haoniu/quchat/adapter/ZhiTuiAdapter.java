package com.haoniu.quchat.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.aite.chat.R;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.entity.ZhiTuiNumberInfo;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.utils.ImageUtil;
import com.haoniu.quchat.utils.StringUtil;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.List;

/**
 * zhitui
 */
public class ZhiTuiAdapter extends BaseQuickAdapter<ZhiTuiNumberInfo, BaseViewHolder> {

    public ZhiTuiAdapter(List<ZhiTuiNumberInfo> list) {
        super(R.layout.adapter_invers, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZhiTuiNumberInfo item) {
        ImageUtil.setAvatar(helper.getView(R.id.img_head));
        helper.setText(R.id.tv_name, StringUtil.formateUserPhone(item.getPhone()));
        GlideUtils.loadImageViewLoding(AppConfig.ImageMainUrl + item.getUserImg(), helper.getView(R.id.img_head), R.mipmap.img_default_avatar);
        helper.setText(R.id.tv_zhitui_number, item.getOneLevelNum() + "");
    }
}