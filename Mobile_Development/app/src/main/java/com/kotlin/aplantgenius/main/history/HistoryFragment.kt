package com.kotlin.aplantgenius.main.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.aplantgenius.R
import com.kotlin.aplantgenius.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private val viewModel: HistoryViewModel by viewModels()

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

        viewModel.getHistory(token.toString(), { listHistory ->
            adapter.setList(listHistory)
            progressBar(false)

            if (listHistory.isEmpty()) {
                binding.tvEmptyHistory.visibility = View.VISIBLE
            } else {
                binding.tvEmptyHistory.visibility = View.GONE
            }
        }, { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            progressBar(false)
        })
    }

    private fun progressBar(visible: Boolean) {
        binding.progressBar.visibility = if (visible) View.VISIBLE else View.GONE
    }
}