package com.flym.yh.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


import com.flym.yh.R;
import com.flym.yh.base.BaseActivity;

import butterknife.BindView;


/**
 * Created by Morphine on 2017/9/2.
 */

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.web_view)
    WebView webView;

    String url;
    private String html;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    @Override
    public String getPagerTitle() {
        return "";
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        url = getIntent().getStringExtra("url");
        html = getIntent().getStringExtra("html");
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initData() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        if (!TextUtils.isEmpty(url) && TextUtils.isEmpty(html)) {
            webView.loadUrl(url);
        } else if (!TextUtils.isEmpty(html) && TextUtils.isEmpty(url)) {
            webView.loadDataWithBaseURL("http://119.23.126.233:163/",html, "text/html; charset=UTF-8", null,null);
        }
        // 添加一个对象, 让JS可以访问该对象的方法, 该对象中可以调用JS中的方法
//        webView.addJavascriptInterface(new JSInterface(), "shop");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                toolbarTitle.setText(webView.getTitle());
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }

//    class JSInterface {
//        //JavaScript调用此方法,点击跳转到店铺详情页
//        @JavascriptInterface
//        public void shop(String orgid) {
//            ActivityManager.getInstance().startNextActivity(new Intent(WebViewActivity.this, ShopDetailsActivity.class).putExtra("orgid", orgid));
//        }
//
//        //立即购买
//        @JavascriptInterface
//        public void payNow(String orgid) {
//
//        }
//    }
}
