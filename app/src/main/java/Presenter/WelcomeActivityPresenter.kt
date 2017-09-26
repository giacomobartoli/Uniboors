package Presenter

import Model.User
import View.WelcomeView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by gzano on 26/09/2017.
 */
class WelcomeActivityPresenter(var user: User, var welcomeView: WelcomeView) : Presenter {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate() {
        mAuth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener(welcomeView.getActivityContext(), object : OnCompleteListener<AuthResult> {
            override fun onComplete(p0: Task<AuthResult>) {
                if (p0.isSuccessful) {
                    welcomeView.updateView(mAuth.currentUser)
                }

            }

        })
    }

    fun createUser(email: String, password: String) {
        user.email = email
        user.password = password
    }



}