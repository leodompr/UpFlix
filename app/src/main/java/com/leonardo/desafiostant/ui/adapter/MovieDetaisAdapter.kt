package com.leonardo.desafiostant.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.leonardo.desafiostant.R
import com.leonardo.desafiostant.model.Movie

class MovieDetaisAdapter(private val onItemClick : (Movie) -> Unit) : RecyclerView.Adapter<MovieDetaisAdapter.ViewHolder>() { //Adapter DetailsActivity
    private var movies : List<Movie> = listOf()


    fun setDataSet(movies: List<Movie>){ //Alimenta a RecyclerView
        this.movies = movies
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun linkItem(movie: Movie, onItemClick: (Movie) -> Unit) {

            itemView.setOnClickListener { //Captura os eventos de click
                onItemClick(movie)
            }

            val imgCover =  itemView.findViewById<ImageView>(R.id.image_view_cover)
            Glide.with(imgCover)
                .load("https://image.tmdb.org/t/p/w500/" + movie.poster_path)
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
        holder.linkItem(movie, onItemClick)


    }

    override fun getItemCount(): Int {
        return Math.min(movies.size, 9);
    }
}
