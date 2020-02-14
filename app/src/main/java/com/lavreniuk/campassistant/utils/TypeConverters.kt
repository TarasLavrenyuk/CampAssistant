package com.lavreniuk.campassistant.utils

import androidx.room.TypeConverter
import com.lavreniuk.campassistant.enums.Gender
import com.lavreniuk.campassistant.enums.PersonType
import java.util.*

class TypeConverters {

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

    @TypeConverter
    fun fromPersonTypeToString(personType: PersonType) = "$personType"

    @TypeConverter
    fun fromStringToPersonType(personTypeString: String) = Gender.valueOf(personTypeString)


}