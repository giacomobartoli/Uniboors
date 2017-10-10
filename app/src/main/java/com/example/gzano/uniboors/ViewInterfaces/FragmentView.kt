package com.example.gzano.uniboors.ViewInterfaces

import android.support.v4.app.Fragment
import android.view.View
import com.example.gzano.uniboors.Model.Room
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

        fun updateWelcomeMessage(message: String)


        fun onGoToAppPressed()

        fun setListeners()

    }

    interface PlacesFragmentView : FragmentView {
        fun setAdapter(fetchedRooms: ArrayList<Room>)

        fun showProgressBar()

        fun hideProgressBar()

        fun suggestUserToLookForPlaces()

        fun showAlertGoToNavigationOrStay(data: HashMap<String, String>, roomName: String)

        fun showAlertAlreadyInsert()

        fun showPopUp(view: View)

        fun getPageTag(): Int

        fun startActivity()

        fun showGoAlertOrRemove(roomName: String)


    }
}
