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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.chat.R;
import com.haoniu.quchat.activity.AddContactActivity;
import com.haoniu.quchat.activity.ChatActivity;
import com.haoniu.quchat.activity.ContactActivity;
import com.haoniu.quchat.activity.MainActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.db.InviteMessgeDao;
import com.haoniu.quchat.global.Global;
import com.haoniu.quchat.operate.GroupOperateManager;
import com.haoniu.quchat.operate.UserOperateManager;
import com.haoniu.quchat.widget.EaseAlertDialog;
import com.haoniu.quchat.widget.EaseConversationList;
import com.haoniu.quchat.widget.PopWinShare;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.NetUtils;
import com.zds.base.code.activity.CaptureActivity;
import com.zds.base.util.DensityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE;

/**
 * @author Administrator
 */
public class ShareCardIdConversationListFragment extends BaseConversationListFragment {

    @BindView(R.id.hint)
    TextView mHint;
    private TextView errorText;
    private Bundle mBundle;

    public static final ShareCardIdConversationListFragment newInstance(Bundle bundles) {
        ShareCardIdConversationListFragment fragment = new ShareCardIdConversationListFragment();
        Bundle bundle = new Bundle();
        bundle = bundles;
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ease_fragment_share_cardid_conversation_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        mBundle = extras;
    }

    @Override
    protected void initLogic() {
        super.initLogic();
        titleBar.setBar();
        View errorView = (LinearLayout) View.inflate(getActivity(), R.layout.em_chat_neterror_item, null);
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);
        registerForContextMenu(conversationListView);

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
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                conversationListView.filter(s);
                if (s.length() > 0) {
                    clearSearch.setVisibility(View.VISIBLE);
                    mHint.setVisibility(View.GONE);
                } else {
                    clearSearch.setVisibility(View.INVISIBLE);
                    mHint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        conversationListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.conversationId();
                if (username.equals(EMClient.getInstance().getCurrentUser())) {
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                } else {
                    showShareCardIdDailog(conversation);
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

    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())) {
            errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
            errorText.setText(R.string.the_current_network);
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean deleteMessage = false;
        if (item.getItemId() == R.id.delete_message) {
            deleteMessage = true;
        } else if (item.getItemId() == R.id.delete_conversation) {
            deleteMessage = false;
        }
        EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
        if (tobeDeleteCons == null) {
            return true;
        }

        try {
            // delete conversation
            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.conversationId(), deleteMessage);
            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
            inviteMessgeDao.deleteMessage(tobeDeleteCons.conversationId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();

        // update unread count
        ((MainActivity) getActivity()).updateUnreadLabel();
        return true;
    }


    private void showShareCardIdDailog(EMConversation conversation) {
        String name = "";
        String username = conversation.conversationId();
        // start chat acitivity
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        if (conversation.isGroup()) {
            if (conversation.getType() == EMConversationType.ChatRoom) {
                // it's group chat
                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
            } else {

                name = GroupOperateManager.getInstance().getGroupInfo(username).getGroupName();
                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
            }

        } else {
            name = UserOperateManager.getInstance().getUserName(username);
        }
        // it's single chat


        String finalUsername = username;
        new EaseAlertDialog(getContext(), "确定发给：", name, true, new EaseAlertDialog.AlertDialogUser() {
            @Override
            public void onResult(boolean confirmed, Bundle bundle) {
                if (confirmed) {
                    //发送自己的名片和好友的名片给他人判断
                    intent.putExtra("from", "1");
                    intent.putExtras(mBundle);
                    intent.putExtra(Constant.EXTRA_USER_ID, finalUsername);
                    startActivity(intent);
                }
            }
        }, true).show();
    }


    private PopWinShare popWinShare;

    /**
     * 显示浮窗菜单
     */
    private void showPopudown() {
        if (popWinShare == null) {
            View.OnClickListener paramOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //扫一扫
                    if (v.getId() == R.id.layout_saoyisao) {
                        Global.addUserOriginType = Constant.ADD_USER_ORIGIN_TYPE_QRCODE;
                        Intent intent = new Intent(getActivity(), CaptureActivity.class);
                        startActivityForResult(intent, REQUEST_CODE);

                    }//添加好友
                    else if (v.getId() == R.id.layout_add_firend) {
                        startActivity(new Intent(getActivity(), AddContactActivity.class));

                    } else if (v.getId() == R.id.layout_group) {
                        startActivity(new Intent(getActivity(), ContactActivity.class).putExtra("from", "1"));
                    }

                    popWinShare.dismiss();
                }


            };

            popWinShare = new PopWinShare(getActivity(), paramOnClickListener, (int) DensityUtils.getWidthInPx(getContext()), (int) DensityUtils.getHeightInPx(getContext()) - DensityUtils.dip2px(getContext(), 45) - DensityUtils.statusBarHeight2(getContext()));
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
        popWinShare.showAsDropDown(mImgShow, 0, DensityUtils.dip2px(getActivity(), 8));
        //如果窗口存在，则更新
        popWinShare.update();
    }
}
