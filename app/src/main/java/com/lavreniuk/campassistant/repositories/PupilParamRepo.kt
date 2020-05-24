package com.lavreniuk.campassistant.repositories

import androidx.lifecycle.LiveData
import com.lavreniuk.campassistant.dao.PupilParamDao
import com.lavreniuk.campassistant.dao.SquadDao
import com.lavreniuk.campassistant.models.PupilParam
import com.lavreniuk.campassistant.models.Squad
import com.lavreniuk.campassistant.models.crossrefs.SquadWithPupils
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PupilParamRepo @Inject constructor(
    private val pupilParamDao: PupilParamDao
) {

    fun getPupilParams(pupilId: String): LiveData<List<PupilParam>> =
        pupilParamDao.getPupilParams(pupilId)

    fun deleteById(pupilParamId: String) = pupilParamDao.deleteById(pupilParamId)

    fun updateParam(param: PupilParam) = pupilParamDao.update(param)
}
