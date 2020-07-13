package com.lavreniuk.campassistant.user

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
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
