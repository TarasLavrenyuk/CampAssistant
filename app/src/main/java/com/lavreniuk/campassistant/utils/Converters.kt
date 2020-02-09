package com.lavreniuk.campassistant.utils

import androidx.room.TypeConverter
import com.lavreniuk.campassistant.enums.Gender
import java.util.*

class Converters {

    @TypeConverter
    fun fromGenderToString(gender: Gender?): String = "$gender"

    @TypeConverter
    fun fromStringToGender(genderString: String): Gender? {
        return if (genderString == "null") null
        else Gender.valueOf(genderString)
    }

    @TypeConverter
    fun fromDateToString(date: Date?): String = "${date?.time}"

    @TypeConverter
    fun fromStringToDate(dateString: String): Date? {
        return if (dateString == "null") null
        else Date(dateString.toLong())
    }
}