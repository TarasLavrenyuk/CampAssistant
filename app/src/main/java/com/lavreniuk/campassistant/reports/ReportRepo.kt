package com.lavreniuk.campassistant.reports

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportRepo @Inject constructor(
    private val reportDao: ReportDao
) {
    val allReports: LiveData<Report> = reportDao.getAllReports()
}
