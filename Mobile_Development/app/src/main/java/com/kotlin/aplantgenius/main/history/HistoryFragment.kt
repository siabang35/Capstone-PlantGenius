package com.kotlin.aplantgenius.main.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.aplantgenius.R
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

        val drawable = R.drawable.photo_history

        val historyItems = listOf(
            ListHistory(
                "Item 1",
                drawable,
                "cndcjzcjbzjbzbchzb cbuzdbuzbcubc cbz cbzbckuzbc zcb zbc z bcukzb cuz ckz bkz ckuzb ckuzbckuz cz kcb zuczk ",
                "1"
            ),
            ListHistory("Item 2", drawable, "Description 2", "2"),
            ListHistory("Item 3", drawable, "Description 3", "3"),
            ListHistory("Item 4", drawable, "Description 4", "4"),
            ListHistory("Item 5", drawable, "Description 5", "5")
        )

        adapter = HistoryAdapter()
        adapter.setList(historyItems)
        binding.rvHistory.adapter = adapter
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())

        binding.rvHistory
        val itemSpacing = resources.getDimensionPixelSize(R.dimen.item_spacing)

        val itemDecoration = CardList(itemSpacing)
        binding.rvHistory.addItemDecoration(itemDecoration)

        binding.SubsButton.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur Ini Akan Segera Hadir", Toast.LENGTH_SHORT)
                .show()
        }
    }
}
