package com.lavreniuk.campassistant.pupil

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.pupil.SortPupilsDialog.Companion.PUPIL_LIST_TYPE_INTENT_PARAM_NAME
import com.lavreniuk.campassistant.pupil.SortPupilsDialog.Companion.SORT_PARAM_INTENT_PARAM_NAME
import kotlinx.android.synthetic.main.dialog_sort_pupils.*

class SortPupilsDialog : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_sort_pupils)

        when (PupilOrder.valueOf(intent.getStringExtra(SORT_PARAM_INTENT_PARAM_NAME))) {
            PupilOrder.LastName -> dialog_srt_pupils_radio_button_sort_by_lname.isChecked = true
            PupilOrder.Info -> dialog_srt_pupils_radio_button_sort_by_info.isChecked = true
        }

        if (intent.getBooleanExtra(PUPIL_LIST_TYPE_INTENT_PARAM_NAME, false)) {
            dialog_srt_pupils_radio_button_sort_by_info.text = getString(R.string.ui_room)
        } else {
            dialog_srt_pupils_radio_button_sort_by_info.text = getString(R.string.ui_squad)
        }

        dialog_srt_pupils_radio_button_sort_by_lname.setOnClickListener {
            intent?.putExtra(SORT_PARAM_INTENT_PARAM_NAME, PupilOrder.LastName.name)
            setResult(RESULT_OK, intent)
            finish()
        }

        dialog_srt_pupils_radio_button_sort_by_info.setOnClickListener {
            intent?.putExtra(SORT_PARAM_INTENT_PARAM_NAME, PupilOrder.Info.name)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    companion object {
        const val SORT_PARAM_INTENT_PARAM_NAME = "sort-param"
        const val PUPIL_LIST_TYPE_INTENT_PARAM_NAME = "pupil-list-type"
    }
}

data class SortPupilsDialogDto(
    // true if only pupils of particular squad are displayed, or false if all pupils are displayed
    val isSquadPupils: Boolean,
    val order: PupilOrder
)

class SortPupilsContract : ActivityResultContract<SortPupilsDialogDto, PupilOrder>() {

    override fun createIntent(context: Context, dto: SortPupilsDialogDto): Intent {
        return Intent(context, SortPupilsDialog::class.java).apply {
            putExtra(SORT_PARAM_INTENT_PARAM_NAME, dto.order.name)
            putExtra(PUPIL_LIST_TYPE_INTENT_PARAM_NAME, dto.isSquadPupils)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): PupilOrder? {
        if (resultCode != Activity.RESULT_OK || intent == null) return null
        return PupilOrder.valueOf(intent.getStringExtra(SORT_PARAM_INTENT_PARAM_NAME))
    }
}
