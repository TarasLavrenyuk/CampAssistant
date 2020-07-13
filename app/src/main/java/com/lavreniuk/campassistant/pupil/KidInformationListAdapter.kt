package com.lavreniuk.campassistant.pupil

import android.app.Activity
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.utils.Helpers

class KidInformationListAdapter(
    private var pupilParams: ArrayList<PupilParam> = arrayListOf(),
    private val activity: Activity
) : RecyclerView.Adapter<KidInformationListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater,
            parent
        )
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
        private val parent: ViewGroup
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
            // set param name
            kidParam.hint = parent.context.getString(pupilParam.paramType.resourceId)
            canBeRemoved = pupilParam.paramType.canBeRemoved

            // set param value if exists
            pupilParam.paramValue?.let { kidParamValue.setText(it) }

            // update param value after user input
            kidParamValue.doAfterTextChanged {
                pupilParam.paramValue =
                    if (kidParamValue.text.toString().isBlank()) null
                    else kidParamValue.text.toString().trim()
            }

            when (pupilParam.paramType) {
                PupilParamType.PrimaryNumber -> {
                    // TODO: add call number func
                }
                PupilParamType.BirthDay -> {
                    Helpers.setUpDateInputField(kidParamValue, activity)
                }
                PupilParamType.Contact -> {
                    kidParam.hint = pupilParam.paramName
                    // TODO: determine if number and add call number func
                }

                PupilParamType.Note -> {
                    kidParamValue.gravity = Gravity.START
                    kidParamValue.setSingleLine(false)
                    kidParamValue.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
                }
            }
        }
    }
}
