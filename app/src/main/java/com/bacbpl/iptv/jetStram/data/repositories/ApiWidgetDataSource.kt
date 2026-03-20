//package com.bacbpl.iptv.jetStram.data.repositories
//
//// com/bacbpl/iptv/jetStram/data/repositories/ApiWidgetDataSource.kt
//
//import com.bacbpl.iptv.jetStram.data.entities.OttWidget
//import com.bacbpl.iptv.jetStram.data.entities.toOttWidgetItem
//import com.bacbpl.iptv.jetStram.data.models.OttWidgetResponse
//import com.bacbpl.iptv.jetStram.data.network.ApiService
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Singleton
//class ApiWidgetDataSource @Inject constructor(
//    private val apiService: ApiService
//) {
//    suspend fun getWidgetsFromApi(): List<OttWidget> {
//        return try {
//            val response = apiService.getOttWidgets()
//            if (response.success) {
//                response.widgets.map { widget ->
//                    OttWidget(
//                        id = widget.id,
//                        name = widget.name,
//                        type = widget.type,
//                        items = widget.data.map { it.toOttWidgetItem() }
//                    )
//                }
//            } else {
//                emptyList()
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            emptyList()
//        }
//    }
//
//    fun getWidgetsFlow(): Flow<List<OttWidget>> = flow {
//        emit(getWidgetsFromApi())
//    }
//}

package com.bacbpl.iptv.jetStram.data.repositories

import android.util.Log
import com.bacbpl.iptv.jetStram.data.entities.OttWidget
import com.bacbpl.iptv.jetStram.data.entities.OttWidgetItem
import com.bacbpl.iptv.jetStram.data.entities.toOttWidgetItem
import com.bacbpl.iptv.jetStram.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiWidgetDataSource @Inject constructor(
    private val apiService: ApiService
) {
    // Cache for widgets
    private var cachedWidgets: List<OttWidget> = emptyList()
    private var cachedItems: List<OttWidgetItem> = emptyList()

    suspend fun getWidgetsFromApi(): List<OttWidget> {
        // Return cached data if available
        if (cachedWidgets.isNotEmpty()) {
            return cachedWidgets
        }

        return try {
            val response = apiService.getOttWidgets()
            Log.d("WidgetAPI", "Response success: ${response.success}, widgets: ${response.widgets.size}")

            if (response.success) {
                cachedWidgets = response.widgets.map { widget ->
                    OttWidget(
                        id = widget.id,
                        name = widget.name,
                        type = widget.type,
                        items = widget.data.map { it.toOttWidgetItem() }
                    )
                }
                cachedItems = cachedWidgets.flatMap { it.items }
                cachedWidgets
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("WidgetAPI", "Error fetching widgets: ${e.message}")
            e.printStackTrace()
            emptyList()
        }
    }

    fun getCachedWidgetItems(): List<OttWidgetItem> = cachedItems

    fun getWidgetsFlow(): Flow<List<OttWidget>> = flow {
        emit(getWidgetsFromApi())
    }
}