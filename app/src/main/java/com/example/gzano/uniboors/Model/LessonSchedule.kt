package com.example.gzano.uniboors.Model

import org.joda.time.DateTime
import java.util.*

/**
 * Created by gzano on 31/10/2017.
 */
class LessonSchedule(val daysAndTime: HashMap<Int, LessonTime>) {


    companion object Utils {

        private lateinit var daysAndTime: HashMap<Int, LessonTime>

        fun createLessonSchedule(dt: HashMap<Int, LessonTime>): LessonSchedule {
            daysAndTime = dt
            return LessonSchedule(dt)
        }

        fun isLessonAboutToStart(timeStart: Int): Boolean {
            val jodaDate = DateTime(Date())
            return getTimeDifference(timeStart) <= 5
        }

        fun getClosestDayOfLesson(daysOfWeek: Array<Int>): Int {
            val localTime = DateTime()
            var temp: Int? = null
            for (i in daysOfWeek) {
                daysOfWeek
                        .filter { localTime.withDayOfWeek(i).isBefore(localTime.withDayOfWeek(it)) }
                        .forEach { temp = i }
            }
            return temp!!
        }

        fun isToday(daysOfWeek: Array<Int>): Boolean {
            val localTime = DateTime()
            return daysOfWeek.any { localTime.dayOfWeek == it }
        }

        private fun getTimeDifference(timeStart: Int): Int {
            val jodaDate = DateTime(Date())
            return if (jodaDate.hourOfDay - timeStart != 0) {
                60 - jodaDate.minuteOfDay
            } else {
                0
            }
        }


    }


}

data class LessonTime(val timeStart: Int, val timeEnd: Int, val roomName: String)
data class Hour(val hour: Int)
data class Minute(val minute: Int)