package com.lavreniuk.campassistant.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.lavreniuk.campassistant.models.Pupil
import com.lavreniuk.campassistant.models.dto.PupilWithInfo

@Dao
interface PupilDao : AbstractDao<Pupil> {

    @Query("DELETE FROM pupils ")
    override fun deleteAll()

    @Query("SELECT P.* FROM pupils P WHERE P.pupilId = :pupilId ")
    fun getPupil(pupilId: String): LiveData<Pupil>

    @Query("SELECT P.* FROM pupils P WHERE P.pupilId = :pupilId ")
    fun getPupilObject(pupilId: String): Pupil?

    @Query("SELECT P.photo FROM pupils P WHERE P.pupilId = :pupilId ")
    fun getPupilAvatarObject(pupilId: String): String?

    @Query("SELECT P.photo FROM pupils P WHERE P.pupilId = :pupilId ")
    fun getPupilAvatar(pupilId: String): LiveData<String>

    @Query("UPDATE pupils SET photo = :path WHERE pupilId = :pupilId")
    fun updateAvatar(pupilId: String, path: String? = null)

    @Query(
        """SELECT P.pupilId, P.firstName, P.lastName, P.photo, PPO.paramValue as info FROM pupils P 
            LEFT JOIN (SELECT PPI.* FROM pupil_params PPI WHERE PPI.paramType = 'Room' ) PPO ON P.pupilId = PPO.pupilId
        WHERE P.pupilId IN (SELECT CR.pupilId FROM SquadPupilCrossRef CR WHERE CR.squadId = :squadId )
        ORDER BY P.lastName ASC """
    )
    fun getSquadPupilsWithRooms(squadId: String): LiveData<List<PupilWithInfo>>

    @Query(
        """SELECT P.pupilId, P.firstName, P.lastName, P.photo, PPO.paramValue as info FROM pupils P 
            LEFT JOIN (SELECT PPI.* FROM pupil_params PPI WHERE PPI.paramType = 'Room' ) PPO ON P.pupilId = PPO.pupilId
        WHERE P.pupilId IN (SELECT CR.pupilId FROM SquadPupilCrossRef CR WHERE CR.squadId = :squadId )
        ORDER BY P.lastName ASC """
    )
    fun getSquadPupilsWithRoomsObjects(squadId: String): List<PupilWithInfo>

    @Query(
        """SELECT P.pupilId, P.firstName, P.lastName, P.photo, S.squadName AS info FROM pupils P 
            JOIN SquadPupilCrossRef CROSS_REF ON P.pupilId = CROSS_REF.pupilId JOIN squads S ON CROSS_REF.squadId = S.squadId
            ORDER BY P.lastName ASC 
        """
    )
    fun getAllPupilsWithSquadsObjects(): List<PupilWithInfo>

    @Query(
        """SELECT P.pupilId, P.firstName, P.lastName, P.photo, PPO.paramValue AS info FROM pupils P 
            LEFT JOIN (SELECT PPI.* FROM pupil_params PPI WHERE PPI.paramType = 'Room' ) PPO ON P.pupilId = PPO.pupilId
            WHERE P.pupilId IN (SELECT CR.pupilId FROM SquadPupilCrossRef CR 
                WHERE CR.squadId = (SELECT S.squadId FROM squads S WHERE S.isCurrent = 1) 
            )
            ORDER BY P.lastName ASC 
        """
    )
    fun getPupilsOfCurrentSquadWithRooms(): LiveData<List<PupilWithInfo>>
}
