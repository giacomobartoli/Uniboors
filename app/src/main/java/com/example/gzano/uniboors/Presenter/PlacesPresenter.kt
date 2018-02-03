package com.example.gzano.uniboors.Presenter

import com.example.gzano.uniboors.Presenter.PresenterInterface.Presenter
import com.example.gzano.uniboors.ViewInterfaces.FragmentView.PlacesFragmentView
import com.example.gzano.uniboors.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by gzano on 04/10/2017.
 */
class PlacesPresenter(val placesFragmentView: PlacesFragmentView) : Presenter {

    private val campusDatabaseRef = FirebaseDatabase.getInstance().getReference(Constants.CESENA_CAMPUS_NODE)
    private val userDatabaseRef = FirebaseDatabase.getInstance().getReference(Constants.NODE_USERS_PATH).child(FirebaseAuth.getInstance().currentUser?.uid).child(Constants.PLACES_NODE_VALUE).ref
    override fun onCreate() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


