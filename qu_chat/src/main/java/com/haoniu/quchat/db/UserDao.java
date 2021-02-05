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
package com.haoniu.quchat.db;

import android.content.Context;

import com.haoniu.quchat.domain.EaseGroupInfo;
import com.haoniu.quchat.domain.EaseUser;
import com.haoniu.quchat.domain.RobotUser;
import com.haoniu.quchat.entity.ApplyStateInfo;
import com.haoniu.quchat.entity.GroupDetailInfo;
import com.haoniu.quchat.entity.MsgStateInfo;

import java.util.List;
import java.util.Map;

public class UserDao {
    public static final String TABLE_NAME = "uers";
    public static final String COLUMN_NAME_ID = "username";
    public static final String COLUMN_NAME_NICK = "nick";
    public static final String COLUMN_NAME_AVATAR = "avatar";
    public static final String COLUMN_NAME_TYPE = "type";
    public static final String COLUMN_NAME_FRIEND_NICK = "friend_nick";
    public static final String COLUMN_NAME_GROUP_TYPE = "groupType";
    public static final String TABLE_NAME_MSG = "msg_read_type";
    public static final String MSG_ISREAD = "msg_is_read";
    public static final String MSG_TYPE = "msg_type";
    public static final String MSG_ID = "msg_id";

    public static final String TABLE_NAME_APPLY = "apply_read_type";
    public static final String APPLY_ISREAD = "apply_is_read";
    public static final String APPLY_TYPE = "apply_type";
    public static final String APPLY_ID = "apply_id";

    public static final String ALL_USERS_TABLE_NAME = "alluers";
    public static final String GROUPS_TABLE_NAME = "groups";

    public static final String PREF_TABLE_NAME = "pref";
    public static final String COLUMN_NAME_DISABLED_GROUPS = "disabled_groups";
    public static final String COLUMN_NAME_DISABLED_IDS = "disabled_ids";

    public static final String ROBOT_TABLE_NAME = "robots";
    public static final String ROBOT_COLUMN_NAME_ID = "username";
    public static final String ROBOT_COLUMN_NAME_NICK = "nick";
    public static final String ROBOT_COLUMN_NAME_AVATAR = "avatar";


    /**
     * TABLE_GROUP_USER_LIST 群成员列表
     * COLUMN_USER_RANK 用户等级 0-普通用户 1-管理员 2-群主
     * COLUMN_USER_ID 用户id
     * COLUMN_SAY_FLAG 禁言状态
     */

    public static final String TABLE_GROUP_USER_LIST = "group_user_list";
    public static final String COLUMN_USER_RANK = "user_rank";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_SAY_FLAG = "say_flag";


    /**
     * 会话结合表
     * COLUMN_CONVERSION_ID 会话id
     * COLUMN_SINGLE_CHAT_PATH 聊天背景
     * COLUMN_CONVERSION_MSY_IS_FREE 会话是否免打扰
     * COLUMN_CONVERSION_IS_TOP 会话是否置顶
     */
    public static final String TABLE_CONVERSION_LIST_BG = "conversion_list_bg";
    public static final String COLUMN_CONVERSION_ID = "conversion_id";
    public static final String COLUMN_SINGLE_CHAT_PATH = "single_chat_path";
    public static final String COLUMN_CONVERSION_MSY_IS_FREE = "conversion_msg_is_free";
    public static final String COLUMN_CONVERSION_IS_TOP = "conversion_is_top";





    /**
     * 群中成员的群昵称
     * GROUP_ID 群ID
     */
    public static final String TABLE_GROUP_USER_NICK = "group_user_nickname";
    public static final String GROUP_ID = "group_id";



    public UserDao(Context context) {

    }

    /**
     * save contact list
     *
     * @param contactList
     */
    public void saveContactList(List<EaseUser> contactList) {
        DemoDBManager.getInstance().saveContactList(contactList);
    }

    /**
     * save user list
     *
     * @param contactList
     */
    public void saveUserList(List<EaseUser> contactList) {
        DemoDBManager.getInstance().saveUserList(contactList);
    }

    /**
     * get contact list
     *
     * @return
     */
    public Map<String, EaseUser> getContactList() {

        return DemoDBManager.getInstance().getContactList();
    }

    /**
     * get List groups
     *
     * @return
     */
    public Map<String, EaseGroupInfo> getGroupList() {
        return DemoDBManager.getInstance().getGroupList();
    }

    /**
     * get group
     *
     * @return
     */
    public EaseGroupInfo getGroup(String id) {
        return DemoDBManager.getInstance().getGroupById(id);
    }

    /**
     * get MsgStateInfo
     *
     * @return
     */
    public MsgStateInfo getMsgStateById(String id) {
        return DemoDBManager.getInstance().getMsgStateById(id);
    }

    /**
     * save MsgStateInfo
     *
     * @return
     */
    public void saveMsgStateInfo(MsgStateInfo msgStateInfo) {
        DemoDBManager.getInstance().saveMsgState(msgStateInfo);
    }

    /**
     * get MsgStateInfo
     *
     * @return
     */
    public ApplyStateInfo getApplyStateById(String id) {
        return DemoDBManager.getInstance().getApplyStateById(id);
    }

    /**
     * save MsgStateInfo
     *
     * @return
     */
    public void saveApplyStateInfo(ApplyStateInfo applyStateInfo) {
        DemoDBManager.getInstance().saveApplyState(applyStateInfo);
    }

    /**
     * get user
     *
     * @return
     */
    public EaseUser getUser(String id) {
        return DemoDBManager.getInstance().getUserById(id);
    }


    /**
     * get contact
     *
     * @return
     */
    public EaseUser getContact(String id) {
        return DemoDBManager.getInstance().getContactById(id);
    }

    /**
     * delete a contact
     *
     * @param username
     */
    public void deleteContact(String username) {
        DemoDBManager.getInstance().deleteContact(username);
    }

    /**
     * delete a user
     *
     * @param username
     */
    public void deleteuser(String username) {
        DemoDBManager.getInstance().deleteuser(username);
    }

    /**
     * delete a group
     *
     * @param username
     */
    public void deleteGroup(String username) {
        DemoDBManager.getInstance().deleteGroup(username);
    }

    /**
     * save a contact
     *
     * @param user
     */
    public void saveContact(EaseUser user) {
        DemoDBManager.getInstance().saveContact(user);
    }

    /**
     * save a user
     *
     * @param user
     */
    public void saveuser(EaseUser user) {
        DemoDBManager.getInstance().saveuser(user);
    }

    /**
     * save a group
     *
     * @param user
     */
    public void saveGroup(EaseGroupInfo user) {
        DemoDBManager.getInstance().saveGroup(user);
    }

    public void setDisabledGroups(List<String> groups) {
        DemoDBManager.getInstance().setDisabledGroups(groups);
    }

    public List<String> getDisabledGroups() {
        return DemoDBManager.getInstance().getDisabledGroups();
    }

    public void setDisabledIds(List<String> ids) {
        DemoDBManager.getInstance().setDisabledIds(ids);
    }

    public List<String> getDisabledIds() {
        return DemoDBManager.getInstance().getDisabledIds();
    }

    public Map<String, RobotUser> getRobotUser() {
        return DemoDBManager.getInstance().getRobotList();
    }

    public void saveRobotUser(List<RobotUser> robotList) {
        DemoDBManager.getInstance().saveRobotList(robotList);
    }


    /**
     * 保存群成员
     */
    public void saveGroupUserList(List<GroupDetailInfo.GroupUserDetailVoListBean> groupUserList) {
        DemoDBManager.getInstance().saveGroupUserList(groupUserList);
    }

    /**
     * 获取群成员
     */
    public List<GroupDetailInfo.GroupUserDetailVoListBean> getGroupUserList(String id, String userrank) {
        return DemoDBManager.getInstance().getGroupUserList(id, userrank);
    }


    public void saveChatBg(String conversionId, String chatBg, String conversionMsgIsFree, String isTop) {
        DemoDBManager.getInstance().saveChatBg(conversionId, chatBg, conversionMsgIsFree, isTop);
    }


    public String getChatBg(String conversionId) {
        return DemoDBManager.getInstance().getChatBg(conversionId);
    }


    /**
     * 获取会话是否置顶
     */
    public String getIsTop(String conversionId) {
        return DemoDBManager.getInstance().getIsTop(conversionId);
    }


    /**
     * 获取群消息是否免打扰
     */
    public String getMsgFree(String conversionId) {
        return DemoDBManager.getInstance().getIsMsgFree(conversionId);
    }



    public void saveGroupUserNickName(EaseGroupInfo groupInfo) {
        DemoDBManager.getInstance().saveGroupUserNickName(groupInfo);
    }


    public EaseGroupInfo getGroupUserNeckNameById(String id, String groupId) {
        return DemoDBManager.getInstance().getGroupUserNeckNameById(id,groupId);
    }


}
