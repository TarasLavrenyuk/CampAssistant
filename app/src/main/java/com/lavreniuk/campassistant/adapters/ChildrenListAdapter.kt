package com.lavreniuk.campassistant.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.kidscreen.KidActivity
import com.lavreniuk.campassistant.models.dto.PupilWithInfo
import com.lavreniuk.campassistant.utils.ImageLoaderUtils

open class ChildrenListAdapter(
    protected var pupils: List<PupilWithInfo> = listOf()
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

    fun updatePupilList(newPupilList: List<PupilWithInfo>) {
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
        private val layout: ViewGroup = itemView.findViewById(R.id.children_list_item_layout)

        fun bind(pupil: PupilWithInfo) {
            ImageLoaderUtils.getBitmapFromPath(path = pupil.photo)?.let {
                kidAvatarView.setImageBitmap(it)
            } ?: kotlin.run {
                kidAvatarView.setImageDrawable(parent.context.getDrawable(R.drawable.ic_default_avatar))
            }

            kidNameLabel.text = pupil.getFullName()

            kidRoomLabel.text = pupil.info ?: ""

            layout.setOnClickListener {
                with(parent.context) {
                    startActivity(
                        Intent(this, KidActivity::class.java).also {
                            it.putExtra(getString(R.string.intent_kid_id), pupil.pupilId)
                        }
                    )
                }
            }
        }
    }
}
