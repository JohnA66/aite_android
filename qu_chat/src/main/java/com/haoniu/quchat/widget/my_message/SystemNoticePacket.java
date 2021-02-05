package com.haoniu.quchat.widget.my_message;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.utils.EaseCommonUtils;
import com.haoniu.quchat.utils.StringUtil;
import com.haoniu.quchat.operate.UserOperateManager;
import com.haoniu.quchat.widget.chatrow.EaseChatRow;
import com.hyphenate.chat.EMMessage;

public class SystemNoticePacket extends EaseChatRow {
    private TextView tv_message,tv_message_new;
    private LinearLayout ll_container,llay_msg;

    public SystemNoticePacket(Context context, EMMessage message,
                              int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.SYSTEM_NOTICE)
                || message.getStringAttribute(Constant.MSGTYPE, "").equals("addgroupuser")
                || message.getStringAttribute(Constant.MSGTYPE, "").equals("delgroupuser")) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.system_notice_message :
                    R.layout.system_notice_message, this);
        }
    }

    @Override
    protected void onFindViewById() {
        tv_message = (TextView) findViewById(R.id.tv_msg);
        tv_message_new = (TextView) findViewById(R.id.tv_msg_new);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        llay_msg = (LinearLayout) findViewById(R.id.llay_msg);
    }

    /**
     * refresh view when message status change
     *
     * @param msg
     */
    @Override
    protected void onViewUpdate(EMMessage msg) {

    }


    @Override
    protected void onSetUpView() {
        String systemNotice = EaseCommonUtils.getMessageDigest(message,
                getContext());
        if (message.getStringAttribute(Constant.MSGTYPE, "").equals("addgroupuser")
                || message.getStringAttribute(Constant.MSGTYPE, "").equals("delgroupuser")){

            String inviterId = message.getStringAttribute(Constant.INVITER_ID, "");
            String userId = message.getStringAttribute(Constant.USER_ID, "");
            String delUserId = message.getStringAttribute(Constant.DEL_USER_ID, "");

            if ("addgroupuser".equals(Constant.MSGTYPE) ) {
                systemNotice = getContext().getString(R.string.msg_group_invite_user,
                        UserOperateManager.getInstance().getUserName(userId),
                        UserOperateManager.getInstance().getUserName(inviterId)
                );
            }else if ("delgroupuser".equals(Constant.MSGTYPE) ){
                systemNotice = getContext().getString(R.string.msg_group_remove_user,
                        UserOperateManager.getInstance().getUserName(userId),
                        UserOperateManager.getInstance().getUserName(delUserId)
                );
            }

            tv_message_new.setVisibility(VISIBLE);
            llay_msg.setVisibility(GONE);
            tv_message_new.setText(StringUtil.isEmpty(systemNotice) ? "" :
                    systemNotice);
            return;
        }
        tv_message_new.setVisibility(GONE);
        llay_msg.setVisibility(VISIBLE);
        tv_message.setText(StringUtil.isEmpty(systemNotice) ? "" :
                systemNotice);
    }


}
