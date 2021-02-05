package com.zds.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.tools.Constant;
import com.zds.base.R;

import java.io.File;

/**
 * Created by zlw on 2018/07/26/下午 2:23
 * 选择图片
 */

public class SelectFileUtil {
    public static String pathUrl = "";


    /**
     * 调用系统相机
     *
     * @param activity
     */
    public static void selectSystemCameraImageOne(Activity activity) {
        pathUrl =getDiskCacheDir(activity)+ "/cityHelp_" + System.currentTimeMillis() + ".jpeg";
        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
            Uri imageUri = parUri(new File(pathUrl), activity);
            cameraIntent.putExtra("output", imageUri);
            activity.startActivityForResult(cameraIntent, 1111);
        }
    }

    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
            || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    private static Uri parUri(File cameraFile, Activity activity) {
        String authority = activity.getPackageName() + ".provider";
        Uri imageUri;
        if (Build.VERSION.SDK_INT > 23) {
            imageUri = FileProvider.getUriForFile(activity, authority, cameraFile);
        } else {
            imageUri = Uri.fromFile(cameraFile);
        }

        return imageUri;
    }

    /**
     * 单张照相机
     *
     * @param activity
     * @param isCrop
     */
    public static void selectCameraImageOne(Activity activity, boolean isCrop) {
        if (isCrop) {
            PictureSelector.create(activity)
                    .openCamera(PictureMimeType.ofImage())
                    .maxSelectNum(1)
                    .imageSpanCount(4)
                    .isCamera(true)
                    .enableCrop(true)
                    .compress(false)// 是否压缩 true or false
                    .freeStyleCropEnabled(true)
                    .withAspectRatio(1, 1)// int 裁剪比例 如 16:9 3:2 3:4 1:1 可自定义
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                    .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为 false   true or false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为 false    true or false
                    .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                    .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                    .selectionMode(PictureConfig.MULTIPLE)
                    .forResult(1111);
        } else {
            PictureSelector.create(activity)
                    .openCamera(PictureMimeType.ofImage())
                    .selectionMode(PictureConfig.SINGLE)
                    .forResult(1111);
        }
    }

    /**
     * 多张照相机
     *
     * @param activity
     * @param imageNum
     * @param isCrop
     */
    public static void selectCameraImageMore(Activity activity, int imageNum, boolean isCrop) {
        if (isCrop) {
            PictureSelector.create(activity)
                    .openCamera(PictureMimeType.ofImage())
                    .maxSelectNum(imageNum)
                    .imageSpanCount(4)
                    .isCamera(true)
                    .enableCrop(true)
                    .compress(false)// 是否压缩 true or false
                    .freeStyleCropEnabled(true)
                    .withAspectRatio(1, 1)// int 裁剪比例 如 16:9 3:2 3:4 1:1 可自定义
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                    .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为 false   true or false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为 false    true or false
                    .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                    .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                    .selectionMode(PictureConfig.MULTIPLE)
                    .forResult(1111);
        } else {
            PictureSelector.create(activity)
                    .openCamera(PictureMimeType.ofImage())
                    .maxSelectNum(imageNum)
                    .imageSpanCount(4)
                    .isCamera(true)
                    .compress(true)// 是否压缩 true or false
                    .freeStyleCropEnabled(true)
                    .selectionMode(PictureConfig.MULTIPLE)
                    .forResult(1111);
        }
    }

    /**
     * 单张图库图片
     *
     * @param activity
     * @param isCrop
     */
    public static void selectGalleryImageOne(Activity activity, boolean isCrop) {
        if (isCrop) {
            PictureSelector.create(activity)
                    .openGallery(PictureMimeType.ofImage())
                    .maxSelectNum(1)
                    .imageSpanCount(4)
                    .isCamera(true)
                    .enableCrop(true)
                    .compress(false)// 是否压缩 true or false
                    .freeStyleCropEnabled(true)
                    .withAspectRatio(1, 1)// int 裁剪比例 如 16:9 3:2 3:4 1:1 可自定义
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                    .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为 false   true or false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为 false    true or false
                    .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                    .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                    .selectionMode(PictureConfig.MULTIPLE)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        } else {
            PictureSelector.create(activity)
                    .openGallery(PictureMimeType.ofImage())
                    .maxSelectNum(1)
                    .imageSpanCount(4)
                    .isCamera(true)
                    .compress(true)// 是否压缩 true or false
                    .freeStyleCropEnabled(true)
                    .selectionMode(PictureConfig.MULTIPLE)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }
    }

    /**
     * 多张图库图片
     *
     * @param activity
     * @param imageNum
     * @param isCrop
     */
    public static void selectGalleryImageMore(Activity activity, int imageNum, boolean isCrop) {
        if (isCrop) {
            PictureSelector.create(activity)
                    .openGallery(PictureMimeType.ofImage())
                    .maxSelectNum(imageNum)
                    .imageSpanCount(4)
                    .isCamera(true)
                    .enableCrop(true)
                    .compress(false)// 是否压缩 true or false
                    .freeStyleCropEnabled(true)
                    .withAspectRatio(1, 1)// int 裁剪比例 如 16:9 3:2 3:4 1:1 可自定义
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                    .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为 false   true or false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为 false    true or false
                    .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                    .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                    .selectionMode(PictureConfig.MULTIPLE)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        } else {
            PictureSelector.create(activity)
                    .openGallery(PictureMimeType.ofImage())
                    .maxSelectNum(imageNum)
                    .imageSpanCount(4)
                    .isCamera(true)
                    .compress(true)// 是否压缩 true or false
                    .freeStyleCropEnabled(true)
                    .selectionMode(PictureConfig.MULTIPLE)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }
    }

}
