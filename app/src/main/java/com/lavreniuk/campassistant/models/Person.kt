package com.lavreniuk.campassistant.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lavreniuk.campassistant.enums.Gender
import com.lavreniuk.campassistant.enums.PersonType
import com.lavreniuk.campassistant.utils.GeneratorUtils
import java.util.*

@Entity(tableName = "persons")
class Person(
    @PrimaryKey val personId: String = GeneratorUtils.generateUUID(),
    var firstName: String,
    var lastName: String? = null,
    var db: Date? = null,
    var photo: String? = null,
    var gender: Gender? = null,
    var personType: PersonType
)