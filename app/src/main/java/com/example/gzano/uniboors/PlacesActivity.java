package com.example.gzano.uniboors;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gzano.uniboors.Fragments.CampusPlacesFragment;
import com.example.gzano.uniboors.utils.Constants;
import com.google.firebase.database.FirebaseDatabase;

public class PlacesActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // PlacesPagerAdapter mSectionsPagerAdapter = new PlacesPagerAdapter(getSupportFragmentManager());

        CampusPlacesFragment recyclerFragment = new CampusPlacesFragment();
        recyclerFragment.setPlacesPresenter(FirebaseDatabase.getInstance().getReference(Constants.CESENA_CAMPUS_NODE));
        getSupportFragmentManager().beginTransaction().add(R.id.places_fragment_container, recyclerFragment).commit();


//        mViewPager = findViewById(R.id.container);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
//        TabLayout tabLayout = findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_your_places, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
