package com.leonardo.desafiostant.repositories

import com.leonardo.desafiostant.interfacesRetro.RetrofitService

class CategoryRepository(private val retrofitService: RetrofitService) {
    fun getMoviesCategory(page: Int) = retrofitService.getAllMovies(page = page) //MoviesResponse
    fun getAllGenr() = retrofitService.getAllGenres() //GenresResponse
}

