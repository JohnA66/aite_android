package com.haoniu.quchat.widget.my_message;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.widget.chatrow.EaseChatRow;
import com.hyphenate.chat.EMMessage;


/**
 * 作   者：赵大帅
 * 描   述: 提现审核
 * 日   期: 2017/11/27 17:26
 * 更新日期: 2017/11/27
 */
public class ChatWithdrawPacket extends EaseChatRow {
    private TextView tv_money,apply_state,tv_time_show,tv_refuse,tv_money_fee,tv_message_ts;
    private LinearLayout ll_refuse,ll_money_fee;

    public ChatWithdrawPacket(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.WITHDRAW)) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.apply_withdraw_notice : R.layout.apply_withdraw_notice, this);
        }
    }

    @Override
    protected void onFindViewById() {
        //提现金额
        tv_money = (TextView) findViewById(R.id.tv_money);
        //提现审核状态
        apply_state=(TextView)findViewById(R.id.apply_state);
        //审核时间
        tv_time_show=(TextView)findViewById(R.id.tv_time_show);
        ll_refuse=(LinearLayout) findViewById(R.id.ll_refuse);
        //拒绝理由
        tv_refuse=(TextView)findViewById(R.id.tv_refuse);
        ll_money_fee=(LinearLayout) findViewById(R.id.ll_money_fee);
        //手续费
        tv_money_fee=(TextView)findViewById(R.id.tv_money_fee);
        //温馨提示
        tv_message_ts=(TextView)findViewById(R.id.tv_message_ts);
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

        //审核状态 1.审核成功 2 审核失败 3 等待审核
        int state=message.getIntAttribute("status", 0);
        //提现金额
        String money=message.getStringAttribute("money", "");
        //手续费
        String proceMoney=message.getStringAttribute("proceMoney", "");
        //审核时间
        String updateTime=message.getStringAttribute("updateTime", "");
        //温馨提示
        String tips=message.getStringAttribute("tips", "");
        //拒绝原因
        String description=message.getStringAttribute("description", "");
        if (state==1){
            ll_money_fee.setVisibility(VISIBLE);
            ll_refuse.setVisibility(GONE);
            tv_money_fee.setText(proceMoney+context.getResources().getString(R.string.glod));
            tv_money.setText(money+context.getResources().getString(R.string.glod));
            tv_time_show.setText(updateTime);
            tv_message_ts.setText(tips);
            apply_state.setText("通过审核");
        }else if (state==2){
            ll_refuse.setVisibility(VISIBLE);
            ll_money_fee.setVisibility(GONE);
            tv_money.setText(money+context.getResources().getString(R.string.glod));
            tv_time_show.setText(updateTime);
            tv_message_ts.setText(tips);
            tv_refuse.setText(description);
            apply_state.setText("未通过审核");
        }else {
            ll_money_fee.setVisibility(GONE);
            ll_refuse.setVisibility(GONE);
        }
    }
}
