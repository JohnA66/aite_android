package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.BlackAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.model.BlackListInfo;
import com.haoniu.quchat.model.ContactInfo;
import com.haoniu.quchat.model.ContactListInfo;
import com.haoniu.quchat.utils.EventUtil;
import com.zds.base.json.FastJsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @author lhb
 * 黑名单
 */
public class BlackListActivity extends BaseActivity {

    @BindView(R.id.rv_black)
    RecyclerView mRvBlack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    private BlackAdapter mBlackAdapter;
    private List<ContactInfo> mBeanList;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_black_list);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("黑名单");
        mBeanList = new ArrayList<>();
        mBlackAdapter = new BlackAdapter(mBeanList);
        RclViewHelp.initRcLmVertical(this, mRvBlack, mBlackAdapter);
        queryBlack();
    }

    @Override
    protected void onEventComing(EventCenter center) {
        if (center.getEventCode() == EventUtil.REFRESH_BLACK) {
            queryBlack();
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
    }


    /**
     * 黑名单
     */
    private void queryBlack() {
        Map<String, Object> map = new HashMap<>(1);
        ApiClient.requestNetHandle(this, AppConfig.BLACK_USER_LIST, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                BlackListInfo contactListInfo = FastJsonUtil.getObject(json, BlackListInfo.class);
                mBeanList.clear();
                if (contactListInfo.getData().size() > 0) {
                    mBeanList.addAll(contactListInfo.getData());
                }

                mBlackAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }
}
