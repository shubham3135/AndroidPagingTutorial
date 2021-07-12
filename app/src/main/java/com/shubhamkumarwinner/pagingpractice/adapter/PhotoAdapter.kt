package com.shubhamkumarwinner.pagingpractice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shubhamkumarwinner.pagingpractice.data.Photo
import com.shubhamkumarwinner.pagingpractice.databinding.ItemBinding

class PhotoAdapter: PagingDataAdapter<Photo, PhotoAdapter.PhotoViewHolder>(diffCallBack) {
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        if (item!=null){
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    class PhotoViewHolder(private val binding: ItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(photo: Photo?) {
            binding.apply {
                imageView.load(photo?.urls?.regular)
                textViewUserName.text = photo?.user?.username
            }
        }
    }

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                // Id is unique.
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem == newItem
            }
        }
    }

}