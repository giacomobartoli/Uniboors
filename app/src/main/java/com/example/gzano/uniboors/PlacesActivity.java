package com.example.gzano.uniboors;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.gzano.uniboors.Fragments.CampusPlacesFragment;
import com.example.gzano.uniboors.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class PlacesActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        CampusPlacesFragment recyclerFragment = new CampusPlacesFragment();
        recyclerFragment.setPlacesPresenter(FirebaseDatabase.getInstance().getReference(Constants.NODE_USERS_PATH)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("places").getRef());
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
