package com.lavreniuk.campassistant.utils

import java.util.*

object ConverterUtils {

    fun fromDateToString(date: Date): String {
        val day = date.date
        val month = date.month + 1
        val year = date.year + 1900
        return "$day/$month/$year"
    }

}