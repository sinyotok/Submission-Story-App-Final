package com.aryanto.storyappfinal.ui.activity.upload

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aryanto.storyappfinal.R
import com.aryanto.storyappfinal.BuildConfig
import com.aryanto.storyappfinal.databinding.ActivityUploadBinding
import com.aryanto.storyappfinal.ui.activity.home.HomeActivity
import com.aryanto.storyappfinal.utils.ClientState
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import kotlin.math.sqrt

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    private val uploadVM: UploadVM by viewModel<UploadVM>()

    private var currentImage: Uri? = null

    companion object {
        private const val MAX_IMAGE_SIZE = 1000000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setView()

        btnOpenCamera()

        btnOpenGallery()

        btnUpload()

    }

    private fun setView() {
        binding.apply {
            uploadVM.photoPath.observe(this@UploadActivity) {
                imagePreview.setImageURI(it)
                currentImage = it
            }

            uploadVM.addStory.observe(this@UploadActivity) { resources ->
                when (resources) {
                    is ClientState.SUCCESS -> {
                        uploadProgressBar.visibility = View.GONE
//                        resources.data?.message?.let { showToast(it) }
                        showToast("${resources.data?.message}")
                        directToHome()
                    }

                    is ClientState.ERROR -> {
                        uploadProgressBar.visibility = View.GONE
                        handleError(resources.message)
                        showToast("${resources.message}")
                    }

                    is ClientState.LOADING -> {
                        uploadProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun handleError(errorMSG: String?) {
        binding.apply {
            when {
                errorMSG?.contains("description") == true -> {
                    descriptionForm.setDescError(errorMSG)
                }

                else -> {
                    showToast("$errorMSG")
                }
            }
        }
    }

    private val launchCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            currentImage?.let { uploadVM.setPhotoPath(it) }
        } else {
            showToast("No media captured")
        }
    }

    private fun btnOpenCamera() {
        binding.apply {
            buttonOpenCamera.setOnClickListener {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val photoUri = createImageUri(this@UploadActivity)
                currentImage = photoUri
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                launchCamera.launch(intent)
            }
        }
    }

    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            uploadVM.setPhotoPath(uri)
            currentImage = uri
        } else {
            showToast("No media selected")
        }
    }

    private fun btnOpenGallery() {
        binding.apply {
            buttonOpenGallery.setOnClickListener {
                launchGallery.launch(arrayOf("image/*"))
            }
        }
    }

    private fun btnUpload() {
        binding.apply {
            buttonUpload.setOnClickListener {
                val fileImage = currentImage?.let { File(getPathImage(it)) }
                val desc = descriptionForm.getDesc()

                if (fileImage != null) {
                    val compressedFile = compressImage(this@UploadActivity,fileImage)
                    val photoRB =
                        compressedFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val descPart = desc.toRequestBody("text/plain".toMediaTypeOrNull())

                    val photoPart = photoRB.let {
                        MultipartBody.Part.createFormData(
                            "photo",
                            fileImage.name,
                            it
                        )
                    }

                    uploadVM.uploadStory(photoPart, descPart)

                } else {
                    showToast("Please take a picture")
                }
            }
        }
    }


    private fun createImageUri(context: Context): Uri {
        var uri: Uri? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val timestamp = System.currentTimeMillis()
            val contentValue = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "SA_IMG_$timestamp.jpg")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/StoryApp")
            }

            uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValue
            )
        }

        return uri ?: getImageUri(context)

    }

    private fun getImageUri(context: Context): Uri {
        val fileDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val timestamp = System.currentTimeMillis()
        val imageFile = File(fileDir, "/StoryApp/SA_IMG_$timestamp.jpg")

        return FileProvider.getUriForFile(
            context,
            "${BuildConfig.APPLICATION_ID}.fileprovider",
            imageFile
        )
    }

    private fun getPathImage(uri: Uri): String {
        var filePath = ""

        if (uri.scheme == "content") {

            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                it.moveToFirst()
                val columIndex: Int =
                    it.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME)
                val fileName = it.getString(columIndex)
                val inputStream = contentResolver.openInputStream(uri)
                val file = File(cacheDir,fileName)

                inputStream?.use { input ->
                    val outputStream = FileOutputStream(file)
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }

                inputStream?.close()
                filePath = file.absolutePath
            }
        } else if (uri.scheme == "file") {
            filePath = uri.pathSegments.joinToString(File.separator)
        }

        return filePath

    }

    private fun compressImage(context: Context, file: File): File {
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

        val timestamp = System.currentTimeMillis()
        val compressedFile = File(context.cacheDir,"compressed_$timestamp.jpg")
        FileOutputStream(compressedFile).use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, it)
        }

        return compressedFile

    }

    private fun directToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this@UploadActivity, message, Toast.LENGTH_LONG).show()
    }

}