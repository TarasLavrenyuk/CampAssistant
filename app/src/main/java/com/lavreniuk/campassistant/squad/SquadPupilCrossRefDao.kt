package com.lavreniuk.campassistant.squad

import androidx.room.Dao
import androidx.room.Query
import com.lavreniuk.campassistant.AbstractDao

@Dao
interface SquadPupilCrossRefDao :
    AbstractDao<SquadPupilCrossRef> {

    @Query("DELETE FROM squadpupilcrossref")
    override fun deleteAll()

    @Query("DELETE FROM squadpupilcrossref WHERE squadId = :squadId ")
    fun deletePupilFromSquad(squadId: String)
}
