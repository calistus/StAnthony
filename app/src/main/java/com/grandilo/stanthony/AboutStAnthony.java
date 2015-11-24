package com.grandilo.stanthony;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AboutStAnthony extends AppCompatActivity {
    private WebView aboutWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_st_anthony);
        aboutWebView = (WebView) findViewById(R.id.aboutWebView);
        aboutWebView.setWebViewClient(new WebViewClient());
        aboutWebView.getSettings().setBuiltInZoomControls(true);
        aboutWebView.getSettings().setJavaScriptEnabled(true);
        aboutWebView.loadUrl("http://www.catholic.org/saints/saint.php?saint_id=24");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //    progressBar.setVisibility(View.GONE);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && aboutWebView.canGoBack()) {
            aboutWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}