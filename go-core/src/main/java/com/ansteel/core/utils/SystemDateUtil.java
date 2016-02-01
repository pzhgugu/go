package com.ansteel.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/2/1.
 */
public class SystemDateUtil {
    public final static int SYSTEM_YEAR=1;
    public final static int SYSTEM_MONTH=2;
    public final static int SYSTEM_DAY=3;

    public static String getYear(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        return formatter.format(date);
    }

    public static String getMonth(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        return formatter.format(date);
    }

    public static String getDay(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        return formatter.format(date);
    }

}
