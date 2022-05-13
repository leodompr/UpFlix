package com.leonardo.desafiostant.interfacesRetro


import android.provider.MediaStore
import com.leonardo.desafiostant.model.Genre
import com.leonardo.desafiostant.model.GenreResponse
import com.leonardo.desafiostant.model.MovieResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("movie/now_playing") //Chamada da Lista de Filmes
    fun getAllMovies(
        @Query("api_key") apiKey: String = "f321a808e68611f41312aa8408531476",
        @Query("language") lang: String = "pt-BR",
        @Query("page") page: Int
    ): retrofit2.Call<MovieResponse>

    @GET("genre/movie/list") //Chamada da lista de Gêneros
    fun getAllGenres(
        @Query("api_key") apiKey: String = "f321a808e68611f41312aa8408531476",
        @Query("language") lang: String = "pt-BR"
    ) : retrofit2.Call<GenreResponse>


    @GET("movie/now_playing") //Lista Secundaria para a DetailsActivity
    fun getSimilarMovies(
        @Query("api_key") apiKey: String = "f321a808e68611f41312aa8408531476",
        @Query("language") lang: String = "pt-BR",
        @Query("page") page: Int = 2
    ) : retrofit2.Call<MovieResponse>



    companion object {
        private val retrofitService : RetrofitService by lazy {  //RetrofitService

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(RetrofitService::class.java)

        }

        fun getInstance() : RetrofitService {
            return retrofitService
        }

    }


}