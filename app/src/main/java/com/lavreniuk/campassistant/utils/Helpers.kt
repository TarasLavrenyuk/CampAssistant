package com.lavreniuk.campassistant.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
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
}

