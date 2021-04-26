package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.RedPacketAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.RedPacketInfo;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.operate.UserOperateManager;
import com.haoniu.quchat.utils.ImageUtil;
import com.haoniu.quchat.widget.EaseImageView;
import com.zds.base.ImageLoad.GlideUtils;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.json.FastJsonUtil;
import com.zds.base.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 作   者：赵大帅
 * 描   述: 红包详情
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/11/27 14:08
 * 更新日期: 2017/11/27
 *
 * @author Administrator
 */
public class RedPacketDetailActivity extends BaseActivity {

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


    //红包id
    private String rid;
    //红包类型 0群聊红包 1单聊红包
    private String type = "0";

    private RedPacketAdapter mAdapter;
    private List<RedPacketInfo.RedPacketDetailListBean> mList;

    private TextView tv_money, tv_name;
    private EaseImageView img_head;
    //头像
    private String head;
    //昵称
    private String nickname;
    //判断是否从红包记录查询
    private boolean fromRecord;
    private TextView tv_message_hb, tv_intro;
    private LinearLayout ll_user_money;
    private LinearLayout ll_message_hb;

    /**
     * 替代onCreate的使用
     *
     * @param bundle
     */
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_red_packet);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        isTransparency(true);
        View headView =
                LayoutInflater.from(this).inflate(R.layout.red_packet_head_view, null);
        tv_money = headView.findViewById(R.id.tv_money);
        ll_user_money = headView.findViewById(R.id.ll_user_money);
        ll_user_money.setVisibility(View.GONE);
        tv_name = headView.findViewById(R.id.tv_name);
        tv_intro = headView.findViewById(R.id.tv_intro);
        img_head = headView.findViewById(R.id.img_head);
        tv_message_hb = headView.findViewById(R.id.tv_message_hb);
        ll_message_hb = headView.findViewById(R.id.ll_message_hb);

        ImageUtil.setAvatar(img_head);
        mList = new ArrayList<>();
        mAdapter = new RedPacketAdapter(mList);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mAdapter);
        mAdapter.addHeaderView(headView);
        initHeadView();
        getData();
        setTitle("红包详情");
        mToolbarSubtitle.setText("红包记录");
        mToolbarSubtitle.setVisibility(View.VISIBLE);

        mToolbarSubtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ChatRedRecordActivity.class);
            }
        });
    }

    private void initHeadView() {
        if (!TextUtils.isEmpty(head))
            GlideUtils.loadRoundCircleImage(AppConfig.checkimg(head), img_head,
                    R.mipmap.img_default_avatar, 10);
        if (!TextUtils.isEmpty(nickname))
            tv_name.setText(nickname + "的红包");
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
        rid = extras.getString("rid", "");
        head = extras.getString("head", "");
        nickname = extras.getString("nickname", "");
        type = extras.getString("type", "");
        fromRecord = extras.getBoolean("fromRecord", false);
    }

    /**
     * 获取红包数据
     */
    private void getData() {

        Map<String, Object> map = new HashMap<>();
        map.put("redPacketId", rid + "");
        String url = fromRecord ? AppConfig.getRedPacketFromDB : AppConfig.getRedPacket;
        ApiClient.requestNetHandle(this, url, "",
                map, new ResultListener() {
                    @Override
                    public void onSuccess(String json, String msg) {
                        if (json != null) {
                            if (type.equals("1")) {
                                ll_message_hb.setVisibility(View.VISIBLE);
                            }
                            RedPacketInfo redPacketInfo =
                                    FastJsonUtil.getObject(json
                                            , RedPacketInfo.class);
                            if (redPacketInfo == null) {
                                ToastUtil.toast("信息异常");
                                return;
                            }
                            String time =
                                    StringUtil.isEmpty(redPacketInfo.getRobFinishTime()) ? "" : "，" + redPacketInfo.getRobFinishTime() + "被抢光";
                            tv_message_hb.setText("红包个数" + (redPacketInfo.getPacketAmount() == 0 ? "1" : redPacketInfo.getPacketAmount()) + "个，" + "共计" + StringUtil
                                    .getFormatValue2(redPacketInfo.getMoney()) + "元" + time);
                            head = redPacketInfo.getUserHead();
                            nickname = redPacketInfo.getUserNickName();

                            if (UserOperateManager.getInstance().hasUserName(redPacketInfo.getUserId())) {
                                nickname = UserOperateManager.getInstance().getUserName(redPacketInfo.getUserId());
                            }

                            initHeadView();
                            if (tv_intro != null) {
                                //tv_intro.setText(StringUtil.isEmpty(redPacketInfo.getRemark().toString()) ? "" : redPacketInfo.getRemark().toString());
                                tv_intro.setText(redPacketInfo.getRemark() == null ? "恭喜发财，大吉大利！" : redPacketInfo.getRemark().toString());
                            }
                            ll_user_money.setVisibility(View.VISIBLE);
                            if (tv_money != null) {
                                tv_money.setText("￥" + StringUtil.getFormatValue2(redPacketInfo.getMoney()));

                                if (redPacketInfo.getPacketAmount() > 0) {

                                    List<RedPacketInfo.RedPacketDetailListBean> list = redPacketInfo.getRedPacketDetailList();

                                    for (int i = 0; i < list.size(); i++) {
                                        if (list.get(i).getUserId().equals(UserComm.getUserInfo().getUserId())) {
                                            tv_money.setText("￥" + StringUtil.getFormatValue2(list.get(i).getMoney()));
                                            break;
                                        }
                                    }
                                }
                            }

                            if (redPacketInfo.getRedPacketDetailList() != null && redPacketInfo.getRedPacketDetailList().size() > 0) {
                                mList.addAll(redPacketInfo.getRedPacketDetailList());
                                /**
                                 * 已领数量
                                 */
                                int ylSize = mList.size();
                                /**
                                 * 总数量
                                 */
                                int allSize = redPacketInfo.getPacketAmount();

                                mAdapter.setIsfirsh(false);


                                double allMoney = 0;
                                for (int i = 0; i < mList.size(); i++) {
                                    allMoney += mList.get(i).getMoney();
                                }

                                tv_message_hb.setText("已领取" + ylSize + "/" + allSize + "，" + "共" + StringUtil.getFormatValue2(allMoney) +
                                        "/" + StringUtil.getFormatValue2(redPacketInfo.getMoney()) + "元" + time);
                                if (type.equals("1")) {
                                    ll_message_hb.setVisibility(View.GONE);
                                } else {
                                    ll_message_hb.setVisibility(View.VISIBLE);
                                }
                                if (ylSize == allSize) {
                                    mAdapter.setIsfirsh(true);
                                } else {
                                    mAdapter.setIsfirsh(false);
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.toast(msg);
                    }
                });
    }


}
