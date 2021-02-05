package com.haoniu.quchat.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.SetGroupManageAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.model.GroupManageListInfo;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.widget.EaseAlertDialog;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.json.FastJsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lhb
 * 设置群管理员
 */
public class SetGroupManageActivity extends BaseActivity implements SetGroupManageAdapter.OnCancelGroupUserListener {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rv_group_manage)
    RecyclerView mRvGroupManage;
    private SetGroupManageAdapter mSetGroupManageAdapter;
    private List<GroupManageListInfo> mManageListInfoList;

    private String groupId;
    private String emChatId;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_set_group_manage);
    }

    @Override
    protected void initLogic() {


        mToolbarTitle.setText("设置群管理员");

        mManageListInfoList = new ArrayList<>();

        mSetGroupManageAdapter = new SetGroupManageAdapter(mManageListInfoList);
        mSetGroupManageAdapter.setOnDelGroupUserListener(this);
        RclViewHelp.initRcLmVertical(this, mRvGroupManage, mSetGroupManageAdapter);
        mRvGroupManage.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 1;
                outRect.bottom = 1;
            }
        });
        queryGroupManage();
    }

    @Override
    protected void onEventComing(EventCenter center) {
        if (center.getEventCode() == EventUtil.SET_GROUP_MANAGE) {
            mManageListInfoList.clear();
            queryGroupManage();
        }

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        groupId = extras.getString("groupId");
        emChatId =extras.getString(Constant.PARAM_EM_CHAT_ID,"");
    }


    /**
     * 查询群管理员
     *
     * @param
     */
    private void queryGroupManage() {
        Map<String, Object> map = new HashMap<>(1);
        map.put("groupId", groupId);
        ApiClient.requestNetHandle(this, AppConfig.LIST_GROUP_MANAGE, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (json != null && json.length() > 0) {
                    mManageListInfoList.addAll(FastJsonUtil.getList(json, GroupManageListInfo.class));
                    mSetGroupManageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }


    /**
     * 取消群管理员
     *
     * @param
     */
    private void CancelGroupManage(String userId, int pos) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("groupId", groupId);
        map.put("userId", userId);
        //userRank0-普通用户 1-管理员
        map.put("userRank", "0");

        ApiClient.requestNetHandle(this, AppConfig.MODIFY_GROUP_MANEGER, "正在取消...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                toast("取消成功");
                mManageListInfoList.remove(pos);
                mSetGroupManageAdapter.notifyItemRemoved(pos);
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }


    /**
     * 取消群管理员成功回调
     */
    @Override
    public void delGroup(String userId, int pos) {
        new EaseAlertDialog(this, null, "确定取消该成员的管理员的权限？", null, new EaseAlertDialog.AlertDialogUser() {

            @Override
            public void onResult(boolean confirmed, Bundle bundle) {
                if (confirmed) {
                    CancelGroupManage(userId, pos);
                }
            }
        }, true).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rv_group_manage, R.id.ll_footerView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rv_group_manage:
                break;
            case R.id.ll_footerView:
                Bundle bundle = new Bundle();
                bundle.putString("groupId", groupId);
                bundle.putString(Constant.PARAM_EM_CHAT_ID,emChatId);

                startActivity(GroupUserListActivity.class, bundle);
                break;
            default:
                break;
        }
    }
}
