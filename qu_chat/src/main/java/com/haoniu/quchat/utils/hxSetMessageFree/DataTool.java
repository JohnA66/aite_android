package com.haoniu.quchat.utils.hxSetMessageFree;

import java.lang.reflect.Field;

public class DataTool {
    /**
     * 反射获取指定字段对象
     */
    public static Object getSpecifiedFieldObject(Object obj, String fieldName) {
        Class<?> clazz = obj.getClass();
        Object object = null;
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            object = field.get(obj);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return object;
    }
}
