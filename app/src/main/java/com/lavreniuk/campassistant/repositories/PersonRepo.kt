package com.lavreniuk.campassistant.repositories

import androidx.lifecycle.LiveData
import com.lavreniuk.campassistant.dao.PersonDao
import com.lavreniuk.campassistant.models.Person
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonRepo @Inject constructor(
    private val userDao: PersonDao
) {

    fun getById(userId: String): LiveData<Person> {
        return userDao.load(userId)
    }

}