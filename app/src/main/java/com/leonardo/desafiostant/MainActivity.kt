package com.leonardo.desafiostant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.NumberPicker
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RestrictTo
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.leonardo.desafiostant.interfacesRetro.RetrofitService
import com.leonardo.desafiostant.model.Movie

import com.leonardo.desafiostant.repositories.MainRepository
import com.leonardo.desafiostant.ui.DetailsActivity
import com.leonardo.desafiostant.ui.adapter.MovieAdapter


import com.leonardo.desafiostant.viewmodel.MainViewModelFactory
import com.leonardo.desafiostant.viewmodel.MianViewModel
import kotlinx.coroutines.CoroutineScope

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var page : Int = 1
    var list : MutableList<Movie> = mutableListOf()
    var adapter = MovieAdapter{}
    lateinit var viewModel : MianViewModel
    private val retrofitService = RetrofitService.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this@MainActivity, MainViewModelFactory(MainRepository(retrofitService)))[MianViewModel::class.java]
        try {
            adapter = MovieAdapter{
                it.let {
                    intent = Intent(applicationContext, DetailsActivity::class.java)
                    intent.putExtra("posterPatchMovie", it.poster_path)
                    intent.putExtra("nameMovie", it.title)
                    startActivity(intent)
                }


           }
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "ERRO", Toast.LENGTH_LONG).show()
        }


    }



    override fun onResume() {
        super.onResume()
        val scope = MainScope()
        adapter.setDataSet(list)
        scope.launch {
            viewModel.getAllMovies(page)
            viewModel.getAllGenre()
        }


            val recyclerView : RecyclerView = findViewById(R.id.recycler_vie_movie_list)
            val layoutManager = GridLayoutManager(applicationContext, 3, GridLayoutManager.VERTICAL, false)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter


            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy >0){
                        val visibleItemCount: Int = layoutManager.childCount
                        val pastVisibleItem : Int = layoutManager.findFirstCompletelyVisibleItemPosition()
                        val total = adapter.itemCount

                        if (visibleItemCount + pastVisibleItem >= total){
                            if (page < 66){
                                page ++
                            }

                            viewModel.getAllMovies(page)
                            adapter.setDataSet(list)
                            recyclerView.post {
                                adapter.notifyDataSetChanged()
                            }

                        }

                    }

                    super.onScrolled(recyclerView, dx, dy)
                }
            })

            println(viewModel.movieList)

        }







    override fun onStart() {
        super.onStart()

        Log.d("TESTE", "onStart")
        viewModel.movieList.observe(this, Observer {
            Log.d("TAG", "onCreate: $it")
            for (i in it.results){
                list.add(i)
                    }
            if (page < 66){
                page ++
            }

        })


        viewModel.errorMessage.observe(this, Observer {
            Log.d("Observer", "Error: $it")
        })

        viewModel.genreList.observe(this, Observer {
            Log.d("TAG", "genr: $it")
            adapter.setDataGenre(it.genres)
        })




    }



}