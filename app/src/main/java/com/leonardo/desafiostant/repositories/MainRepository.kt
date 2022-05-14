package com.leonardo.desafiostant.repositories

import com.leonardo.desafiostant.interfacesRetro.RetrofitService


class MainRepository(private val retrofitService: RetrofitService,) {
    fun getAllMovies(page: Int) = retrofitService.getAllMovies(page = page) //MoviesResponse
   fun getAllGenres() = retrofitService.getAllGenres() //GenresResponse
}