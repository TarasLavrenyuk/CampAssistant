package com.lavreniuk.campassistant.squad

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.lavreniuk.campassistant.pupil.Pupil

/**
 * @see [Pupil] and [Squad]
 */
@Entity(primaryKeys = ["squadId", "pupilId"])
data class SquadPupilCrossRef(
    val squadId: String,
    val pupilId: String
)

data class SquadWithPupils(
    @Embedded val squad: Squad,
    @Relation(
        parentColumn = "squadId",
        entityColumn = "pupilId",
        associateBy = Junction(SquadPupilCrossRef::class)
    )
    val pupils: List<Pupil>
) {
    fun getNumberOfChildrenAsString() =
        when (pupils.size) {
            1 -> "1 child"
            else -> "${pupils.size} children"
        }
}
