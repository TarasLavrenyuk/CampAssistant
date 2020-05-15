package com.lavreniuk.campassistant.listeners

import android.app.Activity
import android.app.DatePickerDialog
import android.view.View
import android.widget.TextView
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.utils.ConverterUtils
import com.lavreniuk.campassistant.utils.Helpers
import java.util.Calendar

class DateInputOnClickListener(
    private val activity: Activity
) : View.OnClickListener {

    override fun onClick(view: View?) {
        Helpers.hideKeyboard(activity)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            view!!.context,
            R.style.CalendarDialogTheme,
            DatePickerDialog.OnDateSetListener { _, pickedYear, monthOfYear, dayOfMonth ->

                val cal = Calendar.getInstance()
                cal.set(pickedYear, monthOfYear, dayOfMonth)
                val targetDate = cal.time

                (view as TextView).text = ConverterUtils.fromDateToString(targetDate)

            },
            year,
            month,
            day
        )
        dpd.show()
    }
}