package com.lavreniuk.campassistant.customviews

import android.content.Context
import android.util.AttributeSet
import com.lavreniuk.campassistant.R

class YellowSpot @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(
    context,
    attrs,
    defStyleAttr
) {

    init {
        setImageResource(R.drawable.yellow_spot)
    }
}