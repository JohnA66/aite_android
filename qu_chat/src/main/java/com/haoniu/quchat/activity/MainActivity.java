package com.haoniu.quchat.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.gyf.immersionbar.ImmersionBar;
import com.haoniu.quchat.activity.fragment.ContactListFragment;
import com.haoniu.quchat.activity.fragment.ConversationListFragment;
import com.haoniu.quchat.activity.fragment.FindFragment;
import com.haoniu.quchat.activity.fragment.PersonalFragment;
import com.haoniu.quchat.base.ActivityStackManager;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.base.MyHelper;
import com.haoniu.quchat.base.Storage;
import com.haoniu.quchat.constant.SP;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.MyGroupInfoList;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.login.LoginNewActivity;
import com.haoniu.quchat.model.EaseAtMessageHelper;
import com.haoniu.quchat.operate.GroupOperateManager;

import com.haoniu.quchat.receiver.CallReceiver;
import com.haoniu.quchat.runtimepermissions.PermissionsManager;
import com.haoniu.quchat.runtimepermissions.PermissionsResultAction;
import com.haoniu.quchat.utils.BarUtil;
import com.haoniu.quchat.utils.CretinAutoUpdateUtils;
import com.haoniu.quchat.utils.EaseCommonUtils;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.utils.PreferenceManager;
import com.haoniu.quchat.utils.hxSetMessageFree.UnReadMsgCount;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMMultiDeviceListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMucSharedFile;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.Preference;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


@SuppressLint("NewApi")
public class MainActivity extends BaseActivity {

    @BindView(R.id.view_status_bar_bg)
    View view_status_bar_bg;
    @BindView(R.id.btn_Room)
    Button mBtnRoom;
    @BindView(R.id.btn_container_Room)
    RelativeLayout mBtnContainerRoom;
    @BindView(R.id.btn_conversation)
    Button mBtnConversation;
    @BindView(R.id.unread_msg_number)
    TextView unreadLabel;
    @BindView(R.id.btn_container_conversation)
    RelativeLayout mBtnContainerConversation;
    @BindView(R.id.btn_address_list)
    Button mBtnAddressList;
    @BindView(R.id.unread_address_number)
    TextView unreadAddressLable;
    @BindView(R.id.btn_container_address_list)
    RelativeLayout mBtnContainerAddressList;
    @BindView(R.id.btn_setting)
    Button mBtnSetting;
    @BindView(R.id.btn_container_setting)
    RelativeLayout mBtnContainerSetting;
    @BindView(R.id.main_bottom)
    LinearLayout mMainBottom;
    @BindView(R.id.fragment_container)
    RelativeLayout mFragmentContainer;
    @BindView(R.id.mainLayout)
    RelativeLayout mMainLayout;
    @BindView(R.id.unread_apply_number)
    TextView unreadApplyNumber;


    /**
     * conversationListFragment 会话
     * contactListFragment 通讯录
     * CustomServiceFragment 发现
     * mPersonalFragment 我
     */
    private ContactListFragment contactListFragment;
    private ConversationListFragment conversationListFragment;
    private FindFragment findFragment;
    private PersonalFragment mPersonalFragment;

    private Button[] mTabs;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;
    // user logged into another device
    public boolean isConflict = false;
    // user account was removed
    private boolean isCurrentAccountRemoved = false;

    /**
     * check if current user account was remove
     */
    public boolean getCurrentAccountRemoved() {
        return isCurrentAccountRemoved;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager pm =
                    (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                try {
                    //some device doesn't has activity to handle this intent
                    //so add try catch
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                    startActivity(intent);
                } catch (Exception e) {
                }
            }
        }
        //make sure activity will not in background if user is logged into
        // another device or removed
        if (getIntent() != null &&
                (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) ||
                        getIntent().getBooleanExtra(Constant.ACCOUNT_KICKED_BY_CHANGE_PASSWORD, false) ||
                        getIntent().getBooleanExtra(Constant.ACCOUNT_KICKED_BY_OTHER_DEVICE, false))) {
            MyHelper.getInstance().logout(false, null);
            finish();
            startActivity(new Intent(this, /*AuthenticationActivity.class*/LoginNewActivity.class));
            return;
        } else if (getIntent() != null && getIntent().getBooleanExtra(
                "isConflict", false)) {
            finish();
            startActivity(new Intent(this, /*AuthenticationActivity.class*/LoginNewActivity.class));
            return;
        }
        AppConfig.checkVersion(this,true);


    }


    /**
     * 替代onCreate的使用
     *
     * @param bundle
     */
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_main_s);
    }


    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        isTransparency(false);
        initImmersionBar(true);
        ImmersionBar.setStatusBarView(this,view_status_bar_bg);
        mTabs = new Button[4];
        //服务
        mTabs[2] = mBtnConversation;
        //通讯录
        mTabs[1] = mBtnRoom;
        //主页
        mTabs[0] = mBtnAddressList;
        //我
        mTabs[3] = mBtnSetting;
        // select first tab
        mTabs[0].setSelected(true);
        index = 0;
//        currentTabIndex = 2;
        // 请求权限
        requestPermissions();
        showExceptionDialogFromIntent(getIntent());
        findFragment = new FindFragment();
        conversationListFragment = new ConversationListFragment();
        contactListFragment = new ContactListFragment();
        mPersonalFragment = new PersonalFragment();

        fragments = new Fragment[]{conversationListFragment,
                contactListFragment, findFragment, mPersonalFragment};

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, findFragment)
                .add(R.id.fragment_container, conversationListFragment).add(R.id.fragment_container, contactListFragment).add(R.id.fragment_container, mPersonalFragment).hide(findFragment).hide(contactListFragment).hide(mPersonalFragment).show(conversationListFragment)
                .commit();
        //注册广播监听
        registerBroadcastReceiver();
        //注册消息好群组监听
        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
        EMClient.getInstance().addMultiDeviceListener(new MyMultiDeviceListener());
        EMClient.getInstance().groupManager().addGroupChangeListener(new GroupListener());
        getPZ();
//        MyHelper.getInstance().asyncFetchContactsFromServer(null);
//        AppConfig.checkVersion(this, true);
        IntentFilter callFilter =
                new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        CallReceiver callReceiver = null;
        if (callReceiver == null) {
            callReceiver = new CallReceiver();
        }
        getApplication().registerReceiver(callReceiver, callFilter);

        groupList();
    }


    /**
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
    @Override
    protected void onEventComing(EventCenter center) {
        if (center.getEventCode() == EventUtil.LOSETOKEN) {
            finish();
            ActivityStackManager.getInstance().killAllActivity();
            UserComm.clearUserInfo();
            startActivity(/*AuthenticationActivity.class*/LoginNewActivity.class);
            //刷新公告数量
        } else if (center.getEventCode() == EventUtil.UNREADCOUNT) {
//            mCustomServiceFragment.getUnReadCount();
        } else if (center.getEventCode() == EventUtil.NOTICNUM) {
            updateUnreadAddressLable();
        } else if (center.getEventCode() == EventUtil.KEFU) {
            change(2);
        } else if (center.getEventCode() == EventUtil.TONGXUNLU) {
            change(1);
        } else if (center.getEventCode() == EventUtil.REFRESH_CONVERSION || center.getEventCode() == EventUtil.REFRESH_GROUP_NAME) {
            conversationListFragment.refresh();
        }else if (center.getEventCode() == EventUtil.FLUSHGROUP){
            groupList();
        }
    }

    /**
     * 获取配置
     */
    private void getPZ() {
        Map<String, Object> map = new HashMap<>();
        ApiClient.requestNetHandle(this, AppConfig
                .GET_SYS_CONFIG, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
//                PZInfo pzInfo = FastJsonUtil.getObject(json, PZInfo.class);
//                Storage.savePZ(pzInfo);
//                String fileUrlPre = FastJsonUtil.getString(json,
//                "fileUrlPre");
//                AppConfig.ImageMainUrl = fileUrlPre;
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    //权限申请
    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
            }

            @Override
            public void onDenied(String permission) {
            }
        });
    }


    /**
     * on tab clicked
     *
     * @param view
     */

    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_conversation:
                index = 2;
                initImmersionBar(true);
                ImmersionBar.setStatusBarView(this,view_status_bar_bg);
                break;
            case R.id.btn_Room:
                index = 1;
                EventBus.getDefault().post(new EventCenter(EventUtil.FLUSHGROUP));
                initImmersionBar(true);
                ImmersionBar.setStatusBarView(this,view_status_bar_bg);
                break;
            case R.id.btn_address_list:
                index = 0;
                EventBus.getDefault().post(new EventCenter(EventUtil.CHECK_MULTI_STATUS));
                initImmersionBar(true);
                ImmersionBar.setStatusBarView(this,view_status_bar_bg);
                break;
            case R.id.btn_setting:
                index = 3;
                MyApplication.getInstance().UpUserInfo();
                initImmersionBar(false);
                ImmersionBar.setStatusBarView(this,0,view_status_bar_bg);
                break;
            default:
        }

//        if (index == 2) {
//            startActivity(new Intent(MainActivity.this, WebViewActivity
//            .class).putExtra("title", "lan").putExtra("url", AppConfig.FIND));
//            return;
//        }
        if (currentTabIndex != index) {
            FragmentTransaction trx =
                    getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        // set current tab selected
        mTabs[index].setSelected(true);


        currentTabIndex = index;
        if (index == 2) {
//            startActivity(new Intent(MainActivity.this, WebViewActivity
//            .class).putExtra("title", "lan").putExtra("url", AppConfig.FIND));

//            customServiceFragment.getUnReadCount();
//            EventBus.getDefault().post(new EventCenter(EventUtil.FLUSHXTTZ));
        } else if (index == 3) {
//            ApplyNumber();
        }


    }

    private void change(int indexs) {
        index = indexs;
        if (index == 0) {
            EventBus.getDefault().post(new EventCenter(EventUtil.FLUSHGROUP));
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx =
                    getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commitAllowingStateLoss();
        }
        mTabs[currentTabIndex].setSelected(false);
        // set current tab selected
        mTabs[index].setSelected(true);
        currentTabIndex = index;
        if (index == 2) {
//            mCustomServiceFragment.getUnReadCount();
        } else if (index == 3) {
//            ApplyNumber();
        }
    }

    @Override
    protected void transparencyBar() {

    }

    /**
     * 消息监听
     */
    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            EaseAtMessageHelper.get().parseMessages(messages);
            refreshUIWithMessage();

            for (EMMessage message : messages) {
                EaseCommonUtils.initMessage(message);
                MyHelper.getInstance().getNotifier().onNewMsg(message);
            }

        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // refresh unread count
                if (currentTabIndex == 0) {
                    // refresh conversation list
                    if (conversationListFragment != null) {
                        conversationListFragment.refresh();
                    }
                } else if (currentTabIndex == 2) {
                    EventBus.getDefault().post(new EventCenter(EventUtil.FLUSHXTTZ));
                }

                updateUnreadLabel();
                updateUnreadAddressLable();

            }
        });
    }

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                updateUnreadLabel();
                if (currentTabIndex == 0) {
                    // refresh conversation list
                    if (conversationListFragment != null) {
                        conversationListFragment.refresh();
                    }
                }
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    public class MyContactListener implements EMContactListener {
        @Override
        public void onContactAdded(String username) {
        }

        @Override
        public void onContactDeleted(final String username) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (ChatActivity.activityInstance != null && ChatActivity.activityInstance.toChatUsername != null &&
                            username.equals(ChatActivity.activityInstance.toChatUsername)) {
                        String st10 =
                                getResources().getString(R.string.have_you_removed);
//                        Toast.makeText(MainActivity.this, ChatActivity
//                        .activityInstance.getToChatUsername() + st10, Toast
//                        .LENGTH_LONG)
//                                .show();
                        ChatActivity.activityInstance.finish();
                    }
                }
            });
        }

        @Override
        public void onContactInvited(String username, String reason) {
        }

        @Override
        public void onFriendRequestAccepted(String username) {
        }

        @Override
        public void onFriendRequestDeclined(String username) {
        }
    }

    /**
     * 群组监听
     */
    public class GroupListener implements EMGroupChangeListener {

        @Override
        public void onInvitationReceived(String s, String s1, String s2,
                                         String s3) {

        }

        @Override
        public void onRequestToJoinReceived(String s, String s1, String s2,
                                            String s3) {

        }

        @Override
        public void onRequestToJoinAccepted(String s, String s1, String s2) {
            EventBus.getDefault().post(new EventCenter(EventUtil.FLUSHGROUP));
        }

        @Override
        public void onRequestToJoinDeclined(String s, String s1, String s2,
                                            String s3) {

        }

        @Override
        public void onInvitationAccepted(String s, String s1, String s2) {

        }

        @Override
        public void onInvitationDeclined(String s, String s1, String s2) {

        }

        @Override
        public void onUserRemoved(String s, String s1) {

        }

        @Override
        public void onGroupDestroyed(String s, String s1) {
            EventBus.getDefault().post(new EventCenter(EventUtil.FLUSHGROUP));
        }

        @Override
        public void onAutoAcceptInvitationFromGroup(String s, String s1,
                                                    String s2) {

        }

        @Override
        public void onMuteListAdded(String s, List<String> list, long l) {

        }

        @Override
        public void onMuteListRemoved(String s, List<String> list) {

        }

        @Override
        public void onAdminAdded(String s, String s1) {

        }

        @Override
        public void onAdminRemoved(String s, String s1) {
        }

        @Override
        public void onOwnerChanged(String s, String s1, String s2) {
        }

        @Override
        public void onMemberJoined(String s, String s1) {
        }

        @Override
        public void onMemberExited(String s, String s1) {
        }

        @Override
        public void onAnnouncementChanged(String s, String s1) {
        }

        @Override
        public void onSharedFileAdded(String s, EMMucSharedFile file) {
        }

        @Override
        public void onSharedFileDeleted(String s, String s1) {
        }
    }


    /**
     * 设备监听
     */
    public class MyMultiDeviceListener implements EMMultiDeviceListener {

        @Override
        public void onContactEvent(int event, String target, String ext) {
        }


        @Override
        public void onGroupEvent(int event, String target,
                                 final List<String> username) {
            switch (event) {
                case EMMultiDeviceListener.GROUP_LEAVE:
                    ChatActivity.activityInstance.finish();
                    break;
                default:
                    break;
            }
        }
    }

    private void unregisterBroadcastReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        PreferenceManager.getInstance().setFirstOpenApp(true);
        super.onDestroy();
        if (exceptionBuilder != null) {
            exceptionBuilder.create().dismiss();
            exceptionBuilder = null;
            isExceptionDialogShow = false;
        }

        unregisterBroadcastReceiver();
        try {
            unregisterReceiver(internalDebugReceiver);
            CretinAutoUpdateUtils.getInstance(this).destroy();
        } catch (Exception e) {
        }

    }

    /**
     * 更新未读消息数量
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            unreadLabel.setText(String.valueOf(count));
            unreadLabel.setVisibility(View.VISIBLE);
        } else {
            unreadLabel.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 更新未读通知消息总数
     */
    public void updateUnreadAddressLable() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int applyJoinGroupcount = (int) PreferenceManager.getInstance().getParam(SP.APPLY_JOIN_GROUP_NUM, 0);
                int addUserCount        = (int) PreferenceManager.getInstance().getParam(SP.APPLY_ADD_USER_NUM, 0);
                int count = applyJoinGroupcount + addUserCount;
                if (count > 0) {
                     unreadAddressLable.setVisibility(View.VISIBLE);
                     unreadAddressLable.setText(count + "");
                } else {
                     unreadAddressLable.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    /**
     * get unread event notification count, including application, accepted, etc
     *
     * @return
     */
    public int getUnreadAddressCountTotal() {
        EMConversation conversation =
                EMClient.getInstance().chatManager().getConversation(Constant.ADMIN);
        int count = Double.valueOf(Preference.getStringPreferences(this,
                Constant.UNREADCOUNT, "0")).intValue();
        int count1 = 0;
        if (conversation != null) {
            count1 = conversation.getUnreadMsgCount();
        }
        return count1 + count;
    }

    /**
     * get unread message count
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        EMConversation conversation =
                EMClient.getInstance().chatManager().getConversation(Constant.ADMIN);
//        return EMClient.getInstance().chatManager().getUnreadMessageCount()
//        - ((conversation != null && conversation.getUnreadMsgCount() > 0) ?
//        conversation.getUnreadMsgCount() : 0);
        return UnReadMsgCount.getUnreadMessageCount() - ((conversation != null && conversation.getUnreadMsgCount() > 0) ? conversation.getUnreadMsgCount() : 0);
    }

    /**
     * 未审核数量
     */
    private void ApplyNumber() {
        Map<String, Object> map = new HashMap<>();
        ApiClient.requestNetHandle(this, AppConfig.ApplyNumber, "", map,
                new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        int number = 0;
                        try {
                            number = Double.valueOf(json).intValue();
                            EventBus.getDefault().post(new EventCenter<Integer>(EventUtil.APPLYNUMER, number));
                            if (number <= 0) {
                                unreadApplyNumber.setVisibility(View.GONE);
                            } else {
                                unreadApplyNumber.setVisibility(View.VISIBLE);
                            }
                            unreadApplyNumber.setText(number + "");
                        } catch (Exception e) {
                            unreadApplyNumber.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (!isConflict && !isCurrentAccountRemoved) {
            updateUnreadLabel();
            updateUnreadAddressLable();
        }
        MyHelper sdkHelper = MyHelper.getInstance();
        sdkHelper.pushActivity(this);

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    protected void onStop() {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        MyHelper sdkHelper = MyHelper.getInstance();
        sdkHelper.popActivity(this);
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        outState.putBoolean(Constant.ACCOUNT_REMOVED, isCurrentAccountRemoved);
        super.onSaveInstanceState(outState);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(false);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    private AlertDialog.Builder exceptionBuilder;
    private boolean isExceptionDialogShow = false;
    private BroadcastReceiver internalDebugReceiver;
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;

    private int getExceptionMessageId(String exceptionType) {
        if (exceptionType.equals(Constant.ACCOUNT_CONFLICT)) {
            return R.string.connect_conflict;
        } else if (exceptionType.equals(Constant.ACCOUNT_REMOVED)) {
            return R.string.em_user_remove;
        } else if (exceptionType.equals(Constant.ACCOUNT_FORBIDDEN)) {
            return R.string.user_forbidden;
        }
        return R.string.Network_error;
    }

    /**
     * show the dialog when user met some exception: such as login on another
     * device, user removed or user forbidden
     */
    private void showExceptionDialog(String exceptionType) {
        isExceptionDialogShow = true;
        MyHelper.getInstance().logout(false, null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (exceptionBuilder == null) {
                    exceptionBuilder =
                            new AlertDialog.Builder(MainActivity.this);
                }
                exceptionBuilder.setTitle(st);
                exceptionBuilder.setMessage(getExceptionMessageId(exceptionType));
                exceptionBuilder.setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                exceptionBuilder = null;
                                isExceptionDialogShow = false;
                                finish();
                                Intent intent = new Intent(MainActivity.this,
                                        /*AuthenticationActivity.class*/LoginNewActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
                exceptionBuilder.setCancelable(false);
                exceptionBuilder.create().show();
                isConflict = true;
            } catch (Exception e) {
            }
        }
    }

    private void showExceptionDialogFromIntent(Intent intent) {
        if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false)) {
            showExceptionDialog(Constant.ACCOUNT_CONFLICT);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false)) {
            showExceptionDialog(Constant.ACCOUNT_REMOVED);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_FORBIDDEN, false)) {
            showExceptionDialog(Constant.ACCOUNT_FORBIDDEN);
        } else if (intent.getBooleanExtra(Constant.ACCOUNT_KICKED_BY_CHANGE_PASSWORD, false) ||
                intent.getBooleanExtra(Constant.ACCOUNT_KICKED_BY_OTHER_DEVICE, false)) {
            this.finish();
            startActivity(new Intent(this, /*AuthenticationActivity.class*/LoginNewActivity.class));
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showExceptionDialogFromIntent(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions,
                grantResults);
    }

    /**
     * 我的群组列表
     *
     * @param
     */
    private void groupList() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("pageSize", 1500);
        map.put("pageNum", 1);
        ApiClient.requestNetHandle(this, AppConfig.MY_GROUP_LIST, "", map,
                new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        MyGroupInfoList myGroupInfo = FastJsonUtil.getObject(json,
                                MyGroupInfoList.class);
                        if (myGroupInfo.getData() != null && myGroupInfo.getData().size() > 0) {
                            GroupOperateManager.getInstance().saveGroupsInfo(myGroupInfo,json);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        toast(msg);
                    }
                });
    }

    protected void initImmersionBar(boolean isStatusBarDartFont) {
        //初始化，默认透明状态栏和黑色导航栏。
        ImmersionBar.with(this)
                .keyboardEnable(true)
                //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .statusBarDarkFont(isStatusBarDartFont, 0.2f)
                //.navigationBarColor("#E9E9E9")
                //采用系统默认导航栏颜色
                .navigationBarEnable(false)
                .init();//有时需要直接由子类实现该功能
    }

}
