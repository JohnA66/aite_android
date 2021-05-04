package com.haoniu.quchat.widget.my_message;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.widget.chatrow.EaseChatRow;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;

public class ChatApplyFriendNoticePacket extends EaseChatRow {
    private TextView tv_message;

    public ChatApplyFriendNoticePacket(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.ADD_USER)) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.row_apply_frined_notice_message : R.layout.row_apply_frined_notice_message, this);
        }
    }

    @Override
    protected void onFindViewById() {
        tv_message = (TextView) findViewById(R.id.tv_msg);
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
        if (message.getChatType().equals(EMMessage.ChatType.Chat)) {
            try {
                String applyStatus = message.getStringAttribute("applyStatus");
                String nickname = message.getStringAttribute(Constant.NICKNAME, "");
                if (applyStatus.equals("1")) {
                    tv_message.setText("您已经和" + nickname + "成为好友，现在可以开始聊天了。");
                } else {
                    tv_message.setText(nickname + "拒绝了您的好友申请!");
                }

            } catch (HyphenateException e) {
                e.printStackTrace();
            }

        }
    }
}
