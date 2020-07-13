package com.lavreniuk.campassistant.repositories

import com.lavreniuk.campassistant.dao.ReportDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportRepo @Inject constructor(
    private val reportDao: ReportDao
)
