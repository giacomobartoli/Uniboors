package com.example.gzano.uniboors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.gzano.uniboors.ViewInterfaces.ActivityView;

import org.jetbrains.annotations.NotNull;

public class NavigationActivity extends AppCompatActivity implements ActivityView.NavigationView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Intent intent = getIntent();
        Log.d("TAGNAVIGATION", intent.getStringExtra("className") + " " + intent.getStringExtra("teacherName"));
    }

    @Override
    public void setETA(@NotNull String estimatedTimeOfArrival) {

    }

    @Override
    public void setCurrentPosition(@NotNull String currentPosition) {

    }

    @Override
    public void setDestination(@NotNull String destination) {

    }
}
