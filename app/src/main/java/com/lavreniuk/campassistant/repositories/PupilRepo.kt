package com.lavreniuk.campassistant.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.lavreniuk.campassistant.dao.PupilDao
import com.lavreniuk.campassistant.dao.SquadDao
import com.lavreniuk.campassistant.models.Pupil
import com.lavreniuk.campassistant.models.dto.PupilWithInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PupilRepo @Inject constructor(
    private val pupilDao: PupilDao,
    private val squadDao: SquadDao
) {

    /**
     * @return null if there is no active squad
     * @return empty list if active squad has no pupils
     * @return list of pupils from currently active squad with their rooms in the [PupilWithInfo.info] field
     */
    val pupilsOfCurrentSquadWithRooms = MediatorLiveData<List<PupilWithInfo>?>()

    init {
        pupilsOfCurrentSquadWithRooms.addSource(getPupilsOfCurrentSquadWithRooms()) { pupils: List<PupilWithInfo> ->
            if (pupils.isEmpty() && squadDao.getActiveSquadObject() == null) {
                pupilsOfCurrentSquadWithRooms.value = null
            } else {
                pupilsOfCurrentSquadWithRooms.value = pupils
            }
        }
    }

    fun getSquadPupilsWithRooms(squadId: String): LiveData<List<PupilWithInfo>> =
        pupilDao.getSquadPupilsWithRooms(squadId)

    fun getAllPupilsWithSquadsObjects(): List<PupilWithInfo> =
        pupilDao.getAllPupilsWithSquadsObjects()

    fun getPupil(pupilId: String): LiveData<Pupil> = pupilDao.getPupil(pupilId)

    fun getPupilAvatarObject(pupilId: String): String? = pupilDao.getPupilAvatarObject(pupilId)

    fun getPupilObject(pupilId: String): Pupil? = pupilDao.getPupilObject(pupilId)

    fun updateAvatar(pupilId: String, newPath: String? = null) =
        pupilDao.updateAvatar(pupilId, newPath)

    fun getPupilAvatar(pupilId: String): LiveData<String> = pupilDao.getPupilAvatar(pupilId)

    fun update(pupil: Pupil) = pupilDao.update(pupil)

    fun save(pupil: Pupil) = pupilDao.insert(pupil)

    /**
     * @return empty list if there is no active squad or there are no pupils in the squad.
     */
    private fun getPupilsOfCurrentSquadWithRooms(): LiveData<List<PupilWithInfo>> =
        pupilDao.getPupilsOfCurrentSquadWithRooms()
}
