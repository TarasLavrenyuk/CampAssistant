package com.lavreniuk.campassistant.utils

import androidx.room.TypeConverter
import com.lavreniuk.campassistant.enums.Gender
import com.lavreniuk.campassistant.enums.ParameterType
import com.lavreniuk.campassistant.enums.SocialType
import java.util.Date

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
    fun fromParameterTypeToString(parameterType: ParameterType?): String = "$parameterType"

    @TypeConverter
    fun fromStringToParameterType(parameterType: String): ParameterType? {
        return if (parameterType == "null") null
        else ParameterType.valueOf(parameterType)
    }

    @TypeConverter
    fun fromSocialTypeToString(socialType: SocialType?): String = "$socialType"

    @TypeConverter
    fun fromStringToSocialType(socialType: String): SocialType? {
        return if (socialType == "null") null
        else SocialType.valueOf(socialType)
    }
}
