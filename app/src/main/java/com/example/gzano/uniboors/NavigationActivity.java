package com.example.gzano.uniboors;


import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.example.gzano.uniboors.utils.WebAppInterfaces.BeaconCollector;
import com.example.gzano.uniboors.Presenter.NavigationPresenter;
import com.example.gzano.uniboors.ViewInterfaces.ActivityView;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.jetbrains.annotations.NotNull;

public class NavigationActivity extends AppCompatActivity implements ActivityView.NavigationView {

    protected static final String TAG = "MonitoringActivity";
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private Context context;
    private BeaconManager beaconManager;

    private WebView webView;
    private FloatingActionButton fab;
    private NavigationPresenter navigationPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        fab = findViewById(R.id.bluetooth_fab);

        Log.d("TAGWEBViEW", "web view");
        context = this;
        beaconManager = BeaconManager.getInstanceForApplication(this);

        navigationPresenter = new NavigationPresenter(this, beaconManager);
        navigationPresenter.onCreate();
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (ColorStateList.valueOf(Color.parseColor("#28a745")) == view.getBackgroundTintList()) {
                    view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFF44336")));

                } else {
                    view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#28a745")));

                    startMonitoring();
                }


            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {

        navigationPresenter.startRegionMonitoring();
    }

    @Override
    public void createBuilder(final String room, final String classroomKey) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//             createABuilder(room,classroomKey);
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle(room + " found");
                builder.setMessage("The class is starting soon. Take a seat.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Log.i("WEBVIEW", "starting new web view content");
                        if(classroomKey=="AulaA"){
                            webView.loadUrl("javascript:load('AulaA')");

                        }
                        else{
                            webView.loadUrl("javascript:load('AulaB')");

                        }

                    }


                });
                builder.show();
            }
        });
    }


    @Override
    public void askPermission() {
        //Checking if the device supports BLE
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Uff");
            builder.setMessage("This device does not support Bluetooth LE. Buy another phone.");
            builder.setPositiveButton(android.R.string.ok, null);
            finish();
        }

        //Checking if the Bluetooth is on/off
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Uff");
            builder.setMessage("This device does not support Bluetooth.");
            builder.setPositiveButton(android.R.string.ok, null);
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 2);
            }
        }

    }


    @Override
    public void loadWebViewClass(@NotNull String url) {
        webView = findViewById(R.id.unindors_web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String summary = "file:///android_asset/ClassDetailedForMobile.html";
        webView.loadUrl(summary);
        webView.addJavascriptInterface(new BeaconCollector(this), "Android");

    }

    @Override
    public void startMonitoring() {
        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check 
            if (this.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }
    }

    @Override
    public void addRegionNotifier() {

    }

    @Override
    public void removeRegionNotifier() {

    }

}



