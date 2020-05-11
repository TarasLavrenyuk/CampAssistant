package com.lavreniuk.campassistant.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.Fragment
import com.lavreniuk.campassistant.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Calendar

object ImageLoaderUtils {

    private val APP_DIRECTORY = "${Environment.getExternalStorageDirectory()}/CampAssistant/"

    @JvmOverloads
    fun selectImageAction(
        activity: Activity,
        deletePicture: (() -> Unit)? = null
    ) {
        val actions = mutableListOf(
            activity.getString(R.string.ui_take_photo),
            activity.getString(R.string.ui_select_from_gallery)
        )
        if (deletePicture != null) {
            actions += activity.getString(R.string.ui_remove_photo)
        }

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(activity.getString(R.string.ui_choose_action))

        builder.setItems(actions.toTypedArray()) { dialog: DialogInterface, index: Int ->
            when (actions[index]) {
                activity.getString(R.string.ui_take_photo) -> {
                    activity.startActivityForResult(
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                        RequestCodes.REQUEST_CODE_PHOTO_FROM_CAMERA
                    )
                }
                activity.getString(R.string.ui_select_from_gallery) -> {
                    activity.startActivityForResult(
                        Intent(Intent.ACTION_PICK).also { it.type = "image/*" },
                        RequestCodes.REQUEST_CODE_PHOTO_FROM_GALLERY
                    )
                }
                activity.getString(R.string.ui_remove_photo) -> {
                    deletePicture!!()
                }
                activity.getString(R.string.ui_cancel) -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    fun choosePhotoFromGallery(activity: Activity) {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        activity.startActivityForResult(galleryIntent, RequestCodes.REQUEST_CODE_PHOTO_FROM_GALLERY)
    }

    fun choosePhotoFromGallery(fragment: Fragment) {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        fragment.startActivityForResult(galleryIntent, RequestCodes.REQUEST_CODE_PHOTO_FROM_GALLERY)
    }

    fun takePhotoFromCamera(activity: Activity) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activity.startActivityForResult(intent, RequestCodes.REQUEST_CODE_PHOTO_FROM_CAMERA)
    }

    fun takePhotoFromCamera(fragment: Fragment) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        fragment.startActivityForResult(intent, RequestCodes.REQUEST_CODE_PHOTO_FROM_CAMERA)
    }

    fun getBitmapFromGalleryUri(contentResolver: ContentResolver, imageUri: Uri?): Bitmap {
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri!!)
        val exifInterface = ExifInterface(contentResolver.openInputStream(imageUri))
        val orientation = exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )

        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotate(bitmap, 90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotate(bitmap, 180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotate(bitmap, 270f)
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> flip(bitmap, true, false)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> flip(bitmap, false, true)
            else -> bitmap
        }
    }

    private fun rotate(bitmap: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun flip(bitmap: Bitmap, horizontal: Boolean, vertical: Boolean): Bitmap {
        val matrix = Matrix()
        matrix.preScale(if (horizontal) -1f else 1f, if (vertical) -1f else 1f)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun getBitmapFromPath(path: String?): Bitmap? {
        if (!path.isNullOrBlank()) {
            val imageFile = File(path)
            if (imageFile.exists()) {
                return BitmapFactory.decodeFile(imageFile.absolutePath)
            }
        }
        return null
    }

    fun deleteImage(path: String) {
        FileUtils.deleteFile(path)
    }

    @Throws(IOException::class)
    fun saveImage(myBitmap: Bitmap): String {
        val avatarFile = File("$APP_DIRECTORY${Calendar.getInstance().timeInMillis}.png")
        createImgDirectoryIfNotExists()

        try {
            avatarFile.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        try {
            FileOutputStream(avatarFile).also {
                myBitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
                it.flush()
                it.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return avatarFile.absolutePath
    }

    private fun createImgDirectoryIfNotExists() {
        val imageDirectory = File(APP_DIRECTORY)
        if (!imageDirectory.exists()) {
            imageDirectory.mkdirs()
        }
    }
}
