package com.example.myevent.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myevent.data.response.ListEventsItem
import com.example.myevent.databinding.ItemRowEventBinding

class EventAdapter(private val onItemClick: (ListEventsItem) -> Unit): ListAdapter<ListEventsItem, EventAdapter.MyViewHolder>(DIFF_CALLBACK)  {
    class MyViewHolder(val binding: ItemRowEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem, onItemClick: (ListEventsItem) -> Unit) {
            // Bind image
            Glide.with(binding.root.context)
                .load(event.imageLogo) // URL gambar dari API
                .into(binding.imgItemHome)

            // Bind deskripsi
            binding.tvDesHome.text = event.name

            // Set click listener for the item
            binding.root.setOnClickListener {
                onItemClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event, onItemClick)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}