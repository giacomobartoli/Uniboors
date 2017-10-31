package com.example.gzano.uniboors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gzano.uniboors.Fragments.LessonsFragment;

public class PlacesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        Toolbar toolbar = findViewById(R.id.places_toolbar);
        setSupportActionBar(toolbar);

        LessonsFragment recyclerFragment = new LessonsFragment();
        recyclerFragment.setPlacesPresenter();
        getSupportFragmentManager().beginTransaction().add(R.id.places_fragment_container, recyclerFragment).commit();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_your_places, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


}
