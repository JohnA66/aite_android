package com.haoniu.quchat.activity.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aite.chat.R;
import com.haoniu.quchat.base.JsMethod;
import com.haoniu.quchat.base.MyApplication;
import com.haoniu.quchat.base.MyBaseFragment;
import com.haoniu.quchat.entity.EventCenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Administrator
 * 日期 2018/8/15
 * 描述 网页
 */

public class WebViewFragment extends MyBaseFragment {


    Unbinder unbinder;
    @BindView(R.id.wv_find)
    WebView mWebView;
    private String title;
    private String url;


    @Override
    protected int setContentView() {
        return R.layout.activity_web;
    }


    @SuppressLint("NewApi")
    @Override
    protected void initData() {
        webSet();
    }

    @Override
    public void onDestroyView() {
        if (mWebView!=null){
            mWebView.clearCache(true);
            mWebView.clearHistory();
        }
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        clearCookies(MyApplication.getInstance().getApplicationContext());
        super.onDestroy();
    }

    @Override
    protected void onEventComing(EventCenter center) {
    }


    public boolean back() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void initBundle(Bundle extras) {
        title = extras.getString("title");
        url = extras.getString("url");
        //mToolbarTitle.setText(title + "");
    }

    @SuppressLint({"JavascriptInterface", "NewApi"})
    private void webSet() {
        if (mWebView!=null){
            mWebView.clearCache(true);
            mWebView.clearHistory();
        }
        WebSettings setting = mWebView.getSettings();
        //支持js

        //设置字符编码
        setting.setDefaultTextEncodingName("utf-8");
        setting.setJavaScriptEnabled(true);
        setting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        setting.setAllowFileAccess(true); //设置可以访问文件
        setting.setAllowContentAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        clearCookies(getActivity());
        //设置js调用前缀如：（window.android.show()）以及调用的类
        JsMethod jsMethod = new JsMethod(mWebView, getActivity());
        mWebView.addJavascriptInterface(jsMethod, "app");
        WebView.setWebContentsDebuggingEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);


        //在自己的浏览器打开url
        mWebView.setWebViewClient(new WebViewClient() {
            //设置加载成功失败方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = view.getUrl();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (url.contains("alipays://platformapi")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(request.getUrl().toString()));
                        startActivity(intent);
                    } else {
                        return super.shouldOverrideUrlLoading(view, request);
                    }
                } else {
                    if (url.contains("alipays://platformapi")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(request.getUrl().toString()));
                        startActivity(intent);
                    } else {
                        return super.shouldOverrideUrlLoading(view, request);
                    }
                }
                return false;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // 接受网站证书
            }

            //在开始加载网页时会回调
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                //mLlNoNet.setVisibility(View.GONE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            //加载错误的时候会回调
            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                super.onReceivedError(webView, i, s, s1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return;
                }
                //mLlNoNet.setVisibility(View.VISIBLE);
            }

            //加载错误的时候会回调
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
                if (webResourceRequest.isForMainFrame()) {
                    //mLlNoNet.setVisibility(View.VISIBLE);
                }
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, final String message,
                                     final JsResult result) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("提示");
                dialog.setMessage(message);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                });
                return super.onJsAlert(view, url, message, result);
            }
        });

        mWebView.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                //表示按返回键时的操作
                if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                    //后退
                    mWebView.goBack();
                    //已处理
                    return true;
                }
            }
            return false;
        });
        mWebView.loadUrl(url);
    }

    public static void clearCookies(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            cookieManager.removeAllCookie();
            CookieSyncManager.getInstance().sync();
        }
        WebStorage.getInstance().deleteAllData();
    }


    public static Fragment getInstance(String url, String title) {
        WebViewFragment newsFragment = new WebViewFragment();
        Bundle budle = new Bundle();
        budle.putString("url", url);
        budle.putString("title", title);
        newsFragment.setArguments(budle);
        return newsFragment;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
