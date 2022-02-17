package com.example.myapplication.repository.network

import com.example.myapplication.data.PokemonItemDetail
import com.example.myapplication.data.PokemonPageResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import java.util.concurrent.TimeUnit


interface ApiInterface {
    @GET
    suspend fun getPage(@Url url: String?): PokemonPageResponse

    @GET
    suspend fun getItemDetail(@Url url: String?): PokemonItemDetail

    companion object {

        var BASE_URL = "https://pokeapi.co/api/v2/pokemon/"
        fun create(): ApiInterface {
            val okhttpClient = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .followRedirects(false)
                .followSslRedirects(false)
                .apply { addOptionalLogging() }
                .build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okhttpClient)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }

        private fun OkHttpClient.Builder.addOptionalLogging() {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
            addInterceptor(logging)
        }
    }
}