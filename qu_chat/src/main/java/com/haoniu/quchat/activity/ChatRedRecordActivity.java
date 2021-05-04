package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haoniu.quchat.adapter.ChatRedRecordAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.model.MyRedInfo;
import com.zds.base.json.FastJsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @author lhb
 * 聊天红包记录
 */
public class ChatRedRecordActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rv_red_record)
    RecyclerView mRvRedRecord;

    private ChatRedRecordAdapter mRedRecordAdapter;
    private List<MyRedInfo.DataBean> mPacketInfoList = new ArrayList<>();
    private int page = 1;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_chat_red_record);

    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("红包记录");
        mRedRecordAdapter = new ChatRedRecordAdapter(mPacketInfoList);
        RclViewHelp.initRcLmVertical(this, mRvRedRecord, mRedRecordAdapter);

        mRedRecordAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                queryRed();
            }
        });


        queryRed();
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }


    /**
     * 查询红包
     */
    private void queryRed() {
        Map<String, Object> map = new HashMap<>(3);
        map.put("type", "");
        map.put("pageNum", page);
        map.put("pageSize", 15);

        ApiClient.requestNetHandle(this, AppConfig.RED_PACK_RECORD, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                MyRedInfo info = FastJsonUtil.getObject(json, MyRedInfo.class);
                if (info.getData() != null && info.getData().size() > 0) {
                    mPacketInfoList.addAll(info.getData());
                    mRedRecordAdapter.notifyDataSetChanged();
                    mRedRecordAdapter.loadMoreComplete();
                } else {
                    mRedRecordAdapter.loadMoreEnd(true);
                }
            }

            @Override
            public void onFailure(String msg) {
                mRedRecordAdapter.loadMoreFail();
            }
        });


    }


}
