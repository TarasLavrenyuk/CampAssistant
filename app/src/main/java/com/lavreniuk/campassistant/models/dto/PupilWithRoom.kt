package com.lavreniuk.campassistant.models.dto

data class PupilWithRoom(
    val pupilId: String,
    val firstName: String,
    val lastName: String?,
    val room: String?,
    val photo: String?
) {
    fun getFullName(): String {
        return lastName?.let { "$firstName $it" } ?: firstName
    }
}