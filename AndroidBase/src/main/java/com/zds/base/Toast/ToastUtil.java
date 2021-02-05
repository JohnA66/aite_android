package com.zds.base.Toast;

import android.widget.Toast;

import com.zds.base.util.Utils;

/**
 * 作   者：赵大帅
 * 描   述: 吐司工具类
 * 日   期: 2017/9/15 10:04
 * 更新日期: 2017/9/15
 */
public class ToastUtil {

    public static void toast(String message) {
        if (message == null || message.equals("")) {
            return;
        }
        Toast.makeText(Utils.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
