package com.lavreniuk.campassistant.repositories

import androidx.lifecycle.LiveData
import com.lavreniuk.campassistant.dao.UserDao
import com.lavreniuk.campassistant.models.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepo @Inject constructor(
    private val userDao: UserDao
) {

    fun getUser(userId: String): LiveData<User> {
        return userDao.load(userId)
    }

}