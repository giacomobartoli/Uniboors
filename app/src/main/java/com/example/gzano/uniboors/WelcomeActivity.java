package com.example.gzano.uniboors;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class WelcomeActivity extends FragmentActivity {

    private FirebaseAuth mAuth;
    private EditText emailText;
    private EditText emailPassword;
    private Activity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        emailText = findViewById(R.id.editText);
        emailPassword = findViewById(R.id.editText2);
        mAuth = FirebaseAuth.getInstance();
        Button loginButton = findViewById(R.id.Login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(emailText.getText().toString(), emailPassword.getText().toString())
                        .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("PROVA AUTH", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("PROVA ERROR", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(WelcomeActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                            }
                        });
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(WelcomeActivity.this, user.getEmail(),
                    Toast.LENGTH_LONG).show();
        }
    }
}