package com.example.gzano.uniboors.Presenter.FirebaseAdapters

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

/**
 * Created by gzano on 01/10/2017.
 */
class UserListener(private var mUser: FirebaseUser) : ValueEventListener {


    override fun onCancelled(p0: DatabaseError?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDataChange(snapshot: DataSnapshot?) {
        val childRefPlaces = snapshot?.child(mUser.uid)?.child("places")

        val childRef = snapshot?.child(mUser.uid)?.ref

        if (childRef == null || childRefPlaces!!.value == null) {
            snapshot?.child(mUser.uid)?.child("places")?.ref?.setValue("empty")

        }
    }
}