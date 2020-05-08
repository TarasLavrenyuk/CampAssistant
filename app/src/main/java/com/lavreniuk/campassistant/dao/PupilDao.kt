package com.lavreniuk.campassistant.dao

import androidx.room.Dao
import androidx.room.Query
import com.lavreniuk.campassistant.models.Pupil

@Dao
interface PupilDao : AbstractDao<Pupil> {

    @Query("DELETE FROM pupils ")
    override fun deleteAll()
}
