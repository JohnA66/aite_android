package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.ShareMyOrOtherIdAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.AddContactInfo;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.GroupInfo;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.model.ContactListInfo;
import com.haoniu.quchat.operate.GroupOperateManager;
import com.haoniu.quchat.operate.UserOperateManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lhb
 * 分享自己或者自己其他好友的名片给他人
 */
public class ShareMyOrOtherActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.query)
    EditText mQuery;
    @BindView(R.id.search_clear)
    ImageButton mSearchClear;
    @BindView(R.id.rv_contact)
    RecyclerView mRvContact;

    public Bundle mBundle;

    private List<AddContactInfo> mInfoList = new ArrayList<>();
    private ShareMyOrOtherIdAdapter mMyOrOtherIdAdapter;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_share_my_other_card);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("联系人");

        HashMap<String, GroupInfo> groupsHashMap  = GroupOperateManager.getInstance().getGroups();
        for (String key : groupsHashMap.keySet()) {
            GroupInfo groupInfo = groupsHashMap.get(key);
            AddContactInfo info = new AddContactInfo();
            info.setUserId(groupInfo.getGroupId());
            info.setNickName(groupInfo.getGroupName());
            info.setUserImg(groupInfo.getGroupHead());
            //群组
            info.setType("0");
            mInfoList.add(info);
        }

        for (ContactListInfo.DataBean dataBean : UserOperateManager.getInstance().getContactList()) {
            AddContactInfo info = new AddContactInfo();
            info.setUserId(dataBean.getFriendUserId());
            info.setNickName(dataBean.getFriendNickName());
            info.setUserImg(dataBean.getFriendUserHead());
            //联系人
            info.setType("1");
            mInfoList.add(info);

        }

        mMyOrOtherIdAdapter = new ShareMyOrOtherIdAdapter(mInfoList);
        RclViewHelp.initRcLmVertical(this, mRvContact, mMyOrOtherIdAdapter);


        mQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMyOrOtherIdAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mSearchClear.setVisibility(View.VISIBLE);
                } else {
                    mSearchClear.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        mBundle = extras;
    }


    @OnClick({R.id.query, R.id.search_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.query:
                break;
            case R.id.search_clear:
                mQuery.getText().clear();
                break;
            default:
                break;
        }
    }

    public Bundle getBundle() {
        return mBundle;
    }
}
