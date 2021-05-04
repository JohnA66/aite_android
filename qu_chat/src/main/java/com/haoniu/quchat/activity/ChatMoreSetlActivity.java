package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.GroupDetailInfo;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.operate.GroupOperateManager;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.view.MyDialog;
import com.haoniu.quchat.widget.EaseAlertDialog;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lhb
 * 更多设置
 */
public class ChatMoreSetlActivity extends BaseActivity {

    @BindView(R.id.rl_black)
    RelativeLayout mRlBlack;
    @BindView(R.id.tv_report)
    TextView mTvReport;
    @BindView(R.id.switch_shut_up)
    CheckBox mSwitchShutUp;
    @BindView(R.id.fl_group_jinyan)
    FrameLayout mFlGroupJinyan;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_del_friend)
    TextView tvDel;
    @BindView(R.id.switch_black)
    CheckBox mSwitchBlack;
    @BindView(R.id.tv_group_manager)
    TextView mTvGroupManager;
    /**
     * groupId 群id
     * mUserList 群成员列表
     */
    private String groupId;

    //1单聊用户详情过来，2群详情过来
    private String fromPerson;

    private String emChatId;
    private String nickName;
    private boolean isFriend;

    private List<GroupDetailInfo.GroupUserDetailVoListBean> mUserList =
            new ArrayList<>();

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_chat_more_set);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("更多设置");


        if (isFriend){
            tvDel.setVisibility(View.VISIBLE);
        }

        //加入黑名单
        mSwitchBlack.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                blackContact("1");
            } else {
                blackContact("0");
            }
        });

        if (!fromPerson.equals("1")) {
            GroupDetailInfo groupDetailInfo = GroupOperateManager.getInstance().getGroupData(emChatId);

            if (groupDetailInfo.getGroupSayFlag().equals("0")) {
                mSwitchShutUp.setChecked(false);
            } else {
                mSwitchShutUp.setChecked(true);
            }

            mSwitchShutUp.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    modifyGroupUserSayStatus("1");
                } else {
                    modifyGroupUserSayStatus("0");
                }
            });
        }
    }

    @Override
    protected void onEventComing(EventCenter center) {
        if (center.getEventCode() == EventUtil.SET_CHAT_BG) {
            finish();
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        emChatId = extras.getString(Constant.PARAM_EM_CHAT_ID);
        nickName = extras.getString(Constant.NICKNAME);;
        isFriend = extras.getBoolean("isFriend");;

        fromPerson = extras.getString("from");
        if (fromPerson.equals("1")) {
            mRlBlack.setVisibility(View.VISIBLE);
        } else {
            if (extras.getInt("userRank") == 0) {
                mFlGroupJinyan.setVisibility(View.GONE);
            } else {
                //userRank 用户等级 0-普通用户 1-管理员 2-群主
                mFlGroupJinyan.setVisibility(View.VISIBLE);
                mTvGroupManager.setVisibility(extras.getInt("userRank") == 2
                        ? View.VISIBLE : View.GONE);
            }
            GroupDetailInfo info =  GroupOperateManager.getInstance().getGroupData(emChatId);

            mUserList.addAll(info.getGroupUserDetailVoList());

            groupId = extras.getString("groupId");
            mTvReport.setVisibility(View.VISIBLE);

        }
    }

    @OnClick({R.id.tv_chat_bg,R.id.tv_del_friend, R.id.tv_report, R.id.tv_clear_history,
            R.id.tv_group_manager})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_chat_bg:
                //聊天背景
                Bundle bundlebg = new Bundle();
                bundlebg.putString("from", "1");
                bundlebg.putString("username", emChatId);
                startActivity(ChatBgActivity.class, bundlebg);
                break;
            case R.id.tv_report:
                //举报
                startActivity(new Intent(this, ReportActivity.class).putExtra("from", "2").putExtra("userGroupId", groupId));

                break;
            case R.id.tv_del_friend:
                new MyDialog(this)
                        .setTitle("删除联系人")
                        .setMessage("将联系人 "+nickName+" 删除，将同时删除与该联系人的聊天记录")
                        .setPositiveButton("删除", new MyDialog.OnMyDialogButtonClickListener() {
                            @Override
                            public void onClick() {
                                deleteContact();
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();

                break;
            case R.id.tv_clear_history:
                //清空聊天记录
                new EaseAlertDialog(ChatMoreSetlActivity.this, null,
                        "确定清空聊天记录吗？", null, (confirmed, bundle) -> {
                    if (confirmed) {
                        clearSingleChatHistory();
                    }
                }, true).show();
                break;
            case R.id.tv_group_manager:
                //设置管理员
                //设置群管理员
                Bundle bundle = new Bundle();
                bundle.putString("groupId", groupId);
                bundle.putString(Constant.PARAM_EM_CHAT_ID, emChatId);
                startActivity(SetGroupManageActivity.class, bundle);
                break;

            default:
                break;
        }
    }


    /**
     * 禁言/取消禁言
     *
     * @param
     */
    private void modifyGroupUserSayStatus(String sayStatus) {
        List<String> muteMembers = new ArrayList<>();
        //获取禁言列表
        if (mUserList.size() > 0) {
            for (GroupDetailInfo.GroupUserDetailVoListBean bean : mUserList) {
                //用户等级 0-普通用户 1-管理员 2-群主
                if (bean.getUserRank().equals("0")) {
                    muteMembers.add(bean.getUserId() + Constant.ID_REDPROJECT);
                }
            }
        }


        Map<String, Object> map = new HashMap<>(1);
        map.put("groupId", groupId);
        //0 - 取消禁言 1 - 禁言
        map.put("sayStatus", sayStatus);

        ApiClient.requestNetHandle(this,
                AppConfig.MODIFY_GROUP_ALL_USER_SAY_STATUS, "", map,
                new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {

                try {
                    EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }

                toast(msg);
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }


    /**
     * delete contact
     */
    public void deleteContact() {
        Map<String, Object> map = new HashMap<>(1);
        map.put("friendUserId", emChatId.split("-")[0]);
        ApiClient.requestNetHandle(this, AppConfig.DEL_USER_FRIEND, "正在删除..."
                , map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                try {
                    EMClient.getInstance().contactManager().deleteContact(emChatId);
                    EMClient.getInstance().chatManager().deleteConversation(emChatId, false);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                EventBus.getDefault().post(new EventCenter<>(EventUtil.DELETE_CONTACT));
                EventBus.getDefault().post(new EventCenter<>(EventUtil.REFRESH_REMARK));
                toast("删除成功");
                finish();

            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }


    /**
     * 拉黑好友
     */
    public void blackContact(String blackStatus) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("friendUserId", emChatId.split("-")[0]);
        //拉黑状态 0-未拉黑 1-已拉黑
        map.put("blackStatus", blackStatus);

        ApiClient.requestNetHandle(this, AppConfig.BLACK_USER_FRIEND,
                mSwitchBlack.isChecked() ? "正在拉黑..." : "正在取消拉黑...", map,
                new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (mSwitchBlack.isChecked()) {
                    toast("拉黑成功");
                    EMClient.getInstance().chatManager().deleteConversation(emChatId.contains(Constant.ID_REDPROJECT) ? emChatId : emChatId + Constant.ID_REDPROJECT, false);

                    EventBus.getDefault().post(new EventCenter<>(EventUtil.OPERATE_BLACK));
                    finish();
                }

            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }


    /**
     * 清空单聊聊天记录
     */
    private void clearSingleChatHistory() {
        EventBus.getDefault().post(new EventCenter<>(EventUtil.CLEAR_HUISTROY));
        Toast.makeText(this, R.string.messages_are_empty, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
