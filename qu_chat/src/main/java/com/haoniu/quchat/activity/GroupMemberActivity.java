package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.GroupMemberAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.GroupDetailInfo;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.operate.GroupOperateManager;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.utils.SortUtil;
import com.haoniu.quchat.view.SlideRecyclerView;
import com.haoniu.quchat.widget.EaseAlertDialog;
import com.haoniu.quchat.widget.SearchBar;
import com.zds.base.json.FastJsonUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static com.haoniu.quchat.base.Constant.PARAM_AT_NAME;
import static com.haoniu.quchat.base.Constant.PARAM_AT_USERID;


/**
 * @author lhb
 * 群成员
 */
public class GroupMemberActivity extends BaseActivity implements GroupMemberAdapter.OnDelClickListener {
    @BindView(R.id.rv_new_friend)
    SlideRecyclerView mRvNewFriend;
    @BindView(R.id.search_bar)
    SearchBar searchBar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    private ArrayList<GroupDetailInfo.GroupUserDetailVoListBean> mStringList = new ArrayList<>();
    private GroupMemberAdapter mGroupMemberAdapter;
    private HashMap<String, Integer> lettes;
    private boolean isForAtMerber;
    private String emChatId;
    private String groupId;
//    private GroupDetailInfo info;
    private GroupDetailInfo groupDetailInfo;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_group_member);
    }

    @Override
    protected void initLogic() {
        setTitle("群聊成员");

        lettes = new HashMap<>(1);
        mGroupMemberAdapter = new GroupMemberAdapter(mStringList, this, lettes, emChatId);
        mGroupMemberAdapter.setOnDelClickListener(this);

        RclViewHelp.initRcLmVertical(this, mRvNewFriend, mGroupMemberAdapter);

        if (isForAtMerber) {
            mGroupMemberAdapter.setOnClickAtUserListener(new GroupMemberAdapter.OnClickAtUserListener() {
                @Override
                public void atUser(String atUserName, String atUserId) {
                    Intent intent = new Intent();
                    intent.putExtra(PARAM_AT_NAME, atUserName);
                    intent.putExtra(PARAM_AT_USERID, atUserId);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }

        searchBar.setOnSearchBarListener(new SearchBar.OnSearchBarListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mGroupMemberAdapter.getFilter().filter(s.toString());
            }
        });

        loadGroupDataFromLocal();
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        emChatId = extras.getString(Constant.PARAM_EM_GROUP_ID);
        isForAtMerber = extras.getBoolean(Constant.PARAM_FOR_AT_MERBER);
        groupId = GroupOperateManager.getInstance().getGroupId(emChatId);
    }


    public void loadGroupDataFromLocal() {

        queryGroupDetail();

//        groupDetailInfo = GroupOperateManager.getInstance().getGroupMemberList(emChatId);
//        Map<String, Object> map = new HashMap<>();
//        map.put("groupId", groupId);
//        if (groupDetailInfo != null) {
//            setGroupMemberData();
//        }
//        ApiClient.requestNetHandle(this, AppConfig.CHECK_GROUP_DATA_VERSION, "",
//                map, new ResultListener() {
//                    @Override
//                    public void onSuccess(String json, String msg) {
////                        int groupVersion = FastJsonUtil.getInt(json, "groupVersion");
//                        //本地数据版本更服务器不一致 就需要更新数据接口
////                        if (groupDetailInfo.getGroupVersion() != groupVersion) {
//                            queryGroupDetail();
////                        }
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//                        queryGroupDetail();
//                    }
//                });

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
        if (null != groupDetailInfo && groupDetailInfo.getGroupUserDetailVoList().size() > 0) {
            mStringList.clear();
            mGroupMemberAdapter.setGroupUserRank(groupDetailInfo.getGroupUserRank());
            mGroupMemberAdapter.setUserReadDetail(groupDetailInfo.getGroupUserRank() != 0 || groupDetailInfo.getSeeFriendFlag() == 1);
            mStringList.addAll(SortUtil.getInstance().groupUserAlphabetical(groupDetailInfo.getGroupUserDetailVoList()));
        }


        for (int i = 0; i < mStringList.size(); i++) {
            if (!lettes.containsKey(mStringList.get(i).getTop())) {
                lettes.put(mStringList.get(i).getTop(), i);
            }
        }

        mGroupMemberAdapter.notifyDataSetChanged();
    }

    /**
     * 移出群员
     *
     * @param pos 群成员集合索引下标
     */
    @Override
    public void delUser(int pos) {
        if (!isForAtMerber)
            new EaseAlertDialog(GroupMemberActivity.this, null, "确定移除该成员？", null, new EaseAlertDialog.AlertDialogUser() {
                @Override
                public void onResult(boolean confirmed, Bundle bundle) {
                    if (confirmed) {
                        // 0-普通用户 1-管理员 2-群主
                        if (groupDetailInfo.getGroupUserRank() == 2 || groupDetailInfo.getGroupUserRank() == 1) {
                            delGroupUser(pos);
                        }
                    }
                }
            }, true).show();
    }


    /**
     * 移出群员
     *
     * @param
     */
    private void delGroupUser(int pos) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("groupId", groupId);
        map.put("userId", mStringList.get(pos).getUserId());

        ApiClient.requestNetHandle(this, AppConfig.DEL_GROUP_USER, "正在踢出成员...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                toast("踢出成功");
                mStringList.remove(pos);
                mGroupMemberAdapter.notifyDataSetChanged();
                EventBus.getDefault().post(new EventCenter<>(EventUtil.INVITE_USER_ADD_GROUP));
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });

    }

}
