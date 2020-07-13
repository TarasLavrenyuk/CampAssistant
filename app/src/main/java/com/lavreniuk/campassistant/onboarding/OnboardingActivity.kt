package com.lavreniuk.campassistant.onboarding

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.pupil.RegistrationActivity
import com.lavreniuk.campassistant.utils.Helpers

open class OnboardingActivity : TutorialActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addFragment(
            PermissionStep.Builder().setTitle(getString(R.string.ui_permissions_required))
                .setContent(getString(R.string.ui_permissions_required_description))
                .setBackgroundColor(
                    Color.parseColor(
                        Helpers.getColorAsStringValue(this, R.color.backgroundColor)
                    )
                )
                .setDrawable(R.drawable.blue_spot)
                .setPermissions(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE
                    )
                )
                .build()
        )
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
                .setDrawable(R.drawable.plan_and_monitor)
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
                .setDrawable(R.drawable.stay_connected)
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
                .setDrawable(R.drawable.take_care)
                .build()
        )
    }

    override fun finishTutorial() {
        finish()
        startActivity(Intent(this, RegistrationActivity::class.java))
    }

    override fun currentFragmentPosition(position: Int) {
    }
}
