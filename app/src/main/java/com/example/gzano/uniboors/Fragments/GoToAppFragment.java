package com.example.gzano.uniboors.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gzano.uniboors.R;

import Presenter.WelcomePresenter;
import ViewInterfaces.FragmentView;


public class GoToAppFragment extends Fragment implements FragmentView.WelcomeFragmentView {

    private WelcomePresenter presenter;
    private TextView welcomeTextView;
    private TextView newAccount;
    private View view;

    public GoToAppFragment() {
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
        presenter = new WelcomePresenter(this);
        view = inflater.inflate(R.layout.fragment_go_to_app, container, false);
        welcomeTextView = view.findViewById(R.id.welcomeTextView);
        newAccount = view.findViewById(R.id.newAccountTextView);
        newAccount.setClickable(true);
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.textPressed();
            }
        });
        presenter.onCreate();

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
    public void onGoToAppPressed() {

    }



    @Override
    public void updateWelcomeMessage(String message) {
      welcomeTextView.setText(message);

    }

    @Override
    public void backToSignIn() {
        LoginFragment fragment=new LoginFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
