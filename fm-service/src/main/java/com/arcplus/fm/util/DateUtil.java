package com.arcplus.fm.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author jv_team
 * @date 2019/12/4 16:53
 */
public class DateUtil {
    /**
     * 判断是不是同一天
     * @param day1
     * @param day2
     * @return
     */
    public static boolean sameDay(long day1, long day2) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(day1);
        int d1 = instance.get(Calendar.DAY_OF_YEAR);
        instance.setTimeInMillis(day2);
        int d2 = instance.get(Calendar.DAY_OF_YEAR);
        return d1 == d2;
    }

    /**
     * 	判断是不是昨天、明天
     * @param day1
     * @param day2
     * @return
     */
    public static boolean isYestoday(long day1, long day2) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(day1);
        int d1 = instance.get(Calendar.DAY_OF_YEAR);
        instance.setTimeInMillis(day2);
        int d2 = instance.get(Calendar.DAY_OF_YEAR);
        return d1 - d2 == 1 || d2 - d1 == 1;
    }


    public static Date addMins(final Date date, final int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    private static Date add(final Date date, final int calendarField, final int amount) {
        if (date == null) {
            return null;
        }
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }
}
