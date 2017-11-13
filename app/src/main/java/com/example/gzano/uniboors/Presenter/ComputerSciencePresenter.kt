package com.example.gzano.uniboors.Presenter

import android.util.Log
import com.example.gzano.uniboors.Model.Lesson
import com.example.gzano.uniboors.Model.LessonSchedule
import com.example.gzano.uniboors.Model.LessonTime
import com.example.gzano.uniboors.Model.Lessons
import com.example.gzano.uniboors.Presenter.PresenterInterface.Presenter
import com.example.gzano.uniboors.R
import com.example.gzano.uniboors.ViewInterfaces.FragmentView
import com.example.gzano.uniboors.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * Created by gzano on 30/10/2017.
 */
class ComputerSciencePresenter(val lessonView: FragmentView.LessonFragmentView) : Presenter.LessonsPresenter {

    private val databaseRef = FirebaseDatabase.getInstance().getReference(Constants.CESENA_CAMPUS_NODE).child("Corsi")
    private val userLessonDatabaseRef = FirebaseDatabase.getInstance().getReference(Constants.NODE_USERS_PATH).child(FirebaseAuth.getInstance().currentUser?.uid).child("lessons").ref

    //  init {
//        userLessonDatabaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            val userLessons = ArrayList<String>()
//            val campusLessons = ArrayList<Lesson>()
//            override fun onCancelled(p0: DatabaseError?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onDataChange(p0: DataSnapshot?) {
    //    p0?.children?.mapTo(userLessons) { it.child("lessonType").value.toString() }
    //    Log.d("TAGUSERLES", userLessons.toString())
//                databaseRef.addChildEventListener(object : ChildEventListener {
//                    override fun onCancelled(p0: DatabaseError?) {
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                    }
//
//                    override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                    }
//
//                    override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
//                    }
//
//                    override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
//                        val scheduleDatabaseRef = p0?.child("schedule")
//                        if (p0 != null) {
//
//                            Log.d("TAGCHILD", p0.toString() + " " + p0.key.toString())
//                            val dayAndTime = HashMap<Int, LessonTime>()
//                            for (snapshot in scheduleDatabaseRef?.children!!) {
//                                val timeStart = snapshot?.child("timeStart")?.value.toString().toInt()
//                                val timeEnd = snapshot?.child("timeEnd")?.value.toString().toInt()
//                                val roomString = snapshot?.child("place")?.value.toString()
//                                val dayOfWeek = snapshot.child("value")?.value.toString().toInt()
//                                Log.d("TAGSCHEDULE", timeEnd.toString() + " " + timeStart.toString() + " " + roomString + " " + dayOfWeek)
//                                dayAndTime.put(dayOfWeek, LessonTime(timeStart, timeEnd, roomString))
//
//                            }
//                            val lessonSchedule = LessonSchedule.createLessonSchedule(dayAndTime)
//                            when (Lessons.valueOf(p0.child("type").value.toString())) {
//                                Lessons.APPLICAZIONI_WEB -> {
//
//                                    campusLessons.add(Lesson.WebApplication("Applicazioni Web", Lessons.APPLICAZIONI_WEB, p0.child("credits").value.toString().toInt(), p0.child("teacher").value.toString(), lessonSchedule))
//
//                                }
//                                Lessons.SVILUPPO_SISTEMI_SOFTWARE -> {
//                                    campusLessons.add(Lesson.SoftwareDevelopment("Sviluppo di Sistemi Software", Lessons.SVILUPPO_SISTEMI_SOFTWARE, p0.child("credits").value.toString().toInt(), p0.child("teacher").value.toString(), lessonSchedule))
//                                }
//
//                                Lessons.SISTEMI_DISTRIBUITI -> TODO()
//                                Lessons.MACHINE_LEARNING -> campusLessons.add(Lesson.MachineLearning("Machine Learning", Lessons.MACHINE_LEARNING, p0.child("credits").value.toString().toInt(), p0.child("teacher").value.toString(), lessonSchedule))
//
//                                Lessons.SISTEMI_INFORMATIVI -> TODO()
//                                Lessons.SICUREZZA_DELLE_RETI -> TODO()
//                                Lessons.LCMC -> TODO()
//                                Lessons.DATA_MINING -> {
//                                    campusLessons.add(Lesson.DataMining("Data Mining", Lessons.DATA_MINING, p0.child("credits").value.toString().toInt(), p0.child("teacher").value.toString(), lessonSchedule))
//                                }
//                            }
//                            if(campusLessons.size==4){
//                                Log.d("TAGCAMPUSLES", campusLessons.toString())
//                            }
//                        }
//
//                    }
//
//                    override fun onChildRemoved(p0: DataSnapshot?) {
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                    }
//
//                })
//                lessonView.setAdapter(ArrayList(), userLessons)
//
//            }
//
//        })
    //   }

    override fun onCreate() {
        userLessonDatabaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            val userLessons = ArrayList<String>()
            val campusLessons = ArrayList<Lesson>()
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?) {
                p0?.children?.mapTo(userLessons) { it.child("lessonType").value.toString() }
                Log.d("TAGUSERLES", userLessons.toString())
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
                            when (Lessons.valueOf(p0.child("type").value.toString())) {
                                Lessons.APPLICAZIONI_WEB -> {

                                    campusLessons.add(Lesson.WebApplication("Applicazioni Web", Lessons.APPLICAZIONI_WEB, p0.child("credits").value.toString().toInt(), p0.child("teacher").value.toString(), lessonSchedule))

                                }
                                Lessons.SVILUPPO_SISTEMI_SOFTWARE -> {
                                    campusLessons.add(Lesson.SoftwareDevelopment("Sviluppo di Sistemi Software", Lessons.SVILUPPO_SISTEMI_SOFTWARE, p0.child("credits").value.toString().toInt(), p0.child("teacher").value.toString(), lessonSchedule))
                                }

                                Lessons.SISTEMI_DISTRIBUITI -> TODO()
                                Lessons.MACHINE_LEARNING -> campusLessons.add(Lesson.MachineLearning("Machine Learning", Lessons.MACHINE_LEARNING, p0.child("credits").value.toString().toInt(), p0.child("teacher").value.toString(), lessonSchedule))

                                Lessons.SISTEMI_INFORMATIVI -> TODO()
                                Lessons.SICUREZZA_DELLE_RETI -> TODO()
                                Lessons.LCMC -> TODO()
                                Lessons.DATA_MINING -> {
                                    campusLessons.add(Lesson.DataMining("Data Mining", Lessons.DATA_MINING, p0.child("credits").value.toString().toInt(), p0.child("teacher").value.toString(), lessonSchedule))
                                }
                            }
                            if (campusLessons.size == 4) {
                                Log.d("TAGCAMPUSLES", campusLessons.toString())
                                lessonView.setAdapter(campusLessons, userLessons)

                            }
                        }

                    }

                    override fun onChildRemoved(p0: DataSnapshot?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })

            }

        })
    }

    //        databaseRef.addChildEventListener(object : ChildEventListener {
//            override fun onCancelled(p0: DatabaseError?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
//            }
//
//            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
//                val scheduleDatabaseRef = p0?.child("schedule")
//                if (p0 != null) {
//
//                    Log.d("TAGCHILD", p0.toString() + " " + p0.key.toString())
//                    val dayAndTime = HashMap<Int, LessonTime>()
//                    for (snapshot in scheduleDatabaseRef?.children!!) {
//                        val timeStart = snapshot?.child("timeStart")?.value.toString().toInt()
//                        val timeEnd = snapshot?.child("timeEnd")?.value.toString().toInt()
//                        val roomString = snapshot?.child("place")?.value.toString()
//                        val dayOfWeek = snapshot.child("value")?.value.toString().toInt()
//                        Log.d("TAGSCHEDULE", timeEnd.toString() + " " + timeStart.toString() + " " + roomString + " " + dayOfWeek)
//                        dayAndTime.put(dayOfWeek, LessonTime(timeStart, timeEnd, roomString))
//
//                    }
//                    val lessonSchedule = LessonSchedule.createLessonSchedule(dayAndTime)
//                    when (Lessons.valueOf(p0.child("type").value.toString())) {
//                        Lessons.APPLICAZIONI_WEB -> {
//
//                            lessonView.addLesson(Lesson.WebApplication("Applicazioni Web", Lessons.APPLICAZIONI_WEB, p0.child("credits").value.toString().toInt(), p0.child("teacher").value.toString(), lessonSchedule))
//
//                        }
//                        Lessons.SVILUPPO_SISTEMI_SOFTWARE -> {
//                            lessonView.addLesson(Lesson.SoftwareDevelopment("Sviluppo di Sistemi Software", Lessons.SVILUPPO_SISTEMI_SOFTWARE, p0.child("credits").value.toString().toInt(), p0.child("teacher").value.toString(), lessonSchedule))
//                        }
//
//                        Lessons.SISTEMI_DISTRIBUITI -> TODO()
//                        Lessons.MACHINE_LEARNING -> lessonView.addLesson(Lesson.MachineLearning("Machine Learning", Lessons.MACHINE_LEARNING, p0.child("credits").value.toString().toInt(), p0.child("teacher").value.toString(), lessonSchedule))
//
//                        Lessons.SISTEMI_INFORMATIVI -> TODO()
//                        Lessons.SICUREZZA_DELLE_RETI -> TODO()
//                        Lessons.LCMC -> TODO()
//                        Lessons.DATA_MINING -> {
//                            lessonView.addLesson(Lesson.DataMining("Data Mining", Lessons.DATA_MINING, p0.child("credits").value.toString().toInt(), p0.child("teacher").value.toString(), lessonSchedule))
//                        }
//                    }
//                }
//
//            }
//
//            override fun onChildRemoved(p0: DataSnapshot?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//        })
//    }
//
    override fun addLesson(lesson: Lesson, position: Int) {
        userLessonDatabaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {

                if (p0?.value?.toString() != null) {
                    val isPresent = p0.children?.any { it.key == lesson.name }
                    val data = HashMap<String, String>()
                    data.put("lessonType", lesson.type.toString())
                    if (!isPresent!!) {
                        userLessonDatabaseRef.child(lesson.name).setValue(data).addOnCompleteListener {
                            lessonView.setNewClickListener(R.drawable.ic_check_added, position, lesson)

                        }
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

    }

    override fun removeLesson(lesson: Lesson, position: Int) {
        userLessonDatabaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {
                if (p0?.childrenCount == 1.toLong()) {
                    p0.ref.setValue(Constants.EMPTY_NODE_VALUE).addOnCompleteListener {
                        if (it.isComplete) {
                            lessonView.setNewClickListener(R.drawable.ic_add, position, lesson)
                            lessonView.removeUserLesson(lesson)

                        }
                    }
                }
                p0?.children?.filter { it.key == lesson.name }?.forEach {
                    it.ref.removeValue().addOnCompleteListener({
                        if (it.isComplete) {
                            lessonView.setNewClickListener(R.drawable.ic_add, position, lesson)
                            lessonView.removeUserLesson(lesson)
                        }
                    })
                }

            }


            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}