package com.example.gzano.uniboors.ViewInterfaces

import android.support.v4.app.Fragment
import com.example.gzano.uniboors.Model.Room
import com.example.gzano.uniboors.Model.RoomType
import java.io.File
import java.util.*

/**
 * Created by gzano on 26/09/2017.
 */
interface FragmentView {


    interface LoginFragmentView : FragmentView {
        fun setButtonListener()

        fun replaceFragment(fragment: Fragment)

        fun informUserWrongPassword(message: String)

        fun informUserWrongEmail(message: String)

        fun hideHintPassword()

        fun hideHintEmail()

        fun showProgressBar()

        fun hideProgressBar()
    }


    interface WelcomeFragmentView : FragmentView {

        fun updateWelcomeMessage(message:String)


        fun onGoToAppPressed()

        fun setListeners()

    }

    interface PlacesFragmentView : FragmentView {
        fun setAdapter(fetchedRooms: ArrayList<Room>, files: HashMap<RoomType, File>)

        fun showProgressBar()

        fun hideProgressBar()

        fun suggestUserToLookForPlaces()

        fun showAlert()

        fun hideAlert()
    }
}
