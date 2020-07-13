package com.lavreniuk.campassistant.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.R.id.user_info_list_param
import com.lavreniuk.campassistant.R.id.user_info_list_value
import com.lavreniuk.campassistant.user.Param
import com.lavreniuk.campassistant.user.ParameterType
import com.lavreniuk.campassistant.user.User

class UserParamListAdapter(
    private var params: ArrayList<Param> = arrayListOf(
        Param(
            ownerId = User.USER_ID,
            type = ParameterType.Text,
            name = "Date of birth",
            value = "22/03/1997"
        ),
        Param(
            ownerId = User.USER_ID,
            type = ParameterType.Text,
            name = "Mobile",
            value = "+380938213708"
        ),
        Param(
            ownerId = User.USER_ID,
            type = ParameterType.Text,
            name = "Instagram",
            value = "@taras.lavrenyuk"
        ),
        Param(
            ownerId = User.USER_ID,
            type = ParameterType.Text,
            name = "Twitter"
        )
    )
) : RecyclerView.Adapter<UserParamListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(params[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int = params.size

    fun updateParams(newParams: ArrayList<Param>) {
        params = newParams
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        params.removeAt(position)
        notifyItemRemoved(position)
    }

    class ViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(inflater.inflate(R.layout.user_info_list_item, parent, false)) {

        private val hint: TextInputLayout = itemView.findViewById(user_info_list_param)
        private val value: TextInputEditText = itemView.findViewById(user_info_list_value)

        fun bind(param: Param) {
            hint.hint = param.name
            param.value?.let { value.setText(it) }

            hint.setEndIconOnClickListener {
                println("${param.value} -> ${param.paramId}")
            }
        }
    }
}
