package com.lavreniuk.campassistant.repositories

import androidx.lifecycle.LiveData
import com.lavreniuk.campassistant.dao.SquadDao
import com.lavreniuk.campassistant.models.Squad
import com.lavreniuk.campassistant.models.crossrefs.SquadWithPupils
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SquadRepo @Inject constructor(
    private val squadDao: SquadDao
) {
    val squadsWithPupils: LiveData<List<SquadWithPupils>> = squadDao.getSquadsWithPupils()

    fun getSquad(squadId: String): LiveData<Squad> = squadDao.getSquad(squadId)

    fun updateSquadName(squadId: String, newName: String) =
        squadDao.updateSquadName(squadId, newName)

    fun updateFromDate(squadId: String, newFromDate: Date?) =
        squadDao.updateSquadFromDate(squadId, newFromDate)

    fun updateUntilDate(squadId: String, newUntilDate: Date?) =
        squadDao.updateSquadUntilDate(squadId, newUntilDate)

    fun setAllSquadsInactive() = squadDao.setAllSquadsInactive()

    fun updateSquadIsActive(squadId: String, isActive: Boolean = true) = squadDao.setSquadIsActive(squadId, isActive)

    fun save(squad: Squad) = squadDao.insert(squad)

    fun getSquadObject(squadId: String): Squad? = squadDao.getSquadObject(squadId)

    fun deleteSquad(squadId: String) = squadDao.deleteSquad(squadId)
}
