package com.bacbpl.iptv.jetStram.data.repositories

// com/bacbpl/iptv/jetStram/data/repositories/WidgetRepository.kt

import com.bacbpl.iptv.jetStram.data.entities.OttWidget
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WidgetRepository @Inject constructor(
    private val apiWidgetDataSource: ApiWidgetDataSource
) {
    suspend fun getWidgets(): List<OttWidget> {
        return apiWidgetDataSource.getWidgetsFromApi()
    }

    fun getWidgetsFlow(): Flow<List<OttWidget>> {
        return apiWidgetDataSource.getWidgetsFlow()
    }
}