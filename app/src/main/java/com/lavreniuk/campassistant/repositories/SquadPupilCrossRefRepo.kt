package com.lavreniuk.campassistant.repositories

import com.lavreniuk.campassistant.dao.SquadPupilCrossRefDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SquadPupilCrossRefRepo @Inject constructor(
    private val squadPupilCrossRefDao: SquadPupilCrossRefDao
) {

    fun deletePupilFromSquad(squadId: String) = squadPupilCrossRefDao.deletePupilFromSquad(squadId)

}
