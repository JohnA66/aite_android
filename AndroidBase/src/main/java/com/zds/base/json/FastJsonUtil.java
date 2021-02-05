package com.zds.base.json;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.zds.base.log.XLog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作   者：赵大帅
 * 描   述: json 解析
 * 日   期: 2017/9/6 14:41
 * 更新日期: 2017/9/6
 */

public class FastJsonUtil {

    /**
     * @param
     * @param type
     * @param <T>
     * @return
     */
    public static final <T> T getObject(String jsontext, Type type) {
        T t = null;
        try {
            t = new GsonBuilder().create().fromJson(jsontext, type);
        } catch (Exception e) {
            XLog.error("json字符串转换失败" + jsontext, e);
        }
        return t;
    }

    /**
     * 解析
     *
     * @param jsontext
     * @param clazz
     * @param <T>
     * @return
     */
    // 注：传入任意的jsontext,返回的T都不会为null,只是T的属性为null
    public static final <T> T getObject(String jsontext, Class<T> clazz) {
        T t = null;
        try {
            t = new GsonBuilder().create().fromJson(jsontext, clazz);
        } catch (Exception e) {
            XLog.error("json字符串转换失败" + jsontext, e);
        }
        return t;
    }

    /**
     * 解析实体类
     *
     * @param jsontext
     * @param obj_str
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> T getObject(String jsontext, String obj_str,
                                        Class<T> clazz) {
        JsonObject jsonobj = new GsonBuilder().create().fromJson(jsontext, JsonObject.class);
        if (jsonobj == null) {
            return null;
        }

        Object obj = jsonobj.get(obj_str);
        if (obj == null) {
            return null;
        }

        if (obj instanceof JsonObject) {
            return new GsonBuilder().create().fromJson(jsonobj.getAsJsonObject(obj_str).toString(), clazz);
        } else {
            if (obj instanceof JsonArray) {
                return new GsonBuilder().create().fromJson(toJSONString(obj), new ListOfJson<T>(clazz));
            } else {
                XLog.e(jsontext);
            }
        }

        return null;
    }

    /**
     * 解析实体类
     *
     * @param jsontext
     * @param obj_str
     * @return
     */
    public static final String getString(String jsontext, String obj_str) {
        JsonObject jsonobj = new GsonBuilder().create().fromJson(jsontext, JsonObject.class);
        if (jsonobj == null) {
            return null;
        }
        if (jsonobj.get(obj_str)==null){
            return null;
        }
        String obj = jsonobj.get(obj_str).getAsString();
        if (obj == null) {
            return null;
        }
        return obj;
    }


    public static final String getXString(String jsontext, String obj_str) {
        JsonObject jsonobj = new GsonBuilder().create().fromJson(jsontext, JsonObject.class);
        if (jsonobj == null) {
            return null;
        }
        String obj = jsonobj.get(obj_str).toString();
        if (obj == null) {
            return null;
        }
        return obj;
    }


    /**
     * @param jsontext
     * @param obj_str
     * @return
     */
    public static final int getInt(String jsontext, String obj_str) {
        JsonObject jsonobj = new GsonBuilder().create().fromJson(jsontext, JsonObject.class);
        if (jsonobj == null) {
            return 0;
        }

        Object obj = jsonobj.get(obj_str);
        if (obj == null) {
            return 0;
        }
        try {
            return Double.valueOf(obj.toString()).intValue();
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * @param jsontext
     * @param obj_str
     * @return
     */
    public static final double getDouble(String jsontext, String obj_str) {
        JsonObject jsonobj = new GsonBuilder().create().fromJson(jsontext, JsonObject.class);
        if (jsonobj == null) {
            return 0;
        }

        Object obj = jsonobj.get(obj_str);
        if (obj == null) {
            return 0;
        }
        try {
            return Double.valueOf(obj.toString());
        } catch (Exception e) {
            return 0;
        }

    }
    /**
     * 解析集合
     *
     * @param jsontext
     * @param list_str
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> List<T> getList(String jsontext, String list_str,
                                            Class<T> clazz) {
        JsonObject jsonobj = new GsonBuilder().create().fromJson(jsontext, JsonObject.class);
        if (jsonobj == null) {
            return null;
        }
        Object obj = jsonobj.get(list_str);
        if (obj == null) {
            return null;
        }
        // if(obj instanceof JSONObject){}
        if (obj instanceof JsonArray) {
            return new GsonBuilder().create().fromJson(toJSONString(obj), new ListOfJson<T>(clazz));
        }
        return null;
    }


    public static final String toJSONString(Object object) {
        if (object instanceof String) {
            return object.toString();
        }
        return new GsonBuilder().create().toJson(object);
    }

    /**
     * @param jsonStr json字符串
     * @param clazz   class名称
     * @return
     * @Description： json字符串转成为List
     * @author: GuXiYang
     * @date: 2017/05/08 10:25:00
     */
    public static <T> List<T> getList(String jsonStr, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        try {
            // 生成List<T> 中的 List<T>
            return new GsonBuilder().create().fromJson(jsonStr, new ListOfJson<T>(clazz));
        } catch (Exception e) {
            XLog.error("json字符串转List失败！" + jsonStr, e);
        }
        return list;
    }


    /**
     * @param jsonString json字符串
     * @return
     * @Description： json字符串转换成list<Map>
     * @author: GuXiYang
     * @date: 2017/05/08 10:27:16
     */
    public static List<Map<String, Object>> listKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            return new GsonBuilder().create().fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {
            }.getType());
        } catch (Exception e) {
            XLog.error("json字符串转map失败", e);
        }
        return list;
    }

    /**
     * @param jsonStr json字符串
     * @return
     * @Description： json字符串转换为Map
     * @author: GuXiYang
     * @date: 2017/05/08 10:32:33
     */
    public static Map<String, Object> json2Map(String jsonStr) {
        try {
            return new GsonBuilder().create().fromJson(jsonStr, new TypeToken<Map<String, Object>>() {
            }.getType());
        } catch (Exception e) {
            XLog.error("json字符串转换失败！" + jsonStr, e);
        }
        return null;
    }
}
