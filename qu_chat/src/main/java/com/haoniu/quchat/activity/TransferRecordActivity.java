package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.TransferAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.TransferRecordInfo;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.zds.base.json.FastJsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lhb
 * 转账记录
 */
public class TransferRecordActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_no_data)
    TextView mTvNoData;

    private TransferAdapter mTransferAdapter;
    private List<TransferRecordInfo> mRecordInfoList = new ArrayList<>();

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_transfer_record);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("转账记录");
        mTransferAdapter = new TransferAdapter(mRecordInfoList);
        RclViewHelp.initRcLmVertical(this, mRecyclerView, mTransferAdapter);
        transferRecord();
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    /**
     * 转账记录
     */
    private void transferRecord() {
        Map<String, Object> map = new HashMap<>(1);
        ApiClient.requestNetHandle(this, AppConfig.TRANSFER_RECORD, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (FastJsonUtil.getList(json, TransferRecordInfo.class) != null && FastJsonUtil.getList(json, TransferRecordInfo.class).size() > 0) {
                    mRecordInfoList.addAll(FastJsonUtil.getList(json, TransferRecordInfo.class));
                    mTransferAdapter.notifyDataSetChanged();
                } else {
                    mTvNoData.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
