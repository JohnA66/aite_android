package com.haoniu.quchat.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.aite.chat.R;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.operate.UserOperateManager;
import com.haoniu.quchat.utils.EaseSmileUtils;
import com.haoniu.quchat.utils.EaseUserUtils;
import com.haoniu.quchat.utils.ImageUtil;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;
import com.zds.base.ImageLoad.GlideUtils;

import java.util.Date;
import java.util.List;

/**
 * 代理收益
 */
public class ChatRecordAdapter extends BaseQuickAdapter<EMMessage, BaseViewHolder> {

    public ChatRecordAdapter(List<EMMessage> list) {
        super(R.layout.adapter_chat_record, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, EMMessage message) {


        helper.setText(R.id.tv_name, UserOperateManager.getInstance().getUserName(message.getFrom()));
        ImageUtil.setAvatar(helper.getView(R.id.img_head));
        GlideUtils.loadImageViewLoding(UserOperateManager.getInstance().getUserAvatar(message.getFrom()), helper.getView(R.id.img_head), R.mipmap.img_default_avatar);
        helper.setText(R.id.tv_time, DateUtils.getTimestampString(new Date(message.getMsgTime())));
        helper.setText(R.id.tv_chat_content, EaseSmileUtils.getSmiledText(mContext,  ((EMTextMessageBody) message.getBody()).getMessage()) );
    }

}