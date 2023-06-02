package com.kotlin.aplantgenius.main.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlin.aplantgenius.data.ListHistory
import com.kotlin.aplantgenius.databinding.ListHistoryBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val listStory = mutableListOf<ListHistory>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val binding = ListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        holder.bind(listStory[position])
    }

    override fun getItemCount(): Int {
        return listStory.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListHistory)
    }

    fun setList(users: List<ListHistory>?) {
        if (users != null) {
            val diffCallback = ListStoryDiffCallback(listStory, users)
            val result = DiffUtil.calculateDiff(diffCallback)
            listStory.clear()
            listStory.addAll(users)
            result.dispatchUpdatesTo(this)
        }
    }

    inner class ViewHolder(private val binding: ListHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ListHistory) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.photo)
                    .into(ivPhoto)
                tvName.text = user.name
                tvDes.text = user.description
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }

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
