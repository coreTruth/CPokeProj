package com.example.myapplication.data

import com.squareup.moshi.Json
import java.io.Serializable

data class Ability(
    val ability: CommonItem,
    @Json(name = "is_hidden")
    val isHidden: Boolean,
    val slot: Int
): Serializable