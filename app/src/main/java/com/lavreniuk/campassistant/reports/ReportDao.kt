package com.lavreniuk.campassistant.reports

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface ReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(report: Report)

    @Update
    fun update(report: Report)
}
