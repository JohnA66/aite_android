package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.UngetRedAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.model.UnGetRedInfo;
import com.zds.base.json.FastJsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @author lhb
 * 未领取红包
 */
public class UnGetRedActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rv_un_red)
    RecyclerView mRvUnRed;

    private List<UnGetRedInfo.DataBean> mRedInfoList;
    private UngetRedAdapter mUngetRedAdapter;
    private int page = 1;
    private String groupId;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_unget_red);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("未领取红包");
        mRedInfoList = new ArrayList<>();
        mUngetRedAdapter = new UngetRedAdapter(mRedInfoList);
        RclViewHelp.initRcLmVertical(this, mRvUnRed, mUngetRedAdapter);
        mUngetRedAdapter.setOnLoadMoreListener(() -> {
            page++;
            queryRed();
        }, mRvUnRed);
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        groupId = extras.getString("groupId");
        queryRed();
    }

    /**
     * 获取未领取红包
     */
    private void queryRed() {
        Map<String, Object> map = new HashMap<>(3);
        map.put("pageNum", page);
        map.put("pageSize", 15);
        map.put("groupId", groupId);

        ApiClient.requestNetHandle(this, AppConfig.NO_RED_PACK_RECORD, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                UnGetRedInfo info = FastJsonUtil.getObject(json, UnGetRedInfo.class);
                if (info.getData() != null && info.getData().size() > 0) {
                    mRedInfoList.addAll(info.getData());
                    mUngetRedAdapter.notifyDataSetChanged();
                    mUngetRedAdapter.loadMoreComplete();
                } else {
                    mUngetRedAdapter.loadMoreEnd(true);
                }
            }

            @Override
            public void onFailure(String msg) {
                mUngetRedAdapter.loadMoreFail();
            }
        });


    }
}
