package com.lavreniuk.campassistant.repositories

import androidx.lifecycle.LiveData
import com.lavreniuk.campassistant.dao.PupilParamDao
import com.lavreniuk.campassistant.models.PupilParam
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PupilParamRepo @Inject constructor(
    private val pupilParamDao: PupilParamDao
) {

    fun getPupilGeneralParams(pupilId: String): LiveData<List<PupilParam>> =
        pupilParamDao.getPupilGeneralParams(pupilId)

    fun getPupilHealthParams(pupilId: String): LiveData<List<PupilParam>> =
        pupilParamDao.getPupilHealthParams(pupilId)

    fun deleteById(pupilParamId: String) = pupilParamDao.deleteById(pupilParamId)

    fun updateParam(param: PupilParam) = pupilParamDao.update(param)

    fun createParam(param: PupilParam) = pupilParamDao.insert(param)

    fun save(pupilParams: List<PupilParam>) = pupilParamDao.insert(*pupilParams.toTypedArray())
}
