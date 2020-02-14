package com.lavreniuk.campassistant.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lavreniuk.campassistant.viewmodels.PersonInfoViewModel
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.adapters.PersonInfoListAdapter
import com.lavreniuk.campassistant.models.Person
import kotlinx.android.synthetic.main.person_info_list_fragment.*


class PersonInfoListFragment(
    private val personId: String
) : Fragment() {

    private lateinit var personInfoViewModel: PersonInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.person_info_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        personInfoViewModel = ViewModelProvider(this).get(PersonInfoViewModel::class.java).also {
            it.personId = personId
        }
        val personInfoListAdapter = PersonInfoListAdapter { resId -> getString(resId) }.also {
            it.updatePersonInfo(personInfoViewModel.user.value)
        }
        user_info_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@PersonInfoListFragment.context)
            adapter = personInfoListAdapter
        }

        personInfoViewModel.user.observe(viewLifecycleOwner, Observer<Person> { personInfo ->
            personInfoListAdapter.updatePersonInfo(personInfo)
        })
    }
}
