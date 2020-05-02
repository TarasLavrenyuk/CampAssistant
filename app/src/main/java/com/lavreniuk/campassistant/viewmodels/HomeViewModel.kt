package com.lavreniuk.campassistant.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lavreniuk.campassistant.dao.AppDatabase
import com.lavreniuk.campassistant.models.User
import com.lavreniuk.campassistant.repositories.UserRepo

class HomeViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val userRepo: UserRepo = UserRepo(AppDatabase.getAppDataBase(application).userDao())

    val userPhoto: LiveData<String> = userRepo.getUserPhoto()

    /**
     * @return [LiveData] object with user's first name
     */
    fun getUserName(): LiveData<String> = userRepo.userName
}