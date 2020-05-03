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
    val user: LiveData<User> = userDao.getUser()

    val userName: LiveData<String> = userDao.getUserName()

    val userPhoto: LiveData<String> = userDao.getUserPhoto()

    fun save(user: User) = userDao.save(user)

    fun getUser(): User? = userDao.getUserObject()

    fun getUserPhoto(): String? = userDao.getUserPhotoObject()

    fun updateAvatar(path: String? = null) = userDao.updateAvatar(path = path)

    fun update(user: User) = userDao.update(user)
}