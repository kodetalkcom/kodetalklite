package com.kodetalk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        webView = (WebView) findViewById(R.id.webView);

        if(savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        } else {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(false);
            webView.getSettings().setBuiltInZoomControls(false);
            webView.getSettings().setLoadWithOverviewMode(false);
            webView.getSettings().setUseWideViewPort(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.setBackgroundColor(Color.WHITE);
            webView.setWebViewClient(new ourViewClient());
            webView.setWebChromeClient(new WebChromeClient(){
                public void onProgressChanged(WebView webView, int progress) {
                    progressBar.setProgress(progress);
                    if(progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                        progressBar.setVisibility(ProgressBar.VISIBLE);
                    }
                    if(progress == 100) {
                        progressBar.setVisibility(ProgressBar.GONE);
                    }
                }
            });
            if(!checkIfInternetConnectionThere(getApplicationContext())){
                makeAToast();
            }else {
                String data = getIntent().getDataString();
                if (Intent.ACTION_VIEW.equals(getIntent().getAction())) {
                    webView.loadUrl(data);
                } else {
                    webView.loadUrl("https://www.kodetalk.com/home");
                }
            }
        }
    }

    public class ourViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView v, String url) {
            if(url.contains("kodetalk.com")) {
                v.loadUrl(url);
                CookieManager.getInstance().setAcceptCookie(true);
            } else {
                Uri uri = Uri.parse(url);
                startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, uri), "Choose Browser"));
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView v, String url) {
            super.onPageFinished(v, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            makeAToast();
        }
    }

    public void makeAToast() {
        webView.loadUrl("about:blank");
        Toast.makeText(getBaseContext(), "Internet connection is not avialable", Toast.LENGTH_SHORT).show();
    }

    public boolean checkIfInternetConnectionThere(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
