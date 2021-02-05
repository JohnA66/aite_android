package com.haoniu.quchat.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.WindowManager;
import android.widget.TextView;

import com.aite.chat.R;

/**
 * 作   者：赵大帅
 * 描   述:
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/11/16 17:19
 * 更新日期: 2017/11/16
 */
public class Loading_view extends Dialog {
    public Loading_view(Context context) {
        super(context, R.style.CustomDialog);
        init();
    }

    private TextView mTextView;


    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.app_dialog_loading);//loading的xml文件
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

    }

    public void setMessage(String message) {
        mTextView = findViewById(R.id.tv_message);
        mTextView.setText(message);
    }

    @Override
    public void show() {//开启
        super.show();
    }

    @Override
    public void dismiss() {//关闭
        super.dismiss();
    }
}