package com.example.gzano.uniboors.Model

/**
 * Created by gzano on 30/10/2017.
 */
interface Lesson {
    val name: String
    val type: Lessons
    val credits: Int
    val teacher: String
    val schedule: LessonSchedule

    enum class Teacher {

        VIROLI, RICCI, OMICINI, MIRRI, MALTONI, DANGELO, GOLFARELLI, BRAVETTI;

    }

    data class WebApplication(override val name: String, override val type: Lessons, override val credits: Int, override val teacher: String, override val schedule: LessonSchedule) : Lesson
    data class SoftwareDevelopment(override val name: String, override val type: Lessons, override val credits: Int, override val teacher: String, override val schedule: LessonSchedule) : Lesson
    data class DistributedSystems(override val name: String, override val type: Lessons, override val credits: Int, override val teacher: String, override val schedule: LessonSchedule) : Lesson
    data class MachineLearning(override val name: String, override val type: Lessons, override val credits: Int, override val teacher: String, override val schedule: LessonSchedule) : Lesson
    data class NetsSecurity(override val name: String, override val type: Lessons, override val credits: Int, override val teacher: String, override val schedule: LessonSchedule) : Lesson
    data class InformativeSystems(override val name: String, override val type: Lessons, override val credits: Int, override val teacher: String, override val schedule: LessonSchedule) : Lesson
    data class DataMining(override val name: String, override val type: Lessons, override val credits: Int, override val teacher: String, override val schedule: LessonSchedule) : Lesson
    data class DecisionSupport(override val name: String, override val type: Lessons, override val credits: Int, override val teacher: String, override val schedule: LessonSchedule) : Lesson
    data class PervasiveComputing(override val name: String, override val type: Lessons, override val credits: Int, override val teacher: String, override val schedule: LessonSchedule) : Lesson
    data class RoboticSystems(override val name: String, override val type: Lessons, override val credits: Int, override val teacher: String, override val schedule: LessonSchedule) : Lesson
    data class SmartCity(override val name: String, override val type: Lessons, override val credits: Int, override val teacher: String, override val schedule: LessonSchedule) : Lesson
    data class BI(override val name: String, override val type: Lessons, override val credits: Int, override val teacher: String, override val schedule: LessonSchedule) : Lesson
    data class BigData(override val name: String, override val type: Lessons, override val credits: Int, override val teacher: String, override val schedule: LessonSchedule) : Lesson


}
