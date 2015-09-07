package com.phicomm.account.register;

import com.phicomm.account.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;

@SuppressLint("JavascriptInterface")
public class RegWebView extends Activity {
    private WebView mWebView;
    private Handler mHandler = new Handler();

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.webviewdemo);
        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new Object() {
            public void clickOnAndroid() {
                mHandler.post(new Runnable() {
                    public void run() {
                        mWebView.loadUrl("javascript:wave()");
                    }
                });
            }
        }, "register");
        mWebView.loadUrl("http://172.16.99.52:8080/webProject/wap/reg.jsp");
    }
}
