package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.MyRoomDeatilAdapter;
import com.haoniu.quchat.aop.SingleClick;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.MyHelper;
import com.haoniu.quchat.domain.EaseGroupInfo;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.GroupDetailInfo;
import com.haoniu.quchat.global.Global;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.utils.EventUtil;
import com.haoniu.quchat.operate.GroupOperateManager;
import com.haoniu.quchat.utils.StringUtil;
import com.haoniu.quchat.utils.hxSetMessageFree.EaseSharedUtils;
import com.haoniu.quchat.widget.CommonDialog;
import com.haoniu.quchat.widget.EaseAlertDialog;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.exceptions.HyphenateException;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zds.base.ImageLoad.GlideUtils;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.Utils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lhb
 * 群组详情
 */
public class MyGroupDetailActivity extends BaseActivity implements MyRoomDeatilAdapter.OnDelClickListener {
    @BindView(R.id.bar)
    View mBar;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.ll_back)
    LinearLayout mLlBack;
    @BindView(R.id.img_right)
    ImageView mImgRight;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.llayout_title_1)
    RelativeLayout mLlayoutTitle1;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.group_img)
    ImageView mGroupImg;
    @BindView(R.id.rl_room_img)
    RelativeLayout mRlRoomImg;
    @BindView(R.id.tv_group_name)
    TextView mTvGroupName;
    @BindView(R.id.group_name)
    TextView mGroupName;
    @BindView(R.id.rl_room_id)
    RelativeLayout mRlRoomId;
    @BindView(R.id.tv_group_zx)
    TextView mTvGroupZx;
    @BindView(R.id.tv_group_id)
    TextView mTvGroupId;
    @BindView(R.id.tv_msg_record)
    TextView mTvMsgRecord;
    @BindView(R.id.tv_msg_disturb)
    TextView mTvMsgDisturb;
    @BindView(R.id.switch_msg)
    CheckBox mSwitchMsg;
    @BindView(R.id.tv_clear_msg)
    TextView mTvClearMsg;
    @BindView(R.id.tv_exit)
    TextView mTvExit;
    @BindView(R.id.tv_my_group_nick_name)
    TextView mTvMyGroupNickName;
    @BindView(R.id.tv_total)
    TextView mTvTotal;
    @BindView(R.id.tv_group_announcement)
    TextView mTvGroupAnnouncement;
    @BindView(R.id.tv_user_red_detail)
    TextView tvUserRedDetail;
    @BindView(R.id.switch_user_red_detail)
    CheckBox switchUserRedDetail;
    @BindView(R.id.fl_user_read_detail)
    FrameLayout flUserReadDetail;
    @BindView(R.id.tv_group_manager)
    TextView tvGroupManager;
    @BindView(R.id.fl_group_jinyan)
    FrameLayout mFlGroupJinyan;
    @BindView(R.id.switch_shut_up)
    CheckBox mSwitchShutUp;

    @BindView(R.id.switch_top_conversation)
    CheckBox mSwitchTopConversation;

    @BindView(R.id.tv_transfer_group)
    TextView tv_transfer_group;

    @BindView(R.id.tv_group_remark)
    TextView tv_group_remark;

    @BindView(R.id.rl_container_group_single_member_jinyan)
    RelativeLayout rl_container_group_single_member_jinyan;


    /**
     * groupId 自己服务器群id
     * emChatId 环信服务器id
     */
    private String groupId;
    private String emChatId;
    private List<GroupDetailInfo.GroupUserDetailVoListBean> mDetailVoListBeanList = new ArrayList<>();
    private MyRoomDeatilAdapter mRoomDeatilAdapter;
    private GroupDetailInfo info;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_my_group_details);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("群信息");

        String isMsgFree =
                MyHelper.getInstance().getModel().getConversionMsgIsFree(emChatId);
        if (null != isMsgFree && isMsgFree.equals("false")) {
            //设置消息免打扰
            mSwitchMsg.setChecked(true);
        } else {
            //取消消息免打扰
            mSwitchMsg.setChecked(false);
        }
        //消息免打扰
        mSwitchMsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    EaseSharedUtils.setEnableMsgRing(Utils.getContext(),
                            UserComm.getUserId() + Constant.ID_REDPROJECT,
                            emChatId, false);
                    MyHelper.getInstance().getModel().saveChatBg(emChatId, null, "false", null);
                } else {
                    EaseSharedUtils.setEnableMsgRing(Utils.getContext(),
                            UserComm.getUserId() + Constant.ID_REDPROJECT,
                            emChatId, true);
                    MyHelper.getInstance().getModel().saveChatBg(emChatId, null, "true", null);
                }
            }
        });


        mRoomDeatilAdapter = new MyRoomDeatilAdapter(mDetailVoListBeanList);
        mRoomDeatilAdapter.setOnDelClickListener(this);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setNestedScrollingEnabled(false);
        mRecycleView.setLayoutManager(new GridLayoutManager(this, 5));
        mRecycleView.setAdapter(mRoomDeatilAdapter);
//        info = GroupOperateManager.getInstance().getGroupData(emChatId);


        EMConversation emConversation =  EMClient.getInstance().chatManager().getConversation(emChatId);

        if (emConversation.getExtField().equals("toTop")) {
            mSwitchTopConversation.setChecked(true);
        } else {
            mSwitchTopConversation.setChecked(false);
        }

        //会话置顶
        mSwitchTopConversation.setOnCheckedChangeListener((buttonView,
                                                           isChecked) -> {
            if (!emConversation.conversationId().equals(Constant.ADMIN)) {
                if (emConversation.conversationId().equals(emChatId)) {
                    if (isChecked) {
                        emConversation.setExtField("toTop");
                    } else {
                        emConversation.setExtField("false");
                    }
                }
            }
        });

        if (info == null){
            queryGroupDetail();
        }else {
            setGroupDetail();
        }
    }

    /**
     * 是否可以查看用户之间详情
     *
     * @param isChecked
     */
    private void uploadStatus(boolean isChecked) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("groupId", groupId);
        map.put("seeFriendFlag", isChecked ? 1 : 0);

        ApiClient.requestNetHandle(this,
                AppConfig.GROUP_DETAIL_READ_USER_DETAIL, "", map,
                new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                    }

                    @Override
                    public void onFailure(String msg) {
                        toast(msg);
                    }
                });
    }


    /**
     * 查询群详情
     */
    public void queryGroupDetail() {
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", groupId);
        ApiClient.requestNetHandle(MyGroupDetailActivity.this,
                AppConfig.GET_GROUP_INFO, "", map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        if (json != null && json.length() > 0) {
                            info = FastJsonUtil.getObject(json,
                                    GroupDetailInfo.class);

                            GroupOperateManager.getInstance().saveGroupDetailToLocal(emChatId,info,json);
                            setGroupDetail();
                        }

                    }

                    @Override
                    public void onFailure(String msg) {
                    }
                });
    }

    /**
     * 查询群详情
     */
    public void setGroupDetail() {

        mRoomDeatilAdapter.setGroupName(info.getGroupName());
        mRoomDeatilAdapter.setEmGroupId(emChatId);
        mRoomDeatilAdapter.setCurrentUserRank(info.getGroupUserRank());
        mRoomDeatilAdapter.setUserReadDetail(info.getSeeFriendFlag() == 1 || info.getGroupUserRank() != 0);
        mRoomDeatilAdapter.setCurrentUserRank(info.getGroupUserRank());
        mTvGroupId.setText(StringUtil.isEmpty(info.getHuanxinGroupId()) ? "" : info.getHuanxinGroupId());
        mTvMyGroupNickName.setText(info.getGroupUserNickName());

        //GlideUtils.loadRoundCircleImage(AppConfig
        // .checkimg(info
        // .getGroupHead()), (ImageView) mGroupImg, R.mipmap
        // .img_default_avatar, 12);
        GlideUtils.GlideLoadCircleErrorImageUtils(MyGroupDetailActivity.this, AppConfig.checkimg(info.getGroupHead())
                , mGroupImg, R.mipmap.img_default_avatar);

        mGroupName.setText(info.getGroupName());
        tv_group_remark.setText(info.groupNickName);

        //用户的群等级 0-普通用户 1-管理员 2-群主
        if (info.getGroupUserRank() == 2) {
            mTvExit.setText("解散群聊");
            tvGroupManager.setVisibility( View.VISIBLE);
            mFlGroupJinyan.setVisibility(View.VISIBLE);
        } else if (info.getGroupUserRank() == 1 ){
            mTvExit.setText("退出群聊");
            mFlGroupJinyan.setVisibility(View.VISIBLE);
            tvGroupManager.setVisibility(View.GONE);
        }else if (info.getGroupUserRank() == 0 ) {
            mTvExit.setText("退出群聊");
            mFlGroupJinyan.setVisibility(View.GONE);
            tvGroupManager.setVisibility( View.GONE);
        }
        flUserReadDetail.setVisibility(0 == info.getGroupUserRank() ? View.GONE : View.VISIBLE);
        tv_transfer_group.setVisibility(2 == info.getGroupUserRank() ? View.VISIBLE : View.GONE);
        //rl_container_group_single_member_jinyan.setVisibility(0 == info.getGroupUserRank() ? View.GONE : View.VISIBLE);

        int showSize = 19;
        if (info.getGroupUserDetailVoList().size() > 0) {
            mTvTotal.setText("共" + info.getGroupUsers() + "人");
            mDetailVoListBeanList.clear();
            if (info.getGroupUserRank() != 0)
                showSize = 18;
            if (info.getGroupUserDetailVoList().size() <= showSize) {
                mDetailVoListBeanList.addAll(info.getGroupUserDetailVoList());
            } else {
                for (int i = 0; i < showSize; i++) {
                    mDetailVoListBeanList.add(info.getGroupUserDetailVoList().get(i));
                }
            }



            GroupDetailInfo.GroupUserDetailVoListBean bean =
                    new GroupDetailInfo.GroupUserDetailVoListBean();
            //最后一个添加图标
            bean.setUserId("add");
            //群id
            bean.setGroupId(groupId);
            //群昵称
            bean.setUserNickName(info.getGroupUserNickName());
            mDetailVoListBeanList.add(bean);


            if (info.getGroupUserRank() != 0){
                GroupDetailInfo.GroupUserDetailVoListBean delBean =
                        new GroupDetailInfo.GroupUserDetailVoListBean();
                //最后一个添加图标
                delBean.setUserId("del");
                //群id
                delBean.setGroupId(groupId);
                //群昵称
                delBean.setUserNickName(info.getGroupUserNickName());
                mDetailVoListBeanList.add(delBean);
            }

            mTvGroupAnnouncement.setText(StringUtil.isEmpty(info.getGroupNotice()) ? "暂无群公告" : info.getGroupNotice());
            mTvGroupAnnouncement.setVisibility(View.VISIBLE);
            mRoomDeatilAdapter.notifyDataSetChanged();
        }
        setSWitchReadPermission();
        setMuteSwitch();
    }

    @Override
    protected void onEventComing(EventCenter center) {
        if (center.getEventCode() == EventUtil.REFRESH_GROUP_NAME) {
            mGroupName.setText(center.getData().toString());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().groupManager().getGroupFromServer(emChatId);
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } else if (center.getEventCode() == EventUtil.REFRESH_MY_GROUP_NAME) {
            mTvMyGroupNickName.setText(center.getData().toString());
        } else if (center.getEventCode() == EventUtil.INVITE_USER_ADD_GROUP || center.getEventCode() == EventUtil.TRANSFER_GROUP) {
            queryGroupDetail();
        }else if (center.getEventCode() == EventUtil.DEL_GROUP_MEMBER){
            List<GroupDetailInfo.GroupUserDetailVoListBean> mIdList = (List<GroupDetailInfo.GroupUserDetailVoListBean>) center.getData();
            for (GroupDetailInfo.GroupUserDetailVoListBean groupUserDetailVoListBean : mIdList) {
                for (int i = mDetailVoListBeanList.size() - 1; i >= 0; i--) {
                    GroupDetailInfo.GroupUserDetailVoListBean userDetailVoListBean = mDetailVoListBeanList.get(i);
                    if (userDetailVoListBean.getUserId().equals(groupUserDetailVoListBean.getUserId())){
                        mDetailVoListBeanList.remove(userDetailVoListBean);
                        info.getGroupUserDetailVoList().remove(userDetailVoListBean);
                    }
                }
            }

            int count = info.getGroupUsers() - mIdList.size();
            info.setGroupUsers(count);
            mTvTotal.setText("共" + count + "人");
            mRoomDeatilAdapter.notifyDataSetChanged();
        }else if (center.getEventCode() == 404){
            //刷新群公告
            if(!TextUtils.isEmpty(center.getData().toString())){
                mTvGroupAnnouncement.setText(center.getData().toString());
            }

        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        groupId = extras.getString("groupId");
        emChatId = extras.getString("username");
    }

    @Override
    protected void onStart() {
        super.onStart();
//        queryGroupDetail();
    }




    /**
     * 选择图片上传
     */
    private void toSelectPic() {
        final CommonDialog.Builder builder =
                new CommonDialog.Builder(this).fullWidth().fromBottom()
                        .setView(R.layout.dialog_select_head);
        builder.setOnClickListener(R.id.tv_cell, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        builder.setOnClickListener(R.id.tv_xiangji, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                PictureSelector.create(MyGroupDetailActivity.this)
                        .openCamera(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .withAspectRatio(1, 1)
                        .enableCrop(true)
                        .showCropFrame(false)
                        .showCropGrid(false)
                        .freeStyleCropEnabled(true)
                        .circleDimmedLayer(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });
        builder.setOnClickListener(R.id.tv_xiangce, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                PictureSelector.create(MyGroupDetailActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .withAspectRatio(1, 1)
                        .enableCrop(true)
                        .showCropFrame(false)
                        .showCropGrid(false)
                        .freeStyleCropEnabled(true)
                        .circleDimmedLayer(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);

            }
        });
        builder.create().show();
    }


    /**
     * 更新头像
     *
     * @param filePath
     */

    private void saveHead(String filePath) {

        ApiClient.requestNetHandleFile(MyGroupDetailActivity.this,
                AppConfig.groupUpHead, "正在上传...", new File(filePath),
                new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        headUrl = json;
                        setGroupImg();
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.toast(msg);
                    }
                });

    }


    /**
     * 修改群昵称
     */
    private void setGroupImg() {
        if (headUrl.length() <= 0) {
            toast("请先上传头像");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("groupName", "");
        map.put("groupHead", headUrl);
        map.put("groupId", groupId);

        //1-群名称 2-群头像
        map.put("updateType", "2");

        ApiClient.requestNetHandle(this, AppConfig.MODIFY_GROUP_NAME_OF_HEAD,
                "", map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        EventBus.getDefault().post(new EventCenter<>(EventUtil.REFRESH_CONVERSION));
                        EventBus.getDefault().post(new EventCenter(EventUtil.FLUSHGROUP));
                        GlideUtils.GlideLoadCircleErrorImageUtils(MyGroupDetailActivity.this, AppConfig.checkimg(headUrl), mGroupImg,
                                R.mipmap.img_default_avatar);
                        toast("上传成功");
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.toast(msg);
                    }
                });
    }


    /**
     * 退出群组
     *
     * @param
     */
    private void exitGroup() {
        Map<String, Object> map = new HashMap<>(1);
        map.put("groupId", groupId);
        ApiClient.requestNetHandle(MyGroupDetailActivity.this,
                AppConfig.exitGroup, "正在退出群组...", map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        EventBus.getDefault().post(new EventCenter<>(EventUtil.DEL_EXIT_GROUP));
                        finish();
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.toast(msg);
                    }
                });

    }

    /**
     * 解散群组
     *
     * @param
     */
    private void delGroup() {
        Map<String, Object> map = new HashMap<>(1);
        map.put("groupId", groupId);
        ApiClient.requestNetHandle(MyGroupDetailActivity.this,
                AppConfig.DEL_GROUP, "正在解散群组...", map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        EventBus.getDefault().post(new EventCenter<>(EventUtil.DEL_EXIT_GROUP));
                        finish();
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.toast(msg);
                    }
                });

    }

    public void setMuteSwitch(){
        mSwitchShutUp.setOnCheckedChangeListener(null);

        if (info.getGroupSayFlag().equals("0")) {
            mSwitchShutUp.setChecked(false);
        } else {
            mSwitchShutUp.setChecked(true);
        }

        mSwitchShutUp.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                modifyGroupUserSayStatus("1");
                info.setGroupSayFlag("1");
            } else {
                modifyGroupUserSayStatus("0");
                info.setGroupSayFlag("0");
            }
            GroupOperateManager.getInstance().updateGroupData(emChatId,info);
        });
    }
    public void setSWitchReadPermission(){
        switchUserRedDetail.setOnCheckedChangeListener(null);
        if (2 == info.getGroupUserRank() || 1 == info.getGroupUserRank()) {
            switchUserRedDetail.setChecked(info.getSeeFriendFlag() != 0);
        }
        switchUserRedDetail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                uploadStatus(isChecked);

                if (isChecked) {
                    info.setSeeFriendFlag(1);
                } else {
                    info.setSeeFriendFlag(0);
                }
                GroupOperateManager.getInstance().updateGroupData(emChatId,info);
            }
        });

    }

    /**
     * 移出群员
     *
     * @param
     */
    private void delGroupUser(int pos) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("groupId", groupId);
        map.put("userId", mDetailVoListBeanList.get(pos).getUserId());

        ApiClient.requestNetHandle(this, AppConfig.DEL_GROUP_USER, "正在踢出成员.." +
                ".", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                ToastUtil.toast(msg);
                mRoomDeatilAdapter.setMode(0);
                mDetailVoListBeanList.remove(pos);
                mRoomDeatilAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });

    }


    private String headUrl = "";

    @OnClick({R.id.fl_group_member, R.id.tv_my_group_nick_name_s,
            R.id.tv_group_zx, R.id.rl_room_id,R.id.rl_container_group_remark,
            R.id.tv_group_notice, R.id.tv_msg_record,
            R.id.tv_group_manager,R.id.rl_container_group_single_member_jinyan,
            R.id.tv_clear_msg, R.id.tv_exit,R.id.tv_transfer_group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_room_id:
                //群昵称
                if (info.getGroupUserRank() != 0) {
                    startActivity(new Intent(this, EditInfoActivity.class)
                            .putExtra("from", "4")
                            .putExtra("groupName",
                                    mGroupName.getText().toString().trim())
                            .putExtra("groupId", groupId));
                }

                break;
            case R.id.rl_container_group_remark:
                startActivity(new Intent(this, EditInfoActivity.class)
                        .putExtra("from", EditInfoActivity.FUNC_TYPE_MODIFY_GROUP_REMARK)
                        .putExtra("key_intent_group_remark",
                                tv_group_remark.getText().toString().trim())
                        .putExtra("groupId", groupId));
                break;
            case R.id.tv_my_group_nick_name_s:
                //昵称
                startActivity(new Intent(this, EditInfoActivity.class)
                        .putExtra("from", "5")
                        .putExtra("myGroupName",
                                mTvMyGroupNickName.getText().toString().trim())
                        .putExtra("groupId", groupId));
                break;
            case R.id.rl_room_img:
                //群头像
                if (info.getGroupUserRank() == 2) {
                    toSelectPic();
                }
                break;

            case R.id.tv_group_notice:
                //群公告
                startActivity(new Intent(this, NoticeActivity.class)
                        .putExtra("time", info.getUpdateGroupNoticeTime())
                        .putExtra("img_head",
                                mDetailVoListBeanList.get(0).getUserHead())
                        .putExtra("tv_head",
                                mDetailVoListBeanList.get(0).getUserNickName())
                        .putExtra("groupId", groupId)
                        .putExtra("user_rank", info.getGroupUserRank())
                        .putExtra("notice", info.getGroupNotice())
                        .putExtra("username", emChatId));
                finish();

                break;
            case R.id.tv_msg_record:
                startActivity(new Intent(this, ChatRecordActivity.class).putExtra("chatId", emChatId));
                break;

            case R.id.tv_group_manager:
                //设置管理员
                //设置群管理员
                Bundle bundle = new Bundle();
                bundle.putString("groupId", groupId);
                bundle.putString(Constant.PARAM_EM_CHAT_ID, emChatId);
                startActivity(SetGroupManageActivity.class, bundle);
                break;
            case R.id.tv_clear_msg:
                //清空聊天记录
                String st9 =
                        getResources().getString(R.string.sure_to_empty_this);
                new EaseAlertDialog(MyGroupDetailActivity.this, null, st9,
                        null, new EaseAlertDialog.AlertDialogUser() {

                    @Override
                    public void onResult(boolean confirmed, Bundle bundle) {
                        if (confirmed) {
                            clearGroupHistory();
                        }
                    }
                }, true).show();
                break;
            case R.id.tv_exit:
                //退出 1:解散群组 （群主）
                //2 :退出群组 （普通成员，管理员）
                String str = "";
                if (info.getGroupUserRank() == 2) {
                    str = getResources().getString(R.string.dissolution_group_hint);
                } else {
                    str = getResources().getString(R.string.exit_group_hint);
                }
                new EaseAlertDialog(MyGroupDetailActivity.this, null, str,
                        null, new EaseAlertDialog.AlertDialogUser() {
                    @Override
                    public void onResult(boolean confirmed, Bundle bundle) {
                        if (confirmed) {
                            // 0-普通用户 1-管理员 2-群主
                            if (info.getGroupUserRank() == 2) {
                                delGroup();
                            } else {
                                exitGroup();
                            }
                        }
                    }
                }, true).show();

                break;
            case R.id.tv_group_zx:
                Bundle bundle2 = new Bundle();
                bundle2.putString("from", "2");
                bundle2.putString("id", groupId);
                bundle2.putString("head", info.getGroupHead());
                bundle2.putString("name", info.getGroupName());
                startActivity(MyQrActivity.class, bundle2);
                break;
            case R.id.fl_group_member:
                Global.addUserOriginType = Constant.ADD_USER_ORIGIN_TYPE_GROUPCHAT;
                Global.addUserOriginName = info.getGroupName();

                Bundle bundle1 = new Bundle();
                bundle1.putString(Constant.PARAM_GROUP_ID,groupId);
                bundle1.putString(Constant.PARAM_EM_GROUP_ID,emChatId);
                startActivity(GroupMemberActivity.class, bundle1);
                break;
            case R.id.tv_transfer_group:
                TransferGroupActivity.start(this,emChatId,groupId);
                break;
            case R.id.rl_container_group_single_member_jinyan:
                GroupSingleMemberMuteActivity.start(this,emChatId,groupId);
                break;
            default:
                break;
        }
    }


    /**
     * 清空群聊天记录
     */
    private void clearGroupHistory() {
        EventBus.getDefault().post(new EventCenter<>(EventUtil.CLEAR_HUISTROY));
        Toast.makeText(this, R.string.messages_are_empty, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> selectList =
                        PictureSelector.obtainMultipleResult(data);
                if (selectList.size() > 0) {
                    if (selectList.get(0).isCut()) {
                        saveHead(selectList.get(0).getCutPath());

                    } else {
                        saveHead(selectList.get(0).getPath());
                    }
                }
            }
        }
    }


    /**
     * 移出群员
     *
     * @param pos 群成员集合索引下标
     */
    @Override
    public void delUser(int pos) {
        new EaseAlertDialog(MyGroupDetailActivity.this, null, "确定移除该成员？",
                null, new EaseAlertDialog.AlertDialogUser() {
            @Override
            public void onResult(boolean confirmed, Bundle bundle) {
                if (confirmed) {
                    // 0-普通用户 1-管理员 2-群主
                    if (info.getGroupUserRank() == 2) {
                        delGroupUser(pos);
                    }
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

    private void modifyGroupUserSayStatus(String sayStatus) {
        List<String> muteMembers = new ArrayList<>();
        //获取禁言列表
        if (info.getGroupUserDetailVoList().size() > 0) {
            for (GroupDetailInfo.GroupUserDetailVoListBean bean : info.getGroupUserDetailVoList()) {
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

                        if (sayStatus.equals("1")){
                            toast("已开启禁言功能");
                        }else if (sayStatus.equals("0")){
                            toast("禁言功能已关闭");
                        }

                    }

                    @Override
                    public void onFailure(String msg) {
                        toast(msg);
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().post(new EventCenter<>(EventUtil.ROOM_INFO));
    }
}
