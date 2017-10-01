package com.example.gzano.uniboors.Presenter

import com.example.gzano.uniboors.Model.User
import com.example.gzano.uniboors.Presenter.FirebaseAdapters.UserListener
import com.example.gzano.uniboors.Utils.Constants
import com.example.gzano.uniboors.ViewInterfaces.FragmentView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by gzano on 26/09/2017.
 */
class WelcomePresenter(private var welcomeFragmentView: FragmentView.WelcomeFragmentView) : Presenter {

    private val firebaseDatabase = FirebaseDatabase.getInstance().getReference(Constants.NODE_USERS_PATH)

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val mUser = mAuth.currentUser

    private val user = User(FirebaseAuth.getInstance().currentUser?.email.toString())


    override fun onCreate() {
        welcomeFragmentView.updateWelcomeMessage("  welcome " + user.toString())
        firebaseDatabase.ref.addListenerForSingleValueEvent(UserListener(mUser!!))

    }

    fun goButtonPressed() {

    }


}