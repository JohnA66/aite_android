package com.haoniu.quchat.help;

import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * @author lhb
 * @date 2018/12/24
 * handler封装；防止强引用导致内存泄露
 */

public class UiHandler<T> extends Handler {
    protected WeakReference<T> ref;

    public UiHandler(T cls) {
        ref = new WeakReference<T>(cls);
    }

    public T getRef() {
        return ref != null ? ref.get() : null;
    }

}
