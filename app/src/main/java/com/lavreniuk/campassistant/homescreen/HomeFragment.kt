package com.lavreniuk.campassistant.homescreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.activities.UserSettingsActivity
import com.lavreniuk.campassistant.utils.ImageLoaderUtils
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

        homeViewModel.userName.observe(viewLifecycleOwner, Observer { userFirstName ->
            userFirstName?.let {
                fragment_home_user_greetings.text = "${getString(R.string.ui_hi)} $userFirstName"
            }
        })

        homeViewModel.userPhoto.observe(viewLifecycleOwner, Observer { photoSrc ->
            ImageLoaderUtils.getBitmapFromPath(photoSrc)?.let {
                fragment_home_user_avatar.setImageBitmap(it)
                return@Observer
            }
            fragment_home_user_avatar.setImageDrawable(requireContext().getDrawable(R.drawable.ic_default_avatar))
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment_home_user_avatar.setOnClickListener {
            startActivity(Intent(context, UserSettingsActivity::class.java))
        }

        // setup squad list
        val squadListAdapter = SquadListAdapter(context = requireContext())
        fragment_home_squad_list.apply {
            layoutManager = LinearLayoutManager(
                this@HomeFragment.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = squadListAdapter
        }
        homeViewModel.squadsWithPupils.observe(viewLifecycleOwner, Observer { squadsWithPupils ->
            if (squadsWithPupils.isNullOrEmpty()) {
                squadListAdapter.updateSquads(listOf())
                fragment_home_poster.visibility = View.VISIBLE
                fragment_home_squad_list_layout.visibility = View.GONE
            } else {
                squadListAdapter.updateSquads(squadsWithPupils)
                fragment_home_poster.visibility = View.GONE
                fragment_home_squad_list_layout.visibility = View.VISIBLE
            }
        })

        fragment_home_new_squad_button.setOnClickListener {
        }
    }
}
