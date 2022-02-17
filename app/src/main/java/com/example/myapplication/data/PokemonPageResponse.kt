package com.example.myapplication.data

data class PokemonPageResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonItem> = emptyList()
)