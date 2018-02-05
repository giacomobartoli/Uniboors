package com.example.gzano.uniboors;


import android.Manifest;
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
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gzano.uniboors.ViewInterfaces.ActivityView;
import com.example.gzano.uniboors.utils.WebAppInterfaces.ClassDetailsPicker;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class NavigationActivity extends AppCompatActivity implements ActivityView.NavigationView, BeaconConsumer {


    protected static final String TAG = "MonitoringActivity";
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private Context context;
    private BeaconManager beaconManager;
    private TextView destination;
    private String room = "";
    private WebView webView;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        fab = findViewById(R.id.bluetooth_fab);
        Intent intent = getIntent();
        room = intent.getStringExtra("placeName");
        Log.d("TAGWEBViEW", "web view");
        webView = findViewById(R.id.unindors_web_view);
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
                startSensing();


            }
        });
        String summary = "file:///android_asset/ClassDetailedForMobile.html";
        webView.loadUrl(summary);
        webView.addJavascriptInterface(new ClassDetailsPicker(this), "Android");

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


        ImageView img = findViewById(R.id.imageView3);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  beaconManager.unbind((NavigationActivity)context);
            }
        });

    }


    @Override
    public void onFabClick() {

    }

    @Override
    public void onBeaconSelected(@NotNull String placeKey) {

    }


    private void startSensing() {
        beaconManager = BeaconManager.getInstanceForApplication(this);
        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check 
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }
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

        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.i("Bluetooth", "I just saw an beacon for the first time!");
            }

            @Override
            public void didExitRegion(Region region) {
                Log.i("Bluetooth", "I no longer see an beacon");

            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.i("Bluetooth", "I have just switched from seeing/not seeing beacons: " + state);
            }
        });
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                Log.i("#BEACONS FOUND ", "" + beacons.size());
                if (beacons.size() > 0) {
                    Log.i("DISTANCE_M_" + beacons.iterator().next().getId1(), "The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.");
                    //Toast.makeText(getBaseContext(),"The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.",Toast.LENGTH_LONG).show();

                    Beacon b = beacons.iterator().next();
                    Log.i("BEACONID", b.getId1().toString());
                    if (b.getDistance() < 3.0) {

                        beaconManager.removeAllRangeNotifiers();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                createBuilder(room);

//
                            }
                        });
//                            BeaconManager.setAndroidLScanningDisabled(true);
//                        Log.i("Disabled",String.valueOf(BeaconManager.isAndroidLScanningDisabled()));

                        //   associateBeaconWithRoom(beacons);

                    }
                }

            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
        }
    }


    private void createBuilder(String room) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(room + " found");
        builder.setMessage("The class is starting soon. Take a seat.");
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.i("WEBVIEW", "starting new web view content");
                webView.loadUrl("javascript:load()");

            }


        });
        builder.show();
    }

    private boolean associateBeaconWithRoom(Collection<Beacon> beacons) {

        String id1 = "5894d7c1-b6b9-4766-85b9-419843b89471"; //AulaA
        String id2 = "1"; //AulaB
        String id3 = "8"; //AulaC

        double distance = 100.0;
        Beacon closestBeacon = beacons.iterator().next();

        for (Beacon b : beacons) {
            if ((b.getDistance() < distance)) {
                closestBeacon = b;
                distance = b.getDistance();
            }
        }

        if (closestBeacon.getId1().toString().equals(id1)) {
            if (this.room.equals("AulaA")) {
                createBuilder("AULA A");
            }

        } else if (closestBeacon.getId2().toString().equals(id2)) {
            if (this.room.equals("AulaB")) {
                createBuilder("AULA B");
            }
        } else if (closestBeacon.getId3().toString().equals(id3)) {
            if (this.room.equals("AulaC")) {
                createBuilder("AULA C");
            }
        }

        return false;
    }


    @Override
    public void askPermission() {

    }
}
