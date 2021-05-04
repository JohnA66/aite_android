package com.haoniu.quchat.model;

import android.text.TextUtils;

import com.aite.chat.R;
import com.haoniu.quchat.base.EaseConstant;
import com.haoniu.quchat.base.EaseUI;
import com.haoniu.quchat.domain.EaseUser;
import com.haoniu.quchat.utils.EaseUserUtils;
import com.haoniu.quchat.utils.PreferenceManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.zds.base.json.FastJsonUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class EaseAtMessageHelper {
    private List<String> toAtUserList = new ArrayList<String>();
    private List<String> atMeGroupList = null;
    private static EaseAtMessageHelper instance = null;
    public synchronized static EaseAtMessageHelper get(){
        if(instance == null){
            instance = new EaseAtMessageHelper();
        }
        return instance;
    }
    
    
    private EaseAtMessageHelper(){
        atMeGroupList = FastJsonUtil.getList(PreferenceManager.getInstance().getAtMeGroups(),String.class);
        if(atMeGroupList == null) {
            atMeGroupList = new ArrayList<>();
        }
        
    }
    
    /**
     * add user you want to @
     * @param userId
     */
    public void addAtUser(String userId){
        synchronized (toAtUserList) {
            if(!toAtUserList.contains(userId)){
                toAtUserList.add(userId);
            }
        }
        
    }
    
    /**
     * check if be mentioned(@) in the content  (检查是否包含@)
     * @param content
     * @return
     */
    public boolean containsAtUsername(String content){
        if(TextUtils.isEmpty(content)){
            return false;
        }
        synchronized (toAtUserList) {
            for(String userId : toAtUserList){
                String nick = userId;
                if(EaseUserUtils.getUserInfo(userId) != null){
                    EaseUser user = EaseUserUtils.getUserInfo(userId);
                    if (user != null) {
                        nick = user.getNickname();
                    }
                }
                if(content.contains(nick)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsAtAll(String content){
        String atAll = "@" + EaseUI.getInstance().getContext().getString(R.string.all_members);
        if(content.contains(atAll)){
            return true;
        }
        return false;
    }
    
    /**
     * get the users be mentioned(@) 
     * @param content
     * @return
     */
    public List<String> getAtMessageUsernames(String content){
        if(TextUtils.isEmpty(content)){
            return null;
        }
        synchronized (toAtUserList) {
            List<String> list = null;
            for(String username : toAtUserList){
                String nick = username;
                if(EaseUserUtils.getUserInfo(username) != null){
                    EaseUser user = EaseUserUtils.getUserInfo(username);
                    if (user != null) {
                        nick = user.getNickname();
                    }
                }
                if(content.contains(nick)){
                    if(list == null){
                        list = new ArrayList<String>();
                    }
                    list.add(username);
                }
            }
            return list;
        }
    }
    
    /**
     * parse the message, get and save group id if I was mentioned(@)
     * @param messages
     */
    public void parseMessages(List<EMMessage> messages) {

        for(EMMessage msg : messages){
            if(msg.getChatType() == ChatType.GroupChat){
                String groupId = msg.getTo();
                try {
                    JSONArray jsonArray = msg.getJSONArrayAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG);
                    for(int i = 0; i < jsonArray.length(); i++){
                        String userId = jsonArray.getString(i);
                        if(EMClient.getInstance().getCurrentUser().contains(userId)){
                            if(!atMeGroupList.contains(groupId)){
                                atMeGroupList.add(groupId);
                                break;
                            }
                        }
                    }
                } catch (Exception e1) {
                    //Determine whether is @ all message
                    String usernameStr = msg.getStringAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG, null);
                    if(usernameStr != null){
                        if(usernameStr.toUpperCase().equals(EaseConstant.MESSAGE_ATTR_VALUE_AT_MSG_ALL)){
                            if(!atMeGroupList.contains(groupId)){
                                atMeGroupList.add(groupId);
                            }
                        }
                    }
                }
                String groupsStr = FastJsonUtil.toJSONString(atMeGroupList);

                PreferenceManager.getInstance().setAtMeGroups(groupsStr);
            }
        }
    }
    
    /**
     * get groups which I was mentioned
     * @return
     */
    public List<String> getAtMeGroups(){
        return atMeGroupList;
    }
    
    /**
     * remove group from the list
     * @param groupId
     */
    public void removeAtMeGroup(String groupId){
        if(atMeGroupList.contains(groupId)){
            atMeGroupList.remove(groupId);
            PreferenceManager.getInstance().setAtMeGroups(FastJsonUtil.toJSONString(atMeGroupList));
        }
    }
    
    /**
     * check if the input groupId in atMeGroupList
     * @param groupId
     * @return
     */
    public boolean hasAtMeMsg(String groupId){
        return atMeGroupList.contains(groupId);
    }
    
    public boolean isAtMeMsg(EMMessage message){
        EaseUser user = EaseUserUtils.getUserInfo(message.getFrom());
        if(user != null){
            try {
                JSONArray jsonArray = message.getJSONArrayAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG);
                
                for(int i = 0; i < jsonArray.length(); i++){
                    String username = jsonArray.getString(i);
                    if(username.equals(EMClient.getInstance().getCurrentUser())){
                        return true;
                    }
                }
            } catch (Exception e) {
                //perhaps is a @ all message
                String atUsername = message.getStringAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG, null);
                if(atUsername != null){
                    if(atUsername.toUpperCase().equals(EaseConstant.MESSAGE_ATTR_VALUE_AT_MSG_ALL)){
                        return true;
                    }
                }
                return  false;
            }
            
        }
        return false;
    }
    
    public JSONArray atListToJsonArray(List<String> atList){
        JSONArray jArray = new JSONArray();
        int size = atList.size();
        for(int i = 0; i < size; i++){
            String username = atList.get(i);
            jArray.put(username);
        }
        return jArray;
    }

    public List getAtList() {
        return toAtUserList;
    }

    public void cleanToAtUserList(){
        synchronized (toAtUserList){
            toAtUserList.clear();
        }
    }
}
