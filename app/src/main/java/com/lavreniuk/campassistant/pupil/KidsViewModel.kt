package com.lavreniuk.campassistant.pupil

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.lavreniuk.campassistant.AppDatabase
import com.lavreniuk.campassistant.squad.Squad
import com.lavreniuk.campassistant.squad.SquadPupilCrossRefRepo
import com.lavreniuk.campassistant.squad.SquadRepo

class KidsViewModel(application: Application) : AndroidViewModel(application) {

    private val pupilRepo: PupilRepo =
        PupilRepo(
            AppDatabase.getInstance(application).pupilDao(),
            AppDatabase.getInstance(application).squadDao()
        )
    private val pupilParamRepo: PupilParamRepo =
        PupilParamRepo(
            AppDatabase.getInstance(
                application
            ).pupilParamDao()
        )
    private val squadRepo: SquadRepo =
        SquadRepo(
            AppDatabase.getInstance(application).squadDao()
        )
    private val squadPupilCrossRefRepo: SquadPupilCrossRefRepo =
        SquadPupilCrossRefRepo(
            AppDatabase.getInstance(
                application
            ).squadPupilCrossRefDao()
        )

    val currentSquad: LiveData<Squad> = squadRepo.activeSquad

    val pupilList = MediatorLiveData<List<PupilWithInfo>>()
    var squadPupilsOrder: PupilOrder = PupilOrder.LastName

    init {
        pupilList.addSource(pupilRepo.pupilsOfCurrentSquadWithRooms) { pupils: List<PupilWithInfo>? ->
            if (pupils == null) {
                pupilList.value =
                    sortSquadPupils(pupilRepo.getAllPupilsWithSquadsObjects(), squadPupilsOrder)
            } else {
                pupilList.value = sortSquadPupils(pupils, squadPupilsOrder)
            }
        }
    }

    fun rearrangePupils(newOrder: PupilOrder) = pupilList.value?.let {
        pupilList.value = sortSquadPupils(it, newOrder)
    }.also { squadPupilsOrder = newOrder }

    /**
     * Method sorts squad pupils list.
     */
    private fun sortSquadPupils(
        pupils: List<PupilWithInfo>,
        order: PupilOrder
    ): List<PupilWithInfo> =
        when (order) {
            PupilOrder.LastName -> {
                pupils.sortedWith(compareBy { it.lastName })
            }
            PupilOrder.Info -> {
                pupils.sortedWith(compareBy { it.info })
            }
        }

    fun createNewKid(squadId: String): String? = PupilUtils.createNewPupil(
        squadId,
        { pupil -> pupilRepo.save(pupil) },
        { params -> pupilParamRepo.save(params) },
        { crossRefDao -> squadPupilCrossRefRepo.save(crossRefDao) })
}
