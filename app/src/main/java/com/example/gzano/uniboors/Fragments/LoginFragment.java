package com.example.gzano.uniboors.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gzano.uniboors.R;
import com.example.gzano.uniboors.UniboorsActivity;


import Presenter.LoginPresenter;
import ViewInterfaces.FragmentView;


public class LoginFragment extends Fragment implements FragmentView.LoginFragmentView {

    private LoginPresenter loginPresenter;
    private Button buttonSignUp, buttonLogin;
    private TextView userInfo;
    private EditText email, password;
    private View view;
    private ProgressBar progressBar;


    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.loginPresenter = new LoginPresenter(this);
        view = inflater.inflate(R.layout.fragment_login, container, false);
        buttonLogin = view.findViewById(R.id.button2);
        buttonSignUp = view.findViewById(R.id.button3);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        userInfo = view.findViewById(R.id.userInfo);
        progressBar = view.findViewById(R.id.progressBar);

        loginPresenter.onCreate();

        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void setButtonListener() {
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignUpPressed(view);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginPressed(view);
            }
        });
    }


    public void replaceFragment(Fragment fragment) {
        Log.d("TESTACT", String.valueOf(getActivity() instanceof UniboorsActivity));
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void informUser(String message) {
        userInfo.setText(message);
    }

    private void onLoginPressed(View view) {
        loginPresenter.signIn(email.getText().toString(), password.getText().toString());

    }

    private void onSignUpPressed(View view) {

        Log.d("MARCO ", " email: " + email.getText() + " pass " + password.getText().toString());
        loginPresenter.createUser(email.getText().toString(), password.getText().toString());

    }


    @Override
    public void showProgressBar(String message) {
        progressBar.setClickable(false);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
