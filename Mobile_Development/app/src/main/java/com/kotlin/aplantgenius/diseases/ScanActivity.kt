package com.kotlin.aplantgenius.diseases

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.kotlin.aplantgenius.R
import com.kotlin.aplantgenius.data.*
import com.kotlin.aplantgenius.databinding.ActivityScanBinding
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS,
            )
        }
        binding.backScan.setOnClickListener {
            finish()
        }

        binding.cameraXButton.setOnClickListener { startCameraX() }

        binding.galleryButton.setOnClickListener { startGallery() }

        binding.btnToCheck.setOnClickListener {
            val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val myToken = sharedPref.getString("token", null)

            if (getFile != null) {
                CoroutineScope(Dispatchers.Main).launch {
                    uploadImage(myToken.toString(), getFile!!)
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.noImage),
                    Toast.LENGTH_SHORT
                ).show()
                progressBar(false)
            }
        }
    }

    private suspend fun uploadImage(token: String, file: File) {

        progressBar(true)
        withContext(Dispatchers.IO) {
            val fileCompress = compress(file)
            val fileBytes = fileCompress.readBytes()

            val base64 = base64(fileCompress)

            val request = ImageRequest(base64)

            val apiService = ApiConfig().getApi()
            val call = apiService.scanImage(token, request)

            call.enqueue(object : Callback<ImageResponse> {
                override fun onResponse(
                    call: Call<ImageResponse>,
                    response: Response<ImageResponse>
                ) {
                    if (response.isSuccessful) {
                        val predictionResponse = response.body()

                        if (predictionResponse != null) {
                            Toast.makeText(
                                applicationContext,
                                predictionResponse.penyakit,
                                Toast.LENGTH_SHORT
                            ).show()
                            progressBar(false)


                            val intent = Intent(this@ScanActivity, DetailActivity::class.java)
                            intent.putExtra("image", fileBytes)
                            intent.putExtra("penyakit", predictionResponse.penyakit)
                            intent.putExtra("penanganan", predictionResponse.penanganan)
                            startActivity(intent)
                        }

                    } else {
                        val errorResponse = response.errorBody()?.string()
                        val error = Gson().fromJson(errorResponse, ErrorImage::class.java)
                        val errorMessage = error?.message
                        Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
                        progressBar(false)
                    }
                }

                override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                    Toast.makeText(
                        applicationContext, getString(R.string.failServer), Toast.LENGTH_SHORT
                    ).show()
                    progressBar(false)
                }
            })
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_PERMISSIONS -> {
                if (allPermissionsGranted()) {
                    Toast.makeText(this, getString(R.string.getPermission), Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this, getString(R.string.noPermission), Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraXActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as? File

            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            runOnUiThread {
                progressBar(true)
                myFile?.let { file ->
                    rotateFile(file, isBackCamera)
                    getFile = file
                    binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
                    progressBar(false)
                }
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri

            runOnUiThread {
                progressBar(true)
                selectedImg.let { uri ->
                    val myFile = uriToFile(uri, this)
                    getFile = myFile
                    binding.previewImageView.setImageURI(uri)
                    progressBar(false)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("imagePath", getFile?.absolutePath)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val imagePath = savedInstanceState.getString("imagePath")
        if (!imagePath.isNullOrEmpty()) {
            getFile = File(imagePath)
            binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(imagePath))
        }
    }

    private fun progressBar(visible: Boolean) {
        if (visible) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
        )
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}
