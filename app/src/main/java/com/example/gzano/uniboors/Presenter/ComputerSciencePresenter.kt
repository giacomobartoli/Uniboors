package com.example.gzano.uniboors.Presenter

import android.util.Log
import com.example.gzano.uniboors.Model.Lesson
import com.example.gzano.uniboors.Model.LessonSchedule
import com.example.gzano.uniboors.Model.LessonTime
import com.example.gzano.uniboors.Model.Lessons
import com.example.gzano.uniboors.Presenter.PresenterInterface.Presenter
import com.example.gzano.uniboors.ViewInterfaces.FragmentView
import com.example.gzano.uniboors.Utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by gzano on 30/10/2017.
 */
class ComputerSciencePresenter(val lessonView: FragmentView.LessonFragmentView) : Presenter.LessonsPresenter {


    private val databaseRef = FirebaseDatabase.getInstance().getReference(Constants.CESENA_CAMPUS_NODE).child("Corsi")
    private val userLessonDatabaseRef = FirebaseDatabase.getInstance().getReference(Constants.NODE_USERS_PATH).child(FirebaseAuth.getInstance().currentUser?.uid).child("lessons").ref



    override fun onCreate() {
        Log.d("TAGCHILD", "it stops before firebase")

        lessonView.showProgressBar()
        val campusLessons = ArrayList<Lesson>()

                databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot?) {
                        for (snapshot1 in p0!!.children!!) {
                            Log.d("TAGCHILD", " lessons " + p0.childrenCount)
                            val scheduleDatabaseRef = snapshot1?.child("schedule")

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
                            Log.d("TAGTEACHER", snapshot1.child("teacher").value.toString())

                            Log.d("TAGCREDITS", snapshot1.child("credits").value.toString())

                            when (Lessons.valueOf(snapshot1.child("type").value.toString())) {
                                Lessons.APPLICAZIONI_WEB -> {

                                    campusLessons.add(Lesson.WebApplication("Applicazioni Web", Lessons.APPLICAZIONI_WEB, snapshot1.child("credits").value.toString().toInt(), snapshot1.child("teacher").value.toString(), lessonSchedule))

                                }
                                Lessons.SVILUPPO_SISTEMI_SOFTWARE -> {
                                    campusLessons.add(Lesson.SoftwareDevelopment("Sviluppo di Sistemi Software", Lessons.SVILUPPO_SISTEMI_SOFTWARE, snapshot1.child("credits").value.toString().toInt(), snapshot1.child("teacher").value.toString(), lessonSchedule))
                                }

                                Lessons.SISTEMI_DISTRIBUITI -> campusLessons.add(Lesson.DistributedSystems("Distributed Systems", Lessons.SISTEMI_DISTRIBUITI, snapshot1.child("credits").value.toString().toInt(), snapshot1.child("teacher").value.toString(), lessonSchedule))
                                Lessons.MACHINE_LEARNING -> campusLessons.add(Lesson.MachineLearning("Machine Learning", Lessons.MACHINE_LEARNING, snapshot1.child("credits").value.toString().toInt(), snapshot1.child("teacher").value.toString(), lessonSchedule))

                                Lessons.SISTEMI_INFORMATIVI -> campusLessons.add(Lesson.InformativeSystems("Informative Systems", Lessons.SISTEMI_INFORMATIVI, snapshot1.child("credits").value.toString().toInt(), snapshot1.child("teacher").value.toString(), lessonSchedule))
                                Lessons.PERVASIVE_COMPUTING -> campusLessons.add(Lesson.PervasiveComputing("Pervasive Computing", Lessons.PERVASIVE_COMPUTING, snapshot1.child("credits").value.toString().toInt(), snapshot1.child("teacher").value.toString(), lessonSchedule))
                                Lessons.NETS_SECURITY -> campusLessons.add(Lesson.NetsSecurity("Nets Security", Lessons.NETS_SECURITY, snapshot1.child("credits").value.toString().toInt(), snapshot1.child("teacher").value.toString(), lessonSchedule))
                                Lessons.BIG_DATA -> campusLessons.add(Lesson.BigData("Big Data", Lessons.BIG_DATA, snapshot1.child("credits").value.toString().toInt(), snapshot1.child("teacher").value.toString(), lessonSchedule))
                                Lessons.SMART_CITY_TECNOLOGIE_MOBILI -> campusLessons.add(Lesson.SmartCity("Smart City", Lessons.SMART_CITY_TECNOLOGIE_MOBILI, snapshot1.child("credits").value.toString().toInt(), snapshot1.child("teacher").value.toString(), lessonSchedule))
                                Lessons.SUPPORTO_ALLE_DECISIONI -> campusLessons.add(Lesson.DecisionSupport("Supporto alle Decisioni", Lessons.SUPPORTO_ALLE_DECISIONI, snapshot1.child("credits").value.toString().toInt(), snapshot1.child("teacher").value.toString(), lessonSchedule))
                                Lessons.BUSINESS_INTELLIGENCE -> campusLessons.add(Lesson.BI("Business Intelligence", Lessons.BUSINESS_INTELLIGENCE, snapshot1.child("credits").value.toString().toInt(), snapshot1.child("teacher").value.toString(), lessonSchedule))
                                Lessons.SISTEMI_INTELLIGENTI_ROBOTICI -> campusLessons.add(Lesson.RoboticSystems("Smart Robotic Systems", Lessons.SISTEMI_INTELLIGENTI_ROBOTICI, snapshot1.child("credits").value.toString().toInt(), snapshot1.child("teacher").value.toString(), lessonSchedule))
                                Lessons.DATA_MINING -> {
                                    campusLessons.add(Lesson.DataMining("Data Mining", Lessons.DATA_MINING, snapshot1.child("credits").value.toString().toInt(), snapshot1.child("teacher").value.toString(), lessonSchedule))
                                }
                            }
                            if (campusLessons.size == p0.childrenCount.toInt()) {
                                lessonView.hideProgressBar()
                                lessonView.setAdapter(campusLessons)
                            }
                        }
                    }


                    override fun onCancelled(p0: DatabaseError?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


                    }
                })
    }
}






