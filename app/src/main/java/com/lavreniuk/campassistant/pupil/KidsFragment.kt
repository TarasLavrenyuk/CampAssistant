package com.lavreniuk.campassistant.pupil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lavreniuk.campassistant.MainActivity
import com.lavreniuk.campassistant.R
import kotlinx.android.synthetic.main.fragment_kids.*

class KidsFragment : Fragment() {

    private val viewModel: KidsViewModel by viewModels()

    private val sortPupilsContractRegistration =
        registerForActivityResult(SortPupilsContract()) { order ->
            order?.let { viewModel.rearrangePupils(order) }
                ?: viewModel.rearrangePupils(PupilOrder.LastName)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_kids, container, false)
        setHasOptionsMenu(true)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPupilList()
        setUpAddKidButton()
    }

    private fun setUpPupilList() {
        val squadPupilsListAdapter =
            ChildrenListAlphabeticalAdapter()
        kids_fragment_kids_list.apply {
            layoutManager = LinearLayoutManager(this@KidsFragment.context)
            adapter = squadPupilsListAdapter
        }
        viewModel.pupilList.observe(viewLifecycleOwner, Observer { pupils ->
            // add sort button to the appbar if there are more than one pupils in the list
            setHasOptionsMenu(pupils.size >= 2)

            if (pupils.isEmpty()) {
                kids_fragment_search_view_layout.visibility = View.GONE
                kids_fragment_kids_list.visibility = View.GONE
                kids_fragment_poster.visibility = View.VISIBLE

                kids_fragment_poster.text =
                    viewModel.currentSquad.value?.let {
                        getString(
                            R.string.ui_seems_like_there_is_no_kids_yet_in_the_squad,
                            it.squadName
                        )
                    } ?: getString(
                        R.string.ui_seems_like_there_is_no_kids_yet
                    )
            } else {
                kids_fragment_poster.visibility = View.GONE
                kids_fragment_search_view_layout.visibility = View.VISIBLE
                kids_fragment_kids_list.visibility = View.VISIBLE
                squadPupilsListAdapter.updatePupilList(pupils)
            }
        })
    }

    private fun setUpAddKidButton() {
        viewModel.currentSquad.observe(viewLifecycleOwner, Observer { activeSquad ->
            if (activeSquad == null) {
                kids_fragment_add_kid_button.visibility = LinearLayout.GONE
                setAppbarTitle(getString(R.string.ui_all_kids))
            } else {
                kids_fragment_add_kid_button.visibility = LinearLayout.VISIBLE
                kids_fragment_add_kid_button.setOnClickListener {
                    viewModel.createNewKid(activeSquad.squadId)?.let { newPupilId ->
                        startActivity(
                            Intent(this@KidsFragment.context, KidActivity::class.java).also {
                                it.putExtra(getString(R.string.intent_kid_id), newPupilId)
                            }
                        )
                    }
                }
                setAppbarTitle(activeSquad.squadName)
            }
        })
    }

    private fun setAppbarTitle(title: String) =
        run { (activity as? MainActivity)?.supportActionBar?.title = title }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.kids_fragment_filter_icon) {
            sortPupilsContractRegistration.launch(
                SortPupilsDialogDto(
                    isSquadPupils = viewModel.currentSquad.value != null,
                    order = viewModel.squadPupilsOrder
                )
            )
            true
        } else super.onOptionsItemSelected(item)
    }
}
