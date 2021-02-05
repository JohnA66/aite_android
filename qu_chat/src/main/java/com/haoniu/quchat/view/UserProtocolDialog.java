package com.haoniu.quchat.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.activity.WebViewActivity;
import com.haoniu.quchat.http.AppConfig;
import com.zds.base.base.BaseDialog;
import com.zds.base.util.TextAreaClickUtils;

/**
 * 用户协议和隐私政策弹窗
 */
public class UserProtocolDialog extends BaseDialog {

    private LinearLayout ll_root;
    private TextView tv_not_agree;
    private TextView tv_agree;
    private TextView tv_content;

    private OnAgreeClickListener mOnAgreeClickListener;
    private OnNotAgreeClickListener mOnNotAgreeClickListener;

    private String mContent = "亲，感谢您对艾特一直以来的信任！\n\n" +
            "我们深知个人信息对用户的重要性，将按照法律法规要求，采取相应的保护措施，尽力保障您的个人信息安全。\n\n" +
            "鉴此，我们特向您说明如下：\n\n" +
            "① 您在使用艾特各项产品或服务时，将会提供与具体功能相关的个人信息（可能涉及账号、位置、交易等信息）。\n\n" +
            "② 未经您的再次同意，我们不会将上述信息用于您未授权的其他用途或目的。\n\n" +
            "③ 您可以随时查询、更正您的个人信息。\n\n" +
            "点击同意即表示您已阅读《艾特平台服务协议》和《艾特隐私政策》并接受全部条款。";


    public UserProtocolDialog(Activity activity) {
        super(activity);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_user_protocol;
    }

    @Override
    protected void initView() {
        tv_not_agree = findViewById(R.id.tv_not_agree);
        tv_agree = findViewById(R.id.tv_agree);
        tv_content = findViewById(R.id.tv_content);

    }

    @Override
    protected void initEventAndData() {
        tv_not_agree.setOnClickListener(this);
        tv_agree.setOnClickListener(this);

        final String[] clickKeywords = new String[3];
        clickKeywords[0] = "《艾特平台服务协议》";
        clickKeywords[1] = "《艾特隐私政策》";
        clickKeywords[2] = "鉴此，我们特向您说明如下：";
        TextAreaClickUtils.initText(tv_content, mContent,
                new String[]{clickKeywords[0], clickKeywords[1],clickKeywords[2]}, Color.parseColor("#000000"),
                clickKeywords, Color.parseColor("#FF2B53"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //ToastUtils.show(mActivity,"点击了" + v.getTag().toString());
                        String clickStr = v.getTag().toString();
                        if (clickStr.equals(clickKeywords[0])) {
                            mActivity.startActivity(new Intent(mActivity, WebViewActivity.class).putExtra("title", "lan").putExtra("url", AppConfig.user_agree));
                        } else if (clickStr.equals(clickKeywords[1])) {
                            mActivity.startActivity(new Intent(mActivity, WebViewActivity.class).putExtra("title", "lan").putExtra("url", AppConfig.register_agree));
                        }
                    }
                });

        setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Window window = getWindow();
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                //layoutParams.width =  getWidth();
                int height = window.getDecorView().getMeasuredHeight();
                if(height > 0.68 * getScreenHeight(mActivity)){
                    layoutParams.height = (int) (0.68 * getScreenHeight(mActivity));
                }
                window.setAttributes(layoutParams);
            }
        });

    }

    /**
     * Return the height of screen, in pixel.
     *
     * @return the height of screen, in pixel
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_not_agree:
                dismiss();
                if(mOnNotAgreeClickListener != null){
                    mOnNotAgreeClickListener.onNotAgreeClick();
                }
                break;
            case R.id.tv_agree:
                dismiss();
                if(mOnAgreeClickListener != null){
                    mOnAgreeClickListener.onAgreeClick();
                }
                break;
        }
    }

    @Override
    protected boolean isCancelable() {
        return false;
    }

    @Override
    protected boolean isCanceledOnTouchOutside() {
        return false;
    }

    public void setOnAgreeClickListener(OnAgreeClickListener listener){
        mOnAgreeClickListener = listener;
    }

    public void setOnNotAgreeClickListener(OnNotAgreeClickListener listener){
        mOnNotAgreeClickListener = listener;
    }

    public interface OnAgreeClickListener {
        void onAgreeClick();
    }


    public interface OnNotAgreeClickListener {
        void onNotAgreeClick();
    }
}
