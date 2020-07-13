package com.lavreniuk.campassistant.pupil

data class PupilWithInfo(
    val pupilId: String,
    val firstName: String,
    val lastName: String?,
    val info: String?,
    val photo: String?
) {
    fun getFullName(): String {
        return lastName?.let { "$firstName $it" } ?: firstName
    }
}
