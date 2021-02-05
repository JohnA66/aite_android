package com.haoniu.quchat.utils;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.haoniu.quchat.http.AppConfig;
import com.zds.base.ImageLoad.GlideUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Administrator
 *         日期 2018/8/23
 *         描述
 */

public class ResourUtil {
    public static WebResourceResponse shouldInterceptRequest(WebResourceResponse web, WebView view, String url) {
        WebResourceResponse response = null;
        String img = url.replace(AppConfig.mainUrl, "");
        try {
            if (url.endsWith(".png") || url.endsWith(".jpg") || url.endsWith(".gif")) {
                InputStream openlo = GlideUtils.getDiskCache(url);
                if (openlo != null) {
                    if (url.endsWith(".png")) {
                        response = new WebResourceResponse("image/png", "UTF-8", openlo);
                    } else if (url.endsWith(".gif")) {
                        response = new WebResourceResponse("image/gif", "UTF-8", openlo);
                    } else if (url.endsWith(".jpg")) {
                        response = new WebResourceResponse("image/jpg", "UTF-8", openlo);
                    }
                } else {
                    GlideUtils.loadDiskCache(url);
                }
            }
            if (response == null) {
                try {
                    InputStream open = view.getContext().getAssets().open(img);
                    if (open != null) {
                        if (url.endsWith(".png")) {
                            response = new WebResourceResponse("image/png", "UTF-8", open);
                        } else if (url.endsWith(".gif")) {
                            response = new WebResourceResponse("image/gif", "UTF-8", open);
                        } else if (url.endsWith(".jpg")) {
                            response = new WebResourceResponse("image/jpg", "UTF-8", open);
                        } else if (url.endsWith(".js")) {
                            response = new WebResourceResponse("application/x-javascript", "UTF-8", open);
                        } else if (url.endsWith(".html")) {
                            response = new WebResourceResponse("text/html", "UTF-8", open);
                        } else if (url.endsWith(".css")) {
                            response = new WebResourceResponse("text/css", "UTF-8", open);
                        } else if (url.endsWith(".otf") || url.endsWith(".eot") || url.endsWith(".svg") || url.endsWith(".ttf")
                                || url.endsWith(".woff") || url.endsWith(".woff2") || url.endsWith(".svg")) {
                            response = new WebResourceResponse("application/x-font-ttf", "UTF-8", open);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        if (response == null) {
            return web;
        }
        return response;
    }

    public static boolean shouldOverrideUrlLoading(WebView view, boolean defult, String url) {
        // 如下方案可在非微信内部WebView的H5页面中调出微信支付
        if (url.startsWith("weixin://wap/pay?") || url.startsWith("weixin://")) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            view.getContext().startActivity(intent);
            return true;
        } else if (parseScheme(url)) {
            try {
                Intent intent = Intent.parseUri(url,
                        Intent.URI_INTENT_SCHEME);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setComponent(null);
                view.getContext().startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return defult;
    }

    protected static boolean parseScheme(String url) {
        if (url.contains("platformapi/startapp") || url.contains("platformapi/startApp")) {
            return true;
        } else {
            return false;
        }
    }
}
