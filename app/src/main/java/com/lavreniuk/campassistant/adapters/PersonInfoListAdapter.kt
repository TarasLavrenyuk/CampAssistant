package com.lavreniuk.campassistant.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.enums.UserParameterType

class PersonInfoListAdapter(
    private val personInfo: PersonInfo
): RecyclerView.Adapter<PersonInfoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PersonInfoListAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.person_info_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int = personInfoArray.size

    override fun onBindViewHolder(holder: PersonInfoListAdapter.ViewHolder, position: Int) {
        when (position) {
            1 -> {
                holder.key.text = "First Name"
                holder.value.text = personInfo.fname
                holder.userParameterType = UserParameterType.Name
            }
            //TODO(end this shit)
        }
    }

    class ViewHolder(
        view: View,
        internal val key: TextView = view.findViewById(key_text_view_id),
        internal val value: TextView = view.findViewById(value_text_view_id)
    ) : RecyclerView.ViewHolder(view) {
        lateinit var userParameterType: UserParameterType
    }
}