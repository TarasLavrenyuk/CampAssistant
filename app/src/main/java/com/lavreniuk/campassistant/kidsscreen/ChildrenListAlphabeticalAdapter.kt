package com.lavreniuk.campassistant.kidsscreen

import android.view.ViewGroup
import android.widget.SectionIndexer
import androidx.recyclerview.widget.RecyclerView
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.adapters.ChildrenListAdapter
import com.lavreniuk.campassistant.models.Pupil
import com.lavreniuk.campassistant.utils.getFirstLetterUpperCase
import fastscroll.app.fastscrollalphabetindex.AlphabetIndexFastScrollRecyclerView

class ChildrenListAlphabeticalAdapter(
    pupils: List<Pupil> = listOf()
) : ChildrenListAdapter(pupils), SectionIndexer {

    private lateinit var mSectionPositions: ArrayList<Int>

    override fun getSections(): Array<String> {
        val sections = mutableListOf<String>()
        mSectionPositions = arrayListOf()
        pupils.forEachIndexed { index: Int, pupil: Pupil ->
            pupil.lastName.getFirstLetterUpperCase()?.let { section ->
                if (!sections.contains(section)) {
                    sections.add(section)
                    mSectionPositions.add(index)
                }
            }
        }
        return sections.toTypedArray()
    }

    override fun getSectionForPosition(position: Int): Int = mSectionPositions[position]

    override fun getPositionForSection(sectionIndex: Int): Int = 0
}