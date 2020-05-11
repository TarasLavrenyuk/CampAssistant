package com.lavreniuk.campassistant.repositories

import androidx.lifecycle.LiveData
import com.lavreniuk.campassistant.dao.SquadDao
import com.lavreniuk.campassistant.models.crossrefs.SquadWithPupils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SquadRepo @Inject constructor(
    private val squadDao: SquadDao
) {
    val squadsWithPupils: LiveData<List<SquadWithPupils>> = squadDao.getSquadsWithPupils()
}
