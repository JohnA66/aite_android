package com.haoniu.quchat.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.haoniu.quchat.utils.DisplayUtil;

public class AuthenticationTextView extends android.support.v7.widget.AppCompatTextView {
    private Paint mPaint;
    private boolean isSelect = false;
    public AuthenticationTextView(Context context) {
        super(context);
        mPaint = new Paint();
    }


    public AuthenticationTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    public AuthenticationTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (isSelect){
            mPaint.setColor(Color.parseColor("#19B70F"));
            mPaint.setStrokeWidth(DisplayUtil.dip2px(getContext(),4));
            canvas.drawLine(0, getHeight()-17, getWidth(), getHeight()-17, mPaint);
            TextPaint tp = getPaint();
            tp.setFakeBoldText(true);
        }else {
            TextPaint tp = getPaint();
            tp.setFakeBoldText(false);
        }

        super.onDraw(canvas);
    }
}
