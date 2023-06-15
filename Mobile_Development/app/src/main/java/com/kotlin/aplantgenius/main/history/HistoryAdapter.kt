package com.kotlin.aplantgenius.main.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlin.aplantgenius.data.ListHistory
import com.kotlin.aplantgenius.databinding.ListHistoryBinding
import com.kotlin.aplantgenius.diseases.DetailActivity

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val listHistory = mutableListOf<ListHistory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val binding = ListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        holder.bind(listHistory[position])
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    fun setList(users: List<ListHistory>?) {
        if (users != null) {
            val diffCallback = ListStoryDiffCallback(listHistory, users)
            val result = DiffUtil.calculateDiff(diffCallback)
            listHistory.clear()
            listHistory.addAll(users)
            result.dispatchUpdatesTo(this)
        }
    }

    inner class ViewHolder(private val binding: ListHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: ListHistory) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(history.image)
                    .into(ivPhoto)
                tvName.text = history.result

                root.setOnClickListener {
                    val intent = Intent(root.context, DetailActivity::class.java).apply {
                        val bundle = Bundle().apply {
                            putString(DetailActivity.EXTRA_ID, history.id.toString())
                        }
                        putExtras(bundle)
                    }
                    root.context.startActivity(intent)
                }
            }
        }
    }

    class ListStoryDiffCallback(
        private val oldList: List<ListHistory>,
        private val newList: List<ListHistory>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}