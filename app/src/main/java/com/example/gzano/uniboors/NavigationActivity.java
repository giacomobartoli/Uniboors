package com.example.gzano.uniboors;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gzano.uniboors.ViewInterfaces.ActivityView;
import com.example.gzano.uniboors.utils.WebAppInterfaces.ClassDetailsPicker;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;


import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class NavigationActivity extends AppCompatActivity implements ActivityView.NavigationView,BeaconConsumer {

    protected static final String TAG = "MonitoringActivity";
    private BeaconManager beaconManager;
    private TextView destination;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private String room = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Intent intent = getIntent();
        destination = findViewById(R.id.destination_chosen);
        destination.setText(intent.getStringExtra("placeName"));
        WebView webView = findViewById(R.id.unindors_web_view);
        room = intent.getStringExtra("placeName");
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
                startSensing();
            }
        });

    }

    @Override
    public void setETA(@NotNull String estimatedTimeOfArrival) {

    }

    @Override
    public void setCurrentPosition(@NotNull String currentPosition) {

    }

    private void startSensing(){
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
                Log.d("Bluetooth", "I just saw an beacon for the first time!");
            }

            @Override
            public void didExitRegion(Region region) {
                Log.d("Bluetooth", "I no longer see an beacon");

            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.d("Bluetooth", "I have just switched from seeing/not seeing beacons: " + state);
            }
        });
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                Log.d("#BEACONS FOUND ",""+beacons.size());
                if (beacons.size() > 0) {
                    Log.d("DISTANCE_M_"+beacons.iterator().next().getId1(), "The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.");
                    //Toast.makeText(getBaseContext(),"The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.",Toast.LENGTH_LONG).show();

                    Beacon b = beacons.iterator().next();
                    if(b.getDistance() < 3.0){

                     //   associateBeaconWithRoom(beacons);
                        createBuilder(room);

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


    private void createBuilder(String room){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(room+" found");
        builder.setMessage("The class is starting soon. Take a seat.");
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
            }

        });
        builder.show();
    }

    private boolean associateBeaconWithRoom(Collection<Beacon> beacons){

         String id1="5894d7c1-b6b9-4766-85b9-419843b89471"; //AulaA
         String id2="1"; //AulaB
         String id3="8"; //AulaC

        double distance = 100.0;
        Beacon closestBeacon=beacons.iterator().next();

        for(Beacon b : beacons){
            if((b.getDistance() < distance)){
                closestBeacon=b;
                distance=b.getDistance();
            }
        }

        if(closestBeacon.getId1().toString().equals(id1)){
            if(this.room.equals("AulaA")){
                createBuilder("AULA A");
            }

        }else if(closestBeacon.getId2().toString().equals(id2)){
            if(this.room.equals("AulaB")){
                createBuilder("AULA B");
            }
        }else if(closestBeacon.getId3().toString().equals(id3)){
            if(this.room.equals("AulaC")){
                createBuilder("AULA C");
            }
        }

        return false;
    }



}
