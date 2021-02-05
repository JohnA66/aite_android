package com.haoniu.quchat.widget;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

/**
 * 作   者：赵大帅
 * 描   述:
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/11/28 9:52
 * 更新日期: 2017/11/28
 */
public class MySelectorJYDecorator implements DayViewDecorator {


    public MySelectorJYDecorator() {
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(true);
    }

}
