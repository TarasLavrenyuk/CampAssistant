package com.lavreniuk.campassistant.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lavreniuk.campassistant.enums.PupilParamType
import com.lavreniuk.campassistant.utils.GeneratorUtils
import java.util.Date

@Entity(tableName = "pupil_params")
class PupilParam(
    @PrimaryKey
    val pupilParamId: String = GeneratorUtils.generateUUID(),
    var pupilId: String,
    var paramName: String? = null,
    var paramValue: String?,
    var paramType: PupilParamType,
    val createdAt: Date = Date()
)