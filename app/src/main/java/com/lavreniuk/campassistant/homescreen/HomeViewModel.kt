package com.lavreniuk.campassistant.homescreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lavreniuk.campassistant.AppDatabase
import com.lavreniuk.campassistant.squad.Squad
import com.lavreniuk.campassistant.squad.SquadRepo
import com.lavreniuk.campassistant.squad.SquadWithPupils
import com.lavreniuk.campassistant.user.UserRepo

class HomeViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val userRepo: UserRepo =
        UserRepo(
            AppDatabase.getInstance(application).userDao()
        )

    private val squadRepo: SquadRepo =
        SquadRepo(
            AppDatabase.getInstance(application).squadDao()
        )

    val userPhoto: LiveData<String> = userRepo.userPhoto

    val userName: LiveData<String> = userRepo.userName

    val squadsWithPupils: LiveData<List<SquadWithPupils>> = squadRepo.squadsWithPupils

    fun createNewSquad(squadName: String): Squad {
        return Squad(squadName = squadName).also {
            squadRepo.save(it)
        }
    }
}
