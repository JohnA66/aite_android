package com.haoniu.quchat.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zzx on 2018/10/29/下午 3:20
 */

public class PhoneFormatUtil {

    /**
     * 验证号码 手机号 固话均可
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;

        String expression = "((^(13|14|15|16|17|18|19)[0-9]{9}$)|(^(([04]\\d{2,3}\\d{7,8})|(1[3584]\\d{9}))$))";
        CharSequence inputStr = phoneNumber;

        Pattern pattern = Pattern.compile(expression);

        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}
