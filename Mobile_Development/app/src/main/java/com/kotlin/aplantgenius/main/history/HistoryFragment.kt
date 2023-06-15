package com.kotlin.aplantgenius.main.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.kotlin.aplantgenius.R
import com.kotlin.aplantgenius.data.ApiConfig
import com.kotlin.aplantgenius.data.ErrorResponse
import com.kotlin.aplantgenius.data.HistoryResponse
import com.kotlin.aplantgenius.data.ListHistory
import com.kotlin.aplantgenius.databinding.FragmentHistoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HistoryAdapter()
        binding.rvHistory.adapter = adapter
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())

        val itemSpacing = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val itemDecoration = CardList(itemSpacing)
        binding.rvHistory.addItemDecoration(itemDecoration)

        binding.SubsButton.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.coming_soon), Toast.LENGTH_SHORT)
                .show()
        }

        getHistory()
    }

    private fun getHistory() {
        val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", null)

        progressBar(true)
        val apiService = ApiConfig().getApi()
        val call = apiService.getHistory(token.toString())

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
                        adapter.setList(listHistory)
                        progressBar(false)
                    }
                } else {
                    val errorResponse = response.errorBody()?.string()
                    val error = Gson().fromJson(errorResponse, ErrorResponse::class.java)
                    val errorMessage = error.message
                    Toast.makeText(
                        requireContext(), errorMessage, Toast.LENGTH_SHORT
                    ).show()
                    progressBar(false)
                }
            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                Toast.makeText(requireContext(), getString(R.string.failServer), Toast.LENGTH_SHORT)
                    .show()
                progressBar(false)
            }
        })
    }

    private fun progressBar(visible: Boolean) {
        binding.progressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }
}
