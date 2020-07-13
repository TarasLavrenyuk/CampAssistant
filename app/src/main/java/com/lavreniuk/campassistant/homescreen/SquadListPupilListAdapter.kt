package com.lavreniuk.campassistant.homescreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.pupil.Pupil
import com.lavreniuk.campassistant.utils.ImageLoaderUtils
import com.lavreniuk.campassistant.utils.Logger
import de.hdodenhof.circleimageview.CircleImageView

class SquadListPupilListAdapter(
    private val items: List<SquadListPupilListAdapterViewItem> = listOf()
) : RecyclerView.Adapter<SquadListPupilListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater,
            parent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        inflater: LayoutInflater,
        private val parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.squad_list_pupil_list_item,
            parent,
            false
        )
    ) {

        private val pupilAvatar: CircleImageView =
            itemView.findViewById(R.id.squad_list_pupil_list_item_pupil_avatar)

        private val textView: TextView =
            itemView.findViewById(R.id.squad_list_pupil_list_item_text_view)

        @SuppressLint("SetTextI18n")
        fun bind(item: SquadListPupilListAdapterViewItem) {
            item.pupil?.let { pupil ->
                ImageLoaderUtils.getBitmapFromPath(path = pupil.photo)?.let {
                    pupilAvatar.setImageBitmap(it)
                }
                return
            }
            if (item.pupilsLeft == null || item.squadId == null) {
                Logger.error(this.javaClass) { "Parameters 'pupilsLeft' and 'squadId' must not be null" }
                return
            }
            pupilAvatar.setImageDrawable(parent.context.getDrawable(R.drawable.yellow_spot))
            textView.text = "+${item.pupilsLeft}"
        }
    }
}

data class SquadListPupilListAdapterViewItem(
    val pupil: Pupil? = null,
    val pupilsLeft: Int? = null,
    val squadId: String? = null
)
