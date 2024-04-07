package com.aryanto.storyappfinal.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.aryanto.storyappfinal.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.sqrt

private const val FILE_FORMAT = "dd-MM-yyyy"
private const val MAX_IMAGE_SIZE = 1000000

val timeStamp: String = SimpleDateFormat(
    FILE_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun compressImage(context: Context, file: File): File {
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }
    BitmapFactory.decodeFile(file.absolutePath, options)

    val imageSize = options.outWidth * options.outHeight
    if (imageSize > MAX_IMAGE_SIZE) {
        val scaleFactor = sqrt(imageSize.toDouble() / MAX_IMAGE_SIZE)
        options.inSampleSize = scaleFactor.toInt()
    }

    options.inJustDecodeBounds = false
    val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)

    val compressedFile = File(context.cacheDir, "compressed_$timeStamp.jpg")
    FileOutputStream(compressedFile).use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, it)
    }

    return compressedFile

}

fun getImageUri(context: Context): Uri {
    var uri: Uri? = null

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValue = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "SA_IMG_$timeStamp.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/StoryApp")
        }

        uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValue
        )
    }

    return uri ?: getImageUriForQ(context)

}

fun getImageUriForQ(context: Context): Uri {
    val fileDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val imageFile = File(fileDir, "/StoryApp/SA_IMG_$timeStamp.jpg")

    return FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.fileprovider",
        imageFile
    )
}

