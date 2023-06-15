package com.kotlin.aplantgenius.diseases

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.kotlin.aplantgenius.R
import com.kotlin.aplantgenius.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ScanViewModel(application: Application) : AndroidViewModel(application) {
    suspend fun uploadImage(
        token: String,
        file: File,
        onSuccess: (ImageResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val fileCompress = compress(file)
        val base64 = base64(fileCompress)
        val request = ImageRequest(base64)

        val apiService = ApiConfig().getApi()
        val call = apiService.scanImage(token, request)

        call.enqueue(object : Callback<ImageResponse> {
            override fun onResponse(
                call: Call<ImageResponse>, response: Response<ImageResponse>
            ) {
                if (response.isSuccessful) {
                    val predictionResponse = response.body()

                    if (predictionResponse != null) {
                        onSuccess(predictionResponse)
                    }
                } else {
                    val errorResponse = response.errorBody()?.string()
                    val error = Gson().fromJson(errorResponse, ErrorResponse::class.java)
                    val errorMessage = error.message
                    onFailure(errorMessage)
                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                onFailure(getApplication<Application>().getString(R.string.failServer))
            }
        })
    }
}
