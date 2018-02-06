package com.example.gzano.uniboors.Presenter;

import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.gzano.uniboors.Presenter.PresenterInterface.Presenter;
import com.example.gzano.uniboors.ViewInterfaces.ActivityView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

/**
 * Created by gzano on 05/02/2018.
 */

public class NavigationPresenter implements Presenter.NavigationPresenter {
    private ActivityView.NavigationView navigationView;
    private BeaconManager beaconManager;

    public NavigationPresenter(ActivityView.NavigationView navigationView, BeaconManager beaconManager) {
        this.navigationView = navigationView;
        this.beaconManager = beaconManager;
    }

    @Override
    public void handleBeacon(@NonNull String classroomName) {
//        Log.i("WEBVIEW", "starting new web view content");
//        classDetailsPicker.setBeaconRoomKeyPicked(classroomKey);
//        Log.i("tagdet", classDetailsPicker.getBeaconRoomKeyPicked());
//        if (classDetailsPicker.getBeaconRoomKeyPicked() != null)
//            webView.loadUrl("javascript:load()");
    }


    @Override
    public void onCreate() {
        navigationView.loadWebViewClass("file:///android_asset/ClassDetailedForMobile.html");
        navigationView.askPermission();

    }

    @Override
    public void startRegionMonitoring() {


        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(final Collection<Beacon> beacons, Region region) {
                Log.i("#BEACONS FOUND ", "" + beacons.size());

                if (beacons.size() > 0) {
                    Log.i("DISTANCE_M_" + beacons.iterator().next().getId1(), "The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.");
                    //Toast.makeText(getBaseContext(),"The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.",Toast.LENGTH_LONG).show();

                    Beacon b = beacons.iterator().next();
                    Log.i("BEACONID", b.getId1().toString());
                    if (b.getDistance() < 3.0) {

                        beaconManager.removeAllRangeNotifiers();


                        String id1 = "5894d7c1-b6b9-4766-85b9-419843b89471"; //AulaA
                        String id2 = "d77657c4-52a7-426f-b9d0-d71e10798c8a"; //AulaB

                        double distance = 100.0;
                        Beacon closestBeacon = beacons.iterator().next();


                        for (Beacon beacon : beacons) {
                            if ((beacon.getDistance() < distance)) {
                                closestBeacon = beacon;
                                distance = beacon.getDistance();
                            }
                        }

                        Log.i("LOGGINO", closestBeacon.getId1().toString());
                        if (closestBeacon.getId1().toString().equals(id1)) {
                            navigationView.createBuilder("Aula A", "AulaA");


                        } else if (closestBeacon.getId1().toString().equals(id2)) {
                            navigationView.createBuilder("Aula B", "AulaB");
                        }


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
}
