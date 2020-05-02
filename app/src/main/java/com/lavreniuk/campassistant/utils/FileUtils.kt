package com.lavreniuk.campassistant.utils

import java.io.File

object FileUtils {

    fun deleteFile(path: String) {
        val fileToDelete = File(path)
        if (fileToDelete.exists()) {
            fileToDelete.delete()
        }
    }
}