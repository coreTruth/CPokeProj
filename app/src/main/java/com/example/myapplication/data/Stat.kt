package com.example.myapplication.data

import com.squareup.moshi.Json
import java.io.Serializable

data class Stat(
    @Json(name = "base_stat")
    val baseStat: Int,
    val effort: Int,
    val stat: CommonItem
): Serializable