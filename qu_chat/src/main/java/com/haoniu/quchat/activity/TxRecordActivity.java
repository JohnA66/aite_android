package com.haoniu.quchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haoniu.quchat.adapter.RechargeAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.RechargeRecordInfo;
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

/**
 * @author lhb
 * 提现记录
 */
public class TxRecordActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rv_red_record)
    RecyclerView mRvRedRecord;
    private List<RechargeRecordInfo.DataBean> mRecordInfoList = new ArrayList<>();
    private RechargeAdapter mRechargeAdapter;
    private int page = 1;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_red_record);
    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("提现记录");
        mRechargeAdapter = new RechargeAdapter(mRecordInfoList);
        RclViewHelp.initRcLmVertical(this, mRvRedRecord, mRechargeAdapter);
        mRechargeAdapter.openLoadAnimation();
        mRechargeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                queryTxRecord();
            }
        },mRvRedRecord);
        mRechargeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                withdraw(mRecordInfoList.get(position).getWithdrawId());
            }
        });
        queryTxRecord();
    }

    private boolean isRequest;
    /**
     * 提现
     */
    private void withdraw(String id){
        if (isRequest){
            toast("加载中，请勿重复提交");
            return;
        }
        isRequest=true;
        Map<String,Object> map =new HashMap<>();
        map.put("walletWithdrawId",id);
        ApiClient.requestNetHandle(this, AppConfig.afterWithdraw, "请求中...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                startActivity(new Intent(TxRecordActivity.this,WebViewActivity.class).putExtra("url",json).putExtra("title","提现"));
            }

            @Override
            public void onFinsh() {
                super.onFinsh();
                isRequest=false;
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }
    @Override
    protected void onEventComing(EventCenter center) {
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
    }


    /**
     * 查询提现记录
     */
    private void queryTxRecord() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("pageNum", page);
        map.put("pageSize", 15);
        ApiClient.requestNetHandle(this, AppConfig.GET_WITHDRAAW_RECORD, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                RechargeRecordInfo info = FastJsonUtil.getObject(json, RechargeRecordInfo.class);
                if (info.getData() != null && info.getData().size() > 0) {
                    mRecordInfoList.addAll(info.getData());
                    mRechargeAdapter.notifyDataSetChanged();
                    mRechargeAdapter.loadMoreComplete();
                } else {
                    mRechargeAdapter.loadMoreEnd(true);
                }
            }
            @Override
            public void onFailure(String msg) {
                mRechargeAdapter.loadMoreFail();
            }
        });


    }


}
