package com.haoniu.quchat.base;


import com.haoniu.quchat.entity.LoginInfo;
import com.haoniu.quchat.entity.PZInfo;
import com.zds.base.util.Preference;
import com.zds.base.util.Utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

public class Storage {
    /**
     * 保存token
     *
     * @param token
     */
    public static void saveToken(String token) {
        if (token == null) {
            return;
        }
        Preference.saveStringPreferences(Utils.getContext(), "token", token);
    }

    /**
     * 获取token
     */
    public static String getToken() {
        return Preference.getStringPreferences(Utils.getContext(), "token", "");
    }


    /**
     * 清除缓存用户信息数据
     */
    public static void ClearUserInfo() {
        new File(MyApplication.getInstance().getCacheDir().getPath() + "/"
                + "feiyu_userinfo.bean").delete();
    }



    /**
     * 获取Cooike
     */
    public static String getCooike() {
        return Preference.getStringPreferences(Utils.getContext(), "cooike", "");
    }


    /**
     * 保存Cooike
     *
     * @param token
     */
    public static void saveCooike(String token) {
        if (token == null) {
            return;
        }
        Preference.saveStringPreferences(Utils.getContext(), "cooike", token);
    }


    /**
     * CooikeKey
     */
    public static String getCooikeKey() {
        return Preference.getStringPreferences(Utils.getContext(), "cooike_key", "");
    }


    /**
     * 保存CooikeKey
     *
     * @param token
     */
    public static void saveCooikeKey(String token) {
        if (token == null) {
            return;
        }
        Preference.saveStringPreferences(Utils.getContext(), "cooike_key", token);
    }




    /**
     * 清除缓存用户登录信息数据
     */
    public static void ClearUserLoginInfo() {
        new File(MyApplication.getInstance().getCacheDir().getPath() + "/"
                + "youxin_userlogininfo.bean").delete();
    }

    /**
     * 保存用户登录信息至缓存
     *
     * @param loginInfo
     */
    public static void saveUsersLoginInfo(LoginInfo loginInfo) {
        try {
            new ObjectOutputStream(new FileOutputStream(new File(MyApplication
                    .getInstance().getCacheDir().getPath()
                    + "/" + "youxin_userlogininfo.bean"))).writeObject(loginInfo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取缓存数据
     *
     * @return
     */
    public static LoginInfo GetUserLoginInfo() {
        File file = new File(MyApplication.getInstance().getCacheDir().getPath()
                + "/" + "youxin_userlogininfo.bean");
        if (!file.exists()) {
            return null;
        }

        if (file.isDirectory()) {
            return null;
        }

        if (!file.canRead()) {
            return null;
        }

        try {
            @SuppressWarnings("resource")
            LoginInfo loginInfo = (LoginInfo) new ObjectInputStream(
                    new BufferedInputStream(new FileInputStream(file)))
                    .readObject();
            return loginInfo;
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 保存配置信息至缓存
     *
     * @param pzInfoMap
     */
    public static void savePZ(PZInfo pzInfoMap) {
        try {
            new ObjectOutputStream(new FileOutputStream(new File(MyApplication
                    .getInstance().getCacheDir().getPath()
                    + "/" + "feiyu_PZ.bean"))).writeObject(pzInfoMap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取缓存数据配置信息
     *
     * @return
     */
    public static PZInfo GetPZ() {
        File file = new File(MyApplication.getInstance().getCacheDir().getPath()
                + "/" + "feiyu_PZ.bean");
        if (!file.exists()) {
            return null;
        }

        if (file.isDirectory()) {
            return null;
        }

        if (!file.canRead()) {
            return null;
        }

        try {
            @SuppressWarnings("resource")
            PZInfo pzInfoMap = (PZInfo) new ObjectInputStream(
                    new BufferedInputStream(new FileInputStream(file)))
                    .readObject();
            return pzInfoMap;
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存 公告
     *
     * @param id
     */
    public static void saveGG(int id) {
        Preference.saveIntPreferences(Utils.getContext(), "ggid", id);
    }

    /**
     * 获取域名
     */
    public static int getGGId() {
        return Preference.getIntPreferences(Utils.getContext(), "ggid", 0);
    }



    /**
     * 保存全局背景(相机)
     *
     * @param globleBg
     */
    public static void saveGlobalChatBg(String globleBg) {
        if (globleBg == null) {
            return;
        }
        Preference.saveStringPreferences(Utils.getContext(), "globle_bg", globleBg);
    }

    /**
     * 获取全局背景（相机）
     */
    public static String getGlobleChatBg() {
        return Preference.getStringPreferences(Utils.getContext(), "globle_bg", "");
    }


    /**
     * 保存全局背景(本地mipmap)
     *
     * @param local_globle_bg
     */
    public static void saveGlobalChatBgLocal(int local_globle_bg) {
        Preference.saveIntPreferences(Utils.getContext(), "local_globle_bg", local_globle_bg);
    }

    /**
     * 获取全局背景（本地mipmap）
     */
    public static int getGlobleChatBgLocal() {
        return Preference.getIntPreferences(Utils.getContext(), "local_globle_bg", 0);
    }


    /**
     * 保存聊天进入设置背景（本地mipmap）
     *
     * @param local_globle_bg
     */
    public static void saveChatBgLocal(String id, int local_globle_bg) {
        Preference.saveIntPreferences(Utils.getContext(), id, local_globle_bg);
    }

    /**
     * 获取聊天进入设置背景（本地mipmap）
     */
    public static int getChatBgLocal(String id) {
        return Preference.getIntPreferences(Utils.getContext(), id, 0);
    }


    /**
     * 是否保存图片到相册 1保存 0趣小保存
     */
    public static void saveImage(String tag){
        Preference.saveStringPreferences(Utils.getContext(), "saveImages", tag);
    }
    public static String getImage(){
        return Preference.getStringPreferences(Utils.getContext(), "saveImages", "0");
    }

}
