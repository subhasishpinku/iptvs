package com.bacbpl.iptv.jetStram.data.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
//data class TvChannel(
//    val id: String,
//    val name: String,
//    val logoUrl: String,
//    val streamUrl: String,
//    val category: String = "Live TV"
//)

@Parcelize
data class TvChannel(
    val id: String,
    val name: String,
    val logoUrl: String,
    val streamUrl: String,
    val category: String
) : Parcelable