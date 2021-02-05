package com.haoniu.quchat.utils.hxSetMessageFree;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.adapter.EMAChatManager;
import com.hyphenate.chat.adapter.EMAConversation;
import com.zds.base.util.Utils;

import java.util.Iterator;
import java.util.List;

public class UnReadMsgCount {
    /**
     * 获取未读总数代码(不包括免打扰消息)
     */
    public static int getUnreadMessageCount() {
        EMAChatManager emaObject = (EMAChatManager) DataTool.getSpecifiedFieldObject(EMClient.getInstance().chatManager(), "emaObject");
        List<EMAConversation> emaConversationList = emaObject.getConversations();
        int unRead = 0;
        Iterator iterator = emaConversationList.iterator();
        while (iterator.hasNext()) {
            EMAConversation conversation = (EMAConversation) iterator.next();
            if (conversation.messagesCount()>0){
                if (conversation._getType() != EMAConversation.EMAConversationType.CHATROOM &&
                        EaseSharedUtils.isEnableMsgRing(Utils.getContext(), EMClient.getInstance().getCurrentUser(), conversation.conversationId())  && conversation.latestMessage().from() !=null && !conversation.latestMessage().from().equals("系统管理员")) {
                    unRead += conversation.unreadMessagesCount();
                }
            }
        }
        return unRead;
    }
}
