package com.lavreniuk.campassistant.squadscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lavreniuk.campassistant.dao.AppDatabase
import com.lavreniuk.campassistant.models.Squad
import com.lavreniuk.campassistant.repositories.PupilRepo
import com.lavreniuk.campassistant.repositories.SquadRepo
import com.lavreniuk.campassistant.utils.ioThread
import java.util.*

class SquadViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val squadRepo: SquadRepo = SquadRepo(AppDatabase.getInstance(application).squadDao())

    private val pupilRepo: PupilRepo = PupilRepo(AppDatabase.getInstance(application).pupilDao())

    fun getSquad(squadId: String): LiveData<Squad> = squadRepo.getSquad(squadId)

    fun updateSquadName(squadId: String, newName: String) {
        ioThread {
            squadRepo.updateSquadName(squadId, newName)
        }
    }

    fun updateFromDate(squadId: String, newFromDate: Date) {
        ioThread {
            squadRepo.updateFromDate(squadId, newFromDate)
        }
    }

    fun updateUntilDate(squadId: String, newUntilDate: Date) {
        ioThread {
            squadRepo.updateUntilDate(squadId, newUntilDate)
        }
    }

    fun updateIsCurrentSquad(squadId: String, isCurrentSquad: Boolean) {
        ioThread {
            if (isCurrentSquad) {
                squadRepo.setAllSquadsInactive()
                squadRepo.updateSquadIsActive(squadId, true)
            } else {
                squadRepo.updateSquadIsActive(squadId, false)
            }
        }
    }
}