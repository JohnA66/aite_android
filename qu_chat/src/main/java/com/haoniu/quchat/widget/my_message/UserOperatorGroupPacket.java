package com.haoniu.quchat.widget.my_message;

import android.content.Context;
import android.text.TextUtils;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.operate.UserOperateManager;
import com.haoniu.quchat.utils.EaseCommonUtils;
import com.haoniu.quchat.utils.StringUtil;
import com.haoniu.quchat.widget.EaseImageView;
import com.haoniu.quchat.widget.chatrow.EaseChatRow;
import com.hyphenate.chat.EMMessage;

/**
 * @author lhb
 * 名片
 */
public class UserOperatorGroupPacket extends EaseChatRow {
    private TextView tv_message,tv_message_new;
    private EaseImageView img_card_head;
    private LinearLayout ll_container,llay_msg;

    public UserOperatorGroupPacket(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }


    @Override
    protected void onInflateView() {
        String msgType = message.getStringAttribute(Constant.MSGTYPE, "");
        if ("addgroupuser".equals(msgType) || "delgroupuser".equals(msgType)) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.system_notice_message :
                    R.layout.system_notice_message, this);
        }
    }

    @Override
    protected void onFindViewById() {
        tv_message = (TextView) findViewById(R.id.tv_msg);
        tv_message_new = (TextView) findViewById(R.id.tv_msg_new);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        llay_msg = (LinearLayout) findViewById(R.id.llay_msg);
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {

    }

    @Override
    protected void onSetUpView() {
        String systemNotice = EaseCommonUtils.getMessageDigest(message,
                getContext());
        String msgType = message.getStringAttribute(Constant.MSGTYPE, "");
        if ("addgroupuser".equals(msgType) || "delgroupuser".equals(msgType)) {

            String inviterId = message.getStringAttribute(Constant.INVITER_ID, "");
            String userId = message.getStringAttribute(Constant.USER_ID, "");
            String delUserId = message.getStringAttribute(Constant.DEL_USER_ID, "");
            String nickName = message.getStringAttribute(Constant.USER_ADDNIKE, "");
            String operaterNickName = message.getStringAttribute(Constant.NICKNAME, "");
            if ("addgroupuser".equals(msgType) ) {
                nickName = TextUtils.isEmpty(UserOperateManager.getInstance().getUserName(inviterId))?nickName:UserOperateManager.getInstance().getUserName(inviterId);
                operaterNickName = UserOperateManager.getInstance().hasUserName(userId)?UserOperateManager.getInstance().getUserName(userId):operaterNickName;
                if (!TextUtils.isEmpty(nickName) && !TextUtils.isEmpty(operaterNickName)){
                    systemNotice = getContext().getString(R.string.msg_group_invite_user,
                            operaterNickName,
                            nickName
                    );
                }
            }else if ("delgroupuser".equals(msgType) ){
                if (UserOperateManager.getInstance().hasUserName(delUserId)){
                    nickName = UserOperateManager.getInstance().getUserName(delUserId);
                }
                if (UserOperateManager.getInstance().hasUserName(userId)){
                    operaterNickName = UserOperateManager.getInstance().getUserName(userId);
                }

                if (!TextUtils.isEmpty(nickName) && !TextUtils.isEmpty(operaterNickName)){
                    systemNotice = getContext().getString(R.string.msg_group_remove_user,
                            operaterNickName,
                            nickName
                    );
                }
            }
            tv_message_new.setVisibility(VISIBLE);
            llay_msg.setVisibility(GONE);
            tv_message_new.setText(StringUtil.isEmpty(systemNotice) ? "" :
                    systemNotice);
            return;
        }
        tv_message_new.setVisibility(GONE);
        llay_msg.setVisibility(VISIBLE);
        tv_message.setText(StringUtil.isEmpty(systemNotice) ? "" :
                systemNotice);
    }
}
