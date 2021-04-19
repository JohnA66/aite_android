package com.zds.base.base;

import android.content.Context;
import android.view.View;



import razerdp.basepopup.BasePopupWindow;

/**
 * 弹窗基类
 * @author xgp
 * @description:
 * @date :2019/8/9 17:07
 *
 * todo BasePop生命周期  构造函数执行之后才会初始化成员变量
 */
public abstract class BasePop extends BasePopupWindow implements View.OnClickListener{

    //todo 可去掉 父类中提供getContenxt方法
    protected Context mContext;

    public BasePop(Context context) {
        super(context);//执行顺序1
        mContext = context;//执行顺序4
        initView();
        initEventAndData();
        //setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(getLayoutId());//执行顺序2
    }

    /**
     * 获取布局文件的资源id，用作PopupWindow中setContentView()方法的参数
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
     * 处理控件的单击事件(PopupWindow中所有控件的单击事件都可以在此方法中处理)
     * 调用该方法的前提是setOnClickListener(必须传递this)
     * @param v 被点击的控件View对象
     */
    @Override
    public void onClick(View v) {

    }
}

