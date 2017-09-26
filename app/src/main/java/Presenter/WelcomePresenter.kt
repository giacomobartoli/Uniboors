package Presenter

import Model.User
import ViewInterfaces.BaseView
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by gzano on 26/09/2017.
 */
class WelcomePresenter(private var user: User, private var welcomeView: BaseView.LoginView) : Presenter {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    init{
    }
    override fun onCreate() {
    }

    fun createUser(email: String, password: String) {
        user.email = email
        user.password = password
    }



}