package com.zhs.util;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * Created by xuguoqiang on 2016/11/25.
 */

public class StringUtils {

    private static DecimalFormat mDecimalFormat = new DecimalFormat("##0.0");

    //将阿拉伯数字替换成汉字
    public static String ToCH(int intInput) {
        String si = String.valueOf(intInput);
        String sd = "";
        if (si.length() == 1) // 個
        {
            sd += GetCH(intInput);
            return sd;
        } else if (si.length() == 2)// 十
        {
            if (si.substring(0, 1).equals("1"))
                sd += "十";
            else
                sd += (GetCH(intInput / 10) + "十");
            sd += ToCH(intInput % 10);
        } else if (si.length() == 3)// 百
        {
            sd += (GetCH(intInput / 100) + "百");
            if (String.valueOf(intInput % 100).length() < 2)
                sd += "零";
            sd += ToCH(intInput % 100);
        } else if (si.length() == 4)// 千
        {
            sd += (GetCH(intInput / 1000) + "千");
            if (String.valueOf(intInput % 1000).length() < 3)
                sd += "零";
            sd += ToCH(intInput % 1000);
        } else if (si.length() == 5)// 萬
        {
            sd += (GetCH(intInput / 10000) + "萬");
            if (String.valueOf(intInput % 10000).length() < 4)
                sd += "零";
            sd += ToCH(intInput % 10000);
        }

        return sd;
    }

    private static String GetCH(int input) {
        String sd = "";
        switch (input) {
            case 1:
                sd = "一";
                break;
            case 2:
                sd = "二";
                break;
            case 3:
                sd = "三";
                break;
            case 4:
                sd = "四";
                break;
            case 5:
                sd = "五";
                break;
            case 6:
                sd = "六";
                break;
            case 7:
                sd = "七";
                break;
            case 8:
                sd = "八";
                break;
            case 9:
                sd = "九";
                break;
            default:
                break;
        }
        return sd;
    }

    /**
     * 验证内容是否为空
     * 对应都是空格，都是回车，或者只有回车换行空格
     *
     * @param content 要验证的文本内容
     * @return true是空  false不为空
     */
    public static boolean isContentEmpty(String content) {
        if (TextUtils.isEmpty(content)) {
            return true;
        }
        String norn = content.replaceAll("\n", "");
        String nok = norn.replaceAll(" ", "");
        if (TextUtils.isEmpty(nok)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 格式化float，如果小数点后一位为零则不保留，反之保留小数
     */
    public static String formatFloat(float score) {
        if ((score * 100) % 100 != 0) {
            return mDecimalFormat.format(score);
        } else {
            return ((int) ((score * 100)) / 100) + "";
        }
    }

    /**
     * 判断字符是否是汉字
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符是否是字母或者是数字
     */
    private boolean getWordOrNum(char ch) {
        if (Character.isLetter(ch) || Character.isDigit(ch)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean equalsIgnoreCase(String l, String r) {
        if (l == null) {
            return l == r;
        }
        return l.equalsIgnoreCase(r);
    }

}