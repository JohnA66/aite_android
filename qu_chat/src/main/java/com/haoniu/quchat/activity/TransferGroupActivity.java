package com.haoniu.quchat.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haoniu.quchat.adapter.TransferGroupAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.GroupDetailInfo;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.operate.GroupOperateManager;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.utils.SortUtil;
import com.haoniu.quchat.view.CommonConfirmDialog;
import com.haoniu.quchat.view.SlideRecyclerView;
import com.haoniu.quchat.widget.SearchBar;
import com.zds.base.json.FastJsonUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class TransferGroupActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.searchBar)
    SearchBar searchBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String emChatId = "";
    private String groupId = "";

    private TransferGroupAdapter mAdapter;

    private GroupDetailInfo groupDetailInfo;
    private List<GroupDetailInfo.GroupUserDetailVoListBean> mGroupMemberBeans = new ArrayList<>();
    private List<GroupDetailInfo.GroupUserDetailVoListBean> mGroupUserDetailVoListBeans = new ArrayList<>();

    public static void start(Context context, String emChatId, String groupId) {
        Intent intent = new Intent(context, TransferGroupActivity.class);
        intent.putExtra("key_intent_emChatId", emChatId);
        intent.putExtra("key_intent_groupId", groupId);
        context.startActivity(intent);
    }

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_transfer_group);
    }

    @Override
    protected void initLogic() {
        getIntentData();
        setTitle("转让该群");
        initRecyclerView();

        searchBar.setOnSearchBarListener(new SearchBar.OnSearchBarListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mGroupMemberBeans != null && mGroupMemberBeans.size() > 0) {
                    String inputStr = s.toString();
                    if (TextUtils.isEmpty(inputStr)) {
                        mAdapter.setNewData(mGroupMemberBeans);
                        return;
                    }

                    mGroupUserDetailVoListBeans.clear();
                    for (int i = 0; i < mGroupMemberBeans.size(); i++) {
                        GroupDetailInfo.GroupUserDetailVoListBean groupUserDetailVoListBean = mGroupMemberBeans.get(i);
                        if (groupUserDetailVoListBean.getUserNickName().contains(inputStr)) {
                            mGroupUserDetailVoListBeans.add(groupUserDetailVoListBean);
                        }
                    }
                    mAdapter.setNewData(mGroupUserDetailVoListBeans);
                }
            }
        });

        loadGroupDataFromLocal();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        emChatId = intent.getStringExtra("key_intent_emChatId");
        groupId = intent.getStringExtra("key_intent_groupId");
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TransferGroupAdapter();
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GroupDetailInfo.GroupUserDetailVoListBean groupUsertBean = mAdapter.getData().get(position);
                showCommonConfirmDialog(groupUsertBean);
            }
        });
    }

    public void loadGroupDataFromLocal() {
        groupDetailInfo = GroupOperateManager.getInstance().getGroupMemberList(emChatId);
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", groupId);
        if (groupDetailInfo != null)
            setGroupMemberData();

        ApiClient.requestNetHandle(this, AppConfig.CHECK_GROUP_DATA_VERSION, "",
                map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
//                        int groupVersion = FastJsonUtil.getInt(json, "groupVersion");
                        //本地数据版本更服务器不一致 就需要更新数据接口
//                        if (groupDetailInfo.getGroupVersion() != groupVersion) {
                        queryGroupDetail();
//                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        queryGroupDetail();
                    }
                });

    }

    public void queryGroupDetail() {
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", groupId);
        ApiClient.requestNetHandle(this, AppConfig.GET_GROUP_DETAIL, "请稍等...",
                map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        if (json != null && json.length() > 0) {
                            groupDetailInfo = FastJsonUtil.getObject(json, GroupDetailInfo.class);
                            setGroupMemberData();
                            GroupOperateManager.getInstance().saveGroupMemberList(emChatId, groupDetailInfo, json);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                });
    }

    public void setGroupMemberData() {
        if (null != groupDetailInfo && groupDetailInfo.getGroupUserDetailVoList().size() > 0) {
            mGroupMemberBeans.clear();
            List<GroupDetailInfo.GroupUserDetailVoListBean> groupUserDetailVoList = groupDetailInfo.getGroupUserDetailVoList();
            for (int i = 0; i < groupUserDetailVoList.size(); i++) {
                GroupDetailInfo.GroupUserDetailVoListBean groupUserDetailVoListBean = groupUserDetailVoList.get(i);
                if (!groupUserDetailVoListBean.getUserRank().equals("2")) {
                    mGroupMemberBeans.add(groupUserDetailVoListBean);
                }
            }

            mAdapter.setNewData(mGroupMemberBeans);
        }

    }

    private CommonConfirmDialog mCommonConfirmDialog;

    private void showCommonConfirmDialog(GroupDetailInfo.GroupUserDetailVoListBean groupUsertBean) {
        if (mCommonConfirmDialog == null) {
            mCommonConfirmDialog = new CommonConfirmDialog(this);
            mCommonConfirmDialog.setOnConfirmClickListener(new CommonConfirmDialog.OnConfirmClickListener() {
                @Override
                public void onConfirmClick(View view) {
                    transferGroup(groupUsertBean);
                }
            });
        }
        mCommonConfirmDialog.show();
        mCommonConfirmDialog.setTitle("转让");
        mCommonConfirmDialog.setContent("转让给" + groupUsertBean.getUserNickName() + "后，你将失去群主身份");
    }

    private void transferGroup(GroupDetailInfo.GroupUserDetailVoListBean groupUsertBean) {
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", groupId);
        map.put("userId", groupUsertBean.getUserId());

        ApiClient.requestNetHandle(this, AppConfig.TRANSFER_GROUP, "正在转让群...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                toast("转让成功");
                //通知群详情页面刷新数据
                EventBus.getDefault().post(new EventCenter<>(EventUtil.TRANSFER_GROUP));
                finish();
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}
