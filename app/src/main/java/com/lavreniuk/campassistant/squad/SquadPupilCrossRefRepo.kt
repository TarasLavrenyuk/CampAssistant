package com.lavreniuk.campassistant.squad

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SquadPupilCrossRefRepo @Inject constructor(
    private val squadPupilCrossRefDao: SquadPupilCrossRefDao
) {

    fun deletePupilFromSquad(squadId: String) = squadPupilCrossRefDao.deletePupilFromSquad(squadId)

    fun save(squadPupilCrossRef: SquadPupilCrossRef) = squadPupilCrossRefDao.insert(squadPupilCrossRef)
}
