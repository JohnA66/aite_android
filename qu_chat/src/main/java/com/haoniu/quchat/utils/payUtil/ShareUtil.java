package com.haoniu.quchat.utils.payUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.haoniu.quchat.base.MyApplication;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

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
    public static void shareUrl(Context context, String url, String title, String des, int res, int type) {
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = title;
        msg.description = des;
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), res);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        thumb.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        msg.thumbData = baos.toByteArray();
        SendMessageToWX.Req req = new SendMessageToWX.Req();

        req.transaction = System.currentTimeMillis() + "";
        req.message = msg;
        req.scene = type;
        MyApplication.getInstance().registerWx().sendReq(req);
    }

}
