package com.lavreniuk.campassistant.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.lavreniuk.campassistant.dao.ParamDao
import com.lavreniuk.campassistant.models.Param
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ParamRepo @Inject constructor(
    personId: String,
    private val paramDao: ParamDao
) {
    val personParams: LiveData<List<Param>> = paramDao.getParamsByOwnerId(personId)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun insertParam(param: Param) {
        paramDao.insert(param)
    }
}
