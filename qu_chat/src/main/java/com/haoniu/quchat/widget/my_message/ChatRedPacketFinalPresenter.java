package com.haoniu.quchat.widget.my_message;

import android.content.Context;
import android.widget.BaseAdapter;

import com.haoniu.quchat.widget.chatrow.EaseChatRow;
import com.haoniu.quchat.widget.presenter.EaseChatRowPresenter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;

/**
 * 作   者：赵大帅
 * 描   述: 红包抢完
 * 日   期: 2017/11/21 9:12
 * 更新日期: 2017/11/21
 */

public class ChatRedPacketFinalPresenter extends EaseChatRowPresenter {

    @Override
    protected EaseChatRow onCreateChatRow(Context cxt, EMMessage message, int position, BaseAdapter adapter) {
        return new ChatRedFinalPacket(cxt, message, position, adapter);
    }

    @Override
    protected void handleReceiveMessage(EMMessage message) {
        if (!message.isAcked() && message.getChatType() == EMMessage.ChatType.Chat) {
            try {
                EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }
    }
}
