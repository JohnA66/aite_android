package com.haoniu.quchat.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.utils.BitmapUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.zds.base.Toast.ToastUtil;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.BitmapCallback;
import com.zxy.tiny.callback.FileCallback;

import java.io.ByteArrayOutputStream;

/**
 * 作   者：赵大帅
 * 描   述:
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/12/6 10:34
 * 更新日期: 2017/12/6
 */
public class ShareUtil {
    /**
     * 微信分享
     *
     * @param context
     * @param url
     * @param title
     * @param des
     * @param res
     * @param type    SendMessageToWX.Req.WXSceneTimeline（微信朋友圈）    SendMessageToWX.Req.WXSceneSession (微艾特天)
     */
    public static void shareUrl(final Context context, final String url, final String title, final String des, final int res, final int type) {

        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                WXWebpageObject webpageObject = new WXWebpageObject();
                webpageObject.webpageUrl = url;
                WXMediaMessage msg = new WXMediaMessage(webpageObject);
                msg.title = title;
                msg.description = des;
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), res);
                bitmap=drawBg4Bitmap(Color.WHITE,bitmap);
                msg.thumbData = BitmapUtil.bmpToByteArray(bitmap, true);
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = System.currentTimeMillis() + "";
                req.message = msg;
                req.scene = type;
                MyApplication.getInstance().registerWx().sendReq(req);
            }
        });
    }
    public static Bitmap drawBg4Bitmap(int color, Bitmap orginBitmap) {
        Paint paint = new Paint();
        paint.setColor(color);
        Bitmap bitmap = Bitmap.createBitmap(orginBitmap.getWidth(),
                orginBitmap.getHeight(), orginBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(0, 0, orginBitmap.getWidth(), orginBitmap.getHeight(), paint);
        canvas.drawBitmap(orginBitmap, 0, 0, paint);
        return bitmap;
    }

    /**
     * 微信分享
     *
     * @param context
     * @param url
     * @param title
     * @param des
     * @param res
     * @param type    SendMessageToWX.Req.WXSceneTimeline（微信朋友圈）    SendMessageToWX.Req.WXSceneSession (微艾特天)
     */
    public static void shareUrl(final Context context, final String url, final String title, final String des, final String res, final int type) {

        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(context).asBitmap().load(res).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull final Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                        options.size = 32;
                        Tiny.getInstance().source(resource).asFile().withOptions(options).compress(new FileCallback() {
                            @Override
                            public void callback(boolean isSuccess, String outfile, Throwable t) {
                                Bitmap bs = resource;
                                if (isSuccess) {
                                    Bitmap bitmap = BitmapFactory.decodeFile(outfile);
                                    bs = bitmap;
                                }
                                WXWebpageObject webpageObject = new WXWebpageObject();
                                webpageObject.webpageUrl = url;
                                WXMediaMessage msg = new WXMediaMessage(webpageObject);
                                msg.title = title;
                                msg.description = des;
                                msg.thumbData = BitmapUtil.bmpToByteArray(Bitmap.createScaledBitmap(bs, 150, 150, true), true);
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = System.currentTimeMillis() + "";
                                req.message = msg;
                                req.scene = type;
                                MyApplication.getInstance().registerWx().sendReq(req);
                            }
                        });

                    }
                });
            }
        });
    }

    /**
     * 微信分享
     *
     * @param bitmap
     * @param type   SendMessageToWX.Req.WXSceneTimeline（微信朋友圈）    SendMessageToWX.Req.WXSceneSession (微艾特天)
     */
    public static void shareUrlImage(Context context, final String title, final String des, Bitmap bitmap, final int type) {
        Tiny.BitmapCompressOptions options = new Tiny.BitmapCompressOptions();
        Tiny.getInstance().source(bitmap).asBitmap().withOptions(options).compress(new BitmapCallback() {
            @Override
            public void callback(boolean isSuccess, Bitmap bitmap, Throwable t) {
                if (isSuccess) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    WXImageObject webpageObject = new WXImageObject();
                    webpageObject.imageData = baos.toByteArray();
                    WXMediaMessage msg = new WXMediaMessage(webpageObject);
                    msg.title = title;
                    msg.description = des;
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = System.currentTimeMillis() + "";
                    req.message = msg;
                    req.scene = type;
                    MyApplication.getInstance().registerWx().sendReq(req);
                } else {
                    ToastUtil.toast("图片异常");
                }
            }
        });

    }

    public static Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }
}
