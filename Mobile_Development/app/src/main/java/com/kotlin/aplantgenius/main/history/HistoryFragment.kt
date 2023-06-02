package com.kotlin.aplantgenius.main.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.aplantgenius.data.ListHistory
import com.kotlin.aplantgenius.databinding.FragmentHistoryBinding

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

        val historyItems = listOf(
            ListHistory("Item 1", "photo_1.jpg", "Description 1", "1"),
            ListHistory("Item 2", "photo_2.jpg", "Description 2", "2"),
            ListHistory("Item 3", "photo_3.jpg", "Description 3", "3")
        )

        adapter = HistoryAdapter()
        adapter.setList(historyItems)
        binding.rvHistory.adapter = adapter
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
    }
}
