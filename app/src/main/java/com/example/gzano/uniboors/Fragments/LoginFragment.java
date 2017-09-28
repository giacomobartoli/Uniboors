package com.example.gzano.uniboors.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.gzano.uniboors.R;
import com.example.gzano.uniboors.UniboorsActivity;


import Presenter.AccountAuthPresenter;
import ViewInterfaces.FragmentView;


public class LoginFragment extends Fragment implements FragmentView.LoginFragmentView {

    private AccountAuthPresenter authPresenter;
    private Button buttonLogin;
    private TextInputEditText email,password;
    private View view;
    private TextInputLayout textInputLayoutemail, textInputLayoutPassword;
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
        authPresenter = new AccountAuthPresenter(this);
        view = inflater.inflate(R.layout.fragment_login, container, false);
        buttonLogin = view.findViewById(R.id.button2);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        textInputLayoutemail = view.findViewById(R.id.email_helper);
        textInputLayoutPassword = view.findViewById(R.id.password_helper);
        progressBar = view.findViewById(R.id.progressBarLogin);
        authPresenter.onCreate();

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
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void showProgressBar() {

        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void informUserWrongPassword(String message) {
        textInputLayoutPassword.setErrorEnabled(true);
        textInputLayoutPassword.setError(message);
    }


    @Override
    public void informUserWrongEmail(String message) {
        textInputLayoutemail.setErrorEnabled(true);
        textInputLayoutemail.setError(message);

    }

    private void onLoginPressed(View view) {
        authPresenter.signIn(email.getText().toString(), password.getText().toString());

    }

    private void onSignUpPressed(View view) {

        authPresenter.createUser(email.getText().toString(), password.getText().toString());

    }

    private void updateView() {

    }


    @Override
    public void hideHintPassword() {
        if (textInputLayoutPassword.isErrorEnabled()) {
            textInputLayoutPassword.setErrorEnabled(false);
        }

    }

    @Override
    public void hideHintEmail() {
        if(textInputLayoutemail.isErrorEnabled()) {
            textInputLayoutemail.setErrorEnabled(false);
        }
    }
}
