package com.example.gzano.uniboors.Presenter.PresenterInterface

/**
 * Created by gzano on 30/10/2017.
 */
interface Presenter {

    fun onCreate()

    interface LessonsPresenter : Presenter {

        fun startActivity()


    }
}