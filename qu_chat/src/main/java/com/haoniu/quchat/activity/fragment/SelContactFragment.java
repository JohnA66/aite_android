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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.aite.chat.R;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.domain.EaseUser;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.model.ContactListInfo;
import com.haoniu.quchat.utils.EaseCommonUtils;
import com.haoniu.quchat.widget.EaseContactList;
import com.zds.base.json.FastJsonUtil;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * contact list
 * @author lhb
 */
public class SelContactFragment extends EaseBaseFragment {
    private static final String TAG = "EaseContactListFragment";
    protected List<EaseUser> contactList;
    protected ListView listView;
    protected boolean hidden;
    protected ImageButton clearSearch;
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
    @BindView(R.id.img_back)
    ImageView mImgBack;
    private String userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sel_contact_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //to avoid crash when open app after long time stay in background after user logged into another device
        if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false)) {
            return;
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initLogic() {
        mImgShow.setVisibility(View.GONE);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        contentContainer = (FrameLayout) getView().findViewById(R.id.content_container);
        contactListLayout = (EaseContactList) getView().findViewById(R.id.contact_list);
        listView = contactListLayout.getListView();
        //search
        query = (EditText) getView().findViewById(R.id.query);
        clearSearch = (ImageButton) getView().findViewById(R.id.search_clear);


        contactList = new ArrayList<EaseUser>();
        getContactList();
        //init list
        contactListLayout.init(contactList);

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EaseUser user = (EaseUser) listView.getItemAtPosition(position);
                //发送名片
                Intent intent = new Intent();
                intent.putExtra("username", user.getFriendUserCode());
                intent.putExtra("nickname", user.getNickName());
                intent.putExtra("avatar", user.getAvatar());
                intent.putExtra("friendUserId", user.getFriendUserId());
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }
        });



        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactListLayout.filter(s);
                if (s.length() > 0) {
                    clearSearch.setVisibility(View.VISIBLE);
                } else {
                    clearSearch.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        clearSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                query.getText().clear();
                hideSoftKeyboard();
            }
        });

        listView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
                return false;
            }
        });
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }


    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    @Override
    protected void getBundleExtras(Bundle extras) {
        userId = extras.getString("userId");
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
        ApiClient.requestNetHandle(getActivity(), AppConfig.USER_FRIEND_LIST, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ContactListInfo info = FastJsonUtil.getObject(json, ContactListInfo.class);
                List<ContactListInfo.DataBean> mContactList = new ArrayList<>();
                mContactList.addAll(info.getData());


                if (mContactList.size() > 0) {
                    Map<String, EaseUser> userlist = new HashMap<String, EaseUser>();
                    for (ContactListInfo.DataBean bean : mContactList) {
                        EaseUser user = new EaseUser(bean.getFriendUserId() + Constant.ID_REDPROJECT);
                        user.setNickname(bean.getFriendNickName());
                        user.setNickName(bean.getNickName());
                        user.setAvatar(bean.getFriendUserHead());
                        user.setFriendUserCode(bean.getFriendUserCode());
                        user.setFriendUserId(bean.getFriendUserId());
                        EaseCommonUtils.setUserInitialLetter(user);
                        contactList.add(user);

                        EaseUser userLocal = new EaseUser(bean.getFriendUserId() + Constant.ID_REDPROJECT);
                        userLocal.setAvatar(bean.getFriendUserHead());
                        userLocal.setNickname(bean.getFriendNickName());

                        EaseCommonUtils.setUserInitialLetter(userLocal);

                        //发送名片， 过滤掉将要接收名片的好友
                        if (!userId.equals(bean.getFriendUserId()+Constant.ID_REDPROJECT)) {
                            userlist.put(bean.getFriendUserId() + Constant.ID_REDPROJECT, userLocal);
                        }

                    }

                    Comparator comparator= Collator.getInstance(java.util.Locale.CHINA);


                    Collections.sort(contactList, new Comparator<EaseUser>() {
                        @Override
                        public int compare(EaseUser o1, EaseUser o2) {
                            return comparator.compare(o1.getNickname(), o2.getNickname());
                        }
                    });

                    contactListLayout.refresh();
                }
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });


        //// sorting
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
                    return lhs.getInitialLetter().compareTo(rhs.getInitialLetter());
                }

            }
        });

    }

}
