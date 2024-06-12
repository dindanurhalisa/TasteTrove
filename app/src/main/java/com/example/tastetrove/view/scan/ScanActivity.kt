package com.example.tastetrove.view.scan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.net.toUri
import com.example.tastetrove.R
import com.example.tastetrove.common.base.BaseActivity
import com.example.tastetrove.common.ext.setImageExt
import com.example.tastetrove.common.ext.showToast
import com.example.tastetrove.common.ext.startActivityExt
import com.example.tastetrove.databinding.ActivityScanBinding
import com.example.tastetrove.util.getFileNameFromUri
import com.example.tastetrove.util.getImageUri
import com.example.tastetrove.view.picker.CameraActivity
import com.example.tastetrove.view.picker.CameraActivity.Companion.CAMERAX_RESULT
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanActivity : BaseActivity<ActivityScanBinding>() {

    private val viewModel : ScanViewModel by viewModels()

    private var currentImageUri: Uri? = null

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            runUCrop(uri)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun runUCrop(uri: Uri) {
        val destinationUri = getFileNameFromUri(uri, "jpg")
        UCrop.of(uri, destinationUri)
            .withMaxResultSize(400, 400)
            .withAspectRatio(16f, 9f)
            .start(this)
    }


    override fun setupViewBinding(): ActivityScanBinding {
        return ActivityScanBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {

            cameraButton.setOnClickListener {
                startCamera()
            }

            galeryButton.setOnClickListener {
                startGallery()
            }

            analyzeButton.setOnClickListener {
                analyzeImage()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            if (data != null) {
                currentImageUri = UCrop.getOutput(data)
                showImage()
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            showToast(cropError?.message ?: "Internal App Error")
        }
    }

    private fun startGallery() {
        // TODO: Mendapatkan gambar dari Gallery.
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.scanImage.setImageExt(it.toString())
        }
    }

    private fun analyzeImage() {
        // TODO: Menganalisa gambar yang berhasil ditampilkan.
        currentImageUri?.let {
            startActivityExt<ResultActivity> {
                it.putExtra(ResultActivity.EXTRA_IMAGE_URI, currentImageUri.toString())
            }
        } ?: run {
            showToast(getString(R.string.empty_image))
        }
    }
}