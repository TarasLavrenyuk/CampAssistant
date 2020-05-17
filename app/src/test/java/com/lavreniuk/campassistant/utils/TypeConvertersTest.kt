package com.lavreniuk.campassistant.utils

import com.lavreniuk.campassistant.enums.Gender
import com.lavreniuk.campassistant.enums.ParameterType
import com.lavreniuk.campassistant.enums.SocialType
import java.util.Date
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class TypeConvertersTest {

    companion object {
        @JvmStatic
        fun genderConverterTestData() = listOf(
            Arguments.of(Gender.Male),
            Arguments.of(Gender.Female),
            Arguments.of(null)
        )

        @JvmStatic
        fun dateConverterTestData() = listOf(
            Arguments.of(Date(999651600000)),
            Arguments.of(Date(1607043600000)),
            Arguments.of(Date(1104541200000)),
            Arguments.of(Date(1435513363000)),
            Arguments.of(null)
        )

        @JvmStatic
        fun socialTypeConverterTestData() = listOf(
            Arguments.of(SocialType.facebook),
            Arguments.of(SocialType.skype),
            Arguments.of(null)
        )

        @JvmStatic
        fun parameterTypeConverterTestData() = listOf(
            Arguments.of(ParameterType.Social),
            Arguments.of(ParameterType.Date),
            Arguments.of(null)
        )
    }

    @ParameterizedTest
    @MethodSource("genderConverterTestData")
    fun `gender converter test`(gender: Gender?) {
        val typeConverter = TypeConverters()
        assertEquals(
            gender,
            typeConverter.fromStringToGender(typeConverter.fromGenderToString(gender))
        )
    }

    @ParameterizedTest
    @MethodSource("dateConverterTestData")
    fun `date converter test`(date: Date?) {
        val typeConverter = TypeConverters()
        assertEquals(
            date,
            typeConverter.fromStringToDate(typeConverter.fromDateToString(date))
        )
    }

    @ParameterizedTest
    @MethodSource("socialTypeConverterTestData")
    fun `social type converter test`(socialType: SocialType?) {
        val typeConverter = TypeConverters()
        assertEquals(
            socialType,
            typeConverter.fromStringToSocialType(typeConverter.fromSocialTypeToString(socialType))
        )
    }

    @ParameterizedTest
    @MethodSource("parameterTypeConverterTestData")
    fun `parameter type converter test`(parameterType: ParameterType?) {
        val typeConverter = TypeConverters()
        assertEquals(
            parameterType,
            typeConverter.fromStringToParameterType(
                typeConverter.fromParameterTypeToString(
                    parameterType
                )
            )
        )
    }
}
