package com.haoniu.quchat.activity.custom1;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haoniu.quchat.activity.fragment.FindFragmentNewAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.Custom1BaseInfo;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.NewFindInfo;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.ResultListener;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.json.FastJsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.haoniu.quchat.http.AppConfig.CUSTOM_QUESTIONS;

public class Custom1Activity extends BaseActivity {

    private List<Custom1BaseInfo> mNewInfoList = new ArrayList<>();
    private Custom1Adapter mAdapter;
    private RecyclerView mCustoms;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_custom1);
    }

    @Override
    protected void initLogic() {
        setTitle("客服1");
        mCustoms = findViewById(R.id.recycle_view);
        mCustoms.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new Custom1Adapter(mNewInfoList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Custom1BaseInfo info = mAdapter.getItem(i);
                if (info == null) return;
                boolean isOpen = info.isOpen();
                info.setOpen(!isOpen);
                mAdapter.notifyDataSetChanged();
            }
        });
        mCustoms.setAdapter(mAdapter);

        ApiClient.requestNetHandle(this,CUSTOM_QUESTIONS,"",new HashMap<>(),new ResultListener() {

            @Override
            public void onSuccess(String json, String msg) {
                Log.e("Custom1Activity","json="+json);
                Log.e("Custom1Activity","msg="+msg);
                if (json != null && json.length() > 0) {
                    List<Custom1BaseInfo> findInfos = FastJsonUtil.getList(json,Custom1BaseInfo.class);
                    if (null != findInfos && findInfos.size() > 0) {
                        if (mNewInfoList.size() >0) {
                            mNewInfoList.clear();
                        }
                        mNewInfoList.addAll(findInfos);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String msg) {
                Log.e("Custom1Activity",msg);
                ToastUtil.toast(msg);
            }
        });

    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}
