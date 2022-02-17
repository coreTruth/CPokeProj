package com.example.myapplication.data

import com.squareup.moshi.Json

data class GameIndex(
    @Json(name = "game_index")
    val gameIndex: Int,
    val version: CommonItem
)