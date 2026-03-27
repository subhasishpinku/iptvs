package com.bacbpl.iptv.jetStram.data.repositories

import com.bacbpl.iptv.jetStram.data.entities.TvChannel
import com.bacbpl.iptv.jetStram.data.network.ApiService
import jakarta.inject.Inject
import jakarta.inject.Singleton

// ChannelRepository.kt
@Singleton
class ChannelRepository @Inject constructor(
    private val apiService: ApiService
) {
    private var cachedChannels: List<TvChannel>? = null

    suspend fun getChannels(): List<TvChannel> {
        if (cachedChannels == null) {
            cachedChannels = apiService.getTvChannels()
        }
        return cachedChannels ?: emptyList()
    }

    fun getCachedChannels(): List<TvChannel> {
        return cachedChannels ?: emptyList()
    }

    fun getChannelById(id: Int): TvChannel? {
        return cachedChannels?.find { it.id == id }
    }
}