package com.lavreniuk.campassistant.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.lavreniuk.campassistant.AppDatabase

class UserViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val userRepo: UserRepo =
        UserRepo(
            AppDatabase.getInstance(application).userDao()
        )

    fun isUserAlreadyExists(): Boolean {
        return userRepo.getUser()?.let {
            true
        } ?: false
    }

    fun createUser(fName: String, lName: String?) = userRepo.save(
        User(
            firstName = fName,
            lastName = lName
        )
    )
}
