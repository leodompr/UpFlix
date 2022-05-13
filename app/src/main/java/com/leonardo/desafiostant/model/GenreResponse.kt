package com.leonardo.desafiostant.model

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>
)