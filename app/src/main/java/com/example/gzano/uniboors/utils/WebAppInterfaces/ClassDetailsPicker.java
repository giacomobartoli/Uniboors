package com.example.gzano.uniboors.utils.WebAppInterfaces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gzano on 03/02/2018.
 */

public class ClassDetailsPicker {

    private Context context;

    public ClassDetailsPicker(Context context) {
        this.context = context;
    }
    @JavascriptInterface
    public void showToast() {
        Toast.makeText(context, "ciao", Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public String print() {
        return "funziono sta tranquillo";
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

    public class ClassDetails {
        private String placeName;

        public ClassDetails(String placeName) {

            this.placeName = placeName;
        }



        public String getPlaceName() {
            return placeName;
        }

        public String createJsonObject() throws JSONException {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("placeName", placeName);


            return String.valueOf(jsonObject);
        }
    }


}


