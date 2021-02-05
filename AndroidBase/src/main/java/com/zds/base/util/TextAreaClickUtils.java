package com.zds.base.util;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 给同一个文本的不同文字区域设置点击事件
 * @author xgp
 * @description:
 * @date :2019/5/30 22:14
 */
public class TextAreaClickUtils {

    public static void initText(TextView textView, String text, String[] keywords, @ColorInt int color,
                                String[] clickKeywords, @ColorInt int clickColor, View.OnClickListener listener) {

        SpannableString spanableInfo = new SpannableString(text);

        for (int i = 0; i < keywords.length; i++) {
            Pattern p = Pattern.compile(keywords[i]);
            Matcher m = p.matcher(spanableInfo);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                spanableInfo.setSpan(new ForegroundColorSpan(color), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        for (int i = 0; i < clickKeywords.length; i++) {
            String clickKeyword = clickKeywords[i];
            spanableInfo.setSpan(new AreaClickableSpan(listener,clickKeyword,clickColor),
                    text.indexOf(clickKeyword), text.indexOf(clickKeyword) + clickKeyword.length(), //设置需要监听的字符串位置
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        textView.setText(spanableInfo);  //将处理过的数据set到View里
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        //textView.setHighlightColor(textView.getContext().getResources().getColor(R.color.transparent));//默认情况下点击文本后 , 目标文本被选中并高亮
    }


    static class AreaClickableSpan extends ClickableSpan {
        private final View.OnClickListener mListener;
        private String mClickKeyword;
        private int mClickColor;

        public AreaClickableSpan(View.OnClickListener listener, String clickKeyword, @ColorInt int clickColor) {
            mListener = listener;
            mClickKeyword = clickKeyword;
            mClickColor = clickColor;
        }
        /**
         * 重写父类点击事件
         */
        @Override
        public void onClick(View v) {
            v.setTag(mClickKeyword);
            mListener.onClick(v);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);// 设置文字下划线不显示
            ds.setColor(mClickColor);// 设置字体颜色  要点击的文本
            ds.setFakeBoldText(true);
            if(mClickKeyword.contains("鉴此，我们特向您说明如下")){
                ds.setColor(Color.parseColor("#000000"));
            }
        }
    }

}

