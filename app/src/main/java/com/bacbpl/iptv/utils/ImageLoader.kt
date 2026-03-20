package com.bacbpl.iptv.utils

import android.widget.ImageView
import coil.load
import coil.transform.RoundedCornersTransformation

object ImageLoader {
    private const val BASE_IMAGE_URL = "http://192.168.1.11/ott-icons/"

    fun loadOttIcon(imageView: ImageView, thumbnailName: String) {
        val imageUrl = BASE_IMAGE_URL + thumbnailName
        imageView.load(imageUrl) {
            crossfade(true)
            transformations(RoundedCornersTransformation(8f))
            error(android.R.drawable.ic_menu_gallery) // Placeholder on error
        }
    }
}