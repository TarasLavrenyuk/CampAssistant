package com.lavreniuk.campassistant.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.fragments.CalendarFragment
import com.lavreniuk.campassistant.kidsscreen.KidsFragment
import com.lavreniuk.campassistant.fragments.ReportsFragment
import com.lavreniuk.campassistant.homescreen.HomeFragment
import com.lavreniuk.campassistant.onboarding.OnboardingActivity
import com.lavreniuk.campassistant.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()

    private val homeFragment = HomeFragment()
    private val kidsFragment =
        KidsFragment()
    private val calendarFragment = CalendarFragment()
    private val reportsFragment = ReportsFragment()

    private var currentFragment: Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkIfOnboardingNeeded()

        reportsFragment.preloadFragment()
        calendarFragment.preloadFragment()
        kidsFragment.preloadFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.main_activity_fragment_container, homeFragment, "homeFragment").commit()

        title = resources.getString(R.string.ui_home)
        homeFragment.loadFragment()

        main_activity_navigation_toolbar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    title = resources.getString(R.string.ui_home)
                    homeFragment.loadFragment()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_squad -> {
                    title = resources.getString(R.string.ui_kids)
                    kidsFragment.loadFragment()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_calendar -> {
                    title = resources.getString(R.string.ui_calendar)
                    calendarFragment.loadFragment()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_reports -> {
                    title = resources.getString(R.string.ui_reports)
                    reportsFragment.loadFragment()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun checkIfOnboardingNeeded() {
        if (!userViewModel.isUserAlreadyExists()) {
            finish()
            startActivity(Intent(this, OnboardingActivity::class.java))
        }
    }

    private fun Fragment.loadFragment() {
        supportFragmentManager.beginTransaction().hide(currentFragment).show(this).commit()
        currentFragment = this
    }

    private fun Fragment.preloadFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_activity_fragment_container, this)
            .hide(this)
            .commit()
    }
}
