package com.bacbpl.iptv.jetStram.data.entities

import android.os.Parcelable
import com.bacbpl.iptv.jetStram.data.models.WidgetItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class OttWidget(
    val id: String,
    val name: String,
    val type: String,
    val items: List<OttWidgetItem>
) : Parcelable

@Parcelize
data class OttWidgetItem(
    val id: String,
    val title: String,
    val format: String,
    val language: String,
    val releaseYear: Int,
    val ottProvider: String,
    val posterUrl: String,
    val backdropUrl: String,
    val rating: Double,
    val genre: String,
    val casts: List<CastInfo> = emptyList(),  // Update to use CastInfo with images
    val ottplayUrl: String = ""
) : Parcelable

@Parcelize
data class CastInfo(
    val name: String,
    val posterUrl: String?  // Cast image URL
) : Parcelable

fun WidgetItem.toOttWidgetItem(): OttWidgetItem = OttWidgetItem(
    id = id,
    title = name,
    format = format,
    language = language,
    releaseYear = release_year,
    ottProvider = ott_provider_name,
    posterUrl = poster_image,
    backdropUrl = backdrop_image,
    rating = ottplay_rating,
    genre = genre,
    casts = casts?.mapNotNull { cast ->
        if (cast.name.isNotEmpty()) {
            CastInfo(
                name = cast.name,
                posterUrl = cast.poster
            )
        } else null
    } ?: emptyList(),
    ottplayUrl = ottplay_url
)