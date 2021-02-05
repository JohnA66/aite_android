package com.haoniu.quchat.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

import com.aite.chat.R;
import com.haoniu.quchat.entity.GroupDetailInfo;

import java.util.ArrayList;

public class MoreItemDecoration extends RecyclerView.ItemDecoration {
    private Context context;
    private Paint backgroundPaint;// 画背景的笔
    private Paint textPaint;// 画字母的笔

    private int titleHeight;

    private int textHeight;// 文字高度
    private int textBaseLineOffset;// 文字底部位置
    private int textStartMargin;// 文字的起始 margin

    private ArrayList<GroupDetailInfo.GroupUserDetailVoListBean> list;// 数据源。 bean 有自己的字母属性
    private String currentParentName = "";// 当前的 item 的产品的字母是什么

    public MoreItemDecoration(Context context, ArrayList<GroupDetailInfo.GroupUserDetailVoListBean> list) {
        this.context = context;
        this.list = list;
        this.titleHeight = context.getResources().getDimensionPixelSize(R.dimen.dp_40);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(context.getResources().getColor(R.color.bg_color_my_gold));

        textPaint = new TextPaint();
        textPaint.setColor(context.getResources().getColor(R.color.bg_color));
        textPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.sp_14));

        Paint.FontMetrics fm = textPaint.getFontMetrics();
        textHeight = (int) (fm.bottom - fm.top);
        textBaseLineOffset = (int) fm.bottom;
        textStartMargin = context.getResources().getDimensionPixelOffset(R.dimen.dp_8);
    }

    // 先执行完全部的 getItemOffsets 方法，再去执行 onDraw 方法
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        // 该方法的目的是找到需要在该 position 的 item 的上方空间写字母的 position，然后修改偏移量
        // 获取 recyclerView 的 item 的 position
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        if (position == 0) {
            outRect.set(0, titleHeight, 0, 0);
        } else {
            if (!list.get(position - 1).getTop().equals(list.get(position).getTop())) {
                outRect.set(0, titleHeight, 0, 0);
            } else {
                outRect.set(0, 0, 0, 0);
            }
        }

        // 判断当前字母是否和 bean 的字母相同 下面方法在数据较多复用 item 时存在 bug
        /*if (!currentParentName.equals(list.get(position).getParentName())) {
            // 不相同，则绘制分割线空隙为设定的高度
            outRect.set(0, titleHeight, 0, 0);
            // 将 bean 的字母值赋予给当前的字母值变量
            currentParentName = list.get(position).getParentName();
        } else {
            outRect.set(0, 0, 0, 0);
        }*/
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        // 该方法的目的是在需要写字母的 item 的上方写上字母
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewAdapterPosition();
            if (position == 0) {
                drawText(c, left, right, child, params, position);
            } else {
                if (!list.get(position - 1).getTop().equals(list.get(position).getTop())) {
                    // 绘制字母
                    drawText(c, left, right, child, params, position);
                }
            }

            // 如果当前的字母值和 bean 的字母值相同，则跳过，不再绘制，比如已经绘制了 A 字母，第二个 bean 的字母属性还是 A，则跳过，不再绘制 A。 下面方法在数据较多时存在问题
            /*if (currentParentName.equals(list.get(position).getParentName())) {
                continue;
            }
            // 绘制字母
            drawText(c, left, right, child, params, position);*/
        }
    }

    private void drawText(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {
        int rectBottom = child.getTop() - params.topMargin;
        c.drawRect(left, rectBottom - titleHeight, right, rectBottom, backgroundPaint);
        c.drawText(list.get(position).getTop(), child.getPaddingLeft() + textStartMargin, rectBottom - (titleHeight - textHeight) / 2 - textBaseLineOffset, textPaint);
        // 之前写到，执行完全部的 getItemOffsets  方法后，才执行 onDraw 方法，所以，这里需要再绘制完字母后，修改当前字母变量
        //currentParentName = list.get(position).getParentName();
    }
}
