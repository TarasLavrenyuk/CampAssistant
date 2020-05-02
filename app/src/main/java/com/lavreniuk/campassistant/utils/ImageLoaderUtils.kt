package com.lavreniuk.campassistant.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.Fragment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


object ImageLoaderUtils {

    private const val APP_DIRECTORY = "/CampAssistant"

    fun choosePhotoFromGallery(activity: Activity) {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*";
        activity.startActivityForResult(galleryIntent, RequestCodes.REQUEST_CODE_PHOTO_FROM_GALLERY)
    }

    fun choosePhotoFromGallery(fragment: Fragment) {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*";
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

    @TargetApi(Build.VERSION_CODES.N)
    fun getBitmapFromGalleryUri(contentResolver: ContentResolver, imageUri: Uri?): Bitmap {
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri!!)
        val exifInterface = ExifInterface(contentResolver.openInputStream(imageUri))
        val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

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
                return BitmapFactory.decodeFile(imageFile.absolutePath);
            }
        }
        return null
    }

    fun deleteImage(path: String) {
        FileUtils.deleteFile(path)
    }

    @Throws(IOException::class)
    fun saveImage(myBitmap: Bitmap, context: Context): String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            Environment.getExternalStorageDirectory().toString() + APP_DIRECTORY
        )
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }

        val f = File(
            wallpaperDirectory, ((Calendar.getInstance().timeInMillis).toString() + ".jpg")
        )
        f.createNewFile()
        val fo = FileOutputStream(f)
        fo.write(bytes.toByteArray())
        MediaScannerConnection.scanFile(
            context,
            arrayOf(f.path),
            arrayOf("image/jpeg"), null
        )
        fo.close()

        return f.absolutePath
    }

}