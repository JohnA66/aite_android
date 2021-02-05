package com.haoniu.quchat.widget.my_message;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.widget.chatrow.EaseChatRow;
import com.hyphenate.chat.EMMessage;
import com.zds.base.util.StringUtil;

/**
 * 作   者：赵大帅
 * 描   述: 退款
 * 日   期: 2017/11/27 17:26
 * 更新日期: 2017/11/27
 */
public class ChatRedReturnPacket extends EaseChatRow {
    private TextView tv_message,tv_message_red;
    private LinearLayout ll_container;

    public ChatRedReturnPacket(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.RETURNGOLD)) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.red_packet_ack_gold_return : R.layout.red_packet_ack_gold_return, this);
        }
    }

    @Override
    protected void onFindViewById() {
        tv_message = (TextView) findViewById(R.id.tv_money);
        tv_message_red=(TextView)findViewById(R.id.tv_message_red);
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
        tv_message.setText(StringUtil.getFormatValue2(message.getStringAttribute("money", "0")) + getResources().getString(R.string.glod));
        tv_message_red.setText(message.getStringAttribute("describe", ""));
    }
}
