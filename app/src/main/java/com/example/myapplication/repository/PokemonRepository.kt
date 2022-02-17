package com.example.myapplication.repository

import com.example.myapplication.data.PokemonItemDetail
import com.example.myapplication.data.PokemonPageResponse
import com.example.myapplication.repository.network.ApiInterface
import com.example.myapplication.repository.network.NetworkResource
import com.example.myapplication.repository.network.SafeApiCall

object PokemonRepository: SafeApiCall {
    private val apiInterface = ApiInterface.create()

    suspend fun getPage(url: String?): NetworkResource<PokemonPageResponse> = safeApiCall {
        apiInterface.getPage(url)
    }

    suspend fun getItemDetail(url: String?): NetworkResource<PokemonItemDetail>  = safeApiCall {
        apiInterface.getItemDetail(url)
    }
}