package com.lavreniuk.campassistant.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lavreniuk.campassistant.models.crossrefs.SquadPupilCrossRef
import com.lavreniuk.campassistant.utils.GeneratorUtils
import com.lavreniuk.campassistant.utils.ioThread

@Entity(tableName = "pupils")
data class Pupil(
    @PrimaryKey val pupilId: String = GeneratorUtils.generateUUID(),
    var firstName: String,
    var lastName: String? = null,
    var photo: String? = null
) {
    fun getFullName(): String {
        return lastName?.let { "$firstName $it" } ?: firstName
    }
}

object PupilUtils {

    fun createNewPupil(
        squadId: String,
        savePupil: (Pupil) -> Unit,
        savePupilParamList: (List<PupilParam>) -> Unit,
        addPupilToSquad: (SquadPupilCrossRef) -> Unit

    ): String? {
        val newPupil = Pupil(
            firstName = "New pupil"
        ).also {
            ioThread {
                savePupil.invoke(it)
                savePupilParamList.invoke(PupilParam.createInitPupilParams(it.pupilId))
                addPupilToSquad.invoke(
                    SquadPupilCrossRef(
                        squadId = squadId,
                        pupilId = it.pupilId
                    )
                )
            }
        }
        return newPupil.pupilId
    }
}
