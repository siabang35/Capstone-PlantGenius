package com.kotlin.aplantgenius.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.aplantgenius.data.ApiConfig
import com.kotlin.aplantgenius.data.RegisterRequest
import com.kotlin.aplantgenius.data.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
class RegisterViewModel : ViewModel() {

    private val _registerStatus = MutableLiveData<Boolean>()
    val registerStatus: LiveData<Boolean> = _registerStatus

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun registerUser(name: String, email: String, password: String, phone: String) {
        _isLoading.value = true

        val request = RegisterRequest(name, email, password, phone)
        val call = ApiConfig().getApi().registerUser(request)

        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _registerStatus.value = true
                } else {
                    _errorMessage.value = response.errorBody().toString()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}

 */
