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
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.widget.chatrow.EaseChatRow;
import com.hyphenate.chat.EMMessage;

public class ChatRedWelfarePacket extends EaseChatRow {
    private TextView tv_message;
    private LinearLayout ll_container;
    private ImageView img_red;

    public ChatRedWelfarePacket(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.WELFARE)) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.row_red_packet_ack_message : R.layout.row_red_packet_ack_message, this);
        }
    }

    @Override
    protected void onFindViewById() {
        tv_message = (TextView) findViewById(R.id.tv_money_msg);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        img_red = findViewById(R.id.img_red);
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
//        img_red.setVisibility(GONE);
        img_red.setImageResource(R.mipmap.img_welfare);
        if (message.getChatType().equals(EMMessage.ChatType.GroupChat)) {
            String name = message.getStringAttribute(Constant.NAME, "");
            String welfaretypes = message.getStringAttribute(Constant.WELFARETYPE, "");
            String welfaretype = "'" + message.getStringAttribute(Constant.WELFARETYPE, "") + "'";
            String welfaretMoney = "" + message.getStringAttribute(Constant.WELFARE, "") + "";
            if (message.getStringAttribute("id", "").equals(UserComm.getUserInfo().getUserId())) {
                name = "你";
            }
//            welfaretype = ((infoMap != null && infoMap.get(welfaretypes) != null) ? infoMap.get(welfaretypes).getItemName() : welfaretype);
            String messageStr = "恭喜" + name + "获得福利包" + welfaretype + "，奖励" + welfaretMoney + context.getResources().getString(R.string.glod);
            if (name.length() > 0) {
                final SpannableStringBuilder sp = new SpannableStringBuilder(messageStr);
                sp.setSpan(new ForegroundColorSpan(0xffFA9E3B), 2, 2 + name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
                sp.setSpan(new ForegroundColorSpan(0xffFA9E3B), 7 + name.length(), 7 + name.length() + welfaretype.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
                sp.setSpan(new ForegroundColorSpan(0xffFA9E3B), 10 + name.length() + welfaretype.length(), 10 + name.length() + welfaretype.length() + welfaretMoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
                tv_message.setText(sp);
            } else {
                tv_message.setText(messageStr);
            }

        }
    }
}
