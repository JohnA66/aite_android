package com.haoniu.quchat.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.chat.R;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.base.BaseAndroidJs;
import com.haoniu.quchat.base.Constant;
import com.haoniu.quchat.base.EaseConstant;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.global.UserComm;
import com.haoniu.quchat.utils.BitmapUtil;
import com.haoniu.quchat.utils.ResourUtil;
import com.haoniu.quchat.widget.CommonDialog;
import com.zds.base.Toast.ToastUtil;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.BitmapCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 作   者：赵大帅
 * 描   述: 网页
 * 邮   箱: 2510722254@qqq.com
 * 日   期: 2017/12/2 16:46
 * 更新日期: 2017/12/2
 *
 * @author Administrator
 */
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.bar)
    View mBar;
    @BindView(R.id.ll_back)
    LinearLayout mLlBack;
    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubtitle;
    @BindView(R.id.img_right)
    ImageView mImgRight;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.webview)
    WebView mWebview;
    @BindView(R.id.llayout_title_1)
    RelativeLayout mLlayoutTitle1;
    private String title;
    private String url;
    private String groupId;
    private Boolean isRedPage;
    private int isBackLastPage = 0;

    /**
     * 替代onCreate的使用
     *
     * @param bundle
     */
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_webview);
    }

    /**
     * 初始化逻辑
     */
    @Override
    protected void initLogic() {
        if (title.equals("lan")) {
            mLlayoutTitle1.setVisibility(View.GONE);
            mWebview.setInitialScale(100);
        }
        setTitle(title);

        if (isRedPage) {
            mToolbarSubtitle.setVisibility(View.GONE);
        }

        mLlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRedPage) {
                    if (isBackLastPage == 0) {
                        finish();
                        return;
                    }
                    Intent intent = new Intent(WebViewActivity.this,
                            ChatActivity.class);
                    intent.putExtra(Constant.EXTRA_USER_ID, groupId).putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                }
            }
        });

        WebSettings webSetting = mWebview.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setDisplayZoomControls(false);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setLoadWithOverviewMode(true);
        setCookie(url);
        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
//                toast(newProgress+"");
                Log.e("fsfds",newProgress+"");
                if (newProgress==100){
                    if (url.toString().length()>1500) {
                        finish();
                    }
                }
            }
        });

        mWebview.addJavascriptInterface(new BaseAndroidJs(this, mWebview),
                "app");
        mWebview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult result = ((WebView) v).getHitTestResult();
                if (null == result)
                    return false;
                int type = result.getType();
                if (type == WebView.HitTestResult.UNKNOWN_TYPE)
                    return false;
                if (type == WebView.HitTestResult.EDIT_TEXT_TYPE) {
                    //let TextViewhandles context menu return true;
                }
                switch (type) {
                    case WebView.HitTestResult.PHONE_TYPE: // 处理拨号
                        break;
                    case WebView.HitTestResult.EMAIL_TYPE: // 处理Email
                        break;
                    case WebView.HitTestResult.GEO_TYPE: // TODO
                        break;
                    case WebView.HitTestResult.SRC_ANCHOR_TYPE: // 超链接
                        // Log.d(DEG_TAG, "超链接");
                        break;
                    case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                        break;
                    case WebView.HitTestResult.IMAGE_TYPE: // 处理长按图片的菜单项
                        toSelectPic(result.getExtra());
                        //通过GestureDetector获取按下的位置，来定位PopWindow显示的位置
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        WebViewClient webViewClient = new WebViewClient() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (isRedPage) {
                    String[] payStatus = url.split("status=");
                    if (payStatus.length > 1) {
                        isBackLastPage = payStatus[1].contains("unpay") ? 0 : 1;
                    }
                }
                return ResourUtil.shouldOverrideUrlLoading(view, super.shouldOverrideUrlLoading(view, request), url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return ResourUtil.shouldOverrideUrlLoading(view, super.shouldOverrideUrlLoading(view, url), url);
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return ResourUtil.shouldInterceptRequest(super.shouldInterceptRequest(view, request), view, request.getUrl().toString());
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              String url) {
                return ResourUtil.shouldInterceptRequest(super.shouldInterceptRequest(view, url), view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, android.net.http.SslError error) { // 重写此方法可以让webview处理https请求
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        };


        //url长度大于1500个字节 代表是html网页,执行加再网页代码
        if (url.toString().length()<1500) {
            mWebview.setWebViewClient(webViewClient);
            Map<String, String> additionalHttpHeaders = new HashMap<>();
            additionalHttpHeaders.put("token", UserComm.getToken());
            mWebview.loadUrl(url, additionalHttpHeaders);
        }else {
            String detailHtml = url;
            mWebview.loadData(detailHtml, "text/html", "UTF-8");
        }

    }

    /**
     * 保存图片dialog
     */
    private void toSelectPic(final String url) {
        final CommonDialog.Builder builder =
                new CommonDialog.Builder(this).fullWidth().fromBottom()
                        .setView(R.layout.dialog_save_pic);
        builder.setOnClickListener(R.id.tv_cell, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        builder.setOnClickListener(R.id.tv_xiangce, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                save();
            }
        });
        builder.create().show();
    }

    private void save() {
        if (PermissionsUtil.hasPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            saveFile();
        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                        @Override
                        public void permissionGranted(@NonNull String[] permission) {
                            saveFile();
                        }

                        @Override
                        public void permissionDenied(@NonNull String[] permission) {
                            ToastUtil.toast("请打开读写权限");
                        }
                    }, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }


    }

    /**
     * 保存图片
     */
    private void saveFile() {
        showLoading("正在保存...");
        try {
            Tiny.getInstance().source(createViewBitmap(mWebview)).asBitmap().compress(new BitmapCallback() {
                @Override
                public void callback(boolean isSuccess, Bitmap bitmap1,
                                     Throwable t) {
                    if (isSuccess) {
                        BitmapUtil.saveBitmapInFile(WebViewActivity.this,
                                bitmap1);
                        ToastUtil.toast("保存成功");
                    } else {
                        ToastUtil.toast("保存失败");
                    }
                    dismissLoading();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toast("保存失败");
                    dismissLoading();
                }
            });
        }
    }

    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    protected boolean parseScheme(String url) {
        if (url.contains("platformapi/startapp") || url.contains("platformapi" +
                "/startApp")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * EventBus接收消息
     *
     * @param center 获取事件总线信息
     */
    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    public void onBackPressed() {
        if (isRedPage) {
            if (isBackLastPage == 0) {
                finish();
                return;
            }
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra(Constant.EXTRA_USER_ID, groupId).putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
            startActivity(intent);
            finish();
        } else {
            if (mWebview.canGoBack()) {
                mWebview.goBack();
            } else {
                super.onBackPressed();
            }
        }
    }

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    @Override
    protected void getBundleExtras(Bundle extras) {
        title = extras.getString("title", "");
        url = extras.getString("url", "");
        groupId = extras.getString("groupId", "");
        isRedPage = extras.getBoolean("isRedPage", false);
    }

    public void setCookie(String url) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                CookieSyncManager.createInstance(getApplicationContext());
            }

            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);

            if (UserComm.IsOnLine()) {
                cookieManager.setCookie(url,
                        UserComm.getUserInfo().getSessionKey() + "=" + UserComm.getUserInfo().getSessionValue() +
                                ";path=/");
            } else {
                try {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                        cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                            @Override
                            public void onReceiveValue(Boolean value) {

                            }
                        });
                    }

                } catch (Exception e) {

                }
            }

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                cookieManager.flush();
                String newCookie = cookieManager.getCookie(url);

            }
        } catch (Exception e) {

        }
    }
}
