package com.example.myapplication.data

import com.google.gson.internal.LinkedTreeMap
import com.squareup.moshi.Json

data class PokemonItemDetail(
    val abilities: List<Ability> = emptyList(),
    @Json(name = "base_experience")
    val baseExperience: Int,
    val forms: List<CommonItem> = emptyList(),
    @Json(name = "game_indices")
    val gameIndices: List<GameIndex> = emptyList(),
    val height: Int,
    @Json(name = "held_items")
    val heldItems: List<Any> = emptyList(),
    val id: Int,
    @Json(name = "is_default")
    val isDefault: Boolean,
    @Json(name = "location_area_encounters")
    val locationAreaEncounters: String?,
    val moves: List<Moves> = emptyList(),
    val name: String?,
    val order: Int,
    @Json(name = "past_bytes")
    val pastBytes: List<Any> = emptyList(),
    val species: CommonItem,
    val sprites: LinkedTreeMap<String, Any>,
    val stats: List<Stat> = emptyList(),
    val types: List<Type> = emptyList(),
    val weight: Int
) {
    fun getIconUrl(): String? {
        for ((_, value) in sprites) {
            if (value != null && value is String) {
                return value
            }
        }
        return null
    }

    fun getTypesString(): String {
        val typesString: StringBuilder = StringBuilder()
        for (type in types) {
            typesString.append(type.type.name, ", ")
        }
        return typesString.substring(0, typesString.length - 2)
    }
}