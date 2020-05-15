package com.lavreniuk.campassistant.utils

import java.util.Date
import java.util.Calendar

object ConverterUtils {

    fun fromDateToString(date: Date): String {
        val day = date.date
        val month = date.month + 1
        val year = date.year + 1900
        return "$day/$month/$year"
    }

    fun fromStringToDate(text: String): Date {
        val dateArray = text.split("/")
        val calendar = Calendar.getInstance()
        calendar.set(dateArray[2].toInt(), dateArray[1].toInt() - 1, dateArray[0].toInt())
        return calendar.time
    }

    fun isCorrectDateString(dateString: String): Boolean {
        return dateString.matches(Regex("\\d{1,2}/\\d{1,2}/\\d{4}"))
    }
}
