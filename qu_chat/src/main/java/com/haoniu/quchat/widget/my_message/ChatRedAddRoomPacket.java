package com.haoniu.quchat.widget.my_message;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.widget.chatrow.EaseChatRow;
import com.hyphenate.chat.EMMessage;

/**
 * 作   者：赵大帅
 * 描   述: 创建房间
 * 日   期: 2017/11/30 19:08
 * 更新日期: 2017/11/30
 */
public class ChatRedAddRoomPacket extends EaseChatRow {
    private TextView tv_message;
    private LinearLayout ll_container;
    private ImageView img_red;

    public ChatRedAddRoomPacket(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.ADDROOM)) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.row_red_packet_ack_message : R.layout.row_red_packet_ack_message, this);
        }
    }

    @Override
    protected void onFindViewById() {
        tv_message = (TextView) findViewById(R.id.tv_money_msg);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        img_red = (ImageView) findViewById(R.id.img_red);
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
        img_red.setVisibility(GONE);
        if (message.getChatType().equals(EMMessage.ChatType.GroupChat)) {
            tv_message.setText("创建房间成功，赶快邀请好友一起抢红包吧");
        }
    }


}
