package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.entity.GroupDetailInfo;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.aite.chat.R;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.utils.ImageUtil;
import com.haoniu.quchat.utils.StringUtil;
import com.haoniu.quchat.widget.EaseImageView;
import com.hyphenate.chat.EMMessage;
import com.zds.base.ImageLoad.GlideUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 描   述: 群公告
 * 日   期: 2017/11/30 15:45
 * 更新日期: 2017/11/30
 * @author lhb
 */
public class NoticeActivity extends BaseActivity {

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
    @BindView(R.id.img_head)
    EaseImageView mImgHead;
    @BindView(R.id.tv_name)//群主
            TextView mTvName;
    @BindView(R.id.tv_time)//发布时间
            TextView mTvTime;
    @BindView(R.id.tv_notice)
    TextView mNotice;
    @BindView(R.id.et_notice)
    TextView mEtNotice;
    private String groupId;
    private String toChatUsername;
    private String noticeString;
    private long time;
    private Boolean isMyroom = false;
    private String img_head, tv_head;
    private int user_rank;
    private List<GroupDetailInfo.GroupUserDetailVoListBean> mDetailVoListBeanList = new ArrayList<>();

    /**
     * 替代onCreate的使用
     *
     * @param bundle
     */
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_notice);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        setTitle("群公告");

        if (user_rank == 2) {
            mToolbarSubtitle.setText("编辑");
            mToolbarSubtitle.setVisibility(View.VISIBLE);
        } else {
            mToolbarSubtitle.setVisibility(View.GONE);
        }
        if (noticeString == null) {
            mNotice.setText("暂无公告");
        } else {
            mNotice.setText(noticeString);
        }
        GlideUtils.loadImageViewLoding(AppConfig.checkimg(img_head), mImgHead, R.mipmap.img_default_avatar);
        mTvName.setText(tv_head);
        mTvTime.setText(StringUtil.formatDateMinute(time,""));
        ImageUtil.setAvatar(mImgHead);
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
        groupId = extras.getString("groupId");
        noticeString = extras.getString("notice");
        time = extras.getLong("time");
        img_head = extras.getString("img_head");
        tv_head = extras.getString("tv_head");
        user_rank = extras.getInt("user_rank");
        toChatUsername = extras.getString("username");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.toolbar_subtitle)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                if (toolbar_subtitle.getText().toString().equals("编辑")) {
                    mNotice.setVisibility(View.GONE);
                    mEtNotice.setVisibility(View.VISIBLE);
                    toolbar_subtitle.setText("提交");
                } else {
                    submit();

                    startActivity(new Intent(this, MyGroupDetailActivity.class).putExtra("username", toChatUsername).putExtra("groupId", groupId));

                    finish();
                }
                break;
            default:
                break;
        }
    }

    private void submit() {
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", groupId);
        map.put("groupNotice", mEtNotice.getText().toString());
        ApiClient.requestNetHandle(NoticeActivity.this, AppConfig.MODIFY_GROUP_NOTICE, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                EventBus.getDefault().post(new EventCenter<String>(404,mEtNotice.getText().toString()));
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }
}
