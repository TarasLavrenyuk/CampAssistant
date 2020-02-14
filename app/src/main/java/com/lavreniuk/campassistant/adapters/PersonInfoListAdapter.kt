package com.lavreniuk.campassistant.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.enums.UserParameterType
import com.lavreniuk.campassistant.models.Person
import com.lavreniuk.campassistant.utils.ConverterUtils
import com.lavreniuk.campassistant.utils.Logger
import kotlinx.android.synthetic.main.person_info_list_item.view.*

class PersonInfoListAdapter(
    private val getString: (resId: Int) -> String
) : RecyclerView.Adapter<PersonInfoListAdapter.ViewHolder>() {

    private lateinit var person: Person


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

    override fun getItemCount(): Int = TODO()

    override fun onBindViewHolder(holder: PersonInfoListAdapter.ViewHolder, position: Int) {
        when (position) {
            1 -> {
                holder.fill(
                    infoParam = getString(R.string.ui_first_name),
                    infoValue = person.firstName,
                    userParameterType = UserParameterType.Name
                )
            }
            2 -> {
                holder.fill(
                    infoParam = getString(R.string.ui_last_name),
                    infoValue = person.lastName ?: "No information",
                    userParameterType = UserParameterType.Name
                )
            }
            3 -> {
                holder.fill(
                    infoParam = getString(R.string.ui_date_of_birth),
                    infoValue = person.db?.let { ConverterUtils.fromDateToString(it) }
                        ?: getString(R.string.ui_no_info),
                    userParameterType = UserParameterType.Date
                )
            }
            4 -> {
                holder.fill(
                    infoParam = getString(R.string.ui_gender),
                    infoValue = person.gender?.getResId()?.let { getString(it) }
                        ?: getString(R.string.ui_no_info),
                    userParameterType = UserParameterType.Gender
                )
            }
        }
    }

    fun updatePersonInfo(person: Person?) {
        person?.let {
            this@PersonInfoListAdapter.person = it
            notifyDataSetChanged()
        }
        Logger.error(this.javaClass) { "person must not be null." }
    }


    class ViewHolder(
        view: View,
        val key: TextView = view.person_info_param,
        val value: TextView = view.person_info_value
    ) : RecyclerView.ViewHolder(view) {
        lateinit var userParameterType: UserParameterType

        fun fill(infoParam: String, infoValue: String, userParameterType: UserParameterType) {
            key.text = infoParam
            value.text = infoValue
            this.userParameterType = userParameterType
        }
    }
}