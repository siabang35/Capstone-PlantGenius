package com.kotlin.aplantgenius.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.aplantgenius.data.ApiConfig
import com.kotlin.aplantgenius.data.LoginRequest
import com.kotlin.aplantgenius.data.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
//class LoginViewModel Try
class LoginViewModel : ViewModel() {

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> = _loginStatus

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loginUser(email: String, password: String) {
        _isLoading.value = true

        val request = LoginRequest(email, password)
        val call = ApiConfig().getApi().loginUser(request)

        call.enqueue(object : Callback<LoginResponse> {

            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _loginStatus.value = true

                } else {
                    val errorResponse = response.errorBody()
                    _errorMessage.value = errorResponse.toString()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}
 */
