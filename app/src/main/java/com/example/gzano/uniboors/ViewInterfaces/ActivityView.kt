package com.example.gzano.uniboors.ViewInterfaces

import org.altbeacon.beacon.BeaconConsumer

/**
 * Created by gzano on 15/11/2017.
 */
interface ActivityView {

    interface NavigationView : BeaconConsumer {

        fun askPermission()

        fun loadWebViewClass(url: String)

        fun startMonitoring()

        fun addRegionNotifier()

        fun removeRegionNotifier()

        fun createBuilder(classroomName: String, classroomKey: String)







    }
}