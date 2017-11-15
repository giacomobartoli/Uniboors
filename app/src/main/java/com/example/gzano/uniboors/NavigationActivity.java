package com.example.gzano.uniboors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.gzano.uniboors.ViewInterfaces.ActivityView;

import org.jetbrains.annotations.NotNull;

public class NavigationActivity extends AppCompatActivity implements ActivityView.NavigationView {

    private TextView destination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Intent intent = getIntent();
        destination = findViewById(R.id.destination_chosen);
        destination.setText(intent.getStringExtra("placeName"));
    }

    @Override
    public void setETA(@NotNull String estimatedTimeOfArrival) {

    }

    @Override
    public void setCurrentPosition(@NotNull String currentPosition) {

    }


}
