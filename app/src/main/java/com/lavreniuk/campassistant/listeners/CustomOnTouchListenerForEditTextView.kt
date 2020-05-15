package com.lavreniuk.campassistant.listeners

import android.view.MotionEvent
import android.view.View

/*
* This listener ensure, that onClickListener for EditText will be triggered immediately
*/
class CustomOnTouchListenerForEditTextView : View.OnTouchListener {
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_UP) {
            v!!.performClick()
            return true
        }
        return false
    }
}