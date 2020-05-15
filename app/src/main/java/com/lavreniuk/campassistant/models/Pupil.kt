package com.lavreniuk.campassistant.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lavreniuk.campassistant.utils.GeneratorUtils
import java.util.Date

@Entity(tableName = "pupils")
data class Pupil(
    @PrimaryKey val pupilId: String = GeneratorUtils.generateUUID(),
    var firstName: String,
    var lastName: String? = null,
    var room: String? = null,
    var photo: String? = null,
    var bDay: Date? = null,
    var height: Int? = null,
    var weight: Int? = null,
    var blood: String? = null
) {
    fun getFullName(): String {
        return lastName?.let { "$firstName $it" } ?: firstName
    }
}
