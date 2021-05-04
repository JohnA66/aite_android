package com.haoniu.quchat.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.chat.R;
import com.bumptech.glide.Glide;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.EaseUI;
import com.haoniu.quchat.base.EaseUI.EaseUserProfileProvider;
import com.haoniu.quchat.domain.EaseUser;
import com.haoniu.quchat.http.AppConfig;
import com.hyphenate.chat.EMMessage;
import com.zds.base.ImageLoad.GlideUtils;
import com.zds.base.json.FastJsonUtil;

public class EaseUserUtils {

    static EaseUserProfileProvider userProvider;

    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }

    /**
     * get EaseUser according username
     *
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username) {
        if (userProvider != null) {
            return userProvider.getUser(username);
        }

        return null;
    }


    /**
     * set user avatar
     *
     * @param message
     */
    public static void setUserAvatar(Context context, EMMessage message, ImageView imageView) {
        EaseUser user = getUserInfo(message.getFrom());
        if (user != null && !StringUtil.isEmpty(user.getNickname()) && user.getNickname().contains(Constant.ID_REDPROJECT)) {
            String json = FastJsonUtil.toJSONString(message.ext());
            String h = FastJsonUtil.getString(json, "headImg");
            try {
//                int avatarResId = Integer.parseInt(user.getAvatar());
//                GlideUtils.loadImageView(avatarResId, imageView);
                GlideUtils.loadImageViewLoding(AppConfig.checkimg(h), imageView, R.mipmap.img_default_avatar);
            } catch (Exception e) {
                //use default avatar
                GlideUtils.loadImageViewLoding(AppConfig.checkimg(h), imageView, R.mipmap.img_default_avatar);
            }
            return;
        }
        if (user != null && user.getAvatar() != null) {
            try {
//                int avatarResId = Integer.parseInt(user.getAvatar());
//                GlideUtils.loadImageView(avatarResId, imageView);
                GlideUtils.loadImageViewLoding(AppConfig.checkimg(user.getAvatar()), imageView, R.mipmap.img_default_avatar);
            } catch (Exception e) {
                //use default avatar
                GlideUtils.loadImageViewLoding(AppConfig.checkimg(user.getAvatar()), imageView, R.mipmap.img_default_avatar);
            }
        } else {
            Glide.with(context).load(R.mipmap.img_default_avatar).into(imageView);
        }
    }

    /**
     * set user's nickname
     */
    public static void setUserNick(String username, TextView textView) {

        if (textView != null) {
            EaseUser user = getUserInfo(username);
            if (user != null && user.getNickname() != null) {
                textView.setText(user.getNickname());
            } else {
                textView.setText(username);
            }
        }
    }

}
