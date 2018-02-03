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


        fun getLessonTime(dayOfWeek: Int, lesson: Lesson): LessonTime {
            return if (lesson.schedule.daysAndTime.containsKey(dayOfWeek)) {
                lesson.schedule.daysAndTime[dayOfWeek]!!
            } else {
                lesson.schedule.daysAndTime[DateTime().dayOfWeek]!!
            }
        }

        fun getClosestDayOfLesson(daysOfWeek: Array<Int>): Int {
            val localTime = DateTime()
            val today = localTime.dayOfWeek
            var temp = daysOfWeek[0]
            for (dayOfTheWeek in daysOfWeek) {
                if (dayOfTheWeek > today && temp > today && dayOfTheWeek < temp) {
                    temp = dayOfTheWeek
                }
                if (dayOfTheWeek < today && temp < today && dayOfTheWeek < temp) {
                    temp = dayOfTheWeek

                }
                if (dayOfTheWeek > today) {
                    temp = dayOfTheWeek
                }
                if (dayOfTheWeek == today) {
                    return 0
                }

            }
            return temp
        }




    }


}

data class LessonTime(val timeStart: Int, val timeEnd: Int, val roomName: String)
data class Hour(val hour: Int)
data class Minute(val minute: Int)