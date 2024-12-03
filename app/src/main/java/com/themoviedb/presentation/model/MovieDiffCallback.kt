package com.themoviedb.presentation.model

import androidx.recyclerview.widget.DiffUtil

class MovieDiffCallback : DiffUtil.ItemCallback<MovieUIItem>() {
    override fun areItemsTheSame(oldItem: MovieUIItem, newItem: MovieUIItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieUIItem, newItem: MovieUIItem): Boolean {
        return oldItem == newItem
    }
}
