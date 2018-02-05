package com.example.gzano.uniboors.ViewInterfaces

import android.support.v4.app.Fragment
import android.view.View
import com.example.gzano.uniboors.Model.Lesson
import com.example.gzano.uniboors.Model.Room

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
        fun setAdapter(fetchedRooms: ArrayList<Room>, fetchedRoomsUser: ArrayList<Room>)

        fun showProgressBar()

        fun hideProgressBar()

        fun suggestUserToLookForPlaces()


        fun startActivity()
        fun setFavoriteIcon(resource: Int, position: Int, room: Room)
        fun setNewClickListener(resource: Int, position: Int, room: Room)
        fun addCampusRoom(room: Room)
        fun removeCampusRoom(room: Room)
        fun addUserRoom(room: Room)
        fun removeUserRoom(room: Room)


    }

    interface LessonFragmentView : FragmentView {

        fun setAdapter(fetchedLessons: ArrayList<Lesson>)

        fun showProgressBar()

        fun hideProgressBar()

        fun makeRowClickable(view: View)

        fun goToNavigationActivity(className: String, teacherName: String, day: String, startTime: String, endTime: String, placeName: String, lessonName: String, dayValue: Int)

    }
}
