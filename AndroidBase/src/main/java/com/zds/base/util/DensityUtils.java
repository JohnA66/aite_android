package com.zds.base.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

public class DensityUtils {

    /**
     * 获得屏幕的宽度 单位px
     *
     * @param context
     * @return
     */
    public static final float getHeightInPx(Context context) {
        final float height = context.getResources().getDisplayMetrics().heightPixels;
        return height;
    }

    /**
     * 获得屏幕的高度 单位px
     *
     * @param context
     * @return
     */
    public static final float getWidthInPx(Context context) {
        final float width = context.getResources().getDisplayMetrics().widthPixels;
        return width;
    }

    /**
     * 获得屏幕的高度 单位 dp
     *
     * @param context
     * @return
     */
    public static final int getHeightInDp(Context context) {
        final float height = context.getResources().getDisplayMetrics().heightPixels;
        int heightInDp = px2dip(context, height);
        return heightInDp;
    }

    /**
     * 获得屏幕的宽度 单位dp
     *
     * @param context
     * @return
     */
    public static final int getWidthInDp(Context context) {
        final float height = context.getResources().getDisplayMetrics().heightPixels;
        int widthInDp = px2dip(context, height);
        return widthInDp;
    }

    /**
     * 将单位dp的值转换为单位px的值
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 将单位px的值转换为单位dp的值
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将单位px的值转换为单位sp的值
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将单位sp的值转换为单位px的值
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }


    /**
     * 获得状态栏高度 单位px
     *
     * @param context
     * @return
     */
    public static int statusBarHeight(Context context) {
        Rect frame = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }


    /**
     * 获得状态栏高度 单位px
     *
     * @param context
     * @return
     */
    public static int statusBarHeight2(Context context) {
        Class<?> c = null;

        Object obj = null;

        Field field = null;

        int x = 0, sbar = 0;
        try {

            c = Class.forName("com.android.internal.R$dimen");

            obj = c.newInstance();

            field = c.getField("status_bar_height");

            x = Integer.parseInt(field.get(obj).toString());

            sbar = context.getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {

            e1.printStackTrace();

        }

        return sbar;
    }

    /**
     * 获得控件的宽高
     *
     * @param v
     * @return int[0] 宽  int[1] 高
     */
    public static int[] getViewSize(View view) {
        LayoutParams rlp = view.getLayoutParams();
        int childEndWidth = rlp.width;
        int childEndHeight = rlp.height;
        if (childEndWidth <= 0 || childEndHeight <= 0) {
            view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            childEndWidth = view.getMeasuredWidth();
            childEndHeight = view.getMeasuredHeight();
        }

        return new int[]{childEndWidth, childEndHeight};
    }


    /**
     * 关闭软键盘
     */
    public static void closeKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            view.clearFocus();
        }
    }


    /**
     * 打开软键盘
     *
     * @param view
     */
    public static void openKeyboard(View view) {
        if (view != null) {
            view.requestFocus();
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }
}
