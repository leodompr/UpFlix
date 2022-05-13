package com.leonardo.desafiostant.repositories

import com.leonardo.desafiostant.interfacesRetro.RetrofitService

class DetailsRepository(private val retrofitService: RetrofitService) {
    fun getSimiliarMovies() = retrofitService.getSimilarMovies() //MoviesResponse
    fun getAllGenr() = retrofitService.getAllGenres() //GenresResponse
}