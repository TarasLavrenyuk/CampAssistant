package com.lavreniuk.campassistant.pageadapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.lavreniuk.campassistant.fragments.ContactListFragment
import com.lavreniuk.campassistant.fragments.PersonInfoListFragment
import com.lavreniuk.campassistant.utils.Logger

class UserActivityPagerAdapter(
    fm: FragmentManager,
    private val personId: String
) : FragmentStatePagerAdapter(fm) {

    companion object {
        const val NUMBER_OF_TABS = 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                PersonInfoListFragment(personId)
            }
            1 -> {
                ContactListFragment(personId)
            }
            else -> {
                Logger.error(this.javaClass) { "Adapter ${UserActivityPagerAdapter::class.java.name}  should have only 2 tabs." }
                PersonInfoListFragment(personId)
            }
        }
    }

    override fun getCount(): Int = NUMBER_OF_TABS
}