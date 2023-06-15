package com.kotlin.aplantgenius.main.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.kotlin.aplantgenius.R
import com.kotlin.aplantgenius.data.ApiConfig
import com.kotlin.aplantgenius.data.ErrorResponse
import com.kotlin.aplantgenius.data.HistoryResponse
import com.kotlin.aplantgenius.data.ListHistory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    fun getHistory(
        token: String,
        onSuccess: (List<ListHistory>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val apiService = ApiConfig().getApi()
        val call = apiService.getHistory(token)

        call.enqueue(object : Callback<HistoryResponse> {
            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) {
                if (response.isSuccessful) {
                    val historyResponse = response.body()?.history
                    if (historyResponse != null) {
                        val listHistory = historyResponse.map {
                            ListHistory(
                                it.id,
                                it.result,
                                it.image,
                                null
                            )
                        }
                        onSuccess(listHistory)
                    }
                } else {
                    val errorResponse = response.errorBody()?.string()
                    val error = Gson().fromJson(errorResponse, ErrorResponse::class.java)
                    val errorMessage = error.message
                    onFailure(errorMessage)
                }
            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                onFailure(getApplication<Application>().getString(R.string.failServer))
            }
        })
    }
}
