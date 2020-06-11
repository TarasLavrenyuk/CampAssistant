package com.lavreniuk.campassistant.enums

import com.lavreniuk.campassistant.R

enum class PupilParamType(val resourceId: Int, val canBeRemoved: Boolean) {
    // General params
    Contact(resourceId = R.string.ui_pupil_param_number, canBeRemoved = true),
    PrimaryNumber(resourceId = R.string.ui_pupil_param_number, canBeRemoved = false),
    BirthDay(resourceId = R.string.ui_pupil_param_birthday, canBeRemoved = false),
    Room(resourceId = R.string.ui_pupil_param_room, canBeRemoved = false),

    // Health params
    Height(resourceId = R.string.ui_pupil_param_height, canBeRemoved = false),
    Weight(resourceId = R.string.ui_pupil_param_weight, canBeRemoved = false),
    Blood(resourceId = R.string.ui_pupil_param_blood, canBeRemoved = false),
    Note(resourceId = R.string.ui_pupil_param_note, canBeRemoved = false);

    companion object {
        fun getGeneralParams(): Set<String> = setOf(
            Room.toString(),
            PrimaryNumber.toString(),
            BirthDay.toString(),
            Contact.toString()
        )

        fun getHealthParams(): Set<String> = setOf(
            Height.toString(),
            Weight.toString(),
            Blood.toString(),
            Note.toString()
        )
    }
}
