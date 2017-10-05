package com.example.gzano.uniboors.Presenter

import android.util.Log
import com.example.gzano.uniboors.Model.Room
import com.example.gzano.uniboors.Model.Room.*
import com.example.gzano.uniboors.Model.RoomType
import com.example.gzano.uniboors.ViewInterfaces.FragmentView.PlacesFragmentView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.IOException
import java.util.*

/**
 * Created by gzano on 04/10/2017.
 */
class PlacesPresenter(val placesFragmentView: PlacesFragmentView, private val databaseRef: DatabaseReference) : Presenter {

    private var imagesfile = HashMap<RoomType, File>()
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

//                    }


                    p0?.children?.mapTo(fetchedRooms) {
                        when (it.value.toString()) {
                            "classroom" -> {

                                ClassRoom(RoomType.CLASSROOM, it.key.toString(), false)

                            }
                            "computerlab" -> {
                                ComputerLab(RoomType.COMPUTER_LAB, it.key.toString(), false)
                            }
                            else -> {
                                GenericRoom(RoomType.GENERIC, it.key.toString(), false)
                            }
                        }
                    }

                    val storage1 = FirebaseStorage.getInstance().reference.child("complab.jpg")

                    var localFile1: File?
                    try {
                        localFile1 = File.createTempFile("image1", "jpg")
                        storage1.getFile(localFile1).addOnSuccessListener {
                            imagesfile.put(RoomType.COMPUTER_LAB, localFile1!!)
                            val storage2 = FirebaseStorage.getInstance().reference.child("job-day5.jpg")
                            var localFile2: File?
                            try {
                                localFile2 = File.createTempFile("image2", "jpg")
                                storage2.getFile(localFile2).addOnSuccessListener {
                                    imagesfile.put(RoomType.CLASSROOM, localFile2!!)
                                    placesFragmentView.setAdapter(fetchedRooms, imagesfile)
                                    placesFragmentView.hideProgressBar()
                                    Log.d("TESTFILE ", " map " + imagesfile)
                                }

                            } catch (e: IOException) {
                                e.printStackTrace()
                            }


                        }


                    } catch (e: IOException) {
                        e.printStackTrace()
                    }




                }

            }

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })


    }


}



