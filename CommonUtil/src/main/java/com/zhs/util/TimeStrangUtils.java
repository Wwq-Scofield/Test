package com.zhs.util;

import android.text.TextUtils;
import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by 彭雁 on 2016/8/19.
 * Description ${TODO}
 */
public class TimeStrangUtils {

    /**
     * 日期工具类(未特别说明的均为系统默认时区下的时间)
     * */

    /**
     * 1s==1000ms
     */
    private final static int TIME_MILLISECONDS = 1000;
    /**
     * 时间中的分、秒最大值均为60
     */
    private final static int TIME_NUMBERS = 60;
    /**
     * 时间中的小时最大值
     */
    private final static int TIME_HOURSES = 24;
    /**
     * 格式化日期的标准字符串
     */
    private final static String FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final static long TIME_ZONE_DFF = 8 * 60 * 60 * 1000;

    /**
     * 获取时区信息
     */
    public static TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }

    /**
     * 获取当前时间7天前的月份
     */
    public static String getSevenAgoMonth() {
        String month = "";
        try {
            long nowDayM = System.currentTimeMillis();
            long sevenAgoDayM = nowDayM - 518400000;
            String sevenAgoDay = TimeStrangUtils.longToString(sevenAgoDayM, "yyyy-M-dd");
            month = sevenAgoDay.substring(sevenAgoDay.indexOf("-")+1, sevenAgoDay.lastIndexOf("-"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return month;
    }

    /**
     * 将日期字符串转换为Date对象
     *
     * @param date 日期字符串，必须为"yyyy-MM-dd HH:mm:ss"
     * @return 日期字符串的Date对象表达形式
     */
    public static Date parseDate(String date) {
        if (date == null) {
            return null;
        }
        return parseDate(date, FORMAT);
    }

    /**
     * 将日期字符串转换为Date对象
     *
     * @param date   日期字符串，必须为"yyyy-MM-dd HH:mm:ss"
     * @param format 格式化字符串
     * @return 日期字符串的Date对象表达形式
     */
    public static Date parseDate(String date, String format) {
        Date dt = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            dt = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dt;
    }

    /**
     * 将Date对象转换为指定格式的字符串
     *
     * @param date Date对象
     * @return Date对象的字符串表达形式"yyyy-MM-dd HH:mm:ss"
     */
    public static String formatDate(Date date) {
        return formatDate(date, FORMAT);
    }

    /**
     * 将Date对象转换为指定格式的字符串
     *
     * @param date Date对象
     * @return Date对象的字符串表达形式
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 格式化日期
     *
     * @return 日期字符串"yyyy-MM-dd HH:mm:ss"
     */
    public static String formatUnixTime(long unixTime) {
        return formatUnixTime(unixTime, FORMAT);
    }

    /**
     * 将日期字符串"yyyy-MM-dd HH:mm:ss"转换为时间毫秒值
     *
     * @param time
     * @return
     */
    public static Long formatStringTime(String time) {
        Date date = parseDate(time);
        if (date==null){
            return null;
        }
        long timeMins = date.getTime();
        return timeMins;
    }

    /**
     * 格式化日期
     *
     * @return 日期字符串
     */
    public static String formatUnixTime(long unixTime, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(unixTime);
    }

    /**
     * 将GMT日期格式化为系统默认时区的日期字符串表达形式
     *
     * @param gmtUnixTime GTM时间戳
     * @return 日期字符串"yyyy-MM-dd HH:mm:ss"
     */
    public static String formatGMTUnixTime(long gmtUnixTime) {
        return formatGMTUnixTime(gmtUnixTime, FORMAT);
    }

    /**
     * 将GMT日期格式化为系统默认时区的日期字符串表达形式
     *
     * @param gmtUnixTime GTM时间戳
     * @param format      格式化字符串
     * @return 日期字符串"yyyy-MM-dd HH:mm:ss"
     */
    public static String formatGMTUnixTime(long gmtUnixTime, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(gmtUnixTime + TimeZone.getDefault().getRawOffset());
    }

    /**
     * 获取时间戳的Date表示形式
     *
     * @param unixTime unix时间戳
     * @return Date对象
     */
    public static Date getDate(long unixTime) {
        return new Date(unixTime);
    }

    /**
     * 获取GMT时间戳的Date表示形式（转换为Date表示形式后，为系统默认时区下的时间）
     *
     * @param gmtUnixTime GMT Unix时间戳
     * @return Date对象
     */
    public static Date getGMTDate(long gmtUnixTime) {
        return new Date(gmtUnixTime + TimeZone.getDefault().getRawOffset());
    }

    /**
     * 将系统默认时区的Unix时间戳转换为GMT Unix时间戳
     *
     * @param unixTime unix时间戳
     * @return GMT Unix时间戳
     */
    public static long getGMTUnixTime(long unixTime) {
        return unixTime - TimeZone.getDefault().getRawOffset();
    }

    /**
     * 将GMT Unix时间戳转换为系统默认时区的Unix时间戳
     *
     * @param gmtUnixTime GMT Unix时间戳
     * @return 系统默认时区的Unix时间戳
     */
    public static long getCurrentTimeZoneUnixTime(long gmtUnixTime) {
        return gmtUnixTime + TimeZone.getDefault().getRawOffset();
    }

    /**
     * 获取当前时间的GMT Unix时间戳
     *
     * @return 当前的GMT Unix时间戳
     */
    public static long getGMTUnixTimeByCalendar() {
        Calendar calendar = Calendar.getInstance();
        // 获取当前时区下日期时间对应的时间戳
        long unixTime = calendar.getTimeInMillis();
        // 获取标准格林尼治时间下日期时间对应的时间戳
        long unixTimeGMT = unixTime - TimeZone.getDefault().getRawOffset();
        return unixTimeGMT;
    }

    /**
     * 获取当前时间的Unix时间戳
     *
     * @return 当前的Unix时间戳
     */
    public static long getUnixTimeByCalendar() {
        Calendar calendar = Calendar.getInstance();
        // 获取当前时区下日期时间对应的时间戳
        long unixTime = calendar.getTimeInMillis();
        return unixTime;
    }

    /**
     * 获取更改时区后的时间
     *
     * @param date    时间
     * @param oldZone 旧时区
     * @param newZone 新时区
     * @return 时间
     */
    public static Date changeTimeZone(Date date, TimeZone oldZone, TimeZone newZone) {
        Date dateTmp = null;
        if (date != null) {
            int timeOffset = oldZone.getRawOffset() - newZone.getRawOffset();
            dateTmp = new Date(date.getTime() - timeOffset);
        }
        return dateTmp;
    }

    /**
     * 将总秒数转换为时分秒表达形式
     *
     * @param seconds 任意秒数
     * @return %s小时%s分%s秒
     */
    public static String formatTime(long seconds) {
        long hh = seconds / TIME_NUMBERS / TIME_NUMBERS;
        long mm = (seconds - hh * TIME_NUMBERS * TIME_NUMBERS) > 0 ? (seconds - hh * TIME_NUMBERS * TIME_NUMBERS) / TIME_NUMBERS : 0;
        long ss = seconds < TIME_NUMBERS ? seconds : seconds % TIME_NUMBERS;
        return (hh == 0 ? "" : (hh < 10 ? "0" + hh : hh) + "小时")
                + (mm == 0 ? "" : (mm < 10 ? "0" + mm : mm) + "分")
                + (ss == 0 ? "" : (ss < 10 ? "0" + ss : ss) + "秒");
    }


    public static String formatTimemm(long time) {
        long mt = time > 0 ? time : 0;
        long s = mt % 60;
        long h = mt / 3600;
        long m = (mt - s - h * 3600) / 60;
        String hh = (h > 9 ? h : "0" + h) + ":";
        if ("00:".equals(hh)) {
            hh = "";
        }
        return hh + (m > 9 ? m : "0" + m) + ":"
                + (s > 9 ? s : "0" + s);
    }

    public static long EEA2GMT(long seconds) {
        return seconds - TIME_ZONE_DFF;
    }

    /**
     * 获取当前时间距离指定日期时差的大致表达形式
     *
     * @return 时差的大致表达形式
     */
    public static String getDiffTime(long date) {
        String strTime = "很久很久以前";
        long time = Math.abs(new Date().getTime() - date);
        // 一分钟以内
        if (time < TIME_NUMBERS * TIME_MILLISECONDS) {
            strTime = "刚刚";
        } else {
            int min = (int) (time / TIME_MILLISECONDS / TIME_NUMBERS);
            if (min < TIME_NUMBERS) {
                if (min < 15) {
                    strTime = "一刻钟前";
                } else if (min < 30) {
                    strTime = "半小时前";
                } else {
                    strTime = "1小时前";
                }
            } else {
                int hh = min / TIME_NUMBERS;
                if (hh < TIME_HOURSES) {
                    strTime = hh + "小时前";
                } else {
                    int days = hh / TIME_HOURSES;
                    if (days <= 6) {
                        strTime = days + "天前";
                    } else {
                        int weeks = days / 7;
                        if (weeks < 3) {
                            strTime = weeks + "周前";
                        }
                    }
                }
            }
        }

        return strTime;
    }

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    public static String long2Date(long time) throws Exception {
        Date date = longToDate(time, FORMAT); // long类型转成Date类型
        String strTime = dateToString(date, FORMAT); // date类型转成String
        return strTime;
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        return sDateTime;
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    public static long dateStringToLong(String dateString) throws Exception {
        Date date = stringToDate(dateString, FORMAT);
        return date.getTime();
    }

    /**
     * 获取当前时间距离指定日期是否大于十分钟
     *
     * @return 时差的大致表达形式
     */
    public static boolean isOutOfTenMin(String date) {
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = sim.parse(date);
            Date end = new Date();
            long min = Math.abs(((end.getTime() - start.getTime()) / 1000) / 60);

            if (min > 10) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 获取当前时间距离指定日期是否大于十分钟
     *
     * @return 时差的大致表达形式
     */
    public static boolean isOutOfTenMin2(long time) {
        if (time == 0) {
            return false;
        }
        try {
            Date end = new Date();
            long min = Math.abs(((end.getTime() - time) / 1000) / 60);

            if (min > 10) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 判断当前时间是否已经超过目标时间
     *
     * @return 时差的大致表达形式
     */
    public static boolean isBeyondCurrentTime(String date) {
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = sim.parse(date);
            Date end = new Date();
            long result = end.getTime() - start.getTime();

            if (result >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss转换为Date对象
     *
     * @param time 时间格式
     * @return date对象
     */
    public static Date formatDate(String time) {
        return parseDate(time);
    }

    public static String setPushTime(String pushTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long millionSeconds = simpleDateFormat.parse(pushTime).getTime();//毫秒
            long serviceTime = TimeStrangUtils.EEA2GMT(millionSeconds);//服务器EEA时间转GMT时间
            long currentTime = System.currentTimeMillis();//系统时间
            long gmtUnixTime = TimeStrangUtils.getGMTUnixTime(currentTime);//GMT时间
            long timeOffset = gmtUnixTime - serviceTime;//时间差
            long minute = 60 * 1000;//1分钟
            Calendar instance = Calendar.getInstance();//获取日历对象
            int currentYear = instance.get(Calendar.YEAR);//获取系统当前年份
            int systemYear = Integer.parseInt(pushTime.substring(0, 4));//系统的年份
            if (timeOffset < minute) {//小于一分钟
                return "刚刚";
            } else if (timeOffset < minute * 60) {
                return (timeOffset / minute) + "分钟前";
            } else if (timeOffset < minute * 60 * 24) {
                int days = new Date(gmtUnixTime).getDay();
                int serDays = new Date(serviceTime).getDay();
                if (days != serDays) {
                    return "昨天";
                } else {
                    return (timeOffset / 60 / minute) + "小时前";
                }
            } else if (currentYear == systemYear) {//同一年
                int days = new Date(gmtUnixTime).getDay();
                int serDays = new Date(serviceTime).getDay();
                if (timeOffset < minute * 60 * 24 * 3) {
                    if (days - serDays == 1) {
                        return "昨天";
                    } else if (days - serDays == 2) {
                        return "前天";
                    } else {
                        if ((days + 7 - serDays) == 1) {
                            return "昨天";
                        } else if ((days + 7 - serDays) == 2) {
                            return "前天";
                        } else {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String format = dateFormat.format(new Date(millionSeconds));
                            String[] split = format.split("-");
                            return split[1] + "月" + split[2] + "日";
                        }
                    }
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String format = dateFormat.format(new Date(millionSeconds));
                    String[] split = format.split("-");
                    return split[1] + "月" + split[2] + "日";
                }
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String format = dateFormat.format(new Date(millionSeconds));
                String[] split = format.split("-");
                return split[0] + "年" + split[1] + "月" + split[2] + "日";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] split = pushTime.split(" ");
        return split[0];
    }

    private static String thanTen(int str) {

        String string = null;

        if (str < 10) {
            string = "0" + str;
        } else {

            string = "" + str;

        }
        return string;
    }

    public static String getNowTime() {
        String timeString = null;
        Time time = new Time();
        time.setToNow();
        String year = thanTen(time.year);
        String month = thanTen(time.month + 1);
        String monthDay = thanTen(time.monthDay);
        String hour = thanTen(time.hour);
        String minute = thanTen(time.minute);
        String second = thanTen(time.second);

        timeString = year + "-" + month + "-" + monthDay + " " + hour + ":"
                + minute + ":" + second;
        // System.out.println("-------timeString----------" + timeString);
        return timeString;
    }

    /**
     * 与当前时间比较早晚
     *
     * @param time 需要比较的时间
     * @return 输入的时间比现在时间晚则返回true
     */
    public static boolean compareNowTime(String time) {
        boolean isDayu = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);

        try {
            Date parse = dateFormat.parse(time);
            Date parse1 = dateFormat.parse(getNowTime());

            long diff = parse1.getTime() - parse.getTime();
            if (diff <= 0) {
                isDayu = true;
            } else {
                isDayu = false;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return isDayu;
    }

    // TODO 秒转分秒
    public static String millisToStringShort(String str) {
        long s = 300 * 1000;//毫秒数
        long millis = 0;
        if (!TextUtils.isEmpty(str)) {
            millis = Long.valueOf(str) * 1000;
            SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");//初始化Formatter的转换格式。

            String ms = formatter.format(millis);
            return ms;
        } else {
            return "00:00";
        }
    }

    public static String getYMDTime(String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatDate(time));
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }
}

