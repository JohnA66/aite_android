package com.haoniu.quchat.operate;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.haoniu.quchat.constant.SP;
import com.haoniu.quchat.entity.GroupDetailInfo;
import com.haoniu.quchat.entity.GroupInfo;
import com.haoniu.quchat.entity.MyGroupInfoList;
import com.haoniu.quchat.utils.ImageUtil;
import com.haoniu.quchat.utils.PreferenceManager;
import com.zds.base.json.FastJsonUtil;

import java.util.HashMap;
import java.util.WeakHashMap;

public class GroupOperateManager {
    //类初始化时，不初始化这个对象(延时加载，真正用的时候再创建)
    private static GroupOperateManager instance;

    private HashMap<String, String> groupIdHashMap;

    private HashMap<String, GroupInfo> groupsHashMap;

    private WeakHashMap<String, GroupDetailInfo> groupDetailWeakHashMap;

    //构造器私有化
    private GroupOperateManager() {
        groupsHashMap = new HashMap<>();
        groupIdHashMap = new HashMap<>();
        groupDetailWeakHashMap = new WeakHashMap<>();
    }

    //方法同步，调用效率低
    public static synchronized GroupOperateManager getInstance() {
        if (instance == null) {
            instance = new GroupOperateManager();
        }
        return instance;
    }

    public void saveGroupsInfo(MyGroupInfoList myGroupInfo, String json) {
        for (GroupInfo datum : myGroupInfo.getData()) {
            groupsHashMap.put(datum.getHuanxinGroupId(), datum);
        }
    }


    public HashMap getGroups() {
        return groupsHashMap;
    }

    //获取群信息
    public GroupInfo getGroupInfo(String emChatId) {
        return groupsHashMap.get(emChatId);
    }

    public String getGroupAvatar(String emChatId) {
        if (groupsHashMap.containsKey(emChatId)) {
            return ImageUtil.checkimg(groupsHashMap.get(emChatId).getGroupHead());
        }
        return "";
    }

    public String getGroupId(String emChatId) {
        if (groupIdHashMap.containsKey(emChatId))
            return groupIdHashMap.get(emChatId);

        String groupId = (String) PreferenceManager.getInstance().getParam(SP.SP_GROUP_ID + emChatId, "");

        if (!TextUtils.isEmpty(groupId))
            groupIdHashMap.put(emChatId, groupId);

        return groupId;
    }

    public void updateGroupData(String emChatId, GroupDetailInfo groupDetailInfo) {
        groupDetailWeakHashMap.put(emChatId, groupDetailInfo);
    }

    public void saveGroupIdToLocal(String emChatId, String groupId) {
        PreferenceManager.getInstance().setParam(SP.SP_GROUP_ID + emChatId, groupId);
        groupIdHashMap.put(emChatId, groupId);
    }

    public void saveGroupDetailToLocal(String emChatId, GroupDetailInfo groupDetailInfo, @Nullable String json) {
        groupDetailWeakHashMap.put(emChatId, groupDetailInfo);
        PreferenceManager.getInstance().setParam(SP.SP_GROUP_DATA + emChatId, json);
    }

    public GroupDetailInfo getGroupData(String emChatId) {
        if (groupDetailWeakHashMap.containsKey(emChatId)) {
            return groupDetailWeakHashMap.get(emChatId);
        } else {
            String groupDetailJson = (String) PreferenceManager.getInstance().getParam(SP.SP_GROUP_DATA + emChatId, "");
            if (!TextUtils.isEmpty(groupDetailJson)) {
                GroupDetailInfo groupDetailInfo = FastJsonUtil.getObject(groupDetailJson,
                        GroupDetailInfo.class);
                groupDetailWeakHashMap.put(emChatId, groupDetailInfo);
                return groupDetailInfo;
            } else {
                return null;
            }
        }
    }

    public void saveGroupMemberList(String emChatId,GroupDetailInfo groupDetailInfo, @Nullable String json){
        PreferenceManager.getInstance().setParam(SP.SP_GROUP_MEMBER_DATA + emChatId, json);
    }

    public GroupDetailInfo getGroupMemberList(String emChatId) {
        String groupDetailJson = (String) PreferenceManager.getInstance().getParam(SP.SP_GROUP_MEMBER_DATA + emChatId, "");
        if (!TextUtils.isEmpty(groupDetailJson)) {
            GroupDetailInfo groupDetailInfo = FastJsonUtil.getObject(groupDetailJson,
                    GroupDetailInfo.class);
            return groupDetailInfo;
        } else {
            return null;
        }
    }
}
