package com.example.gzano.uniboors.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gzano.uniboors.R;

import Presenter.WelcomePresenter;
import ViewInterfaces.BaseView;


public class WelcomeFragment extends Fragment implements BaseView.WelcomeView {

    private WelcomePresenter welcomeActivityPresenter;

    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false);
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
    public void onGoToAppPressed() {

    }

    @Override
    public void onFailedLogin() {

    }

    @Override
    public void onSuccessfulLogin() {

    }

    @Override
    public void setPresenter(WelcomePresenter presenter) {
        this.welcomeActivityPresenter = presenter;
    }
}
