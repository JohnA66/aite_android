package com.haoniu.quchat.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.aite.chat.R;
import com.haoniu.quchat.activity.AddContactActivity;
import com.haoniu.quchat.activity.ChatActivity;
import com.haoniu.quchat.activity.ContactActivity;
import com.haoniu.quchat.activity.MainActivity;
import com.haoniu.quchat.activity.MyQrActivity;
import com.haoniu.quchat.activity.UserInfoDetailActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.db.InviteMessgeDao;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.global.Global;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.model.EaseAtMessageHelper;
import com.haoniu.quchat.operate.UserOperateManager;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.widget.EaseConversationList;
import com.haoniu.quchat.widget.PopWinShare;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.chat.EMMessage;
import com.zds.base.code.activity.CaptureActivity;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.DensityUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

import static com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE;
import static com.zds.base.code.activity.CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN;

/**
 * @author Administrator
 */
public class ConversationListFragment extends BaseConversationListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ease_fragment_conversation_list
                , container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onEventComing(EventCenter center) {
        //刷新通讯录和本地数据库
        if (center.getEventCode() == EventUtil.REFRESH_REMARK) {
            refresh();
        }else if (center.getEventCode() == EventUtil.CHECK_MULTI_STATUS){
            getMultiDevices();
        }
    }

    @Override
    protected void initLogic() {
        super.initLogic();
        titleBar.setBar();
        conversationListView.setDrag(true);

        mImgShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    showPopudown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                conversationListView.filter(s);
                if (s.length() > 0) {
                    clearSearch.setVisibility(View.VISIBLE);
                } else {
                    clearSearch.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        conversationListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation =
                        conversationListView.getItem(position);
                String emUserId = conversation.conversationId();

                if (id == Integer.MAX_VALUE) {
                    //删除和某个user会话，如果需要保留聊天记录，传false
                    EMClient.getInstance().chatManager().deleteConversation(emUserId, true);
                    refresh();
                    return;
                }

                if (emUserId.equals(EMClient.getInstance().getCurrentUser())) {
                    Toast.makeText(getActivity(),
                            R.string.Cant_chat_with_yourself,
                            Toast.LENGTH_SHORT).show();
                } else {
                    // start chat acitivity
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    boolean isSystem = false;
                    if (conversation != null && conversation.getLastMessage() != null && conversation.getLastMessage().ext() != null) {
                        String json = FastJsonUtil.toJSONString(conversation.getLastMessage().ext());

                        if (json.contains("msgType")) {
                            String msgType = FastJsonUtil.getString(json, "msgType");

                            if ("systematic".equals(msgType)){
                                intent.putExtra(Constant.NICKNAME,"艾特官方");
                                isSystem = true;
                            }else  if ("walletMsg".equals(msgType)){
                                intent.putExtra(Constant.NICKNAME,"钱包助手");
                                isSystem = true;
                            }

                        }
                        try {
                            emUserId = conversation.conversationId();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (conversation.isGroup()) {
                        if (conversation.getType() == EMConversationType.ChatRoom) {
                            // it's group chat
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
                        } else {
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                        }
                        //移除群组at标志
                        EaseAtMessageHelper.get().removeAtMeGroup(emUserId);
                    } else {
                        //设置单聊中环信ID是否包含 -youxin (不包含，加上)
                        if (!emUserId.contains(Constant.ID_REDPROJECT)) {
                            emUserId += Constant.ID_REDPROJECT;
                        }
                    }
                    // it's single chat
                    intent.putExtra(Constant.EXTRA_USER_ID, emUserId);
                    intent.putExtra("isSystem", isSystem);
                    startActivity(intent);
                }
            }
        });
        //red packet code : 红包回执消息在会话列表最后一条消息的展示

        conversationListView.setConversationListHelper(new EaseConversationList.EaseConversationListHelper() {
            @Override
            public String onSetItemSecondaryText(EMMessage lastMessage) {
                if (lastMessage.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.RETURNGOLD)) {
                    return "红包退还通知";
                } else if ("系统管理员".equals(lastMessage.getFrom())) {
                    return "房间创建成功";
                } else if (lastMessage.getBooleanAttribute("cmd", false)) {
                    return "";
                }
                return null;
            }
        });
        getMultiDevices();
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean deleteMessage = false;
        if (item.getItemId() == R.id.delete_message) {
            deleteMessage = true;
        } else if (item.getItemId() == R.id.delete_conversation) {
            deleteMessage = false;
        }
        EMConversation tobeDeleteCons =
                conversationListView.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
        if (tobeDeleteCons == null) {
            return true;
        }

        try {
            // delete conversation
            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.conversationId(), deleteMessage);
            InviteMessgeDao inviteMessgeDao =
                    new InviteMessgeDao(getActivity());
            inviteMessgeDao.deleteMessage(tobeDeleteCons.conversationId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();

        // update unread count
        ((MainActivity) getActivity()).updateUnreadLabel();
        return true;
    }



    private PopWinShare popWinShare;

    /**
     * 显示浮窗菜单
     */
    private void showPopudown() {
        if (popWinShare == null) {
            View.OnClickListener paramOnClickListener =
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //扫一扫
                            if (v.getId() == R.id.layout_saoyisao) {
                                Global.addUserOriginType = Constant.ADD_USER_ORIGIN_TYPE_QRCODE;
                                Intent intent = new Intent(getActivity(),
                                        CaptureActivity.class);
                                startActivityForResult(intent, REQUEST_CODE);
                            }//添加好友
                            else if (v.getId() == R.id.layout_add_firend) {
                                startActivity(new Intent(getActivity(),
                                        AddContactActivity.class));
                            } else if (v.getId() == R.id.layout_group) {
                                startActivity(new Intent(getActivity(),
                                        ContactActivity.class).putExtra("from"
                                        , "1"));
                            } else if (v.getId() == R.id.layout_my_qr) {
                                Bundle bundle = new Bundle();
                                bundle.putString("from", "1");
                                startActivity(MyQrActivity.class, bundle);
                            }

                            popWinShare.dismiss();
                        }


                    };

            popWinShare = new PopWinShare(getActivity(), paramOnClickListener
                    , (int) DensityUtils.getWidthInPx(getContext()),
                    (int) DensityUtils.getHeightInPx(getContext()) - DensityUtils.dip2px(getContext(), 45) - DensityUtils.statusBarHeight2(getContext()));
            //监听窗口的焦点事件，点击窗口外面则取消显示
            popWinShare.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        popWinShare.dismiss();
                    }
                }
            });
        }
        //设置默认获取焦点
        popWinShare.setFocusable(true);
        //以某个控件的x和y的偏移量位置开始显示窗口
        popWinShare.showAsDropDown(mImgShow, 0,
                DensityUtils.dip2px(getActivity(), 8));
        //如果窗口存在，则更新
        popWinShare.update();
    }


    public void getMultiDevices() {
        Map<String,Object> map =new HashMap<>();
        ApiClient.requestNetHandle(getContext(), AppConfig.isSingleDevice, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (json.contains("1")) {
                    multiDeviceView.setVisibility(View.GONE);
                }else {
                    multiDeviceView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(String msg) {}
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                String result = bundle.getString(INTENT_EXTRA_KEY_QR_SCAN);

                if (result.contains("person")) {
                    startActivity(new Intent(getContext(),
                            UserInfoDetailActivity.class).putExtra(
                            "friendUserId", result.split("_")[0]));
                } else if (result.contains("group")) {
                    UserOperateManager.getInstance().scanInviteContact(getContext(),result);
                }
            }
        }
    }





}
