package com.example.gzano.uniboors.Model

/**
 * Created by gzano on 04/10/2017.
 */
interface Room {
    val roomType: RoomType

    val roomName: String

    var isFriendly: Boolean

    val floor: Floor


    data class ClassRoom(override val roomType: RoomType, override val roomName: String, override var isFriendly: Boolean, override val floor: Floor) : Room
    data class ComputerLab(override val roomType: RoomType, override val roomName: String, override var isFriendly: Boolean, override val floor: Floor) : Room
    data class GenericRoom(override val roomType: RoomType, override val roomName: String, override var isFriendly: Boolean, override val floor: Floor) : Room
}