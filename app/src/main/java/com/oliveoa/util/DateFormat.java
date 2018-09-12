package com.oliveoa.util;

import android.util.Log;

import java.text.ParseException;
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.i("LongtoDate:",format.format(date1));
        return format.format(date1);
    }
    //长整型转换为分钟时间型
    public String LongtoDatemm(long time){
        Date date1 = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Log.i("LongtoDatemm:",format.format(date1));
        return format.format(date1);
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }
    // date类型转换为long类型
    // date要转换的date类型的时间
    public long dateToLong(Date date) {
        return date.getTime();
    }





}
