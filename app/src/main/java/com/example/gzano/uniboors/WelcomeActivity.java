package com.example.gzano.uniboors;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.gzano.uniboors.Fragments.LoginFragment;
import com.example.gzano.uniboors.Utils.LoginInflater;
import com.google.firebase.auth.FirebaseAuth;

import Presenter.WelcomePresenter;

public class WelcomeActivity extends FragmentActivity {

    private FirebaseAuth mAuth;
    private EditText emailText;
    private EditText emailPassword;
    private Activity context = this;
    public ConstraintLayout fragmentContainer;
    private WelcomePresenter welcomeActivityPresenter;
    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        fragmentContainer = findViewById(R.id.fragment_container);
        new LoginInflater(this).inflateFragment(new LoginFragment());


    }
}