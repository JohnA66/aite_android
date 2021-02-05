package com.haoniu.quchat.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aite.chat.R;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.widget.TitleBar;
import com.zds.base.util.BarUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public abstract class EaseBaseFragment extends Fragment {
    protected TitleBar titleBar;
    protected InputMethodManager inputMethodManager;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //noinspection ConstantConditions
        try {
            titleBar = (TitleBar) getView().findViewById(R.id.title_bar);
        } catch (Exception e) {}
        Bundle extras = getArguments();
        if (null != extras) {
            getBundleExtras(extras);
        }
        EventBus.getDefault().register(this);
        initLogic();
    }

    public void showTitleBar() {
        if (titleBar != null) {
            titleBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideTitleBar() {
        if (titleBar != null) {
            titleBar.setVisibility(View.GONE);
        }
    }

    public void setTitles(String title) {
        if (titleBar != null && title != null) {
            titleBar.setTitle(title);
            titleBar.setVisibility(View.VISIBLE);
        }
    }

    protected void initBar() {
        View bar = getView().findViewById(R.id.bar);
        try {
            if (bar != null) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bar.getLayoutParams();
                params.height = BarUtils.getStatusBarHeight(getActivity());
                bar.setLayoutParams(params);
            }
        } catch (Exception e) {
            if (bar != null) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bar.getLayoutParams();
                params.height = BarUtils.getStatusBarHeight(getActivity());
                bar.setLayoutParams(params);
            }
        }

    }

    /**
     * 初始化逻辑
     */
    protected abstract void initLogic();

    /**
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
    protected abstract void onEventComing(EventCenter center);

    /**
     * EventBus接收消息
     *
     * @param center 消息接收
     */
    @Subscribe
    public void onEventMainThread(EventCenter center) {

        if (null != center) {
            onEventComing(center);
        }

    }

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    protected abstract void getBundleExtras(Bundle extras);

    /**
     * 吐司
     *
     * @param msg
     */
    public void toast(String msg) {
        if (msg != null && !"".equals(msg)) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void startActivity(Class<?> cls) {
        startActivity(new Intent(getContext(), cls));
    }

    public void startActivity(Class<?> cls,Bundle bundle) {
        startActivity(new Intent(getContext(), cls).putExtras(bundle));
    }
}
