package com.lavreniuk.campassistant.utils

import android.content.Context
import androidx.core.content.ContextCompat

object Helpers {

    fun getColorAsStringValue(context: Context, resourceCode: Int): String {
        return "#${Integer.toHexString(ContextCompat.getColor(context, resourceCode))}"
    }
}
