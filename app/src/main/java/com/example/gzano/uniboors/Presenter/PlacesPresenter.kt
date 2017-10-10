package com.example.gzano.uniboors.Presenter

import android.view.View
import com.example.gzano.uniboors.Model.Floor
import com.example.gzano.uniboors.Model.Room
import com.example.gzano.uniboors.Model.Room.*
import com.example.gzano.uniboors.Model.RoomType
import com.example.gzano.uniboors.ViewInterfaces.FragmentView.PlacesFragmentView
import com.example.gzano.uniboors.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by gzano on 04/10/2017.
 */
class PlacesPresenter(val placesFragmentView: PlacesFragmentView, private val databaseRef: DatabaseReference) : Presenter {


    init {
        databaseRef.keepSynced(true)
    }

    override fun onCreate() {


        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {
                val fetchedRooms = ArrayList<Room>()
                if (p0 != null && p0.value == "empty") {
                    placesFragmentView.suggestUserToLookForPlaces()
                } else {
                    placesFragmentView.showProgressBar()
                    val childrenCount = p0?.childrenCount

                    p0?.children?.mapTo(fetchedRooms) {
                        when (it.child("type").value.toString()) {
                            Constants.CLASSROOM_NODE_VALUE -> {

                                ClassRoom(RoomType.CLASSROOM, it.key.toString(), false, Floor.FIRST_FLOOR)

                            }
                            Constants.COMPUTER_LAB_NODE_VALUE -> {
                                ComputerLab(RoomType.COMPUTER_LAB, it.key.toString(), false, Floor.FIRST_FLOOR)
                            }
                            else -> {
                                GenericRoom(RoomType.GENERIC, it.key.toString(), false, Floor.FIRST_FLOOR)
                            }
                        }
                    }
                    if (fetchedRooms.size == childrenCount?.toInt()) {
                        placesFragmentView.setAdapter(fetchedRooms)
                        placesFragmentView.hideProgressBar()
                    }


                }

            }

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            }

        })


    }

    fun addToFavPlaces(room: Room) {


        val databaseRef = FirebaseDatabase.getInstance().getReference(Constants.NODE_USERS_PATH).
                child(FirebaseAuth.getInstance().currentUser?.uid).child("places")
        when (room.roomType) {
            RoomType.CLASSROOM -> checkIfPresent(databaseRef, room)


            RoomType.COMPUTER_LAB -> checkIfPresent(databaseRef, room)
            else -> {
            }
        }
    }


    fun onLongPressed(view: View) {

        if (placesFragmentView.getPageTag() == Constants.PAGE_TAG_YOUR_PLACE) {
            placesFragmentView.showPopUp(view)
        }

    }

    fun removeRoom(roomName: String) {
        val databaseRef = FirebaseDatabase.getInstance().getReference(Constants.NODE_USERS_PATH)
                .child(FirebaseAuth.getInstance().currentUser?.uid).child("places")


        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                p0?.children?.filter { it.key == roomName }?.forEach { it.ref.removeValue() }
            }
        })


    }

    fun add(data: java.util.HashMap<String, String>, roomName: String) {
        databaseRef.child(roomName).setValue(data)

    }

    private fun checkIfPresent(databaseRef: DatabaseReference, room: Room) {
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0 != null) {
                    val isPresent = p0.children.any { it.key == room.roomName }
                    val data = HashMap<String, String>()
                    data.put("type", room.roomType.toString())
                    data.put("isFriendly", room.isFriendly.toString())
                    data.put("floor", room.floor.toString())
                    if (!isPresent && placesFragmentView.getPageTag() == Constants.PAGE_TAG_CESENA_PLACE) {

                        placesFragmentView.showAlertGoToNavigationOrStay(data, room.roomName)


                    }
                    if (isPresent && placesFragmentView.getPageTag() == Constants.PAGE_TAG_CESENA_PLACE) {
                        placesFragmentView.showGoAlertOrRemove(room.roomName)
                    }
                    if (isPresent && placesFragmentView.getPageTag() == Constants.PAGE_TAG_YOUR_PLACE) {
                        placesFragmentView.startActivity()
                    }
                }
            }

        })
    }
}



