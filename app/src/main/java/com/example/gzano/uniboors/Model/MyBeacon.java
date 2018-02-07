package com.example.gzano.uniboors.Model;

/**
 * Created by Giacomo on 05/02/2018.
 */

public class MyBeacon {

    private String beaconID = "";
    private String roomID = "";


    public MyBeacon(String beaconID, String roomID) {
        this.beaconID = beaconID;
        this.roomID = roomID;
    }

    public String getBeaconID() {
        return beaconID;
    }

    public String getRoomID() {
        return roomID;
    }
}
