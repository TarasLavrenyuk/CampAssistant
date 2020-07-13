package com.lavreniuk.campassistant.reports

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lavreniuk.campassistant.utils.GeneratorUtils
import java.util.Date

@Entity(tableName = "reports")
data class Report(
    @PrimaryKey val reportId: String = GeneratorUtils.generateUUID(),
    var reportName: String,
    val created: Date = Date(),
    var updated: Date = Date(),
    val options: List<ReportOption> = mutableListOf(),
    val selectedPupils: List<String> = mutableListOf()
)

enum class ReportOption {
    Params,
    Age,
    Birthday,
    Number,
    Blanks,
    Parents;
}
