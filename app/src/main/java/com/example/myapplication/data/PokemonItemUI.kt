package com.example.myapplication.data

import java.io.Serializable

data class PokemonItemUI(
    val name: String? = null,
    val url: String? = null,
    var icon: String? = null,
    var ability: List<Ability> = emptyList(),
    var stats: List<Stat> = emptyList(),
    var typeString: String? = null,
    var isLoadMoreButton: Boolean = false
): Serializable  {
    fun getStatValue(statName: String) =
        stats.firstOrNull { stat -> stat.stat.name == statName }
}