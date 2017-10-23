package com.example.gzano.uniboors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.gzano.uniboors.utils.Adapters.RecyclerAdapterMajors;

public class PresentationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);
        Toolbar toolbar = findViewById(R.id.major_toolbar);
        setSupportActionBar(toolbar);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.majors_recycler_view);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        RecyclerAdapterMajors adapterMajors = new RecyclerAdapterMajors();
        recyclerView.setAdapter(adapterMajors);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_your_places, menu);
        return true;
    }
}
