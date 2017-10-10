package com.example.gzano.uniboors.Presenter

import android.util.Log
import com.example.gzano.uniboors.Fragments.GoToAppFragment
import com.example.gzano.uniboors.Model.AuthenticationMode
import com.example.gzano.uniboors.ViewInterfaces.FragmentView
import com.example.gzano.uniboors.utils.Constants
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*

/**
 * Created by gzano on 26/09/2017.
 */
class AccountAuthPresenter(private var loginFragmentView: FragmentView.LoginFragmentView) : Presenter {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()


    private val TAG = "FIREBASEEXC"


    override fun onCreate() {
        loginFragmentView.setButtonListener()
    }

    fun executeAuthentication(email: String, password: String, mode: AuthenticationMode) {
        if (email.isEmpty()) {
            loginFragmentView.informUserWrongEmail(Constants.ERROR_MESSAGE_AUTH_EMAIL_EMPTY)
            loginFragmentView.hideHintPassword()

        }
        if (password.isEmpty()) {
            loginFragmentView.informUserWrongPassword(Constants.ERROR_MESSAGE_AUTH_PASSWORD_EMPTY)
            loginFragmentView.hideHintEmail()


        }
        if (email.isEmpty() && password.isEmpty()) {
            loginFragmentView.informUserWrongPassword(Constants.ERROR_MESSAGE_AUTH_PASSWORD_EMPTY)
            loginFragmentView.informUserWrongEmail(Constants.ERROR_MESSAGE_AUTH_EMAIL_EMPTY)
        }
        if (!email.isEmpty() && !password.isEmpty()) {
            loginFragmentView.showProgressBar()
            when (mode) {
                AuthenticationMode.SIGN_IN -> mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { authenticationTask ->
                    if (authenticationTask.isSuccessful) {

                        loginFragmentView.replaceFragment(GoToAppFragment())

                    } else {
                        informUser(authenticationTask)

                    }
                }
                AuthenticationMode.SIGN_UP -> mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authenticationTask ->
                    if (authenticationTask.isSuccessful) {


                        loginFragmentView.replaceFragment(GoToAppFragment())

                    } else {


                        informUser(authenticationTask)

                    }
                }


            }
        }

    }

    private fun informUser(authenticationTask: Task<AuthResult>) {
        loginFragmentView.hideProgressBar()
        try {
            throw authenticationTask.exception!!
        } catch (e: FirebaseAuthWeakPasswordException) {
            Log.d(TAG, e.errorCode)

            loginFragmentView.informUserWrongPassword(Constants.WEAK_PASSWORD)
            loginFragmentView.hideHintEmail()


        } catch (e: FirebaseAuthInvalidCredentialsException) {

            Log.d(TAG, e.errorCode)
            when (e.errorCode) {
                Constants.ERROR_WRONG_PASSWORD -> {
                    loginFragmentView.informUserWrongPassword(Constants.GENERAL_PASSWORD_ERROR_AUTH)
                    loginFragmentView.hideHintEmail()
                }
                Constants.ERROR_INVALID_EMAIL -> {
                    loginFragmentView.informUserWrongEmail(Constants.WRONG_EMAIL_FORMAT)
                    loginFragmentView.hideHintPassword()
                }
            }


        } catch (e: FirebaseAuthUserCollisionException) {
            Log.d(TAG, e.errorCode)

            loginFragmentView.informUserWrongEmail(Constants.EMAIL_ALREADY_IN_USE)
            loginFragmentView.hideHintPassword()

        } catch (e: Exception) {
            Log.d(TAG, e.message)

        }
    }

}