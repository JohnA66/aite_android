package com.haoniu.quchat.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.aite.chat.R;
import com.haoniu.quchat.adapter.RecommandAdapter;
import com.haoniu.quchat.base.BaseActivity;
import com.haoniu.quchat.entity.EventCenter;
import com.haoniu.quchat.help.RclViewHelp;
import com.haoniu.quchat.http.ApiClient;
import com.haoniu.quchat.http.AppConfig;
import com.haoniu.quchat.http.ResultListener;
import com.haoniu.quchat.model.RecommandInfo;
import com.zds.base.json.FastJsonUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @author lhb
 */
public class FindDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.webview)
    WebView mWebview;
    @BindView(R.id.rvv_recommand)
    RecyclerView mRvvRecommand;

    private RecommandAdapter mRecommandAdapter;
    private List<RecommandInfo> mRecommandInfoList = new ArrayList<>();

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_find_detail);

    }

    @Override
    protected void initLogic() {
        mToolbarTitle.setText("详情");
        mRecommandAdapter = new RecommandAdapter(mRecommandInfoList);
        RclViewHelp.initRcLmVertical(this, mRvvRecommand, mRecommandAdapter);
        RclViewHelp.recycleviewAndScrollView(mRvvRecommand);
        WebSettings webSettings = mWebview.getSettings();
        //隐藏webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        //支持js
        webSettings.setJavaScriptEnabled(true);
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 得到 URL 可以传给应用中的某个 WebView 页面加载显示
                return true;
            }
        });
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        getDetail(extras.getString("id"));
    }


    private void getDetail(String id) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("newsId", id);
        ApiClient.requestNetHandle(this, AppConfig.FIND_NEW_BY_ID, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                String url = FastJsonUtil.getString(json, "newsDetails");
                String css = "<style type=\"text/css\"> </style>";
                String html = "<html><header><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no>" + css + "</header>" + "<body>" + url + "</body>" + "</html>";
                mWebview.loadDataWithBaseURL(null, getNewContent(html), "text/html", "UTF-8", null);
                getRecommand();

            }

            @Override
            public void onFailure(String msg) {
                toast(msg);
            }
        });
    }

    private void getRecommand() {
        Map<String, Object> map = new HashMap<>(1);
        ApiClient.requestNetHandle(this, AppConfig.FIND_PUSH_NEWS, "", map, new ResultListener() {
            @Override
            public void onSuccess(String json, String msg) {
                if (FastJsonUtil.getList(json, RecommandInfo.class) != null) {
                    mRecommandInfoList.addAll(FastJsonUtil.getList(json, RecommandInfo.class));
                    mRecommandAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String msg) {
            }
        });
    }


    private String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }
}
