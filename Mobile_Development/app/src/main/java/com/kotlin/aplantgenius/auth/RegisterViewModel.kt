package com.kotlin.aplantgenius.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.kotlin.aplantgenius.R
import com.kotlin.aplantgenius.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    fun register(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val request = RegisterRequest(name, email, password)
        val call = ApiConfig().getApi().registerUser(request)

        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>, response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    val errorResponse = response.errorBody()?.string()
                    val error = Gson().fromJson(errorResponse, ErrorRegister::class.java)
                    val errorMessage = error.details.getErrorMessage()
                    onFailure(errorMessage)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                onFailure(getApplication<Application>().getString(R.string.failServer))
            }
        })
    }

    private fun ErrorDetails.getErrorMessage(): String {
        val errorMessages = mutableListOf<String>()

        email?.message?.let { errorMessages.add(it) }
        name?.message?.let { errorMessages.add(it) }
        password?.message?.let { errorMessages.add(it) }

        val errorMessage = errorMessages.joinToString(" and ")
        errorMessages.clear()

        return errorMessage
    }
}
