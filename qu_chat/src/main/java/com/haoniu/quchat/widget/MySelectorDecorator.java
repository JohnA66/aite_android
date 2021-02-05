package com.haoniu.quchat.widget;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.style.RelativeSizeSpan;

import com.aite.chat.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 作   者：赵大帅
 * 描   述:
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/11/28 9:52
 * 更新日期: 2017/11/28
 */
public class MySelectorDecorator implements DayViewDecorator {

    private final Drawable drawable;
    private List<Date> mDates;


    public MySelectorDecorator(Activity context, List<Date> mDates) {
        drawable = context.getResources().getDrawable(R.drawable.bor_singn_4);
        this.mDates = mDates;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        for (Date date : mDates) {
            if (isSameDate(date, day.getCalendar().getTime())) {
                return true;
            }
        }
        return false;
    }

    public void setDates(List<Date> dates) {
        mDates = dates;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
        view.addSpan(new RelativeSizeSpan(1.0f));
    }

    private boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);
        return isSameDate;
    }
}
