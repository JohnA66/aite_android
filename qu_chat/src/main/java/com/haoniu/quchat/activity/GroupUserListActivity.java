package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.aite.chat.R;
import com.haoniu.quchat.adapter.GroupUserListAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.MyHelper;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.GroupDetailInfo;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.operate.GroupOperateManager;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.utils.SortUtil;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.json.FastJsonUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @author lhb
 * 设置群管理员-群用户列表，选择管理员
 */
public class GroupUserListActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_subtitle)
    TextView mToolSubTitle;
    @BindView(R.id.rv_group)
    RecyclerView mRvGroup;
    @BindView(R.id.query)
    EditText mQuery;
    @BindView(R.id.search_clear)
    ImageButton mSearchClear;
    @BindView(R.id.img_left_back)
    ImageView mImgLeftBack;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    private List<GroupDetailInfo.GroupUserDetailVoListBean> mGroupUserList = new ArrayList<>();
    private GroupUserListAdapter mGroupUserListAdapter;
    private GroupDetailInfo groupDetailInfo;

    private String groupId;
    private String emChatId;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_contact);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("添加群管理员");
        mToolSubTitle.setText("确定");
        mImgLeftBack.setVisibility(View.GONE);
        mTvBack.setText("取消");

        mQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

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

        mSearchClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuery.setText("");
            }
        });

        mToolSubTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGroupManage();
            }
        });

        mRvGroup.setHasFixedSize(true);


        mGroupUserListAdapter = new GroupUserListAdapter(mGroupUserList);
        mGroupUserListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mGroupUserListAdapter.setSelected(position);
                mGroupUserListAdapter.notifyDataSetChanged();
            }
        });
        RclViewHelp.initRcLmVertical(this, mRvGroup, mGroupUserListAdapter);
        loadGroupDataFromLocal();
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
                        int groupVersion = FastJsonUtil.getInt(json, "groupVersion");
                        //本地数据版本更服务器不一致 就需要更新数据接口
                        if (groupDetailInfo.getGroupVersion() != groupVersion) {
                            queryGroupDetail();
                        }
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
                            groupDetailInfo = FastJsonUtil.getObject(json,
                                    GroupDetailInfo.class);
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
        //用户的群等级 0-普通用户 1-管理员 2-群主
        mGroupUserList.addAll(groupDetailInfo.getGroupUserDetailVoList());

        for (int i = mGroupUserList.size() - 1; i >= 0; i--) {
            GroupDetailInfo.GroupUserDetailVoListBean bean = mGroupUserList.get(i);
            if (bean.getUserRank().equals("2") || bean.getUserRank().equals("1"))
                mGroupUserList.remove(i);
        }
        mGroupUserListAdapter.notifyDataSetChanged();
//        if (null != groupDetailInfo && groupDetailInfo.getGroupUserDetailVoList().size() > 0) {
//            mStringList.clear();
//            mGroupMemberAdapter.setGroupUserRank(groupDetailInfo.getGroupUserRank());
//            mGroupMemberAdapter.setUserReadDetail(groupDetailInfo.getGroupUserRank() != 0 || groupDetailInfo.getSeeFriendFlag() == 1);
//            mStringList.addAll(SortUtil.getInstance().groupUserAlphabetical(groupDetailInfo.getGroupUserDetailVoList()));
//        }
//
//
//        for (int i = 0; i < mStringList.size(); i++) {
//            if (!lettes.containsKey(mStringList.get(i).getTop())) {
//                lettes.put(mStringList.get(i).getTop(), i);
//            }
//        }
//
//        mGroupMemberAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        groupId = extras.getString("groupId");
        emChatId = extras.getString(Constant.PARAM_EM_CHAT_ID);
    }

    /**
     * 设置群管理员
     *
     * @param
     */
    private void setGroupManage() {
        if (mGroupUserListAdapter.getSelected() < 0) {
            toast("请选择群成员");
            return;
        }

        Map<String, Object> map = new HashMap<>(2);
        map.put("groupId", groupId);
        //0-普通用户 1-管理员
        map.put("userRank", 1);
        if (mGroupUserList.get(mGroupUserListAdapter.getSelected()).getUserId().equals(UserComm.getUserId())) {
            ToastUtil.toast("你已是群主");
            return;
        }
        map.put("userId", mGroupUserList.get(mGroupUserListAdapter.getSelected()).getUserId());


        ApiClient.requestNetHandle(this, AppConfig.MODIFY_GROUP_MANEGER, "设置群管理员...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                EventBus.getDefault().post(new EventCenter<>(EventUtil.SET_GROUP_MANAGE));
                finish();
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }


}
