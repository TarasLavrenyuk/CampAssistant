package com.lavreniuk.campassistant.homescreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lavreniuk.campassistant.dao.AppDatabase
import com.lavreniuk.campassistant.models.crossrefs.SquadWithPupils
import com.lavreniuk.campassistant.repositories.SquadRepo
import com.lavreniuk.campassistant.repositories.UserRepo

class HomeViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val userRepo: UserRepo = UserRepo(AppDatabase.getInstance(application).userDao())

    private val squadRepo: SquadRepo = SquadRepo(AppDatabase.getInstance(application).squadDao())

    val userPhoto: LiveData<String> = userRepo.userPhoto

    val userName: LiveData<String> = userRepo.userName

    val squadsWithPupils: LiveData<List<SquadWithPupils>> = squadRepo.squadsWithPupils
}
