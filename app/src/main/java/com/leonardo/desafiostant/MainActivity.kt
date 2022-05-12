package com.leonardo.desafiostant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.Resource
import com.leonardo.desafiostant.interfacesRetro.RetrofitService
import com.leonardo.desafiostant.model.MovieResponse
import com.leonardo.desafiostant.repositories.MainRepository
import com.leonardo.desafiostant.ui.adapter.CategoryAdapter
import com.leonardo.desafiostant.ui.adapter.MovieAdapter
import com.leonardo.desafiostant.viewmodel.MainViewModelFactory
import com.leonardo.desafiostant.viewmodel.MianViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var adapter = MovieAdapter()
    private var adapter2 = CategoryAdapter()
    lateinit var viewModel : MianViewModel
    private val retrofitService = RetrofitService.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this@MainActivity, MainViewModelFactory(MainRepository(retrofitService))).get(MianViewModel::class.java)
        val recyclerView : RecyclerView = findViewById(R.id.recycler_vie_movie_list)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        println(viewModel.movieList)


    }



    override fun onResume() {
        super.onResume()
        viewModel.getAllMovies()

        Log.d("TESTE", viewModel.genreList.value.toString())


    }

    override fun onStart() {
        super.onStart()
        Log.d("TESTE", "onStart")

        viewModel.movieList.observe(this, Observer {
            Log.d("TAG", "onCreate: $it")
            adapter.setDataSet(it.results)
            adapter2.setDataSet(it.results)
        })

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            Log.d("TAG", "onCreate: $it")
        })




    }



}