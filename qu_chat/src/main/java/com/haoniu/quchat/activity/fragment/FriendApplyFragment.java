package com.haoniu.quchat.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.NewFriendAdapter;
import com.haoniu.quchat.base.MyBaseFragment;
import com.haoniu.quchat.db.InviteMessgeDao;
import com.haoniu.quchat.entity.ApplyFriendData;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.NewFriendInfo;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.view.SlideRecyclerView;
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
 * 好友申请
 */
public class FriendApplyFragment extends MyBaseFragment implements NewFriendAdapter.OnAgreeListener {
    @BindView(R.id.rv_new_friend)
    SlideRecyclerView mRvNewFriend;
    @BindView(R.id.search_bar)
    SearchBar searchBar;
    private List<ApplyFriendData> mStringList = new ArrayList<>();
    private NewFriendAdapter mNewFriendAdapter;
    private HashMap<String, Integer> lettes;

    private int page = 1;

    @Override
    protected int setContentView() {
        return R.layout.activity_new_friend;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        searchBar.setOnSearchBarListener(new SearchBar.OnSearchBarListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNewFriendAdapter.getFilter().filter(s);
            }
        });

        lettes = new HashMap<>();

        mNewFriendAdapter = new NewFriendAdapter(mStringList, mContext, lettes);
        RclViewHelp.initRcLmVertical(mContext, mRvNewFriend, mNewFriendAdapter);
        mNewFriendAdapter.setOnAgreeListener(this);
        queryUserStatus();
        mNewFriendAdapter.setOnDelClickListener(new NewFriendAdapter.OnDelClickListener() {
            @Override
            public void delUser(int pos) {
                delApplyUserData(pos);
            }
        });
    }
    /**
     * 查询用户申请列表
     */
    private void delApplyUserData(int position) {
        Map<String, Object> map = new HashMap<>();
        map.put("applyId", mStringList.get(position).getApplyId());
        ApiClient.requestNetHandle(mContext, AppConfig.DEL_APPLY_ADD_USER, "", map, null);
        mStringList.remove(position);
        mNewFriendAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }


    /**
     * 查询用户申请列表
     */
    private void queryUserStatus() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", page);
        map.put("pageSize", 20);
        ApiClient.requestNetHandle(mContext, AppConfig.APPLY_ADD_USER_LIST, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (null != json && json.length() > 0) {
                    if (page == 1 && mStringList.size() > 0) {
                        mStringList.clear();
                    }
                    NewFriendInfo info = FastJsonUtil.getObject(json, NewFriendInfo.class);

                    mStringList.addAll(info.getData());
                    mNewFriendAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

    /**
     * 同意拒绝好友申请
     */
    private void agreeApply(String applyId, int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("applyId", applyId);
        map.put("applyStatus", type);

        ApiClient.requestNetHandle(mContext, AppConfig.APPLY_ADD_USER_STATUS, type == 1 ? "正在同意..." : "正在拒绝...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                page = 1;
                queryUserStatus();

                if (type == 1) {
                    toast("已同意");
                } else {
                    toast("已拒绝");
                }

            }

            @Override
            public void onFailure(String msg) {
                toast(msg);

            }
        });

    }


    /**
     * 好友申请同意
     */
    @Override
    public void agree(String applyId, int type) {
        agreeApply(applyId, type);
    }


    public boolean isDisGoodFriend() {
        int q = 0;
        for (int i = 0; i < mStringList.size(); i++) {
            if (!"0".equals(mStringList.get(i).getApplyStatus())) {
                q += 1;
            }
        }
        return q == mStringList.size();
    }

}
