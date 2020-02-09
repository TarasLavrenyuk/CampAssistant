package com.lavreniuk.campassistant.utils

import android.util.Log

object Logger {

    fun error(clazz: Class<Any>, message: () -> (String)) {
        Log.e(getTag(clazz), message.invoke())
    }

    private fun getTag(clazz: Class<Any>) = clazz.name

}