package com.lavreniuk.campassistant.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lavreniuk.campassistant.enums.Gender
import com.lavreniuk.campassistant.utils.GeneratorUtils
import java.util.*

@Entity(tableName = "users")
data class User(
    @PrimaryKey val userId: String = GeneratorUtils.generateUUID(),
    val firstName: String,
    val secondName: String? = null,
    val lastName: String? = null,
    val birthday: Date? = null,
    val photo: String? = null,
    val addInfo: String? = null,
    val gender: Gender? = null
)