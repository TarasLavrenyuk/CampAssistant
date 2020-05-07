package com.lavreniuk.campassistant.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.lavreniuk.campassistant.dao.AppDatabase
import com.lavreniuk.campassistant.models.User
import com.lavreniuk.campassistant.repositories.UserRepo

class UserViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val userRepo: UserRepo = UserRepo(AppDatabase.getAppDataBase(application).userDao())

    fun isUserAlreadyExists(): Boolean {
        return userRepo.getUser()?.let {
            true
        } ?: false
    }

    fun createUser(fName: String, lName: String?) = userRepo.save(User(firstName = fName, lastName = lName))
}
