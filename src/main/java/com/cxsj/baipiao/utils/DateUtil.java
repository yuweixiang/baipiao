/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.cxsj.baipiao.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author pengjian.pj
 * @version $Id: DateUtil.java, v 0.1 2014-6-10 上午12:12:05 xiaochuan.zhang Exp $
 */
public class DateUtil {

    /** 年月日(无下划线) yyyyMMdd */
    public static final String  shortFormat       = "yyyyMMdd";

    /** 年月日 yyyy-MM-dd */
    public static final String  webFormat         = "yyyy-MM-dd";

    /** 年月日 yyyy-MM-dd */
    public static final String  taoFormat         = "yyyy-MM-dd H:mm:ss";

    /** 年月日 yyyy-MM-dd */
    public static final String  chineseWebFormat         = "yyyy年MM月dd日";

    public static final String  chineseFormat     = "yyyy年MM月dd日 HH:mm:ss";

    /** 完整时间 yyyy-MM-dd HH:mm:ss */
    public static final String  simple            = "yyyy-MM-dd HH:mm:ss";

    /** 带毫秒的日期格式 */
    private static final String milliSecondFormat = "yyyy-MM-dd HH:mm:ss SSS";

    /**
     * 
     * @param date 日期
     * @param format 格式
     * @return 时间字符串
     */
    public static String format(Date date, String format) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     *
     * @param date 日期
     * @param format 格式
     * @return 时间字符串
     */
    public static Date formatDate(Date date, String format) {
        try {
            if (date == null) {
                return null;
            }
            return string2Date(new SimpleDateFormat(format).format(date), format);
        }catch (Exception e){
            return date;
        }
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期
     * @return 时间字符串
     */
    public static final String simpleFormat(Date date) {
        if (date == null) {
            return null;
        }
        return getFormat(simple).format(date);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @return 时间字符串
     */
    public static final String getToday() {
        return getFormat(webFormat).format(new Date());
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @return 时间字符串
     */
    public static final String getShortToday() {
        return getFormat(shortFormat).format(new Date());
    }

    public static String getNextYearDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return getDateString(cal.getTime(), DateUtil.getNewDateFormat(webFormat));
    }

    public static Date getNextMonthDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return parseDate(cal.getTime(),webFormat);
    }

    public static Date getNextNMonthDay(int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, n);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return parseDate(cal.getTime(),webFormat);
    }
    //2014-09-22
    public static Date getNextNDays(int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, n);
        return parseDate(cal.getTime(),webFormat);
    }
    public static Date getNextNDays(Date date,int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return parseDate(cal.getTime(),webFormat);
    }
    public static Date getNextNDaysWithAllTime(Date date,int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return parseDate(cal.getTime(),simple);
    }
    //2014-09-22
    public static Date getNextNTaoDays(int n) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(string2Date(format(new Date(),taoFormat),taoFormat));
            cal.add(Calendar.DATE, n);
            return parseDate(cal.getTime(), taoFormat);
        }catch (Exception e){
            return new Date();
        }
    }

    public static String getChineseDate(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        return df.format(date);
    }

    /**
     * main函数
     *
     * @param args 输入参数
     */
    public static void main(String args[]){
        System.out.println(DateUtil.format(getNextNDays(-30),webFormat));
    }

    public static Date parseDate(Date date,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String s = sdf.format(date);
            return sdf.parse(s);
        }catch (Exception e){
            return null;
        }
    }
    /**
     * 获取格式
     *
     * @param format 格式
     * @return 日期格式
     */
    public static final DateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * 
     * @param date 日期
     * @return 时间字符串
     */
    public static String getNewFormatDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(simple);
        return getDateString(date, dateFormat);
    }

    /**
     *
     * @return 时间字符串
     */
    public static Date getTodayDate() {
        try {
            return getFormat(webFormat).parse(getToday());
        } catch (Exception e) {
            return new Date();
        }
    }

    public static int getYearsBetween(String date){
        if (StringUtils.isBlank(date)){
            return 0;
        }
        try {
            Calendar calBegin = Calendar.getInstance(); //获取日历实例
            Calendar calEnd = Calendar.getInstance();
            calBegin.setTime(string2Date(date, "yyyy")); //字符串按照指定格式转化为日期
            calEnd.setTime(parseDate(new Date(), "yyyy"));
            return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);
        }catch (Exception e){
            return 0;
        }
    }
    public static int getDaysBetween(String date){
        try {
            long difference =  (new Date().getTime()-DateUtil.string2Date(date).getTime())/86400000;
            return new BigDecimal(Math.abs(difference)).intValue();
        }catch (Exception e){
            return 0;
        }
    }
    public static int getDaysBetween(Date date){
        try {
            long difference =  (new Date().getTime()-date.getTime())/86400000;
            return new BigDecimal(Math.abs(difference)).intValue();
        }catch (Exception e){
            return 0;
        }
    }
    public static int getChineseDaysBetween(String date){
        try {
            long difference =  (new Date().getTime()-DateUtil.chineseString2Date(date).getTime())/86400000;
            return new BigDecimal(Math.abs(difference)).intValue();
        }catch (Exception e){
            return 0;
        }
    }

    /**
     * 
     * @param date 日期
     * @param dateFormat 格式
     * @return 时间字符串
     */
    public static String getDateString(Date date, DateFormat dateFormat) {
        if (date == null || dateFormat == null) {
            return null;
        }

        return dateFormat.format(date);
    }

    /**
     * 
     * @param pattern 日期格式正则
     * @return 日期格式
     */
    public static DateFormat getNewDateFormat(String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);

        df.setLenient(false);
        return df;
    }

    /**
     * yyyy-MM-dd 日期字符转换为时间
     * 
     * @param stringDate 日期字符串
     * 
     * @return 日期
     * @throws ParseException 转化异常
     */
    public static final Date string2Date(String stringDate) {

        if (stringDate == null) {
            return null;
        }

        try {
            return getFormat(webFormat).parse(stringDate);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * yyyy-MM-dd 日期字符转换为时间
     *
     * @param stringDate 日期字符串
     *
     * @return 日期
     * @throws ParseException 转化异常
     */
    public static final Date chineseString2Date(String stringDate) throws ParseException {
        if (stringDate == null) {
            return null;
        }

        return getFormat(chineseWebFormat).parse(stringDate);
    }

    /**
     * yyyy-MM-dd 日期字符转换为时间
     *
     * @param stringDate 日期字符串
     *
     * @return 日期
     * @throws ParseException 转化异常
     */
    public static final Date string2Date(String stringDate,String format) {
        try {
            if (stringDate == null) {
                return null;
            }

            return getFormat(format).parse(stringDate);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * @param sDate 日期字符串
     * @return 日期
     */
    public static Date parseDateNewFormat(String sDate) {
        DateFormat dateFormat = new SimpleDateFormat(simple);
        Date d = null;
        if ((sDate != null) && (sDate.length() == simple.length())) {
            try {
                d = dateFormat.parse(sDate);
            } catch (ParseException ex) {
                return null;
            }
        }
        return d;
    }

    /**
     * 时间转换为yyyy-MM-dd HH:mm:ss SSS格式的字符串
     * 
     * @param date 日期
     * @return 日期字符串
     */
    public static String getMilliSecondDateString(Date date) {
        return format(date, milliSecondFormat);
    }
}
