package com.haoniu.quchat.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.widget.EditText;
import android.widget.TextView;

import com.zds.base.util.DensityUtils;
import com.zds.base.util.Utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 作   者：赵大帅
 * 描   述:
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/11/9 17:31
 * 更新日期: 2017/11/9
 */
public class StringUtil {


    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(EditText input) {
        if (input == null) {
            return true;
        }
        return isEmpty(input.getText().toString());
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(TextView input) {
        if (input == null) {
            return true;
        }
        return isEmpty(input.getText().toString());
    }

    /**
     * 格式化时间显示 (显示天)
     *
     * @param dateTime
     * @return
     */
    public static String formatDate(String dateTime) {
        if (dateTime == null || dateTime.equals("null")) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");
            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(dateTime));
            Calendar now = Calendar.getInstance();
            if (cd.get(Calendar.DATE) == now.get(Calendar.DATE)) {
                return "今天";
            }
            return sdf2.format(cd.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    /**
     * 格式化时间显示 (显示到分钟)
     *
     * @param dateTime
     * @return
     */
    public static String formatDateMinute(String dateTime) {
        if (dateTime == null || dateTime.equals("null")) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(dateTime));
            return sdf2.format(cd.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }


    /**
     * 格式化金额
     *
     * @param amount
     * @return
     */
    public static SpannableStringBuilder formateMoney(String amount) {
        SpannableStringBuilder builder = new SpannableStringBuilder(amount);
        builder.setSpan(new AbsoluteSizeSpan(DensityUtils.sp2px(Utils.getContext(), 12)), amount.indexOf("￥"), amount.indexOf("￥") + 1
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(DensityUtils.sp2px(Utils.getContext(), 12)), amount.indexOf("."), amount.length()
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    /**
     * 格式化时间显示
     *
     * @param dateTime
     * @return
     */
    public static String formatDateMinute(long dateTime,String pattern) {
        if (StringUtil.isEmpty(pattern)){
            pattern = "yyyy年MM月dd日 HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.format(new Date(dateTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 格式化时间显示
     *
     * @param dateTime
     * @return
     */
    public static String formatDateMinute4(long dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            return sdf.format(new Date(dateTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 格式化时间显示
     *
     * @param dateTime
     * @return
     */
    public static String formatDateMinute2(long dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.format(new Date(dateTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 格式化时间显示
     *
     * @param dateTime 时分秒
     * @return
     */
    public static String formatDateMinute3(long dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            return sdf.format(new Date(dateTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 格式化时间显示
     *
     * @param dateTime
     * @return
     */
    public static String formatDate(long dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        try {
            return sdf.format(new Date(dateTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 格式化时间显示
     *
     * @param dateTime
     * @return
     */
    public static String formatDate3(long dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            return sdf.format(new Date(dateTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatDateMinute(Long dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
            Calendar cd = Calendar.getInstance();
            cd.setTime(new Date(dateTime));
            Calendar now = Calendar.getInstance();
            if (cd.get(Calendar.DATE) == now.get(Calendar.DATE)) {
                return "" + sdf2.format(cd.getTime());
            } else if (cd.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cd.getTime());
            }
            return sdf.format(new Date(dateTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 移除空字符
     *
     * @return
     */
    public static String removeEmpty(List<String> list) {
        String listString = "";
        for (String string : list) {
            if (!string.equals("")) {
                listString += string + ";";
            }
        }
        if (!listString.equals("")) {
            listString = listString.substring(0, listString.length() - 1);
        }
        return listString;
    }

    /**
     * 格式化 money 保留两位小数
     *
     * @param d
     * @return
     */
    public static String getFormatValue2(double d) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);
    }

    /**
     * 格式化 money 保留两位小数
     *
     * @param d
     * @return
     */
    public static String getFormatValue2(String d) {
        double number = 0;
        try {
            number = Double.valueOf(d);
        } catch (Exception e) {
            e.getStackTrace();
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(number);
    }

    /**
     * 格式化 money 保留三位小数
     *
     * @param d
     * @return
     */
    public static String getFormatValue3(double d) {
        DecimalFormat df = new DecimalFormat("0.000");
        return df.format(d);
    }

    public static String getFormatValue1(double d) {
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(d);
    }

    public static String getFormatValue(double d) {
        DecimalFormat df = new DecimalFormat("0");
        return df.format(d);
    }

    public static String getFormatebfb(double d) {
        DecimalFormat df = new DecimalFormat("0%");
        return df.format(d);
    }


    public static String getFormatValue0(String d) {
        double number = 0;
        try {
            number = Double.valueOf(d);
        } catch (Exception e) {
            e.getStackTrace();
        }
        DecimalFormat df = new DecimalFormat("0");
        return df.format(number);
    }


    /**
     * 格式化用户手机号码
     *
     * @param userPhone
     * @return
     */
    public static String formateUserPhone(String userPhone) {
        if (userPhone != null && userPhone.length() > 7) {
            userPhone = userPhone.replaceFirst(userPhone.substring(3, 7), "****");
            return userPhone;
        }
        return userPhone;
    }

    /**
     * 格式化用户手机号码
     *
     * @param userPhone
     * @return
     */
    public static boolean isPhone(String userPhone) {
        if (userPhone != null && userPhone.length() == 11) {
            return true;
        }
        return false;
    }

    /**
     * 格式化用户手机号码
     *
     * @param userPhone
     * @return
     */
    public static boolean isPhone(EditText userPhone) {
        if (userPhone == null) {
            return false;
        }
        String userPhoneStr = userPhone.getText().toString();
        if (userPhoneStr != null && userPhoneStr.length() == 11) {
            return true;
        }
        return false;
    }

    /**
     * byte 转 String
     *
     * @param bytes
     * @param length
     * @return
     */
    public static String bytesToHexString(byte[] bytes, int length) {
        String result = "";
        for (int i = 0; i < (length > 0 ? length : bytes.length); i++) {
            String hexString = Integer.toHexString(bytes[i] & 0xFF);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            result += hexString.toUpperCase();
        }
        return result;
    }

    public static String arrayToString(List<String> list) {
        if (list == null || list.size() == 0) {
            return null;
        } else {
            String strIndex = "";
            for (String str : list) {
                strIndex += str + ",";
            }
            if (strIndex.length()>0){
                strIndex=strIndex.substring(0,strIndex.length() - 1);
            }
            return strIndex;
        }
    }
}
