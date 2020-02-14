package com.lavreniuk.campassistant.repositories

import androidx.lifecycle.LiveData
import com.lavreniuk.campassistant.dao.UserDao
import com.lavreniuk.campassistant.models.PersonInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonInfoRepo @Inject constructor(
    private val userDao: UserDao
) {

    fun getById(userId: String): LiveData<PersonInfo> {
        return userDao.load(userId)
    }


}