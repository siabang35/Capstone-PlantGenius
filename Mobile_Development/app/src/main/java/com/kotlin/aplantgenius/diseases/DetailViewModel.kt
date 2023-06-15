package com.kotlin.aplantgenius.diseases

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.kotlin.aplantgenius.R
import com.kotlin.aplantgenius.data.ApiConfig
import com.kotlin.aplantgenius.data.DetailResponse
import com.kotlin.aplantgenius.data.ErrorResponse
import com.kotlin.aplantgenius.data.ListHistory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _historyDetail = MutableLiveData<ListHistory>()
    val detailHistory: LiveData<ListHistory> = _historyDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetail(token: String?, id: Int?) {
        _isLoading.value = true

        val client = ApiConfig().getApi().getHistoryById(token!!, id!!)

        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    val detailHistory = response.body()?.let {
                        ListHistory(
                            it.id,
                            it.penyakit,
                            it.image,
                            it.penanganan
                        )
                    }
                    _historyDetail.value = detailHistory!!
                    _isLoading.value = false

                } else {
                    val errorResponse = response.errorBody()?.string()
                    val error = Gson().fromJson(errorResponse, ErrorResponse::class.java)
                    val errorMessage = error.message
                    Toast.makeText(getApplication(), errorMessage, Toast.LENGTH_SHORT).show()
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Toast.makeText(
                    getApplication(),
                    getApplication<Application>().getString(R.string.failServer),
                    Toast.LENGTH_SHORT
                ).show()
                _isLoading.value = false
            }
        })
    }
}
