package com.haoniu.quchat.widget.my_message;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.widget.chatrow.EaseChatRow;
import com.hyphenate.chat.EMMessage;

public class ChatRedUpRoomNamePacket extends EaseChatRow {
    private TextView tv_message;
    private LinearLayout ll_container;

    public ChatRedUpRoomNamePacket(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.RENAME)) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.row_red_packet_ack_message : R.layout.row_red_packet_ack_message, this);
        }
    }

    @Override
    protected void onFindViewById() {
        tv_message = (TextView) findViewById(R.id.tv_money_msg);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
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
        ll_container.setVisibility(VISIBLE);
        if (message.getChatType().equals(EMMessage.ChatType.GroupChat)) {
            String name = message.getStringAttribute(Constant.NAME, "");
            final SpannableStringBuilder sp = new SpannableStringBuilder("修改房间名称为" + name);
            if (!name.equals("")) {
                sp.setSpan(new ForegroundColorSpan(0xffFA9E3B), 7, 7 + name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
            }
            tv_message.setText(sp);
        }
    }
}
