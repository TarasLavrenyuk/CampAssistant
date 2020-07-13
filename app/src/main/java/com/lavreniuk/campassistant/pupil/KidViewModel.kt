package com.lavreniuk.campassistant.pupil

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.lavreniuk.campassistant.AppDatabase
import com.lavreniuk.campassistant.utils.ImageLoaderUtils
import com.lavreniuk.campassistant.utils.ioThread
import java.util.ArrayList

class KidViewModel(application: Application) : AndroidViewModel(application) {

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

    fun getKid(kidId: String): LiveData<Pupil> = pupilRepo.getPupil(kidId)

    fun getKidObject(kidId: String): Pupil? = pupilRepo.getPupilObject(kidId)

    fun getKidPhoto(kidId: String): LiveData<String> = pupilRepo.getPupilAvatar(kidId)

    fun getKidAvatarObject(kidId: String): String? = pupilRepo.getPupilAvatarObject(kidId)

    fun deleteAvatar(kidId: String) = updateAvatar(kidId, null)

    fun updateAvatar(kidId: String, newPath: String?) {
        ioThread {
            pupilRepo.getPupilObject(kidId)?.photo?.let { ImageLoaderUtils.deleteImage(it) }
            pupilRepo.updateAvatar(kidId, newPath)
        }
    }

    fun updateName(kidId: String, fName: String, lName: String?) =
        ioThread {
            pupilRepo.getPupilObject(kidId)?.let {
                it.firstName = fName
                it.lastName = lName
                pupilRepo.update(it)
            }
        }

    fun getPupilGeneralParams(kidId: String): LiveData<List<PupilParam>> =
        pupilParamRepo.getPupilGeneralParams(kidId)

    fun getPupilHealthParams(kidId: String): LiveData<List<PupilParam>> =
        pupilParamRepo.getPupilHealthParams(kidId)

    fun deletePupilParamById(pupilParamId: String) = ioThread {
        pupilParamRepo.deleteById(pupilParamId)
    }

    fun updatePupilParams(params: ArrayList<PupilParam>) = ioThread {
        params.forEach(pupilParamRepo::updateParam)
    }

    fun createPupilParam(param: PupilParam) = ioThread {
        pupilParamRepo.createParam(param)
    }
}
