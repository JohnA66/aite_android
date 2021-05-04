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
import com.haoniu.quchat.adapter.NewFriendAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.db.InviteMessgeDao;
import com.haoniu.quchat.entity.ApplyFriendData;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.NewFriendInfo;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.zds.base.json.FastJsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lhb
 * 新的朋友
 */
public class NewFriendActivity extends BaseActivity implements NewFriendAdapter.OnAgreeListener {
    @BindView(R.id.rv_new_friend)
    RecyclerView mRvNewFriend;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.query)
    EditText mQuery;
    @BindView(R.id.search_clear)
    ImageButton mSearchClear;
    private List<ApplyFriendData> mStringList = new ArrayList<>();
    private NewFriendAdapter mNewFriendAdapter;
    private HashMap<String, Integer> lettes;

    private int page = 1;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_new_friend);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("新的朋友");

        InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(this);
        inviteMessgeDao.saveUnreadMessageCount(0);
        mQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNewFriendAdapter.getFilter().filter(s);
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

        lettes = new HashMap<>();

        mNewFriendAdapter = new NewFriendAdapter(mStringList, this, lettes);
        RclViewHelp.initRcLmVertical(this, mRvNewFriend, mNewFriendAdapter);
        mNewFriendAdapter.setOnAgreeListener(this);
        queryUserStatus();
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }


    /**
     * 查询用户申请列表
     */
    private void queryUserStatus() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", page);
        map.put("pageSize", 20);
        ApiClient.requestNetHandle(this, AppConfig.APPLY_ADD_USER_LIST, "", map, new ResultListener() {
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

        ApiClient.requestNetHandle(this, AppConfig.APPLY_ADD_USER_STATUS, type == 1 ? "正在同意..." : "正在拒绝...", map, new ResultListener() {
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


    @OnClick({R.id.search_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_clear:
                //搜索框删除按钮
                mQuery.getText().clear();
                hideSoftKeyboard();
                break;
            default:
                break;
        }
    }
}
