package com.lavreniuk.campassistant.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lavreniuk.campassistant.enums.ParameterType
import com.lavreniuk.campassistant.enums.SocialType
import com.lavreniuk.campassistant.utils.GeneratorUtils

@Entity(tableName = "params")
class Param(
    @PrimaryKey
    val paramId: String = GeneratorUtils.generateUUID(),
    val ownerId: String,
    val type: ParameterType,
    val socialType: SocialType? = null,
    var name: String,
    var value: String? = null
)