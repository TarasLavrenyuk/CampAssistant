package com.lavreniuk.campassistant.dialogs

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContract
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.dialogs.CreateContactDialog.Companion.NAME_INTENT_PARAM
import com.lavreniuk.campassistant.dialogs.CreateContactDialog.Companion.VALUE_INTENT_PARAM
import com.lavreniuk.campassistant.utils.ShowKeyboard
import kotlinx.android.synthetic.main.dialog_create_contact.*

class CreateContactDialog : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_create_contact)

        create_contact_dialog_contact_name_input.postDelayed(
            ShowKeyboard(create_contact_dialog_contact_name_input, this), HALF_SECOND_DELAY
        )

        val inputMethodManager: InputMethodManager =
            this.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

        create_contact_dialog_create_label.setOnClickListener {
            val contactValue = create_contact_dialog_contact_value_input.text
            contactValue?.let {
                if (it.isBlank()) {
                    return@setOnClickListener
                }
            } ?: return@setOnClickListener

            val contactName =
                create_contact_dialog_contact_name_input.text?.toString()?.trim()?.let {
                    return@let if (it.isBlank()) null else it
                }

            intent?.let {
                it.putExtra(VALUE_INTENT_PARAM, contactValue.toString().trim())
                it.putExtra(NAME_INTENT_PARAM, contactName)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    companion object {
        const val NAME_INTENT_PARAM = "name"
        const val VALUE_INTENT_PARAM = "value"

        const val HALF_SECOND_DELAY: Long = 500
    }
}

data class Contact(
    val name: String? = null,
    val value: String
)

class CreateContactContract : ActivityResultContract<Unit, Contact>() {

    override fun createIntent(context: Context, input: Unit?): Intent =
        Intent(context, CreateContactDialog::class.java)

    override fun parseResult(resultCode: Int, intent: Intent?): Contact? {
        if (resultCode != Activity.RESULT_OK || intent == null) return null
        return Contact(
            name = intent.getStringExtra(NAME_INTENT_PARAM),
            value = intent.getStringExtra(VALUE_INTENT_PARAM)
        )
    }
}
