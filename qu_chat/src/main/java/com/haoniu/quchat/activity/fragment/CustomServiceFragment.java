package com.haoniu.quchat.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.aite.chat.R;
import com.haoniu.quchat.activity.ChatActivity;
import com.haoniu.quchat.activity.custom1.Custom1Activity;
import com.haoniu.quchat.adapter.ServiceAdapter;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.EaseConstant;
import com.haoniu.quchat.base.MyHelper;
import com.haoniu.quchat.domain.EaseUser;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.entity.ServiceInfo;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.zds.base.Toast.ToastUtil;
import com.zds.base.json.FastJsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author lhb
 * 客服fragment
 */
public class CustomServiceFragment extends EaseBaseFragment {

    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.custom1)
    RelativeLayout mCuston1;

    Unbinder unbinder;

    ServiceAdapter mServiceAdapter;
    List<ServiceInfo.DataBean> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_service, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        mToolbarTitle.setText("客服列表");
        list = new ArrayList<>();
        mServiceAdapter = new ServiceAdapter(list);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleView.setAdapter(mServiceAdapter);
        mServiceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // start chat acitivity
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                ServiceInfo.DataBean dataBean = list.get(position);
                intent.putExtra(Constant.EXTRA_USER_ID, dataBean.getUserId() + Constant.ID_REDPROJECT)
                        .putExtra(Constant.NICKNAME, dataBean.getNickName())
                        .putExtra(Constant.CUSTOM_KF,true)
                        .putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                startActivity(intent);
            }
        });
        getService();
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

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    /**
     * 客服列表
     */
    private void getService() {
        Map<String, Object> map = new HashMap<>(3);
        map.put("pageNum", "1");
        map.put("pageSize", "15");
        ApiClient.requestNetHandle(getActivity(), AppConfig.CUSTOM_LIST, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (null != json && json.length() > 0) {
                    ServiceInfo info = FastJsonUtil.getObject(json, ServiceInfo.class);
                    if (info.getData() != null && info.getData().size() > 0) {
                        list.addAll(info.getData());
                        mServiceAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.toast(msg);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.img_left_back,R.id.custom1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_left_back:
                getActivity().finish();
                break;
            case R.id.custom1:
                Intent intent = new Intent(getActivity(), Custom1Activity.class);
                getActivity().startActivity(intent);
                break;
            default:
                break;
        }
    }
}
