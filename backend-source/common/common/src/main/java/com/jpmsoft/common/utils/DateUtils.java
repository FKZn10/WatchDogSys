package com.jpmsoft.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Deprecated
public class DateUtils {

    public static String DateFormat(Date time, String format) {
        if (time == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
    }
}
