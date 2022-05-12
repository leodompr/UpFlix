package com.leonardo.desafiostant.model

import com.google.gson.annotations.SerializedName


data class MovieResponse(
    @SerializedName("results")
    var results: MutableList<Movie>,
    @SerializedName("page")
    var page: Int,
    @SerializedName("total_pages")
    var totalPages: Int,
)