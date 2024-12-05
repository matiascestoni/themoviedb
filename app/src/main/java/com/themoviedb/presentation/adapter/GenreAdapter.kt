package com.themoviedb.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.themoviedb.databinding.ItemGenreSectionBinding
import com.themoviedb.presentation.model.MovieUIItem
import com.themoviedb.presentation.view.OnMovieSelectedListener

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    private var data: Map<String, List<MovieUIItem>> = emptyMap()
    private var listener: OnMovieSelectedListener? = null

    fun setListener(listener: OnMovieSelectedListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding =
            ItemGenreSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = data.keys.elementAt(position)
        val movies = data[genre] ?: emptyList()
        holder.bind(genre, movies)
    }

    fun submitData(newData: Map<String, List<MovieUIItem>>) {
        data = newData
        notifyDataSetChanged()
    }

    inner class GenreViewHolder(private val binding: ItemGenreSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: String, movies: List<MovieUIItem>) {
            binding.genreTitle.text = genre
            val movieAdapter = HorizontalMovieAdapter()
            movieAdapter.setListener(listener)
            binding.moviesList.adapter = movieAdapter
            movieAdapter.submitList(movies)
        }
    }
}