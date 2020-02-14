package com.lavreniuk.campassistant.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lavreniuk.campassistant.utils.GeneratorUtils

@Entity(tableName = "users")
class User(
    @PrimaryKey val userId: String = GeneratorUtils.generateUUID(),
    firstName: String
): PersonInfo(firstName)