package com.lavreniuk.campassistant.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.adapters.UserParamListAdapter
import com.lavreniuk.campassistant.models.Param
import com.lavreniuk.campassistant.utils.ImageLoaderUtils
import com.lavreniuk.campassistant.viewmodels.UserSettingsViewModel
import kotlinx.android.synthetic.main.activity_user_settings.*

class UserSettingsActivity : AppCompatActivity() {

    private val userSettingsViewModel: UserSettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)

        userSettingsViewModel.user.observe(this, Observer { user ->
            user_settings_user_name.text = user.getFullName()
        })

        userSettingsViewModel.userPhoto.observe(this, Observer { photoSrc ->
            ImageLoaderUtils.getBitmapFromPath(photoSrc)?.let {
                user_settings_user_avatar.setImageBitmap(it)
            }
        })

        val paramListAdapter = UserParamListAdapter()

        // setup param list
        user_settings_information_list.apply {
            layoutManager = LinearLayoutManager(this@UserSettingsActivity)
            adapter = paramListAdapter
        }

        userSettingsViewModel.params.observe(this, Observer<List<Param>> { params ->
            // TODO: observe user params
        })
    }
}
