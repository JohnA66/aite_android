package com.haoniu.quchat.operate;

import android.content.Context;
import android.text.TextUtils;

import com.aite.chat.BuildConfig;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.constant.SP;
import com.haoniu.quchat.entity.GroupDetailInfo;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.model.ContactListInfo;
import com.haoniu.quchat.utils.ImageUtil;
import com.haoniu.quchat.utils.PreferenceManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.json.FastJsonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.haoniu.quchat.base.Constant.SEPARATOR_UNDERLINE;

public class UserOperateManager {

    //类初始化时，不初始化这个对象(延时加载，真正用的时候再创建)
    private static UserOperateManager instance;

    //会把联系人接口 和 群详情接口拿到的用户数据存一份在hash里
    //存储方式为 id跟要展示的名称
    private HashMap<String, String> userKVHash;

    //会把联系人接口 和 群详情接口拿到的用户数据存一份在hash里
    //存储方式为 id跟要展示的名称
    private HashMap<String, String> userAvatarKVHash;

    private int contactVersion = -1;

    private List<ContactListInfo.DataBean> contactList;

    //构造器私有化
    private UserOperateManager() {
        userKVHash = new HashMap<>();
        userAvatarKVHash = new HashMap<>();
    }



    //方法同步，调用效率低
    public static synchronized UserOperateManager getInstance() {
        if (instance == null) {
            instance = new UserOperateManager();
        }
        return instance;
    }


    public int getContactVersion() {
        return contactVersion;
    }

    public void setContactVersion(int version) {
        this.contactVersion = version;
    }


    public void saveContactListToLocal(ContactListInfo info, String json){
        contactList = info.getData();
        contactVersion = info.getCacheVersion();
        updateUserList(info.getData());
        PreferenceManager.getInstance().setParam(SP.SP_CONTACT_DATA ,json);
    }

    public List<ContactListInfo.DataBean> getContactList() {
        if (contactList == null) {
            String contactLocalData = (String) PreferenceManager.getInstance().getParam(SP.SP_CONTACT_DATA ,"");
            if (!TextUtils.isEmpty(contactLocalData)){
                ContactListInfo info = FastJsonUtil.getObject(contactLocalData, ContactListInfo.class);
                contactList = info.getData();
                contactVersion = info.getCacheVersion();
                updateUserList(info.getData());
            }
        }
        return contactList;
    }


    public boolean hasUserName(String userId) {

        if (userId.contains(Constant.ID_REDPROJECT)) {
            return userKVHash.containsKey(userId.split("-")[0]);
        } else {
            return userKVHash.containsKey(userId);
        }
    }

    public String getUserName(EMMessage message){
        if (hasUserName(message.getFrom())) {
            return getUserName(message.getFrom());
        }else {
            return message.getUserName();
        }
    }

    public String getUserName(String userId) {
        if (userId.contains(Constant.ID_REDPROJECT)) {
            return userKVHash.get(userId.split("-")[0]);
        }
        return userKVHash.get(userId);
    }

    public void updateUserName(String userId, String nickName) {
        userKVHash.put(userId, nickName);
    }

    public String getUserAvatar(String userId) {
        if (userId.contains(Constant.ID_REDPROJECT)) {
            return ImageUtil.checkimg(userAvatarKVHash.get(userId.split("-")[0]));
        }
        return ImageUtil.checkimg(userAvatarKVHash.get(userId));
    }


    public void updateUserList(List<ContactListInfo.DataBean> data){
        for (ContactListInfo.DataBean datum : data) {
            userKVHash.put(datum.getFriendUserId(), datum.getFriendNickName());
            userAvatarKVHash.put(datum.getFriendUserId(), datum.getFriendUserHead());
        }
    }

    public void loadGroupUserData(String toChatId) {

            GroupDetailInfo info = GroupOperateManager.getInstance().getGroupData(toChatId);
            if (info == null)
                return;

            List<GroupDetailInfo.GroupUserDetailVoListBean> groupUserList = info.getGroupUserDetailVoList();
            for (GroupDetailInfo.GroupUserDetailVoListBean groupUserDetailVoListBean : groupUserList) {
                if (!TextUtils.isEmpty(groupUserDetailVoListBean.getFriendNickName())) {
                    userKVHash.put(groupUserDetailVoListBean.getUserId(), groupUserDetailVoListBean.getFriendNickName());
                } else if (!TextUtils.isEmpty(groupUserDetailVoListBean.getUserNickName())) {
                    userKVHash.put(groupUserDetailVoListBean.getUserId(), groupUserDetailVoListBean.getUserNickName());
                } else {
                    userKVHash.put(groupUserDetailVoListBean.getUserId(), groupUserDetailVoListBean.getNickName());
                }
            }

    }

    public void queryGroupDetail(String groupId, String emGroupId) {
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", groupId);
        ApiClient.requestNetHandle(MyApplication.getInstance(), AppConfig.GROUP_DETAIL, "",
                map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        if (json != null && json.length() > 0) {
                            GroupDetailInfo groupDetailInfo = FastJsonUtil.getObject(json,
                                    GroupDetailInfo.class);

                            for (GroupDetailInfo.GroupUserDetailVoListBean groupUserDetailVoListBean : groupDetailInfo.getGroupUserDetailVoList()) {
                                if (!TextUtils.isEmpty(groupUserDetailVoListBean.getFriendNickName())) {
                                    userKVHash.put(groupUserDetailVoListBean.getUserId(), groupUserDetailVoListBean.getFriendNickName());
                                } else if (!TextUtils.isEmpty(groupUserDetailVoListBean.getUserNickName())) {
                                    userKVHash.put(groupUserDetailVoListBean.getUserId(), groupUserDetailVoListBean.getUserNickName());
                                } else {
                                    userKVHash.put(groupUserDetailVoListBean.getUserId(), groupUserDetailVoListBean.getNickName());
                                }
                            }

                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                });
    }


    //群分享二维码 数据排序 服务器群id - 标志符 - 邀请人id - 群主环信id
    public void scanInviteContact(Context context, String qrResult) {


        String groupId = qrResult.split(SEPARATOR_UNDERLINE)[0];
        String inviterId = qrResult.split(SEPARATOR_UNDERLINE)[2];

        Map<String, Object> map = new HashMap<>(4);
        map.put("type", 1);
        map.put("groupId", groupId);
        map.put("inviterId", inviterId);
        map.put("userId", UserComm.getUserInfo().getUserId());
        ApiClient.requestNetHandle(context, AppConfig.SAVE_GROUP_USER,
                "正在加入...", map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        ToastUtil.toast(msg);
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.toast(msg);
                    }
                });
    }

    public void sendNotification(String groupOwnerId) {
        EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);

        String action = Constant.ACTION_APPLY_JOIN_GROUP;
        EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
        cmdMsg.addBody(cmdBody);
        cmdMsg.setTo(groupOwnerId);
        cmdMsg.setFrom(UserComm.getUserId());
        cmdMsg.setAttribute("applyNums",1);
        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
        if (BuildConfig.DEBUG)
            ToastUtil.toast("发送透传消息 通知群主");
    }


}
