package com.lavreniuk.campassistant.user

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.adapters.SwipeToDeleteCallback
import com.lavreniuk.campassistant.adapters.UserParamListAdapter
import com.lavreniuk.campassistant.dialogs.EnterNameContract
import com.lavreniuk.campassistant.dialogs.Name
import com.lavreniuk.campassistant.utils.ImageLoaderUtils
import com.lavreniuk.campassistant.utils.RequestCodes
import kotlinx.android.synthetic.main.activity_user_settings.*

class UserSettingsActivity : AppCompatActivity() {

    private val userSettingsViewModel: UserSettingsViewModel by viewModels()

    private val enterNameContractRegistration =
        registerForActivityResult(EnterNameContract()) { name ->
            name?.let {
                userSettingsViewModel.updateName(name.fName, name.lName)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)

        user_settings_user_avatar.setOnClickListener {
            ImageLoaderUtils.selectImageAction(
                activity = this,
                deletePicture = userSettingsViewModel.getUserPhoto()?.let {
                    {
                        userSettingsViewModel.deleteAvatar()
                    }
                }
            )
        }

        user_settings_user_name.setOnClickListener {
            enterNameContractRegistration.launch(
                userSettingsViewModel.getUser()?.let {
                    Name(it.firstName, it.lastName)
                }
            )
        }

        userSettingsViewModel.user.observe(this, Observer { user ->
            user_settings_user_name.text = user.getFullName()
        })

        userSettingsViewModel.userPhoto.observe(this, Observer { userPhotoPath ->
            ImageLoaderUtils.getBitmapFromPath(userPhotoPath)?.let {
                user_settings_user_avatar.setImageBitmap(it)
            } ?: run {
                user_settings_user_avatar.setImageDrawable(getDrawable(R.drawable.ic_default_avatar))
            }
        })

        val paramListAdapter = UserParamListAdapter()

        // setup param list
        user_settings_information_list.apply {
            layoutManager = LinearLayoutManager(this@UserSettingsActivity)
            adapter = paramListAdapter

            val swipeHandler = object : SwipeToDeleteCallback(context) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    paramListAdapter.removeAt(viewHolder.adapterPosition)
                }
            }

            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(this@apply)
        }

        userSettingsViewModel.params.observe(this, Observer { params ->
            // TODO: observe user params
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0) return

        when (requestCode) {
            RequestCodes.REQUEST_CODE_PHOTO_FROM_GALLERY -> {
                userSettingsViewModel.updateAvatar(
                    ImageLoaderUtils.saveImage(
                        ImageLoaderUtils.getBitmapFromGalleryUri(contentResolver, data!!.data)
                    )
                )
            }
            RequestCodes.REQUEST_CODE_PHOTO_FROM_CAMERA -> {
                userSettingsViewModel.updateAvatar(
                    ImageLoaderUtils.saveImage(
                        data?.extras?.get("data") as Bitmap
                    )
                )
            }
        }
    }
}
