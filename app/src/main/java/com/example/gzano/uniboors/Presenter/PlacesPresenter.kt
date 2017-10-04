package com.example.gzano.uniboors.Presenter

import com.example.gzano.uniboors.Model.Room
import com.example.gzano.uniboors.Model.Room.ClassRoom
import com.example.gzano.uniboors.Model.RoomType
import com.example.gzano.uniboors.ViewInterfaces.FragmentView.PlacesFragmentView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

/**
 * Created by gzano on 04/10/2017.
 */
class PlacesPresenter(private val placesFragmentView: PlacesFragmentView, private val databaseRef: DatabaseReference) : Presenter {

    override fun onCreate() {

        placesFragmentView.showProgressBar()



        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {
                val fetchedRooms = ArrayList<Room>()
                if (p0 != null && p0.value == "empty") {
                    placesFragmentView.suggestUserToLookForPlaces()
                } else {
                    val children = p0?.childrenCount
                    placesFragmentView.showProgressBar()
                    p0?.children?.mapTo(fetchedRooms) { ClassRoom(RoomType.CLASSROOM, it.key.toString(), false) }
                    if (fetchedRooms.size.toLong() >= children!!) {

                        placesFragmentView.hideProgressBar()
                        placesFragmentView.setAdapter(fetchedRooms)
                    }
                }

            }

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })


    }
}