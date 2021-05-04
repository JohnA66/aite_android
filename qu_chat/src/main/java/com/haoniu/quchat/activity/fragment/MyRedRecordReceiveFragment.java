package com.haoniu.quchat.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haoniu.quchat.activity.RedPacketDetailActivity;
import com.haoniu.quchat.adapter.MyRedAdapter;
import com.haoniu.quchat.base.MyBaseFragment;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.model.MyRedInfo;
import com.zds.base.ImageLoad.GlideUtils;
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
 * 我的红包-收到
 */
public class MyRedRecordReceiveFragment extends MyBaseFragment {

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
    @BindView(R.id.img_head)
    ImageView mImgHead;
    Unbinder unbinder1;
    /**
     * type 100-发红包 101-领取红包
     */
    private String type = "101";

    private MyRedAdapter mMyRedAdapter;
    private List<MyRedInfo.DataBean> mBeanList;
    private int page = 1;


    @Override
    protected int setContentView() {
        return R.layout.fragment_my_red_receive;
    }

    @Override
    protected void initData() {
        super.initData();
        GlideUtils.GlideLoadCircleErrorImageUtils(mContext, UserComm.getUserInfo().getUserHead(), mImgHead, R.mipmap.img_default_avatar);
        mBeanList = new ArrayList<>();
        mMyRedAdapter = new MyRedAdapter(mBeanList, type);
        RclViewHelp.initRcLmVertical(mContext, mRvRed, mMyRedAdapter);
        mMyRedAdapter.setOnLoadMoreListener(() -> {
            page++;
            queryRed();
        }, mRvRed);
        queryRedTotal();
        queryRed();

        mMyRedAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyRedInfo.DataBean dataBean = mMyRedAdapter.getData().get(position);
                startActivity(new Intent(getActivity(), RedPacketDetailActivity.class)
                        .putExtra("rid", dataBean.getRedPacketId())
                        .putExtra("fromRecord", true)
                        .putExtra("type", "0"));

            }
        });
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

        ApiClient.requestNetHandle(mContext, AppConfig.RED_PACK_TOTAL, "", map, new ResultListener() {
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
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
