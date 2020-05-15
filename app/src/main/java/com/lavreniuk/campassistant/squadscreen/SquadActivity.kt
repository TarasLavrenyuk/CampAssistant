package com.lavreniuk.campassistant.squadscreen

import android.os.Bundle
import android.widget.Switch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.adapters.ChildrenListAdapter
import com.lavreniuk.campassistant.listeners.CustomOnTouchListenerForEditTextView
import com.lavreniuk.campassistant.listeners.DateInputOnClickListener
import com.lavreniuk.campassistant.utils.ConverterUtils
import kotlinx.android.synthetic.main.activity_squad.squad_activity_from_input
import kotlinx.android.synthetic.main.activity_squad.squad_activity_until_input
import kotlinx.android.synthetic.main.activity_squad.squad_activity_children_list
import kotlinx.android.synthetic.main.activity_squad.squad_activity_squad_name_input
import kotlinx.android.synthetic.main.activity_squad.squad_activity_is_squad_active_switcher
import kotlinx.android.synthetic.main.activity_squad.squad_activity_is_squad_active_text

class SquadActivity : AppCompatActivity() {

    private val squadViewModel: SquadViewModel by viewModels()

    private lateinit var squadId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_squad)

        squadId = intent.getStringExtra(getString(R.string.intent_squad_id))!!

        squad_activity_from_input.setOnClickListener(DateInputOnClickListener(this))
        squad_activity_from_input.setOnTouchListener(CustomOnTouchListenerForEditTextView())

        squad_activity_until_input.setOnClickListener(DateInputOnClickListener(this))
        squad_activity_until_input.setOnTouchListener(CustomOnTouchListenerForEditTextView())

        // setup squad list
        val childrenListAdapter = ChildrenListAdapter()
        squad_activity_children_list.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = childrenListAdapter
        }

        squad_activity_squad_name_input.doAfterTextChanged {
            with(it.toString()) {
                if (isNotBlank()) {
                    squadViewModel.updateSquadName(squadId, this)
                }
            }
        }

        squad_activity_from_input.doAfterTextChanged {
            with(it.toString()) {
                if (isNotBlank() && ConverterUtils.isCorrectDateString(this)) {
                    squadViewModel.updateFromDate(squadId, ConverterUtils.fromStringToDate(this))
                }
            }
        }

        squad_activity_until_input.doAfterTextChanged() {
            with(it.toString()) {
                if (isNotBlank() && ConverterUtils.isCorrectDateString(this)) {
                    squadViewModel.updateUntilDate(squadId, ConverterUtils.fromStringToDate(this))
                }
            }
        }

        squad_activity_is_squad_active_switcher.setOnClickListener {
            if ((it as Switch).isChecked) {
                squad_activity_is_squad_active_text.text = getString(R.string.ui_squad_is_active)
            } else {
                squad_activity_is_squad_active_text.text = getString(R.string.ui_squad_is_inactive)
            }
        }

        squadViewModel.getSquad(squadId).observe(this, Observer { squad ->
            squad_activity_squad_name_input.setText(squad.squadName)
            squad.from?.let { squad_activity_from_input.setText(ConverterUtils.fromDateToString(it)) }
            squad.until?.let { squad_activity_from_input.setText(ConverterUtils.fromDateToString(it)) }

            if (squad.isCurrent) {
                squad_activity_is_squad_active_switcher.isChecked = true
                squad_activity_is_squad_active_text.text = getString(R.string.ui_squad_is_active)
            } else {
                squad_activity_is_squad_active_switcher.isChecked = false
                squad_activity_is_squad_active_text.text = getString(R.string.ui_squad_is_inactive)
            }
        })
    }

    override fun onStop() {
        super.onStop()
        squadViewModel.updateIsCurrentSquad(
            squadId,
            (squad_activity_is_squad_active_switcher as Switch).isChecked
        )
    }
}
