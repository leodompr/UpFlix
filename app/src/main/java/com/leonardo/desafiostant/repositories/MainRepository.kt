package com.leonardo.desafiostant.repositories

import com.leonardo.desafiostant.interfacesRetro.RetrofitService


class MainRepository(private val retrofitService: RetrofitService) {
    fun getAllMovie1() = retrofitService.getAllMovies()
   fun getAllGenre2() = retrofitService.getAllGenres()
}