package com.lavreniuk.campassistant.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.enums.ParameterType
import com.lavreniuk.campassistant.models.Param
import com.lavreniuk.campassistant.models.User
import kotlinx.android.synthetic.main.user_info_list_item.view.*

class UserParamListAdapter(
    private var params: Array<Param> = arrayOf(
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
    ),
    private val context: Context
) : BaseAdapter() {

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int = params.size

    override fun getItem(position: Int): Any {
        return params[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return ViewHolder(context, params[position])
    }

    fun updateParams(newParams: Array<Param>) {
        params = newParams
        notifyDataSetChanged()
    }

    class ViewHolder(
        context: Context
    ) : LinearLayout(context) {

        constructor(context: Context, param: Param) : this(context) {
            View.inflate(context, R.layout.user_info_list_item, this)
            user_info_list_param.hint = param.name
            param.value?.let { user_info_list_value.setText(it) }

        }
    }
}