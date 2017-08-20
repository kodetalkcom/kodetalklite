package com.kodetalk.kodetalklite;

import android.graphics.Color;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * Created by Deb on 8/16/2017.
 */

public class KTChromeClient extends WebChromeClient {

    private ProgressBar progressBar;

    public KTChromeClient(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void onProgressChanged(WebView webView, int progress) {
        progressBar.setProgress(progress);
        progressBar.getProgressDrawable()
                .setColorFilter(Color.parseColor("#283653"), android.graphics.PorterDuff.Mode.MULTIPLY);
        if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }
        if (progress == 100) {
            progressBar.setVisibility(ProgressBar.GONE);
        }
    }
}
