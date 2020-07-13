package com.lavreniuk.campassistant.reports

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ReportUtils {

    fun getReportDate(reportDate: Date): String =
        SimpleDateFormat("dd MMMM yyyy", Locale.GERMAN).format(reportDate)
}
