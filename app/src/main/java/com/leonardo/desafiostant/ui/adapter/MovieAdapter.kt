package com.leonardo.desafiostant.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.leonardo.desafiostant.R
import com.leonardo.desafiostant.model.Movie

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private lateinit var context : Context
    private var movies : List<Movie> = listOf()


    fun setDataSet(movies: List<Movie>){
        this.movies = movies
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun linkItem(movie: Movie) {

           val imgCover =  itemView.findViewById<ImageView>(R.id.image_view_cover)
            Glide.with(imgCover)
                .load("https://image.tmdb.org/t/p/w500/" + movie.poster_path)
                .placeholder(R.drawable.ic_movies)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fallback(R.drawable.ic_broken)
                .into(imgCover)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.mov_item , parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.linkItem(movie)


    }

    override fun getItemCount(): Int {
        return movies.size
    }
}
