package com.lavreniuk.campassistant.utils

import java.util.Date
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class ConverterUtilsTest {

    companion object {
        @JvmStatic
        fun dateToStringTestData() = listOf(
            Arguments.of(Date(1577840400000), "1/1/2020"),
            Arguments.of(Date(1293757200000), "31/12/2010"),
            Arguments.of(Date(858992400000), "22/3/1997"),
            Arguments.of(Date(999651600000), "5/9/2001")
        )

        @JvmStatic
        fun stringToDateTestData() = listOf(
            Arguments.of("1/1/2020"),
            Arguments.of("31/12/2010"),
            Arguments.of("22/3/1997"),
            Arguments.of("19/4/1971"),
            Arguments.of("24/8/2003"),
            Arguments.of("5/9/2001")
        )

        @JvmStatic
        fun isCorrectDateFormatTestData() = listOf(
            Arguments.of("01/01/2020", true),
            Arguments.of("31/12/2010", true),
            Arguments.of("22/03/1997", true),
            Arguments.of("19-4-1971", false),
            Arguments.of("24.08.2003", false),
            Arguments.of("24.08.2003", false),
            Arguments.of("2003.08.24", false),
            Arguments.of("1971-4-19", false)
        )
    }

    @ParameterizedTest
    @MethodSource("dateToStringTestData")
    fun `from date to string test`(date: Date, dateAsString: String) {
        assertEquals(
            dateAsString,
            ConverterUtils.fromDateToString(date)
        )
    }

    @ParameterizedTest
    @MethodSource("stringToDateTestData")
    fun `string to date converter test`(dateAsString: String) {
        assertEquals(
            dateAsString,
            ConverterUtils.fromDateToString(ConverterUtils.fromStringToDate(dateAsString)!!)
        )
    }

    @ParameterizedTest
    @MethodSource("isCorrectDateFormatTestData")
    fun `is correct date format test`(dateAsString: String, expectedValue: Boolean) {
        assertEquals(
            expectedValue,
            ConverterUtils.isCorrectDateString(dateAsString)
        )
    }
}
