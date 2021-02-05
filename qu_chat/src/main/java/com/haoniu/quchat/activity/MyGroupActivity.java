package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.MyGroupAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.MyHelper;
import com.haoniu.quchat.domain.EaseGroupInfo;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.GroupInfo;
import com.haoniu.quchat.entity.MyGroupInfoList;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.widget.SearchBar;
import com.zds.base.json.FastJsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lhb
 * 我的群组
 */
public class MyGroupActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rv_group)
    RecyclerView mRvGroup;
    @BindView(R.id.search_bar)
    SearchBar searchBar;

    private List<GroupInfo> mGroupInfoList;
    private MyGroupAdapter mMyGroupAdapter;
    private int page = 1;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_my_group);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("我的群组");
        searchBar.setOnSearchBarListener(new SearchBar.OnSearchBarListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMyGroupAdapter.getFilter().filter(s);
            }
        });


        mGroupInfoList = new ArrayList<>();
        mMyGroupAdapter = new MyGroupAdapter(mGroupInfoList);
        mMyGroupAdapter.setOnLoadMoreListener(() -> {
            page++;
            groupList();
        }, mRvGroup);
        RclViewHelp.initRcLmVertical(this, mRvGroup, mMyGroupAdapter);

        groupList();
    }

    @Override
    protected void onEventComing(EventCenter center) {
        if (center.getEventCode() == EventUtil.CREATE_GROUP_SUCCESS || center.getEventCode() == EventUtil.DEL_EXIT_GROUP) {
            page = 1;
            groupList();
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    /**
     * 我的群组列表
     *
     * @param
     */
    private void groupList() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("pageSize", 100);
        map.put("pageNum", page);

        ApiClient.requestNetHandle(this, AppConfig.MY_GROUP_LIST, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                MyGroupInfoList myGroupInfo = FastJsonUtil.getObject(json, MyGroupInfoList.class);
                if (myGroupInfo.getData() != null && myGroupInfo.getData().size() > 0) {
                    if (page == 1 && mGroupInfoList.size() > 0) {
                        mGroupInfoList.clear();
                    }
                    mGroupInfoList.addAll(myGroupInfo.getData());
                    mMyGroupAdapter.notifyDataSetChanged();
                    mMyGroupAdapter.loadMoreComplete();
                } else {
                    mMyGroupAdapter.loadMoreEnd(true);
                }
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
                mMyGroupAdapter.loadMoreFail();
            }
        });
    }
}
