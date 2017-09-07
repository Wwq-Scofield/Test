package com.zhs.util;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类名：CommonUtil
 * 类描述：通用
 * 创建人：fei.wang
 * 创建日期： 2014/2/20.
 */
public class CommonUtil {

    private static final double EARTH_RADIUS = 6378137;
    private static final DecimalFormat df = new DecimalFormat("#0.00");
    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     *
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static double distance2Point(double lng1, double lat1, double lng2,
                                        double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    public static String getDistance(double lng1, double lat1, double lng2,
                                     double lat2) {
        String strDistance = null;
        double douDistance = distance2Point(lng1, lat1, lng2, lat2);
        if (douDistance > 10000) {
            strDistance = ">10KM";
        } else {
            strDistance = df.format(douDistance / 1000) + "KM";
        }
        return strDistance;
    }
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 修改part2的文字的颜色
     *
     * @param part1
     * @param part2
     * @param part3
     * @param color
     * @return
     */
    public static SpannableStringBuilder getDialogSpanString(String part1, String part2, String part3, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(part1 + part2 + part3);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        builder.setSpan(span, part1.length(), part1.length() + part2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }


    /**
     * @param context
     * @param style1
     * @param part1
     * @param style2
     * @param part2
     * @param style3
     * @param part3
     * @return
     */
    public static SpannableStringBuilder getDateAndMoney(Context context, int style1, String part1, int style2, String part2, int style3, String part3) {
        SpannableStringBuilder builder = new SpannableStringBuilder(part1 + part2 + part3);
        TextAppearanceSpan span1 = new TextAppearanceSpan(context, style1);
        TextAppearanceSpan span2 = new TextAppearanceSpan(context, style2);
        TextAppearanceSpan span3 = new TextAppearanceSpan(context, style3);

        builder.setSpan(span1, 0, part1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(span2, part1.length(), part1.length() + part2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(span3, part1.length() + part2.length(), part1.length() + part2.length() + part3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;
    }

    public static SpannableStringBuilder get3TextStyle(Context context, int style1, String part1, int style2, String part2, int style3, String part3) {
        SpannableStringBuilder builder = new SpannableStringBuilder(part1 + part2 + part3);
        TextAppearanceSpan span1 = new TextAppearanceSpan(context, style1);
        TextAppearanceSpan span2 = new TextAppearanceSpan(context, style2);
        TextAppearanceSpan span3 = new TextAppearanceSpan(context, style3);

        builder.setSpan(span1, 0, part1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(span2, part1.length(), part1.length() + part2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(span3, part1.length() + part2.length(), part1.length() + part2.length() + part3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;
    }

    public static SpannableStringBuilder getOtherFee(Context context, int style1, String part1, int style2, String part2) {
        if (part1 == null || part2 == null) {
            return new SpannableStringBuilder(part1 + part2);
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(part1 + part2);
        TextAppearanceSpan span1 = new TextAppearanceSpan(context, style1);
        TextAppearanceSpan span2 = new TextAppearanceSpan(context, style2);

        builder.setSpan(span1, 0, part1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(span2, part1.length(), part1.length() + part2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;
    }

    /**
     * 根据资源名获取资源
     *
     * @param context
     * @param name
     * @return
     */
    public static int getDrawableWithResName(Context context, String name) {
        Resources res = context.getResources();
        final String packageName = context.getPackageName();
        int imageResId = res.getIdentifier(name, "drawable", packageName);
        return imageResId;
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f * (dpValue >= 0 ? 1 : -1));
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f * (spValue >= 0 ? 1 : -1));
    }

    /**
     * dp专px
     * @param context
     * @param dip
     * @return
     */
    public static int convertDipToPx(Context context, int dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context
     * @return true 表示开启
     */
    public static final boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

    /**
     * 强制帮用户打开GPS
     *
     * @param context
     */
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(30);
        if (!(serviceList.size() > 0)) {
            Log.d("hu8huService", "serviceList.size()=" + serviceList.size());
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            Log.d("hu8huService", "serviceName=" + serviceList.get(i).service.getClassName());
            if (serviceList.get(i).service.getClassName().contains(className) == true) {
                Log.d("hu8huService", className + ".isRunning=true");
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * 判断点击事件频率
     * @return
     */
    private static long lastClickTime = 0;//上次点击的时间
    private static int spaceTime = 1500;//时间间隔
    public static boolean isFastClick() {
        long currentTime = System.currentTimeMillis();//当前系统时间
        boolean isAllowClick;//是否允许点击
        if (currentTime - lastClickTime > spaceTime) {
            isAllowClick = false;
        } else {
            isAllowClick = true;
        }
        lastClickTime = currentTime;
        return isAllowClick;
    }


    /**
     当前界面必须已经加载完成，不能直接在Activity的onCreate()，onResume()，onAttachedToWindow()中使用，
     可以在这些方法中通过postDelayed的方式来延迟执行showSoftInput()。
     * @param context 上下文
     * @param view 必须是VISIBLE的EditText，如果不是VISIBLE的，需要先将其设置为VISIBLE。
     */
    public static void showSoftInput(Context context, EditText view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }


    /**
     *
     * @param context 上下文
     * @param view view可以当前布局中已经存在的任何一个View，如果找不到可以用getWindow().getDecorView()。
     */
    public static void hideSoftInput(Context context,EditText view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     * 判断是否包含数字
     * @param content
     * @return true 包含  false 不包含
     */
    public static boolean HasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 去除一个字符串中的数字返回的字符串
     * @param str
     * @return
     */
    public static String clearStringNum(String str){
        return str.replaceAll("\\d+","");
    }

    /**
     * 删除字符串中指定的字符
     * @param str  原来的字符串
     * @param replace  指定的字符
     * @return 去除指定的字符的字符串
     */
    public static String clearString(String str,String replace){
        return str.replaceAll(replace,"");
    }

    public static String formatDateTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (time == null || "".equals(time)) {
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        // Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH,
                current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        Calendar tomorrow = Calendar.getInstance();

        tomorrow.set(Calendar.YEAR, current.get(Calendar.YEAR));
        tomorrow.set(Calendar.MONTH, current.get(Calendar.MONTH));
        tomorrow.set(Calendar.DAY_OF_MONTH,
                current.get(Calendar.DAY_OF_MONTH) + 1);
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today) && current.before(tomorrow)) {
            return "今天 "+time.split(" ")[1];
        } else if (current.before(today) && current.after(yesterday)) {
            return "昨天 " + time.split(" ")[1];
        } else {
            return dateToString(date,"MM月dd日");
        }
    }


    public static String formatDateTime1(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (time == null || "".equals(time)) {
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();
        current.setTime(date);
        return dateToString(date,"M月d日 HH:mm");
    }

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    public static String formatDateTime2(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (time == null || "".equals(time)) {
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar current = Calendar.getInstance();
        current.setTime(date);
        return dateToString(date,"HH:mm");
    }





    public static String millisToStringShort(long millis, boolean isWhole,
                                             boolean isFormat) {
        String h = "";
        String m = "";
        if (isWhole) {
            h = isFormat ? "00小时" : "0小时";
            m = isFormat ? "00分钟" : "0分钟";
        }

        long temp = millis;

//       long hper = 60 * 60 * 1000;
        long mper = 60 * 1000;
        long sper = 1000;

        if (temp / mper > 0) {
            if (isFormat) {
                h = temp / mper < 10 ? "0" + temp / mper : temp / mper + "";
            } else {
                h = temp / mper + "";
            }
            h += ":";
        }
        temp = temp % mper;

        if (temp / sper > 0) {
            if (isFormat) {
                m = temp / sper < 10 ? "0" + temp / sper : temp / sper + "";
            } else {
                m = temp / sper + "";
            }
            m += "";
        }

        return h + m;
    }


    public static String numberWithDelimiter(long num) {
        StringBuilder accum = new StringBuilder();
        int len = accum.append(num).length();
        if (len <= 3) return accum.toString();	//如果长度小于等于3不做处理
        while ((len -= 3) > 0) {	//从个位开始倒序插入
            accum.insert(len, ",");
        }
        return accum.toString();
    }

    public static String getStringNum(String number){
        Long num = Long.parseLong(number);
        if(num >= 10000){
            double d = (double)num / (double)10000;
            DecimalFormat df = new DecimalFormat("#.#");
            return df.format(d) + "万";
        }
        return number;

    }

    public static InputStream getStringStream(String sInputString){
        if (sInputString != null && !sInputString.trim().equals("")) {
            ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
            return tInputStringStream;
        }else{
            return null;
        }
    }

    // true今天 false不是
    public static boolean isToday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
        }
        return DateLocal.get();
    }

    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();

    /**
     *
     * @param DATE1  预告时间
     * @param DATE2  当前时间
     * @return  大于0设置闹钟
     */
    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                Log.e("compare_date","dt1大于当前时间，设置闹钟");
                return 1;  // dt1 大于 dt2
            } else if (dt1.getTime() < dt2.getTime()) {
                Log.e("compare_date","dt1小于当前时间，不设置闹钟");
                return -1;  // dt1 小于 dt2
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static String currentTime(){
        // 获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String format = df.format(new Date());
        return format;
    }
}
