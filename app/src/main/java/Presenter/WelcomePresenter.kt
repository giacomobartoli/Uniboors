package Presenter

import Model.User
import ViewInterfaces.FragmentView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Created by gzano on 26/09/2017.
 */
class WelcomePresenter(private var welcomeFragmentView: FragmentView.WelcomeFragmentView) : Presenter {

    private val user = FirebaseAuth.getInstance().currentUser


    override fun onCreate() {
        welcomeFragmentView.updateWelcomeMessage("      welcome "+user?.email)
    }

    fun goButtonPressed(){

    }

    fun textPressed(){
        welcomeFragmentView.backToSignIn()
    }





}