package com.haoniu.quchat.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.FindAdapter;
import com.haoniu.quchat.base.MyBaseFragment;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.FindInfo;
import com.haoniu.quchat.entity.NewFindInfo;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zds.base.json.FastJsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * @author lhb
 * 发现
 */
public class FindFragment extends MyBaseFragment {
    @BindView(R.id.rv_find)
    RecyclerView mRvFind;
    @BindView(R.id.smart)
    SmartRefreshLayout mSmart;
    Unbinder unbinder;

//    private List<FindInfo.DataBean> mInfoList = new ArrayList<>();
    private List<NewFindInfo.NewFindInfoItem> mNewInfoList = new ArrayList<>();
//    private FindAdapter mFindAdapter;
    private FindFragmentNewAdapter mAdapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mSmart.setEnableLoadmore(false);
        mSmart.setOnRefreshListener(refreshlayout -> {
            queryFind();
        });

//        mFindAdapter = new FindAdapter(mInfoList);
        mAdapter = new FindFragmentNewAdapter(mNewInfoList);
        RclViewHelp.initRcLmVertical(mContext, mRvFind, mAdapter);

        mSmart.autoRefresh();
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }


    private void queryFind() {
        Map<String, Object> map1 = new HashMap<>(1);
        ApiClient.requestNetHandle(mContext, AppConfig.GET_FIND_LIST_NEW, "", map1, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (null != json && json.length() > 0) {
                    List<NewFindInfo.NewFindInfoItem> findInfos = FastJsonUtil.getList(json,NewFindInfo.NewFindInfoItem.class);
                    if (findInfos != null && findInfos.size() > 0) {
                        if (mNewInfoList.size() > 0) {
                            mNewInfoList.clear();
                        }
                        mNewInfoList.addAll(findInfos);
                    }
                }
                mAdapter.notifyDataSetChanged();
                if (null != mSmart) {
                    mSmart.finishRefresh();
                }
            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
                if (null != mSmart) {
                    mSmart.finishRefresh();
                }
            }
        });

//        Map<String, Object> map = new HashMap<>(1);
//        ApiClient.requestNetHandle(mContext, AppConfig.GET_FIND_LIST, "", map, new ResultListener() {
//            @Override
//            public void onSuccess(String json, String msg) {
//                if (null != json && json.length() > 0) {
//                    FindInfo info = FastJsonUtil.getObject(json, FindInfo.class);
//                    if (info.getData() != null && info.getData().size() > 0) {
//                        if (mInfoList.size() > 0) {
//                            mInfoList.clear();
//                        }
//                        mInfoList.addAll(info.getData());
//                    }
//                }
//
//                mFindAdapter.notifyDataSetChanged();
//
//                if (null != mSmart) {
//                    mSmart.finishRefresh();
//                }
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                toast(msg);
//                if (null != mSmart) {
//                    mSmart.finishRefresh();
//                }
//
//            }
//        });
    }
}
