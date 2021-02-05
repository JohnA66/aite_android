package com.haoniu.quchat.widget.my_message;

import android.content.Context;
import android.content.Intent;
import android.widget.BaseAdapter;

import com.haoniu.quchat.activity.UserInfoDetailActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.global.Global;
import com.haoniu.quchat.utils.ProjectUtil;
import com.haoniu.quchat.widget.chatrow.EaseChatRow;
import com.haoniu.quchat.widget.presenter.EaseChatRowPresenter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;

/**
 * 作   者：赵大帅
 */

public class ChatidCardPresenter extends EaseChatRowPresenter {

    @Override
    protected EaseChatRow onCreateChatRow(Context cxt, EMMessage message, int position, BaseAdapter adapter) {
        return new ChatIdCardpacket(cxt, message, position, adapter);
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

    @Override
    public void onBubbleClick(EMMessage message) {
        if (message.getBooleanAttribute(Constant.SEND_CARD, false)) {
            String account = message.getStringAttribute("otherUserCode", "");
            String nickname = message.getStringAttribute("otherName", "");
            String avatar = message.getStringAttribute("otherImg", "");
            String friendUserId = message.getStringAttribute("otherUserId", "");
            Global.addUserOriginType = Constant.ADD_USER_ORIGIN_TYPE_RECOMMEND;
            Global.addUserOriginName = message.getStringAttribute(Constant.NICKNAME,"");
            Global.addUserOriginId   = ProjectUtil.transformId(message.getFrom());
            getContext().startActivity(new Intent(getContext(), UserInfoDetailActivity.class).putExtra("friendUserId", friendUserId).putExtra("from", "1"));

        }
    }
}
