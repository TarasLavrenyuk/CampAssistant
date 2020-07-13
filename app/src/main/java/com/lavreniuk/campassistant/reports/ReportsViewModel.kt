package com.lavreniuk.campassistant.reports

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.lavreniuk.campassistant.AppDatabase

class ReportsViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val reportRepo: ReportRepo =
        ReportRepo(
            AppDatabase.getInstance(
                application
            ).reportDao()
        )
}
