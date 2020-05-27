package com.lavreniuk.campassistant.kidscreen

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lavreniuk.campassistant.R
import com.lavreniuk.campassistant.adapters.SwipeToDeleteCallback
import com.lavreniuk.campassistant.dialogs.EnterNameContract
import com.lavreniuk.campassistant.dialogs.Name
import com.lavreniuk.campassistant.models.Pupil
import com.lavreniuk.campassistant.models.PupilParam
import com.lavreniuk.campassistant.utils.ImageLoaderUtils
import com.lavreniuk.campassistant.utils.RequestCodes
import kotlinx.android.synthetic.main.activity_kid.*

class KidActivity : AppCompatActivity() {

    private val kidViewModel: KidViewModel by viewModels()

    private val enterNameContractRegistration =
        registerForActivityResult(EnterNameContract()) { name ->
            name?.let {
                kidViewModel.updateName(kidId, name.fName, name.lName)
            }
        }

    private lateinit var kidId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kid)

        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }

        kidId = intent.getStringExtra(getString(R.string.intent_squad_id))!!

        kid_activity_kid_avatar.setOnClickListener {
            ImageLoaderUtils.selectImageAction(
                activity = this,
                deletePicture = kidViewModel.getKidAvatarObject(kidId)?.let {
                    {
                        kidViewModel.deleteAvatar(kidId)
                    }
                }
            )
        }

        kidViewModel.getKidPhoto(kidId).observe(this, Observer { pupilPhoto ->
            ImageLoaderUtils.getBitmapFromPath(pupilPhoto)?.let {
                kid_activity_kid_avatar.setImageBitmap(it)
            } ?: run {
                kid_activity_kid_avatar.setImageDrawable(getDrawable(R.drawable.ic_default_avatar))
            }
        })

        kidViewModel.getKidPhoto(kidId).observe(this, Observer { kidAvatar ->
            ImageLoaderUtils.getBitmapFromPath(kidAvatar)?.let {
                kid_activity_kid_avatar.setImageBitmap(it)
            } ?: run {
                kid_activity_kid_avatar.setImageDrawable(getDrawable(R.drawable.ic_default_avatar))
            }
        })

        kid_activity_kid_name.setOnClickListener {
            enterNameContractRegistration.launch(
                kidViewModel.getKidObject(kidId)?.let {
                    Name(it.firstName, it.lastName)
                }
            )
        }

        kidViewModel.getKid(kidId).observe(this, Observer { kid: Pupil ->
            kid_activity_kid_name.text = kid.getFullName()
        })

        setUpGeneralInformationList(
            kid_activity_information_list,
            kidViewModel.getPupilGeneralParams(kidId)
        )
        setUpGeneralInformationList(
            kid_activity_health_list,
            kidViewModel.getPupilHealthParams(kidId)
        )
    }

    private fun setUpGeneralInformationList(
        list: RecyclerView,
        params: LiveData<List<PupilParam>>
    ) {
        val kidInformationListAdapter = KidInformationListAdapter(activity = this)
        list.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = kidInformationListAdapter

            val swipeHandler = object : SwipeToDeleteCallback(context) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    updatePupilGeneralParams()
                    kidViewModel.deletePupilParamById(
                        kidInformationListAdapter.getItemOnPosition(
                            viewHolder.adapterPosition
                        ).pupilParamId
                    )
                }

                override fun getMovementFlags(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ): Int =
                    if (viewHolder is KidInformationListAdapter.ViewHolder && viewHolder.canBeRemoved)
                        super.getMovementFlags(recyclerView, viewHolder)
                    else 0
            }

            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(this@apply)
        }
        params.observe(this, Observer { params: List<PupilParam> ->
            kidInformationListAdapter.updateParams(ArrayList(params))
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        updatePupilGeneralParams()
        updatePupilHealthParams()
        super.onBackPressed()
    }

    private fun updatePupilGeneralParams() {
        kidViewModel.updatePupilParams((kid_activity_information_list.adapter as KidInformationListAdapter).getPupilParamList())
    }

    private fun updatePupilHealthParams() {
        kidViewModel.updatePupilParams((kid_activity_health_list.adapter as KidInformationListAdapter).getPupilParamList())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0) return

        when (requestCode) {
            RequestCodes.REQUEST_CODE_PHOTO_FROM_GALLERY -> {
                kidViewModel.updateAvatar(
                    kidId,
                    ImageLoaderUtils.saveImage(
                        ImageLoaderUtils.getBitmapFromGalleryUri(contentResolver, data!!.data)
                    )
                )
            }
            RequestCodes.REQUEST_CODE_PHOTO_FROM_CAMERA -> {
                kidViewModel.updateAvatar(
                    kidId,
                    ImageLoaderUtils.saveImage(
                        data?.extras?.get("data") as Bitmap
                    )
                )
            }
        }
    }
}
