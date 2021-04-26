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
package com.haoniu.quchat.activity.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.aite.chat.R;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.MyHelper;
import com.haoniu.quchat.db.UserDao;
import com.haoniu.quchat.domain.EaseUser;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.model.ContactListInfo;
import com.haoniu.quchat.utils.EaseCommonUtils;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.operate.UserOperateManager;
import com.haoniu.quchat.widget.EaseContactList;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.zds.base.json.FastJsonUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * contact list
 */
public class EaseContactListFragment extends EaseBaseFragment {
    private static final String TAG = "EaseContactListFragment";
    protected List<EaseUser> contactList;
    protected ListView listView;
    protected boolean hidden;
    protected EditText query;
    protected Handler handler = new Handler();
    protected EaseUser toBeProcessUser;
    protected String toBeProcessUsername;
    protected EaseContactList contactListLayout;
    protected boolean isConflict;
    protected FrameLayout contentContainer;
    Unbinder unbinder;
    @BindView(R.id.img_show)
    ImageView mImgShow;
    List<ContactListInfo.DataBean> mContactList;



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //to avoid crash when open app after long time stay in background
        // after user logged into another device
        if (savedInstanceState != null && savedInstanceState.getBoolean(
                "isConflict", false)) {
            return;
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initLogic() {
        contentContainer =
                (FrameLayout) getView().findViewById(R.id.content_container);
        contactListLayout =
                (EaseContactList) getView().findViewById(R.id.contact_list);
        listView = contactListLayout.getListView();
        //search
        query = (EditText) getView().findViewById(R.id.query);


        EMClient.getInstance().addConnectionListener(connectionListener);

        contactList = new ArrayList<EaseUser>();
        checkSeviceContactData();
        //init list
        contactListLayout.init(contactList);

        if (listItemClickListener != null) {
            listView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    EaseUser user =
                            (EaseUser) listView.getItemAtPosition(position);
                    listItemClickListener.onListItemClicked(user);
                }
            });
        }


        listView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
                return false;
            }
        });
    }

    /**
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
    @Override
    protected void onEventComing(EventCenter center) {
        //刷新通讯录和本地数据库
        if (center.getEventCode() == EventUtil.REFRESH_CONTACT) {
            getContactList();
        }

    }

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    @Override
    protected void getBundleExtras(Bundle extras) {

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden) {
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
            refresh();
        }
    }


    /**
     * refresh ui
     */
    public void refresh() {
        checkSeviceContactData();
        contactListLayout.refresh();
    }


    @Override
    public void onDestroy() {
        EMClient.getInstance().removeConnectionListener(connectionListener);
        super.onDestroy();
    }

    public void checkSeviceContactData(){

        mContactList = UserOperateManager.getInstance().getContactList();

        if (mContactList == null) {
            //本地不存在数据直接请求接口
            getContactList();
        } else {
            setContactData(mContactList);
            ApiClient.requestNetHandle(getContext(), AppConfig.CHECK_FRIEND_DATA_VERSION, "",
                    null, new ResultListener() {
                        @Override
                        public void onSuccess(String json, String msg) {
                            int cacheVersion = FastJsonUtil.getInt(json, "cacheVersion");
                            //本地数据版本更服务器不一致 就需要更新数据接口
//                            if (cacheVersion != UserOperateManager.getInstance().getContactVersion()) {
                                getContactList();
//                            }
                        }

                        @Override
                        public void onFailure(String msg) {
                            getContactList();
                        }
                    });
        }


    }

    /**
     * query contact
     *
     * @param
     */
    public void getContactList() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("pageNum", 1);
        map.put("pageSize", 10000);

        //未同步通讯录到本地
        ApiClient.requestNetHandle(getActivity(), AppConfig.USER_FRIEND_LIST,
                "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ContactListInfo info = FastJsonUtil.getObject(json,
                        ContactListInfo.class);
                List<ContactListInfo.DataBean> mContactList = new ArrayList<>();
                mContactList.addAll(info.getData());


                if (mContactList.size() > 0) {
                    UserOperateManager.getInstance().saveContactListToLocal(info,json);
                    setContactData(mContactList);
                } else {
                    contactList.clear();
                    contactListLayout.refresh();
                }
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });


        //// sorting

    }


    protected void setContactData(List<ContactListInfo.DataBean> mContactList) {
        contactList.clear();
        //todo 需要优化
        Map<String, EaseUser> userlist = new HashMap<String,
                EaseUser>(5);
        for (ContactListInfo.DataBean bean : mContactList) {
            EaseUser user =
                    new EaseUser(bean.getFriendUserId() + Constant.ID_REDPROJECT);
            user.setNickname(bean.getFriendNickName());
            user.setAvatar(bean.getFriendUserHead());
            EaseCommonUtils.setUserInitialLetter(user);
            if (bean.getBlackStatus().equals("0")) {
                contactList.add(user);
            }
            EaseUser userLocal =
                    new EaseUser(bean.getFriendUserId() + Constant.ID_REDPROJECT);
            userLocal.setAvatar(bean.getFriendUserHead());
            userLocal.setNickname(bean.getFriendNickName());

            EaseCommonUtils.setUserInitialLetter(userLocal);
            userlist.put(bean.getFriendUserId() + Constant.ID_REDPROJECT, userLocal);

        }


        Collections.sort(contactList, new Comparator<EaseUser>() {
            @Override
            public int compare(EaseUser lhs, EaseUser rhs) {
                if (lhs.getInitialLetter().equals(rhs.getInitialLetter())) {
                    return lhs.getNickname().compareTo(rhs.getNickname());
                } else {
                    if ("#".equals(lhs.getInitialLetter())) {
                        return 1;
                    } else if ("#".equals(rhs.getInitialLetter())) {
                        return -1;
                    }
                    return lhs.getInitialLetter().compareTo(rhs
                            .getInitialLetter());
                }
            }
        });

        contactListLayout.refresh();
    }
    protected EMConnectionListener connectionListener =
            new EMConnectionListener() {

        @Override
        public void onDisconnected(int error) {
            if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE || error == EMError.SERVER_SERVICE_RESTRICTED) {
                isConflict = true;
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onConnectionDisconnected();
                    }

                });
            }
        }

        @Override
        public void onConnected() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onConnectionConnected();
                }

            });
        }
    };
    private EaseContactListItemClickListener listItemClickListener;


    protected void onConnectionDisconnected() {}

    protected void onConnectionConnected() {}

//    /**
//     * set contacts map, key is the hyphenate id
//     *
//     * @param contactsMap
//     */
//    public void setContactsMap(Map<String, EaseUser> contactsMap) {
//        this.contactsMap = contactsMap;
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface EaseContactListItemClickListener {
        /**
         * on click event for item in contact list
         *
         * @param user --the user of item
         */
        void onListItemClicked(EaseUser user);

    }

    /**
     * set contact list item click listener
     *
     * @param listItemClickListener
     */
    public void setContactListItemClickListener(EaseContactListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

}
