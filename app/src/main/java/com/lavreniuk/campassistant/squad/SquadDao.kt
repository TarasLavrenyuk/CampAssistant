package com.lavreniuk.campassistant.squad

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lavreniuk.campassistant.AbstractDao
import java.util.Date

@Dao
interface SquadDao : AbstractDao<Squad> {

    /**
     * @return list of all squads with pupils in the following order: first squad is an active one, all the followings are sorted by [Squad.creationDate]
     */
    @Transaction
    @Query("SELECT * FROM squads S ORDER BY S.isCurrent DESC, S.creationDate DESC ")
    fun getSquadsWithPupils(): LiveData<List<SquadWithPupils>>

    @Query("DELETE FROM squads ")
    override fun deleteAll()

    @Query("SELECT S.* FROM squads S WHERE S.squadId = :squadId ")
    fun getSquad(squadId: String): LiveData<Squad>

    @Query("SELECT S.* FROM squads S WHERE S.squadId = :squadId ")
    fun getSquadObject(squadId: String): Squad

    @Query("UPDATE squads SET squadName = :newName WHERE squadId = :squadId ")
    fun updateSquadName(squadId: String, newName: String)

    @Query("UPDATE squads SET `from` = :from WHERE squadId = :squadId ")
    fun updateSquadFromDate(squadId: String, from: Date?)

    @Query("UPDATE squads SET until = :until WHERE squadId = :squadId ")
    fun updateSquadUntilDate(squadId: String, until: Date?)

    @Query("UPDATE squads SET isCurrent = 0 ")
    fun setAllSquadsInactive()

    @Query("UPDATE squads SET isCurrent = :isActive WHERE squadId = :squadId ")
    fun setSquadIsActive(squadId: String, isActive: Boolean)

    @Query("DELETE FROM squads WHERE squadId = :squadId ")
    fun deleteSquad(squadId: String)

    @Query("SELECT S.* FROM squads S WHERE S.isCurrent = 1 ")
    fun getActiveSquadObject(): Squad?

    @Query("SELECT S.* FROM squads S WHERE S.isCurrent = 1 ")
    fun getActiveSquad(): LiveData<Squad>
}
