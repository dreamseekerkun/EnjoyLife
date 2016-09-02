package com.dream.plmm.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by likun on 15/10/23.
 */
public class DateUtils {

    /**
     * 毫秒转化成date
     */
    public static String ConvertTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return format.format(calendar.getTime());
    }

}
