package com.lavreniuk.campassistant.reports

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportRepo @Inject constructor(
    private val reportDao: ReportDao
)
