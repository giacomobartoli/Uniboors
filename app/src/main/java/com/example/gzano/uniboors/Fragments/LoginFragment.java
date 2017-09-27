package com.example.gzano.uniboors.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gzano.uniboors.R;
import com.example.gzano.uniboors.WelcomeActivity;

import Presenter.LoginPresenter;
import ViewInterfaces.BaseView;


public class LoginFragment extends Fragment implements BaseView.LoginView {

    private LoginPresenter loginPresenter;
    private ViewGroup viewGroup;
    private Button buttonSignUp, buttonLogin;

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
        viewGroup = container;
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        buttonLogin = view.findViewById(R.id.button2);
        buttonSignUp = view.findViewById(R.id.button3);

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
    public void setPresenter(LoginPresenter presenter) {
        this.loginPresenter = presenter;

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

    private void onLoginPressed(View view) {

    }

    private void onSignUpPressed(View view) {

        loginPresenter.createUser("ciaone@due.com", "usdghkl");

    }

    @Override
    public void replaceFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new GoToAppFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
