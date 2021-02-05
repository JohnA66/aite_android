package com.haoniu.quchat.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.haoniu.quchat.domain.EaseAvatarOptions;
import com.haoniu.quchat.domain.EaseEmojicon;
import com.haoniu.quchat.domain.EaseUser;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.model.EaseAtMessageHelper;
import com.haoniu.quchat.model.EaseNotifier;
import com.haoniu.quchat.utils.EaseCommonUtils;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class EaseUI {
    private static final String TAG = EaseUI.class.getSimpleName();

    /**
     * the global EaseUI instance
     */
    private static EaseUI instance = null;

    /**
     * user profile provider
     */
    private EaseUserProfileProvider userProvider;

    private EaseSettingsProvider settingsProvider;

    private EaseAvatarOptions avatarOptions;

    /**
     * application context
     */
    private Context appContext = null;

    /**
     * init flag: test if the sdk has been inited before, we don't need to init again
     */
    private boolean sdkInited = false;

    /**
     * the notifier
     */
    private EaseNotifier notifier = null;

    /**
     * save foreground Activity which registered eventlistener
     */
    private List<Activity> activityList = new ArrayList<Activity>();

    public void pushActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(0, activity);
        }
    }

    public void popActivity(Activity activity) {
        activityList.remove(activity);
    }

    public Activity getTopActivity() {
        return activityList.get(0);
    }

    private EaseUI() {
    }

    /**
     * get instance of EaseUI
     *
     * @return
     */
    public synchronized static EaseUI getInstance() {
        if (instance == null) {
            instance = new EaseUI();
        }
        return instance;
    }

    /**
     * this function will initialize the SDK and easeUI kit
     *
     * @param context
     * @param options use default if options is null
     * @return
     */
    public synchronized boolean init(Context context, EMOptions options) {
        if (sdkInited) {
            return true;
        }
        appContext = context;

        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);

        Log.d(TAG, "process app name : " + processAppName);

        // if there is application has remote service, application:onCreate() maybe called twice
        // this check is to make sure SDK will initialized only once
        // return if process name is not application's name since the package name is the default process name
        if (processAppName == null || !processAppName.equalsIgnoreCase(appContext.getPackageName())) {
            Log.e(TAG, "enter the service process!");
            return false;
        }
        if (options == null) {
            EMClient.getInstance().init(context, initChatOptions());
        } else {
            EMClient.getInstance().init(context, options);
        }

        initNotifier();
        registerMessageListener();

        if (settingsProvider == null) {
            settingsProvider = new DefaultSettingsProvider();
        }

        sdkInited = true;
        return true;
    }


    protected EMOptions initChatOptions() {

        EMOptions options = new EMOptions();
        // change to need confirm contact invitation
        options.setAcceptInvitationAlways(false);
        // set if need read ack
        options.setRequireAck(true);
        // set if need delivery ack
        options.setRequireDeliveryAck(false);
        return options;
    }

    void initNotifier() {
        notifier = createNotifier();
        notifier.init(appContext);
    }

    private void registerMessageListener() {
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                EaseAtMessageHelper.get().parseMessages(messages);
                EaseCommonUtils.initMessage(messages);
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> messages) {
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {

            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {

            }
//            msg{from:e49e078df19b11ea909d506b4bbe21ec, to:53b54a96e39711ea909d506b4bbe21ec-liaoqu body:cmd:"action_apply_add_friend"
            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage emMessage : messages) {
                    if (emMessage.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.REDPACKET)) {
                        String currentUser = UserComm.getUserInfo().getUserId() +Constant.ID_REDPROJECT;
                        if (currentUser.contains(emMessage.getFrom())) {
                        } else {
                            continue;
                        }
                    }


                    EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) emMessage.getBody();
                    final String action = cmdMsgBody.action();
                    if (action.equals(Constant.ACTION_APPLY_ADD_FRIEND)) {
                        continue;
                    }
                    if (action.contains(Constant.ACTION_APPLY_JOIN_GROUP)) {
                        continue;
                    }
                    if (action.contains(Constant.ACTION_ALONE_REDPACKET)) {
                        continue;
                    }
                    if (action.equals(Constant.ACTION_RAB)){
                        continue;
                    }
                    if (action.equals(Constant.ACTION_RAB_SELF)){
                        continue;
                    }

                    EaseCommonUtils.initMessage(emMessage);
                    EMMessage msg = EMMessage.createTxtSendMessage(emMessage.getStringAttribute("message",""), emMessage.getTo());
                    msg.setChatType(EMMessage.ChatType.GroupChat);
                    msg.setFrom(emMessage.getFrom());
                    msg.setTo(emMessage.getTo());
                    msg.setMsgId(emMessage.getMsgId());
                    msg.setMsgTime(emMessage.getMsgTime());

                    if (emMessage.getFrom().equals(UserComm.getUserId())) {

                        msg.setDirection(EMMessage.Direct.SEND);
                    } else {
                        msg.setDirection(EMMessage.Direct.RECEIVE);
                    }
                    msg.setUnread(false);
                    msg.setAttribute("cmd",true);
                    for (String key : emMessage.ext().keySet()) {
                        if (emMessage.ext().get(key) instanceof Integer) {
                            try {
                                msg.setAttribute(key, Integer.valueOf((Integer) emMessage.ext().get(key)));
                            } catch (Exception e) {
                            }
                        } else if (emMessage.ext().get(key) instanceof String) {
                            try {
                                msg.setAttribute(key, emMessage.ext().get(key).toString());
                            } catch (Exception e) {
                            }
                        } else if (emMessage.ext().get(key) instanceof Boolean) {
                            try {
                                msg.setAttribute(key, emMessage.ext().get(key).toString());
                            } catch (Exception e) {
                            }
                        } else if (emMessage.ext().get(key) instanceof Boolean) {
                            try {
                                msg.setAttribute(key, (Boolean) emMessage.ext().get(key));
                            } catch (Exception e) {
                            }
                        } else if (emMessage.ext().get(key) instanceof Long) {
                            try {
                                msg.setAttribute(key, (Long) emMessage.ext().get(key));
                            } catch (Exception e) {
                            }
                        }
                    }
                    //保存消息
                    EMClient.getInstance().chatManager().saveMessage(msg);
                }
            }
        });
    }

    protected EaseNotifier createNotifier() {
        return new EaseNotifier();
    }

    public EaseNotifier getNotifier() {
        return notifier;
    }

    public boolean hasForegroundActivies() {
        return activityList.size() != 0;
    }


    public void setAvatarOptions(EaseAvatarOptions avatarOptions) {
        this.avatarOptions = avatarOptions;
    }

    public EaseAvatarOptions getAvatarOptions() {
        return avatarOptions;
    }


    /**
     * set user profile provider
     *
     * @param
     */
    public void setUserProfileProvider(EaseUserProfileProvider userProvider) {
        this.userProvider = userProvider;
    }

    /**
     * get user profile provider
     *
     * @return
     */
    public EaseUserProfileProvider getUserProfileProvider() {
        return userProvider;
    }

    public void setSettingsProvider(EaseSettingsProvider settingsProvider) {
        this.settingsProvider = settingsProvider;
    }

    public EaseSettingsProvider getSettingsProvider() {
        return settingsProvider;
    }


    /**
     * check the application process name if process name is not qualified, then we think it is a service process and we will not init SDK
     *
     * @param pID
     * @return
     */
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = appContext.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                    // Log.d("Process", "Id: "+ info.pid +" ProcessName: "+
                    // info.processName +"  Label: "+c.toString());
                    // processName = c.toString();
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    /**
     * User profile provider
     *
     * @author wei
     */
    public interface EaseUserProfileProvider {
        /**
         * return EaseUser for input username
         *
         * @param username
         * @return
         */
        EaseUser getUser(String username);
    }

    /**
     * Emojicon provider
     */
    public interface EaseEmojiconInfoProvider {
        /**
         * return EaseEmojicon for input emojiconIdentityCode
         *
         * @param emojiconIdentityCode
         * @return
         */
        EaseEmojicon getEmojiconInfo(String emojiconIdentityCode);

        /**
         * get Emojicon map, key is the text of emoji, value is the resource id or local path of emoji icon(can't be URL on internet)
         *
         * @return
         */
        Map<String, Object> getTextEmojiconMapping();
    }

    private EaseEmojiconInfoProvider emojiconInfoProvider;

    /**
     * Emojicon provider
     *
     * @return
     */
    public EaseEmojiconInfoProvider getEmojiconInfoProvider() {
        return emojiconInfoProvider;
    }

    /**
     * set Emojicon provider
     *
     * @param emojiconInfoProvider
     */
    public void setEmojiconInfoProvider(EaseEmojiconInfoProvider emojiconInfoProvider) {
        this.emojiconInfoProvider = emojiconInfoProvider;
    }

    /**
     * new message options provider
     */
    public interface EaseSettingsProvider {
        boolean isMsgNotifyAllowed(EMMessage message);

        boolean isMsgSoundAllowed(EMMessage message);

        boolean isMsgVibrateAllowed(EMMessage message);

        boolean isSpeakerOpened();
    }

    /**
     * default settings provider
     */
    protected class DefaultSettingsProvider implements EaseSettingsProvider {

        @Override
        public boolean isMsgNotifyAllowed(EMMessage message) {
            // TODO Auto-generated method stub
            return true;
        }

        @Override
        public boolean isMsgSoundAllowed(EMMessage message) {
            return true;
        }

        @Override
        public boolean isMsgVibrateAllowed(EMMessage message) {
            return true;
        }

        @Override
        public boolean isSpeakerOpened() {
            return true;
        }
    }

    public Context getContext() {
        return appContext;
    }
}
