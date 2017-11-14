package com.example.gzano.uniboors;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.gzano.uniboors.Fragments.GoToAppFragment;
import com.example.gzano.uniboors.Fragments.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;

public class UniboorsActivity extends FragmentActivity {


    public ConstraintLayout fragmentContainer;
    private LoginFragment loginFragment;
    private GoToAppFragment goToAppFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        fragmentContainer = findViewById(R.id.fragment_container);
        loginFragment = new LoginFragment();
        goToAppFragment = new GoToAppFragment();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            inflateFragment(goToAppFragment);

        } else {
            inflateFragment(loginFragment);
        }


    }

    private void inflateFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
    }


}