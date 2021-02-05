package com.zds.base.base;

/**
 * Created by xgp on 2018/1/12.
 */

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zds.base.R;
import com.zds.base.util.SizeUtils;


/**
 * on 2019/8/9 11:06  xgp
 * 对话框基类
 */

public abstract class BaseDialog extends Dialog implements View.OnClickListener {

    protected Activity mActivity;

    public BaseDialog(Activity activity) {
        super(activity, R.style.BLCommonDialog);
        mActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initLayoutParams();
        initCancelEvent();
        initView();
        initEventAndData();
        //动态设置dialog的高度
        /*setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Window window = getWindow();
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                //layoutParams.width =  getWidth();
                int height = window.getDecorView().getMeasuredHeight();
                if(height > 0.68 * DeviceUtils.getScreenHeight()){
                    layoutParams.height = (int) (0.68 * DeviceUtils.getScreenHeight());
                }
                window.setAttributes(layoutParams);
            }
        });*/
    }

    protected void initLayoutParams(){
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        //注：1.根布局设置为match_parent,没有设置p.width，则dialog宽度为屏幕宽度-固定值,并居中显示
        //2.根布局设置为具体值200dp,没有设置p.width,则dialog宽度为200dp,并居中显示
        //3.根布局设置为match_parent,并设置p.width,则dialog宽度为p.width,并居中显示
        //4.根布局设置为具体值200dp,并设置p.width,则dialog宽度为p.width,并居中显示,但右边有透明部分，看起来并不居中
        layoutParams.width =  getWidth();
        window.setAttributes(layoutParams);
        window.setGravity(Gravity.CENTER);
    }

    /**
     * 获取当前Dialog的宽度
     * @return
     */
    protected int getWidth() {
        DisplayMetrics metric = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels - getMinusWidth();
    }

    private void initCancelEvent() {
        setCancelable(isCancelable());
        setCanceledOnTouchOutside(isCanceledOnTouchOutside());
    }

    /**
     * 获取屏幕宽度要减去的宽度值
     * @return
     */
    protected int getMinusWidth(){
        return SizeUtils.dp2px(60);
    }

    /**
     * 点击返回键是否可取消Dialog
     * @return 返回true，表示点击返回键可取消Dialog。默认可取消Dialog。
     */
    protected boolean isCancelable() {
        return true;
    }

    /**
     * 点击Dialog外部是否可取消Dialog
     * @return 返回true，表示点击Dialog外部可取消Dialog。默认可取消Dialog。
     */
    protected boolean isCanceledOnTouchOutside() {
        return true;
    }

    /**
     * 获取布局文件的资源id，用作Dialog中setContentView()方法的参数
     *
     * @return 布局文件的资源id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图(用于完成findViewById的操作)
     */
    protected abstract void initView();

    /**
     * 初始化事件和数据,调用在onCreate方法中
     */
    protected abstract void initEventAndData();

    /**
     * 处理控件的单击事件(Dialog中所有控件的单击事件都可以在此方法中处理)
     * 调用该方法的前提是setOnClickListener(必须传递this)
     * @param v 被点击的控件View对象
     */
    @Override
    public void onClick(View v) {

    }

}

