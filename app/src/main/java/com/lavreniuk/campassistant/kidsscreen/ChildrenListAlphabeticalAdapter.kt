package com.lavreniuk.campassistant.kidsscreen

import android.widget.SectionIndexer
import com.lavreniuk.campassistant.adapters.ChildrenListAdapter
import com.lavreniuk.campassistant.models.dto.PupilWithRoom
import com.lavreniuk.campassistant.utils.getFirstLetterUpperCase

class ChildrenListAlphabeticalAdapter(
    pupils: List<PupilWithRoom> = listOf()
) : ChildrenListAdapter(pupils), SectionIndexer {

    private lateinit var mSectionPositions: ArrayList<Int>

    override fun getSections(): Array<String> {
        val sections = mutableListOf<String>()
        mSectionPositions = arrayListOf()
        pupils.forEachIndexed { index: Int, pupil: PupilWithRoom ->
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