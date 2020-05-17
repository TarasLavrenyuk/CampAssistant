package com.lavreniuk.campassistant.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.lavreniuk.campassistant.models.Pupil

@Dao
interface PupilDao : AbstractDao<Pupil> {

    @Query("DELETE FROM pupils ")
    override fun deleteAll()

    @Query(
        """SELECT P.* FROM pupils P 
        WHERE P.pupilId IN (SELECT CR.pupilId FROM SquadPupilCrossRef CR WHERE CR.squadId = :squadId ) 
        ORDER BY P.lastName ASC """
    )
    fun getPupilList(squadId: String): LiveData<List<Pupil>>
}
