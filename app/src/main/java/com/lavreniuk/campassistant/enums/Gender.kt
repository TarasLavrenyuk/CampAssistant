package com.lavreniuk.campassistant.enums

import com.lavreniuk.campassistant.R

enum class Gender {

    Male {
        override fun getResId(): Int = R.string.ui_male_gender
    },
    Female {
        override fun getResId(): Int = R.string.ui_female_gender
    };

    abstract fun getResId(): Int
}
