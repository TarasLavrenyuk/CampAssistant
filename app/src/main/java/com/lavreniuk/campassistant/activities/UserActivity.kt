package com.lavreniuk.campassistant.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.markersinterfaces.ActivityWithTabs
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity(), ActivityWithTabs {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
    }

    override fun setUpTabs() {
        user_activity_tabs_layout.addTab(user_activity_tabs_layout.newTab().setText(getString(R.string.ui_personal_info)))
        user_activity_tabs_layout.addTab(user_activity_tabs_layout.newTab().setText(getString(R.string.ui_contact_details)))
        user_activity_tabs_layout.tabGravity = TabLayout.GRAVITY_FILL
        val pagerAdapter = UserActivityPagerAdapter(
            supportFragmentManager
        )
        user_activity_pager.adapter = pagerAdapter
        user_activity_pager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(user_activity_tabs_layout)
        )

        user_activity_tabs_layout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                user_activity_pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}
