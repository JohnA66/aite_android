package com.haoniu.quchat.help;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by zlw on 2018/09/14/上午 9:21
 * RecyclerView 帮助类
 */

public class RclViewHelp {

    /**
     * 解决RecyclerView与ScrollView滑动不流畅问题
     *
     * @param view
     */
    public static void recycleviewAndScrollView(View view) {
        RecyclerView RecyclerView = (RecyclerView) view;
        RecyclerView.setHasFixedSize(true);
        RecyclerView.setNestedScrollingEnabled(false);
    }

    public interface OnMyRefreshListener {
        void onRefresh(int staust);
    }

    /**
     * 垂直
     *
     * @param context
     * @param rclView
     * @param srLayoutView
     * @param adapter
     * @param listener
     * @param <T>
     */
    public static <T extends RecyclerView.Adapter> void initRcLmVertical(Context context, View rclView, SmartRefreshLayout srLayoutView, T adapter, final OnMyRefreshListener listener) {
        RecyclerView view = (RecyclerView) rclView;
        LinearLayoutManager lm = new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        view.setLayoutManager(lm);
        view.setAdapter(adapter);
        srLayoutView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                listener.onRefresh(0);
            }
        });
        srLayoutView.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                listener.onRefresh(1);
            }
        });
    }

    /**
     * 垂直
     *
     * @param context
     * @param rclView
     * @param adapter
     * @param <T>
     */
    public static <T extends RecyclerView.Adapter> void initRcLmVertical(Context context, View rclView, T adapter) {
        LinearLayoutManager lm = new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        if (rclView instanceof RecyclerView) {
            RecyclerView view = (RecyclerView) rclView;
            view.setLayoutManager(lm);
            view.setAdapter(adapter);
        } else {
            RecyclerView view = (RecyclerView) rclView;
            view.setLayoutManager(lm);
            view.setAdapter(adapter);
        }

    }


    /**
     * 垂直
     *
     * @param cotext
     * @param rclView
     * @param adapter
     * @param <T>
     */
    public static <T extends RecyclerView.Adapter> void initRcLmVerticalNoSpace(Context cotext, View rclView, T adapter) {
        LinearLayoutManager lm = new LinearLayoutManager(cotext);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        if (rclView instanceof RecyclerView) {
            RecyclerView view = (RecyclerView) rclView;
            view.setLayoutManager(lm);
            view.setAdapter(adapter);
        } else {
            RecyclerView view = (RecyclerView) rclView;
            view.setLayoutManager(lm);
            view.setAdapter(adapter);
        }

    }

    /**
     * 水平
     *
     * @param context
     * @param rclView
     * @param adapter
     * @param <T>
     */
    public static <T extends RecyclerView.Adapter> void initRcLmLevel(Context context, View rclView, T adapter) {
        LinearLayoutManager lm = new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (rclView instanceof RecyclerView) {
            RecyclerView view = (RecyclerView) rclView;
            view.setLayoutManager(lm);
            view.setAdapter(adapter);
        } else {
            RecyclerView view = (RecyclerView) rclView;
            view.setLayoutManager(lm);
            view.setAdapter(adapter);
        }
    }

    /**
     * 网格
     *
     * @param context
     * @param rclView
     * @param srLayoutView
     * @param adapter
     * @param listener
     * @param <T>
     */
    public static <T extends RecyclerView.Adapter> void initRcLmGrid(Context context, View rclView, SmartRefreshLayout srLayoutView, int gridNum, T adapter, final OnMyRefreshListener listener) {
        RecyclerView view = (RecyclerView) rclView;
        GridLayoutManager lm = new GridLayoutManager(context, gridNum);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        view.setLayoutManager(lm);
        view.setAdapter(adapter);
        srLayoutView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                listener.onRefresh(0);
            }
        });
        srLayoutView.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                listener.onRefresh(1);
            }
        });
    }


    /**
     * 网格
     *
     * @param context
     * @param rclView
     * @param gridNum
     * @param adapter
     * @param <T>
     */
    public static <T extends RecyclerView.Adapter> void initRcLmGrid(Context context, View rclView, int gridNum, T adapter) {
        GridLayoutManager lm = new GridLayoutManager(context, gridNum);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        if (rclView instanceof RecyclerView) {
            RecyclerView view = (RecyclerView) rclView;
            view.setLayoutManager(lm);
            view.setAdapter(adapter);
        } else {
            RecyclerView view = (RecyclerView) rclView;
            view.setLayoutManager(lm);
            view.setAdapter(adapter);
        }
    }

    /**
     * 网格
     *
     * @param context
     * @param rclView
     * @param gridNum
     * @param adapter
     * @param <T>
     */
    public static <T extends RecyclerView.Adapter> void initRcLmGridNoDecoration(Activity context, View rclView, int gridNum, T adapter) {
        GridLayoutManager lm = new GridLayoutManager(context, gridNum);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        if (rclView instanceof RecyclerView) {
            RecyclerView view = (RecyclerView) rclView;
            view.setLayoutManager(lm);
            view.setAdapter(adapter);
        } else {
            RecyclerView view = (RecyclerView) rclView;
            view.setLayoutManager(lm);
            view.setAdapter(adapter);
        }
    }

    /**
     * 网格
     *
     * @param context
     * @param rclView
     * @param gridNum
     * @param adapter
     * @param <T>
     */
    public static <T extends RecyclerView.Adapter> void initRcLmGridHorizontal(Context context, View rclView, int gridNum, T adapter) {
        GridLayoutManager lm = new GridLayoutManager(context, gridNum);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (rclView instanceof RecyclerView) {
            RecyclerView view = (RecyclerView) rclView;
            view.setLayoutManager(lm);
            view.setAdapter(adapter);
        } else {
            RecyclerView view = (RecyclerView) rclView;
            view.setLayoutManager(lm);
            view.setAdapter(adapter);
        }
    }


    private static int dp2px(Activity activity, float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, activity.getResources().getDisplayMetrics());
    }

    /**
     * 瀑布流
     *
     * @param context
     * @param rclView
     * @param spanCount
     * @param adapter
     * @param <T>
     */
    public static <T extends RecyclerView.Adapter> void initRcStaggeredGrid(Context context, View rclView, int spanCount, T adapter) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        if (rclView instanceof RecyclerView) {
            RecyclerView view = (RecyclerView) rclView;
            view.setLayoutManager(staggeredGridLayoutManager);
            view.setAdapter(adapter);
        } else {
            RecyclerView view = (RecyclerView) rclView;
            view.setLayoutManager(staggeredGridLayoutManager);
            view.setAdapter(adapter);
        }

    }

    /**
     * 瀑布流刷新
     *
     * @param context
     * @param rclView
     * @param spanCount
     * @param srLayoutView
     * @param adapter
     * @param listener
     * @param <T>
     */
    public static <T extends RecyclerView.Adapter> void initRcStaggeredGrid(Context context, View rclView, int spanCount, SmartRefreshLayout srLayoutView, T adapter, final OnMyRefreshListener listener) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        if (rclView instanceof RecyclerView) {
            RecyclerView view = (RecyclerView) rclView;
            view.setLayoutManager(staggeredGridLayoutManager);
            view.setAdapter(adapter);
        } else {
            RecyclerView view = (RecyclerView) rclView;
            view.setLayoutManager(staggeredGridLayoutManager);
            view.setAdapter(adapter);
        }
        srLayoutView.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                listener.onRefresh(1);
            }
        });
        srLayoutView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                listener.onRefresh(0);
            }
        });
    }
}
