package com.lavreniuk.campassistant.onboarding

import android.content.Intent
import android.os.Bundle
import android.graphics.Color

import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.activities.UserSettingsActivity
import com.lavreniuk.campassistant.utils.Helpers

open class OnboardingActivity : TutorialActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addFragment(
            Step.Builder()
                .setTitle(getString(R.string.ui_plan_and_monitor))
                .setContent(getString(R.string.ui_plan_and_monitor_description))
                .setBackgroundColor(
                    Color.parseColor(
                        Helpers.getColorAsStringValue(
                            this,
                            R.color.backgroundColor
                        )
                    )
                )
                .setDrawable(R.drawable.blue_spot)
                .build()
        )
        addFragment(
            Step.Builder()
                .setTitle(getString(R.string.ui_stay_connected))
                .setContent(getString(R.string.ui_stay_connected_description))
                .setBackgroundColor(
                    Color.parseColor(
                        Helpers.getColorAsStringValue(
                            this,
                            R.color.backgroundColor
                        )
                    )
                )
                .setDrawable(R.drawable.red_spot)
                .build()
        )
        addFragment(
            Step.Builder()
                .setTitle(getString(R.string.ui_take_care))
                .setContent(getString(R.string.ui_take_care_description))
                .setBackgroundColor(
                    Color.parseColor(
                        Helpers.getColorAsStringValue(
                            this,
                            R.color.backgroundColor
                        )
                    )
                )
                .setDrawable(R.drawable.yellow_spot)
                .build()
        )
    }

    override fun finishTutorial() {
        finish()
        startActivity(Intent(this, UserSettingsActivity::class.java))
    }

    override fun currentFragmentPosition(position: Int) {
    }
}
