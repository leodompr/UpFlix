package com.leonardo.desafiostant.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.leonardo.desafiostant.MainActivity
import com.leonardo.desafiostant.R
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation

class DetailsActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val toll : Toolbar = findViewById(R.id.toolbarDetails)

        val nameIntent = intent.getStringExtra("nameMovie")
        val overviewIntent = intent.getStringExtra("overviewMovie")
        val dateIntent = intent.getStringExtra("dateMovie")
        val posterPatchIntent = intent.getStringExtra("posterPatchMovie")
        println(nameIntent)


        setActionBar(toll) //ATIVA A TOOLBAR


        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }


        posterPatchIntent.let {

            try {
                val textNameMovie : TextView = findViewById(R.id.text_view_title_movie_details)
                textNameMovie.text = nameIntent

                val imgCover = findViewById<ImageView>(R.id.imageView)
                Glide.with(imgCover)
                    .load("https://image.tmdb.org/t/p/w500/$posterPatchIntent")
                    .placeholder(R.drawable.ic_movies)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fallback(R.drawable.ic_broken)
                    .apply(RequestOptions.bitmapTransform(VignetteFilterTransformation()))
                    .into(imgCover)
            } catch (e: Exception) {
                intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }

        }



    }
}