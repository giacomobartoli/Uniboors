package com.example.gzano.uniboors;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.gzano.uniboors.ViewInterfaces.ActivityView;
import com.example.gzano.uniboors.utils.WebAppInterfaces.ClassDetailsPicker;

import org.jetbrains.annotations.NotNull;

public class NavigationActivity extends AppCompatActivity implements ActivityView.NavigationView {


    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        fab = findViewById(R.id.bluetooth_fab);
        WebView webView = findViewById(R.id.unindors_web_view);
        Log.d("TAGWEBViEW", "web view");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (ColorStateList.valueOf(Color.parseColor("#28a745")) == view.getBackgroundTintList()) {
                    view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFF44336")));
                } else {
                    view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#28a745")));

                }


            }
        });
        String summary = "file:///android_asset/ClassDetailedForMobile.html";
        webView.loadUrl(summary);
        webView.addJavascriptInterface(new ClassDetailsPicker(this), "Android");

    }


    @Override
    public void onFabClick() {

    }

    @Override
    public void onBeaconSelected(@NotNull String placeKey) {

    }
}
