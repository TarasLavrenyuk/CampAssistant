package com.lavreniuk.campassistant.pageadapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.lavreniuk.campassistant.models.User
import com.lavreniuk.campassistant.utils.Logger

class UserActivityPagerAdapter(
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    companion object {
        const val NUMBER_OF_TABS = 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                PersonInfoFragment(userId, User::class.java)
            }
            1 -> {
                PersonContactsFragment(userId)
            }
            else -> {
                Logger.error(this.javaClass) { "Adapter ${UserActivityPagerAdapter::class.java.name}  should have only 2 tabs." }
                PersonInfoFragment(userId, User::class.java)
            }
        }
    }

    override fun getCount(): Int = NUMBER_OF_TABS
}