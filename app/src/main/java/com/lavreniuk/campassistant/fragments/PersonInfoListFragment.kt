package com.lavreniuk.campassistant.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.lavreniuk.campassistant.viewmodels.PersonInfoListViewModel
import com.lavreniuk.campassistant.R
import kotlinx.android.synthetic.main.person_info_list_fragment.*


class PersonInfoListFragment(
    private val personId: String
) : Fragment() {

    private lateinit var viewModel: PersonInfoListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.person_info_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        user_info_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }


        viewModel = ViewModelProvider(this).get(PersonInfoListViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
