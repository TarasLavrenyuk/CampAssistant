package com.lavreniuk.campassistant.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.lavreniuk.campassistant.models.Report

@Dao
interface ReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(report: Report)

    @Update
    fun update(report: Report)
}
