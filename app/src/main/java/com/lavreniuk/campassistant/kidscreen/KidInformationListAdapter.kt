package com.lavreniuk.campassistant.kidscreen

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.enums.PupilParamType
import com.lavreniuk.campassistant.models.PupilParam
import com.lavreniuk.campassistant.utils.Helpers

class KidInformationListAdapter(
    private var pupilParams: ArrayList<PupilParam> = arrayListOf(),
    private val activity: Activity
) : RecyclerView.Adapter<KidInformationListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = pupilParams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(pupilParams[position], activity)
    }

    fun updateParams(newParams: ArrayList<PupilParam>) {
        pupilParams = newParams
        notifyDataSetChanged()
    }

    fun getItemOnPosition(position: Int): PupilParam = pupilParams[position]

    fun getPupilParamList(): ArrayList<PupilParam> = pupilParams

    class ViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        inflater.inflate(R.layout.kid_information_list_item, parent, false)
    ) {
        private val kidParam: TextInputLayout =
            itemView.findViewById(R.id.kid_information_list_param)
        private val kidParamValue: TextInputEditText =
            itemView.findViewById(R.id.kid_information_list_value)

        var canBeRemoved: Boolean = false

        fun bind(
            pupilParam: PupilParam,
            activity: Activity
        ) {
            // set param value if exists
            pupilParam.paramValue?.let { kidParamValue.setText(it) }

            // update param value after user input
            kidParamValue.doAfterTextChanged {
                pupilParam.paramValue =
                    if (kidParamValue.text.toString().isBlank()) null
                    else kidParamValue.text.toString().trim()
            }

            when (pupilParam.paramType) {
                PupilParamType.Room -> {
                    kidParam.hint = "Room"
                }
                PupilParamType.PrimaryNumber -> {
                    kidParam.hint = "Phone"
                    // TODO: add call number func
                }
                PupilParamType.BirthDay -> {
                    kidParam.hint = "Birthday"
                    Helpers.setUpDateInputField(kidParamValue, activity)
                }
                PupilParamType.Number -> {
                    kidParam.hint = pupilParam.paramName
                    canBeRemoved = true
                }
                PupilParamType.Social -> {
                    kidParam.hint = "Social"
                    canBeRemoved = true
                }
            }
        }
    }
}
