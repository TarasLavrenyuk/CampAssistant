package com.lavreniuk.campassistant.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.fragments.CalendarFragment
import com.lavreniuk.campassistant.fragments.HomeFragment
import com.lavreniuk.campassistant.fragments.ReportsFragment
import com.lavreniuk.campassistant.fragments.SquadFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = resources.getString(R.string.ui_home)
        loadFragment(HomeFragment())

        main_activity_navigation_toolbar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    title = resources.getString(R.string.ui_home)
                    loadFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_squad -> {
                    title = resources.getString(R.string.ui_squad)
                    loadFragment(SquadFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_calendar -> {
                    title = resources.getString(R.string.ui_calendar)
                    loadFragment(CalendarFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_reports -> {
                    title = resources.getString(R.string.ui_reports)
                    loadFragment(ReportsFragment())
                    return@setOnNavigationItemSelectedListener true
                }

            }
            false
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_activity_fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
