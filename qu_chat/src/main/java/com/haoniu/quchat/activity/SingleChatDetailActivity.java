package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.MyHelper;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.widget.EaseAlertDialog;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.exceptions.HyphenateException;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lhb
 * 单聊聊天详情
 */
public class SingleChatDetailActivity extends BaseActivity {

    private String toChatUsername;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.switch_top_conversation)
    Switch mSwitchTopConversation;
    @BindView(R.id.switch_black)
    Switch mSwitchBlack;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_single_chat_detail);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("聊天详情");

        String isTop = MyHelper.getInstance().getModel().getIsTop(toChatUsername);
        if (null != isTop && isTop.equals("toTop")) {
            mSwitchTopConversation.setChecked(true);
        } else {
            mSwitchTopConversation.setChecked(false);
        }

        //会话置顶
        mSwitchTopConversation.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
            //添加置顶消息
            synchronized (conversations) {
                for (EMConversation conversation : conversations.values()) {
                    if (conversation.getAllMessages().size() != 0 && !conversation.conversationId().equals(Constant.ADMIN)) {
                        if (conversation.conversationId().equals(toChatUsername)) {
                            if (isChecked) {
                                conversation.setExtField("toTop");
                                MyHelper.getInstance().getModel().saveChatBg(toChatUsername, null, null, "toTop");

                            } else {
                                conversation.setExtField("false");
                                MyHelper.getInstance().getModel().saveChatBg(toChatUsername, null, null, "false");
                            }
                        }
                    }
                }
            }
        });

        //加入黑名单
        mSwitchBlack.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                blackContact("1");
            } else {
                blackContact("0");
            }
        });

    }

    @Override
    protected void onEventComing(EventCenter center) {
        if (center.getEventCode() == EventUtil.SET_CHAT_BG) {
            finish();
        }

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        toChatUsername = extras.getString("username");
    }

    @OnClick({R.id.tv_chat_bg, R.id.tv_report, R.id.tv_clear_history, R.id.tv_del_contact})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_chat_bg:
                //聊天背景
                Bundle bundlebg = new Bundle();
                bundlebg.putString("from", "1");
                bundlebg.putString("username", toChatUsername);
                startActivity(ChatBgActivity.class, bundlebg);
                break;
            case R.id.tv_report:
                //举报
                break;
            case R.id.tv_clear_history:
                //清空聊天记录
                new EaseAlertDialog(SingleChatDetailActivity.this, null, "确定清空聊天记录吗？", null, (confirmed, bundle) -> {
                    if (confirmed) {
                        clearSingleChatHistory();
                    }
                }, true).show();
                break;
            case R.id.tv_del_contact:
                //删除联系人
                new EaseAlertDialog(SingleChatDetailActivity.this, null, "确定删除该联系人吗？", null, (confirmed, bundle) -> {
                    if (confirmed) {
                        deleteContact();
                    }
                }, true).show();
                break;
            default:
                break;
        }
    }

    /**
     * delete contact
     */
    public void deleteContact() {
        Map<String, Object> map = new HashMap<>(1);
        if (toChatUsername.contains(Constant.ID_REDPROJECT)) {
            toChatUsername = toChatUsername.split("-")[0];
        }
        map.put("friendUserId", toChatUsername);
        ApiClient.requestNetHandle(this, AppConfig.DEL_USER_FRIEND, "正在删除...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                try {
                    EMClient.getInstance().contactManager().deleteContact(toChatUsername);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                EventBus.getDefault().post(new EventCenter<>(EventUtil.DELETE_CONTACT));
                toast("删除成功");
                finish();

            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }


    /**
     * 拉黑好友
     */
    public void blackContact(String blackStatus) {
        Map<String, Object> map = new HashMap<>(2);
        if (toChatUsername.contains(Constant.ID_REDPROJECT)) {
            toChatUsername = toChatUsername.split("-")[0];
        }
        map.put("friendUserId", toChatUsername);
        //拉黑状态 0-未拉黑 1-已拉黑
        map.put("blackStatus", blackStatus);

        ApiClient.requestNetHandle(this, AppConfig.BLACK_USER_FRIEND, mSwitchBlack.isChecked() ? "正在拉黑..." : "正在取消拉黑...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (mSwitchBlack.isChecked()) {
                    toast("拉黑成功");
                } else {
                    toast("取消拉黑成功");
                }

            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }


    /**
     * 清空单聊聊天记录
     */
    private void clearSingleChatHistory() {
        EventBus.getDefault().post(new EventCenter<>(EventUtil.CLEAR_HUISTROY));
        Toast.makeText(this, R.string.messages_are_empty, Toast.LENGTH_SHORT).show();
    }
}
