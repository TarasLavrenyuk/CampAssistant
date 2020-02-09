package com.lavreniuk.campassistant.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.viewmodels.AvatarViewModel


class AvatarFragment : Fragment() {

    companion object {
        fun newInstance() =
            AvatarFragment()
    }

    private lateinit var viewModel: AvatarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.avatar_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AvatarViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
