package com.lavreniuk.campassistant.models

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
        const val USER_ID = "76c62e5a-1cb0-4772-9d5c-62b7c39bd3f3"
    }

    fun getFullName(): String {
        return lastName?.let { "$firstName $it" } ?: firstName
    }
}