package com.example.gzano.uniboors.Model

/**
 * Created by gzano on 04/10/2017.
 */
interface Room {
    val roomType: RoomType

    val roomName: String

    var isFriendly: Boolean


    data class ClassRoom(override val roomType: RoomType, override val roomName: String, override var isFriendly: Boolean) : Room
    data class ComputerLab(override val roomType: RoomType, override val roomName: String, override var isFriendly: Boolean) : Room
    data class GenericRoom(override val roomType: RoomType, override val roomName: String, override var isFriendly: Boolean) : Room
}