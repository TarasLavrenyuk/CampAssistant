package com.lavreniuk.campassistant.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.activities.UserSettingsActivity
import com.lavreniuk.campassistant.utils.ImageLoaderUtils
import com.lavreniuk.campassistant.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel.getUserName().observe(viewLifecycleOwner, Observer { userFirstName ->
            userFirstName?.let {
                fragment_home_user_greetings.text = "${getString(R.string.ui_hi)} $userFirstName"
            }
        })

        homeViewModel.userPhoto.observe(viewLifecycleOwner, Observer { photoSrc ->
            ImageLoaderUtils.getBitmapFromPath(photoSrc)?.let {
                fragment_home_user_avatar.setImageBitmap(it)
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment_home_user_avatar.setOnClickListener {
            startActivity(Intent(context, UserSettingsActivity::class.java))
        }
    }
}
