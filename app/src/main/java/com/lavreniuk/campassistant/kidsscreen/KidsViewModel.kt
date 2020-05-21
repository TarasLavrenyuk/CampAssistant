package com.lavreniuk.campassistant.kidsscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.lavreniuk.campassistant.dao.AppDatabase
import com.lavreniuk.campassistant.enums.PupilOrder
import com.lavreniuk.campassistant.models.Pupil
import com.lavreniuk.campassistant.repositories.PupilRepo
import com.lavreniuk.campassistant.repositories.SquadRepo

class KidsViewModel(application: Application) : AndroidViewModel(application) {

    private val pupilRepo: PupilRepo = PupilRepo(AppDatabase.getInstance(application).pupilDao())
    private val squadRepo: SquadRepo = SquadRepo(AppDatabase.getInstance(application).squadDao())

    val pupils = MediatorLiveData<List<Pupil>>()
    private var currentOrder: PupilOrder = PupilOrder.LastName

    init {
        pupils.addSource(getPupils()) { result: List<Pupil>? ->
            result?.let { pupils.value = sortPupils(it, currentOrder) }
        }
    }

    private fun getPupils(): LiveData<List<Pupil>> =
        squadRepo.getActiveSquad()?.let { currentSquad ->
            pupilRepo.getPupilList(currentSquad.squadId)
        } ?: pupilRepo.getAllPupils()

    fun rearrangePupils(newOrder: PupilOrder) = pupils.value?.let {
        pupils.value = sortPupils(it, newOrder)
    }.also { currentOrder = newOrder }

    private fun sortPupils(pupils: List<Pupil>, order: PupilOrder): List<Pupil> = when (order) {
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
}