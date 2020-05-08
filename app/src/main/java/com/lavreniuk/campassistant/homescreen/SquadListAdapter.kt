package com.lavreniuk.campassistant.homescreen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.models.crossrefs.SquadWithPupils

class SquadListAdapter(
    private var squadsWithPupils: List<SquadWithPupils> = listOf(),
    private val context: Context
) : RecyclerView.Adapter<SquadListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater,
            parent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(squadsWithPupils[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int = squadsWithPupils.size

    fun updateSquads(newSquads: List<SquadWithPupils>) {
        squadsWithPupils = newSquads
        notifyDataSetChanged()
    }

    class ViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(inflater.inflate(R.layout.squad_list_item, parent, false)) {

        private val squadNameView: TextView = itemView.findViewById(R.id.squad_list_item_squad_name)
        private val numberOfPupilsView: TextView =
            itemView.findViewById(R.id.squad_list_item_pupils_number)
        private val pupilListRecyclerView: RecyclerView =
            itemView.findViewById(R.id.squad_list_item_pupil_list)

        fun bind(squadWithPupils: SquadWithPupils) {
            squadNameView.text = squadWithPupils.squad.squadName
            numberOfPupilsView.text = squadWithPupils.getNumberOfChildrenAsString()

            val squadListPupilListAdapter = SquadListPupilListAdapter(
                items = if (squadWithPupils.pupils.size >= 5) {
                    squadWithPupils.pupils.subList(0, 3).map {
                        SquadListPupilListAdapterViewItem(pupil = it)
                    } + SquadListPupilListAdapterViewItem(
                        pupilsLeft = squadWithPupils.pupils.size - 3,
                        squadId = squadWithPupils.squad.squadId
                    )
                } else {
                    squadWithPupils.pupils.map {
                        SquadListPupilListAdapterViewItem(pupil = it)
                    }
                }
            )

            // setup squad list
            pupilListRecyclerView.apply {
                layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
                    override fun canScrollHorizontally() = false
                    override fun canScrollVertically() = false
                }
                adapter = squadListPupilListAdapter
            }
        }
    }
}
