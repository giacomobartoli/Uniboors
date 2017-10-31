package com.example.gzano.uniboors.Presenter

import android.util.Log
import com.example.gzano.uniboors.Model.Lesson
import com.example.gzano.uniboors.Model.LessonSchedule
import com.example.gzano.uniboors.Model.LessonTime
import com.example.gzano.uniboors.Model.Lessons
import com.example.gzano.uniboors.Presenter.PresenterInterface.Presenter
import com.example.gzano.uniboors.ViewInterfaces.FragmentView
import com.example.gzano.uniboors.utils.Constants
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by gzano on 30/10/2017.
 */
class ComputerSciencePresenter(val lessonView: FragmentView.LessonFragmentView) : Presenter.LessonsPresenter {

    private val databaseRef = FirebaseDatabase.getInstance().getReference(Constants.CESENA_CAMPUS_NODE).child("Corsi")


    override fun onCreate() {
        databaseRef.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                val scheduleDatabaseRef = p0?.child("schedule")
                if (p0 != null) {

                    Log.d("TAGCHILD", p0.toString() + " " + p0.key.toString())
                    when (Lessons.valueOf(p0.child("type").value.toString())) {
                        Lessons.APPLICAZIONI_WEB -> {
                            val dayAndTime = HashMap<Int, LessonTime>()
                            for (snapshot in scheduleDatabaseRef?.children!!) {
                                val timeStart = snapshot?.child("timeStart")?.value.toString().toInt()
                                val timeEnd = snapshot?.child("timeEnd")?.value.toString().toInt()
                                val roomString = snapshot?.child("place")?.value.toString()
                                val dayOfWeek = snapshot.child("value")?.value.toString().toInt()
                                Log.d("TAGSCHEDULE", timeEnd.toString() + " " + timeStart.toString() + " " + roomString + " " + dayOfWeek)
                                dayAndTime.put(dayOfWeek, LessonTime(timeStart, timeEnd, roomString))
                            }
                            val lessonSchedule = LessonSchedule.createLessonSchedule(dayAndTime)
                            lessonView.addLesson(Lesson.WebApplication("Applicazioni Web", Lessons.APPLICAZIONI_WEB, p0.child("credits").value.toString().toInt(), p0.child("teacher").value.toString(), lessonSchedule))

                        }
                        Lessons.SVILUPPO_SISTEMI_SOFTWARE -> {
                            val dayAndTime = HashMap<Int, LessonTime>()
                            for (snapshot in scheduleDatabaseRef?.children!!) {
                                val timeStart = snapshot?.child("timeStart")?.value.toString().toInt()
                                val timeEnd = snapshot?.child("timeEnd")?.value.toString().toInt()
                                val roomString = snapshot?.child("place")?.value.toString()
                                Log.d("TAGCHECK", snapshot.hasChild("value").toString())
                                val dayOfWeek = snapshot.child("value")?.value.toString().toInt()
                                Log.d("TAGSCHEDULE", timeEnd.toString() + " " + timeStart.toString() + " " + roomString + " " + dayOfWeek)
                                dayAndTime.put(dayOfWeek, LessonTime(timeStart, timeEnd, roomString))
                            }
                            val lessonSchedule = LessonSchedule.createLessonSchedule(dayAndTime)
                            lessonView.addLesson(Lesson.SoftwareDevelopment("Sviluppo di Sistemi Software", Lessons.SVILUPPO_SISTEMI_SOFTWARE, p0.child("credits").value.toString().toInt(), p0.child("teacher").value.toString(), lessonSchedule))
                        }

                        Lessons.SISTEMI_DISTRIBUITI -> TODO()
                        Lessons.MACHINE_LEARNING -> TODO()
                        Lessons.SITEMI_INFORMATIVI -> TODO()
                        Lessons.SICUREZZA_DELLE_RETI -> TODO()
                        Lessons.LCMC -> TODO()
                    }
                }

            }

            override fun onChildRemoved(p0: DataSnapshot?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    override fun addLesson(lesson: Lesson, position: Int) {

    }

    override fun removeLesson(lesson: Lesson, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}