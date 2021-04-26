package com.haoniu.quchat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.zds.base.util.BarUtils;

/**
 * title bar
 *
 * @author Administrator
 */
public class TitleBar extends RelativeLayout {

    protected RelativeLayout leftLayout;
    protected ImageView leftImage;
    protected RelativeLayout rightLayout;
    protected ImageView rightImage;
    protected TextView titleView, right_text;
    protected TextView tvUnreadMsg;
    protected RelativeLayout titleLayout;
    protected View bar;

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TitleBar(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.fragment_title_bar, this);
        leftLayout = (RelativeLayout) findViewById(R.id.left_layout);
        leftImage = (ImageView) findViewById(R.id.left_image);
        rightLayout = (RelativeLayout) findViewById(R.id.right_layout);
        rightImage = (ImageView) findViewById(R.id.right_image);
        titleView = (TextView) findViewById(R.id.title);
        right_text = (TextView) findViewById(R.id.right_text);
        titleLayout = (RelativeLayout) findViewById(R.id.root);
        tvUnreadMsg = (TextView) findViewById(R.id.tv_unread_msg);
        bar = findViewById(R.id.bar);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bar.getLayoutParams();
        params.height = BarUtils.getStatusBarHeight(getContext());
        bar.setLayoutParams(params);
        parseStyle(context, attrs);
    }

    public void setBar() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bar.getLayoutParams();
        params.height = 0;
        bar.setLayoutParams(params);
    }

    public void setUnreadMsgCount(int count) {
        if (count <= 0) {
            tvUnreadMsg.setVisibility(View.GONE);
            return;
        }

        tvUnreadMsg.setVisibility(View.VISIBLE);

        if (count > 99) {
            tvUnreadMsg.setText("99+");
        } else {
            tvUnreadMsg.setText(count + "");
        }

    }

    private void parseStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
            String title = ta.getString(R.styleable.TitleBar_titleBarTitle);
            titleView.setText(title);

            Drawable leftDrawable = ta.getDrawable(R.styleable.TitleBar_titleBarLeftImage);
            if (null != leftDrawable) {
                leftLayout.setVisibility(VISIBLE);
            } else {
                leftLayout.setVisibility(GONE);
            }
            Drawable rightDrawable = ta.getDrawable(R.styleable.TitleBar_titleBarRightImage);
            if (null != rightDrawable) {
                rightImage.setImageDrawable(rightDrawable);
            }

            Drawable background = ta.getDrawable(R.styleable.TitleBar_titleBarBackground);
            if (null != background) {
                titleLayout.setBackgroundDrawable(background);
            }

            ta.recycle();
        }
    }

    public void setLeftImageResource(int resId) {
        leftImage.setImageResource(resId);
    }

    public void setRightImageResource(int resId) {
        rightImage.setImageResource(resId);
    }

    public void setRight_text(String text) {
        right_text.setText(text);
    }

    public void setLeftLayoutClickListener(OnClickListener listener) {
        leftLayout.setOnClickListener(listener);
    }

    public void setRightLayoutClickListener(OnClickListener listener) {
        rightLayout.setOnClickListener(listener);
    }

    public void setLeftLayoutVisibility(int visibility) {
        leftLayout.setVisibility(visibility);
    }

    public void setRightLayoutVisibility(int visibility) {
        rightLayout.setVisibility(visibility);
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public String getTitle() {
        return titleView.getText().toString().trim();
    }

    @Override
    public void setBackgroundColor(int color) {
        titleLayout.setBackgroundColor(color);
    }

    public RelativeLayout getLeftLayout() {
        return leftLayout;
    }

    public RelativeLayout getRightLayout() {
        return rightLayout;
    }
}
