package com.haoniu.quchat.activity.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.MyRedAdapter;
import com.haoniu.quchat.base.MyBaseFragment;
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
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author lhb
 * 我的红包
 */
public class MyRedFragment extends MyBaseFragment {

    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_redpack_num)
    TextView mTvRedpackNum;
    @BindView(R.id.tv_shouqi)
    TextView mTvShouqi;
    @BindView(R.id.rv_red)
    RecyclerView mRvRed;
    @BindView(R.id.tv_money_s)
    TextView mTvMoneyS;
    @BindView(R.id.rv_shouqi)
    RelativeLayout mRvShouqi;
    Unbinder unbinder;
    /**
     * type 100-发红包 101-领取红包
     */
    private String type = "101";
    private final static String TYPE = "type";

    private MyRedAdapter mMyRedAdapter;
    private List<MyRedInfo.DataBean> mBeanList;
    private int page = 1;


    public static final MyRedFragment newInstance(String type) {
        MyRedFragment myRedFragment = new MyRedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, type);
        myRedFragment.setArguments(bundle);
        return myRedFragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_my_red;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        type = bundle.getString(TYPE);
        if (type.equals("100")) {
            mTvMoneyS.setText("共发出（元）");
            mRvShouqi.setVisibility(View.GONE);
        } else {
            mTvMoneyS.setText("共收到（元）");
            mRvShouqi.setVisibility(View.VISIBLE);
        }
        mBeanList = new ArrayList<>();
        mMyRedAdapter = new MyRedAdapter(mBeanList,"");
        RclViewHelp.initRcLmVertical(mContext, mRvRed, mMyRedAdapter);
        mMyRedAdapter.setOnLoadMoreListener(() -> {
            page++;
            queryRed();
        }, mRvRed);
        queryRedTotal();
        queryRed();
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }


    /**
     * 查询红包
     */
    private void queryRed() {
        Map<String, Object> map = new HashMap<>(3);
        map.put("type", type);
        map.put("pageNum", page);
        map.put("pageSize", 15);

        ApiClient.requestNetHandle(mContext, AppConfig.RED_PACK_RECORD, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                MyRedInfo info = FastJsonUtil.getObject(json, MyRedInfo.class);
                if (info.getData() != null && info.getData().size() > 0) {
                    mBeanList.addAll(info.getData());
                    mMyRedAdapter.notifyDataSetChanged();
                    mMyRedAdapter.loadMoreComplete();
                } else {
                    mMyRedAdapter.loadMoreEnd(true);
                }
            }

            @Override
            public void onFailure(String msg) {
                mMyRedAdapter.loadMoreFail();
            }
        });


    }


    /**
     * 查询红包统计
     */
    private void queryRedTotal() {
        Map<String, Object> map = new HashMap<>(3);
        map.put("type", type);

        ApiClient.requestNetHandle(mContext, AppConfig.RED_PACK_TOTAL, "数据查询...", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                //totalRedPacketMoney 总红包金额; totalRedPacketAmount 总数量; totalLuckAmount 手气最佳数量(type=101的时候这个参数有zhi)
                // 总红包金额
                mTvMoney.setText(FastJsonUtil.getDouble(json, "totalRedPacketMoney") + "");
                //总数量
                mTvRedpackNum.setText(FastJsonUtil.getInt(json, "totalRedPacketAmount") + "");
                //手气最佳数量
                mTvShouqi.setText(FastJsonUtil.getInt(json, "totalLuckAmount") + "");

            }

            @Override
            public void onFailure(String msg) {
                mMyRedAdapter.loadMoreFail();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
