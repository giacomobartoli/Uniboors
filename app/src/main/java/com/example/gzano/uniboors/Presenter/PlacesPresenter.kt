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
class PlacesPresenter(val placesFragmentView: PlacesFragmentView) : Presenter {
    private val campusDatabaseRef = FirebaseDatabase.getInstance().getReference(Constants.CESENA_CAMPUS_NODE)
    private val userDatabaseRef = FirebaseDatabase.getInstance().getReference(Constants.NODE_USERS_PATH).child(FirebaseAuth.getInstance().currentUser?.uid).child(Constants.PLACES_NODE_VALUE).ref

    init {
        val userRooms = ArrayList<Room>()
        userDatabaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?) {

                if (p0 != null && p0.value != Constants.EMPTY_NODE_VALUE) {
                    p0.children.mapTo(userRooms) {
                        when (it.child(Constants.TYPE_NODE_VALUE)?.value.toString()) {
                            Constants.CLASSROOM_NODE_VALUE -> {

                                ClassRoom(RoomType.CLASSROOM, it?.child("name")?.value.toString(), false, Floor.FIRST_FLOOR)

                            }
                            Constants.COMPUTER_LAB_NODE_VALUE -> {
                                ComputerLab(RoomType.COMPUTER_LAB, it?.child("name")?.value.toString(), false, Floor.FIRST_FLOOR)
                            }
                            else -> {

                                GenericRoom(RoomType.GENERIC, it?.child("name")?.value.toString(), false, Floor.FIRST_FLOOR)
                            }

                        }
                    }
                }

                placesFragmentView.setAdapter(ArrayList(), userRooms)
            }


        })

    }

    override fun onCreate() {

        placesFragmentView.showProgressBar()
        campusDatabaseRef.addChildEventListener(
                object : ChildEventListener {
                    override fun onCancelled(p0: DatabaseError?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onChildAdded(p0: DataSnapshot?, p1: String?) {

                        when (p0?.child(Constants.TYPE_NODE_VALUE)?.value.toString()) {
                            Constants.CLASSROOM_NODE_VALUE -> {

                                placesFragmentView.addCampusRoom(ClassRoom(RoomType.CLASSROOM, p0?.child("name")?.value.toString(), false, Floor.FIRST_FLOOR))

                            }
                            Constants.COMPUTER_LAB_NODE_VALUE -> {
                                placesFragmentView.addCampusRoom(ComputerLab(RoomType.COMPUTER_LAB, p0?.child("name")?.value.toString(), false, Floor.FIRST_FLOOR))
                            }
                            else -> {
                                //placesFragmentView.addCampusRoom(GenericRoom(RoomType.GENERIC,p0?.child("name")?.value.toString(), false, Floor.FIRST_FLOOR))
                            }

                        }
                        placesFragmentView.hideProgressBar()
                    }

                    override fun onChildRemoved(p0: DataSnapshot?) {

                        when (p0?.child(Constants.TYPE_NODE_VALUE)?.value.toString()) {
                            Constants.CLASSROOM_NODE_VALUE -> {

                                placesFragmentView.removeCampusRoom(ClassRoom(RoomType.CLASSROOM, p0?.child("name")?.value.toString(), false, Floor.FIRST_FLOOR))

                            }
                            Constants.COMPUTER_LAB_NODE_VALUE -> {
                                placesFragmentView.removeCampusRoom(ComputerLab(RoomType.COMPUTER_LAB, p0?.child("name")?.value.toString(), false, Floor.FIRST_FLOOR))
                            }
                            else -> {
                                // placesFragmentView.removeCampusRoom(GenericRoom(RoomType.GENERIC, p0?.child("name")?.value.toString(), false, Floor.FIRST_FLOOR))
                            }

                        }
                    }
                })
    }


    fun addRoom(room: Room, position: Int) {


        when (room.roomType) {
            RoomType.CLASSROOM -> checkIfPresent(room, position)


            RoomType.COMPUTER_LAB -> checkIfPresent(room, position)
            else -> {
            }
        }
    }


    fun removeRoom(room: Room, position: Int) {


        userDatabaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {

                if (p0?.childrenCount == 1.toLong()) {
                    p0.ref.setValue(Constants.EMPTY_NODE_VALUE).addOnCompleteListener {
                        if (it.isComplete) {
                            placesFragmentView.setFavoriteIcon(R.drawable.ic_not_favorite, position, room)
                            placesFragmentView.setNewClickListener(R.drawable.ic_not_favorite, position, room)
                            placesFragmentView.removeUserRoom(room)

                        }
                    }
                }
                p0?.children?.filter { it.key == room.roomName }?.forEach {
                    it.ref.removeValue().addOnCompleteListener({
                        if (it.isComplete) {
                            placesFragmentView.setFavoriteIcon(R.drawable.ic_not_favorite, position, room)
                            placesFragmentView.setNewClickListener(R.drawable.ic_not_favorite, position, room)
                            placesFragmentView.removeUserRoom(room)
                        }
                    })
                }

            }
        })


    }


    private fun checkIfPresent(room: Room, position: Int) {
        userDatabaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {

                if (p0 != null) {
                    val isPresent = p0.children.any { it.key == room.roomName }
                    val data = HashMap<String, String>()
                    data.put(Constants.TYPE_NODE_VALUE, room.roomType.toString())
                    data.put(Constants.IS_FRIENDLY_NODE_VALUE, room.isFriendly.toString())
                    data.put(Constants.FLOOR_NODE_VALUE, room.floor.toString())
                    if (!isPresent) {
                        userDatabaseRef.child(room.roomName).setValue(data).addOnCompleteListener {
                            placesFragmentView.addUserRoom(room)
                            placesFragmentView.setFavoriteIcon(R.drawable.favorite_icon, position, room)
                            placesFragmentView.setNewClickListener(R.drawable.favorite_icon, position, room)

                        }

                    }

                }
            }

        })
    }

}



