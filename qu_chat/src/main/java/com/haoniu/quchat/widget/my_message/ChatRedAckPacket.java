package com.haoniu.quchat.widget.my_message;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.base.MyHelper;

import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.operate.UserOperateManager;
import com.haoniu.quchat.widget.chatrow.EaseChatRow;
import com.hyphenate.chat.EMMessage;
import com.zds.base.json.FastJsonUtil;


public class ChatRedAckPacket extends EaseChatRow {
    private TextView tv_message;
    private ImageView mImgRed;
    private LinearLayout ll_container;

    public ChatRedAckPacket(Context context, EMMessage message, int position,
                            BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.RAB)
                || message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.RAB_SELF)
                || Constant.TURN.equals(message.getStringAttribute(Constant.MSGTYPE, ""))
                || Constant.SURE_TURN.equals(message.getStringAttribute(Constant.MSGTYPE, ""))) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.row_red_packet_ack_message :
                    R.layout.row_red_packet_ack_message, this);
        }
    }

    @Override
    protected void onFindViewById() {
        tv_message = (TextView) findViewById(R.id.tv_money_msg);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        mImgRed = (ImageView) findViewById(R.id.img_red);
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
        String currentUserId = UserComm.getUserInfo().getUserId() + "";
        String robUserId = message.getStringAttribute("id", "");//id
        String robUserName = message.getStringAttribute(Constant.NICKNAME,"");//昵称

        String sendRedPackageUserId = message.getStringAttribute("sendid", "");//发红包的id
        String sendRedPackageNickName = message.getStringAttribute("sendnickname","");//发红包的昵称

        ll_container.setVisibility(VISIBLE);
        mImgRed.setVisibility(VISIBLE);
        if (message.getChatType().equals(EMMessage.ChatType.GroupChat)) {
            //改成透传接口后字段名改变了
            if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.RAB_SELF)){
                robUserId = message.getStringAttribute("sendid", "");//id
                robUserName = message.getStringAttribute("sendnickname","");//昵称
                sendRedPackageUserId = message.getStringAttribute("id", "");
                sendRedPackageNickName = message.getStringAttribute("nickname","");
            }

            if (robUserId.equals(currentUserId)) {
                String message = "";
                if (robUserId.equals(sendRedPackageUserId)) {
                    message =
                            getResources().getString(R.string.msg_take_red_packet);
                } else {
                    //查看是否存在别名
                    String localNikeName = UserOperateManager.getInstance().getUserName(sendRedPackageUserId);
                    if (!TextUtils.isEmpty(localNikeName))
                        sendRedPackageNickName = localNikeName;

                    message =
                            String.format(getResources().getString(R.string.msg_take_someone_red_packet), sendRedPackageNickName);
                }
                final SpannableStringBuilder sp =
                        new SpannableStringBuilder(message);
                sp.setSpan(new ForegroundColorSpan(0xffFA9E3B), 0, 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
                sp.setSpan(new ForegroundColorSpan(0xffFA9E3B), 4,
                        message.length() - 3,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
                tv_message.setText(sp);
            } else if (sendRedPackageUserId.equals(currentUserId)) {

                String localNikeName = UserOperateManager.getInstance().getUserName(robUserId);
                if (!TextUtils.isEmpty(localNikeName))
                    robUserName = localNikeName;

                String message =
                        String.format(getResources().getString(R.string.msg_someone_take_red_packet), robUserName, "你");
                final SpannableStringBuilder sp =
                        new SpannableStringBuilder(message);
                sp.setSpan(new ForegroundColorSpan(0xffFA9E3B), 0,
                        robUserName.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
                sp.setSpan(new ForegroundColorSpan(0xffFA9E3B),
                        robUserName.length() + 3,
                        robUserName.length() + 3 + 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
                tv_message.setText(sp);
            } else {
                String localRobNikeName = UserOperateManager.getInstance().getUserName(robUserId);
                if (!TextUtils.isEmpty(localRobNikeName))
                    robUserName = localRobNikeName;

                if (UserOperateManager.getInstance().hasUserName(sendRedPackageUserId))
                    sendRedPackageNickName = UserOperateManager.getInstance().getUserName(sendRedPackageUserId);

                String message =
                        String.format(getResources().getString(R.string.msg_someone_take_red_packet), robUserName, sendRedPackageNickName);
                final SpannableStringBuilder sp =
                        new SpannableStringBuilder(message);
                sp.setSpan(new ForegroundColorSpan(0xffFA9E3B), 0,
                        robUserName.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
                sp.setSpan(new ForegroundColorSpan(0xffFA9E3B),
                        robUserName.length() + 3,
                        robUserName.length() + 3 + sendRedPackageNickName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
                tv_message.setText(sp);
                ll_container.setVisibility(GONE);
            }
        } else {
            if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.RAB_SELF)) {
                String fromUserId = message.getFrom();
                String localRobNikeName = UserOperateManager.getInstance().getUserName(fromUserId);
                if (!TextUtils.isEmpty(localRobNikeName))
                    robUserName = localRobNikeName;
                sendRedPackageNickName = "你";
            } else if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.RAB)) {
                String fromUserId = message.getFrom();
                robUserName = "你";
            } else if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.TURN)) {
                robUserName = "你已收到转账";
                mImgRed.setVisibility(GONE);
                tv_message.setText(robUserName);
                return;
            } else if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.SURE_TURN)) {
                robUserName = "你已转账成功";
                mImgRed.setVisibility(GONE);
                tv_message.setText(robUserName);
                return;
            }
            String message =
                    String.format(getResources().getString(R.string.msg_someone_take_red_packet), robUserName, sendRedPackageNickName);
            final SpannableStringBuilder sp =
                    new SpannableStringBuilder(message);
            sp.setSpan(new ForegroundColorSpan(0xffFA9E3B), 0,
                    robUserName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
            sp.setSpan(new ForegroundColorSpan(0xffFA9E3B),
                    robUserName.length() + 3,
                    robUserName.length() + 3 + sendRedPackageNickName.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
            tv_message.setText(sp);
        }
    }


}
