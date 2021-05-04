package com.haoniu.quchat.widget.my_message;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.widget.chatrow.EaseChatRow;
import com.hyphenate.chat.EMMessage;
import com.zds.base.Toast.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class GroupNoticePacket extends EaseChatRow {
    private TextView content;
    private TextView agree;
    private Map<String,Object> paramsMap;

    public GroupNoticePacket(Context context, EMMessage message,
                             int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        paramsMap = message.ext();
        inflater.inflate(R.layout.chat_group_notice, this);
    }

    @Override
    protected void onFindViewById() {
        agree   = findViewById(R.id.agree);
        content = findViewById(R.id.content);
    }

    /**
     * refresh view when message status change
     *
     * @param msg
     */
    @Override
    protected void onViewUpdate(EMMessage msg) { }


    @Override
    protected void onSetUpView() {
        paramsMap = message.ext();

        String systemNotice = getContext().getString(R.string.msg_group_invite_user,
                paramsMap.get("inviterName"),
                paramsMap.get("nickname")
        );
        content.setText(systemNotice);
        agree.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                paramsMap = message.ext();
                agreeApply();
            }
        });
    }

    /**
     * 同意入群申请
     */
    private void agreeApply() {
        Map<String, Object> map = new HashMap<>();
        map.put("applyId", paramsMap.get("applyId"));
        map.put("applyStatus", "1");
        map.put("groupId", paramsMap.get("groupId"));
        map.put("userId", paramsMap.get("userID"));

        ApiClient.requestNetHandle(getContext(), AppConfig.AGREE_GROUP_USER,  "正在同意...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast("已同意");
                EventBus.getDefault().post(new EventCenter<>(EventUtil.ROOM_INFO));
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });

    }

}
