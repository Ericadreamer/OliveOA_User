package com.oliveoa.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  时间格式转换工具类
 * */
public class DateFormat {
    public void DateFormatUtil(){
    }

    //长整型转换为时间型
    public String LongtoDate(long time){
        Date date1 = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Log.i("LongtoDate:",format.format(date1));
        return format.format(date1);
    }
    //长整型转换为分钟时间型
    public String LongtoDatemm(long time){
        Date date1 = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Log.i("LongtoDatemm:",format.format(date1));
        return format.format(date1);
    }




 }
