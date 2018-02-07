package com.example.gzano.uniboors.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gzano.uniboors.Model.AuthenticationMode;
import com.example.gzano.uniboors.Presenter.AccountAuthPresenter;
import com.example.gzano.uniboors.R;
import com.example.gzano.uniboors.ViewInterfaces.FragmentView;
import com.example.gzano.uniboors.utils.Constants;


public class LoginFragment extends Fragment implements FragmentView.LoginFragmentView {

    private AccountAuthPresenter authPresenter;
    private Button buttonLogin;
    private TextInputEditText email, password;
    private View view;
    private TextInputLayout textInputLayoutemail, textInputLayoutPassword;
    private ProgressBar progressBar;
    private TextView signUp;


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
        signUp = view.findViewById(R.id.signUpTextView);

        initSpannable();
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
                authPresenter.executeAuthentication(email.getText().toString(), password.getText().toString(), AuthenticationMode.SIGN_IN);

            }
        });
    }


    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        hideProgressBar();

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

    @Override
    public void hideHintPassword() {
        if (textInputLayoutPassword.isErrorEnabled()) {
            textInputLayoutPassword.setErrorEnabled(false);
        }

    }

    @Override
    public void hideHintEmail() {
        if (textInputLayoutemail.isErrorEnabled()) {
            textInputLayoutemail.setErrorEnabled(false);
        }
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);

    }

    private void initSpannable() {
        SpannableString ss = new SpannableString(Constants.DONT_HAVE_ACCOUNT_SIGN_UP);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                buttonLogin.setText("Sign Up!");
                buttonLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        authPresenter.executeAuthentication(email.getText().toString(), password.getText().toString(), AuthenticationMode.SIGN_UP);

                    }
                });
                signUp.setVisibility(View.GONE);
            }
        };
        ss.setSpan(clickableSpan, Constants.DONT_HAVE_ACCOUNT_SIGN_UP.length() - Constants.SIGN_UP.length(), Constants.DONT_HAVE_ACCOUNT_SIGN_UP.length(), Spanned.SPAN_COMPOSING);

        signUp.setMovementMethod(LinkMovementMethod.getInstance());
        signUp.setText(ss);

    }


}