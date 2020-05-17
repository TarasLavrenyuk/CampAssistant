package com.lavreniuk.campassistant.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.models.Pupil
import com.lavreniuk.campassistant.utils.ImageLoaderUtils

class ChildrenListAdapter(
    private var pupils: List<Pupil> = listOf()
) : RecyclerView.Adapter<ChildrenListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater,
            parent
        )
    }

    override fun getItemCount(): Int = pupils.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pupils[position])
    }

    fun updatePupilList(newPupilList: List<Pupil>) {
        pupils = newPupilList
        notifyDataSetChanged()
    }

    class ViewHolder(
        inflater: LayoutInflater,
        private val parent: ViewGroup
    ) : RecyclerView.ViewHolder(inflater.inflate(R.layout.children_list_item, parent, false)) {

        private val kidAvatarView: ImageView =
            itemView.findViewById(R.id.children_list_item_kid_avatar)
        private val kidNameLabel: TextView = itemView.findViewById(R.id.children_list_item_kid_name)
        private val kidRoomLabel: TextView = itemView.findViewById(R.id.children_list_item_kid_room)

        fun bind(pupil: Pupil) {
            ImageLoaderUtils.getBitmapFromPath(path = pupil.photo)?.let {
                kidAvatarView.setImageBitmap(it)
            }
            kidNameLabel.text = pupil.getFullName()
            pupil.room?.let {
                kidRoomLabel.text = it
            }
        }
    }
}
