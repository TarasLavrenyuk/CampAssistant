package com.lavreniuk.campassistant.kidsscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lavreniuk.campassistant.R
import kotlinx.android.synthetic.main.fragment_kids.*


class KidsFragment : Fragment() {

    private val viewModel: KidsViewModel by viewModels()

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

        // setup squad list
        val squadListAdapter = ChildrenListAlphabeticalAdapter()
        kids_fragment_kids_list.apply {
            layoutManager = LinearLayoutManager(this@KidsFragment.context)
            adapter = squadListAdapter
        }
        viewModel.pupils.observe(viewLifecycleOwner, Observer { pupils ->
            if (pupils.isNullOrEmpty()) {
                squadListAdapter.updatePupilList(listOf())
                kids_fragment_poster.visibility = View.VISIBLE
                kids_fragment_search_view_layout.visibility = View.GONE
                kids_fragment_kids_list.visibility = View.GONE
            } else {
                squadListAdapter.updatePupilList(pupils)
                kids_fragment_poster.visibility = View.GONE
                kids_fragment_search_view_layout.visibility = View.VISIBLE
                kids_fragment_kids_list.visibility = View.VISIBLE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar, menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.kids_fragment_filter_icon) {
            viewModel.changeOrder()
            true
        } else super.onOptionsItemSelected(item)
    }
}
