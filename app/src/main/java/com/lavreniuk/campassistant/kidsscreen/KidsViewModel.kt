package com.lavreniuk.campassistant.kidsscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.lavreniuk.campassistant.dao.AppDatabase
import com.lavreniuk.campassistant.enums.PupilOrder
import com.lavreniuk.campassistant.models.Pupil
import com.lavreniuk.campassistant.models.PupilParam
import com.lavreniuk.campassistant.models.Squad
import com.lavreniuk.campassistant.models.crossrefs.SquadPupilCrossRef
import com.lavreniuk.campassistant.models.dto.PupilWithRoom
import com.lavreniuk.campassistant.repositories.PupilParamRepo
import com.lavreniuk.campassistant.repositories.PupilRepo
import com.lavreniuk.campassistant.repositories.SquadPupilCrossRefRepo
import com.lavreniuk.campassistant.repositories.SquadRepo
import com.lavreniuk.campassistant.utils.ioThread

class KidsViewModel(application: Application) : AndroidViewModel(application) {

    private val pupilRepo: PupilRepo = PupilRepo(AppDatabase.getInstance(application).pupilDao())
    private val pupilParamRepo: PupilParamRepo =
        PupilParamRepo(AppDatabase.getInstance(application).pupilParamDao())
    private val squadRepo: SquadRepo = SquadRepo(AppDatabase.getInstance(application).squadDao())
    private val squadPupilCrossRefDao: SquadPupilCrossRefRepo =
        SquadPupilCrossRefRepo(AppDatabase.getInstance(application).squadPupilCrossRefDao())

    val currentSquad: LiveData<Squad> = squadRepo.activeSquad

    val pupils = MediatorLiveData<List<PupilWithRoom>>()
    private var currentOrder: PupilOrder = PupilOrder.LastName

    init {
        pupils.addSource(getPupils()) { result: List<PupilWithRoom>? ->
            result?.let { pupils.value = sortPupils(it, currentOrder) }
        }
    }

    private fun getPupils(): LiveData<List<PupilWithRoom>> =
        squadRepo.getActiveSquadObject()?.let { currentSquad ->
            pupilRepo.getSquadPupilsWithRooms(currentSquad.squadId)
        } ?: pupilRepo.getAllPupilsWithRooms()

    fun rearrangePupils(newOrder: PupilOrder) = pupils.value?.let {
        pupils.value = sortPupils(it, newOrder)
    }.also { currentOrder = newOrder }

    private fun sortPupils(pupils: List<PupilWithRoom>, order: PupilOrder): List<PupilWithRoom> =
        when (order) {
            PupilOrder.LastName -> {
                pupils.sortedWith(compareBy { it.lastName })
            }
            PupilOrder.Room -> {
                pupils.sortedWith(compareBy { it.room })
            }
        }

    fun changeOrder() {
        if (currentOrder == PupilOrder.LastName) {
            rearrangePupils(PupilOrder.Room)
            return
        }
        if (currentOrder == PupilOrder.Room) {
            rearrangePupils(PupilOrder.LastName)
            return
        }
    }

    fun createNewKid(): String? {
        val squad = squadRepo.getActiveSquadObject() ?: return null

        val newPupil = Pupil(
            firstName = "New pupil"
        ).also {
            ioThread {
                pupilRepo.save(it)
                pupilParamRepo.save(PupilParam.createInitPupilParams(it.pupilId))
                squadPupilCrossRefDao.save(
                    SquadPupilCrossRef(
                        squadId = squad.squadId,
                        pupilId = it.pupilId
                    )
                )
            }
        }
        return newPupil.pupilId
    }
}
