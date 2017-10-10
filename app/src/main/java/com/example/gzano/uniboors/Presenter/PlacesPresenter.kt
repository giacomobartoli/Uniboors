package com.example.gzano.uniboors.Presenter

import com.example.gzano.uniboors.Model.Floor
import com.example.gzano.uniboors.Model.Room
import com.example.gzano.uniboors.Model.Room.*
import com.example.gzano.uniboors.Model.RoomType
import com.example.gzano.uniboors.R
import com.example.gzano.uniboors.ViewInterfaces.FragmentView.PlacesFragmentView
import com.example.gzano.uniboors.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

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
                val fetchedRoomsCampus = ArrayList<Room>()
                if (p0 != null && p0.value == "empty") {
                    placesFragmentView.suggestUserToLookForPlaces()
                } else {
                    placesFragmentView.showProgressBar()
                    val childrenCount = p0?.childrenCount

                    p0?.children?.mapTo(fetchedRoomsCampus) {
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
                    val databaseRefUser = FirebaseDatabase.getInstance().getReference(Constants.NODE_USERS_PATH)
                            .child(FirebaseAuth.getInstance().currentUser?.uid).child("places")
                    databaseRefUser.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onDataChange(p0: DataSnapshot?) {

                            val fetchedRoomsUser = ArrayList<Room>()



                            p0?.children?.mapTo(fetchedRoomsUser) {
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
                            if (fetchedRoomsCampus.size == childrenCount?.toInt()) {
                                placesFragmentView.setAdapter(fetchedRoomsCampus, fetchedRoomsUser)
                                placesFragmentView.hideProgressBar()
                            }


                        }

                    })


                }

            }

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            }

        })


    }

    fun addRoom(room: Room, position: Int) {


        val databaseRef = FirebaseDatabase.getInstance().getReference(Constants.NODE_USERS_PATH).
                child(FirebaseAuth.getInstance().currentUser?.uid).child("places").ref
        when (room.roomType) {
            RoomType.CLASSROOM -> checkIfPresent(databaseRef, room, position)


            RoomType.COMPUTER_LAB -> checkIfPresent(databaseRef, room, position)
            else -> {
            }
        }
    }


    fun removeRoom(room: Room, position: Int) {
        val databaseRef = FirebaseDatabase.getInstance().getReference(Constants.NODE_USERS_PATH)
                .child(FirebaseAuth.getInstance().currentUser?.uid).child("places").ref


        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {

                if (p0?.childrenCount == 1.toLong()) {
                    p0.ref.setValue("empty").addOnCompleteListener {
                        if (it.isComplete) {
                            placesFragmentView.setFavoriteIcon(R.drawable.ic_action_name, position)
                        }
                    }
                }
                p0?.children?.filter { it.key == room.roomName }?.forEach {
                    it.ref.removeValue().addOnCompleteListener({
                        if (it.isComplete) {
                            placesFragmentView.setFavoriteIcon(R.drawable.ic_action_name, position)
                        }
                    })
                }

            }
        })


    }


    private fun checkIfPresent(databaseRef: DatabaseReference, room: Room, position: Int) {
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
                    if (!isPresent) {
                        databaseRef.child(room.roomName).setValue(data)
                        placesFragmentView.setFavoriteIcon(R.drawable.favorite_icon, position)

                    }
                    if (isPresent) {
                        //   placesFragmentView.showGoAlertOrRemove(room.roomName)
                    }
//                    if (isPresent && placesFragmentView.getPageTag() == Constants.PAGE_TAG_YOUR_PLACE) {
//                        placesFragmentView.startActivity()
//                    }
                }
            }

        })
    }
}



