package com.example.gzano.uniboors.ViewInterfaces

/**
 * Created by gzano on 15/11/2017.
 */
interface ActivityView {

    interface NavigationView {
        fun setETA(estimatedTimeOfArrival: String)

        fun setCurrentPosition(currentPosition: String)

        fun setDestination(destination: String)
    }
}