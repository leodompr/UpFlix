package com.leonardo.desafiostant.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.leonardo.desafiostant.R
import com.leonardo.desafiostant.model.Category
import com.leonardo.desafiostant.model.Genre
import com.leonardo.desafiostant.model.Movie
import java.util.*

class CategoryAdapter (private val onItemClick : (Category) -> Unit) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() { //Adapter DetailsActivity
    private var genreList : List<Genre> = listOf()
    private var itemList : List<Category> = listOf()


    fun setDataSet(genre: List<Genre>, item: List<Category>){ //Alimenta a RecyclerView
        this.genreList = genre
        this.itemList = item
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun linkItem(category: Genre, onItemClick: (Category) -> Unit, item: Category) {

            itemView.setOnClickListener { //Captura os eventos de click
                onItemClick(item)
            }

            val imgCover =  itemView.findViewById<ImageView>(R.id.image_view_category)

            imgCover.setImageResource(item.img)


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.category_item , parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = genreList[position]
        val item =  itemList[position]
        holder.linkItem(category, onItemClick, item)


    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}