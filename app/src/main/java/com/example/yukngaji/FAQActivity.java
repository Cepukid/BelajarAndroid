package com.example.yukngaji;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class FAQActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        setTitle("FAQ Murid/Santri");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        webView = findViewById(R.id.webfaq);
        webView.setWebChromeClient(new MyWebViewClient());
        progress = findViewById(R.id.progressBar);
        progress.setMax(100);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://sahabatmengaji.com/faq");
        FAQActivity.this.progress.setProgress(0);
    }

    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }

    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            FAQActivity.this.setValue(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }
}
