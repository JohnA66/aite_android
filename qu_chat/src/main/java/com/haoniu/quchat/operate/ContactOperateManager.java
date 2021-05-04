package com.haoniu.quchat.operate;

import android.text.TextUtils;

import com.haoniu.quchat.constant.SP;
import com.haoniu.quchat.model.ContactListInfo;
import com.haoniu.quchat.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class ContactOperateManager {

    private static ContactOperateManager instance;

    private int contactVersion = 0 ;

    private List<ContactListInfo.DataBean> contactList;

    //构造器私有化
    private ContactOperateManager() {
        contactList = new ArrayList<>();
    }

    //方法同步，调用效率低
    public static synchronized ContactOperateManager getInstance() {
        if (instance == null) {
            instance = new ContactOperateManager();
        }
        return instance;
    }

    public int getContactVersion() {
        return contactVersion;
    }

    public void saveContactListToLocal(ContactListInfo info, String json){
        contactList = info.getData();
        contactVersion = info.getCacheVersion();
        PreferenceManager.getInstance().getParam(SP.SP_CONTACT_DATA ,json);
    }

    public List<ContactListInfo.DataBean> getContactList() {
        if (contactList.size() == 0) {
           String contactLocalData = (String) PreferenceManager.getInstance().getParam(SP.SP_CONTACT_DATA ,"");
           if (TextUtils.isEmpty(contactLocalData)){

           }
        }
        return contactList;
    }
}
