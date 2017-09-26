package com.example.gzano.uniboors.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gzano.uniboors.R;

import Presenter.LoginPresenter;
import ViewInterfaces.BaseView;


public class LoginFragment extends Fragment implements BaseView.LoginView {

    private LoginPresenter loginPresenter;

    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter.onCreate();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
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
    public void onLoginPressed() {
        Log.d("TEST"," it works just fine");

    }

    @Override
    public void onSignUpPressed() {

    }

    @Override
    public void setPresenter(LoginPresenter presenter) {
        this.loginPresenter=presenter;

    }
}
