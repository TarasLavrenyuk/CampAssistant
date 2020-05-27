package com.lavreniuk.campassistant.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.lavreniuk.campassistant.listeners.CustomOnTouchListenerForEditTextView
import com.lavreniuk.campassistant.listeners.DateInputOnClickListener
import java.util.*


object Helpers {

    fun getColorAsStringValue(context: Context, resourceCode: Int): String {
        return "#${Integer.toHexString(ContextCompat.getColor(context, resourceCode))}"
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getDateFromInputField(inputField: EditText): Date? {
        return ConverterUtils.fromStringToDate(inputField.text.toString())
    }

    fun setUpDateInputField(inputField: TextInputEditText, activity: Activity) {
        inputField.setOnClickListener(
            DateInputOnClickListener(
                activity = activity,
                initialDate = { getDateFromInputField(inputField) }
            )
        )
        inputField.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                v.callOnClick()
            }
        }
        inputField.setOnTouchListener(CustomOnTouchListenerForEditTextView())
    }

    fun callNumber(number: String, activity: Activity) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$number")
        activity.startActivity(callIntent)
    }
}

fun String?.getFirstLetterUpperCase() =
    if (this.isNullOrBlank()) null
    else get(0).toUpperCase().toString()

class ShowKeyboard(
    private val input: EditText,
    private val activity: Activity
) : Runnable {
    override fun run() {
        input.isFocusableInTouchMode = true
        input.requestFocusFromTouch()
        input.requestFocus()
        activity.window
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
            input,
            0
        )
    }
}
