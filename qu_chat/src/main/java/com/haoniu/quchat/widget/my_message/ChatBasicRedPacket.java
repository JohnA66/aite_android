package com.haoniu.quchat.widget.my_message;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.widget.chatrow.EaseChatRow;
import com.hyphenate.chat.EMMessage;
import com.zds.base.util.StringUtil;

/**
 * 作   者：赵大帅
 * 描   述: 红包消息
 * 日   期: 2017/11/21 9:12
 * 更新日期: 2017/11/21
 *
 * @author Administrator
 */
public class ChatBasicRedPacket extends EaseChatRow {
    private TextView tv_message, tv_time_message, tv_hongbao,tv_time;
    private RelativeLayout bubble;

    public ChatBasicRedPacket(Context context, EMMessage message,
                              int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.REDPACKET)
                || message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.PERSON_RED_BAG)
                || Constant.TURN.equals(message.getStringAttribute(Constant.MSGTYPE, ""))
                || Constant.SURE_TURN.equals(message.getStringAttribute(Constant.MSGTYPE, ""))) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.row_received_red_packet :
                    R.layout.row_send_red_packet, this);
        }
    }

    @Override
    protected void onFindViewById() {
        tv_message = (TextView) findViewById(R.id.message);
        tv_time_message = (TextView) findViewById(R.id.tv_time_message);
        tv_hongbao = (TextView) findViewById(R.id.tv_hongbao);
        tv_time = (TextView) findViewById(R.id.tv_time);
        bubble = findViewById(R.id.bubble);
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
        String redBagInfo = message.getStringAttribute("redPacketType", "");
        boolean isClick = message.getBooleanAttribute("isClick", false);
        String remark = message.getStringAttribute("remark", "恭喜发财，大吉大利！");

        try {
            tv_time.setText(StringUtil.formatTime(message.getMsgTime(),"yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isClick) {
            if (message.direct() == EMMessage.Direct.RECEIVE) {
                bubble.setBackgroundResource(R.mipmap.hb1);
            } else {
                bubble.setBackgroundResource(R.mipmap.hb4);
            }

        } else {
            if (message.direct() == EMMessage.Direct.RECEIVE) {
                bubble.setBackgroundResource(R.mipmap.hb2);
            } else {
                bubble.setBackgroundResource(R.mipmap.hb3);
            }

        }
        if (Constant.TURN.equals(message.getStringAttribute(Constant.MSGTYPE, ""))
                || Constant.SURE_TURN.equals(message.getStringAttribute(Constant.MSGTYPE, ""))) {
            tv_time_message.setText("");
            tv_message.setText("已领取");
        } else {
            if ("1".equals(redBagInfo)) {
                tv_time_message.setText("");
                tv_message.setText(remark);
            } else if ("2".equals(redBagInfo)) {
                tv_time_message.setText("");
                tv_hongbao.setText("");
                tv_message.setText(StringUtil.getFormatValue(Double.valueOf(message.getStringAttribute("money", ""))) + "/" + message.getStringAttribute("thunderPoint", "").replaceAll(",", ""));
            } else if ("3".equals(redBagInfo)) {
                tv_time_message.setText("24小时有效");
                tv_hongbao.setText("");
                tv_message.setText(remark);
            }
        }
    }


}
