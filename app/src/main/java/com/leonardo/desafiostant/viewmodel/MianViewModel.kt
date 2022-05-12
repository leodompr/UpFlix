package com.leonardo.desafiostant.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leonardo.desafiostant.model.Genre
import com.leonardo.desafiostant.model.Movie
import com.leonardo.desafiostant.model.MovieResponse
import com.leonardo.desafiostant.repositories.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MianViewModel(private val repository: MainRepository) : ViewModel() {

    val movieList = MutableLiveData<MovieResponse>()
    val genreList = MutableLiveData<Genre>()
    val errorMessage = MutableLiveData<String>()

    fun getAllMovies() {

        val requestt = repository.getAllMovie1()
        requestt.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                //Quando houver resposta
                movieList.postValue(response.body())
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                //Quando houver falha
                errorMessage.postValue(t.message)
            }

        })
    }


        fun getAllGenre(){

            val request3 = repository.getAllGenre2()
            request3.enqueue(object : Callback<Genre>{
                override fun onResponse(call: Call<Genre>, response: Response<Genre>) {
                    genreList.postValue(response.body())
                }

                override fun onFailure(call: Call<Genre>, t: Throwable) {
                    errorMessage.postValue(t.message)
                }

            })


        }


    }


