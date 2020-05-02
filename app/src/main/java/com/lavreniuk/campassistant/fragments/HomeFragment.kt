package com.lavreniuk.campassistant.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val userViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        userViewModel.getUserName().observe(viewLifecycleOwner, Observer { userFirstName ->
            userFirstName?.let {
                fragment_home_user_greetings.text = "${getString(R.string.ui_hi)} $userFirstName"
            }
        })

        return view
    }
}