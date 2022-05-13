package com.leonardo.desafiostant.repositories

import com.leonardo.desafiostant.interfacesRetro.RetrofitService


class MainRepository(private val retrofitService: RetrofitService,) {
    fun getAllMovie1(page: Int) = retrofitService.getAllMovies(page = page) //MoviesResponse
   fun getAllGenr2() = retrofitService.getAllGenres() //GenresResponse
}