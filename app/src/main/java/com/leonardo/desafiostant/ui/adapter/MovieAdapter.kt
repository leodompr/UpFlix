package com.leonardo.desafiostant.ui.adapter

import android.annotation.SuppressLint
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
import com.bumptech.glide.request.RequestOptions
import com.leonardo.desafiostant.R
import com.leonardo.desafiostant.model.Genre
import com.leonardo.desafiostant.model.Movie
import jp.wasabeef.glide.transformations.*
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation

class MovieAdapter(private val onItemClick : (Movie) -> Unit) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() { //Adapter MainActivity
    private var movies : List<Movie> = listOf()
    private var genre : List<Genre> = listOf()

    fun setDataSet(movies: List<Movie>){ //Alimenta a RecyclerView com os Filmes
        this.movies = movies
    }

    fun setDataGenre(genre: List<Genre>){  //Lista de Generos para a fun linkItem
        this.genre = genre
    }

    fun filterList(movieSearch: MutableList<Movie>) { //Filtro de Busca na Lista
        this.movies = movieSearch
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun linkItem(movie: Movie, onItemClick: (Movie) -> Unit, genre: List<Genre>) {

            itemView.setOnClickListener { //Captura os eventos de click
                onItemClick(movie)
            }

            val nameMovie : TextView = itemView.findViewById(R.id.name_movie_item)
            nameMovie.text = movie.title

            val categoryMovie : TextView = itemView.findViewById(R.id.text_view_title_category_item)

            for (g in genre){
                if (movie.genre_ids!!.contains(g.id)){
                    categoryMovie.text = g.name
                }
            }

            val dataApi = movie.release_date

            val dateMovie : TextView = itemView.findViewById(R.id.text_view_date_category_item)
            dateMovie.text = "${dataApi.substring(8,10)}/${dataApi.substring(5, 7)}/${dataApi.substring(0, 4)}"

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
        holder.linkItem(movie, onItemClick, genre)

    }

    override fun getItemCount(): Int {
        return movies.size
        return genre.size
    }
}
