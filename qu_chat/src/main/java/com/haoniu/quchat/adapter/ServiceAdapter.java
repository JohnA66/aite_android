package com.haoniu.quchat.adapter;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haoniu.quchat.entity.ServiceInfo;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.utils.ImageUtil;
import com.haoniu.quchat.widget.EaseImageView;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.List;

/**
 * 客服中心
 *
 * @author Administrator
 */
public class ServiceAdapter extends BaseQuickAdapter<ServiceInfo.DataBean, BaseViewHolder> {

    public ServiceAdapter(List<ServiceInfo.DataBean> list) {
        super(R.layout.adapter_service_list, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceInfo.DataBean item) {
        ImageUtil.setAvatar((EaseImageView) helper.getView(R.id.img_head));
        helper.setText(R.id.tv_title, item.getNickName());
        helper.setGone(R.id.tv_number, false);
        GlideUtils.loadImageViewLoding(AppConfig.checkimg(item.getUserHead()) , (EaseImageView) helper.getView(R.id.img_head), R.mipmap.img_default_avatar);
    }
}