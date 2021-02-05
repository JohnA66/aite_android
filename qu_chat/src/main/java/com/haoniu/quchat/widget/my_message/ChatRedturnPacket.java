package com.haoniu.quchat.widget.my_message;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.widget.chatrow.EaseChatRow;
import com.hyphenate.chat.EMMessage;

/**
 * 作   者：赵大帅
 * 描   述: 转账
 * 日   期: 2017/11/27 17:26
 * 更新日期: 2017/11/27
 */
public class ChatRedturnPacket extends EaseChatRow {
    private TextView tv_message,tv_messageRemark;

    public ChatRedturnPacket(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getBooleanAttribute(Constant.TURN, false)) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.received_transfer : R.layout.sent_transfer, this);
        }
    }

    @Override
    protected void onFindViewById() {
        tv_message = (TextView) findViewById(R.id.tv_transfer_received);
        tv_messageRemark = (TextView) findViewById(R.id.tv_money_greeting);

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
        String remark = message.getStringAttribute("remark", "");
        if (remark != null && remark.length() > 0) {
            tv_messageRemark.setText(remark);
        }
        tv_message.setText(message.getStringAttribute("money", "") + getResources().getString(R.string.glod));
    }


}
