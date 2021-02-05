package com.haoniu.quchat.utils;

import com.haoniu.quchat.base.EaseUI;
import com.haoniu.quchat.domain.EaseAvatarOptions;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.widget.EaseImageView;

public class ImageUtil {


    public static String checkimg(String path) {
        if (path == null) {
            return "";
        }
        if (path.contains("http://") || path.contains("https://")) {
            return path;
        } else {
            return AppConfig.ImageMainUrl + path;
        }
    }

    /**
     * 设置头像形状
     *
     * @param userAvatarView
     */
    public static void setAvatar(EaseImageView userAvatarView) {
        EaseAvatarOptions avatarOptions = EaseUI.getInstance().getAvatarOptions();
        if (avatarOptions != null && userAvatarView instanceof EaseImageView) {
            EaseImageView avatarView = ((EaseImageView) userAvatarView);
            if (avatarOptions.getAvatarShape() != 0) {
                avatarView.setShapeType(avatarOptions.getAvatarShape());
            }
            if (avatarOptions.getAvatarBorderWidth() != 0) {
                avatarView.setBorderWidth(avatarOptions.getAvatarBorderWidth());
            }
            if (avatarOptions.getAvatarBorderColor() != 0) {
                avatarView.setBorderColor(avatarOptions.getAvatarBorderColor());
            }
            if (avatarOptions.getAvatarRadius() != 0) {
                avatarView.setRadius(avatarOptions.getAvatarRadius());
            }
        }
    }

}
