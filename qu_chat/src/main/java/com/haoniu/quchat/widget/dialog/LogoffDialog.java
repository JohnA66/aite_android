package com.haoniu.quchat.widget.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.aite.chat.R;
import com.zds.base.base.BaseDialog;
import com.zds.base.util.RichTextUtils;

/**
 * 注销二次弹窗
 */
public class LogoffDialog extends BaseDialog {

    private TextView tv_title;
    private TextView tv_content;
    private TextView tv_cancel;
    private TextView tv_confirm;

    private OnConfirmClickListener mOnConfirmClickListener;
    private OnCancelClickListener mOnCancelClickListener;

    public LogoffDialog(Activity activity) {
        super(activity);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_logoff;
    }

    @Override
    protected void initView() {
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_confirm = findViewById(R.id.tv_confirm);
    }

    @Override
    protected void initEventAndData() {
        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);

        RichTextUtils.getBuilder("艾特平台将会")
                .append("永久清空")
                .setForegroundColor(Color.parseColor("#EE4444"))
                .append("您的账号信息， 包括但不限于 个人资料、聊天信息、消费信息")
                .into(tv_content);
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    public void setContent(String content) {
        tv_content.setText(content);
    }

    public void setButtonText(String leftButtonText, String rightButtonText) {
        tv_cancel.setText(leftButtonText);
        tv_confirm.setText(rightButtonText);
    }

    public void setButtonVisible(boolean leftButton, boolean rightButton) {
        tv_cancel.setVisibility(leftButton ? View.VISIBLE : View.GONE);
        tv_confirm.setVisibility(rightButton ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                if (mOnCancelClickListener != null) {
                    mOnCancelClickListener.onCancelClick(tv_cancel);
                }
                break;
            case R.id.tv_confirm:
                dismiss();
                if (mOnConfirmClickListener != null) {
                    mOnConfirmClickListener.onConfirmClick(tv_confirm);
                }
                break;
        }
    }

    public void setOnConfirmClickListener(OnConfirmClickListener listener) {
        mOnConfirmClickListener = listener;
    }

    public interface OnConfirmClickListener {
        void onConfirmClick(View view);
    }

    public void setOnCancelClickListener(OnCancelClickListener listener) {
        mOnCancelClickListener = listener;
    }

    public interface OnCancelClickListener {
        void onCancelClick(View view);
    }

}
