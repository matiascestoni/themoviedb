package com.themoviedb.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.themoviedb.R
import com.themoviedb.databinding.ItemCarouselBinding
import com.themoviedb.domain.model.ImageUrlProvider
import com.themoviedb.presentation.model.MovieDiffCallback
import com.themoviedb.presentation.model.MovieUIItem
import com.themoviedb.presentation.view.OnMovieSelectedListener

class CarouselAdapter :
    ListAdapter<MovieUIItem, CarouselAdapter.CarouselViewHolder>(
        MovieDiffCallback()
    ) {
    private var listener: OnMovieSelectedListener? = null

    fun setListener(listener: OnMovieSelectedListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding =
            ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CarouselViewHolder(private val binding: ItemCarouselBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MovieUIItem) {
            binding.movieOverview.text = item.overview
            Glide.with(binding.root.context)
                .load(ImageUrlProvider.getPosterImageUrl(item.posterPath ?: ""))
                .placeholder(R.drawable.sample_poster)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.error_image)
                .into(binding.movieImage)

            binding.root.setOnClickListener {
                item.id?.let { listener?.onMovieSelected(it) }
            }
        }
    }
}
