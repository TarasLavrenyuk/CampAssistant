package com.lavreniuk.campassistant.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.models.PersonInfo
import com.lavreniuk.campassistant.viewmodels.ContactListViewModel


class ContactListFragment(
    private val personId: String
) : Fragment() {

    private lateinit var viewModel: ContactListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contact_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContactListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
