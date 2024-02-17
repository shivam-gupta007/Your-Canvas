package com.shivamgupta.yourcanvas.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FileUtils {
    private const val DIRECTORY_NAME = "Your Canvas App"

    fun saveImage(
        bitmap: Bitmap,
        onImageSaved: () -> Unit,
        onImageFailure: (exception: Exception) -> Unit,
    ) {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val externalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val storageDir = File(externalDir, DIRECTORY_NAME)

        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }

        val imageFile = File(storageDir,"PNG_${timeStamp}.jpeg")

        try {
            val out = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
            onImageSaved()
        } catch (e: Exception) {
            onImageFailure(e)
        }
    }

}