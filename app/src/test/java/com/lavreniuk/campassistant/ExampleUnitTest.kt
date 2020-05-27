package com.lavreniuk.campassistant

import org.junit.jupiter.api.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val string: String? = null
//        val string: String? = "zLEG"
//        val string = ""
        val res = if (string.isNullOrBlank()) null else { string[0].toUpperCase().toString() }
//        val res = string?.get(0)?.toUpperCase().toString()
        if (res == null) {
            println("null")
        } else {
            println("not null: $res")
        }
    }
}
