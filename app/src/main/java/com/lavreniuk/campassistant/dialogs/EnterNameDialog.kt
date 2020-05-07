package com.lavreniuk.campassistant.dialogs

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.dialogs.EnterNameDialog.Companion.F_NAME_INTENT_PARAM
import com.lavreniuk.campassistant.dialogs.EnterNameDialog.Companion.L_NAME_INTENT_PARAM
import kotlinx.android.synthetic.main.dialog_enter_name.*

class EnterNameDialog : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_enter_name)
        intent?.let {
            enter_name_dialog_fname_input.setText(it.getStringExtra(F_NAME_INTENT_PARAM))
            enter_name_dialog_lname_input.setText(it.getStringExtra(L_NAME_INTENT_PARAM))
        }

        enter_name_dialog_start_label.setOnClickListener {
            val fName = enter_name_dialog_fname_input.text
            fName?.let {
                if (it.isBlank()) {
                    return@setOnClickListener
                }
            } ?: return@setOnClickListener

            val lName = enter_name_dialog_lname_input.text?.toString()?.trim()?.let {
                return@let if (it.isBlank()) null else it
            }

            intent?.let {
                it.putExtra(F_NAME_INTENT_PARAM, fName.toString().trim())
                it.putExtra(L_NAME_INTENT_PARAM, lName)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    companion object {
        const val F_NAME_INTENT_PARAM = "fName"
        const val L_NAME_INTENT_PARAM = "lName"
    }
}

data class Name(
    val fName: String,
    val lName: String? = null
)

class EnterNameContract : ActivityResultContract<Name, Name>() {

    override fun createIntent(context: Context, name: Name): Intent {
        return Intent(context, EnterNameDialog::class.java).apply {
            putExtra(F_NAME_INTENT_PARAM, name.fName)
            putExtra(L_NAME_INTENT_PARAM, name.lName)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Name? {
        if (resultCode != Activity.RESULT_OK || intent == null) return null
        return Name(
            fName = intent.getStringExtra(F_NAME_INTENT_PARAM),
            lName = intent.getStringExtra(L_NAME_INTENT_PARAM)
        )
    }
}
