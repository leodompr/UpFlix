package com.leonardo.desafiostant.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leonardo.desafiostant.model.GenreResponse
import com.leonardo.desafiostant.model.MovieResponse
import com.leonardo.desafiostant.repositories.DetailsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailsViewModel(private val repository: DetailsRepository) : ViewModel() {
    val movieList = MutableLiveData<MovieResponse>()
    val genreList = MutableLiveData<GenreResponse>()
    val errorMessage = MutableLiveData<String>()

    fun getSimilarMovies() {

        val requestt = repository.getSimiliarMovies()
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
        val request5 = repository.getAllGenr()
        request5.enqueue(object : Callback<GenreResponse>{
            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                genreList.postValue(response.body())
            }

            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })


    }




}