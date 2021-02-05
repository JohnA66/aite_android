package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.MyHelper;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.LoginInfo;
import com.haoniu.quchat.global.Global;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.model.FriendInfo;
import com.haoniu.quchat.operate.UserOperateManager;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.utils.ProjectUtil;
import com.haoniu.quchat.utils.hxSetMessageFree.EaseSharedUtils;
import com.haoniu.quchat.view.MyDialog;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.zds.base.ImageLoad.GlideUtils;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.json.FastJsonUtil;

import com.zds.base.log.XLog;
import com.zds.base.util.StringUtil;
import com.zds.base.util.Utils;
import org.greenrobot.eventbus.EventBus;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lhb
 * 用户详情
 */
public class UserInfoDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.img_head)
    ImageView mImgHead;
    @BindView(R.id.tv_account)
    TextView mTvAccount;
    @BindView(R.id.tv_nick_name)
    TextView mTvNickName;
    @BindView(R.id.ll_friend)
    LinearLayout mLlFriend;
    @BindView(R.id.tv_add_friend)
    TextView mTvAddFriend;
    @BindView(R.id.line_status)
    TextView mTvLineStatus;
    @BindView(R.id.switch_msg)
    CheckBox mSwitchMsg;
    @BindView(R.id.switch_mute)
    CheckBox mSwitchMute;
    @BindView(R.id.rl_mute)
    View rlMute;
    @BindView(R.id.img_right)
    ImageView mImgRight;
    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubtitle;
    @BindView(R.id.tv_inviter)
    TextView tvInviter;
    @BindView(R.id.layout_inviter)
    RelativeLayout layoutInviter;
    @BindView(R.id.et_remark)
    EditText mEtRemark;
    @BindView(R.id.llay_remark)
    RelativeLayout mLlayRemark;
    @BindView(R.id.view_bottom)
    View mViewBottom;
    @BindView(R.id.kick_out)
    View kickOut;
    @BindView(R.id.switch_top_conversation)
    CheckBox mSwitchTopConversation;
    @BindView(R.id.fl_send_msg)
    FrameLayout flSendButton;
    private String userId;
    private String inviterUserId;
    private int chatType;
    private String emGroupId;
    private String groupId;
    private String from = "";
    private FriendInfo info;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_userinfo_detail);
    }

    @Override
    protected void initLogic() {
        mImgRight.setVisibility(View.VISIBLE);
        mViewBottom.setVisibility(View.GONE);
        mToolbarTitle.setVisibility(View.GONE);

        mImgRight.setImageResource(R.drawable.gengd);
        mToolbarTitle.setText("用户详情");
        mImgRight.setOnClickListener(v ->
                startActivity(new Intent(UserInfoDetailActivity.this, ChatMoreSetlActivity.class)
                        .putExtra(Constant.PARAM_EM_CHAT_ID, userId + Constant.ID_REDPROJECT)
                        .putExtra(Constant.NICKNAME, info.getFriendNickName())
                        .putExtra("isFriend", info.getFriendFlag().equals("1"))
                        .putExtra("from", "1")));

        if (userId.contains(UserComm.getUserId())) {
            //自己
            LoginInfo loginInfo =
                    UserComm.getUserInfo();
            GlideUtils.GlideLoadCircleErrorImageUtils(UserInfoDetailActivity.this, loginInfo.getUserHead(), mImgHead,
                    R.mipmap.img_default_avatar);
            mTvNickName.setText(loginInfo.getNickName());
            mTvAccount.setText(loginInfo.getUserCode());
            mLlFriend.setVisibility(View.GONE);
            mLlayRemark.setVisibility(View.GONE);
            rlMute.setVisibility(View.GONE);
            kickOut.setVisibility(View.GONE);
            mTvAddFriend.setVisibility(View.GONE);
        }else {
            if ("3".equals(from)) {
                mTvAddFriend.setText("移出黑名单");
            }

            queryFriendInfo();

            if (!TextUtils.isEmpty(inviterUserId)) {
                queryInviter();
            }

            if (!TextUtils.isEmpty(emGroupId)) {
                getGroupMuteList();
                rlMute.setVisibility(View.VISIBLE);
                kickOut.setVisibility(View.VISIBLE);
            }
        }


        String isMsgFree =
                MyHelper.getInstance().getModel().getConversionMsgIsFree(userId.contains(Constant.ID_REDPROJECT) ? userId :
                        userId + Constant.ID_REDPROJECT);
        if (null != isMsgFree && isMsgFree.equals("false")) {
            //设置消息免打扰
            mSwitchMsg.setChecked(true);
        } else {
            //取消消息免打扰
            mSwitchMsg.setChecked(false);
        }

        //消息免打扰
        mSwitchMsg.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!userId.contains(Constant.ID_REDPROJECT)) {
                userId += Constant.ID_REDPROJECT;
            }
            if (isChecked) {
                EaseSharedUtils.setEnableMsgRing(Utils.getContext(),
                        UserComm.getUserId() + Constant.ID_REDPROJECT,
                        userId, false);
                MyHelper.getInstance().getModel().saveChatBg(userId,
                        null, "false", null);
            } else {
                EaseSharedUtils.setEnableMsgRing(Utils.getContext(),
                        UserComm.getUserId() + Constant.ID_REDPROJECT,
                        userId, true);
                MyHelper.getInstance().getModel().saveChatBg(userId,
                        null, "true", null);
            }
        });

        mEtRemark.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    addRemark();
                }
                return false;
            }
        });


        EMConversation emConversation =  EMClient.getInstance().chatManager().getConversation(userId + Constant.ID_REDPROJECT);
        if (emConversation != null){
            if (emConversation.getExtField().equals("toTop")) {
                mSwitchTopConversation.setChecked(true);
            } else {
                mSwitchTopConversation.setChecked(false);
            }
        }


        //会话置顶
        mSwitchTopConversation.setOnCheckedChangeListener((buttonView,
                                                           isChecked) -> {

            if (!emConversation.conversationId().equals(Constant.ADMIN)) {
                if (emConversation.conversationId().contains(userId)) {
                    if (isChecked) {
                        emConversation.setExtField("toTop");
                    } else {
                        emConversation.setExtField("false");
                    }
                }
            }
        });
    }

    private void setMuteListener() {
        mSwitchMute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                modifyGroupUserSayStatus(b);
            }
        });
    }

    private void addRemark() {
        Map<String, Object> map = new HashMap<>();
        if (userId.contains(Constant.ID_REDPROJECT)) {
            userId = userId.split("-")[0];
        }
        map.put("friendUserId", userId);
        map.put("friendNickName", mEtRemark.getText().toString().trim());
        ApiClient.requestNetHandle(this, AppConfig.ADD_GOODS_FRIEND_REMARK,
                "请稍后...", map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        String name = mEtRemark.getText().toString().trim();
                        UserOperateManager.getInstance().updateUserName(userId,name);
                        mTvNickName.setText(StringUtil.isEmpty(name) ? info.getNickName() + " " : name);

                        EventBus.getDefault().post(new EventCenter<>(EventUtil.REFRESH_REMARK));
                        toast("设置成功");
                    }

                    @Override
                    public void onFailure(String msg) {
                        toast(msg);
                    }
                });
    }

    @Override
    protected void onEventComing(EventCenter center) {
        if (center.getEventCode() == EventUtil.OPERATE_BLACK || center.getEventCode() == EventUtil.DELETE_CONTACT) {
            finish();
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        groupId = extras.getString(Constant.PARAM_GROUP_ID);
        emGroupId = extras.getString(Constant.PARAM_EM_GROUP_ID);
        userId = extras.getString("friendUserId");
        chatType = extras.getInt("chatType");
        from = extras.getString("from");
        inviterUserId = extras.getString("entryUserId");
    }

    private void getGroupMuteList() {

        EMClient.getInstance().groupManager().asyncFetchGroupMuteList(emGroupId, 0, 10000, new EMValueCallBack<Map<String, Long>>() {
            @Override
            public void onSuccess(Map<String, Long> stringLongMap) {
                for (String key : stringLongMap.keySet()) {
                    if (key.contains(userId))
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSwitchMute.setChecked(true);
                            }
                        });
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setMuteListener();
                    }
                });


            }

            @Override
            public void onError(int i, String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setMuteListener();
                    }
                });
            }
        });

    }

    private void modifyGroupUserSayStatus(boolean isMute) {

        Map<String, Object> map = new HashMap<>(1);
        if (userId.contains(Constant.ID_REDPROJECT)) {
            userId = userId.split("-")[0];
        }
        String[] s = new String[1];
        s[0] = userId;
        String s1 = FastJsonUtil.toJSONString(s);
        map.put("groupId", groupId);
        map.put("userIds", s);
        map.put("sayStatus", isMute ? 1 : 0);

        ApiClient.requestNetHandle(this, AppConfig.MODIFY_GROUP_USER_SAY_STATUS, "请求提交中", map,
                new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        XLog.e("mute", msg);
                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                });

//        List<String> muteMembers = new ArrayList<>();
//        muteMembers.add(friendUserId+Constant.ID_REDPROJECT);
//        if (isMute) {
//
//            EMClient.getInstance().groupManager().aysncMuteGroupMembers(emGroupId, muteMembers, 12*30*24*60*60*1000, new EMValueCallBack<EMGroup>() {
//                @Override
//                public void onSuccess(EMGroup emGroup) {}
//
//                @Override
//                public void onError(int i, String s) {}
//            });
//        }else {
//            EMClient.getInstance().groupManager().asyncUnMuteGroupMembers(emGroupId, muteMembers, new EMValueCallBack<EMGroup>() {
//                @Override
//                public void onSuccess(EMGroup emGroup) {}
//
//                @Override
//                public void onError(int i, String s) {}
//            });
//        }


    }

    private void queryFriendInfo() {
        Map<String, Object> map = new HashMap<>(1);
        if (userId.contains(Constant.ID_REDPROJECT)) {
            userId = userId.split("-")[0];
        }
        map.put("friendUserId", userId);

        ApiClient.requestNetHandle(this, AppConfig.FRIEND_INFO, "", map,
                new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        if (null != json && json.length() > 0) {
                            info = FastJsonUtil.getObject(json,
                                    FriendInfo.class);
                            GlideUtils.GlideLoadCircleErrorImageUtils(UserInfoDetailActivity.this, info.getUserHead(), mImgHead,
                                    R.mipmap.img_default_avatar);

                            mTvAccount.setText(getString(R.string.str_chat_account,info.getUserCode()));
                            mTvLineStatus.setText(info.getLine().equals(
                                    "online") ?
                                    "在线" : "离线");
                            mTvNickName.setText(info.getNickName());
                            //好友标识 0 不是好友 1 是好友
                            if (info.getFriendFlag().equals("1")) {
                                if (info.getBlackStatus().equals("1")) {
                                    //黑名单中
                                    mLlFriend.setVisibility(View.GONE);
                                    mTvAddFriend.setVisibility(View.VISIBLE);
                                    mTvAddFriend.setText("移出黑名单");

                                } else {
                                    flSendButton.setVisibility(View.VISIBLE);
                                    mLlFriend.setVisibility(View.VISIBLE);
                                    mTvAddFriend.setVisibility(View.GONE);
                                }
                                mLlayRemark.setVisibility(View.VISIBLE);
                                try {
                                    String remark =
                                            UserOperateManager.getInstance().getUserName(userId);
                                    mEtRemark.setText(info.getFriendNickName());
                                    mEtRemark.setSelection(StringUtil.isEmpty(remark) ? 0 :
                                            remark.length());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                mTvAddFriend.setVisibility(View.VISIBLE);
                                mLlFriend.setVisibility(View.GONE);
                                mLlayRemark.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        toast(msg);
                    }
                });
    }

    private void queryInviter() {
        Map<String, Object> map = new HashMap<>(1);
        map.put("friendUserId", inviterUserId);
        ApiClient.requestNetHandle(this, AppConfig.FRIEND_INFO, "", map,
                new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        FriendInfo inviterInfo = FastJsonUtil.getObject(json,
                                FriendInfo.class);

                        layoutInviter.setVisibility(View.VISIBLE);
                        tvInviter.setText(inviterInfo.getNickName());
                    }

                    @Override
                    public void onFailure(String msg) {
                        toast(msg);
                    }
                });
    }

    /**
     * 移出群员
     *
     * @param
     */
    private void delGroupUser() {
        Map<String, Object> map = new HashMap<>(1);
        map.put("groupId", groupId);
        map.put("userId", userId);

        ApiClient.requestNetHandle(this, AppConfig.DEL_GROUP_USER, "正在踢出成员...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                toast("踢出成功");
                EventBus.getDefault().post(new EventCenter<>(EventUtil.INVITE_USER_ADD_GROUP));
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }


    @OnClick({ R.id.tv_chat_record, R.id.fl_send_msg, R.id.tv_add_friend,R.id.kick_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.kick_out:
                delGroupUser();
            break;

            case R.id.tv_chat_record:
                //查找聊天记录
                startActivity(new Intent(this, ChatRecordActivity.class).putExtra("chatId", userId + Constant.ID_REDPROJECT));

                break;
            case R.id.fl_send_msg:
                //发消息
                if (chatType == Constant.CHATTYPE_SINGLE) {
                    finish();
                } else {
                    Intent intent = new Intent(this, ChatActivity.class);
                    if (!userId.contains(Constant.ID_REDPROJECT)) {
                        userId += Constant.ID_REDPROJECT;
                    }
                    intent.putExtra(Constant.EXTRA_USER_ID, userId);
                    startActivity(intent);
                }
                break;
            case R.id.tv_add_friend:
                //加好友
                //好友拉黑状态
                if (info.getFriendFlag().equals("1")) {
                    if (info.getBlackStatus() != null && info.getBlackStatus().equals("1")) {
                        blackContact();
                    }
                } else {
                    addUser();
                }

//                if (from.equals("3")) {
//                } else {
//                    addUser();
//                }
                break;
//            case R.id.tv_report:
//                //举报
//                startActivity(new Intent(this, ReportActivity.class).putExtra("from", "1").putExtra("userGroupId", userId));
//                break;
            default:
                break;
        }
    }

    /**
     * 添加好友
     */
    private void addUser() {
        Map<String, Object> map = new HashMap<>();
        map.put("toUserId", ProjectUtil.transformId(userId));
        if (!TextUtils.isEmpty(Global.addUserOriginType))
            map.put("originType", Global.addUserOriginType);
        if (!TextUtils.isEmpty(Global.addUserOriginId)) {
            map.put("originName", Global.addUserOriginName);
            map.put("originId", Global.addUserOriginId);
        }
        ApiClient.requestNetHandle(this, AppConfig.APPLY_ADD_USER, "正在添加",
                map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        EMMessage cmdMsg =
                                EMMessage.createSendMessage(EMMessage.Type.CMD);

                        //支持单聊和群聊，默认单聊，如果是群聊添加下面这行
                        //cmdMsg.setChatType(ChatType.GroupChat)
                        //action可以自定义
                        String action = Constant.ACTION_APPLY_ADD_FRIEND;
                        EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
                        cmdMsg.addBody(cmdBody);
                        //发送给某个人
                        String toUsername =
                                userId.contains(Constant.ID_REDPROJECT) ?
                                        userId :
                                        userId + Constant.ID_REDPROJECT;

                        cmdMsg.setTo(toUsername);
                        cmdMsg.setFrom(UserComm.getUserId());
                        cmdMsg.setAttribute(Constant.APPLY_ADD_FRIEND_ID,
                                UserComm.getUserInfo().getUserId());

                        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
                        toast(msg);

                        Global.addUserOriginType    = "";
                        Global.addUserOriginName    = "";
                        Global.addUserOriginId      = "";

                        finish();
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.toast(msg);
                    }
                });


    }


    /**
     * 解除拉黑好友
     */
    public void blackContact() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("friendUserId", userId);
        //拉黑状态 0-未拉黑 1-已拉黑
        map.put("blackStatus", "0");

        ApiClient.requestNetHandle(this, AppConfig.BLACK_USER_FRIEND,
                "正在移除黑名单...", map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        EventBus.getDefault().post(new EventCenter<>(EventUtil.REFRESH_BLACK));
                        toast(" 移除成功");
                        finish();
                    }

                    @Override
                    public void onFailure(String msg) {
                        toast(msg);
                    }
                });
    }


    /**
     * delete contact
     */
    public void deleteContact() {
        Map<String, Object> map = new HashMap<>();
        map.put("friendUserId", userId);
        ApiClient.requestNetHandle(this, AppConfig.DEL_USER_FRIEND, "正在删除...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {

                try {
                    EMClient.getInstance().contactManager().deleteContact(userId + Constant.ID_REDPROJECT);
                    toast(" 删除成功");
                    finish();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }

}
