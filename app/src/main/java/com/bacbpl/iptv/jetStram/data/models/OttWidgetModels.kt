package com.bacbpl.iptv.jetStram.data.models
// com/bacbpl/iptv/jetStram/data/models/OttWidgetModels.kt

import kotlinx.serialization.Serializable

@Serializable
data class OttWidgetResponse(
    val success: Boolean,
    val widgets: List<Widget>
)

@Serializable
data class Widget(
    val name: String,
    val type: String,
    val id: String,
    val data: List<WidgetItem>
)

@Serializable
data class WidgetItem(
    val name: String,
    val format: String,
    val language: String,
    val release_year: Int,
    val ott_provider_name: String,
    val poster_image: String,
    val backdrop_image: String,
    val id: String,
    val ottplay_rating: Double,
    val genre: String,
    val casts: List<Cast>? = null,
    val crews: List<Crew>? = null,
    val ottplay_url: String
)

@Serializable
data class Cast(
    val name: String,
    val poster: String?
)

@Serializable
data class Crew(
    val name: String,
    val poster: String?
)