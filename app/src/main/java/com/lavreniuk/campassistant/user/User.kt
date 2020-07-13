package com.lavreniuk.campassistant.user

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Singleton class - only one instance per application
 */
@Entity(tableName = "user")
class User(
    @PrimaryKey val userId: String = USER_ID,
    var firstName: String,
    var lastName: String? = null,
    var photo: String? = null
) {
    companion object {
        const val USER_ID = "USER_ID"
    }

    fun getFullName(): String {
        return lastName?.let { "$firstName $it" } ?: firstName
    }
}
