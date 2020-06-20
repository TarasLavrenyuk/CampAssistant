package com.lavreniuk.campassistant.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lavreniuk.campassistant.enums.PupilParamType
import com.lavreniuk.campassistant.utils.GeneratorUtils
import java.util.Date

@Entity(tableName = "pupil_params")
data class PupilParam(
    @PrimaryKey
    val pupilParamId: String = GeneratorUtils.generateUUID(),
    var pupilId: String,
    var paramName: String? = null,
    var paramValue: String? = null,
    var paramType: PupilParamType,
    val createdAt: Date = Date()
) {
    companion object {
        fun createInitPupilParams(pupilId: String): List<PupilParam> = listOf(
            // regular params
            PupilParam(
                pupilId = pupilId,
                paramType = PupilParamType.PrimaryNumber
            ),
            PupilParam(
                pupilId = pupilId,
                paramType = PupilParamType.BirthDay
            ),
            PupilParam(
                pupilId = pupilId,
                paramType = PupilParamType.Room
            ),

            // health params
            PupilParam(
                pupilId = pupilId,
                paramType = PupilParamType.Height
            ),
            PupilParam(
                pupilId = pupilId,
                paramType = PupilParamType.Weight
            ),
            PupilParam(
                pupilId = pupilId,
                paramType = PupilParamType.Blood
            ),
            PupilParam(
                pupilId = pupilId,
                paramType = PupilParamType.Note
            )
        )
    }
}
