package com.kotlin.aplantgenius.auth

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.kotlin.aplantgenius.R
import com.kotlin.aplantgenius.data.ApiConfig
import com.kotlin.aplantgenius.data.LoginErrorResponse
import com.kotlin.aplantgenius.data.LoginRequest
import com.kotlin.aplantgenius.data.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel(application: Application) : AndroidViewModel(application) {

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val request = LoginRequest(email, password)
        val call = ApiConfig().getApi().loginUser(request)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>, response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val token = response.body()?.token

                    if (token != null) {
                        val userEmail = response.body()?.email
                        val userName = response.body()?.name
                        saveUserCredentials(token, userEmail, userName)
                        onSuccess()
                    }

                } else {
                    val errorResponse = response.errorBody()?.string()
                    val error = Gson().fromJson(errorResponse, LoginErrorResponse::class.java)
                    val errorMessage = error.message
                    onFailure(errorMessage)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onFailure(getApplication<Application>().getString(R.string.failServer))
            }
        })
    }

    private fun saveUserCredentials(token: String, userEmail: String?, userName: String?) {

        val shared = getApplication<Application>()
            .getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        with(shared.edit()) {
            putString("token", token)
            putString("email", userEmail)
            putString("name", userName)
            apply()
        }
    }
}
