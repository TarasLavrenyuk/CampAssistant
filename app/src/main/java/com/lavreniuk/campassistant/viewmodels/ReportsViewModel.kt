package com.lavreniuk.campassistant.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.lavreniuk.campassistant.dao.AppDatabase
import com.lavreniuk.campassistant.repositories.ReportRepo

class ReportsViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val reportRepo: ReportRepo =
        ReportRepo(AppDatabase.getInstance(application).reportDao())
}
