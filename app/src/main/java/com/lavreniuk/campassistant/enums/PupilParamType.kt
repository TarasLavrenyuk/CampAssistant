package com.lavreniuk.campassistant.enums

import com.lavreniuk.campassistant.R

enum class PupilParamType(val resourceId: Int, val canBeRemoved: Boolean) {
    // General params
    Number(resourceId = R.string.ui_pupil_param_number, canBeRemoved = true),
    PrimaryNumber(resourceId = R.string.ui_pupil_param_number, canBeRemoved = false),
    BirthDay(resourceId = R.string.ui_pupil_param_birthday, canBeRemoved = false),
    Room(resourceId = R.string.ui_pupil_param_room, canBeRemoved = false),
    Social(resourceId = R.string.ui_pupil_param_social, canBeRemoved = true),

    // Health params
    Height(resourceId = R.string.ui_pupil_param_height, canBeRemoved = false),
    Weight(resourceId = R.string.ui_pupil_param_weight, canBeRemoved = false),
    Blood(resourceId = R.string.ui_pupil_param_blood, canBeRemoved = true),
    Note(resourceId = R.string.ui_pupil_param_note, canBeRemoved = false);

    companion object {
        fun getGeneralParams(): Set<String> = setOf(
            Number.toString(),
            PrimaryNumber.toString(),
            BirthDay.toString(),
            Room.toString(),
            Social.toString()
        )

        fun getHealthParams(): Set<String> = setOf(
            Height.toString(),
            Weight.toString(),
            Blood.toString(),
            Note.toString()
        )
    }
}
