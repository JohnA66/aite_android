package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haoniu.quchat.adapter.NewRoomDeatilAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.RoomInfo;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.hyphenate.chat.EMCursorResult;
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
 * 作   者：赵大帅
 * 描   述: 群成员
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/12/11 16:05
 * 更新日期: 2017/12/11
 */
public class GroupMemberMoreActivity extends BaseActivity {
    @BindView(R.id.bar)
    View mBar;
    @BindView(R.id.ll_back)
    LinearLayout mLlBack;
    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubtitle;
    @BindView(R.id.img_right)
    ImageView mImgRight;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;

    private NewRoomDeatilAdapter adapter;
    private List<RoomInfo.UserListBean> memberList;
    EMCursorResult<String> result = null;

    private String groupId;
    private boolean isOwer;
    private String ower;
    private String groupOwer;
    private int roomId;
    private int userId;

    /**
     * 替代onCreate的使用
     *
     * @param bundle
     */
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_group_member_more);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        setTitle("群成员");

        if (groupOwer.equals("1")) {
            mToolbarSubtitle.setVisibility(View.VISIBLE);
            mToolbarSubtitle.setText("删除");
        }

        memberList = new ArrayList<>();
//        memberList.add(ower);
        adapter = new NewRoomDeatilAdapter(memberList);
        mRecycleView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecycleView.setAdapter(adapter);
        adapter.openLoadAnimation();
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapters, View view, int position) {
                if (adapter.getMode() == 1) {
                    deleteMembersFromGroup(memberList.get(position).getId() + "", position);
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapters, View view, int position) {
                if (groupOwer.equals("1")) {
                    if (userId == (memberList.get(position).getId())) {
                        toast("不能和自己聊天");
                    } else {
                        startActivity(new Intent(GroupMemberMoreActivity.this, ChatActivity.class).putExtra("userId", memberList.get(position).getId() + Constant.ID_REDPROJECT).putExtra("groups_Id", groupId));
                    }
                }
                //startActivity(new Intent(GroupMemberMoreActivity.this, UserInfoActivity.class).putExtra("username", memberList.get(position)));
            }
        });
//        if (adapter.getMode() == 0) {
//            mToolbarSubtitle.setText("删除");
//        }
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

                mRecycleView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null && !TextUtils.isEmpty(result.getCursor()) && result.getData().size() == 60) {
//                            getData();
                            getDatas();
                        } else {
                            adapter.loadMoreEnd(true);
                        }
                    }
                });

            }
        });
//        if (isOwer) {
//            mToolbarSubtitle.setVisibility(View.VISIBLE);
//        } else {
//            mToolbarSubtitle.setVisibility(View.GONE);
//        }
        getDatas();

    }

    /**
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
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
        groupId = extras.getString("groupId", "");
        isOwer = extras.getBoolean("isOwer", false);
        ower = extras.getString("ower", "");
        groupOwer = extras.getString("groupOwer", "");
        userId = extras.getInt("userId", 0);
        roomId = extras.getInt("roomId", 0);

    }

//    private void getData() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    result = EMClient.getInstance().groupManager().fetchGroupMembers(groupId,
//                            result != null ? result.getCursor() : "", 60);
//                } catch (HyphenateException e) {
//                    e.printStackTrace();
//                }
//                memberList.addAll(result.getData());
//                initHead(result.getData());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter.loadMoreComplete();
//                        adapter.notifyDataSetChanged();
//                    }
//                });
//            }
//        }).start();
//
//    }

    /**
     * 更新群用户数据
     */
    private void getDatas() {
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", groupId);
        ApiClient.requestNetHandle(this, AppConfig.roomAllUser, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (null != json) {
                    memberList.addAll(FastJsonUtil.getList(json, RoomInfo.UserListBean.class));
                    adapter.loadMoreComplete();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);

            }
        });


    }


    /**
     * 更新群用户数据
     */
    private void flushGetDatas() {
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", groupId);
        ApiClient.requestNetHandle(this, AppConfig.roomAllUser, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (null != json) {
                    memberList.clear();
                    toolbar_subtitle.setText("删除");
                    memberList.addAll(FastJsonUtil.getList(json, RoomInfo.UserListBean.class));
                    adapter.loadMoreComplete();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);

            }
        });


    }



    /**
     * 删除群成员
     *
     * @param username
     */
    protected void deleteMembersFromGroup(final String username, final int position) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("roomId", roomId);
        map.put("userId", username);
        ApiClient.requestNetHandle(GroupMemberMoreActivity.this, AppConfig.NeWRemoveRoom, "正在踢人...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                mToolbarSubtitle.setText("删除");
                flushGetDatas();
                try {
                    adapter.remove(position);
                    toast(msg);
                } catch (Exception e) {
                    toast(e.getMessage());
                }
            }

            @Override
            public void onFinsh() {
                super.onFinsh();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.toolbar_subtitle)
    public void onViewClicked() {
        if (adapter.getMode() == 0) {
            adapter.setMode(1);
            mToolbarSubtitle.setText("完成");
        } else if (adapter.getMode() == 1) {
            adapter.setMode(0);
            mToolbarSubtitle.setText("删除");
        }
        adapter.notifyDataSetChanged();
    }
}
