package com.haoniu.quchat.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.aite.chat.R;
import com.zds.base.util.Utils;


public class PopWinShare extends PopupWindow {
    private View mainView;
    private LinearLayout layoutGroup, layoutAddFirend, layoutSaoyisao, llMyQr, llTop;

    public PopWinShare(Activity paramActivity, View.OnClickListener paramOnClickListener, int paramInt1, int paramInt2) {
        super(paramActivity);
        //窗口布局
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.popwin_share, null);
        layoutGroup = ((LinearLayout) mainView.findViewById(R.id.layout_group));
        layoutAddFirend = (LinearLayout) mainView.findViewById(R.id.layout_add_firend);
        layoutSaoyisao = (LinearLayout) mainView.findViewById(R.id.layout_saoyisao);
        llMyQr = (LinearLayout) mainView.findViewById(R.id.layout_my_qr);
        llTop = (LinearLayout) mainView.findViewById(R.id.ll_top);

        //设置每个子布局的事件监听器
        if (paramOnClickListener != null) {
            layoutGroup.setOnClickListener(paramOnClickListener);
            layoutAddFirend.setOnClickListener(paramOnClickListener);
            layoutSaoyisao.setOnClickListener(paramOnClickListener);
            llMyQr.setOnClickListener(paramOnClickListener);

        }
        setContentView(mainView);
        //设置宽度
        setWidth(paramInt1);
        //设置高度
        setHeight(paramInt2);
        //设置显示隐藏动画
//        setAnimationStyle(R.style.AnimTools);
        //设置背景透明
        setBackgroundDrawable(ContextCompat.getDrawable(Utils.getContext(), R.color.trans_half));

        llTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (this != null && isShowing()) {
                    dismiss();
                }
            }
        });
    }
}