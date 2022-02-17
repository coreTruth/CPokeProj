package com.example.myapplication.data

data class PokemonItem(
    val name: String,
    val url: String
) {
    fun toPokemonItemUI() = PokemonItemUI(name = name, url = url)
}