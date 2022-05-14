package com.leonardo.desafiostant.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.leonardo.desafiostant.R
import com.leonardo.desafiostant.interfacesRetro.RetrofitService
import com.leonardo.desafiostant.model.Genre
import com.leonardo.desafiostant.repositories.DetailsRepository
import com.leonardo.desafiostant.ui.adapter.MovieDetaisAdapter
import com.leonardo.desafiostant.viewmodel.DetailsViewModel
import com.leonardo.desafiostant.viewmodel.DetailsViewModelFactory
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class DetailsActivity() : AppCompatActivity() {
    lateinit var viewModel: DetailsViewModel
    private val retrofitService = RetrofitService.getInstance()
    var listGenre : MutableList<Genre> = mutableListOf()
    var adapter = MovieDetaisAdapter {}
    var idMovie : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        viewModel = ViewModelProvider(this@DetailsActivity, DetailsViewModelFactory(DetailsRepository(retrofitService)))[DetailsViewModel::class.java]


        val toolbar = findViewById<Toolbar>(R.id.toolbarDetails)
        setSupportActionBar(toolbar) //ATIVA A TOOLBAR

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            title = null
        }

        //Dados recibidos da MainActivity para exibir os detalhes
        val nameIntent = intent.getStringExtra("nameMovie")
        val overviewIntent = intent.getStringExtra("overviewMovie")
        val dateIntent = intent.getStringExtra("dateMovie")
        val idMovier = intent.getStringExtra("idMovie")
        idMovie = idMovier!!.toInt()
        val posterPatchIntent = intent.getStringExtra("posterPatchMovie")
        val languageIntent = intent.getStringExtra("languageMovie")
        val genresIntent = intent.getStringExtra("genresMovie")
        println(idMovier)


        try {
            adapter = MovieDetaisAdapter{ //Envia os dados para exibir os detalhes
                it.let {
                    intent = Intent(applicationContext, DetailsActivity::class.java)
                    intent.putExtra("posterPatchMovie", it.poster_path)
                    intent.putExtra("nameMovie", it.title)
                    intent.putExtra("dateMovie", it.release_date)
                    intent.putExtra("idMovie", it.id.toString())
                    intent.putExtra("overviewMovie", it.overview)
                    intent.putExtra("languageMovie", it.original_language)

                    for (g in listGenre){
                        if (it.genre_ids!!.contains(g.id)){
                            intent.putExtra("genresMovie", g.name)
                        }
                    }

                    startActivity(intent)
                }


            }
        } catch (e: Exception) {
            Log.d("MyDebug", e.toString())
        }


        val recyclerDetails = findViewById<RecyclerView>(R.id.recycler_view_more_movie_details)
        val layoutManager = GridLayoutManager(applicationContext, 3, GridLayoutManager.VERTICAL, false)
        recyclerDetails.layoutManager = layoutManager
        recyclerDetails.adapter = adapter


        posterPatchIntent.let {

            try {
                val textNameMovie: TextView = findViewById(R.id.text_view_title_movie_details)
                textNameMovie.text = nameIntent

                val textDate: TextView = findViewById(R.id.text_view_date_movie_details)
                textDate.text = getString(
                    R.string.date,
                    "${dateIntent?.substring(8, 10)}/${
                        dateIntent?.substring(
                            5,
                            7
                        )
                    }/${dateIntent?.substring(0, 4)}"
                )

                val textLanguage: TextView = findViewById(R.id.text_view_language_movie_details)
                textLanguage.text = getString(R.string.language, languageIntent?.uppercase())

                val textResume: TextView = findViewById(R.id.text_view_resume_movie_details)
                textResume.text = overviewIntent

                val textGenre: TextView = findViewById(R.id.text_view_genre_movie_details)
                textGenre.text = getString(R.string.genre, genresIntent)

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

    override fun onResume() {
        super.onResume()
        val scope = MainScope()
        scope.launch {
            //Faz a chamda na api
            viewModel.getSimilarMovies()
            viewModel.getAllGenre()
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.movieList.observe(this, Observer {
            Log.d("MyDebug", "onCreateMovie: $it")
            adapter.setDataSet(it.results)
            adapter.notifyDataSetChanged()
        })

        viewModel.genreList.observe(this, Observer {
            Log.d("MyDebug", "onCreateGenre: $it")
            for (i in it.genres){
                listGenre.add(i)
            }
        })


    }
}