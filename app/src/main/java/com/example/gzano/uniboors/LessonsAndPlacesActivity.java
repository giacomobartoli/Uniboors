package com.example.gzano.uniboors;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.gzano.uniboors.Fragments.LessonsFragment;

public class LessonsAndPlacesActivity extends AppCompatActivity {

    private LessonsFragment lessonsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        Toolbar toolbar = findViewById(R.id.places_toolbar);
        toolbar.setTitle("Cesena classes");
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();

        lessonsFragment = new LessonsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.places_fragment_container, lessonsFragment).commit();


    }

    public void onNavigationFABClick(View view) {
        Log.d("TAGFAB", " clicked");
        lessonsFragment.makeRowClickable(view);
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
