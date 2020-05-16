package com.lavreniuk.campassistant.squadscreen

import android.content.DialogInterface
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.adapters.ChildrenListAdapter
import com.lavreniuk.campassistant.listeners.CustomOnTouchListenerForEditTextView
import com.lavreniuk.campassistant.listeners.DateInputOnClickListener
import com.lavreniuk.campassistant.utils.ConverterUtils
import com.lavreniuk.campassistant.utils.Helpers
import kotlinx.android.synthetic.main.activity_squad.*

class SquadActivity : AppCompatActivity() {

    private val squadViewModel: SquadViewModel by viewModels()

    private lateinit var squadId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_squad)

        squadId = intent.getStringExtra(getString(R.string.intent_squad_id))!!

        setUpDateInputField(squad_activity_from_input)
        setUpDateInputField(squad_activity_until_input)

        init()

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

        squad_activity_from_input.doAfterTextChanged {
            with(it.toString()) {
                squadViewModel.updateFromDate(
                    squadId,
                    ConverterUtils.fromStringToDate(this)
                )
            }
        }

        squad_activity_until_input.doAfterTextChanged() {
            with(it.toString()) {
                squadViewModel.updateUntilDate(
                    squadId,
                    ConverterUtils.fromStringToDate(this)
                )
            }
        }

        squad_activity_is_squad_active_switcher.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            squad_activity_is_squad_active_text.text =
                if (isChecked) getString(R.string.ui_squad_is_active)
                else getString(R.string.ui_squad_is_inactive)
        }

        squad_activity_delete_squad_button.setOnClickListener {
            showAlertDialogButtonClicked()
        }
    }

    private fun setUpDateInputField(inputField: TextInputEditText) {
        inputField.setOnClickListener(
            DateInputOnClickListener(
                activity = this,
                initialDate = { Helpers.getDateFromInputField(inputField) }
            )
        )
        inputField.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                v.callOnClick()
            }
        }
        inputField.setOnTouchListener(CustomOnTouchListenerForEditTextView())
    }

    private fun init() {
        val squad = squadViewModel.getSquadObject(squadId) ?: return
        squad_activity_squad_name_input.setText(squad.squadName)
        squad.from?.let { squad_activity_from_input.setText(ConverterUtils.fromDateToString(it)) }
        squad.until?.let { squad_activity_until_input.setText(ConverterUtils.fromDateToString(it)) }

        if (squad.isCurrent) {
            squad_activity_is_squad_active_switcher.isChecked = true
            squad_activity_is_squad_active_text.text = getString(R.string.ui_squad_is_active)
        } else {
            squad_activity_is_squad_active_switcher.isChecked = false
            squad_activity_is_squad_active_text.text = getString(R.string.ui_squad_is_inactive)
        }
    }

    private fun showAlertDialogButtonClicked() {
        // setup the alert builder
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Submit action")
        builder.setMessage("Do you really want to delete the squad?")
        builder.setPositiveButton(
            "Yep"
        ) { dialog, _ ->
            squadViewModel.deleteSquad(squadId)
            dialog.dismiss()
            finish()
        }
        builder.setNegativeButton("Nope") { dialog, _ ->
            dialog.dismiss()
        }
        // create and show the alert dialog
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val updatedSquadName = squad_activity_squad_name_input.text.toString().trim()
        if (updatedSquadName.isBlank()) {
            Toast.makeText(
                this,
                getString(R.string.ui_squad_name_cannot_be_empty),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        squadViewModel.updateIsCurrentSquad(
            squadId,
            (squad_activity_is_squad_active_switcher as SwitchMaterial).isChecked
        )
        squadViewModel.updateSquadName(squadId, updatedSquadName)
        super.onBackPressed()
    }
}
