package com.haoniu.quchat.base;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.aite.chat.BuildConfig;
import com.aite.chat.R;
import com.haoniu.quchat.activity.ChatActivity;
import com.haoniu.quchat.activity.MainActivity;
import com.haoniu.quchat.activity.VideoCallActivity;
import com.haoniu.quchat.activity.VoiceCallActivity;
import com.haoniu.quchat.base.EaseUI.EaseEmojiconInfoProvider;
import com.haoniu.quchat.constant.SP;
import com.haoniu.quchat.db.DemoDBManager;
import com.haoniu.quchat.db.InviteMessgeDao;
import com.haoniu.quchat.domain.EaseAvatarOptions;
import com.haoniu.quchat.domain.EaseEmojicon;
import com.haoniu.quchat.domain.EaseEmojiconGroupEntity;
import com.haoniu.quchat.domain.EaseUser;
import com.haoniu.quchat.domain.EmojiconExampleGroupData;
import com.haoniu.quchat.domain.InviteMessage.InviteMessageStatus;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.GroupInfo;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.model.EaseAtMessageHelper;
import com.haoniu.quchat.model.EaseNotifier;
import com.haoniu.quchat.model.EaseNotifier.EaseNotificationInfoProvider;
import com.haoniu.quchat.operate.GroupOperateManager;
import com.haoniu.quchat.operate.UserOperateManager;

import com.haoniu.quchat.receiver.CallReceiver;
import com.haoniu.quchat.receiver.HeadsetReceiver;
import com.haoniu.quchat.utils.EaseCommonUtils;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.utils.PreferenceManager;
import com.haoniu.quchat.utils.ProjectUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMError;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMMultiDeviceListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMMessage.Status;
import com.hyphenate.chat.EMMessage.Type;
import com.hyphenate.chat.EMMucSharedFile;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;

import org.greenrobot.eventbus.EventBus;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyHelper {


    protected static final String TAG = "MyHelper";

    private EaseUI easeUI;

    /**
     * EMEventListener
     */
    protected EMMessageListener messageListener = null;


    private static MyHelper instance = null;

    private MyModel demoModel = null;

    public boolean isVoiceCalling;
    public boolean isVideoCalling;

    private Context appContext;

    private CallReceiver callReceiver;

    private LocalBroadcastManager broadcastManager;

    private boolean isGroupAndContactListenerRegisted;

    private ExecutorService executor;

    protected android.os.Handler handler;

    Queue<String> msgQueue = new ConcurrentLinkedQueue<>();

    private MyHelper() {
        executor = Executors.newCachedThreadPool();
    }

    public synchronized static MyHelper getInstance() {
        if (instance == null) {
            instance = new MyHelper();
        }
        return instance;
    }

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    /**
     * init helper
     *
     * @param context application context
     */
    public void init(Context context) {
        demoModel = new MyModel(context);
        EMOptions options = initChatOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);
//        options.setRestServer("118.193.28.212:31080");
//        options.setIMServer("118.193.28.212");
//        options.setImPort(31097);

        //use default options if options is null
        if (EaseUI.getInstance().init(context, options)) {
            appContext = context;

            //debug mode, you'd better set it to false, if you want release
            // your App officially.
            EMClient.getInstance().setDebugMode(BuildConfig.DEBUG);
            //get easeui instance
            easeUI = EaseUI.getInstance();
            //to set user's profile and avatar
            setEaseUIProviders();
            //initialize preference manager
            PreferenceManager.init(context);
            //set Call options
            setCallOptions();

            setGlobalListeners();
            broadcastManager = LocalBroadcastManager.getInstance(appContext);

        }
    }


    private EMOptions initChatOptions() {
        Log.d(TAG, "init HuanXin Options");

        EMOptions options = new EMOptions();
        // set if accept the invitation automatically
        options.setAcceptInvitationAlways(false);
        // set if you need read ack
        options.setRequireAck(true);
        // set if you need delivery ack
        options.setRequireDeliveryAck(false);

        /**
         * NOTE:你需要设置自己申请的Sender ID来使用Google推送功能，详见集成文档
         */
        options.setFCMNumber("921300338324");
        //you need apply & set your own id if you want to use Mi push
        // notification
        options.setMipushConfig("2882303761517426801", "5381742660801");
        // 设置是否使用 fcm，有些华为设备本身带有 google 服务，
//        options.setUseFCM(demoModel.isUseFCM());

        //set custom servers, commonly used in private deployment
        if (demoModel.isCustomServerEnable() && demoModel.getRestServer() != null && demoModel.getIMServer() != null) {
            options.setRestServer(demoModel.getRestServer());
            options.setIMServer(demoModel.getIMServer());
            if (demoModel.getIMServer().contains(":")) {
                options.setIMServer(demoModel.getIMServer().split(":")[0]);
                options.setImPort(Integer.valueOf(demoModel.getIMServer().split(":")[1]));
            }
        }

        if (demoModel.isCustomAppkeyEnabled() && demoModel.getCutomAppkey() != null && !demoModel.getCutomAppkey().isEmpty()) {
            options.setAppKey(demoModel.getCutomAppkey());
        }

        options.allowChatroomOwnerLeave(getModel().isChatroomOwnerLeaveAllowed());
        options.setDeleteMessagesAsExitGroup(getModel().isDeleteMessagesAsExitGroup());
        options.setAutoAcceptGroupInvitation(getModel().isAutoAcceptGroupInvitation());
        // Whether the message attachment is automatically uploaded to the
        // Hyphenate server,
        options.setAutoTransferMessageAttachments(getModel().isSetTransferFileByUser());
        // Set Whether auto download thumbnail, default value is true.
        options.setAutoDownloadThumbnail(getModel().isSetAutodownloadThumbnail());
        return options;
    }

    private void setCallOptions() {
        HeadsetReceiver headsetReceiver = new HeadsetReceiver();
        IntentFilter headsetFilter =
                new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        appContext.registerReceiver(headsetReceiver, headsetFilter);

        // min video kbps
        int minBitRate = PreferenceManager.getInstance().getCallMinVideoKbps();
        if (minBitRate != -1) {
            EMClient.getInstance().callManager().getCallOptions().setMinVideoKbps(minBitRate);
        }

        // max video kbps
        int maxBitRate = PreferenceManager.getInstance().getCallMaxVideoKbps();
        if (maxBitRate != -1) {
            EMClient.getInstance().callManager().getCallOptions().setMaxVideoKbps(maxBitRate);
        }

        // max frame rate
        int maxFrameRate =
                PreferenceManager.getInstance().getCallMaxFrameRate();
        if (maxFrameRate != -1) {
            EMClient.getInstance().callManager().getCallOptions().setMaxVideoFrameRate(maxFrameRate);
        }

        // audio sample rate
        int audioSampleRate =
                PreferenceManager.getInstance().getCallAudioSampleRate();
        if (audioSampleRate != -1) {
            EMClient.getInstance().callManager().getCallOptions().setAudioSampleRate(audioSampleRate);
        }

        /**
         * This function is only meaningful when your app need recording
         * If not, remove it.
         * This function need be called before the video stream started, so
         * we set it in onCreate function.
         * This method will set the preferred video record encoding codec.
         * Using default encoding format, recorded file may not be played by
         * mobile player.
         */
        //EMClient.getInstance().callManager().getVideoCallHelper()
        // .setPreferMovFormatEnable(true);

        // resolution
        String resolution =
                PreferenceManager.getInstance().getCallBackCameraResolution();
        if (resolution.equals("")) {
            resolution =
                    PreferenceManager.getInstance().getCallFrontCameraResolution();
        }
        String[] wh = resolution.split("x");
        if (wh.length == 2) {
            try {
                EMClient.getInstance().callManager().getCallOptions().setVideoResolution(new Integer(wh[0]).intValue(),
                        new Integer(wh[1]).intValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // enabled fixed sample rate
        boolean enableFixSampleRate =
                PreferenceManager.getInstance().isCallFixedVideoResolution();
        EMClient.getInstance().callManager().getCallOptions().enableFixedVideoResolution(enableFixSampleRate);

        // Offline call push
        EMClient.getInstance().callManager().getCallOptions().setIsSendPushIfOffline(getModel().isPushCall());
    }

    protected void setEaseUIProviders() {
        //set user avatar to circle shape
        EaseAvatarOptions avatarOptions = new EaseAvatarOptions();
        avatarOptions.setAvatarShape(1);
        easeUI.setAvatarOptions(avatarOptions);

        // set profile provider if you want easeUI to handle avatar and nickname
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {

            @Override
            public EaseUser getUser(String emUserId) {
                return getUserInfo(emUserId);
            }
        });

        //set options 
        easeUI.setSettingsProvider(new EaseUI.EaseSettingsProvider() {

            @Override
            public boolean isSpeakerOpened() {
                return demoModel.getSettingMsgSpeaker();
            }

            @Override
            public boolean isMsgVibrateAllowed(EMMessage message) {
                return demoModel.getSettingMsgVibrate();
            }

            @Override
            public boolean isMsgSoundAllowed(EMMessage message) {
                return demoModel.getSettingMsgSound();
            }

            @Override
            public boolean isMsgNotifyAllowed(EMMessage message) {
                if (message == null) {
                    return demoModel.getSettingMsgNotification();
                }
                if (!demoModel.getSettingMsgNotification()) {
                    return false;
                } else {
                    String chatUsename = null;
                    List<String> notNotifyIds = null;
                    // get user or group id which was blocked to show message
                    // notifications
                    if (message.getChatType() == ChatType.Chat) {
                        chatUsename = message.getFrom();
                        notNotifyIds = demoModel.getDisabledIds();
                    } else {
                        chatUsename = message.getTo();
                        notNotifyIds = demoModel.getDisabledGroups();
                    }

                    if (notNotifyIds == null || !notNotifyIds.contains(chatUsename)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        });
        //set emoji icon provider
        easeUI.setEmojiconInfoProvider(new EaseEmojiconInfoProvider() {

            @Override
            public EaseEmojicon getEmojiconInfo(String emojiconIdentityCode) {
                EaseEmojiconGroupEntity data =
                        EmojiconExampleGroupData.getData();
                for (EaseEmojicon emojicon : data.getEmojiconList()) {
                    if (emojicon.getIdentityCode().equals(emojiconIdentityCode)) {
                        return emojicon;
                    }
                }
                return null;
            }

            @Override
            public Map<String, Object> getTextEmojiconMapping() {
                return null;
            }
        });

        //set notification options, will use default if you don't set it
        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotificationInfoProvider() {

            @Override
            public String getTitle(EMMessage message) {
                //you can update title here
                return null;
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                //you can update icon here
                return 0;
            }

            @Override
            public String getDisplayedText(EMMessage message) {
                // be used on notification bar, different text according the
                // message type.
                String ticker = EaseCommonUtils.getMessageDigest(message,
                        appContext);
                if (message.getType() == Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }


                if (UserOperateManager.getInstance().hasUserName(message.getFrom())) {
                    if (EaseAtMessageHelper.get().isAtMeMsg(message)) {
                        return String.format(appContext.getString(R.string.at_your_in_group), UserOperateManager.getInstance().getUserName(message.getFrom()));
                    }
                    return UserOperateManager.getInstance().getUserName(message.getFrom()) + ": " + ticker;
                } else {
                    if (EaseAtMessageHelper.get().isAtMeMsg(message)) {
                        return String.format(appContext.getString(R.string.at_your_in_group), message.getFrom());
                    }
                    return message.getFrom() + ": " + ticker;
                }
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum,
                                        int messageNum) {
                // here you can customize the text.
                // return fromUsersNum + "contacts send " + messageNum +
                // "messages to you";
                return null;
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                // you can set what activity you want display when user click
                // the notification
                Intent intent = new Intent(appContext, ChatActivity.class);
                // open calling activity if there is call
                if (isVideoCalling) {
                    intent = new Intent(appContext, VideoCallActivity.class);
                } else if (isVoiceCalling) {
                    intent = new Intent(appContext, VoiceCallActivity.class);
                } else {
                    ChatType chatType = message.getChatType();
                    if (chatType == ChatType.Chat) { // single chat message
                        intent.putExtra("userId", message.getFrom());
                        intent.putExtra("chatType", Constant.CHATTYPE_SINGLE);
                    } else { // group chat message
                        // message.getTo() is the group id
                        intent.putExtra("userId", message.getTo());
                        if (chatType == ChatType.GroupChat) {
                            intent.putExtra("chatType",
                                    Constant.CHATTYPE_GROUP);
                        } else {
                            intent.putExtra("chatType",
                                    Constant.CHATTYPE_CHATROOM);
                        }

                    }
                }
                return intent;
            }
        });
    }

    EMConnectionListener connectionListener;

    /**
     * set global listener
     */
    protected void setGlobalListeners() {

        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(int error) {
                EMLog.d("global listener", "onDisconnect" + error);
                if (error == EMError.USER_REMOVED) {
                    onUserException(Constant.ACCOUNT_REMOVED);
                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    onUserException(Constant.ACCOUNT_CONFLICT);
                } else if (error == EMError.SERVER_SERVICE_RESTRICTED) {
                    onUserException(Constant.ACCOUNT_FORBIDDEN);
                } else if (error == EMError.USER_KICKED_BY_CHANGE_PASSWORD) {
                    onUserException(Constant.ACCOUNT_KICKED_BY_CHANGE_PASSWORD);
                } else if (error == EMError.USER_KICKED_BY_OTHER_DEVICE) {
                    onUserException(Constant.ACCOUNT_KICKED_BY_OTHER_DEVICE);
                }
            }

            @Override
            public void onConnected() {
                try {
                    EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        };


        //register incoming call receiver
        IntentFilter callFilter =
                new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        if (callReceiver == null) {
            callReceiver = new CallReceiver();
        }
        appContext.registerReceiver(callReceiver, callFilter);
        //register connection listener
        EMClient.getInstance().addConnectionListener(connectionListener);
        //register group and contact event listener
        registerGroupAndContactListener();
        //register message event listener
        registerMessageListener();

    }

    /**
     * register group and contact listener, you need register when login
     */
    public void registerGroupAndContactListener() {
        if (!isGroupAndContactListenerRegisted) {
            EMClient.getInstance().groupManager().addGroupChangeListener(new MyGroupChangeListener());
            EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
            EMClient.getInstance().addMultiDeviceListener(new MyMultiDeviceListener());
            isGroupAndContactListenerRegisted = true;
        }

    }

    /**
     * group change listener
     */
    class MyGroupChangeListener implements EMGroupChangeListener {

        @Override
        public void onInvitationReceived(String groupId, String groupName,
                                         String inviter, String reason) {
            notifyNewInviteMessage();
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
        }

        @Override
        public void onInvitationAccepted(String groupId, String invitee,
                                         String reason) {

            //user accept your invitation
            boolean hasGroup = false;
            GroupInfo groupInfo = GroupOperateManager.getInstance().getGroupInfo(groupId);
            if (groupInfo != null) {
                hasGroup = true;
            }
            if (!hasGroup)
                return;

            showToast(invitee + "Accept to join the group：" + groupInfo.getGroupName());

            notifyNewInviteMessage();
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
        }


        @Override
        public void onInvitationDeclined(String groupId, String invitee,
                                         String reason) {
            //群邀请拒绝
            new InviteMessgeDao(appContext).deleteMessage(groupId);

            //user declined your invitation
            EMGroup group = null;
            for (EMGroup _group :
                    EMClient.getInstance().groupManager().getAllGroups()) {
                if (_group.getGroupId().equals(groupId)) {
                    group = _group;
                    break;
                }
            }
            if (group == null)
                return;

            notifyNewInviteMessage();
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
        }

        @Override
        public void onUserRemoved(String groupId, String groupName) {
            //user is removed from group
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
//            showToast("current user removed, groupId:" + groupId);
        }

        @Override
        public void onGroupDestroyed(String groupId, String groupName) {
            // group is dismissed,
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
//            showToast("group destroyed, groupId:" + groupId);
        }

        @Override
        public void onRequestToJoinReceived(String groupId, String groupName,
                                            String applyer, String reason) {
            notifyNewInviteMessage();
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
        }

        @Override
        public void onRequestToJoinAccepted(String groupId, String groupName,
                                            String accepter) {
            //同意加入群聊申请
            String st4 =
                    appContext.getString(R.string.Agreed_to_your_group_chat_application);
            // your application was accepted
            EMMessage msg = EMMessage.createReceiveMessage(Type.TXT);
            msg.setChatType(ChatType.GroupChat);
            msg.setFrom(accepter);
            msg.setTo(groupId);
            msg.setMsgId(UUID.randomUUID().toString());
            msg.addBody(new EMTextMessageBody(accepter + " " + st4));
            msg.setStatus(Status.SUCCESS);
            // save accept message
            EMClient.getInstance().chatManager().saveMessage(msg);
            // notify the accept message
            getNotifier().vibrateAndPlayTone(msg);

            showToast("request to join accepted, groupId:" + groupId);
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
        }

        @Override
        public void onRequestToJoinDeclined(String groupId, String groupName,
                                            String decliner, String reason) {
        }

        @Override
        public void onAutoAcceptInvitationFromGroup(String groupId,
                                                    String inviter,
                                                    String inviteMessage) {
            //自动邀请你加入了群聊
            // got an invitation
            String st3 =
                    appContext.getString(R.string.Invite_you_to_join_a_group_chat);
            EMMessage msg = EMMessage.createReceiveMessage(Type.TXT);
            msg.setChatType(ChatType.GroupChat);
            msg.setFrom(inviter);
            msg.setTo(groupId);
            msg.setMsgId(UUID.randomUUID().toString());
            msg.addBody(new EMTextMessageBody(inviter + " " + st3));
            msg.setStatus(EMMessage.Status.SUCCESS);
            // save invitation as messages
            EMClient.getInstance().chatManager().saveMessage(msg);
            // notify invitation message
            getNotifier().vibrateAndPlayTone(msg);
//            showToast("auto accept invitation from groupId:" + groupId);
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
        }

        // ============================= group_reform new add api begin
        @Override
        public void onMuteListAdded(String groupId, final List<String> mutes,
                                    final long muteExpire) {
        }


        @Override
        public void onMuteListRemoved(String groupId,
                                      final List<String> mutes) {
        }


        @Override
        public void onAdminAdded(String groupId, String administrator) {
//            showToast("onAdminAdded: " + administrator);
        }

        @Override
        public void onAdminRemoved(String groupId, String administrator) {
            //   showToast("onAdminRemoved: " + administrator);
        }

        @Override
        public void onOwnerChanged(String groupId, String newOwner,
                                   String oldOwner) {
            //   showToast("onOwnerChanged new:" + newOwner + " old:" +
            //   oldOwner);
        }

        @Override
        public void onMemberJoined(String groupId, String member) {
//            showToast("onMemberJoined: " + member);
        }

        @Override
        public void onMemberExited(String groupId, String member) {
            // showToast("onMemberExited: " + member);
        }

        @Override
        public void onAnnouncementChanged(String groupId, String announcement) {
            //  showToast("onAnnouncementChanged, groupId" + groupId);
        }

        @Override
        public void onSharedFileAdded(String groupId,
                                      EMMucSharedFile sharedFile) {
            // showToast("onSharedFileAdded, groupId" + groupId);
        }

        @Override
        public void onSharedFileDeleted(String groupId, String fileId) {
            // showToast("onSharedFileDeleted, groupId" + groupId);
        }
        // ============================= group_reform new add api end
    }

    void showToast(final String message) {
        Log.d(TAG, "receive invitation to join the group：" + message);

        if (handler != null) {
            Message msg = Message.obtain(handler, 0, message);
            handler.sendMessage(msg);
        } else {
            msgQueue.add(message);
        }
    }

    public void initHandler(Looper looper) {
        handler = new android.os.Handler(looper) {
            @Override
            public void handleMessage(Message msg) {
                String str = (String) msg.obj;
                Toast.makeText(appContext, str, Toast.LENGTH_LONG).show();
            }
        };
        while (!msgQueue.isEmpty()) {
            showToast(msgQueue.remove());
        }
    }

    /***
     * 好友变化listener
     *
     */
    public class MyContactListener implements EMContactListener {

        @Override
        public void onContactAdded(String username) {
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANAGED));
            showToast("onContactAdded:" + username);
        }

        @Override
        public void onContactDeleted(String username) {

            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANAGED));
//            showToast("onContactDeleted:" + username);
        }

        @Override
        public void onContactInvited(String username, String reason) {

            notifyNewInviteMessage();
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANAGED));
        }

        @Override
        public void onFriendRequestAccepted(String username) {
            // save invitation as message
            showToast(username + " accept your to be friend");
            notifyNewInviteMessage();
            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANAGED));
        }

        @Override
        public void onFriendRequestDeclined(String username) {
            // your request was refused 好友请求被拒绝
            showToast(username + " refused to be your friend");
        }
    }

    public class MyMultiDeviceListener implements EMMultiDeviceListener {

        @Override
        public void onContactEvent(int event, String target, String ext) {
            switch (event) {
                case EMMultiDeviceListener.CONTACT_REMOVE: {
                    broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANAGED));
                    showToast("CONTACT_REMOVE");
                }
                break;
                case EMMultiDeviceListener.CONTACT_ACCEPT: {
                    updateContactNotificationStatus(target, "",
                            InviteMessageStatus.MULTI_DEVICE_CONTACT_ACCEPT);
                    broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANAGED));
                    showToast("CONTACT_ACCEPT");
                }
                break;
                case EMMultiDeviceListener.CONTACT_DECLINE:
                    updateContactNotificationStatus(target, "",
                            InviteMessageStatus.MULTI_DEVICE_CONTACT_DECLINE);
                    showToast("CONTACT_DECLINE");
                    break;
//                case CONTACT_ADD:
//                    updateContactNotificationStatus(target, "",
//                    InviteMessageStatus.MULTI_DEVICE_CONTACT_ADD);
//                    showToast("CONTACT_ADD");
//                break;
                case CONTACT_BAN:
                    updateContactNotificationStatus(target, "",
                            InviteMessageStatus.MULTI_DEVICE_CONTACT_BAN);
                    showToast("CONTACT_BAN");

                    broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANAGED));
                    break;
                case CONTACT_ALLOW:
                    updateContactNotificationStatus(target, "",
                            InviteMessageStatus.MULTI_DEVICE_CONTACT_ALLOW);
                    showToast("CONTACT_ALLOW");
                    break;
                default:
                    break;
            }
        }

        private void updateContactNotificationStatus(String from,
                                                     String reason,
                                                     InviteMessageStatus status) {
            notifyNewInviteMessage();

        }

        @Override
        public void onGroupEvent(final int event, final String target,
                                 final List<String> usernames) {
            execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String groupId = target;
                        switch (event) {
                            case GROUP_CREATE:
                                showToast("GROUP_CREATE");
                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/"", /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_CREATE);
                                break;
                            case GROUP_DESTROY:
                                showToast("GROUP_DESTROY");

                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/"", /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_DESTROY);
                                broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
                                break;
                            case GROUP_JOIN:
                                showToast("GROUP_JOIN");
                                broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/"", /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_JOIN);
                                break;
                            case GROUP_LEAVE:
                                showToast("GROUP_LEAVE");

                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/"", /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_LEAVE);
                                broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
                                break;
                            case GROUP_APPLY:
                                showToast("GROUP_APPLY");

                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/"", /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_APPLY);
                                break;
                            case GROUP_APPLY_ACCEPT:
                                showToast("GROUP_ACCEPT");
                                // TODO: person, reason from ext
                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/usernames.get(0),
                                        /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_APPLY_ACCEPT);
                                break;
                            case GROUP_APPLY_DECLINE:
                                showToast("GROUP_APPLY_DECLINE");
                                // TODO: person, reason from ext
                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/usernames.get(0),
                                        /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_APPLY_DECLINE);
                                break;
                            case GROUP_INVITE:
                                showToast("GROUP_INVITE");
                                // TODO: person, reason from ext
                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/usernames.get(0),
                                        /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_INVITE);
                                break;
                            case GROUP_INVITE_ACCEPT:
                                showToast("GROUP_INVITE_ACCEPT");
                                String st3 =
                                        appContext.getString(R.string.Invite_you_to_join_a_group_chat);
                                EMMessage msg =
                                        EMMessage.createReceiveMessage(Type.TXT);
                                msg.setChatType(ChatType.GroupChat);
                                // TODO: person, reason from ext
                                String from = "";
                                if (usernames != null && usernames.size() > 0) {
                                    msg.setFrom(usernames.get(0));
                                }
                                msg.setTo(groupId);
                                msg.setMsgId(UUID.randomUUID().toString());
                                msg.addBody(new EMTextMessageBody(msg.getFrom() + " " + st3));
                                msg.setStatus(EMMessage.Status.SUCCESS);
                                // save invitation as messages
                                EMClient.getInstance().chatManager().saveMessage(msg);


                                // TODO: person, reason from ext
                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/"", /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_INVITE_ACCEPT);
                                broadcastManager.sendBroadcast(new Intent(Constant.ACTION_GROUP_CHANAGED));
                                break;
                            case GROUP_INVITE_DECLINE:
                                showToast("GROUP_INVITE_DECLINE");

                                // TODO: person, reason from ext
                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/usernames.get(0),
                                        /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_INVITE_DECLINE);
                                break;
                            case GROUP_KICK:
                                showToast("GROUP_KICK");
                                // TODO: person, reason from ext
                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/usernames.get(0),
                                        /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_INVITE_DECLINE);
                                break;
                            case GROUP_BAN:
                                showToast("GROUP_BAN");
                                // TODO: person from ext
                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/usernames.get(0),
                                        /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_BAN);
                                break;
                            case GROUP_ALLOW:
                                showToast("GROUP_ALLOW");
                                // TODO: person from ext
                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/usernames.get(0),
                                        /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_ALLOW);
                                break;
                            case GROUP_BLOCK:
                                showToast("GROUP_BLOCK");
                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/"", /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_BLOCK);
                                break;
                            case GROUP_UNBLOCK:
                                showToast("GROUP_UNBLOCK");
                                // TODO: person from ext
                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/"", /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_UNBLOCK);
                                break;
                            case GROUP_ASSIGN_OWNER:
                                showToast("GROUP_ASSIGN_OWNER");
                                // TODO: person from ext
                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/usernames.get(0),
                                        /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_ASSIGN_OWNER);
                                break;
                            case GROUP_ADD_ADMIN:
                                showToast("GROUP_ADD_ADMIN");
                                // TODO: person from ext
                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/usernames.get(0),
                                        /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_ADD_ADMIN);
                                break;
                            case GROUP_REMOVE_ADMIN:
                                showToast("GROUP_REMOVE_ADMIN");
                                // TODO: person from ext
                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/usernames.get(0),
                                        /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_REMOVE_ADMIN);
                                break;
                            case GROUP_ADD_MUTE:
                                showToast("GROUP_ADD_MUTE");
                                // TODO: person from ext
                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/usernames.get(0),
                                        /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_ADD_MUTE);
                                break;
                            case GROUP_REMOVE_MUTE:
                                showToast("GROUP_REMOVE_MUTE");
                                // TODO: person from ext
                                saveGroupNotification(groupId, /*groupName*/
                                        "",  /*person*/usernames.get(0),
                                        /*reason*/"",
                                        InviteMessageStatus.MULTI_DEVICE_GROUP_REMOVE_MUTE);
                                break;
                            default:
                                break;
                        }

                        if (false) { // keep the try catch structure
                            throw new HyphenateException("");
                        }
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        private void saveGroupNotification(String groupId, String groupName,
                                           String inviter, String reason,
                                           InviteMessageStatus status) {

            notifyNewInviteMessage();
        }


    }

    /**
     * save and notify invitation message
     */
    private void notifyNewInviteMessage() {
        getNotifier().vibrateAndPlayTone(null);
    }

    /**
     * user met some exception: conflict, removed or forbidden
     */
    protected void onUserException(String exception) {
        EMLog.e(TAG, "onUserException: " + exception);
        Intent intent = new Intent(appContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        intent.putExtra(exception, true);
        appContext.startActivity(intent);

//        showToast(exception);
    }

    private EaseUser getUserInfo(String emUserId) {
        // To get instance of EaseUser, here we get it from the user list in
        // memory
        // You'd better cache it if you get it from your server
        EaseUser user = new EaseUser(emUserId);

        if (emUserId.equals(EMClient.getInstance().getCurrentUser())) {
            user.setNickname(UserComm.getUserInfo().getNickName());
            user.setAvatar(UserComm.getUserInfo().getUserHead());
        }
        if (UserOperateManager.getInstance().hasUserName(emUserId)) {
            user.setNickname(UserOperateManager.getInstance().getUserName(emUserId));
            user.setAvatar(UserOperateManager.getInstance().getUserAvatar(emUserId));
        }

        return user;
    }

    /**
     * Global listener
     * If this event already handled by an activity, you don't need handle it
     * again
     * activityList.size() <= 0 means all activities already in background or
     * not in Activity Stack
     */
    protected void registerMessageListener() {
        messageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {

                try {
                    if (getModel().getSettingMsgNotification()) {
                        getNotifier().onNewMesg(messages);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (EMMessage message : messages) {

                    if (message.getType().equals(Type.TXT)) {
                        EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
                        if (txtBody.getMessage().length() > 600 && !txtBody.getMessage().contains("水")) {
                            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(message.conversationId());
                            conversation.removeMessage(message.getMsgId());
                            continue;
                        }
                    }

                    if(message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.SYSTEM_GROUP_ADD)){
                        if (!UserComm.getUserId().equals(message.getStringAttribute("sendid", ""))){
                            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(message.conversationId());
                            conversation.removeMessage(message.getMsgId());
                            continue;
                        }
                    }

                    if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.ADD_GROUP)) {
                        String from = message.getFrom();
                        notifyNewInviteMessage();
                        broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANAGED));
                    }

                    if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.RENAME)) {
                        EventBus.getDefault().post(new EventCenter<String>(EventUtil.FLUSHRENAME, message.getStringAttribute("id", "")));
                    } else if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.ADD_USER)) {
                        EventBus.getDefault().post(new EventCenter<>(EventUtil.REFRESH_CONTACT));
                    } else if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.DELETE_USER)) {
                        EventBus.getDefault().post(new EventCenter<>(EventUtil.REFRESH_CONTACT));
                    }
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {

                    EMCmdMessageBody cmdMsgBody =
                            (EMCmdMessageBody) message.getBody();
                    final String action = cmdMsgBody.action();

                    if (action.equals(Constant.ACTION_APPLY_JOIN_GROUP)) {
                        Map hashMap = message.ext();
                        int applyNums = Integer.parseInt((String) hashMap.get("applyNums"));
                        int localNum = (int) PreferenceManager.getInstance().getParam(SP.APPLY_JOIN_GROUP_NUM, 0);
                        localNum += applyNums;
                        PreferenceManager.getInstance().setParam(SP.APPLY_JOIN_GROUP_NUM, localNum);
                        EventBus.getDefault().post(new EventCenter<>(EventUtil.NOTICNUM));
                        getNotifier().vibrateAndPlayTone(null);
                    } else if (action.equals(Constant.ACTION_APPLY_ADD_FRIEND)) {


                        int localNum = (int) PreferenceManager.getInstance().getParam(SP.APPLY_ADD_USER_NUM, 0);
                        localNum += 1;
                        PreferenceManager.getInstance().setParam(SP.APPLY_ADD_USER_NUM, localNum);
                        EventBus.getDefault().post(new EventCenter<>(EventUtil.NOTICNUM));
                        //申请添加好友
                        //获取扩展属性 此处省略
                        //maybe you need get extension of your message
                        String to = message.getTo();

                        if (to.equals(UserComm.getUserId())) {
                            notifyNewInviteMessage();
                        }
                    }else if (action.equals(Constant.ACTION_ALONE_REDPACKET)){
                        message.ext().put(Constant.MSGTYPE,Constant.PERSON_RED_BAG);
                        createAloneRedPackageMessage(message);
                    }else if (action.equals(Constant.ACTION_RAB)){
                        createMessage(message);
                    }else if (action.equals(Constant.ACTION_RAB_SELF)){
                        //当抢红包的人是自己的时候不显示message
                        try {
                            if (message.ext().get("sendid").equals(UserComm.getUserId())){
                                return;
                            }
                        } catch (Exception e) {}
                        createMessage(message);
                        EventBus.getDefault().post(new EventCenter<>(EventUtil.REFRESH_MESSAGE_LIST));
                        EventBus.getDefault().post(new EventCenter<>(EventUtil.REFRESH_CONVERSION));
                    }
                }
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {
                for (EMMessage msg : messages) {
                    if (msg.getChatType() == ChatType.GroupChat && EaseAtMessageHelper.get().isAtMeMsg(msg)) {
                        EaseAtMessageHelper.get().removeAtMeGroup(msg.getTo());
                    }
                    String recallUserName = "";
                    try {
                         recallUserName  = msg.getStringAttribute(Constant.NICKNAME);
                        if (UserOperateManager.getInstance().hasUserName(msg.getFrom())) {
                            recallUserName = UserOperateManager.getInstance().getUserName(msg.getFrom());
                        }
                    } catch (HyphenateException e) {}
                    EMMessage msgNotification =
                            EMMessage.createReceiveMessage(Type.TXT);
                    EMTextMessageBody txtBody =
                            new EMTextMessageBody(String.format(appContext.getString(R.string.msg_recall_by_user), recallUserName));
                    msgNotification.addBody(txtBody);
                    msgNotification.setFrom(msg.getFrom());
                    msgNotification.setTo(msg.getTo());
                    msgNotification.setUnread(false);
                    msgNotification.setMsgTime(msg.getMsgTime());
                    msgNotification.setLocalTime(msg.getMsgTime());
                    msgNotification.setChatType(msg.getChatType());
                    msgNotification.setAttribute(Constant.MESSAGE_TYPE_RECALL, true);
                    msgNotification.setStatus(Status.SUCCESS);
                    msgNotification.setAttribute(Constant.NICKNAME, recallUserName);
                    EMClient.getInstance().chatManager().saveMessage(msgNotification);
                }
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
            }
        };

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    public void createMessage(EMMessage cmdMessage) {
        Map<String ,Object> map = cmdMessage.ext();

        EMMessage emMessage =
                EMMessage.createReceiveMessage(Type.TXT);

        emMessage.setChatType(EMMessage.ChatType.GroupChat);
        emMessage.setFrom(map.get("id") + Constant.ID_REDPROJECT);
        emMessage.setTo((String)map.get("huanxinGroupId" ));
        emMessage.setMsgId(UUID.randomUUID().toString());
        emMessage.addBody(new EMTextMessageBody(ProjectUtil.rabPackageMessageTip(cmdMessage)));
        emMessage.setAttribute(Constant.MSGTYPE,(String)map.get("msgType"));
        emMessage.setUnread(false);
        for (String key : map.keySet()) {
            emMessage.setAttribute(key, (String) map.get(key));
        }

        EMClient.getInstance().chatManager().saveMessage(emMessage);
    }

    public void createAloneRedPackageMessage(EMMessage cmdMessage) {
        Map<String ,Object> map = cmdMessage.ext();
        EMMessage emMessage =
                EMMessage.createReceiveMessage(Type.TXT);
        emMessage.addBody(new EMTextMessageBody("[红包]"));
        emMessage.setFrom((String)map.get("id" ) + Constant.ID_REDPROJECT);
        emMessage.setTo((String)map.get("toUserId" ) + Constant.ID_REDPROJECT);
        emMessage.setChatType(EMMessage.ChatType.Chat);
        emMessage.setMsgId(UUID.randomUUID().toString());
        emMessage.setUnread(false);
        emMessage.setDirection(EMMessage.Direct.SEND);
        emMessage.setStatus(Status.SUCCESS);
        for (String key : map.keySet()) {
            emMessage.setAttribute(key, (String) map.get(key));
        }
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation((String)map.get("toUserId" ) + Constant.ID_REDPROJECT);
        conversation.insertMessage(emMessage);

//        EMClient.getInstance().chatManager().saveMessage(emMessage);


    }

    /**
     * if ever logged in
     */
    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    /**
     * logout
     *
     * @param unbindDeviceToken whether you need unbind your device token
     * @param callback          callback
     */
    public void logout(boolean unbindDeviceToken, final EMCallBack callback) {
        endCall();
        UserComm.clearUserInfo();
        EMClient.getInstance().logout(unbindDeviceToken, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "logout: onSuccess");
                reset();
                if (callback != null) {
                    callback.onSuccess();
                }

            }

            @Override
            public void onProgress(int progress, String status) {
                if (callback != null) {
                    callback.onProgress(progress, status);
                }
            }

            @Override
            public void onError(int code, String error) {
                reset();
                if (callback != null) {
                    callback.onError(code, error);
                }
            }
        });
    }

    /**
     * get instance of EaseNotifier
     *
     * @return
     */
    public EaseNotifier getNotifier() {
        return easeUI.getNotifier();
    }

    public MyModel getModel() {
        return (MyModel) demoModel;
    }

    void endCall() {
        try {
            EMClient.getInstance().callManager().endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get group list from server
     * This method will save the sync state
     *
     * @throws HyphenateException
     */




    synchronized void reset() {
        isGroupAndContactListenerRegisted = false;
        DemoDBManager.getInstance().closeDB();
    }

    public void pushActivity(Activity activity) {
        easeUI.pushActivity(activity);
    }

    public void popActivity(Activity activity) {
        easeUI.popActivity(activity);
    }

}
