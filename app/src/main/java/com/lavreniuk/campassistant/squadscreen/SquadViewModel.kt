package com.lavreniuk.campassistant.squadscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lavreniuk.campassistant.dao.AppDatabase
import com.lavreniuk.campassistant.models.PupilUtils
import com.lavreniuk.campassistant.models.Squad
import com.lavreniuk.campassistant.models.dto.PupilWithInfo
import com.lavreniuk.campassistant.repositories.PupilParamRepo
import com.lavreniuk.campassistant.repositories.PupilRepo
import com.lavreniuk.campassistant.repositories.SquadPupilCrossRefRepo
import com.lavreniuk.campassistant.repositories.SquadRepo
import com.lavreniuk.campassistant.utils.ioThread
import java.util.Date

class SquadViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val squadRepo: SquadRepo = SquadRepo(AppDatabase.getInstance(application).squadDao())
    private val pupilParamRepo: PupilParamRepo =
        PupilParamRepo(AppDatabase.getInstance(application).pupilParamDao())
    private val pupilRepo: PupilRepo = PupilRepo(
        AppDatabase.getInstance(application).pupilDao(),
        AppDatabase.getInstance(application).squadDao()
    )
    private val squadPupilCrossRefRepo: SquadPupilCrossRefRepo =
        SquadPupilCrossRefRepo(AppDatabase.getInstance(application).squadPupilCrossRefDao())

    fun getPupilList(squadId: String): LiveData<List<PupilWithInfo>> =
        pupilRepo.getSquadPupilsWithRooms(squadId)

    fun updateSquadName(squadId: String, newName: String) {
        ioThread {
            squadRepo.updateSquadName(squadId, newName)
        }
    }

    fun updateFromDate(squadId: String, newFromDate: Date? = null) {
        ioThread {
            squadRepo.updateFromDate(squadId, newFromDate)
        }
    }

    fun updateUntilDate(squadId: String, newUntilDate: Date?) {
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

    fun getSquadObject(squadId: String): Squad? = squadRepo.getSquadObject(squadId)

    fun deleteSquad(squadId: String) {
        ioThread {
            squadPupilCrossRefRepo.deletePupilFromSquad(squadId)
            squadRepo.deleteSquad(squadId)
        }
    }

    fun createNewKid(squadId: String): String? = PupilUtils.createNewPupil(
        squadId,
        { pupil -> pupilRepo.save(pupil) },
        { params -> pupilParamRepo.save(params) },
        { crossRefDao -> squadPupilCrossRefRepo.save(crossRefDao) })
}
