package com.lavreniuk.campassistant.repositories

import androidx.lifecycle.LiveData
import com.lavreniuk.campassistant.dao.PupilDao
import com.lavreniuk.campassistant.models.Pupil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PupilRepo @Inject constructor(
    private val pupilDao: PupilDao
) {

    fun getPupilList(squadId: String): LiveData<List<Pupil>> = pupilDao.getPupilList(squadId)
}
