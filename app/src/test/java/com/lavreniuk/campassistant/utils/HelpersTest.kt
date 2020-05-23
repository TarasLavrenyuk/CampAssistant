package com.lavreniuk.campassistant.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class HelpersTest {

    companion object {
        @JvmStatic
        fun firstLetterUpperCaseTestData() = listOf(
            Arguments.of("", null),
            Arguments.of(null, null),
            Arguments.of("taras", "T")
        )
    }

    @ParameterizedTest
    @MethodSource("firstLetterUpperCaseTestData")
    fun `first letter upper case test`(string: String?, firstLetter: String?) {
        assertEquals(
            string.getFirstLetterUpperCase(),
            firstLetter
        )
    }
}