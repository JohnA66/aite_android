package com.haoniu.quchat.widget;

import android.content.Context;
import android.widget.BaseAdapter;

import com.haoniu.quchat.widget.chatrow.EaseChatRow;
import com.haoniu.quchat.widget.presenter.EaseChatRowPresenter;
import com.hyphenate.chat.EMMessage;

/**
 * Created by zhangsong on 17-10-12.
 */

public class EaseChatVoiceCallPresenter extends EaseChatRowPresenter {
    @Override
    protected EaseChatRow onCreateChatRow(Context cxt, EMMessage message, int position, BaseAdapter adapter) {
        return new ChatRowVoiceCall(cxt, message, position, adapter);
    }
}
