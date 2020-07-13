package com.lavreniuk.campassistant.squad

import androidx.lifecycle.LiveData
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SquadRepo @Inject constructor(
    private val squadDao: SquadDao
) {
    val squadsWithPupils: LiveData<List<SquadWithPupils>> = squadDao.getSquadsWithPupils()
    val activeSquad: LiveData<Squad> = squadDao.getActiveSquad()

    fun updateSquadName(squadId: String, newName: String) =
        squadDao.updateSquadName(squadId, newName)

    fun updateFromDate(squadId: String, newFromDate: Date?) =
        squadDao.updateSquadFromDate(squadId, newFromDate)

    fun updateUntilDate(squadId: String, newUntilDate: Date?) =
        squadDao.updateSquadUntilDate(squadId, newUntilDate)

    fun setAllSquadsInactive() = squadDao.setAllSquadsInactive()

    fun updateSquadIsActive(squadId: String, isActive: Boolean = true) =
        squadDao.setSquadIsActive(squadId, isActive)

    fun save(squad: Squad) = squadDao.insert(squad)

    fun getSquadObject(squadId: String): Squad? = squadDao.getSquadObject(squadId)

    fun deleteSquad(squadId: String) = squadDao.deleteSquad(squadId)

    fun getActiveSquadObject(): Squad? = squadDao.getActiveSquadObject()
}
