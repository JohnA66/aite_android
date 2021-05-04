package com.haoniu.quchat.widget.my_message;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.google.gson.reflect.TypeToken;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.widget.chatrow.EaseChatRow;
import com.hyphenate.chat.EMMessage;
import com.zds.base.json.FastJsonUtil;

import java.util.Map;

/**
 * 作   者：赵大帅
 * 描   述: 多人加入房间
 * 日   期: 2017/11/30 19:08
 * 更新日期: 2017/11/30
 */
public class ChatRedAddAllPacket extends EaseChatRow {
    private TextView tv_message;
    private LinearLayout ll_container;
    private ImageView img_red;

    public ChatRedAddAllPacket(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.ADDUSERS)) {
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
            tv_message.setText("");
            if (!"".equals(message.getStringAttribute("userMap", ""))) {
                Map<String, String> map = FastJsonUtil.getObject(message.getStringAttribute("userMap", ""), new TypeToken<Map<String, String>>() {
                }.getType());
                String strNick = "";
                for (String nick : map.keySet()) {
                    strNick += map.get(nick) + ",";
                }
                if (strNick.length() > 0) {
                    strNick = strNick.substring(0, strNick.length() - 1);
                }
                if (strNick.length() > 30) {
                    strNick = strNick.substring(0, 30);
                    strNick += strNick + "等";
                }
                String messages = strNick + "加入了房间";
                if (strNick.length() > 0) {
                    final SpannableStringBuilder sp = new SpannableStringBuilder(messages);
                    sp.setSpan(new ForegroundColorSpan(0xffFA9E3B), 0, strNick.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
                    tv_message.setText(sp);
                } else {
                    tv_message.setText(strNick + "加入了房间");
                }
            }
        }
    }


}
