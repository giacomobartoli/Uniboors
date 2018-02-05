package com.example.gzano.uniboors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.gzano.uniboors.ViewInterfaces.ActivityView;
import com.example.gzano.uniboors.utils.WebAppInterfaces.ClassDetailsPicker;

import org.jetbrains.annotations.NotNull;

public class NavigationActivity extends AppCompatActivity implements ActivityView.NavigationView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Intent intent = getIntent();

        WebView webView = findViewById(R.id.unindors_web_view);
        Log.d("TAGWEBViEW", "web view");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                Log.d("MyApplication", message + " -- From line "
                        + lineNumber + " of "
                        + sourceID);
            }
        });
        String summary = "file:///android_asset/ClassDetailedForMobile.html";
        webView.loadUrl(summary);
        webView.addJavascriptInterface(new ClassDetailsPicker(this), "Android");

    }

    @Override
    public void setETA(@NotNull String estimatedTimeOfArrival) {

    }

    @Override
    public void setCurrentPosition(@NotNull String currentPosition) {

    }


}
