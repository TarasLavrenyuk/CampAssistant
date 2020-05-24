package com.lavreniuk.campassistant.repositories

import androidx.lifecycle.LiveData
import com.lavreniuk.campassistant.dao.PupilDao
import com.lavreniuk.campassistant.models.Pupil
import com.lavreniuk.campassistant.models.dto.PupilWithRoom
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PupilRepo @Inject constructor(
    private val pupilDao: PupilDao
) {

    fun getSquadPupilsWithRooms(squadId: String): LiveData<List<PupilWithRoom>> = pupilDao.getSquadPupilsWithRooms(squadId)

    fun getAllPupilsWithRooms(): LiveData<List<PupilWithRoom>> = pupilDao.getAllPupilsWithRooms()

    fun getPupil(pupilId: String): LiveData<Pupil> = pupilDao.getPupil(pupilId)

    fun getPupilAvatarObject(pupilId: String): String? = pupilDao.getPupilAvatarObject(pupilId)

    fun getPupilObject(pupilId: String): Pupil? = pupilDao.getPupilObject(pupilId)

    fun updateAvatar(pupilId: String, newPath: String? = null) =
        pupilDao.updateAvatar(pupilId, newPath)

    fun getPupilAvatar(pupilId: String): LiveData<String> = pupilDao.getPupilAvatar(pupilId)

    fun update(pupil: Pupil) {
        pupilDao.update(pupil)
    }
}