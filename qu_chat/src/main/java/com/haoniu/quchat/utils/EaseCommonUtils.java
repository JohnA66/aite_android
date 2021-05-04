/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haoniu.quchat.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.aite.chat.R;
import com.google.gson.reflect.TypeToken;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.EaseConstant;
import com.haoniu.quchat.domain.EaseUser;
import com.haoniu.quchat.utils.hxSetMessageFree.EaseSharedUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.HanziToPinyin;
import com.hyphenate.util.HanziToPinyin.Token;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EaseCommonUtils {
    private static final String TAG = "CommonUtils";

    /**
     * check if network avalable
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable() && mNetworkInfo.isConnected();
            }
        }

        return false;
    }

    /**
     * check if sdcard exist
     *
     * @return
     */
    public static boolean isSdcardExist() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static EMMessage createExpressionMessage(String toChatUsername, String expressioName, String identityCode) {
        EMMessage message = EMMessage.createTxtSendMessage("[" + expressioName + "]", toChatUsername);
        if (identityCode != null) {
            message.setAttribute(EaseConstant.MESSAGE_ATTR_EXPRESSION_ID, identityCode);
        }
        message.setAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, true);
        return message;
    }

    /**
     * Get digest according message type and content
     *
     * @param message
     * @param context
     * @return
     */
    public static String getMessageDigest(EMMessage message, Context context) {
        String digest = "";
        switch (message.getType()) {
            case LOCATION:
                if (message.direct() == EMMessage.Direct.RECEIVE) {
                    digest = getString(context, R.string.location_recv);
//                    digest = String.format(digest, message.getFrom());
                    digest = "[位置]";
                    return digest;
                } else {
                    digest = getString(context, R.string.location_prefix);
                }
                break;
            case IMAGE:
                digest = getString(context, R.string.picture);
                break;
            case VOICE:
                digest = getString(context, R.string.voice_prefix);
                break;
            case VIDEO:
                digest = getString(context, R.string.video);
                break;
            case TXT:
                EMTextMessageBody txtBody = (EMTextMessageBody) message.getBody();
                if (message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                    digest = getString(context, R.string.voice_call) + txtBody.getMessage();
                } else if (message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    digest = getString(context, R.string.video_call) + txtBody.getMessage();
                } else if (message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)) {
                    if (!TextUtils.isEmpty(txtBody.getMessage())) {
                        digest = txtBody.getMessage();
                    } else {
                        digest = getString(context, R.string.dynamic_expression);
                    }
                } else {
                    digest = txtBody.getMessage();
                }
                switch (message.getStringAttribute(Constant.MSGTYPE, "")) {
                    case Constant.ADDROOM:
                        digest = "创建房间成功，赶快邀请好友一起抢红包吧";
                        break;
                    case Constant.ADDUSER:
                        digest = message.getStringAttribute(Constant.NAME, "") + "加入了房间";
                        break;
                    case Constant.ADDUSERS:
                        String strNick = "";
                        if (!"".equals(message.getStringAttribute("userMap", ""))) {
                            Map<String, String> map = FastJsonUtil.getObject(message.getStringAttribute("userMap", ""), new TypeToken<Map<String, String>>() {
                            }.getType());
                            for (String nick : map.keySet()) {
                                strNick += map.get(nick) + ",";
                            }
                            if (strNick.length() > 0) {
                                strNick = strNick.substring(0, strNick.length() - 1);
                            }
                            if (strNick.length() > 30) {
                                strNick = strNick.substring(0, 30);
                                strNick += strNick + "等";
                            }
                        }
                        digest = strNick;
                        break;
                    case Constant.DELUSER:
                        digest = message.getStringAttribute(Constant.NAME, "") + "退出了房间";
                        break;
                    case Constant.SHOTUSER:
                        digest = message.getStringAttribute(Constant.NAME, "") + "被房主踢出了房间";
                        break;
                    case Constant.REDPACKET:
//                        digest = message.getStringAttribute(Constant.NICKNAME, "") + "发了一个红包";
                        digest = "[艾特红包]" + message.getStringAttribute("remark", "恭喜发财，大吉大利！ ");
                        break;
                    default:
                        break;
                }

                break;
            case FILE:
                digest = getString(context, R.string.file);
                break;
            default:
                EMLog.e(TAG, "error, unknow type");
                return "";
        }

        return digest;
    }

    static String getString(Context context, int resId) {
        return context.getResources().getString(resId);
    }


    /**
     * 处理消息
     */
    public static void initMessage(List<EMMessage> messages) {

        for (EMMessage message : messages) {
            if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.RABEND) || message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.RAB) || message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.WELFARE) || message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.PUNISHMENT) || message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.ADDROOM) || message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.DELUSER)) {
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(message.getTo());
                conversation.markMessageAsRead(message.getMsgId());
            } else if ("系统管理员".equals(message.getFrom())) {
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(message.getFrom());
//                conversation.removeMessage(message.getMsgId());
                conversation.markMessageAsRead(message.getMsgId());

            } else if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.ADDUSER)) {
//                EaseGroupInfo easeGroupInfo = MyHelper.getInstance().getGroupById(message.getTo());
//                if (!message.getStringAttribute(Constant.ROOMURL, "").equals("")){
//                    easeGroupInfo.setHead(message.getStringAttribute(Constant.ROOMURL, ""));
//                }
//                MyHelper.getInstance().saveGroup(easeGroupInfo);
            } else if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.DELUSER)) {
//                EaseGroupInfo easeGroupInfo = MyHelper.getInstance().getGroupById(message.getTo());
//                if (!message.getStringAttribute(Constant.ROOMURL, "").equals("")){
//                    easeGroupInfo.setHead(message.getStringAttribute(Constant.ROOMURL, ""));
//                }
//                MyHelper.getInstance().saveGroup(easeGroupInfo);
            } else if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.ADD_GROUP)) {
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(message.getFrom());
                conversation.removeMessage(message.getMsgId());
            }
        }
    }

    /**
     * 处理消息
     */
    public static void initMessage(EMMessage message) {
        if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.RABEND) || message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.RAB) || message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.WELFARE) || message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.PUNISHMENT) || message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.ADDROOM) || message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.DELUSER)) {
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(message.getTo());
            conversation.markMessageAsRead(message.getMsgId());
        } else if ("系统管理员".equals(message.getFrom())) {
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(message.getFrom());
//            conversation.removeMessage(message.getMsgId());
            conversation.markMessageAsRead(message.getMsgId());
        } else if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.ADDUSER)) {

        } else if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.DELUSER)) {
        } else if (message.getStringAttribute(Constant.MSGTYPE, "").equals(Constant.ADD_GROUP)) {
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(message.getFrom());
            conversation.removeMessage(message.getMsgId());
        }
    }

    /**
     * get top activity
     *
     * @param context
     * @return
     */
    public static String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null) {
            return runningTaskInfos.get(0).topActivity.getClassName();
        } else {
            return "";
        }
    }

    /**
     * set initial letter of according user's nickname( username if no nickname)
     *
     * @param user
     */
    public static void setUserInitialLetter(EaseUser user) {
        final String DefaultLetter = "#";
        String letter = DefaultLetter;

        final class GetInitialLetter {
            String getLetter(String name) {
                if (TextUtils.isEmpty(name)) {
                    return DefaultLetter;
                }
                char char0 = name.toLowerCase().charAt(0);
                if (Character.isDigit(char0)) {
                    return DefaultLetter;
                }
                ArrayList<Token> l = HanziToPinyin.getInstance().get(name.substring(0, 1));
                if (l != null && l.size() > 0 && l.get(0).target.length() > 0) {
                    Token token = l.get(0);
                    String letter = token.target.substring(0, 1).toUpperCase();
                    char c = letter.charAt(0);
                    if (c < 'A' || c > 'Z') {
                        return DefaultLetter;
                    }
                    return letter;
                }
                return DefaultLetter;
            }
        }

        if (!TextUtils.isEmpty(user.getNickname())) {
            letter = new GetInitialLetter().getLetter(user.getNickname());
            user.setInitialLetter(letter);
            return;
        }
        if (letter.equals(DefaultLetter) && !TextUtils.isEmpty(user.getUsername())) {
            letter = new GetInitialLetter().getLetter(user.getNickname());
        }
        user.setInitialLetter(letter);
    }

    /**
     * change the chat type to EMConversationType
     *
     * @param chatType
     * @return
     */
    public static EMConversationType getConversationType(int chatType) {
        if (chatType == EaseConstant.CHATTYPE_SINGLE) {
            return EMConversationType.Chat;
        } else if (chatType == EaseConstant.CHATTYPE_GROUP) {
            return EMConversationType.GroupChat;
        } else {
            return EMConversationType.ChatRoom;
        }
    }

    /**
     * \~chinese
     * 判断是否是免打扰的消息,如果是app中应该不要给用户提示新消息
     *
     * @param message return
     *                <p>
     *                \~english
     *                check if the message is kind of slient message, if that's it, app should not play tone or vibrate
     * @param message
     * @return
     */
    public static boolean isSilentMessage(EMMessage message) {

        //单独群聊设置免打扰
        if (message.getChatType() == EMMessage.ChatType.GroupChat &&
                !EaseSharedUtils.isEnableMsgRing(Utils.getContext(), EMClient.getInstance().getCurrentUser(), message.getTo())) {
            return true;

        } else if (message.getChatType() == EMMessage.ChatType.Chat && !EaseSharedUtils.isEnableMsgRing(Utils.getContext(), EMClient.getInstance().getCurrentUser(), message.getFrom())) {
            //单聊设置免打扰  (单聊设置 message.getFrom)
            return true;

        }


        //全局设置
        return message.getBooleanAttribute("em_ignore_notification", false);
    }

}
