package com.example.gzano.uniboors.Presenter.PresenterInterface

import com.example.gzano.uniboors.Model.Lesson

/**
 * Created by gzano on 30/10/2017.
 */
interface Presenter {

    fun onCreate()

    interface LessonsPresenter : Presenter {

        fun addLesson(lesson: Lesson, position: Int)

        fun removeLesson(lesson: Lesson, position: Int)


    }
}