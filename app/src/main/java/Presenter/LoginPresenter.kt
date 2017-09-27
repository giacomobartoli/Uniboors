package Presenter

import Model.User
import ViewInterfaces.FragmentView
import android.util.Log
import com.example.gzano.uniboors.Fragments.GoToAppFragment
import com.example.gzano.uniboors.Utils.Constants
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by gzano on 26/09/2017.
 */
class LoginPresenter(private var loginFragmentView: FragmentView.LoginFragmentView) : Presenter {


    val user: User? = null
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate() {
        loginFragmentView.setButtonListener()
    }

    fun createUser(email: String, password: String) {
        loginFragmentView.showProgressBar("creating account...")
        if (email.isEmpty() || password.isEmpty()) {
            loginFragmentView.informUser(Constants.ERROR_MESSAGE_AUTH)
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { authenticationTask ->
                if (authenticationTask.isSuccessful) {
                    loginFragmentView.hideProgressBar()
                    Log.d("GEUUUUUUUUUUUUUUUUUUU", " user created")
                    loginFragmentView.replaceFragment(GoToAppFragment())
                } else {
                    Log.d("AZZ", " error "+authenticationTask.exception)

                    loginFragmentView.informUser(Constants.ERROR_MESSAGE_AUTH)
                }
            }
        }
    }

    fun signIn(email: String, password: String) {
        loginFragmentView.showProgressBar("signing in...")

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { authenticationTask ->
            if (authenticationTask.isSuccessful) {
                loginFragmentView.hideProgressBar()
               // Log.d("GEUUUUUUUUUUUUUUUUUUU", " user created")
                loginFragmentView.replaceFragment(GoToAppFragment())
            } else {
              //  Log.d("AZZ", " error "+authenticationTask.exception)

                loginFragmentView.informUser(Constants.ERROR_MESSAGE_AUTH)
            }
        }

    }


}