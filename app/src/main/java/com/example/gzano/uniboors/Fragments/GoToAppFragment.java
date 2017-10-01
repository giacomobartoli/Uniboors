package com.example.gzano.uniboors.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gzano.uniboors.R;
import com.example.gzano.uniboors.Utils.Constants;

import com.example.gzano.uniboors.Presenter.WelcomePresenter;
import com.example.gzano.uniboors.ViewInterfaces.FragmentView;


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
        SpannableString ss = new SpannableString(Constants.SIGNIN_SIGNUP_TEXT);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                LoginFragment fragment = new LoginFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        };
        ss.setSpan(clickableSpan, 0, Constants.SIGNIN_SIGNUP_TEXT.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        newAccount.setText(ss);
        newAccount.setMovementMethod(LinkMovementMethod.getInstance());
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



}
