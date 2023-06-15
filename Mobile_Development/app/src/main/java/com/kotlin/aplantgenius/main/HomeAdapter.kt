package com.kotlin.aplantgenius.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlin.aplantgenius.data.ListHistory
import com.kotlin.aplantgenius.databinding.HomeListBinding

class HomeAdapter(private val dataList: List<ListHistory>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HomeListBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = dataList.size

    inner class ViewHolder(private val binding: HomeListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ListHistory) {
            binding.apply {
                tvName.text = data.name
                tvDesc.text = data.desc

                Glide.with(itemView)
                    .load(data.image)
                    .into(ivImage)
            }
        }
    }
}
