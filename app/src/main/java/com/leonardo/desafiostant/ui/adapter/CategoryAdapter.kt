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

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private lateinit var context : Context
    private var movies : List<Movie> = listOf()


    fun setDataSet(movies: List<Movie>){
        this.movies = movies
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {




        fun bind() {
            val recycler = itemView.findViewById<RecyclerView>(R.id.recycler_view_movie)
            recycler.layoutManager = LinearLayoutManager(itemView.context,
                RecyclerView.HORIZONTAL, false)
            recycler.adapter = MovieAdapter()

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.category_item , parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]

        holder.bind()


    }

    override fun getItemCount(): Int {
        return movies.size
    }
}