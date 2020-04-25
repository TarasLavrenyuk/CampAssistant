package com.lavreniuk.campassistant.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.adapters.UserParamListAdapter
import com.lavreniuk.campassistant.models.Param
import com.lavreniuk.campassistant.models.User
import com.lavreniuk.campassistant.viewmodels.UserSettingsViewModel
import kotlinx.android.synthetic.main.activity_user_settings.*

class UserSettingsActivity : AppCompatActivity() {

    private val userSettingsViewModel: UserSettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)

        userSettingsViewModel.user.observe(this, Observer<User> { user ->
            // TODO: observe user object
        })

        val paramListAdapter = UserParamListAdapter(context = this)
        user_settings_information_list.adapter = paramListAdapter

        userSettingsViewModel.params.observe(this, Observer<List<Param>> { params ->
            // TODO: observe user params
        })
    }
}
