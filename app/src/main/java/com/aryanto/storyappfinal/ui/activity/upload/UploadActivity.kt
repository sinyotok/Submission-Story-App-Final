package com.aryanto.storyappfinal.ui.activity.upload

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aryanto.storyappfinal.R
import com.aryanto.storyappfinal.databinding.ActivityUploadBinding
import com.aryanto.storyappfinal.ui.activity.home.HomeActivity
import com.aryanto.storyappfinal.utils.ClientState
import com.aryanto.storyappfinal.utils.compressImage
import com.aryanto.storyappfinal.utils.getImageUri
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    private lateinit var checkBox: CheckBox
    private var lat: Double? = null
    private var lon: Double? = null

    private val uploadVM: UploadVM by viewModel<UploadVM>()

    private var currentImage: Uri? = null

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

        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        checkBox = binding.enableLocationCheckBox

        setView()

        btnOpenCamera()

        btnOpenGallery()

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                getMyLocation()
            }
        }

        btnUpload()

    }

    private fun getMyLocation() {

        if (ContextCompat.checkSelfPermission(
                this.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this.applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            binding.enableLocationCheckBox.isChecked = false
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
                100
            )

        } else {
            val location = fusedLocationProvider.lastLocation
            location.addOnSuccessListener {
                binding.enableLocationCheckBox.isChecked = true
                lat = it.latitude
                lon = it.longitude
            }
        }

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
                        resources.data?.message?.let { showToast(it) }
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

    private fun previewImage() {
        currentImage?.let { uploadVM.setPhotoPath(it) }
    }

    private val launchCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            previewImage()
        } else {
            showToast("No media captured")
        }
    }

    private fun btnOpenCamera() {
        binding.buttonOpenCamera.apply {
            setOnClickListener {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                currentImage = getImageUri(this@UploadActivity)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, currentImage)
                launchCamera.launch(intent)
            }
        }
    }

    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            currentImage = uri
            previewImage()
        } else {
            showToast("No media selected")
        }
    }

    private fun btnOpenGallery() {
        binding.buttonOpenGallery.apply {
            setOnClickListener {
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

                    val file = compressImage(this@UploadActivity, fileImage)

                    val photoRB = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val descPart = desc.toRequestBody("text/plain".toMediaTypeOrNull())

                    val filePart = photoRB.let {
                        MultipartBody.Part.createFormData(
                            "photo",
                            file.name,
                            it
                        )
                    }
                    if (lat != null && lon != null) {
                        uploadVM.uploadStory(filePart, descPart, lat!!, lon!!)
                    } else {
                        uploadVM.uploadStory(filePart, descPart, lat = 0.0, lon = 0.0)
                    }

                } else {
                    showToast("Please take a picture")
                }

            }

        }

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
                val file = File(cacheDir, fileName)

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