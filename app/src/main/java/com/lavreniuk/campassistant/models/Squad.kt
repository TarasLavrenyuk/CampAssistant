package com.lavreniuk.campassistant.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lavreniuk.campassistant.utils.GeneratorUtils
import java.util.Date

@Entity(tableName = "squads")
data class Squad(
    @PrimaryKey val squadId: String = GeneratorUtils.generateUUID(),
    var squadName: String,
    var creationDate: Date = Date(),
    var from: Date? = null,
    var until: Date? = null,
    var isCurrent: Boolean = false,
    var photo: String? = null
)
