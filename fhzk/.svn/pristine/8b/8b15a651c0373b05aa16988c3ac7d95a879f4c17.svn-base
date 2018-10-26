package com.flym.fhzk.ui.activity.goods;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.trade.biz.AlibcConstants;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.flym.fhzk.R;
import com.flym.fhzk.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * @author Morphine
 * @date 2018/1/26.
 */

public class TbWebViewActivity extends BaseActivity {
    @BindView(R.id.collect_btn)
    CheckBox collectBtn;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    WebView webView;

    private static final String TAG = "TbWebViewActivity";
    WebViewClient mWebViewClient;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_goods_detail;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void init(Bundle savedInstanceState) {
        WebSettings mWebSetting = webView.getSettings();
        mWebSetting.setJavaScriptEnabled(true);
        mWebViewClient = new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        };
    }

    @Override
    protected void initData() {
        String url = getIntent().getStringExtra("url");
        String goodsId = getIntent().getStringExtra("goodsId");
        Map<String, String> exParams = new HashMap<>();
        exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
        AlibcBasePage detailPage = new AlibcDetailPage("19280600483");
        if (!TextUtils.isEmpty(url)) {
            detailPage = new AlibcPage(url);
        }
        AlibcTaokeParams alibcTaokeParams = new AlibcTaokeParams();
        alibcTaokeParams.pid = "mm_118409394_42164435_211718364";
        alibcTaokeParams.subPid = "mm_118409394_42164435_211718364";
        alibcTaokeParams.extraParams = new HashMap<>();
        alibcTaokeParams.extraParams.put("taokeAppkey", "24754728");
        AlibcTrade.show(this, webView, mWebViewClient, null, detailPage, new AlibcShowParams(OpenType.H5, false), alibcTaokeParams, exParams, new AlibcTradeCallback() {
            @Override
            public void onTradeSuccess(com.alibaba.baichuan.trade.biz.context.AlibcTradeResult alibcTradeResult) {
                Log.e(TAG, "onTradeSuccess: " + alibcTradeResult.toString());
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e(TAG, "onFailure: " + s);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        CallbackContext.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        AlibcTradeSDK.destory();
        super.onDestroy();
    }
}
