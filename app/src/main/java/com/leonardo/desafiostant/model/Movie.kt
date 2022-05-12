package com.leonardo.desafiostant.model



data class Movie(
    val title: String,
    val poster_path: String,
    val genre_ids: ArrayList<Int>? = null,
    val release_date: String,
    val backdrop_path: String,
    val original_language: String,
    val overview: String,
)
