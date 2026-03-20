package com.bacbpl.iptv.jetStram.presentation.screens.home

// file: com/bacbpl/iptv/jetStram/presentation/screens/home/TvChannelViewModel.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bacbpl.iptv.jetStram.data.entities.TvChannel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvChannelViewModel @Inject constructor() : ViewModel() {

    private val _channels = MutableStateFlow<List<TvChannel>>(emptyList())
    val channels: StateFlow<List<TvChannel>> = _channels.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadChannels()
    }

    fun loadChannels() {
        viewModelScope.launch {
            _isLoading.value = true
            // Simulate loading delay
            delay(500)

            _channels.value = getTvChannels()
            _isLoading.value = false
        }
    }

    private fun getTvChannels(): List<TvChannel> {
        return listOf(
            TvChannel(
                id = "1",
                name = "Amar Bangla",
                logoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYxgzAohREFZtBKn-T6cIiMMRQ0SZORhHoYA&s",
                streamUrl = "http://115.187.41.216:8080/hls/amarbangla/index.m3u8",
                category = "Entertainment"
            ),
            TvChannel(
                id = "2",
                name = "Amar Digital",
                logoUrl = "https://yt3.googleusercontent.com/ytc/AIdro_mF09sq2C17-S7RNo_0Bg4jfZHAPF9JtLHc1YDgzxvWPA=s900-c-k-c0x00ffffff-no-rj",
                streamUrl = "http://115.187.41.216:8080/hls/amardigital/index.m3u8",
                category = "Sports"
            ),
            TvChannel(
                id = "3",
                name = "Montv Bangla",
                logoUrl = "https://jiotvimages.cdn.jio.com/dare_images/images/channel/c455ca0e9fe90ef63458716120b5abd1.png",
                streamUrl = "http://115.187.41.216:8080/hls/montvbangla/index.m3u8",
                category = "Entertainment"
            ),
            TvChannel(
                id = "4",
                name = "Bhakti Bangla",
                logoUrl = "https://static.wikia.nocookie.net/etv-gspn-bangla/images/f/fe/Bangla_Bhakti_logo_%282020%29.png/revision/latest?cb=20230510105504",
                streamUrl = "http://115.187.41.216:8080/hls/bhaktibangla/index.m3u8",
                category = "Religious"
            ),
            TvChannel(
                id = "5",
                name = "Salam Bangla",
                logoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRR0UPvb6_mWHmiqn49ztVC4pmroDSl06d-0g&s",
                streamUrl = "http://115.187.41.216:8080/hls/salambangla/index.m3u8",
                category = "Movies"
            ),
            TvChannel(
                id = "6",
                name = "Digital Fashion",
                logoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5fZsVfjAkwpyK_oetMMtvZAFBCdMnCtzbbA&s",
                streamUrl = "http://115.187.41.216:8080/hls/digitalfashion/index.m3u8",
                category = "Lifestyle"
            ),
            TvChannel(
                id = "7",
                name = "Sananda TV",
                logoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIVSW63OS-YIryP3InB1Bt3QrDxYPYAK9u0A&s",
                streamUrl = "http://115.187.41.216:8080/hls/sanandatv/index.m3u8",
                category = "Entertainment"
            ),
            TvChannel(
                id = "8",
                name = "BBC News",
                logoUrl = "https://m.media-amazon.com/images/M/MV5BNGYwNDlmZDgtMDg1Yi00N2JmLTk0NzQtNWVkN2NiMTQxY2RlXkEyXkFqcGc@._V1_.jpg",
                streamUrl = "https://cdn4.skygo.mn/live/disk1/BBC_News/HLSv3-FTA/BBC_News.m3u8",
                category = "News"
            ),
            TvChannel(
                id = "9",
                name = "Ekhon Kolkata",
                logoUrl = "https://i.ytimg.com/vi/JnC6n7ddxMU/hqdefault.jpg",
                streamUrl = "rtmp://live.dataplayer.in:1935/live/ekhonkolkata",
                category = "News"
            ),
            TvChannel(
                id = "10",
                name = "Inception",
                logoUrl = "http://192.168.1.11:8080/banners/inception/Inception-LeonardoDiCaprio-ChristopherNolan-HollywoodSciFiMoviePoster_66029b94-50ae-494c-b11d-60a3d91268b5.jpg",
                streamUrl = "http://192.168.1.8:8080/vod/vod_inception/inception.mp4",
                category = "Movies"
            ),
            TvChannel(
                id = "11",
                name = "Dhurandar",
                logoUrl = "http://192.168.1.8:8080/banners/dhurandar/dhurandhar1763462432_2.jpeg",
                streamUrl = "http://192.168.1.11:8080/vod/vod_dhurandar/dh1.mp4",
                category = "Movies"
            )
        )
    }

    fun getChannelsByCategory(category: String): List<TvChannel> {
        return _channels.value.filter { it.category == category }
    }

    fun getCategories(): List<String> {
        return _channels.value.map { it.category }.distinct()
    }
}