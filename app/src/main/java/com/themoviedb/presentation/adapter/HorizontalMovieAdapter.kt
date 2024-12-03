package com.themoviedb.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.themoviedb.R
import com.themoviedb.databinding.ItemMovieUiBinding
import com.themoviedb.domain.model.ImageUrlProvider
import com.themoviedb.presentation.model.MovieDiffCallback
import com.themoviedb.presentation.model.MovieUIItem

class HorizontalMovieAdapter :
    ListAdapter<MovieUIItem, HorizontalMovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieUiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovieViewHolder(private val binding: ItemMovieUiBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieUIItem) {
            binding.movieTitle.text = item.title
            Glide.with(binding.root.context)
                .load(ImageUrlProvider.getThumbnailImageUrl(item.posterPath ?: ""))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.sample_poster)
                .error(R.drawable.error_image)
                .into(binding.movieImage)
        }
    }
}
