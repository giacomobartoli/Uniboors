package com.example.gzano.uniboors.Utils.WebAppInterfaces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.example.gzano.uniboors.NavigationActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gzano on 07/02/2018.
 */

public class BeaconCollector {

    private String AulaA = "AulaA";
    private String AulaB = "AulaB";
    private Context context;

    public BeaconCollector(Context navigationActivity) {
        context = navigationActivity;

    }

    @JavascriptInterface
    public String getPlaceName() throws JSONException {
        Intent intent = ((Activity) context).getIntent();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("placeName", intent.getStringExtra("placeName"));
        jsonObject.put("teacherName", intent.getStringExtra("teacherName"));
        jsonObject.put("timeStart", intent.getStringExtra("timeStart"));
        jsonObject.put("timeEnd", intent.getStringExtra("timeEnd"));
        jsonObject.put("day", intent.getStringExtra("day"));
        jsonObject.put("lessonName", intent.getStringExtra("lessonName"));
        jsonObject.put("dayValue", intent.getStringExtra("dayValue"));


        // String placeName = intent.getStringExtra("placeName");

        return String.valueOf(jsonObject);
    }

    @JavascriptInterface
    public String getAulaA() {
        return AulaA;
    }

    @JavascriptInterface
    public String getAulaB() {
        return AulaB;
    }


}
