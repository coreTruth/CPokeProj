package com.example.myapplication.data

import com.squareup.moshi.Json

data class Moves(
    val move: CommonItem,
    @Json(name = "version_group_details")
    val versionGroupDetails: List<VersionGroupDetail> = emptyList(),

)