package com.haoniu.quchat.widget.my_message;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.activity.PayPasswordActivity;
import com.haoniu.quchat.activity.RedPacketDetailActivity;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.widget.chatrow.EaseChatRow;
import com.hyphenate.chat.EMMessage;
import com.zds.base.json.FastJsonUtil;

public class WalletPacket extends EaseChatRow {
    private TextView tvCreateTime, tvContent;

    private TextView tv_way;

    private TextView tvTips;
    private TextView tvBank;
    private TextView tvBeginTime;
    private TextView tvOverTime;
    private TextView tvReason;
    private View lyBegin;
    private View lyOver;
    private View lyReason;


    private TextView tvBackTime;
    private View tvCheck;

    String json;
    private String type;
    private String status;
    private String createTime;
    private String updateTime;

    private String money;
    private String bankInfo;
    private String linkId;
    /*
    {
            "bankInfo": "工商银行 (21ec)",
            "linkId": "f28dd78a256b11eb880498039b1b499c",
            "msgType": "walletMsg",
            "money": "1",
            "createTime": "Fri Nov 13 12:51:52 CST 2020",
            "updateTime": "Fri Nov 13 12:51:52 CST 2020",
            "type": "WITHDRAW",
            "status": "0"
    }
 */
    public WalletPacket(Context context, EMMessage message,
                        int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        json = FastJsonUtil.toJSONString(message.ext());
        type = FastJsonUtil.getString(json, "type");
        createTime = FastJsonUtil.getString(json, "createTime");

        switch (type) {
            case "1":
                inflater.inflate(R.layout.chat_wallet_recharge, this);
                break;
            case "2":
                inflater.inflate(R.layout.chat_wallet_withdraw, this);
                break;
            case "3":
                inflater.inflate(R.layout.chat_wallet_redpackage, this);
                break;
            case "4":
                inflater.inflate(R.layout.chat_wallet_account, this);
                break;

        }
    }

    @Override
    protected void onFindViewById() {
        tvContent = findViewById(R.id.tv_content);
        tvCreateTime = findViewById(R.id.tv_create_time);

        if (type.equals("1")) {
            tv_way = findViewById(R.id.tv_way);
        } else if (type.equals("2")) {
            tvTips = findViewById(R.id.tv_tips);
            tvBank = findViewById(R.id.tv_bank);
            tvBeginTime = findViewById(R.id.tv_begin_time);
            tvOverTime = findViewById(R.id.tv_over_time);
            tvReason = findViewById(R.id.tv_reason);
            lyBegin = findViewById(R.id.ly_begin);
            lyOver = findViewById(R.id.ly_over);
            lyReason = findViewById(R.id.ly_reason);
        } else if (type.equals("3")) {
            tvCheck = findViewById(R.id.tv_check);
            tvBackTime = findViewById(R.id.tv_back_time);
        } else if (type.equals("4")) {
            tvCheck = findViewById(R.id.tv_check);
        }
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
        tvCreateTime.setText(createTime);
        switch (type) {
            case "1":
                status = FastJsonUtil.getString(json, "status");
                money = FastJsonUtil.getString(json, "money");
                bankInfo = FastJsonUtil.getString(json, "bankInfo");
                tv_way.setText(bankInfo);
                tvContent.setText(money);
                break;
            case "2":
                money = FastJsonUtil.getString(json, "money");
                updateTime= FastJsonUtil.getString(json, "updateTime");
                bankInfo = FastJsonUtil.getString(json, "bankInfo");
                status = FastJsonUtil.getString(json, "status");
                tvContent.setText(money);
                tvBank.setText(bankInfo);
                tvBeginTime.setText(createTime);
                tvOverTime.setText(updateTime);
                if (status.equals("0")) {
                    tvTips.setText("发起提现");
                    tvOverTime.setText("预计2小时内到账");
                }else if (status.equals("1")) {
                    tvTips.setText("提现到账");
                }else if (status.equals("-1")) {
                    tvTips.setText("提现失败");
                    lyReason.setVisibility(View.VISIBLE);
                    lyOver.setVisibility(View.GONE);
                }
                break;
            case "3":
                money = FastJsonUtil.getString(json, "money");
                linkId = FastJsonUtil.getString(json, "linkId");
                tvBackTime.setText(createTime);
                tvContent.setText(money);
                tvCheck.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getContext().startActivity(
                                new Intent(getContext(), RedPacketDetailActivity.class)
                                        .putExtra("rid", linkId)
                                        .putExtra("fromRecord", true)
                                        .putExtra("head", UserComm.getUserInfo().getUserHead())
                                        .putExtra("nickname", UserComm.getUserInfo().getNickName()));

                    }
                });
                break;
            case "4":
                tvCheck.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getContext().startActivity(new Intent(getContext(), PayPasswordActivity.class));
                    }
                });
                break;

        }
    }


}
