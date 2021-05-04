package com.haoniu.quchat.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.haoniu.quchat.entity.EventCenter;
import com.zds.base.json.FastJsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zlw on 2018/04/23/下午 4:24
 */

public abstract class MyBaseFragment extends Fragment {

    private View view = null;
    /**
     * @param isRootView 判断view是否绘制完成，避免空指针异常
     * @param isFirstData 是否第一次加载数据
     * @param isVisibleToUser 判断fragment是否可见
     */
    private boolean isRootView = false;
    private boolean isFirstData = false;
    private boolean isVisibleToUser = false;
    protected Context mContext;
    Unbinder unbinder;

    public int page = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        initBase();
        initContext();
        Bundle bundle = getArguments();
        if (view == null) {
            view = View.inflate(getContext(), setContentView(), null);
        }

        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

//        view = inflater.inflate(setContentView(), container, false);
/**
 * 里view已经绘制完成
 */
        isRootView = true;
        EventBus.getDefault().register(this);
        unbinder = ButterKnife.bind(this, view);
        initView(savedInstanceState);
        if (bundle != null) {
            initBundle(bundle);
        }
        initData();
        initListener();
        return view;
    }


    protected void initBundle(Bundle bundle) {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initLjzData();
    }

    protected void initContext() {

    }

    protected void initListener() {
    }

    protected void initData() {

    }

    protected void initView(@Nullable Bundle savedInstanceState) {
    }

    protected void initBase() {
    }

    protected abstract int setContentView();


//    protected void initBar() {
//        View bar = view.findViewById(R.id.bar);
//        bar.setBackgroundColor(getResources().getColor(R.color.gray_text_color));
//        if (bar != null) {
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bar.getLayoutParams();
//            params.height = BarUtils.getStatusBarHeight(getActivity());
//            bar.setLayoutParams(params);
//        }
//    }


    /**
     * 吐司
     *
     * @param msg
     */
    public void toast(String msg) {
        if (msg != null && !msg.equals("")) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }

//    Loading_view dialog;
//
//    /**
//     * 显示加载框
//     */
//    public void showLoading() {
//        if (dialog != null && dialog.isShowing()) {
//            return;
//        }
//        dialog = new Loading_view(getContext());
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setMessage("请求网络中...");
//        dialog.show();
//    }

//    /**
//     * 显示加载框
//     *
//     * @param message
//     */
//    protected void showLoading(String message) {
//        if (dialog != null && dialog.isShowing()) {
//            return;
//        }
//        dialog = new Loading_view(getContext());
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setMessage(message);
//        dialog.show();
//    }

//    /**
//     * 隐藏加载框
//     */
//    protected void dismissLoading() {
//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
//        }
//    }


    public Map<String, Object> showMap(Map<String, Object> map) {
        Map<String, Object> showMap = new HashMap<>();
        showMap.put("SearchData", FastJsonUtil.toJSONString(map));
        return showMap;
    }

    public Map<String, Object> SwShowMap(Map<String, Object> map) {
        Map<String, Object> showMap = new HashMap<>();
        showMap.put("portal", FastJsonUtil.toJSONString(map));
        return showMap;
    }

    public String getPanda() {
        return "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524562998440&di=416ddee3492f3468c0e90bab604a0a0f&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D387591d7df62853586edda62f8861cb3%2Fe4dde71190ef76c604ca100e9716fdfaaf5167ff.jpg";
    }


    @Override
    public void onDestroyView() {
        if (view != null) {
            View oldView = (View) view.getParent();
            if (oldView != null) {
                ((ViewGroup) oldView).removeView(view);
            }
        }
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        initLjzData();
    }

    /**
     * 懒加载方法
     * 只有view初始化完成，并且是第一次加载数据和fragment当前可见才能加载数据
     */
    private void initLjzData() {
        if (isRootView && !isFirstData && isVisibleToUser) {
            isFirstData = true;
            onVisibleData();
        } else {
            onUnVisible();
        }
    }

    /**
     * 其他情况比如页面不可见时使用
     */
    protected void onUnVisible() {
    }

    /**
     * 抽象方法，加载数据
     */
    protected void onVisibleData() {
    }


    /**
     * 不带参数跳转
     *
     * @param context
     * @param activity
     */
    public void onMyClickToClass(Context context, Class<?> activity) {
        context.startActivity(new Intent(context, activity));
    }


    /**
     * 带参数跳转
     *
     * @param context
     * @param activity
     */
    public void onMyClickToClass(Context context, Class<?> activity, Bundle bundle) {
        context.startActivity(new Intent(context, activity).putExtras(bundle));
    }

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
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
    protected abstract void onEventComing(EventCenter center);

//    /**
//     * 消息
//     *
//     * @param massage
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(EventMassage massage) {
//        onMyEvent(massage);
//    }
//
//    protected void onMyEvent(EventMassage massage) {
//    }

//    /**
//     * 发送消息
//     *
//     * @param msg
//     */
//    public void sendMassage(String msg) {
//        EventMassage massage = new EventMassage();
//        massage.setTag(msg);
//        EventBus.getDefault().post(massage);
//    }

//    /**
//     * 发送消息
//     *
//     * @param msg
//     * @param t
//     * @param <T>
//     */
//    public <T> void sendMassage(String msg, T t) {
//        EventMassage massage = new EventMassage();
//        massage.setTag(msg);
//        massage.setData(t);
//        EventBus.getDefault().post(massage);
//    }

    protected void setStatusBar() {
        //5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getActivity().getWindow().getDecorView();

            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            //透明
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getActivity().getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        } else {
        }
        //android6.0以后可以对状态栏文字颜色和图标进行修改
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    protected InputMethodManager inputMethodManager;


    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

}
