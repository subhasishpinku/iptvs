package com.bacbpl.iptv.jetStram.presentation.screens.home

// file: com/bacbpl/iptv/jetStram/presentation/screens/home/TvChannelViewModel.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bacbpl.iptv.data.api.RetrofitClient.apiService
import com.bacbpl.iptv.jetStram.data.entities.TvChannel
import com.bacbpl.iptv.jetStram.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvChannelViewModel @Inject constructor(
    private val apiService: ApiService

) : ViewModel() {

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
            try {
                // Fetch channels from the API
                val apiChannels = apiService.getTvChannels()
                _channels.value = apiChannels
            } catch (e: Exception) {
                // Handle error - maybe show error state or fallback to local data
                e.printStackTrace()
                _channels.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

//    fun loadChannels() {
//        viewModelScope.launch {
//            _isLoading.value = true
//            // Simulate loading delay
//            delay(500)
//
//            _channels.value = getTvChannels()
//            _isLoading.value = false
//        }
//    }
    private fun getTvChannels1(): List<TvChannel> {
        return listOf(
            TvChannel(
                id = 1,
                name = "Amar Bangla",
                logoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYxgzAohREFZtBKn-T6cIiMMRQ0SZORhHoYA&s",
                streamUrl = "http://115.187.41.216:8080/hls/amarbangla/index.m3u8",
                category = "Entertainment"
            ),
            TvChannel(
                id = 2,
                name = "Amar Digital",
                logoUrl = "https://yt3.googleusercontent.com/ytc/AIdro_mF09sq2C17-S7RNo_0Bg4jfZHAPF9JtLHc1YDgzxvWPA=s900-c-k-c0x00ffffff-no-rj",
                streamUrl = "http://115.187.41.216:8080/hls/amardigital/index.m3u8",
                category = "Sports"
            ),
            TvChannel(
                id = 3,
                name = "Montv Bangla",
                logoUrl = "https://jiotvimages.cdn.jio.com/dare_images/images/channel/c455ca0e9fe90ef63458716120b5abd1.png",
                streamUrl = "http://115.187.41.216:8080/hls/montvbangla/index.m3u8",
                category = "Entertainment"
            ),
            TvChannel(
                id = 4,
                name = "Bhakti Bangla",
                logoUrl = "https://static.wikia.nocookie.net/etv-gspn-bangla/images/f/fe/Bangla_Bhakti_logo_%282020%29.png/revision/latest?cb=20230510105504",
                streamUrl = "http://115.187.41.216:8080/hls/bhaktibangla/index.m3u8",
                category = "Religious"
            ),
            TvChannel(
                id = 5,
                name = "Salam Bangla",
                logoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRR0UPvb6_mWHmiqn49ztVC4pmroDSl06d-0g&s",
                streamUrl = "http://115.187.41.216:8080/hls/salambangla/index.m3u8",
                category = "Movies"
            ),
            TvChannel(
                id = 6,
                name = "Digital Fashion",
                logoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5fZsVfjAkwpyK_oetMMtvZAFBCdMnCtzbbA&s",
                streamUrl = "http://115.187.41.216:8080/hls/digitalfashion/index.m3u8",
                category = "Lifestyle"
            ),
            TvChannel(
                id = 7,
                name = "Sananda TV",
                logoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIVSW63OS-YIryP3InB1Bt3QrDxYPYAK9u0A&s",
                streamUrl = "http://115.187.41.216:8080/hls/sanandatv/index.m3u8",
                category = "Entertainment"
            ),
            TvChannel(
                id = 8,
                name = "BBC News",
                logoUrl = "https://m.media-amazon.com/images/M/MV5BNGYwNDlmZDgtMDg1Yi00N2JmLTk0NzQtNWVkN2NiMTQxY2RlXkEyXkFqcGc@._V1_.jpg",
                streamUrl = "https://cdn4.skygo.mn/live/disk1/BBC_News/HLSv3-FTA/BBC_News.m3u8",
                category = "News"
            ),
            TvChannel(
                id = 9,
                name = "Ekhon Kolkata",
                logoUrl = "https://i.ytimg.com/vi/JnC6n7ddxMU/hqdefault.jpg",
                streamUrl = "rtmp://live.dataplayer.in:1935/live/ekhonkolkata",
                category = "News"
            ),
            TvChannel(
                id = 10,
                name = "Inception",
                logoUrl = "http://iptv.yogayog.net/banners/inception/Inception-LeonardoDiCaprio-ChristopherNolan-HollywoodSciFiMoviePoster_66029b94-50ae-494c-b11d-60a3d91268b5.jpg",
                streamUrl = "http://192.168.1.8:8080/vod/vod_inception/inception.mp4",
                category = "Movies"
            ),
            TvChannel(
                id = 11,
                name = "Dhurandar",
                logoUrl = "http://iptv.yogayog.net/banners/dhurandar/dhurandhar1763462432_2.jpeg",
                streamUrl = "http://iptv.yogayog.net/vod/vod_dhurandar/dh1.mp4",
                category = "Movies"
            ),
            TvChannel(
                id = 12,
                name = "Kanali 7 Ⓢ",
                logoUrl = "https://i.imgur.com/rL2v9pM.png",
                streamUrl = "https://fe.tring.al/delta/105/out/u/1200_1.m3u8",
                category = "Albania,Kanali"
            ),
            TvChannel(
                id = 13,
                name = "A2 CNN Albania",
                logoUrl = "https://i.imgur.com/TgO3Lzi.png",
                streamUrl = "https://tv.a2news.com/live/smil:a2cnnweb.stream.smil/playlist.m3u8",
                category = "Albania,A2"
            ),
            TvChannel(
                id = 14,
                name = "AlbKanale Music TV Ⓢ",
                logoUrl = "https://i.imgur.com/JdKxscs.png",
                streamUrl = "https://albportal.net/albkanalemusic.m3u8",
                category = "Albania,AlbKanale"
            ),
            TvChannel(
                id = 15,
                name = "Alpo TV",
                logoUrl = "https://i.imgur.com/Pr4ixiA.png",
                streamUrl = "https://5d00db0e0fcd5.streamlock.net/7236/7236/playlist.m3u8",
                category = "Albania,Alpo"
            ),
            TvChannel(
                id = 16,
                name = "CNA",
                logoUrl = "https://i.imgur.com/X3ukD5t.png",
                streamUrl = "https://live1.mediadesk.al/cnatvlive.m3u8",
                category = "Albania,CNA"
            ),
            TvChannel(
                id = 17,
                name = "News 24 Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/News_24_%28Albania%29.svg/1024px-News_24_%28Albania%29.svg.png",
                streamUrl = "https://tv.balkanweb.com/news24/livestream/playlist.m3u8",
                category = "Albania,News"
            ),
            TvChannel(
                id = 18,
                name = "Ora News",
                logoUrl = "https://i.imgur.com/ILZY5bJ.png",
                streamUrl = "https://live1.mediadesk.al/oranews.m3u8",
                category = "Albania,Ora"
            ),
            TvChannel(
                id = 19,
                name = "Panorama TV Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/24/Panorama_logo.svg/512px-Panorama_logo.svg.png",
                streamUrl = "http://198.244.188.94/panorama/livestream/playlist.m3u8",
                category = "Albania,Panorama"
            ),
            TvChannel(
                id = 20,
                name = "Report TV",
                logoUrl = "https://i.imgur.com/yuRDJYY.png",
                streamUrl = "https://deb10stream.duckdns.org/hls/stream.m3u8",
                category = "Albania,Report"
            ),
            TvChannel(
                id = 21,
                name = "Syri",
                logoUrl = "https://i.imgur.com/4zVyj1M.png",
                streamUrl = "https://stream.syritv.al/SyriTV/index.m3u8",
                category = "Albania,Syri"
            ),
            TvChannel(
                id = 22,
                name = "Tropoja",
                logoUrl = "https://i.imgur.com/D3hNOVS.png",
                streamUrl = "https://live.prostream.al/al/smil:tropojatv.smil/playlist.m3u8",
                category = "Albania,Tropoja"
            ),
            TvChannel(
                id = 23,
                name = "TV 7 Albania",
                logoUrl = "https://i.imgur.com/k9WqPLZ.png",
                streamUrl = "https://5d00db0e0fcd5.streamlock.net/7064/7064/playlist.m3u8",
                category = "Albania,TV"
            ),
            TvChannel(
                id = 24,
                name = "Vizion Plus",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fc/Vizion_Plus.svg/512px-Vizion_Plus.svg.png",
                streamUrl = "https://fe.tring.al/delta/105/out/u/rdghfhsfhfshs.m3u8",
                category = "Albania,Vizion"
            ),
            TvChannel(
                id = 25,
                name = "Andorra TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/3/32/Logo_Andorra_Televisi%C3%B3.png",
                streamUrl = "https://videos.rtva.ad/live/rtva/playlist.m3u8",
                category = "Andorra,Andorra"
            ),
            TvChannel(
                id = 26,
                name = "Aunar",
                logoUrl = "http://tvabierta.weebly.com/uploads/5/1/3/4/51344345/aunar.png",
                streamUrl = "https://5fb24b460df87.streamlock.net/live-cont.ar/mirador/playlist.m3u8",
                category = "Argentina,Aunar"
            ),
            TvChannel(
                id = 27,
                name = "Cine.AR",
                logoUrl = "https://i.imgur.com/RPLyrIC.png",
                streamUrl = "https://5fb24b460df87.streamlock.net/live-cont.ar/cinear/playlist.m3u8",
                category = "Argentina,Cine.AR"
            ),
            TvChannel(
                id = 28,
                name = "Tec TV",
                logoUrl = "https://i.imgur.com/EGCq1wc.png",
                streamUrl = "https://tv.initium.net.ar:3939/live/tectvmainlive.m3u8",
                category = "Argentina,Tec"
            ),
            TvChannel(
                id = 29,
                name = "DeporTV",
                logoUrl = "https://i.imgur.com/iyYLNRt.png",
                streamUrl = "https://5fb24b460df87.streamlock.net/live-cont.ar/deportv/playlist.m3u8",
                category = "Argentina,DeporTV"
            ),
            TvChannel(
                id = 30,
                name = "Canal E",
                logoUrl = "https://i.ibb.co/y4pkxH3/Qtc8-M2-PG-400x400.jpg",
                streamUrl = "https://unlimited1-us.dps.live/perfiltv/perfiltv.smil/perfiltv/livestream2/chunks.m3u8",
                category = "Argentina,Canal"
            ),
            TvChannel(
                id = 31,
                name = "Telemax",
                logoUrl = "https://i.imgur.com/gfX0hdB.png",
                streamUrl = "https://live-edge01.telecentro.net.ar/live/smil:tlx.smil/playlist.m3u8",
                category = "Argentina,Telemax"
            ),
            TvChannel(
                id = 32,
                name = "Net TV",
                logoUrl = "https://i.imgur.com/EWmshtx.png",
                streamUrl = "https://unlimited1-us.dps.live/nettv/nettv.smil/playlist.m3u8",
                category = "Argentina,Net"
            ),
            TvChannel(
                id = 33,
                name = "TV Universidad",
                logoUrl = "https://i.imgur.com/tvLHiAT.png",
                streamUrl = "https://stratus.stream.cespi.unlp.edu.ar/hls/tvunlp.m3u8",
                category = "Argentina,TV"
            ),
            TvChannel(
                id = 34,
                name = "Urbana Tevé",
                logoUrl = "https://yt3.ggpht.com/ytc/AKedOLQLeFMWMeoumi-o24ohLPXSEdNL5-oJ9W5oP5KnnA=s900-c-k-c0x00ffffff-no-rj",
                streamUrl = "https://cdnhd.iblups.com/hls/DD3nXkAkWk.m3u8",
                category = "Argentina,Urbana"
            ),
            TvChannel(
                id = 35,
                name = "Comarca TV",
                logoUrl = "http://directostv.teleame.com/wp-content/uploads/2020/10/Comarca-TV-en-vivo-Online.png",
                streamUrl = "https://video-weaver.ord56.hls.ttvnw.net/v1/playlist/Cq4FFLOPxq44Qy0kxjcr_wXuRRwjKU6gJ80Tc5eWRkaz5XdeNfeq7DzoNAstinu7BLtnw7jYDANX5yCDozdlS55_kYfuDyqQhwF0JpzU75lDUv5NOuUVWBwlP0m1Bh1JMn21v_OvA_teSCI7hcIFl2DoINrr8bHnpSS8EUvXNesfdX_LcfQH-clqftQ0sCreLIbTvxI5MyL6Wnm8jGh2yg48A2c_k9YHDH6TpM3tLo-pbRrrfByqMq-IoMQREf5DV_sTfHtAzinQQU3Np309s2ScJwcBlAjzz4VJ7svuJ1UbWBBylenSTnrXQW5Vws7OqG2kcoAHHpAQN6BQCpxaXO6dE7MLg7Nq6E7961fHEVSqFCRQg2Y5orbSsEwMo7_S4s5POZSQX1tn04QDuHfLixx-aBcbctSSFmI1CNZ3YDCEZVvWHOU6SyKQcsZeJCFLA1DsxqY3DeAn6n7xpPS4pdsizKqlqsT66liv_L-48h7jGujNZPDHge0fcgUs_7b7jlKJlKv-S_s1mGgLqPEUEQ8dhn5AugB0kfjAGgcTHIP_qLPRY0M7OikuBaMxh1pUpPkVG9AX4jCg1cwCgcfa64OP9ed4pwMjoiKm0dIfytTQ8hWjwXtkv16nJDfZ3kkqaMnM8ErZG6tRw-JVy3nRooKubY7Re0Fc1RHB85_eX2NYQD88N7r-UgaiO2ax0FQ-E_IdfuwSmp5oDy6Di6pT7r_zDRN3znbV64s4i-INUg-D2Leeq4xqYhe_Zhj-3AoxXDWCB0-DCc1kFEComkNpY01YA19zEm01TKLCbdaJwdC6bQpSEoJkOCoPAMnxm7oPNlqZRbhM4TVCJ34f4Y1guzaDomcEABl4Cii2UF-OhskRUZF-SUWjWdO6z62UqZagzG2QOTr6VBH-ItT_16fLsRUaDGTVKzgYHT8XcSqTkyABKgl1cy1lYXN0LTEwjQY.m3u8",
                category = "Argentina,Comarca"
            ),
            TvChannel(
                id = 36,
                name = "El Trece",
                logoUrl = "https://i.imgur.com/ZK7AQFg.png",
                streamUrl = "https://live-01-02-eltrece.vodgc.net/eltrecetv/index.m3u8",
                category = "Argentina,El"
            ),
            TvChannel(
                id = 37,
                name = "El Nueve",
                logoUrl = "https://i.imgur.com/EtcVSm4.png",
                streamUrl = "https://octubre-live.cdn.vustreams.com/live/channel09/live.isml/live.m3u8",
                category = "Argentina,El"
            ),
            TvChannel(
                id = 38,
                name = "Telefe Ⓨ",
                logoUrl = "https://i.imgur.com/wrZfMXn.png",
                streamUrl = "https://telefe.com/Api/Videos/GetSourceUrl/694564/0/HLS?.m3u8",
                category = "Argentina,Telefe"
            ),
            TvChannel(
                id = 39,
                name = "Armenia 1",
                logoUrl = "https://i.imgur.com/HIwJ4lc.png",
                streamUrl = "https://amtv1.livestreamingcdn.com/am2abr/index.m3u8",
                category = "Armenia,Armenia"
            ),
            TvChannel(
                id = 40,
                name = "Kentron TV Ⓢ",
                logoUrl = "https://i.imgur.com/eCaxBFn.png",
                streamUrl = "https://gineu9.bozztv.com/gin-36bay2/gin-kentron/tracks-v1a1/mono.m3u8",
                category = "Armenia,Kentron"
            ),
            TvChannel(
                id = 41,
                name = "Armenia TV Ⓢ",
                logoUrl = "https://i.imgur.com/UnoI5uM.png",
                streamUrl = "https://cdn.hayastantv.com:8088/armenia/tracks-v1a1/mono.m3u8",
                category = "Armenia,Armenia"
            ),
            TvChannel(
                id = 42,
                name = "5TV Ⓢ",
                logoUrl = "https://i.imgur.com/jOGZZDo.png",
                streamUrl = "https://cdn.hayastantv.com:8088/5tv/tracks-v1a1/mono.m3u8",
                category = "Armenia,5TV"
            ),
            TvChannel(
                id = 43,
                name = "TVSN",
                logoUrl = "https://i.imgur.com/p3QCBOo.png",
                streamUrl = "https://tvsn-i.akamaihd.net/hls/live/261837/tvsn/tvsn_750.m3u8",
                category = "Australia,TVSN"
            ),
            TvChannel(
                id = 44,
                name = "ABC News",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/d/df/ABC_News_Channel.svg/640px-ABC_News_Channel.svg.png",
                streamUrl = "https://abc-iview-mediapackagestreams-2.akamaized.net/out/v1/6e1cc6d25ec0480ea099a5399d73bc4b/index.m3u8",
                category = "Australia,ABC"
            ),
            TvChannel(
                id = 45,
                name = "M4TV",
                logoUrl = "https://i.imgur.com/HZohlNk.png",
                streamUrl = "https://5a32c05065c79.streamlock.net/live/stream/playlist.m3u8",
                category = "Australia,M4TV"
            ),
            TvChannel(
                id = 46,
                name = "Racing.com",
                logoUrl = "https://i.imgur.com/pma0OCf.png",
                streamUrl = "https://racingvic-i.akamaized.net/hls/live/598695/racingvic/1500.m3u8",
                category = "Australia,Racing.com"
            ),
            TvChannel(
                id = 47,
                name = "9Go! Ⓖ",
                logoUrl = "https://i.imgur.com/1CFGu5O.png",
                streamUrl = "https://9now-livestreams.akamaized.net/hls/live/2008312/go-syd/master.m3u8",
                category = "Australia,9Go!"
            ),
            TvChannel(
                id = 48,
                name = "9Life Ⓖ",
                logoUrl = "https://i.imgur.com/ZCUiqlL.png",
                streamUrl = "https://9now-livestreams.akamaized.net/hls/live/2008313/life-syd/master.m3u8",
                category = "Australia,9Life"
            ),
            TvChannel(
                id = 49,
                name = "9Rush Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/c/c2/Logo_of_9RUSH.png",
                streamUrl = "https://9now-livestreams.akamaized.net/hls/live/2010626/rush-syd/master.m3u8",
                category = "Australia,9Rush"
            ),
            TvChannel(
                id = 50,
                name = "ORF 1 Ⓖ",
                logoUrl = "https://i.imgur.com/ft2LuRl.jpg",
                streamUrl = "https://orf1.mdn.ors.at/out/u/orf1/q8c/manifest.m3u8",
                category = "Austria,ORF"
            ),
            TvChannel(
                id = 51,
                name = "ORF 2 Ⓖ",
                logoUrl = "https://i.imgur.com/yPVDaXv.png",
                streamUrl = "https://orf2.mdn.ors.at/out/u/orf2/q8c/manifest.m3u8",
                category = "Austria,ORF"
            ),
            TvChannel(
                id = 52,
                name = "ORF III Ⓖ",
                logoUrl = "https://i.imgur.com/6BuiUE7.png",
                streamUrl = "https://orf3.mdn.ors.at/out/u/orf3/q8c/manifest.m3u8",
                category = "Austria,ORF"
            ),
            TvChannel(
                id = 53,
                name = "ORF Sport + Ⓖ",
                logoUrl = "https://i.imgur.com/MVNZ4gf.png",
                streamUrl = "https://orfs.mdn.ors.at/out/u/orfs/q8c/manifest.m3u8",
                category = "Austria,ORF"
            ),
            TvChannel(
                id = 54,
                name = "Servus TV Ⓖ",
                logoUrl = "https://i.imgur.com/zDWhSxq.png",
                streamUrl = "https://rbmn-live.akamaized.net/hls/live/2002825/geoSTVATweb/master.m3u8",
                category = "Austria,Servus"
            ),
            TvChannel(
                id = 55,
                name = "oe24",
                logoUrl = "https://i.imgur.com/8UTkcPn.png",
                streamUrl = "https://varoe24live.sf.apa.at/oe24-live1/oe24.smil/chunklist_b1900000.m3u8",
                category = "Austria,oe24"
            ),
            TvChannel(
                id = 56,
                name = "W24",
                logoUrl = "https://i.imgur.com/PGb4wYw.png",
                streamUrl = "https://ms01.w24.at/W24/smil:liveevent.smil/playlist.m3u8",
                category = "Austria,W24"
            ),
            TvChannel(
                id = 57,
                name = "P3TV",
                logoUrl = "https://i.imgur.com/1sPhZ57.png",
                streamUrl = "http://p3-6.mov.at:1935/live/weekstream/playlist.m3u8",
                category = "Austria,P3TV"
            ),
            TvChannel(
                id = 58,
                name = "RTV",
                logoUrl = "https://i.imgur.com/oD7GQxT.png",
                streamUrl = "http://iptv.rtv-ooe.at/stream.m3u8",
                category = "Austria,RTV"
            ),
            TvChannel(
                id = 59,
                name = "RTS Ⓖ",
                logoUrl = "https://i.imgur.com/Bhv7lvy.png",
                streamUrl = "https://58b42f6c8c9bf.streamlock.net:8080/live/RTS2015/playlist.m3u8",
                category = "Austria,RTS"
            ),
            TvChannel(
                id = 60,
                name = "Tirol TV Ⓖ",
                logoUrl = "https://i.imgur.com/1E7Nflo.jpg",
                streamUrl = "http://lb.hd-livestream.de:1935/live/TirolTV/playlist.m3u8",
                category = "Austria,Tirol"
            ),
            TvChannel(
                id = 61,
                name = "R9",
                logoUrl = "https://i.imgur.com/2fxVYsL.jpg",
                streamUrl = "https://ms01.w24.at/R9/smil:liveeventR9.smil/playlist.m3u8",
                category = "Austria,R9"
            ),
            TvChannel(
                id = 62,
                name = "ARB 24",
                logoUrl = "https://i.imgur.com/mtvIFyq.png",
                streamUrl = "http://85.132.81.184:8080/arb/live/index.m3u8",
                category = "Azerbaijan,ARB"
            ),
            TvChannel(
                id = 63,
                name = "ARB Ⓢ",
                logoUrl = "https://i.imgur.com/E97M2OL.png",
                streamUrl = "http://109.205.166.68/server124/arb/index.m3u8",
                category = "Azerbaijan,ARB"
            ),
            TvChannel(
                id = 64,
                name = "AzStarTV",
                logoUrl = "https://i.imgur.com/di3XX5L.png",
                streamUrl = "http://live.azstartv.com/azstar/smil:azstar.smil/playlist.m3u8",
                category = "Azerbaijan,AzStarTV"
            ),
            TvChannel(
                id = 65,
                name = "Baku TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3b/Baku_TV_%282018%29.png/640px-Baku_TV_%282018%29.png",
                streamUrl = "https://rtmp.baku.tv/live/bakutv_720p.m3u8",
                category = "Azerbaijan,Baku"
            ),
            TvChannel(
                id = 66,
                name = "CBC",
                logoUrl = "https://i.imgur.com/wVT0dwO.png",
                streamUrl = "https://stream.cbctv.az:5443/LiveApp/streams/cbctv.m3u8",
                category = "Azerbaijan,CBC"
            ),
            TvChannel(
                id = 67,
                name = "CBC Sport Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/az/0/04/CBC_Sport_TV_loqo.png",
                streamUrl = "https://mn-nl.mncdn.com/cbcsports_live/cbcsports/playlist.m3u8",
                category = "Azerbaijan,CBC"
            ),
            TvChannel(
                id = 68,
                name = "İctimai TV Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/%C4%B0ctimai_TV_%282021-h.h.%29.svg/470px-%C4%B0ctimai_TV_%282021-h.h.%29.svg.png",
                streamUrl = "http://109.205.166.68/server124/ictimai_tv/index.m3u8",
                category = "Azerbaijan,İctimai"
            ),
            TvChannel(
                id = 69,
                name = "İdman TV Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/az/thumb/8/88/%C4%B0dman_Az%C9%99rbaycan_TV_loqo_%282019-h.h.%29.png/640px-%C4%B0dman_Az%C9%99rbaycan_TV_loqo_%282019-h.h.%29.png",
                streamUrl = "http://109.205.166.68/server124/idman_az/index.m3u8",
                category = "Azerbaijan,İdman"
            ),
            TvChannel(
                id = 70,
                name = "Mədəniyyət TV Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/f/fc/M%C9%99d%C9%99niyy%C9%99t_TV_logo.png",
                streamUrl = "https://str.yodacdn.net/medeniyyet/index.m3u8",
                category = "Azerbaijan,Mədəniyyət"
            ),
            TvChannel(
                id = 71,
                name = "Space TV Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/24/Space_TV_loqosu_%282023-h.h.%29.png/296px-Space_TV_loqosu_%282023-h.h.%29.png",
                streamUrl = "http://109.205.166.68/server124/space_tv/index.m3u8",
                category = "Azerbaijan,Space"
            ),
            TvChannel(
                id = 72,
                name = "Беларусь 1",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Belarus_1_logo.svg/742px-Belarus_1_logo.svg.png",
                streamUrl = "https://ngtrk.dc.beltelecom.by/ngtrk/smil:belarus1.smil/playlist.m3u8",
                category = "Belarus,Беларусь"
            ),
            TvChannel(
                id = 73,
                name = "Беларусь 2",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c0/Belarus_2_logo.svg/742px-Belarus_2_logo.svg.png",
                streamUrl = "https://ngtrk.dc.beltelecom.by/ngtrk/smil:belarus2.smil/playlist.m3u8",
                category = "Belarus,Беларусь"
            ),
            TvChannel(
                id = 74,
                name = "Беларусь 3",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Belarus_3_logo.svg/742px-Belarus_3_logo.svg.png",
                streamUrl = "https://ngtrk.dc.beltelecom.by/ngtrk/smil:belarus3.smil/playlist.m3u8",
                category = "Belarus,Беларусь"
            ),
            TvChannel(
                id = 75,
                name = "ОНТ Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3d/%D0%9B%D0%BE%D0%B3%D0%BE%D1%82%D0%B8%D0%BF_%D1%82%D0%B5%D0%BB%D0%B5%D0%BA%D0%B0%D0%BD%D0%B0%D0%BB%D0%B0_%C2%AB%D0%9E%D0%9D%D0%A2%C2%BB.svg/991px-%D0%9B%D0%BE%D0%B3%D0%BE%D1%82%D0%B8%D0%BF_%D1%82%D0%B5%D0%BB%D0%B5%D0%BA%D0%B0%D0%BD%D0%B0%D0%BB%D0%B0_%C2%AB%D0%9E%D0%9D%D0%A2%C2%BB.svg.png",
                streamUrl = "https://stream.dc.beltelecom.by/ont/ont/playlist.m3u8",
                category = "Belarus,ОНТ"
            ),
            TvChannel(
                id = 76,
                name = "Беларусь 5",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/71/Belarus_5_logo.svg/742px-Belarus_5_logo.svg.png",
                streamUrl = "https://ngtrk.dc.beltelecom.by/ngtrk/smil:belarus5.smil/playlist.m3u8",
                category = "Belarus,Беларусь"
            ),
            TvChannel(
                id = 77,
                name = "СТВ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/33/%D0%9B%D0%BE%D0%B3%D0%BE%D1%82%D0%B8%D0%BF_%D0%B1%D0%B5%D0%BB%D0%BE%D1%80%D1%83%D1%81%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D1%82%D0%B5%D0%BB%D0%B5%D0%BA%D0%B0%D0%BD%D0%B0%D0%BB%D0%B0_%C2%AB%D0%A1%D0%A2%D0%92%C2%BB.svg/640px-%D0%9B%D0%BE%D0%B3%D0%BE%D1%82%D0%B8%D0%BF_%D0%B1%D0%B5%D0%BB%D0%BE%D1%80%D1%83%D1%81%D1%81%D0%BA%D0%BE%D0%B3%D0%BE_%D1%82%D0%B5%D0%BB%D0%B5%D0%BA%D0%B0%D0%BD%D0%B0%D0%BB%D0%B0_%C2%AB%D0%A1%D0%A2%D0%92%C2%BB.svg.png",
                streamUrl = "https://ctv.dc.beltelecom.by/ctv/ctv.stream/playlist.m3u8",
                category = "Belarus,СТВ"
            ),
            TvChannel(
                id = 78,
                name = "Беларусь 24",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Belarus_24_logo.svg/742px-Belarus_24_logo.svg.png",
                streamUrl = "https://ngtrk.dc.beltelecom.by/ngtrk/smil:belarus24.smil/playlist.m3u8",
                category = "Belarus,Беларусь"
            ),
            TvChannel(
                id = 79,
                name = "Беларусь 5 Интернет",
                logoUrl = "https://i.imgur.com/rzPQ9Iz.png",
                streamUrl = "https://ngtrk.dc.beltelecom.by/ngtrk/smil:belarus5int.smil/playlist.m3u8",
                category = "Belarus,Беларусь"
            ),
            TvChannel(
                id = 80,
                name = "1Mus",
                logoUrl = "https://i.imgur.com/PozF9MT.png",
                streamUrl = "http://hz1.teleport.cc/HLS/HD.m3u8",
                category = "Belarus,1Mus"
            ),
            TvChannel(
                id = 81,
                name = "Belros Ⓢ",
                logoUrl = "https://i.imgur.com/HWqxjGl.png",
                streamUrl = "https://live2.mediacdn.ru/sr1/tro/playlist.m3u8",
                category = "Belarus,Belros"
            ),
            TvChannel(
                id = 82,
                name = "Planeta RTR Ⓢ",
                logoUrl = "https://i.imgur.com/yqRuEJd.png",
                streamUrl = "https://a3569455801-s26881.cdn.ngenix.net/live/smil:rtrp.smil/chunklist_b1600000.m3u8",
                category = "Belarus,Planeta"
            ),
            TvChannel(
                id = 83,
                name = "Radio HIT Orsk",
                logoUrl = "https://i.imgur.com/e2RyN4r.jpg",
                streamUrl = "http://hithd.camsh.orsk.ru/hls/hithd.m3u8",
                category = "Belarus,Radio"
            ),
            TvChannel(
                id = 84,
                name = "Vitebsk Telekanal",
                logoUrl = "https://i.imgur.com/FXAqELU.jpg",
                streamUrl = "https://flu.vtv.by/tvt-non-by/tracks-v1a1/mono.m3u8",
                category = "Belarus,Vitebsk"
            ),
            TvChannel(
                id = 85,
                name = "RTL-Be",
                logoUrl = "https://i.imgur.com/xMhSvax.png",
                streamUrl = "https://rtltvi-lh.akamaihd.net/i/TVI_1@319659/master.m3u8",
                category = "Belgium,RTL-Be"
            ),
            TvChannel(
                id = 86,
                name = "La Une",
                logoUrl = "https://i.imgur.com/hJodwJt.png",
                streamUrl = "http://4ce5e2d62ee2c10e43c709f9b87c44d5.streamhost.cc/m3u8/Belgium/29797c9f3f4fa00.m3u8",
                category = "Belgium,La"
            ),
            TvChannel(
                id = 87,
                name = "Tipik",
                logoUrl = "https://i.imgur.com/PVbVj8o.png",
                streamUrl = "http://4ce5e2d62ee2c10e43c709f9b87c44d5.streamhost.cc/m3u8/Belgium/5dee2de1f4661ce.m3u8",
                category = "Belgium,Tipik"
            ),
            TvChannel(
                id = 88,
                name = "LN24",
                logoUrl = "https://i.imgur.com/hePpxnn.png",
                streamUrl = "https://live.cdn.ln24.be/out/v1/b191621c8b9a436cad37bb36a82d2e1c/index.m3u8",
                category = "Belgium,LN24"
            ),
            TvChannel(
                id = 89,
                name = "BX1",
                logoUrl = "https://i.imgur.com/YjKqWru.png",
                streamUrl = "https://59959724487e3.streamlock.net/stream/live/playlist.m3u8",
                category = "Belgium,BX1"
            ),
            TvChannel(
                id = 90,
                name = "EEN",
                logoUrl = "https://i.imgur.com/66GQlc7.png",
                streamUrl = "https://live-vrt.rabah.net/groupc/live/8edf3bdf-7db3-41c3-a318-72cb7f82de66/live_aes.isml/playlist.m3u8",
                category = "Belgium,EEN"
            ),
            TvChannel(
                id = 91,
                name = "BHT 1",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/9/93/Logo_of_BHT_1_%282003-2012%29.png",
                streamUrl = "https://bhrtstream.bhtelecom.ba/bhrtportal_hd.m3u8",
                category = "Bosnia"
            ),
            TvChannel(
                id = 92,
                name = "Federalna televizija (FTV) Ⓢ",
                logoUrl = "https://i.imgur.com/Jpvs4u3.png",
                streamUrl = "http://94.250.2.6:7374/play/a02s/index.m3u8",
                category = "Bosnia"
            ),
            TvChannel(
                id = 93,
                name = "Televizija Republike Srpske (RTRS) Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7d/RTRS_Logo.svg/640px-RTRS_Logo.svg.png",
                streamUrl = "https://parh.rtrs.tv/tv/live/playlist.m3u8",
                category = "Bosnia"
            ),
            TvChannel(
                id = 94,
                name = "RTRS PLUS Ⓢ",
                logoUrl = "https://i.imgur.com/k06WvYl.png",
                streamUrl = "https://pluslive.rtrs.tv/plus/plus/playlist.m3u8",
                category = "Bosnia"
            ),
            TvChannel(
                id = 95,
                name = "RTV HB Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/6/60/Logo_of_TV_Herceg-Bosne.png",
                streamUrl = "https://prd-hometv-live-open.spectar.tv/ERO_1_083/playlist.m3u8",
                category = "Bosnia"
            ),
            TvChannel(
                id = 96,
                name = "RTV BN",
                logoUrl = "https://i.imgur.com/DUBvfWb.png",
                streamUrl = "https://rtvbn.tv:8080/live/index.m3u8",
                category = "Bosnia"
            ),
            TvChannel(
                id = 97,
                name = "RTV Glas Drine",
                logoUrl = "https://i.imgur.com/9NgxOdb.png",
                streamUrl = "http://glasdrine.cutuk.net:8081/433ssdsw/GlasDrineSD/playlist.m3u8",
                category = "Bosnia"
            ),
            TvChannel(
                id = 98,
                name = "Sevdah Ⓢ",
                logoUrl = "https://i.imgur.com/V6W3yEp.png",
                streamUrl = "https://restreamer2.tnt.ba/hls/stream.m3u8",
                category = "Bosnia"
            ),
            TvChannel(
                id = 99,
                name = "TNT Kids",
                logoUrl = "https://i.imgur.com/irTDbpn.png",
                streamUrl = "https://restreamer1.tnt.ba/hls/tntkids.m3u8",
                category = "Bosnia"
            ),
            TvChannel(
                id = 100,
                name = "Televizija 5",
                logoUrl = "https://i.imgur.com/znpvJys.png",
                streamUrl = "https://balkanmedia.dynu.net/hls/tv5web.m3u8",
                category = "Bosnia"
            ),
            TvChannel(
                id = 101,
                name = "Kanal 6",
                logoUrl = "https://i.imgur.com/GGhvR0l.png",
                streamUrl = "https://restreamer1.tnt.ba/hls/kanal6.m3u8",
                category = "Bosnia"
            ),
            TvChannel(
                id = 102,
                name = "SuperTV",
                logoUrl = "https://i.imgur.com/XYWgd3E.png",
                streamUrl = "https://mirtv.club/live/mirtv/index.m3u8",
                category = "Bosnia"
            ),
            TvChannel(
                id = 103,
                name = "RTV ZE Ⓢ",
                logoUrl = "https://i.imgur.com/TKUaflB.png",
                streamUrl = "https://stream.rtvze.ba/live/123/123.m3u8",
                category = "Bosnia"
            ),
            TvChannel(
                id = 104,
                name = "TV BPK Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/d/df/Logo_of_RTV_BPK_Gora%C5%BEde.jpg",
                streamUrl = "http://94.250.2.6:7374/play/a02u/index.m3u8",
                category = "Bosnia"
            ),
            TvChannel(
                id = 105,
                name = "COM Brasil",
                logoUrl = "https://i.imgur.com/c8ztQnF.png",
                streamUrl = "https://br5093.streamingdevideo.com.br/abc/abc/playlist.m3u8",
                category = "Brazil,COM"
            ),
            TvChannel(
                id = 106,
                name = "ISTV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/pt/b/b5/Logotipo_da_ISTV.png",
                streamUrl = "https://video08.logicahost.com.br/istvnacional/srt.stream/istvnacional.m3u8",
                category = "Brazil,ISTV"
            ),
            TvChannel(
                id = 107,
                name = "Rede Brasil",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/d/d1/Marca_rede_brasil_rgb-color.png",
                streamUrl = "https://video09.logicahost.com.br/redebrasiloficial/redebrasiloficial/playlist.m3u8",
                category = "Brazil,Rede"
            ),
            TvChannel(
                id = 108,
                name = "TV Câmara",
                logoUrl = "https://i.imgur.com/UpV2PRk.png",
                streamUrl = "https://stream3.camara.gov.br/tv1/manifest.m3u8",
                category = "Brazil,TV"
            ),
            TvChannel(
                id = 109,
                name = "TVE RS",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/c/c2/Logotipo_da_TVE_RS.png",
                streamUrl = "http://selpro1348.procergs.com.br:1935/tve/stve/playlist.m3u8",
                category = "Brazil,TVE"
            ),
            TvChannel(
                id = 110,
                name = "TV Cultura",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/8/82/Cultura_logo_2013.svg",
                streamUrl = "https://player-tvcultura.stream.uol.com.br/live/tvcultura.m3u8",
                category = "Brazil,TV"
            ),
            TvChannel(
                id = 111,
                name = "City TV Ⓢ",
                logoUrl = "https://i.imgur.com/BjRTbrU.png",
                streamUrl = "https://tv.city.bg/play/tshls/citytv/index.m3u8",
                category = "Bulgaria,City"
            ),
            TvChannel(
                id = 112,
                name = "TV1",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/6/64/Tv1-new.png",
                streamUrl = "https://tv1.cloudcdn.bg/temp/livestream.m3u8",
                category = "Bulgaria,TV1"
            ),
            TvChannel(
                id = 113,
                name = "CBC Toronto",
                logoUrl = "https://i.imgur.com/H5yEbxf.png",
                streamUrl = "https://bozztv.com/teleyupp1/teleup-ydcl2V1MVC/playlist.m3u8",
                category = "Canada,CBC"
            ),
            TvChannel(
                id = 114,
                name = "Citytv",
                logoUrl = "https://i.imgur.com/BlFNlHz.png",
                streamUrl = "https://bozztv.com/teleyupp1/teleup-iSykLSKMFr/tracks-v1a1/mono.m3u8",
                category = "Canada,Citytv"
            ),
            TvChannel(
                id = 115,
                name = "CTV Toronto",
                logoUrl = "https://i.imgur.com/qOutOWN.png",
                streamUrl = "https://bozztv.com/teleyupp1/teleup-zxsJFt6VvY/playlist.m3u8",
                category = "Canada,CTV"
            ),
            TvChannel(
                id = 116,
                name = "Global Toronto",
                logoUrl = "https://i.imgur.com/2CxLO4H.png",
                streamUrl = "https://d128o1k7zh3htz.cloudfront.net/out/v1/74a58360a3734f97b74ba439bc678044/index.m3u8",
                category = "Canada,Global"
            ),
            TvChannel(
                id = 117,
                name = "Global Calgary",
                logoUrl = "https://i.imgur.com/2CxLO4H.png",
                streamUrl = "https://dfmjr9irb1dl5.cloudfront.net/out/v1/454010ff309e4963a087f5802856e346/index.m3u8",
                category = "Canada,Global"
            ),
            TvChannel(
                id = 118,
                name = "Global Edmonton",
                logoUrl = "https://i.imgur.com/2CxLO4H.png",
                streamUrl = "https://da7sdtkzly6qj.cloudfront.net/out/v1/b317f6c10f2e493993bd2b5314df1c7c/index_1.m3u8",
                category = "Canada,Global"
            ),
            TvChannel(
                id = 119,
                name = "TVO",
                logoUrl = "https://i.imgur.com/PkBPPcL.png",
                streamUrl = "https://bozztv.com/teleyupp1/teleup-OMZsmYVUMp/playlist.m3u8",
                category = "Canada,TVO"
            ),
            TvChannel(
                id = 120,
                name = "ONNtv Ontario",
                logoUrl = "https://i.imgur.com/zz5ST9K.png",
                streamUrl = "https://onntv.vantrix.tv:443/onntv_hls/1080p/onntv_hls-HLS-1080p.m3u8",
                category = "Canada,ONNtv"
            ),
            TvChannel(
                id = 121,
                name = "CBC News",
                logoUrl = "https://i.imgur.com/1EqQGKS.png",
                streamUrl = "https://cbcnewshd-f.akamaihd.net/i/cbcnews_1@8981/index_2500_av-p.m3u8",
                category = "Canada,CBC"
            ),
            TvChannel(
                id = 122,
                name = "CTV News",
                logoUrl = "https://i.imgur.com/T3oBeiX.png",
                streamUrl = "https://pe-fa-lp02a.9c9media.com/live/News1Digi/p/hls/00000201/38ef78f479b07aa0/index/0c6a10a2/live/stream/h264/v1/3500000/manifest.m3u8",
                category = "Canada,CTV"
            ),
            TvChannel(
                id = 123,
                name = "Global News",
                logoUrl = "https://i.imgur.com/IpfmG93.png",
                streamUrl = "https://i.mjh.nz/PlutoTV/62cbef9ebb857100072fc187-alt.m3u8",
                category = "Canada,Global"
            ),
            TvChannel(
                id = 124,
                name = "Global News BC",
                logoUrl = "https://i.imgur.com/IpfmG93.png",
                streamUrl = "https://i.mjh.nz/PlutoTV/62cbf063257170000724590c-alt.m3u8",
                category = "Canada,Global"
            ),
            TvChannel(
                id = 125,
                name = "Global News Calgary",
                logoUrl = "https://i.imgur.com/IpfmG93.png",
                streamUrl = "https://i.mjh.nz/PlutoTV/62cbf23dcfb48300077f8348-alt.m3u8",
                category = "Canada,Global"
            ),
            TvChannel(
                id = 126,
                name = "Global News Halifax",
                logoUrl = "https://i.imgur.com/IpfmG93.png",
                streamUrl = "https://i.mjh.nz/PlutoTV/62cbf398b8e02600071deda5-alt.m3u8",
                category = "Canada,Global"
            ),
            TvChannel(
                id = 127,
                name = "Global News Kingston",
                logoUrl = "https://i.imgur.com/IpfmG93.png",
                streamUrl = "https://i.mjh.nz/PlutoTV/62cbf4964446e2000742073e-alt.m3u8",
                category = "Canada,Global"
            ),
            TvChannel(
                id = 128,
                name = "Global News Montreal",
                logoUrl = "https://i.imgur.com/IpfmG93.png",
                streamUrl = "https://i.mjh.nz/PlutoTV/62cbfbd6ad95670007f567af-alt.m3u8",
                category = "Canada,Global"
            ),
            TvChannel(
                id = 129,
                name = "Global News Peterborough",
                logoUrl = "https://i.imgur.com/IpfmG93.png",
                streamUrl = "https://i.mjh.nz/PlutoTV/62cbfcd8c2db990007861e43-alt.m3u8",
                category = "Canada,Global"
            ),
            TvChannel(
                id = 130,
                name = "Global News Regina",
                logoUrl = "https://i.imgur.com/IpfmG93.png",
                streamUrl = "https://i.mjh.nz/PlutoTV/62cbff53ca8f2200080253b5-alt.m3u8",
                category = "Canada,Global"
            ),
            TvChannel(
                id = 131,
                name = "Global News Saskatoon",
                logoUrl = "https://i.imgur.com/IpfmG93.png",
                streamUrl = "https://i.mjh.nz/PlutoTV/62cc00359cb58900088dc840-alt.m3u8",
                category = "Canada,Global"
            ),
            TvChannel(
                id = 132,
                name = "Global News Winnipeg",
                logoUrl = "https://i.imgur.com/IpfmG93.png",
                streamUrl = "https://i.mjh.nz/PlutoTV/62cc0120880c890007191016-alt.m3u8",
                category = "Canada,Global"
            ),
            TvChannel(
                id = 133,
                name = "CPAC (EN)",
                logoUrl = "https://i.imgur.com/AbdFD0S.png",
                streamUrl = "https://d7z3qjdsxbwoq.cloudfront.net/groupa/live/f9809cea-1e07-47cd-a94d-2ddd3e1351db/live.isml/.m3u8",
                category = "Canada,CPAC"
            ),
            TvChannel(
                id = 134,
                name = "ICI RDI",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/ICI_RDI_logo.svg/640px-ICI_RDI_logo.svg.png",
                streamUrl = "https://rcavlive.akamaized.net/hls/live/704025/xcanrdi/master.m3u8",
                category = "Canada,ICI"
            ),
            TvChannel(
                id = 135,
                name = "ICI Télé HD Ⓖ",
                logoUrl = "https://i.imgur.com/HsSi3NV.png",
                streamUrl = "https://rcavlive.akamaized.net/hls/live/696615/xcancbft/master.m3u8",
                category = "Canada,ICI"
            ),
            TvChannel(
                id = 136,
                name = "TVA Ⓖ",
                logoUrl = "https://i.imgur.com/1GR8Szn.png",
                streamUrl = "https://tvalive.akamaized.net/hls/live/2012413/tva01/master.m3u8",
                category = "Canada,TVA"
            ),
            TvChannel(
                id = 137,
                name = "Télé Québec",
                logoUrl = "https://i.imgur.com/8grBWK9.png",
                streamUrl = "https://bcovlive-a.akamaihd.net/575d86160eb143458d51f7ab187a4e68/us-east-1/6101674910001/playlist.m3u8",
                category = "Canada,Télé"
            ),
            TvChannel(
                id = 138,
                name = "Savoir Média",
                logoUrl = "https://i.imgur.com/pa4wOVY.png",
                streamUrl = "https://hls.savoir.media/live/stream.m3u8",
                category = "Canada,Savoir"
            ),
            TvChannel(
                id = 139,
                name = "CPAC (FR)",
                logoUrl = "https://i.imgur.com/AbdFD0S.png",
                streamUrl = "https://bcsecurelivehls-i.akamaihd.net/hls/live/680604/1242843915001_3/master.m3u8",
                category = "Canada,CPAC"
            ),
            TvChannel(
                id = 140,
                name = "ICI Montreal",
                logoUrl = "https://i.imgur.com/Z1b2TJD.png",
                streamUrl = "https://amdici.akamaized.net/hls/live/873426/ICI-Live-Stream/master.m3u8",
                category = "Canada,ICI"
            ),
            TvChannel(
                id = 141,
                name = "Toronto 360 TV",
                logoUrl = "https://i.imgur.com/PkWndsv.png",
                streamUrl = "http://cdn3.toronto360.tv:8081/toronto360/hd/playlist.m3u8",
                category = "Canada,Toronto"
            ),
            TvChannel(
                id = 142,
                name = "Tchad 24",
                logoUrl = "https://www.lyngsat.com/logo/tv/tt/tchad-24-td.png",
                streamUrl = "http://102.131.58.110/out_1/index.m3u8",
                category = "Chad,Tchad"
            ),
            TvChannel(
                id = 143,
                name = "Télé Tchad Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/fr/b/b6/Logo_T%C3%A9l%C3%A9_Tchad.png",
                streamUrl = "https://strhlslb01.streamakaci.tv/str_tchad_tchad/str_tchad_multi/playlist.m3u8",
                category = "Chad,Télé"
            ),
            TvChannel(
                id = 144,
                name = "UCV Televisión",
                logoUrl = "https://i.imgur.com/2VL4Pts.png",
                streamUrl = "https://unlimited1-cl-isp.dps.live/ucvtv2/ucvtv2.smil/playlist.m3u8",
                category = "Chile,UCV"
            ),
            TvChannel(
                id = 145,
                name = "24 horas",
                logoUrl = "https://i.imgur.com/0rF6Kub.png",
                streamUrl = "https://mdstrm.com/live-stream-playlist/57d1a22064f5d85712b20dab.m3u8",
                category = "Chile,24"
            ),
            TvChannel(
                id = 146,
                name = "NTV Ⓖ",
                logoUrl = "https://i.imgur.com/pt2Kj1A.png",
                streamUrl = "https://mdstrm.com/live-stream-playlist/5aaabe9e2c56420918184c6d.m3u8",
                category = "Chile,NTV"
            ),
            TvChannel(
                id = 147,
                name = "TV Chile Ⓖ",
                logoUrl = "https://i.imgur.com/yCL888l.png",
                streamUrl = "https://mdstrm.com/live-stream-playlist/533adcc949386ce765657d7c.m3u8",
                category = "Chile,TV"
            ),
            TvChannel(
                id = 148,
                name = "TV+ Ⓖ",
                logoUrl = "https://i.imgur.com/NtuZIEJ.png",
                streamUrl = "https://mdstrm.com/live-stream-playlist/5c0e8b19e4c87f3f2d3e6a59.m3u8",
                category = "Chile,TV+"
            ),
            TvChannel(
                id = 149,
                name = "UChile TV",
                logoUrl = "https://i.imgur.com/mF2W8Uh.png",
                streamUrl = "https://unlimited1-us.dps.live/uchiletv/uchiletv.smil/playlist.m3u8",
                category = "Chile,UChile"
            ),
            TvChannel(
                id = 150,
                name = "T13 en vivo",
                logoUrl = "https://i.imgur.com/3CEijac.png",
                streamUrl = "https://redirector.rudo.video/hls-video/10b92cafdf3646cbc1e727f3dc76863621a327fd/t13/t13.smil/playlist.m3u8",
                category = "Chile,T13"
            ),
            TvChannel(
                id = 151,
                name = "13 Entretención",
                logoUrl = "https://i.imgur.com/1vTno0m.png",
                streamUrl = "https://origin.dpsgo.com/ssai/event/BBp0VeP6QtOOlH8nu3bWTg/master.m3u8",
                category = "Chile,13"
            ),
            TvChannel(
                id = 152,
                name = "13 Cultura",
                logoUrl = "https://i.imgur.com/49QkKWv.png",
                streamUrl = "https://origin.dpsgo.com/ssai/event/GI-9cp_bT8KcerLpZwkuhw/master.m3u8",
                category = "Chile,13"
            ),
            TvChannel(
                id = 153,
                name = "13 Prime",
                logoUrl = "https://i.imgur.com/YwDFNxs.png",
                streamUrl = "https://origin.dpsgo.com/ssai/event/p4mmBxEzSmKAxY1GusOHrw/master.m3u8",
                category = "Chile,13"
            ),
            TvChannel(
                id = 154,
                name = "13 Kids",
                logoUrl = "https://i.imgur.com/m6y9AMe.png",
                streamUrl = "https://origin.dpsgo.com/ssai/event/LhHrVtyeQkKZ-Ye_xEU75g/master.m3u8",
                category = "Chile,13"
            ),
            TvChannel(
                id = 155,
                name = "13 Realities",
                logoUrl = "https://i.imgur.com/p1Qpljw.png",
                streamUrl = "https://origin.dpsgo.com/ssai/event/g7_JOM0ORki9SR5RKHe-Kw/master.m3u8",
                category = "Chile,13"
            ),
            TvChannel(
                id = 156,
                name = "13 Teleseries",
                logoUrl = "https://i.imgur.com/aJMBnse.png",
                streamUrl = "https://origin.dpsgo.com/ssai/event/f4TrySe8SoiGF8Lu3EIq1g/master.m3u8",
                category = "Chile,13"
            ),
            TvChannel(
                id = 157,
                name = "El Pingüino TV",
                logoUrl = "https://i.imgur.com/ohXs2NV.png",
                streamUrl = "https://redirector.rudo.video/hls-video/339f69c6122f6d8f4574732c235f09b7683e31a5/pinguinotv/pinguinotv.smil/playlist.m3u8",
                category = "Chile,El"
            ),
            TvChannel(
                id = 158,
                name = "UCL",
                logoUrl = "https://i.imgur.com/JxqVHPX.png",
                streamUrl = "https://redirector.rudo.video/hls-video/c54ac2799874375c81c1672abb700870537c5223/ucl/ucl.smil/playlist.m3u8",
                category = "Chile,UCL"
            ),
            TvChannel(
                id = 159,
                name = "Deportes13 Ⓖ",
                logoUrl = "https://i.imgur.com/GRpxoPf.png",
                streamUrl = "https://redirector.rudo.video/hls-video/ey6283je82983je9823je8jowowiekldk9838274/13d/13d.smil/playlist.m3u8",
                category = "Chile,Deportes13"
            ),
            TvChannel(
                id = 160,
                name = "TVN 3",
                logoUrl = "https://i.imgur.com/84lWqRi.png",
                streamUrl = "https://mdstrm.com/live-stream-playlist/5653641561b4eba30a7e4929.m3u8",
                category = "Chile,TVN"
            ),
            TvChannel(
                id = 161,
                name = "Chilevisión Noticias",
                logoUrl = "https://i.imgur.com/Qh6d0A9.png",
                streamUrl = "https://redirector.rudo.video/hls-video/10b92cafdf3646cbc1e727f3dc76863621a327fd/chvn/chvn.smil/playlist.m3u8",
                category = "Chile,Chilevisión"
            ),
            TvChannel(
                id = 162,
                name = "CCTV-1 综合",
                logoUrl = "https://i.imgur.com/uHU6Vc0.png",
                streamUrl = "https://node1.olelive.com:6443/live/CCTV1HD/hls.m3u8",
                category = "China,CCTV-1"
            ),
            TvChannel(
                id = 163,
                name = "CCTV-2 财经",
                logoUrl = "https://i.imgur.com/6C9JEYt.png",
                streamUrl = "https://node1.olelive.com:6443/live/CCTV2HD/hls.m3u8",
                category = "China,CCTV-2"
            ),
            TvChannel(
                id = 164,
                name = "CCTV-4 中文国际（美） Ⓢ",
                logoUrl = "https://i.imgur.com/1TPiRqR.png",
                streamUrl = "https://global.cgtn.cicc.media.caton.cloud/master/cgtn-america.m3u8",
                category = "China,CCTV-4"
            ),
            TvChannel(
                id = 165,
                name = "CCTV-5 体育",
                logoUrl = "https://i.imgur.com/Mut2omN.png",
                streamUrl = "https://node1.olelive.com:6443/live/CCTV5HD/hls.m3u8",
                category = "China,CCTV-5"
            ),
            TvChannel(
                id = 166,
                name = "CCTV-5+ 体育赛事",
                logoUrl = "https://i.imgur.com/UNjmQVS.png",
                streamUrl = "https://node1.olelive.com:6443/live/CCTV5PHD/hls.m3u8",
                category = "China,CCTV-5+"
            ),
            TvChannel(
                id = 167,
                name = "CCTV-7 国防军事",
                logoUrl = "https://i.imgur.com/GhXlUpM.png",
                streamUrl = "https://node1.olelive.com:6443/live/CCTV7HD/hls.m3u8",
                category = "China,CCTV-7"
            ),
            TvChannel(
                id = 168,
                name = "CCTV-8 电视剧",
                logoUrl = "https://i.imgur.com/Qg1opg9.png",
                streamUrl = "https://node1.olelive.com:6443/live/CCTV8HD/hls.m3u8",
                category = "China,CCTV-8"
            ),
            TvChannel(
                id = 169,
                name = "CCTV-9 纪录",
                logoUrl = "https://i.imgur.com/Ruyzhu5.png",
                streamUrl = "https://node1.olelive.com:6443/live/CCTV9HD/hls.m3u8",
                category = "China,CCTV-9"
            ),
            TvChannel(
                id = 170,
                name = "CCTV-10 科教",
                logoUrl = "https://i.imgur.com/W8JNs1s.png",
                streamUrl = "https://node1.olelive.com:6443/live/CCTV10HD/hls.m3u8",
                category = "China,CCTV-10"
            ),
            TvChannel(
                id = 171,
                name = "CCTV-13 新闻",
                logoUrl = "https://i.imgur.com/pPO8uJN.png",
                streamUrl = "https://node1.olelive.com:6443/live/CCTV13HD/hls.m3u8",
                category = "China,CCTV-13"
            ),
            TvChannel(
                id = 172,
                name = "CCTV-17 农业农村",
                logoUrl = "https://i.imgur.com/XMsoHut.png",
                streamUrl = "https://node1.olelive.com:6443/live/CCTV17HD/hls.m3u8",
                category = "China,CCTV-17"
            ),
            TvChannel(
                id = 173,
                name = "CETV-1",
                logoUrl = "https://i.imgur.com/AMcIAOV.png",
                streamUrl = "http://txycsbl.centv.cn/zb/0628cetv1.m3u8",
                category = "China,CETV-1"
            ),
            TvChannel(
                id = 174,
                name = "CETV-2",
                logoUrl = "https://i.imgur.com/a9mvoeP.png",
                streamUrl = "http://txycsbl.centv.cn/zb/0822cetv2.m3u8",
                category = "China,CETV-2"
            ),
            TvChannel(
                id = 175,
                name = "CETV-3",
                logoUrl = "https://i.imgur.com/t8o5ZKt.png",
                streamUrl = "http://txycsbl.centv.cn/zb/0822cetv3.m3u8",
                category = "China,CETV-3"
            ),
            TvChannel(
                id = 176,
                name = "CETV-4",
                logoUrl = "https://i.imgur.com/BRe0ybV.png",
                streamUrl = "http://txycsbl.centv.cn/zb/0822cetv4.m3u8",
                category = "China,CETV-4"
            ),
            TvChannel(
                id = 177,
                name = "TV BRICS Chinese",
                logoUrl = "https://i.imgur.com/896132Z.png",
                streamUrl = "https://brics.bonus-tv.ru/cdn/brics/chinese/playlist.m3u8",
                category = "China,TV"
            ),
            TvChannel(
                id = 178,
                name = "Canal 1",
                logoUrl = "https://cloudfront-us-east-1.images.arcpublishing.com/gruponacion/2XI5OC6MQZFXXBDGMRRDOZSL2Q.jpg",
                streamUrl = "https://video20.klm99.com:3993/live/canal1crlive.m3u8",
                category = "Costa"
            ),
            TvChannel(
                id = 179,
                name = "Canal 2 CDR",
                logoUrl = "https://i0.wp.com/directostv.teleame.com/wp-content/uploads/2016/06/Canal-2-Costa-Rica-en-vivo-Online.png",
                streamUrl = "https://d3bgcstab9qhdz.cloudfront.net/hls/canal2.m3u8",
                category = "Costa"
            ),
            TvChannel(
                id = 180,
                name = "Canal 4",
                logoUrl = "https://i0.wp.com/directostv.teleame.com/wp-content/uploads/2016/06/Canal-4-Costa-Rica-en-vivo-Online.png",
                streamUrl = "https://d3bgcstab9qhdz.cloudfront.net/hls/canal2.m3u8",
                category = "Costa"
            ),
            TvChannel(
                id = 181,
                name = "Canal 6",
                logoUrl = "https://i0.wp.com/directostv.teleame.com/wp-content/uploads/2016/06/Canal-6-Costa-Rica-en-vivo-Online.png",
                streamUrl = "https://d3bgcstab9qhdz.cloudfront.net/hls/canal2.m3u8",
                category = "Costa"
            ),
            TvChannel(
                id = 182,
                name = "Canal 8",
                logoUrl = "https://platform-static.cdn.mdstrm.com/player/logo/5efe501c21d05a0722152f6d.png",
                streamUrl = "http://mdstrm.com/live-stream-playlist/5a7b1e63a8da282c34d65445.m3u8",
                category = "Costa"
            ),
            TvChannel(
                id = 183,
                name = "Canal 11",
                logoUrl = "https://i0.wp.com/directostv.teleame.com/wp-content/uploads/2016/06/Canal-11-Costa-Rica-en-vivo-Online.png",
                streamUrl = "https://d3bgcstab9qhdz.cloudfront.net/hls/canal2.m3u8",
                category = "Costa"
            ),
            TvChannel(
                id = 184,
                name = "88 Stereo",
                logoUrl = "http://www.88stereo.com/wp-content/uploads/2017/05/88Stereo-logoweb.png",
                streamUrl = "http://k3.usastreams.com/CableLatino/88stereo/playlist.m3u8",
                category = "Costa"
            ),
            TvChannel(
                id = 185,
                name = "HRT 1",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/1/1f/HRT1-logo.png",
                streamUrl = "https://webtvstream.bhtelecom.ba/hls9/hrt1_1200.m3u8",
                category = "Croatia,HRT"
            ),
            TvChannel(
                id = 186,
                name = "HRT 2",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/d/d0/Htv2-logo.png",
                streamUrl = "https://webtvstream.bhtelecom.ba/hls9/hrt2_1200.m3u8",
                category = "Croatia,HRT"
            ),
            TvChannel(
                id = 187,
                name = "RTL",
                logoUrl = "https://i.imgur.com/zAjr6pO.png",
                streamUrl = "https://d1cs5tlhj75jxe.cloudfront.net/rtl/playlist.m3u8",
                category = "Croatia,RTL"
            ),
            TvChannel(
                id = 188,
                name = "HRT 3",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/9/96/HRT3_Logo_aktuell.png",
                streamUrl = "https://webtvstream.bhtelecom.ba/hls9/hrt3_1200.m3u8",
                category = "Croatia,HRT"
            ),
            TvChannel(
                id = 189,
                name = "RTL 2",
                logoUrl = "https://i.imgur.com/dQLaylJ.png",
                streamUrl = "https://d1um9c09e0t5ag.cloudfront.net/rtl2/playlist.m3u8",
                category = "Croatia,RTL"
            ),
            TvChannel(
                id = 190,
                name = "Sportska televizija Ⓖ",
                logoUrl = "https://i.imgur.com/xdxjcVh.png",
                streamUrl = "https://stream.agatin.hr:3087/live/sptvlive.m3u8",
                category = "Croatia,Sportska"
            ),
            TvChannel(
                id = 191,
                name = "RTL Kockica",
                logoUrl = "https://i.imgur.com/BiSVmRa.png",
                streamUrl = "https://d1rzyyum8t0q1e.cloudfront.net/rtl-kockica/playlist.m3u8",
                category = "Croatia,RTL"
            ),
            TvChannel(
                id = 192,
                name = "CMC TV",
                logoUrl = "https://i.imgur.com/Fh2vawT.png",
                streamUrl = "https://stream.cmctv.hr:49998/cmc/live.m3u8",
                category = "Croatia,CMC"
            ),
            TvChannel(
                id = 193,
                name = "Plava Vinkovačka",
                logoUrl = "https://i.imgur.com/WJJNtQ3.jpg",
                streamUrl = "https://player-api.new.livestream.com/accounts/26611954/events/7977299/broadcasts/237205435.secure.m3u8",
                category = "Croatia,Plava"
            ),
            TvChannel(
                id = 194,
                name = "Televizija Slavonije i Baranje (STV)",
                logoUrl = "https://upload.wikimedia.org/wikipedia/hr/0/04/STV.PNG",
                streamUrl = "http://89.201.163.244:8080/hls/hdmi.m3u8",
                category = "Croatia,Televizija"
            ),
            TvChannel(
                id = 195,
                name = "Osječka televizija (OSTV) Ⓢ",
                logoUrl = "https://i.imgur.com/o9JgEyG.png",
                streamUrl = "https://player-api.new.livestream.com/accounts/27681961/events/8347875/broadcasts/237202062.secure.m3u8",
                category = "Croatia,Osječka"
            ),
            TvChannel(
                id = 196,
                name = "TV Nova",
                logoUrl = "https://upload.wikimedia.org/wikipedia/hr/c/c8/TVnova-logo.png",
                streamUrl = "https://stream.agatin.hr:3727/live/tvnovalive.m3u8",
                category = "Croatia,TV"
            ),
            TvChannel(
                id = 197,
                name = "TV Jadran",
                logoUrl = "https://upload.wikimedia.org/wikipedia/hr/9/9a/Tv_jadran_logo.png",
                streamUrl = "https://tvjadran.stream.agatin.hr:3412/live/tvjadranlive.m3u8",
                category = "Croatia,TV"
            ),
            TvChannel(
                id = 198,
                name = "Libertas TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/hr/8/8a/LibertasTV.png",
                streamUrl = "https://stream.luci.xyz/hls/LTV.m3u8",
                category = "Croatia,Libertas"
            ),
            TvChannel(
                id = 199,
                name = "Trend TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/hr/2/22/TrendTV.jpg",
                streamUrl = "http://185.62.75.22:1935/trend/myStream/playlist.m3u8",
                category = "Croatia,Trend"
            ),
            TvChannel(
                id = 200,
                name = "Televizija Zapad",
                logoUrl = "https://upload.wikimedia.org/wikipedia/hr/9/97/TVZ-2018.png",
                streamUrl = "http://webtv.zapad.tv:8080/memfs/1ad23803-84c3-41c7-aa91-fce4c7eac52e.m3u8",
                category = "Croatia,Televizija"
            ),
            TvChannel(
                id = 201,
                name = "Al Jazeera Balkans",
                logoUrl = "https://i.imgur.com/Z1FB6wl.png",
                streamUrl = "https://live-hls-web-ajb.getaj.net/AJB/index.m3u8",
                category = "Croatia,Al"
            ),
            TvChannel(
                id = 202,
                name = "RIK Sat",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ad/Logo_RIK_Sat_2017.svg/640px-Logo_RIK_Sat_2017.svg.png",
                streamUrl = "https://l3.cloudskep.com/cybcsat/abr/playlist.m3u8",
                category = "Cyprus,RIK"
            ),
            TvChannel(
                id = 203,
                name = "BRT 1",
                logoUrl = "https://i.imgur.com/gOPAi2c.png",
                streamUrl = "https://sc-kuzeykibrissmarttv.ercdn.net/brt1hd/bant1/playlist.m3u8",
                category = "Cyprus,BRT"
            ),
            TvChannel(
                id = 204,
                name = "BRT 2",
                logoUrl = "https://i.imgur.com/t5kbIuj.png",
                streamUrl = "https://sc-kuzeykibrissmarttv.ercdn.net/brt2hd/bant1/playlist.m3u8",
                category = "Cyprus,BRT"
            ),
            TvChannel(
                id = 205,
                name = "Ada TV Ⓢ",
                logoUrl = "https://i.imgur.com/LPQfdz2.png",
                streamUrl = "https://sc-kuzeykibrissmarttv.ercdn.net/adatv/bant1/playlist.m3u8",
                category = "Cyprus,Ada"
            ),
            TvChannel(
                id = 206,
                name = "Kıbrıs Genç TV Ⓢ",
                logoUrl = "https://i.imgur.com/eBdQn9h.png",
                streamUrl = "https://sc-kuzeykibrissmarttv.ercdn.net/kibrisgenctv/bant1/playlist.m3u8",
                category = "Cyprus,Kıbrıs"
            ),
            TvChannel(
                id = 207,
                name = "Kanal T Ⓢ",
                logoUrl = "https://i.imgur.com/4bA4pXT.png",
                streamUrl = "https://sc-kuzeykibrissmarttv.ercdn.net/kanalt/bantp1/playlist.m3u8",
                category = "Cyprus,Kanal"
            ),
            TvChannel(
                id = 208,
                name = "Kıbrıs TV Ⓢ",
                logoUrl = "https://i.imgur.com/5MJZPTo.png",
                streamUrl = "https://sc-kuzeykibrissmarttv.ercdn.net/kibristv/bant1/playlist.m3u8",
                category = "Cyprus,Kıbrıs"
            ),
            TvChannel(
                id = 209,
                name = "TV 2020 Ⓢ",
                logoUrl = "https://i.imgur.com/rtfsNdd.png",
                streamUrl = "https://sc-kuzeykibrissmarttv.ercdn.net/tv2020/bantp1/playlist.m3u8",
                category = "Cyprus,TV"
            ),
            TvChannel(
                id = 210,
                name = "JOJ Family Ⓢ",
                logoUrl = "https://i.imgur.com/IZHIAAj.png",
                streamUrl = "https://live.cdn.joj.sk/live/hls/family-540.m3u8",
                category = "Czech"
            ),
            TvChannel(
                id = 211,
                name = "Óčko Ⓢ",
                logoUrl = "https://i.imgur.com/iPmpsnN.png",
                streamUrl = "https://ocko-live.ssl.cdn.cra.cz/channels/ocko/playlist.m3u8",
                category = "Czech"
            ),
            TvChannel(
                id = 212,
                name = "Óčko Star Ⓢ",
                logoUrl = "https://i.imgur.com/tGzQFWw.png",
                streamUrl = "https://ocko-live.ssl.cdn.cra.cz/channels/ocko_gold/playlist.m3u8",
                category = "Czech"
            ),
            TvChannel(
                id = 213,
                name = "Óčko Expres Ⓢ",
                logoUrl = "https://i.imgur.com/e731JNS.png",
                streamUrl = "https://ocko-live.ssl.cdn.cra.cz/channels/ocko_expres/playlist.m3u8",
                category = "Czech"
            ),
            TvChannel(
                id = 214,
                name = "Retro Music Television Ⓢ",
                logoUrl = "https://i.imgur.com/a6S2Yo4.png",
                streamUrl = "https://stream.mediawork.cz/retrotv/smil:retrotv2.smil/playlist.m3u8",
                category = "Czech"
            ),
            TvChannel(
                id = 215,
                name = "Praha TV",
                logoUrl = "https://www.praga2018.cz/wp-content/uploads/logo-prahatv.png",
                streamUrl = "https://stream.polar.cz/prahatv/prahatvlive-1/playlist.m3u8",
                category = "Czech"
            ),
            TvChannel(
                id = 216,
                name = "Východoceská TV",
                logoUrl = "https://i.imgur.com/4Wwptd3.png",
                streamUrl = "https://stream.polar.cz/vctv/vctvlive-1/playlist.m3u8",
                category = "Czech"
            ),
            TvChannel(
                id = 217,
                name = "UTV (Czech Republic)",
                logoUrl = "https://imgur.com/ulfeIwM.png",
                streamUrl = "https://vysilani.zaktv.cz/broadcast/hls/utv/index.m3u8",
                category = "Czech"
            ),
            TvChannel(
                id = 218,
                name = "DR1 Ⓖ",
                logoUrl = "https://i.imgur.com/wEq8UnG.png",
                streamUrl = "https://drlive01texthls.akamaized.net/hls/live/2014186/drlive01text/master.m3u8",
                category = "Denmark,DR1"
            ),
            TvChannel(
                id = 219,
                name = "DR2 Ⓖ",
                logoUrl = "https://i.imgur.com/b79UKYN.png",
                streamUrl = "https://drlive02texthls.akamaized.net/hls/live/2014188/drlive02text/master.m3u8",
                category = "Denmark,DR2"
            ),
            TvChannel(
                id = 220,
                name = "DR Ramasjang Ⓖ",
                logoUrl = "https://i.imgur.com/YD0z2mN.png",
                streamUrl = "https://drlive03texthls.akamaized.net/hls/live/2014191/drlive03text/master.m3u8",
                category = "Denmark,DR"
            ),
            TvChannel(
                id = 221,
                name = "Folketinget TV",
                logoUrl = "https://i.imgur.com/RqQDUzX.png",
                streamUrl = "https://cdnapi.kaltura.com/p/2158211/sp/327418300/playManifest/entryId/1_24gfa7qq/protocol/https/format/applehttp/a.m3u8",
                category = "Denmark,Folketinget"
            ),
            TvChannel(
                id = 222,
                name = "TV Syd+",
                logoUrl = "https://i.imgur.com/k2jf591.png",
                streamUrl = "https://cdn-lt-live.tvsyd.dk/env/cluster-1-e.live.nvp1/live/hls/p/1956351/e/0_e9slj9wh/tl/main/st/0/t/rFEtaqAbdhUFGef_BNF4WQ/index-s32.m3u8",
                category = "Denmark,TV"
            ),
            TvChannel(
                id = 223,
                name = "TV 2/Fyn",
                logoUrl = "https://i.imgur.com/4L6AIMH.png",
                streamUrl = "https://cdn-lt-live.tv2fyn.dk/env/cluster-1-e.live.nvp1/live/hls/p/1966291/e/0_vsfrv0zm/tl/main/st/0/t/EgP1FA1D39taZFVewCa42w/index-s32.m3u8",
                category = "Denmark,TV"
            ),
            TvChannel(
                id = 224,
                name = "TV 2/Øst",
                logoUrl = "https://i.imgur.com/H9l6Ulw.png",
                streamUrl = "https://cdn-lt-live.tveast.dk/env/cluster-1-e.live.nvp1/live/hls/p/1953381/e/0_zphj9q61/tl/main/st/0/t/THUB80e-ZMufZCE4pDhO0g/index-s32.m3u8",
                category = "Denmark,TV"
            ),
            TvChannel(
                id = 225,
                name = "TV 2/Nord",
                logoUrl = "https://i.imgur.com/tEJ22UW.png",
                streamUrl = "https://cdn-lt-live.tv2nord.dk/env/cluster-1-e.live.nvp1/live/hls/p/1956931/e/1_h9yfe7h2/tl/main/st/1/t/_FUn1YHQ6_P6lES4U6mmsA/index-s32.m3u8",
                category = "Denmark,TV"
            ),
            TvChannel(
                id = 226,
                name = "TV 2 Kosmopol",
                logoUrl = "https://i.imgur.com/oVmCoKY.png",
                streamUrl = "https://cdn-lt-live.tv2lorry.dk/env/cluster-1-d.live.nvp1/live/hls/p/2045321/e/1_grusx1zd/tl/main/st/0/t/rCct87c-v2SFFCvQK1BBOg/index-s32.m3u8",
                category = "Denmark,TV"
            ),
            TvChannel(
                id = 227,
                name = "TV/Midt-Vest",
                logoUrl = "https://i.imgur.com/OU7xIVa.png",
                streamUrl = "https://cdn-lt-live.tvmidtvest.dk/env/cluster-1-d.live.frp1/live/hls/p/1953371/e/1_9x5lzos9/tl/main/st/0/t/9MTEhotxVwKuatx1EVXdGg/index-s34.m3u8",
                category = "Denmark,TV/Midt-Vest"
            ),
            TvChannel(
                id = 228,
                name = "TV 2/Østjylland",
                logoUrl = "https://i.imgur.com/qEUXjHp.png",
                streamUrl = "https://cdn-lt-live.tvmidtvest.dk/env/cluster-1-d.live.frp1/live/hls/p/1953371/e/1_9x5lzos9/tl/main/st/0/t/9MTEhotxVwKuatx1EVXdGg/index-s34.m3u8",
                category = "Denmark,TV"
            ),
            TvChannel(
                id = 229,
                name = "TV 2/Bornholm",
                logoUrl = "https://i.imgur.com/cEOpXU6.png",
                streamUrl = "https://live.tv2bornholm.dk/stream/live/playlist.m3u8",
                category = "Denmark,TV"
            ),
            TvChannel(
                id = 230,
                name = "TV Storbyen",
                logoUrl = "https://i.imgur.com/QqjRqow.png",
                streamUrl = "https://5eeb3940cfaa0.streamlock.net/webtv_live/_definst_/mp4:kanalnordvest/playlist.m3u8",
                category = "Denmark,TV"
            ),
            TvChannel(
                id = 231,
                name = "Kanal Hovedstaden",
                logoUrl = "https://i.imgur.com/MCXYDwH.png",
                streamUrl = "http://khkbh.dk:8080/hls/livestream/index.m3u8",
                category = "Denmark,Kanal"
            ),
            TvChannel(
                id = 232,
                name = "Canal RTVD 4",
                logoUrl = "https://static.wikia.nocookie.net/logopedia/images/4/4e/CERTV_4_2015.png",
                streamUrl = "https://protvradiostream.com:1936/canal4rd-1/ngrp:canal4rd-1_all/playlist.m3u8",
                category = "Dominican"
            ),
            TvChannel(
                id = 233,
                name = "Aghapy TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/e/eb/AghapyTV.jpg",
                streamUrl = "https://5b622f07944df.streamlock.net/aghapy.tv/aghapy.smil/playlist.m3u8",
                category = "Egypt,Aghapy"
            ),
            TvChannel(
                id = 234,
                name = "Al Ghad Plus",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/0/06/AlGhad_TV.png",
                streamUrl = "https://playlist.fasttvcdn.com/pl/ykvm3f2fhokwxqsurp9xcg/alghad-plus/playlist.m3u8",
                category = "Egypt,Al"
            ),
            TvChannel(
                id = 235,
                name = "Al Ghad TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/0/06/AlGhad_TV.png",
                streamUrl = "https://eazyvwqssi.erbvr.com/alghadtv/alghadtv.m3u8",
                category = "Egypt,Al"
            ),
            TvChannel(
                id = 236,
                name = "Al Qahera News",
                logoUrl = "https://upload.wikimedia.org/wikipedia/ar/b/b0/%D9%82%D9%86%D8%A7%D8%A9_%D8%A7%D9%84%D9%82%D8%A7%D9%87%D8%B1%D8%A9_%D8%A7%D9%84%D8%A5%D8%AE%D8%A8%D8%A7%D8%B1%D9%8A%D8%A9.png",
                streamUrl = "https://bcovlive-a.akamaihd.net/d30cbb3350af4cb7a6e05b9eb1bfd850/eu-west-1/6057955906001/playlist.m3u8",
                category = "Egypt,Al"
            ),
            TvChannel(
                id = 237,
                name = "Alhayat TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Al-Hayat_Media_Center_Logo_%28variant_2%29.svg",
                streamUrl = "https://cdn3.wowza.com/5/OE5HREpIcEkySlNT/alhayat-live/ngrp:livestream_all/playlist.m3u8",
                category = "Egypt,Alhayat"
            ),
            TvChannel(
                id = 238,
                name = "Coptic TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/4/4c/Coptic_news.jpg",
                streamUrl = "https://5aafcc5de91f1.streamlock.net/ctvchannel.tv/ctv.smil/playlist.m3u8",
                category = "Egypt,Coptic"
            ),
            TvChannel(
                id = 239,
                name = "Huda TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/5/58/Logo_huda_%D8%AD%D8%AC%D9%85_%D9%83%D8%A8%D9%8A%D8%B1.gif",
                streamUrl = "https://cdn.bestream.io:19360/elfaro1/elfaro1.m3u8",
                category = "Egypt,Huda"
            ),
            TvChannel(
                id = 240,
                name = "Koogi TV",
                logoUrl = "",
                streamUrl = "https://5d658d7e9f562.streamlock.net/koogi.tv/koogi.smil/playlist.m3u8",
                category = "Egypt,Koogi"
            ),
            TvChannel(
                id = 241,
                name = "MBC Masr 1",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/7/7c/MBC_Masr_Logo.png",
                streamUrl = "https://mbc1-enc.edgenextcdn.net/out/v1/d5036cabf11e45bf9d0db410ca135c18/index.m3u8",
                category = "Egypt,MBC"
            ),
            TvChannel(
                id = 242,
                name = "MBC Masr 2",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/5/53/MBC_Masr_2_Logo.svg",
                streamUrl = "https://shls-masr2-ak.akamaized.net/out/v1/f683685242b549f48ea8a5171e3e993a/index.m3u8",
                category = "Egypt,MBC"
            ),
            TvChannel(
                id = 243,
                name = "Rotana Cinema",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/9/92/Rotana_Cinema_Egy.png",
                streamUrl = "https://rotana.hibridcdn.net/rotana/cinemamasr_net-7Y83PP5adWixDF93/playlist.m3u8",
                category = "Egypt,Rotana"
            ),
            TvChannel(
                id = 244,
                name = "Watan TV",
                logoUrl = "",
                streamUrl = "https://rp.tactivemedia.com/watantv_source/live/playlist.m3u8",
                category = "Egypt,Watan"
            ),
            TvChannel(
                id = 245,
                name = "ETV Ⓖ",
                logoUrl = "https://i.imgur.com/5URjPgG.png",
                streamUrl = "http://sb.err.ee/live/etv.m3u8",
                category = "Estonia,ETV"
            ),
            TvChannel(
                id = 246,
                name = "ETV2 Ⓖ",
                logoUrl = "https://i.imgur.com/fUjGHDa.png",
                streamUrl = "http://sb.err.ee/live/etv2.m3u8",
                category = "Estonia,ETV2"
            ),
            TvChannel(
                id = 247,
                name = "ETV+ Ⓖ",
                logoUrl = "https://i.imgur.com/YAubPlU.png",
                streamUrl = "http://sb.err.ee/live/etvpluss.m3u8",
                category = "Estonia,ETV+"
            ),
            TvChannel(
                id = 248,
                name = "Riigikogu",
                logoUrl = "https://i.imgur.com/7uWaZLF.png",
                streamUrl = "https://riigikogu.babahhcdn.com/bb1027/smil:riigikogu_ch1.smil/playlist.m3u8",
                category = "Estonia,Riigikogu"
            ),
            TvChannel(
                id = 249,
                name = "Taevas TV7",
                logoUrl = "https://i.imgur.com/usXedIj.png",
                streamUrl = "https://vod.tv7.fi/tv7-ee/_definst_/smil:tv7-ee.smil/playlist.m3u8",
                category = "Estonia,Taevas"
            ),
            TvChannel(
                id = 250,
                name = "Life TV Estonia",
                logoUrl = "https://i.imgur.com/JhrTB82.png",
                streamUrl = "https://lifetv.bitflip.ee/live/stream2.m3u8",
                category = "Estonia,Life"
            ),
            TvChannel(
                id = 251,
                name = "Life TV Europe",
                logoUrl = "https://i.imgur.com/JhrTB82.png",
                streamUrl = "https://lifetv.bitflip.ee/live/stream1.m3u8",
                category = "Estonia,Life"
            ),
            TvChannel(
                id = 252,
                name = "TBN Baltia",
                logoUrl = "https://i.imgur.com/rKBaK56.png",
                streamUrl = "http://dc.tbnbaltia.eu:8088/dvr/rewind-21600.m3u8",
                category = "Estonia,TBN"
            ),
            TvChannel(
                id = 253,
                name = "KVF Sjónvarp",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ab/KVF_logo_2019.svg/640px-KVF_logo_2019.svg.png",
                streamUrl = "https://w1.kringvarp.fo/uttanlands/smil:uttanlands.smil/playlist.m3u8",
                category = "Faroe"
            ),
            TvChannel(
                id = 254,
                name = "Yle TV1 Ⓖ",
                logoUrl = "https://i.imgur.com/6yXZwUL.png",
                streamUrl = "https://yletv.akamaized.net/hls/live/622365/yletv1fin/index.m3u8",
                category = "Finland,Yle"
            ),
            TvChannel(
                id = 255,
                name = "Yle TV2 Ⓖ",
                logoUrl = "https://i.imgur.com/4xkc6PL.png",
                streamUrl = "https://yletv.akamaized.net/hls/live/622366/yletv2fin/index.m3u8",
                category = "Finland,Yle"
            ),
            TvChannel(
                id = 256,
                name = "Yle Teema Fem Ⓖ",
                logoUrl = "https://i.imgur.com/iDljufz.png",
                streamUrl = "https://yletv.akamaized.net/hls/live/622367/yletvteemafemfin/index.m3u8",
                category = "Finland,Yle"
            ),
            TvChannel(
                id = 257,
                name = "Taivas TV7",
                logoUrl = "https://i.imgur.com/a4iNVXA.png",
                streamUrl = "https://vod.tv7.fi/tv7-fi/_definst_/smil:tv7-fi.smil/playlist.m3u8",
                category = "Finland,Taivas"
            ),
            TvChannel(
                id = 258,
                name = "Himlen TV7",
                logoUrl = "https://i.imgur.com/a4iNVXA.png",
                streamUrl = "https://vod.tv7.fi/tv7-se/_definst_/smil:tv7-se.smil/playlist.m3u8",
                category = "Finland,Himlen"
            ),
            TvChannel(
                id = 259,
                name = "MTV Uutiset Live",
                logoUrl = "https://i.imgur.com/IyB6mIb.png",
                streamUrl = "https://live.streaming.a2d.tv/asset/20025962.isml/.m3u8",
                category = "Finland,MTV"
            ),
            TvChannel(
                id = 260,
                name = "Nopola News",
                logoUrl = "https://i.imgur.com/gOj8J6O.png",
                streamUrl = "https://virta2.nopolanews.fi:8443/live/smil:Stream1.smil/playlist.m3u8",
                category = "Finland,Nopola"
            ),
            TvChannel(
                id = 261,
                name = "När-TV Ⓢ",
                logoUrl = "https://i.imgur.com/Ht5yePq.png",
                streamUrl = "https://streaming.nartv.fi/live/ngrp:NAR_TV.stream_all/playlist.m3u8",
                category = "Finland,När-TV"
            ),
            TvChannel(
                id = 262,
                name = "YleX Studio Live",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cf/YleX.svg/450px-YleX.svg.png",
                streamUrl = "https://ylestudiolive.akamaized.net/hls/live/2007826/ylestudiolive-YleX/master.m3u8",
                category = "Finland,YleX"
            ),
            TvChannel(
                id = 263,
                name = "Järviradio TV",
                logoUrl = "https://jarviradio.fi/jrtv2/wp-content/uploads/2022/01/jrtv1.jpg",
                streamUrl = "https://streamer.radiotaajuus.fi/memfs/47f113bf-04ea-493b-a9d4-52945fd9db31.m3u8",
                category = "Finland,Järviradio"
            ),
            TvChannel(
                id = 264,
                name = "Arte Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Arte_Logo_2017.svg/12px-Arte_Logo_2017.svg.png",
                streamUrl = "https://artesimulcast.akamaized.net/hls/live/2031003/artelive_fr/index.m3u8",
                category = "France,Arte"
            ),
            TvChannel(
                id = 265,
                name = "NRJ 12",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/45/NRJ12_logo_2015.svg/749px-NRJ12_logo_2015.svg.png",
                streamUrl = "https://nrj12.nrjaudio.fm/hls/live/2038374/nrj_12/master.m3u8",
                category = "France,NRJ"
            ),
            TvChannel(
                id = 266,
                name = "CGTN Français",
                logoUrl = "https://i.imgur.com/fMsJYzl.png",
                streamUrl = "https://news.cgtn.com/resource/live/french/cgtn-f.m3u8",
                category = "France,CGTN"
            ),
            TvChannel(
                id = 267,
                name = "TV5 Monde Info",
                logoUrl = "https://i.imgur.com/NcysrWH.png",
                streamUrl = "https://ott.tv5monde.com/Content/HLS/Live/channel(info)/index.m3u8",
                category = "France,TV5"
            ),
            TvChannel(
                id = 268,
                name = "TV5 Monde FBS",
                logoUrl = "https://i.imgur.com/uPmwTo9.png",
                streamUrl = "https://ott.tv5monde.com/Content/HLS/Live/channel(fbs)/index.m3u8",
                category = "France,TV5"
            ),
            TvChannel(
                id = 269,
                name = "TV5 Monde Europe",
                logoUrl = "https://i.imgur.com/uPmwTo9.png",
                streamUrl = "https://ott.tv5monde.com/Content/HLS/Live/channel(europe)/index.m3u8",
                category = "France,TV5"
            ),
            TvChannel(
                id = 270,
                name = "First Channel (1TV)",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0d/Pirveli_Arkhi_Logo_2022.svg/512px-Pirveli_Arkhi_Logo_2022.svg.png",
                streamUrl = "https://tv.cdn.xsg.ge/gpb-1tv/index.m3u8",
                category = "Georgia,First"
            ),
            TvChannel(
                id = 271,
                name = "First Channel /Education/ (2TV)",
                logoUrl = "https://upload.wikimedia.org/wikipedia/ka/c/c9/2_Tv_Logo.jpg",
                streamUrl = "https://tv.cdn.xsg.ge/gpb-2tv/index.m3u8",
                category = "Georgia,First"
            ),
            TvChannel(
                id = 272,
                name = "Imedi TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/2/2a/Imlogo_2020.png",
                streamUrl = "https://tv.cdn.xsg.ge/imedihd/index.m3u8",
                category = "Georgia,Imedi"
            ),
            TvChannel(
                id = 273,
                name = "Mtavari Arkhi",
                logoUrl = "https://i.imgur.com/tLtGnJW.png",
                streamUrl = "https://bozztv.com/36bay2/mtavariarxi/playlist.m3u8",
                category = "Georgia,Mtavari"
            ),
            TvChannel(
                id = 274,
                name = "Formula",
                logoUrl = "https://i.imgur.com/fsqBn8G.png",
                streamUrl = "https://c4635.cdn.xsg.ge/c4635/TVFormula/index.m3u8",
                category = "Georgia,Formula"
            ),
            TvChannel(
                id = 275,
                name = "Pos TV",
                logoUrl = "https://i.imgur.com/UOiXFEW.png",
                streamUrl = "https://live.postv.media/stream/index.m3u8",
                category = "Georgia,Pos"
            ),
            TvChannel(
                id = 276,
                name = "Euronews Georgia Ⓖ",
                logoUrl = "https://i.imgur.com/VNJ4soR.png",
                streamUrl = "https://live2.tvg.ge/eng/EURONEWSGEORGIA/playlist.m3u8",
                category = "Georgia,Euronews"
            ),
            TvChannel(
                id = 277,
                name = "Das Erste Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ca/Das_Erste_2014.svg/640px-Das_Erste_2014.svg.png",
                streamUrl = "https://daserste-live.ard-mcdn.de/daserste/live/hls/de/master.m3u8",
                category = "Germany,Das"
            ),
            TvChannel(
                id = 278,
                name = "ZDF Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/ZDF_logo.svg/640px-ZDF_logo.svg.png",
                streamUrl = "http://zdf-hls-15.akamaized.net/hls/live/2016498/de/veryhigh/master.m3u8",
                category = "Germany,ZDF"
            ),
            TvChannel(
                id = 279,
                name = "3sat Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/81/3sat_2019.svg/640px-3sat_2019.svg.png",
                streamUrl = "https://zdf-hls-18.akamaized.net/hls/live/2016501/dach/veryhigh/master.m3u8",
                category = "Germany,3sat"
            ),
            TvChannel(
                id = 280,
                name = "ARD Alpha Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/ARD_alpha.svg/640px-ARD_alpha.svg.png",
                streamUrl = "https://mcdn.br.de/br/fs/ard_alpha/hls/de/master.m3u8",
                category = "Germany,ARD"
            ),
            TvChannel(
                id = 281,
                name = "ARTE Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Arte_Logo_2017.svg/186px-Arte_Logo_2017.svg.png",
                streamUrl = "https://artesimulcast.akamaized.net/hls/live/2030993/artelive_de/index.m3u8",
                category = "Germany,ARTE"
            ),
            TvChannel(
                id = 282,
                name = "DELUXE MUSIC",
                logoUrl = "https://i.imgur.com/E65GQN9.png",
                streamUrl = "https://sdn-global-live-streaming-packager-cache.3qsdn.com/13456/13456_264_live.m3u8",
                category = "Germany,DELUXE"
            ),
            TvChannel(
                id = 283,
                name = "DELUXE MUSIC DANCE BY KONTOR",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c2/Deluxe_Dance_by_Kontor_Logo_2023.svg/666px-Deluxe_Dance_by_Kontor_Logo_2023.svg.png",
                streamUrl = "https://sdn-global-live-streaming-packager-cache.3qsdn.com/64733/64733_264_live.m3u8",
                category = "Germany,DELUXE"
            ),
            TvChannel(
                id = 284,
                name = "DELUXE MUSIC RAP",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/07/Deluxe_Rap_Logo_2023.svg/666px-Deluxe_Rap_Logo_2023.svg.png",
                streamUrl = "https://sdn-global-live-streaming-packager-cache.3qsdn.com/65183/65183_264_live.m3u8",
                category = "Germany,DELUXE"
            ),
            TvChannel(
                id = 285,
                name = "SCHLAGER DELUXE",
                logoUrl = "https://i.imgur.com/YPpgUOg.png",
                streamUrl = "https://sdn-global-live-streaming-packager-cache.3qsdn.com/26658/26658_264_live.m3u8",
                category = "Germany,SCHLAGER"
            ),
            TvChannel(
                id = 286,
                name = "KiKa Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f5/Kika_2012.svg/640px-Kika_2012.svg.png",
                streamUrl = "https://kikageohls.akamaized.net/hls/live/2022693/livetvkika_de/master.m3u8",
                category = "Germany,KiKa"
            ),
            TvChannel(
                id = 287,
                name = "ONE Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3d/One_2022.svg/640px-One_2022.svg.png",
                streamUrl = "https://mcdn-one.ard.de/ardone/hls/master.m3u8",
                category = "Germany,ONE"
            ),
            TvChannel(
                id = 288,
                name = "Phoenix Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Phoenix-logo-2018.svg/640px-Phoenix-logo-2018.svg.png",
                streamUrl = "https://zdf-hls-19.akamaized.net/hls/live/2016502/de/veryhigh/master.m3u8",
                category = "Germany,Phoenix"
            ),
            TvChannel(
                id = 289,
                name = "Tagesschau24",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/24/Tagesschau24-2012.svg/640px-Tagesschau24-2012.svg.png",
                streamUrl = "https://tagesschau.akamaized.net/hls/live/2020115/tagesschau/tagesschau_1/master.m3u8",
                category = "Germany,Tagesschau24"
            ),
            TvChannel(
                id = 290,
                name = "Welt",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3b/Welt_TV_Logo_2016.svg/640px-Welt_TV_Logo_2016.svg.png",
                streamUrl = "https://w-live2weltcms.akamaized.net/hls/live/2041019/Welt-LivePGM/index.m3u8",
                category = "Germany,Welt"
            ),
            TvChannel(
                id = 291,
                name = "ZDFinfo Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/34/ZDFinfo_2011.svg/640px-ZDFinfo_2011.svg.png",
                streamUrl = "https://zdf-hls-17.akamaized.net/hls/live/2016500/de/veryhigh/master.m3u8",
                category = "Germany,ZDFinfo"
            ),
            TvChannel(
                id = 292,
                name = "ZDFneo Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8c/ZDFneo2017_Logo.svg/569px-ZDFneo2017_Logo.svg.png",
                streamUrl = "https://zdf-hls-16.akamaized.net/hls/live/2016499/de/veryhigh/master.m3u8",
                category = "Germany,ZDFneo"
            ),
            TvChannel(
                id = 293,
                name = "BR Nord Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9b/Logo_BR_Fernsehen_2021.svg/768px-Logo_BR_Fernsehen_2021.svg.png",
                streamUrl = "https://mcdn.br.de/br/fs/bfs_nord/hls/de/master.m3u8",
                category = "Germany,BR"
            ),
            TvChannel(
                id = 294,
                name = "BR Süd Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9b/Logo_BR_Fernsehen_2021.svg/768px-Logo_BR_Fernsehen_2021.svg.png",
                streamUrl = "https://brcdn.vo.llnwd.net/br/fs/bfs_sued/hls/de/master.m3u8",
                category = "Germany,BR"
            ),
            TvChannel(
                id = 295,
                name = "HR Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ea/HR-Fernsehen_Logo_2023.svg/640px-HR-Fernsehen_Logo_2023.svg.png",
                streamUrl = "https://hrhls.akamaized.net/hls/live/2024525/hrhls/master.m3u8",
                category = "Germany,HR"
            ),
            TvChannel(
                id = 296,
                name = "MDR Sachsen Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/61/MDR_Logo_2017.svg/640px-MDR_Logo_2017.svg.png",
                streamUrl = "https://mdrtvsnhls.akamaized.net/hls/live/2016928/mdrtvsn/master.m3u8",
                category = "Germany,MDR"
            ),
            TvChannel(
                id = 297,
                name = "MDR Sachsen-Anhalt Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/61/MDR_Logo_2017.svg/640px-MDR_Logo_2017.svg.png",
                streamUrl = "https://mdrtvsahls.akamaized.net/hls/live/2016879/mdrtvsa/master.m3u8",
                category = "Germany,MDR"
            ),
            TvChannel(
                id = 298,
                name = "MDR Thüringen Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/61/MDR_Logo_2017.svg/640px-MDR_Logo_2017.svg.png",
                streamUrl = "https://mdrtvthhls.akamaized.net/hls/live/2016880/mdrtvth/master.m3u8",
                category = "Germany,MDR"
            ),
            TvChannel(
                id = 299,
                name = "NDR Hamburg Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e9/Logo_NDR_Fernsehen_2017.svg/578px-Logo_NDR_Fernsehen_2017.svg.png",
                streamUrl = "https://mcdn.ndr.de/ndr/hls/ndr_fs/ndr_hh/master.m3u8",
                category = "Germany,NDR"
            ),
            TvChannel(
                id = 300,
                name = "NDR Mecklenburg-Vorpommern Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e9/Logo_NDR_Fernsehen_2017.svg/578px-Logo_NDR_Fernsehen_2017.svg.png",
                streamUrl = "https://mcdn.ndr.de/ndr/hls/ndr_fs/ndr_mv/master.m3u8",
                category = "Germany,NDR"
            ),
            TvChannel(
                id = 301,
                name = "NDR Niedersachsen Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e9/Logo_NDR_Fernsehen_2017.svg/578px-Logo_NDR_Fernsehen_2017.svg.png",
                streamUrl = "https://mcdn.ndr.de/ndr/hls/ndr_fs/ndr_nds/master.m3u8",
                category = "Germany,NDR"
            ),
            TvChannel(
                id = 302,
                name = "NDR Schleswig-Holstein Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e9/Logo_NDR_Fernsehen_2017.svg/578px-Logo_NDR_Fernsehen_2017.svg.png",
                streamUrl = "https://mcdn.ndr.de/ndr/hls/ndr_fs/ndr_sh/master.m3u8",
                category = "Germany,NDR"
            ),
            TvChannel(
                id = 303,
                name = "Radio Bremen Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/39/Logo_Radio_Bremen.svg/640px-Logo_Radio_Bremen.svg.png",
                streamUrl = "https://rbhlslive.akamaized.net/hls/live/2020435/rbfs/master.m3u8",
                category = "Germany,Radio"
            ),
            TvChannel(
                id = 304,
                name = "RBB Berlin Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Rbb_Fernsehen_Logo_2017.08.svg/640px-Rbb_Fernsehen_Logo_2017.08.svg.png",
                streamUrl = "https://rbb-hls-berlin.akamaized.net/hls/live/2017824/rbb_berlin/master.m3u8",
                category = "Germany,RBB"
            ),
            TvChannel(
                id = 305,
                name = "RBB Brandenburg Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Rbb_Fernsehen_Logo_2017.08.svg/640px-Rbb_Fernsehen_Logo_2017.08.svg.png",
                streamUrl = "https://rbb-hls-brandenburg.akamaized.net/hls/live/2017825/rbb_brandenburg/master.m3u8",
                category = "Germany,RBB"
            ),
            TvChannel(
                id = 306,
                name = "SR Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9c/SR_Fernsehen_Logo_2023.svg/538px-SR_Fernsehen_Logo_2023.svg.png",
                streamUrl = "https://srfs.akamaized.net/hls/live/689649/srfsgeo/index.m3u8",
                category = "Germany,SR"
            ),
            TvChannel(
                id = 307,
                name = "SWR Baden-Württemberg Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/26/SWR_Logo_2023.svg/640px-SWR_Logo_2023.svg.png",
                streamUrl = "https://swrbwd-hls.akamaized.net/hls/live/2018672/swrbwd/master.m3u8",
                category = "Germany,SWR"
            ),
            TvChannel(
                id = 308,
                name = "SWR Rheinland-Pfalz Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/26/SWR_Logo_2023.svg/640px-SWR_Logo_2023.svg.png",
                streamUrl = "https://swrrpd-hls.akamaized.net/hls/live/2018676/swrrpd/master.m3u8",
                category = "Germany,SWR"
            ),
            TvChannel(
                id = 309,
                name = "WDR Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b3/Wdr_fernsehen_logo_2016.svg/640px-Wdr_fernsehen_logo_2016.svg.png",
                streamUrl = "https://wdrfs247.akamaized.net/hls/live/681509/wdr_msl4_fs247/index.m3u8",
                category = "Germany,WDR"
            ),
            TvChannel(
                id = 310,
                name = "NDR International",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e9/Logo_NDR_Fernsehen_2017.svg/578px-Logo_NDR_Fernsehen_2017.svg.png",
                streamUrl = "https://ndrint.akamaized.net/hls/live/2020766/ndr_int/index.m3u8",
                category = "Germany,NDR"
            ),
            TvChannel(
                id = 311,
                name = "ERT News",
                logoUrl = "https://i.imgur.com/saIGLvr.png",
                streamUrl = "https://ertflix.ascdn.broadpeak.io/ertlive/ertnews/default/index.m3u8",
                category = "Greece,ERT"
            ),
            TvChannel(
                id = 312,
                name = "ERT World",
                logoUrl = "https://i.imgur.com/KsMTWYw.png",
                streamUrl = "https://ertflix-ertworld.siliconweb.com/mpegts/618618_3479286/master_mpegts.m3u8",
                category = "Greece,ERT"
            ),
            TvChannel(
                id = 313,
                name = "ERT Kids Ⓖ",
                logoUrl = "https://i.imgur.com/XkSR66q.png",
                streamUrl = "https://ertflix.akamaized.net/ertlive/kids/default/index.m3u8",
                category = "Greece,ERT"
            ),
            TvChannel(
                id = 314,
                name = "ERT Music Ⓖ",
                logoUrl = "https://i.imgur.com/VrKgtfY.png",
                streamUrl = "https://ertflix.akamaized.net/ertlive/music/default/index.m3u8",
                category = "Greece,ERT"
            ),
            TvChannel(
                id = 315,
                name = "Vouli TV",
                logoUrl = "https://i.imgur.com/1vqW7lc.png",
                streamUrl = "https://diavlos-cache.cnt.grnet.gr/parltv/webtv-1b.sdp/playlist.m3u8",
                category = "Greece,Vouli"
            ),
            TvChannel(
                id = 316,
                name = "RIK Sat",
                logoUrl = "https://i.imgur.com/9edlXHP.png",
                streamUrl = "https://l3.cloudskep.com/cybcsat/abr/playlist.m3u8",
                category = "Greece,RIK"
            ),
            TvChannel(
                id = 317,
                name = "Mega News",
                logoUrl = "https://i.imgur.com/Z3k7iA0.png",
                streamUrl = "https://c98db5952cb54b358365984178fb898a.msvdn.net/live/S99841657/NU0xOarAMJ5X/playlist.m3u8",
                category = "Greece,Mega"
            ),
            TvChannel(
                id = 318,
                name = "ANT1",
                logoUrl = "https://i.imgur.com/xDdVa9U.png",
                streamUrl = "https://lcdn.antennaplus.gr/r86d08d448885424196f6cd3ddc5d1489/eu-central-1/6415884360001/playlist_dvr.m3u8",
                category = "Greece,ANT1"
            ),
            TvChannel(
                id = 319,
                name = "Star",
                logoUrl = "https://i.imgur.com/Hp0stVQ.png",
                streamUrl = "https://livestar.siliconweb.com/starvod/star4/star4.m3u8",
                category = "Greece,Star"
            ),
            TvChannel(
                id = 320,
                name = "Star International",
                logoUrl = "https://i.imgur.com/Hp0stVQ.png",
                streamUrl = "https://livestar.siliconweb.com/starvod/star_int/star_int.m3u8",
                category = "Greece,Star"
            ),
            TvChannel(
                id = 321,
                name = "AlphaTV",
                logoUrl = "https://i.imgur.com/bAVGX0l.png",
                streamUrl = "https://alphatvlive2.siliconweb.com/alphatvlive/live_abr/playlist.m3u8",
                category = "Greece,AlphaTV"
            ),
            TvChannel(
                id = 322,
                name = "Skai TV",
                logoUrl = "https://i.imgur.com/TSg7B8X.png",
                streamUrl = "http://skai-live.siliconweb.com/media/cambria4/index.m3u8",
                category = "Greece,Skai"
            ),
            TvChannel(
                id = 323,
                name = "Open TV Ⓖ",
                logoUrl = "https://i.imgur.com/HzBmvPT.png",
                streamUrl = "https://liveopen.siliconweb.com/openTvLive/liveopen/playlist.m3u8",
                category = "Greece,Open"
            ),
            TvChannel(
                id = 324,
                name = "Makedonia TV",
                logoUrl = "https://i.imgur.com/90iDHbQ.png",
                streamUrl = "https://lcdn.antennaplus.gr/r444865966c0847fca53b9b0c133af7a9/eu-central-1/6415884360001/playlist_dvr.m3u8",
                category = "Greece,Makedonia"
            ),
            TvChannel(
                id = 325,
                name = "Action24",
                logoUrl = "https://i.imgur.com/Zi1YohT.png",
                streamUrl = "https://actionlive.siliconweb.com/actionabr/actiontv/playlist.m3u8",
                category = "Greece,Action24"
            ),
            TvChannel(
                id = 326,
                name = "Alert",
                logoUrl = "https://i.imgur.com/xqa87lG.png",
                streamUrl = "https://itv.streams.ovh/ALEERT/ALEERT/playlist.m3u8",
                category = "Greece,Alert"
            ),
            TvChannel(
                id = 327,
                name = "ART",
                logoUrl = "https://i.imgur.com/7TyUxLj.png",
                streamUrl = "https://rumble.com/live-hls/7113t0/playlist.m3u8",
                category = "Greece,ART"
            ),
            TvChannel(
                id = 328,
                name = "Blue Sky",
                logoUrl = "https://i.imgur.com/rzuQslM.png",
                streamUrl = "https://cdn5.smart-tv-data.com/bluesky/bluesky-live/playlist.m3u8",
                category = "Greece,Blue"
            ),
            TvChannel(
                id = 329,
                name = "High TV",
                logoUrl = "https://i.imgur.com/wHzCGry.png",
                streamUrl = "https://live.streams.ovh/hightv/hightv/playlist.m3u8",
                category = "Greece,High"
            ),
            TvChannel(
                id = 330,
                name = "Kontra",
                logoUrl = "https://i.imgur.com/ROZ9VfV.png",
                streamUrl = "https://kontralive.siliconweb.com/live/kontratv/playlist.m3u8",
                category = "Greece,Kontra"
            ),
            TvChannel(
                id = 331,
                name = "Naftemporiki TV",
                logoUrl = "https://i.imgur.com/9OFdMud.png",
                streamUrl = "https://stream-188125.castr.net/631af9c016e5eace19ff9a5b/live_048998706a2311ee83b33fe7fbad252d/index.fmp4.m3u8",
                category = "Greece,Naftemporiki"
            ),
            TvChannel(
                id = 332,
                name = "One Channel",
                logoUrl = "https://i.imgur.com/GwKaHbM.png",
                streamUrl = "https://onechannel.siliconweb.com/one/stream/chunks_dvr.m3u8",
                category = "Greece,One"
            ),
            TvChannel(
                id = 333,
                name = "4E",
                logoUrl = "https://i.imgur.com/Ed085oJ.png",
                streamUrl = "http://eu2.tv4e.gr:1935/live/myStream.sdp/playlist.m3u8",
                category = "Greece,4E"
            ),
            TvChannel(
                id = 334,
                name = "DION",
                logoUrl = "https://i.imgur.com/13MverN.png",
                streamUrl = "https://rtmp.win:3650/live/diontvlive.m3u8",
                category = "Greece,DION"
            ),
            TvChannel(
                id = 335,
                name = "Euro",
                logoUrl = "https://i.imgur.com/mHCk05E.png",
                streamUrl = "https://live20.bozztv.com/akamaissh101/ssh101/eurotvlive/playlist.m3u8",
                category = "Greece,Euro"
            ),
            TvChannel(
                id = 336,
                name = "Gnomi",
                logoUrl = "https://i.imgur.com/mHCk05E.png",
                streamUrl = "https://live.streams.ovh:8081/gnomitv/index.m3u8",
                category = "Greece,Gnomi"
            ),
            TvChannel(
                id = 337,
                name = "Pella",
                logoUrl = "https://i.imgur.com/pwUkkGL.jpeg",
                streamUrl = "https://video.streams.ovh:1936/pellatv/pellatv/playlist.m3u8",
                category = "Greece,Pella"
            ),
            TvChannel(
                id = 338,
                name = "Pontos",
                logoUrl = "https://i.imgur.com/sbTxP6o.png",
                streamUrl = "https://rtmp.win:3842/live/recme1live.m3u8",
                category = "Greece,Pontos"
            ),
            TvChannel(
                id = 339,
                name = "TV 100",
                logoUrl = "https://i.imgur.com/9rtf8OR.png",
                streamUrl = "https://panel.gwebstream.eu:19360/tv100skg/tv100skg.m3u8",
                category = "Greece,TV"
            ),
            TvChannel(
                id = 340,
                name = "Vergina",
                logoUrl = "https://i.imgur.com/cpF6wvR.png",
                streamUrl = "https://verginanews.gr:8443/hls_live/stream1.m3u8",
                category = "Greece,Vergina"
            ),
            TvChannel(
                id = 341,
                name = "Best TV",
                logoUrl = "https://i.imgur.com/VA13E3w.png",
                streamUrl = "https://besttv.siliconweb.com/bestTV/live_abr/playlist.m3u8",
                category = "Greece,Best"
            ),
            TvChannel(
                id = 342,
                name = "Hlektra",
                logoUrl = "https://i.imgur.com/LbogUPS.png",
                streamUrl = "https://live20.bozztv.com/giatv/giatv-hlektratv/hlektratv/playlist.m3u8",
                category = "Greece,Hlektra"
            ),
            TvChannel(
                id = 343,
                name = "Ionian Channel",
                logoUrl = "https://i.imgur.com/ADVYeQd.png",
                streamUrl = "https://stream.ioniantv.gr/ionian/live_abr/playlist.m3u8",
                category = "Greece,Ionian"
            ),
            TvChannel(
                id = 344,
                name = "Lepanto",
                logoUrl = "https://i.imgur.com/h6Tqe0k.png",
                streamUrl = "https://fr.crystalweb.net:1936/lepantotv/lepantotv/playlist.m3u8",
                category = "Greece,Lepanto"
            ),
            TvChannel(
                id = 345,
                name = "Lychnos",
                logoUrl = "hhttps://i.imgur.com/JYSlBfY.png",
                streamUrl = "https://thor.mental-media.gr:19360/imp/imp.m3u8",
                category = "Greece,Lychnos"
            ),
            TvChannel(
                id = 346,
                name = "Mesogeios TV",
                logoUrl = "https://i.imgur.com/tr0Lf9K.png",
                streamUrl = "https://rtmp.win:3793/live/mesogeiostvlive.m3u8",
                category = "Greece,Mesogeios"
            ),
            TvChannel(
                id = 347,
                name = "Epsilon",
                logoUrl = "https://i.imgur.com/vUQSDvZ.png",
                streamUrl = "https://neon.streams.gr:8081/epsilontv/index.m3u8",
                category = "Greece,Epsilon"
            ),
            TvChannel(
                id = 348,
                name = "91NRG",
                logoUrl = "https://i.imgur.com/g1pCRRG.png",
                streamUrl = "http://tv.nrg91.gr:1935/onweb/live/master.m3u8",
                category = "Greece,91NRG"
            ),
            TvChannel(
                id = 349,
                name = "Thessalia",
                logoUrl = "https://i.imgur.com/KXz67LY.png",
                streamUrl = "https://thessaliachannel.gr:3339/live/thesstvlive.m3u8",
                category = "Greece,Thessalia"
            ),
            TvChannel(
                id = 350,
                name = "TRT",
                logoUrl = "https://i.imgur.com/g0jPOcC.png",
                streamUrl = "https://av.hellasnet.tv/rst/trt/index.m3u8",
                category = "Greece,TRT"
            ),
            TvChannel(
                id = 351,
                name = "Acheloos",
                logoUrl = "https://i.imgur.com/5SVMxcu.png",
                streamUrl = "https://acheloostv.streamings.gr/live/stream/index.m3u8",
                category = "Greece,Acheloos"
            ),
            TvChannel(
                id = 352,
                name = "ART TV",
                logoUrl = "https://i.imgur.com/LyCqQvx.png",
                streamUrl = "https://rtmp.win:3696/live/arttvgrlive.m3u8",
                category = "Greece,ART"
            ),
            TvChannel(
                id = 353,
                name = "Corfu",
                logoUrl = "https://i.imgur.com/dCMqo8w.jpeg",
                streamUrl = "https://itv.streams.ovh:1936/corfuchannel/corfuchannel/playlist.m3u8",
                category = "Greece,Corfu"
            ),
            TvChannel(
                id = 354,
                name = "Epirus TV 1",
                logoUrl = "https://i.imgur.com/QB3aSl1.png",
                streamUrl = "https://rtmp.win:3929/live/epiruslive.m3u8",
                category = "Greece,Epirus"
            ),
            TvChannel(
                id = 355,
                name = "Start",
                logoUrl = "https://i.imgur.com/nrEtmBN.png",
                streamUrl = "https://live.cast-control.eu/StartMedia/StartMedia/playlist.m3u8",
                category = "Greece,Start"
            ),
            TvChannel(
                id = 356,
                name = "Center TV",
                logoUrl = "https://i.imgur.com/52JW71Q.png",
                streamUrl = "https://eu1.streams.gr:8081/centertv/index.m3u8",
                category = "Greece,Center"
            ),
            TvChannel(
                id = 357,
                name = "Delta Evros",
                logoUrl = "https://i.imgur.com/PDfSkRF.png",
                streamUrl = "http://81.171.10.42:1935/liveD/DStream.sdp/chunklist_w819085920.m3u8",
                category = "Greece,Delta"
            ),
            TvChannel(
                id = 358,
                name = "Thraki Net TV",
                logoUrl = "https://i.imgur.com/DV0I0ed.png",
                streamUrl = "https://cdn.onestreaming.com/thrakinettv/thrakinettv/playlist.m3u8",
                category = "Greece,Thraki"
            ),
            TvChannel(
                id = 359,
                name = "Creta",
                logoUrl = "https://i.imgur.com/x0qK8IE.png",
                streamUrl = "http://live.streams.ovh:1935/tvcreta/tvcreta/playlist.m3u8",
                category = "Greece,Creta"
            ),
            TvChannel(
                id = 360,
                name = "Kriti 1",
                logoUrl = "https://i.imgur.com/C1ucQeC.png",
                streamUrl = "https://livetv.streams.ovh:8081/kriti/index.m3u8",
                category = "Greece,Kriti"
            ),
            TvChannel(
                id = 361,
                name = "Kriti TV",
                logoUrl = "https://i.imgur.com/eLhYMmc.png",
                streamUrl = "https://cretetvlive.siliconweb.com/cretetv/liveabr/playlist.m3u8",
                category = "Greece,Kriti"
            ),
            TvChannel(
                id = 362,
                name = "NEA",
                logoUrl = "https://i.imgur.com/nvNW8G7.png",
                streamUrl = "https://live.neatv.gr:8888/hls/neatv_high/index.m3u8",
                category = "Greece,NEA"
            ),
            TvChannel(
                id = 363,
                name = "TeleKriti",
                logoUrl = "https://i.imgur.com/18ZYiyi.png",
                streamUrl = "https://neon.streams.gr:8081/telekriti/index.m3u8",
                category = "Greece,TeleKriti"
            ),
            TvChannel(
                id = 364,
                name = "Samiaki TV",
                logoUrl = "https://i.imgur.com/aV5QoNG.png",
                streamUrl = "http://live.cast-control.eu:1935/samiaki/samiaki/playlist.m3u8",
                category = "Greece,Samiaki"
            ),
            TvChannel(
                id = 365,
                name = "Syros TV1",
                logoUrl = "https://i.imgur.com/duXHyvN.png",
                streamUrl = "https://eco.streams.ovh:1936/syrostv1/syrostv1/playlist.m3u8",
                category = "Greece,Syros"
            ),
            TvChannel(
                id = 366,
                name = "RTHK TV 31",
                logoUrl = "https://i.imgur.com/kf818kM.png",
                streamUrl = "https://rthktv31-live.akamaized.net/hls/live/2036818/RTHKTV31/master.m3u8",
                category = "Hong"
            ),
            TvChannel(
                id = 367,
                name = "RTHK TV 32",
                logoUrl = "https://i.imgur.com/MXLuUoU.png",
                streamUrl = "https://rthktv32-live.akamaized.net/hls/live/2036819/RTHKTV32/master.m3u8",
                category = "Hong"
            ),
            TvChannel(
                id = 368,
                name = "HOY TV Ⓖ",
                logoUrl = "https://i.imgur.com/NfVZPTT.png",
                streamUrl = "https://hoytv-live-stream.hoy.tv/ch78/index-fhd.m3u8",
                category = "Hong"
            ),
            TvChannel(
                id = 369,
                name = "ATV",
                logoUrl = "https://onlinestream.live/logos/4739.png",
                streamUrl = "http://streamservers.atv.hu:80/atvlive/atvstream_2_aac/playlist.m3u8",
                category = "Hungary,ATV"
            ),
            TvChannel(
                id = 370,
                name = "Fix TV",
                logoUrl = "https://onlinestream.live/logos/1833.png",
                streamUrl = "https://fixhd.tv:8082/fix/1080i/playlist.m3u8",
                category = "Hungary,Fix"
            ),
            TvChannel(
                id = 371,
                name = "EWTN TV",
                logoUrl = "https://katolikus.tv/wp-content/themes/bonum/img/ewtn-badge.jpg",
                streamUrl = "https://hls.iptvservice.eu/hls/ewtn-hd.m3u8",
                category = "Hungary,EWTN"
            ),
            TvChannel(
                id = 372,
                name = "Apostol TV",
                logoUrl = "https://www.apostoltv.hu/images/header-logo.png",
                streamUrl = "https://live.apostoltv.hu/live/playlist.m3u8",
                category = "Hungary,Apostol"
            ),
            TvChannel(
                id = 373,
                name = "MUSICPlus",
                logoUrl = "",
                streamUrl = "http://s02.diazol.hu:10192/stream.m3u8",
                category = "Hungary,MUSICPlus"
            ),
            TvChannel(
                id = 374,
                name = "Oxygen Music",
                logoUrl = "",
                streamUrl = "https://oxygenmusic.hu:2443/hls/oxygenmusic.m3u8",
                category = "Hungary,Oxygen"
            ),
            TvChannel(
                id = 375,
                name = "Dance TV",
                logoUrl = "",
                streamUrl = "https://m1b2.worldcast.tv/dancetelevisionone/2/dancetelevisionone.m3u8",
                category = "Hungary,Dance"
            ),
            TvChannel(
                id = 376,
                name = "Izaura TV",
                logoUrl = "https://onlinestream.live/logos/6141.png",
                streamUrl = "http://78.109.104.240:8000/play/a0ch/index.m3u8?HasBahCa.m3u8",
                category = "Hungary,Izaura"
            ),
            TvChannel(
                id = 377,
                name = "Parlamenti közvetítés",
                logoUrl = "",
                streamUrl = "https://plenaris.parlament.hu:446/edgelive/smil:mkogyplen.smil/playlist.m3u8",
                category = "Hungary,Parlamenti"
            ),
            TvChannel(
                id = 378,
                name = "Parlamenti TAB közvetítés",
                logoUrl = "",
                streamUrl = "https://tab.parlament.hu:446/edgelive/smil:mkogytab.smil/playlist.m3u8",
                category = "Hungary,Parlamenti"
            ),
            TvChannel(
                id = 379,
                name = "Balaton TV",
                logoUrl = "https://i.imgur.com/ip8L5Vt.jpg",
                streamUrl = "https://stream.iptvservice.eu/hls/balatontv.m3u8",
                category = "Hungary,Balaton"
            ),
            TvChannel(
                id = 380,
                name = "Budakalász",
                logoUrl = "https://i.imgur.com/MGkvVQg.png",
                streamUrl = "https://stream.streaming4u.hu/TVBudakalasz/tracks-v1a1/mono.m3u8",
                category = "Hungary,Budakalász"
            ),
            TvChannel(
                id = 381,
                name = "Komlos TV",
                logoUrl = "https://i.imgur.com/MDYb5yz.png",
                streamUrl = "https://stream.streaming4u.hu/KomlosTV/tracks-v1a1/mono.m3u8",
                category = "Hungary,Komlos"
            ),
            TvChannel(
                id = 382,
                name = "Ózdi Városi TV",
                logoUrl = "https://i.imgur.com/5cOpdRp.jpg",
                streamUrl = "https://stream.unrealhosting.hu:443/hls/ozdtv/live.m3u8",
                category = "Hungary,Ózdi"
            ),
            TvChannel(
                id = 383,
                name = "Pannon RTV",
                logoUrl = "https://i.imgur.com/iD5tCjX.png",
                streamUrl = "https://stream.unrealhosting.hu:443/hls/pannonrtv/live.m3u8",
                category = "Hungary,Pannon"
            ),
            TvChannel(
                id = 384,
                name = "TV7 Békéscsaba",
                logoUrl = "https://i.imgur.com/G9Ib5K3.png",
                streamUrl = "https://stream.y5.hu/stream/stream_bekescsaba/stream.m3u8",
                category = "Hungary,TV7"
            ),
            TvChannel(
                id = 385,
                name = "VTV Füzesabony",
                logoUrl = "https://i.imgur.com/7ZPYJJ0.jpg",
                streamUrl = "https://stream.unrealhosting.hu:443/hls/ftv/live.m3u8",
                category = "Hungary,VTV"
            ),
            TvChannel(
                id = 386,
                name = "Hegyvidék TV",
                logoUrl = "https://hegyvidektv.hu/wp-content/uploads/2020/08/hegyvidek.jpg",
                streamUrl = "https://tv.hegyvidek.hu/hvtv/hvstream.m3u8",
                category = "Hungary,Hegyvidék"
            ),
            TvChannel(
                id = 387,
                name = "16TV",
                logoUrl = "http://www.16tv.hu/images/xlogo-green.png.pagespeed.ic.79XBdS6JYn.png",
                streamUrl = "https://cloudfront44.lexanetwork.com:1344/freerelay/16tv.sdp/playlist.m3u8",
                category = "Hungary,16TV"
            ),
            TvChannel(
                id = 388,
                name = "CityTV (Belváros Lipótváros)",
                logoUrl = "https://www.citytv.hu/images/logo.png",
                streamUrl = "https://citytv.hu/playlist.m3u8",
                category = "Hungary,CityTV"
            ),
            TvChannel(
                id = 389,
                name = "AlföldTV",
                logoUrl = "http://www.dealood.com/content/uploads/images/March2019/5c9721a07ea87-images-large.png",
                streamUrl = "https://cloudfront41.lexanetwork.com:1344/relay01/livestream006.sdp/playlist.m3u8",
                category = "Hungary,AlföldTV"
            ),
            TvChannel(
                id = 390,
                name = "Kapos TV",
                logoUrl = "https://kapos.hu/static/keptar/13/b/9490.jpg",
                streamUrl = "https://cloudfront63.lexanetwork.com:1344/relay01/livestream004.sdp/playlist.m3u8",
                category = "Hungary,Kapos"
            ),
            TvChannel(
                id = 391,
                name = "Kecskemét TV",
                logoUrl = "https://kecskemetitv.hu/templates/kecskemetitv/img/ktv_logo.png",
                streamUrl = "https://eurobioinvest.hu:444/live/ktv.m3u8",
                category = "Hungary,Kecskemét"
            ),
            TvChannel(
                id = 392,
                name = "Lóverseny közvetítés",
                logoUrl = "https://kincsempark.hu/wp-content/uploads/2016/11/fejlec_logo_f-1.png",
                streamUrl = "https://cloudfront41.lexanetwork.com:1344/xrelay/loverseny2.sdp/playlist.m3u8",
                category = "Hungary,Lóverseny"
            ),
            TvChannel(
                id = 393,
                name = "Zalaegerszeg TV",
                logoUrl = "https://zegtv.hu/wp-content/themes/assembly/images/zegtv-logo.png",
                streamUrl = "https://cloudfront44.lexanetwork.com:1344/freerelay/zegtv.sdp/playlist.m3u8",
                category = "Hungary,Zalaegerszeg"
            ),
            TvChannel(
                id = 394,
                name = "Zugló TV",
                logoUrl = "http://zuglotv.hu/wp-content/themes/ztv/uploads/ztv_logo1.jpg",
                streamUrl = "https://cloudfront44.lexanetwork.com:1344/freerelay/zuglotv.sdp/playlist.m3u8",
                category = "Hungary,Zugló"
            ),
            TvChannel(
                id = 395,
                name = "Tisza TV",
                logoUrl = "https://www.tiszatv.hu/style/tiszatv_logo.png",
                streamUrl = "https://www.tiszatv.hu/onlinetv/tiszatv_1.m3u8",
                category = "Hungary,Tisza"
            ),
            TvChannel(
                id = 396,
                name = "DTV",
                logoUrl = "https://i.imgur.com/YSpqmSO.png",
                streamUrl = "http://cloudfront44.lexanetwork.com:1732/hlsrelay003/hls/livestream.sdp.m3u8",
                category = "Hungary,DTV"
            ),
            TvChannel(
                id = 397,
                name = "Bajai TV",
                logoUrl = "https://i.imgur.com/cyReGWh.png",
                streamUrl = "https://cloudfront41.lexanetwork.com:1344/relay01/livestream002.sdp/playlist.m3u8",
                category = "Hungary,Bajai"
            ),
            TvChannel(
                id = 398,
                name = "Vásárhelyi Televízió",
                logoUrl = "https://i.imgur.com/WOEqdmx.png",
                streamUrl = "https://stream.vasarhelyitelevizio.hu/stream/stream.m3u8",
                category = "Hungary,Vásárhelyi"
            ),
            TvChannel(
                id = 399,
                name = "TV Eger",
                logoUrl = "https://i.imgur.com/GUVW073.png",
                streamUrl = "http://stream.tveger.hu:8010/live.m3u8",
                category = "Hungary,TV"
            ),
            TvChannel(
                id = 400,
                name = "Miskolc TV",
                logoUrl = "https://i.imgur.com/IoiNus2.png",
                streamUrl = "https://video.mhzrt.hu/live/mitv/playlist.m3u8",
                category = "Hungary,Miskolc"
            ),
            TvChannel(
                id = 401,
                name = "Oroszlányi Városi Televízió",
                logoUrl = "https://i.imgur.com/P0fxUH5.png",
                streamUrl = "https://cloudfront44.lexanetwork.com:1344/relay01/broadcast002.sdp/playlist.m3u8",
                category = "Hungary,Oroszlányi"
            ),
            TvChannel(
                id = 402,
                name = "Berente TV",
                logoUrl = "",
                streamUrl = "https://stream.streaming4u.hu/BerenteTV/index.m3u8",
                category = "Hungary,Berente"
            ),
            TvChannel(
                id = 403,
                name = "Budapest Európa TV",
                logoUrl = "",
                streamUrl = "https://cloudfront44.lexanetwork.com:1344/freerelay/bpetv.sdp/playlist.m3u8",
                category = "Hungary,Budapest"
            ),
            TvChannel(
                id = 404,
                name = "Héviz TV",
                logoUrl = "",
                streamUrl = "https://cloudfront44.lexanetwork.com:1344/relay03/livestream003.sdp/playlist.m3u8",
                category = "Hungary,Héviz"
            ),
            TvChannel(
                id = 405,
                name = "Jászsági Térségi TV",
                logoUrl = "",
                streamUrl = "https://cloudfront44.lexanetwork.com:1344/relay01/broadcast007.sdp/playlist.m3u8",
                category = "Hungary,Jászsági"
            ),
            TvChannel(
                id = 406,
                name = "Kanizsa TV",
                logoUrl = "",
                streamUrl = "https://cloudfront44.lexanetwork.com:1344/freerelay/kanizsavtv.sdp/playlist.m3u8",
                category = "Hungary,Kanizsa"
            ),
            TvChannel(
                id = 407,
                name = "Líceum TV",
                logoUrl = "",
                streamUrl = "http://193.225.32.62:8890/live.m3u8",
                category = "Hungary,Líceum"
            ),
            TvChannel(
                id = 408,
                name = "M2 / Petőfi TV",
                logoUrl = "https://i.imgur.com/CzaDhmA.png",
                streamUrl = "https://c201-node61-cdn.connectmedia.hu/110102/7184521041cf54cee9c6548e8d0ba377/64117799/index.m3u8",
                category = "Hungary,M2"
            ),
            TvChannel(
                id = 409,
                name = "M4 Sport",
                logoUrl = "https://nb1.hu/uploads/news/3/31023.jpg",
                streamUrl = "https://c401-node62-cdn.connectmedia.hu/110110/5dd8dc6d853c9b7f94db85646ed44326/641177e3/index.m3u8",
                category = "Hungary,M4"
            ),
            TvChannel(
                id = 410,
                name = "RÚV",
                logoUrl = "https://i.imgur.com/vxaSn1K.png",
                streamUrl = "https://ruv-web-live.akamaized.net/streymi/ruverl/ruverl.m3u8",
                category = "Iceland,RÚV"
            ),
            TvChannel(
                id = 411,
                name = "RÚV 2",
                logoUrl = "https://i.imgur.com/yDKRuXQ.png",
                streamUrl = "https://ruvlive.akamaized.net/out/v1/2ff7673de40f419fa5164498fae89089/index.m3u8",
                category = "Iceland,RÚV"
            ),
            TvChannel(
                id = 412,
                name = "Alþingi",
                logoUrl = "https://i.imgur.com/n170HMm.png",
                streamUrl = "https://althingi-live.secure.footprint.net/althingi/live/index.m3u8",
                category = "Iceland,Alþingi"
            ),
            TvChannel(
                id = 413,
                name = "NDTV India",
                logoUrl = "https://i.imgur.com/QjJYohG.png",
                streamUrl = "https://ndtvindiaelemarchana.akamaized.net/hls/live/2003679/ndtvindia/master.m3u8",
                category = "India,NDTV"
            ),
            TvChannel(
                id = 414,
                name = "ABP News",
                logoUrl = "https://i.imgur.com/DKHUFVQ.png",
                streamUrl = "https://abplivetv.pc.cdn.bitgravity.com/httppush/abp_livetv/abp_abpnews/master.m3u8",
                category = "India,ABP"
            ),
            TvChannel(
                id = 415,
                name = "ABP Ananda",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/ABP_Ananda_logo.svg/500px-ABP_Ananda_logo.svg.png",
                streamUrl = "https://abplivetv.pc.cdn.bitgravity.com/httppush/abp_livetv/abp_ananda/master.m3u8",
                category = "India,ABP"
            ),
            TvChannel(
                id = 416,
                name = "CNBC Indonesia",
                logoUrl = "https://imgur.com/ie2zSTY",
                streamUrl = "https://live.cnbcindonesia.com/livecnbc/smil:cnbctv.smil/chunklist.m3u8",
                category = "Indonesia,CNBC"
            ),
            TvChannel(
                id = 417,
                name = "CNN Indonesia",
                logoUrl = "https://imgur.com/MpxTMiP",
                streamUrl = "http://live.cnnindonesia.com/livecnn/smil:cnntv.smil/playlist.m3u8",
                category = "Indonesia,CNN"
            ),
            TvChannel(
                id = 418,
                name = "BeritaSatu",
                logoUrl = "https://imgur.com/vYJVT07",
                streamUrl = "https://b1news.beritasatumedia.com/Beritasatu/B1News_1280x720.m3u8",
                category = "Indonesia,BeritaSatu"
            ),
            TvChannel(
                id = 419,
                name = "Al-Alam News Network Ⓢ",
                logoUrl = "https://i.imgur.com/UbD0Ndr.png",
                streamUrl = "https://live2.alalam.ir/alalam.m3u8",
                category = "Iran,Al-Alam"
            ),
            TvChannel(
                id = 420,
                name = "Press TV",
                logoUrl = "https://i.imgur.com/X3YP2Gg.png",
                streamUrl = "https://cdnlive.presstv.ir/cdnlive/smil:cdnlive.smil/playlist.m3u8",
                category = "Iran,Press"
            ),
            TvChannel(
                id = 421,
                name = "Press TV French",
                logoUrl = "https://i.imgur.com/X3YP2Gg.png",
                streamUrl = "https://live1.presstv.ir/live/presstvfr/index.m3u8",
                category = "Iran,Press"
            ),
            TvChannel(
                id = 422,
                name = "IranPress Ⓢ",
                logoUrl = "https://i.imgur.com/Qrubr3v.png",
                streamUrl = "https://live1.presstv.ir/live/iranpress/index.m3u8",
                category = "Iran,IranPress"
            ),
            TvChannel(
                id = 423,
                name = "Al-Hurra Iraq",
                logoUrl = "https://i.imgur.com/mXBZEQP.png",
                streamUrl = "https://mbnvvideoingest-i.akamaihd.net/hls/live/1004674/MBNV_ALHURRA_IRAQ/playlist.m3u8",
                category = "Iraq,Al-Hurra"
            ),
            TvChannel(
                id = 424,
                name = "Al-Hurra",
                logoUrl = "https://i.imgur.com/0izeu5z.png",
                streamUrl = "https://mbnvvideoingest-i.akamaihd.net/hls/live/1004673/MBNV_ALHURRA_MAIN/playlist.m3u8",
                category = "Iraq,Al-Hurra"
            ),
            TvChannel(
                id = 425,
                name = "Al-Iraqiya",
                logoUrl = "https://i.imgur.com/imdV6kL.png",
                streamUrl = "https://cdn.catiacast.video/abr/8d2ffb0aba244e8d9101a9488a7daa05/playlist.m3u8",
                category = "Iraq,Al-Iraqiya"
            ),
            TvChannel(
                id = 426,
                name = "Al-Rafidain",
                logoUrl = "https://i.imgur.com/D78qG91.png",
                streamUrl = "https://cdg8.edge.technocdn.com/arrafidaintv/abr_live/playlist.m3u8",
                category = "Iraq,Al-Rafidain"
            ),
            TvChannel(
                id = 427,
                name = "Al-Rasheed",
                logoUrl = "https://i.imgur.com/SU9HbXY.png",
                streamUrl = "https://media1.livaat.com/AL-RASHEED-HD/tracks-v1a1/playlist.m3u8",
                category = "Iraq,Al-Rasheed"
            ),
            TvChannel(
                id = 428,
                name = "Al-Sharqiya News",
                logoUrl = "https://i.imgur.com/P6p17ZY.jpg",
                streamUrl = "https://5d94523502c2d.streamlock.net/alsharqiyalive/mystream/playlist.m3u8",
                category = "Iraq,Al-Sharqiya"
            ),
            TvChannel(
                id = 429,
                name = "Al-Sharqiya",
                logoUrl = "https://i.imgur.com/bPYyXNf.png",
                streamUrl = "https://5d94523502c2d.streamlock.net/home/mystream/playlist.m3u8",
                category = "Iraq,Al-Sharqiya"
            ),
            TvChannel(
                id = 430,
                name = "Dijlah Tarab",
                logoUrl = "https://i.imgur.com/2SBjjBQ.png",
                streamUrl = "https://ghaasiflu.online/tarab/tracks-v1a1/playlist.m3u8",
                category = "Iraq,Dijlah"
            ),
            TvChannel(
                id = 431,
                name = "Dijlah TV",
                logoUrl = "https://i.imgur.com/FJEeYiz.png",
                streamUrl = "https://ghaasiflu.online/Dijlah/tracks-v1a1/playlist.m3u8",
                category = "Iraq,Dijlah"
            ),
            TvChannel(
                id = 432,
                name = "iNEWS",
                logoUrl = "https://i.imgur.com/PeuBkaH.png",
                streamUrl = "https://svs.itworkscdn.net/inewsiqlive/inewsiq.smil/playlist.m3u8",
                category = "Iraq,iNEWS"
            ),
            TvChannel(
                id = 433,
                name = "Iraq Future Ⓢ",
                logoUrl = "https://i.imgur.com/Z7woTe5.png",
                streamUrl = "https://streaming.viewmedia.tv/viewsatstream40/viewsatstream40.smil/playlist.m3u8",
                category = "Iraq,Iraq"
            ),
            TvChannel(
                id = 434,
                name = "Turkmeneli TV",
                logoUrl = "https://i.imgur.com/iUhhg4B.png",
                streamUrl = "https://137840.global.ssl.fastly.net/edge/live_6b7c6e205afb11ebb010f5a331abaf98/playlist.m3u8",
                category = "Iraq,Turkmeneli"
            ),
            TvChannel(
                id = 435,
                name = "Zagros TV",
                logoUrl = "https://i.imgur.com/UjIuIQX.png",
                streamUrl = "https://5a3ed7a72ed4b.streamlock.net/zagrostv/SMIL:myStream.smil/playlist.m3u8",
                category = "Iraq,Zagros"
            ),
            TvChannel(
                id = 436,
                name = "TG4",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/06/TG4_logo.svg/1024px-TG4_logo.svg.png",
                streamUrl = "https://dx4452e0qv6r9.cloudfront.net/tg4_vod_national.m3u8",
                category = "Ireland,TG4"
            ),
            TvChannel(
                id = 437,
                name = "Houses of the Oireachtas Channel",
                logoUrl = "https://i.imgur.com/aC4fsCI.png",
                streamUrl = "https://d33zah5htxvoxb.cloudfront.net/el/live/oirtv/hls.m3u8",
                category = "Ireland,Houses"
            ),
            TvChannel(
                id = 438,
                name = "כאן 11",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c8/Kan11Logo.svg/640px-Kan11Logo.svg.png",
                streamUrl = "https://kan11.media.kan.org.il/hls/live/2024514/2024514/master.m3u8",
                category = "Israel,כאן"
            ),
            TvChannel(
                id = 439,
                name = "ערוץ 13",
                logoUrl = "https://upload.wikimedia.org/wikipedia/he/thumb/1/17/Reshet13Logo2022.svg/559px-Reshet13Logo2022.svg.png",
                streamUrl = "https://d2xg1g9o5vns8m.cloudfront.net/out/v1/0855d703f7d5436fae6a9c7ce8ca5075/index.m3u8",
                category = "Israel,ערוץ"
            ),
            TvChannel(
                id = 440,
                name = "ערוץ 14",
                logoUrl = "https://i.imgur.com/Iq2Kb69.png",
                streamUrl = "https://now14.g-mana.live/media/91517161-44ab-4e46-af70-e9fe26117d2e/mainManifest.m3u8",
                category = "Israel,ערוץ"
            ),
            TvChannel(
                id = 441,
                name = "The Shopping Channel",
                logoUrl = "https://i.imgur.com/PEdXHSE.png",
                streamUrl = "https://shoppingil-rewriter.vidnt.com/index.m3u8",
                category = "Israel,The"
            ),
            TvChannel(
                id = 442,
                name = "مكان 33",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/5/56/MeKan_33_logo_2017.png",
                streamUrl = "https://makan.media.kan.org.il/hls/live/2024680/2024680/master.m3u8",
                category = "Israel,مكان"
            ),
            TvChannel(
                id = 443,
                name = "כאן חינוכית",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/KanHinuchit.svg/640px-KanHinuchit.svg.png",
                streamUrl = "https://kan23.media.kan.org.il/hls/live/2024691/2024691/master.m3u8",
                category = "Israel,כאן"
            ),
            TvChannel(
                id = 444,
                name = "Knesset",
                logoUrl = "https://i.imgur.com/PEdXHSE.png",
                streamUrl = "https://contact.gostreaming.tv/Knesset/myStream/playlist.m3u8",
                category = "Israel,Knesset"
            ),
            TvChannel(
                id = 445,
                name = "Rete 4 Ⓖ",
                logoUrl = "https://i.imgur.com/GWx2Fkl.png",
                streamUrl = "https://live02-seg.msf.cdn.mediaset.net/live/ch-r4/r4-clr.isml/index.m3u8",
                category = "Italy,Rete"
            ),
            TvChannel(
                id = 446,
                name = "Canale 5 Ⓖ",
                logoUrl = "https://i.imgur.com/p6YdiR1.png",
                streamUrl = "https://live02-seg.msf.cdn.mediaset.net/live/ch-c5/c5-clr.isml/index.m3u8",
                category = "Italy,Canale"
            ),
            TvChannel(
                id = 447,
                name = "Italia 1 Ⓖ",
                logoUrl = "https://i.imgur.com/oCiOxBG.png",
                streamUrl = "https://live02-seg.msf.cdn.mediaset.net/live/ch-i1/i1-clr.isml/index.m3u8",
                category = "Italy,Italia"
            ),
            TvChannel(
                id = 448,
                name = "La7",
                logoUrl = "https://i.imgur.com/F90mpSa.png",
                streamUrl = "https://viamotionhsi.netplus.ch/live/eds/la7/browser-HLS8/la7.m3u8",
                category = "Italy,La7"
            ),
            TvChannel(
                id = 449,
                name = "Nove",
                logoUrl = "https://i.imgur.com/Hp723RU.png",
                streamUrl = "https://d31mw7o1gs0dap.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-y5pbi2sq9r609/NOVE_IT.m3u8",
                category = "Italy,Nove"
            ),
            TvChannel(
                id = 450,
                name = "20 Mediaset Ⓖ",
                logoUrl = "https://i.imgur.com/It13jwX.png",
                streamUrl = "https://live02-seg.msf.cdn.mediaset.net/live/ch-lb/lb-clr.isml/index.m3u8",
                category = "Italy,20"
            ),
            TvChannel(
                id = 451,
                name = "Iris Ⓖ",
                logoUrl = "https://i.imgur.com/Ixz1BY3.png",
                streamUrl = "https://live02-seg.msf.cdn.mediaset.net/live/ch-ki/ki-clr.isml/index.m3u8",
                category = "Italy,Iris"
            ),
            TvChannel(
                id = 452,
                name = "27 Twentyseven Ⓖ",
                logoUrl = "https://i.imgur.com/y2PdPCK.png",
                streamUrl = "https://live02-seg.msf.cdn.mediaset.net/live/ch-ts/ts-clr.isml/index.m3u8",
                category = "Italy,27"
            ),
            TvChannel(
                id = 453,
                name = "TV 2000",
                logoUrl = "https://i.imgur.com/x7RaK3a.png",
                streamUrl = "https://hls-live-tv2000.akamaized.net/hls/live/2028510/tv2000/master.m3u8",
                category = "Italy,TV"
            ),
            TvChannel(
                id = 454,
                name = "La7 Cinema",
                logoUrl = "https://i.imgur.com/khPweok.png",
                streamUrl = "https://viamotionhsi.netplus.ch/live/eds/la7d/browser-HLS8/la7d.m3u8",
                category = "Italy,La7"
            ),
            TvChannel(
                id = 455,
                name = "La 5 Ⓖ",
                logoUrl = "https://i.imgur.com/UNyJaho.png",
                streamUrl = "https://live02-seg.msf.cdn.mediaset.net/live/ch-ka/ka-clr.isml/index.m3u8",
                category = "Italy,La"
            ),
            TvChannel(
                id = 456,
                name = "Real Time",
                logoUrl = "https://i.imgur.com/9dcTYg1.png",
                streamUrl = "https://d3562mgijzx0zq.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-kizqtzpvvl3i8/Realtime_IT.m3u8",
                category = "Italy,Real"
            ),
            TvChannel(
                id = 457,
                name = "QVC",
                logoUrl = "https://i.imgur.com/Ea7iUX2.png",
                streamUrl = "https://qrg.akamaized.net/hls/live/2017383/lsqvc1it/master.m3u8",
                category = "Italy,QVC"
            ),
            TvChannel(
                id = 458,
                name = "Food Network",
                logoUrl = "https://i.imgur.com/i60OYr9.png",
                streamUrl = "https://dk3okdd5036kz.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-o4pw0nc02sthz/Foodnetwork_IT.m3u8",
                category = "Italy,Food"
            ),
            TvChannel(
                id = 459,
                name = "Cine34   Ⓖ",
                logoUrl = "https://i.imgur.com/YyldwhI.png",
                streamUrl = "https://live02-seg.msf.cdn.mediaset.net/live/ch-b6/b6-clr.isml/index.m3u8",
                category = "Italy,Cine34"
            ),
            TvChannel(
                id = 460,
                name = "Focus Ⓖ",
                logoUrl = "https://i.imgur.com/M4smqpF.png",
                streamUrl = "https://live02-seg.msf.cdn.mediaset.net/live/ch-fu/fu-clr.isml/index.m3u8",
                category = "Italy,Focus"
            ),
            TvChannel(
                id = 461,
                name = "RTL 102.5",
                logoUrl = "https://i.imgur.com/KdissvS.png",
                streamUrl = "https://dd782ed59e2a4e86aabf6fc508674b59.msvdn.net/live/S97044836/tbbP8T1ZRPBL/playlist.m3u8",
                category = "Italy,RTL"
            ),
            TvChannel(
                id = 462,
                name = "Discovery Channel",
                logoUrl = "https://i.imgur.com/5IxIFJ0.png",
                streamUrl = "https://d24aqelmrau4kx.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-l1oas691aj7p2/WBTV_IT.m3u8",
                category = "Italy,Discovery"
            ),
            TvChannel(
                id = 463,
                name = "Giallo",
                logoUrl = "https://i.imgur.com/0PIRwZS.png",
                streamUrl = "https://d9fqo6nfqlv2h.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-ulukbrgm1n3yb/Giallo_IT.m3u8",
                category = "Italy,Giallo"
            ),
            TvChannel(
                id = 464,
                name = "Top Crime   Ⓖ",
                logoUrl = "https://i.imgur.com/RFIwv9O.png",
                streamUrl = "https://live02-seg.msf.cdn.mediaset.net/live/ch-lt/lt-clr.isml/index.m3u8",
                category = "Italy,Top"
            ),
            TvChannel(
                id = 465,
                name = "BOING   Ⓖ",
                logoUrl = "https://i.imgur.com/niSlrqT.png",
                streamUrl = "https://live02-seg.msf.cdn.mediaset.net/live/ch-kb/kb-clr.isml/index.m3u8",
                category = "Italy,BOING"
            ),
            TvChannel(
                id = 466,
                name = "K2",
                logoUrl = "https://i.imgur.com/wlLgSiA.png",
                streamUrl = "https://d1pmpe0hs35ka5.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-39hsskpppgf72/K2_IT.m3u8",
                category = "Italy,K2"
            ),
            TvChannel(
                id = 467,
                name = "Frisbee",
                logoUrl = "https://i.imgur.com/9y1zIAe.png",
                streamUrl = "https://d6m7lubks416z.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-zmbstsedxme9s/Frisbee_IT.m3u8",
                category = "Italy,Frisbee"
            ),
            TvChannel(
                id = 468,
                name = "Cartoonito Ⓖ",
                logoUrl = "https://i.imgur.com/oK2DcDJ.png",
                streamUrl = "https://live02-seg.msf.cdn.mediaset.net/live/ch-la/la-clr.isml/index.m3u8",
                category = "Italy,Cartoonito"
            ),
            TvChannel(
                id = 469,
                name = "Super!",
                logoUrl = "https://i.imgur.com/1124YEp.png",
                streamUrl = "https://495c5a85d9074f29acffeaea9e0215eb.msvdn.net/super/super_main/super_main_hbbtv/playlist.m3u8",
                category = "Italy,Super!"
            ),
            TvChannel(
                id = 470,
                name = "Italia 2 Ⓖ",
                logoUrl = "https://i.imgur.com/nq48sjO.png",
                streamUrl = "https://live02-seg.msf.cdn.mediaset.net/live/ch-i2/i2-clr.isml/index.m3u8",
                category = "Italy,Italia"
            ),
            TvChannel(
                id = 471,
                name = "TGCOM 24 Ⓖ",
                logoUrl = "https://i.imgur.com/xautVD8.png",
                streamUrl = "https://live02-seg.msf.cdn.mediaset.net/live/ch-kf/kf-clr.isml/index.m3u8",
                category = "Italy,TGCOM"
            ),
            TvChannel(
                id = 472,
                name = "DMAX",
                logoUrl = "https://i.imgur.com/dmEmRX7.png",
                streamUrl = "https://d2j2nqgg7bzth.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-02k1gv1j0ufwn/DMAX_IT.m3u8",
                category = "Italy,DMAX"
            ),
            TvChannel(
                id = 473,
                name = "Mediaset Extra Ⓖ",
                logoUrl = "https://i.imgur.com/mM8lopo.png",
                streamUrl = "https://live02-seg.msf.cdn.mediaset.net/live/ch-kq/kq-clr.isml/index.m3u8",
                category = "Italy,Mediaset"
            ),
            TvChannel(
                id = 474,
                name = "HGTV – Home & Garden Tv",
                logoUrl = "https://i.imgur.com/emLNC0U.png",
                streamUrl = "https://d1tidto9vz737l.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-joaw4f4kh2en1/HGTV_IT.m3u8",
                category = "Italy,HGTV"
            ),
            TvChannel(
                id = 475,
                name = "Motor Trend",
                logoUrl = "https://i.imgur.com/ipj2H0n.png",
                streamUrl = "https://d205m6k582pec4.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-asg5puyzdtnqu/Motortrend_IT.m3u8",
                category = "Italy,Motor"
            ),
            TvChannel(
                id = 476,
                name = "Sportitalia Plus",
                logoUrl = "https://i.imgur.com/hu56Ya5.png",
                streamUrl = "https://sportsitalia-samsungitaly.amagi.tv/playlist.m3u8",
                category = "Italy,Sportitalia"
            ),
            TvChannel(
                id = 477,
                name = "Travel TV",
                logoUrl = "https://i.imgur.com/aXAUyLN.png",
                streamUrl = "https://streaming.softwarecreation.it/GoldTvSat/GoldTvSat/playlist.m3u8",
                category = "Italy,Travel"
            ),
            TvChannel(
                id = 478,
                name = "Donna TV Ⓢ",
                logoUrl = "https://i.imgur.com/Aa1Abme.png",
                streamUrl = "https://5a1178b42cc03.streamlock.net/donnatv/donnatv/playlist.m3u8",
                category = "Italy,Donna"
            ),
            TvChannel(
                id = 479,
                name = "SuperTennis",
                logoUrl = "https://i.imgur.com/GzsPlbX.png",
                streamUrl = "https://live-embed.supertennix.hiway.media/restreamer/supertennix_client/gpu-a-c0-16/restreamer/outgest/aa3673f1-e178-44a9-a947-ef41db73211a/manifest.m3u8",
                category = "Italy,SuperTennis"
            ),
            TvChannel(
                id = 480,
                name = "Alma TV",
                logoUrl = "https://i.imgur.com/Y8JiDwN.png",
                streamUrl = "https://streaming.softwarecreation.it/AlmaTv/AlmaTv/playlist.m3u8",
                category = "Italy,Alma"
            ),
            TvChannel(
                id = 481,
                name = "Radio 105 TV Ⓖ",
                logoUrl = "https://i.imgur.com/3NiLKvj.png",
                streamUrl = "https://live02-seg.msr.cdn.mediaset.net/live/ch-ec/ec-clr.isml/index.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 482,
                name = "R101 TV Ⓖ",
                logoUrl = "https://i.imgur.com/mWeEa9T.png",
                streamUrl = "https://live02-seg.msr.cdn.mediaset.net/live/ch-er/er-clr.isml/index.m3u8",
                category = "Italy,R101"
            ),
            TvChannel(
                id = 483,
                name = "Deejay TV",
                logoUrl = "https://i.imgur.com/rlaKH6k.png",
                streamUrl = "https://4c4b867c89244861ac216426883d1ad0.msvdn.net/live/S85984808/sMO0tz9Sr2Rk/playlist.m3u8",
                category = "Italy,Deejay"
            ),
            TvChannel(
                id = 484,
                name = "RadioItaliaTV",
                logoUrl = "https://i.imgur.com/4VCEJuJ.png",
                streamUrl = "https://radioitaliatv.akamaized.net/hls/live/2093117/RadioitaliaTV/master.m3u8",
                category = "Italy,RadioItaliaTV"
            ),
            TvChannel(
                id = 485,
                name = "Radio KISS KISS TV",
                logoUrl = "https://i.imgur.com/UTStxDW.png",
                streamUrl = "https://kk.fluid.stream/KKMulti/smil:KissKissTV.smil/playlist.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 486,
                name = "RTL 102.5 Traffic",
                logoUrl = "https://i.imgur.com/GH7byqm.jpeg",
                streamUrl = "https://dd782ed59e2a4e86aabf6fc508674b59.msvdn.net/live/S38122967/2lyQRIAAGgRR/playlist.m3u8",
                category = "Italy,RTL"
            ),
            TvChannel(
                id = 487,
                name = "MAN-GA",
                logoUrl = "https://i.imgur.com/8a6WYU2.png",
                streamUrl = "https://c65b9e710bde44819015af98e72cd7ab.msvdn.net/live/S93572876/aILSQPYFY3pF/playlist.m3u8",
                category = "Italy,MAN-GA"
            ),
            TvChannel(
                id = 488,
                name = "Radio24-IlSole24OreTV",
                logoUrl = "https://i.imgur.com/NTqrdWW.png",
                streamUrl = "https://ilsole24ore-radiovisual.akamaized.net/hls/live/2035302/persidera/master.m3u8",
                category = "Italy,Radio24-IlSole24OreTV"
            ),
            TvChannel(
                id = 489,
                name = "BeJoy.Kids",
                logoUrl = "https://i.imgur.com/zuR9Go5.png",
                streamUrl = "https://64b16f23efbee.streamlock.net/bejoy/bejoy/playlist.m3u8",
                category = "Italy,BeJoy.Kids"
            ),
            TvChannel(
                id = 490,
                name = "Gambero Rosso",
                logoUrl = "https://i.imgur.com/RApMW5x.png",
                streamUrl = "https://2018f6355a15442ebb37007fa4f6c064.msvdn.net/live/S7530969/XWerenuxbSdW/playlist.m3u8",
                category = "Italy,Gambero"
            ),
            TvChannel(
                id = 491,
                name = "RadioFreccia",
                logoUrl = "https://i.imgur.com/J5N9F7Z.png",
                streamUrl = "https://dd782ed59e2a4e86aabf6fc508674b59.msvdn.net/live/S3160845/0tuSetc8UFkF/playlist.m3u8",
                category = "Italy,RadioFreccia"
            ),
            TvChannel(
                id = 492,
                name = "RDS Social TV",
                logoUrl = "https://i.imgur.com/TY6FhqI.png",
                streamUrl = "https://stream.rdstv.radio/index.m3u8",
                category = "Italy,RDS"
            ),
            TvChannel(
                id = 493,
                name = "Radio ZETA",
                logoUrl = "https://i.imgur.com/0MgCm1n.png",
                streamUrl = "https://dd782ed59e2a4e86aabf6fc508674b59.msvdn.net/live/S9346184/XEx1LqlYbNic/playlist.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 494,
                name = "Radio TV Serie A con RDS",
                logoUrl = "https://i.imgur.com/NzDeCIx.png",
                streamUrl = "https://stream.radioseriea.com/50773f0d0070476a8612d9984c6059d8/index.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 495,
                name = "Sportitalia SOLOCALCIO",
                logoUrl = "https://i.imgur.com/hu56Ya5.png",
                streamUrl = "https://di-g7ij0rwh.vo.lswcdn.net/sportitalia/sisolocalcio.smil/playlist.m3u8",
                category = "Italy,Sportitalia"
            ),
            TvChannel(
                id = 496,
                name = "BIKE Channel",
                logoUrl = "https://i.imgur.com/4IzVSQI.png",
                streamUrl = "https://stream.prod-01.milano.nxmedge.net/argocdn/bikechannel/video.m3u8",
                category = "Italy,BIKE"
            ),
            TvChannel(
                id = 497,
                name = "Radio Montecarlo TV Ⓖ",
                logoUrl = "https://i.imgur.com/3TMMXmS.png",
                streamUrl = "https://live02-seg.msr.cdn.mediaset.net/live/ch-bb/bb-clr.isml/index.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 498,
                name = "Virgin Radio TV Ⓖ",
                logoUrl = "https://i.imgur.com/7Im3HI1.png",
                streamUrl = "https://live02-seg.msr.cdn.mediaset.net/live/ch-ew/ew-clr.isml/index.m3u8",
                category = "Italy,Virgin"
            ),
            TvChannel(
                id = 499,
                name = "Senato TV",
                logoUrl = "https://i.imgur.com/FoQoNZW.png",
                streamUrl = "https://senato-live.morescreens.com/SENATO_1_001/playlist.m3u8",
                category = "Italy,Senato"
            ),
            TvChannel(
                id = 500,
                name = "Camera dei Deputati Ⓢ",
                logoUrl = "https://i.imgur.com/fqGn1k9.png",
                streamUrl = "https://video-ar.radioradicale.it/diretta/camera2/playlist.m3u8",
                category = "Italy,Camera"
            ),
            TvChannel(
                id = 501,
                name = "Rai 4K Ⓖ",
                logoUrl = "https://i.imgur.com/5gkt9DD.png",
                streamUrl = "https://raievent10-elem-live.akamaized.net/hls/live/619189/raievent10/raievent10/playlist.m3u8",
                category = "Italy,Rai"
            ),
            TvChannel(
                id = 502,
                name = "UniNettuno University TV Ⓖ",
                logoUrl = "https://i.imgur.com/BOGMeio.png",
                streamUrl = "https://stream6-rai-it.akamaized.net/live/uninettuno/playlist.m3u8",
                category = "Italy,UniNettuno"
            ),
            TvChannel(
                id = 503,
                name = "111 Tv",
                logoUrl = "https://i.imgur.com/4jY8yAI.png",
                streamUrl = "https://5db313b643fd8.streamlock.net/111/111/playlist.m3u8",
                category = "Italy,111"
            ),
            TvChannel(
                id = 504,
                name = "12 Tv Parma",
                logoUrl = "https://i.imgur.com/xnUgx6b.png",
                streamUrl = "https://5929b138b139d.streamlock.net/12TVParma/livestream/playlist.m3u8",
                category = "Italy,12"
            ),
            TvChannel(
                id = 505,
                name = "4 You Tv",
                logoUrl = "https://i.imgur.com/NN0cSbz.png",
                streamUrl = "https://streamsrv2.nets-sr.com:19360/4youtv/4youtv.m3u8",
                category = "Italy,4"
            ),
            TvChannel(
                id = 506,
                name = "Abc Tv",
                logoUrl = "https://i.imgur.com/nVmIeTD.png",
                streamUrl = "https://diretta.arcapuglia.it:8080/live/abctv/index.m3u8",
                category = "Italy,Abc"
            ),
            TvChannel(
                id = 507,
                name = "AB Channel",
                logoUrl = "https://i.imgur.com/k8EPLB9.png",
                streamUrl = "https://tsw.streamingwebtv24.it:1936/abchanneltv/abchanneltv/playlist.m3u8",
                category = "Italy,AB"
            ),
            TvChannel(
                id = 508,
                name = "Alpauno",
                logoUrl = "https://i.imgur.com/4QKFtUa.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/alpaunotv/alpaunotv/playlist.m3u8",
                category = "Italy,Alpauno"
            ),
            TvChannel(
                id = 509,
                name = "Alto Adige Tv",
                logoUrl = "https://i.imgur.com/S2sCFQi.png",
                streamUrl = "https://5f204aff97bee.streamlock.net/AltoAdigeTV/livestream/playlist.m3u8",
                category = "Italy,Alto"
            ),
            TvChannel(
                id = 510,
                name = "Antenna 2 Bergamo",
                logoUrl = "https://i.imgur.com/NfvHIAw.png",
                streamUrl = "https://58f12ffd2447a.streamlock.net/Antenna2/livestream/playlist.m3u8",
                category = "Italy,Antenna"
            ),
            TvChannel(
                id = 511,
                name = "Antenna 3 Massa",
                logoUrl = "https://i.imgur.com/CHDU86j.png",
                streamUrl = "https://media2021.rtvweb.com/antenna3massa/a3/playlist.m3u8",
                category = "Italy,Antenna"
            ),
            TvChannel(
                id = 512,
                name = "Antenna 3 Veneto Nord Est",
                logoUrl = "https://i.imgur.com/NiVHLwp.png",
                streamUrl = "https://59d7d6f47d7fc.streamlock.net/antennatreveneto/antennatreveneto.stream/playlist.m3u8",
                category = "Italy,Antenna"
            ),
            TvChannel(
                id = 513,
                name = "Antenna Sud",
                logoUrl = "https://i.imgur.com/b8y6ImZ.png",
                streamUrl = "https://live.antennasudwebtv.it:9443/hls/vod.m3u8",
                category = "Italy,Antenna"
            ),
            TvChannel(
                id = 514,
                name = "Antenna Sud Extra",
                logoUrl = "https://i.imgur.com/6tBv8VD.png",
                streamUrl = "https://live.antennasudwebtv.it:9443/hls/vod92.m3u8",
                category = "Italy,Antenna"
            ),
            TvChannel(
                id = 515,
                name = "Aristanis SuperTv",
                logoUrl = "https://i.imgur.com/v8PlAJO.png",
                streamUrl = "https://video2.azotosolutions.com:1936/supertvoristano/supertvoristano/playlist.m3u8",
                category = "Italy,Aristanis"
            ),
            TvChannel(
                id = 516,
                name = "Arte Network Orler",
                logoUrl = "https://i.imgur.com/DP5y0Er.png",
                streamUrl = "https://tsw.streamingwebtv24.it:1936/artenetwork/artenetwork/playlist.m3u8",
                category = "Italy,Arte"
            ),
            TvChannel(
                id = 517,
                name = "Aurora Arte",
                logoUrl = "https://i.imgur.com/BoLZ5wG.png",
                streamUrl = "https://59d7d6f47d7fc.streamlock.net/auroraarte/auroraarte/playlist.m3u8",
                category = "Italy,Aurora"
            ),
            TvChannel(
                id = 518,
                name = "Azzurra Tv Vco",
                logoUrl = "https://i.imgur.com/mSWw8uW.png",
                streamUrl = "https://59d7d6f47d7fc.streamlock.net/azzurratv/azzurratv/playlist.m3u8",
                category = "Italy,Azzurra"
            ),
            TvChannel(
                id = 519,
                name = "Basilicata 1 Tv",
                logoUrl = "https://i.imgur.com/VS6CQ88.png",
                streamUrl = "http://77.68.40.210:8888/hls/basilicata1.m3u8",
                category = "Italy,Basilicata"
            ),
            TvChannel(
                id = 520,
                name = "BOM Channel",
                logoUrl = "https://i.imgur.com/hISoOK3.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/canale6/canale6/playlist.m3u8",
                category = "Italy,BOM"
            ),
            TvChannel(
                id = 521,
                name = "ByoBlu",
                logoUrl = "https://i.imgur.com/1xaHGtU.png",
                streamUrl = "https://09bd1346f7a44cc9ac230cc1cb22ca4f.msvdn.net/live/S39249178/EnTK3KeeN1Eg/playlist.m3u8",
                category = "Italy,ByoBlu"
            ),
            TvChannel(
                id = 522,
                name = "Cafe Tv 24",
                logoUrl = "https://i.imgur.com/KbcbxFw.png",
                streamUrl = "https://srvx1.selftv.video/cafe/live/playlist.m3u8",
                category = "Italy,Cafe"
            ),
            TvChannel(
                id = 523,
                name = "Calabria Uno Tv",
                logoUrl = "https://i.imgur.com/2TK1GQ5.png",
                streamUrl = "https://635320cd397eb.streamlock.net/live/ngrp:calabriaunolive_all/playlist.m3u8",
                category = "Italy,Calabria"
            ),
            TvChannel(
                id = 524,
                name = "Calabria tv",
                logoUrl = "https://i.imgur.com/qWirucd.png",
                streamUrl = "https://64b16f23efbee.streamlock.net/calabriatv-clone/calabriatv-clone/playlist.m3u8",
                category = "Italy,Calabria"
            ),
            TvChannel(
                id = 525,
                name = "Canale 10",
                logoUrl = "https://i.imgur.com/KuQcjYV.png",
                streamUrl = "https://nrvideo1.newradio.it:1936/desxcerbht/desxcerbht/playlist.m3u8",
                category = "Italy,Canale"
            ),
            TvChannel(
                id = 526,
                name = "Canale 2",
                logoUrl = "https://i.imgur.com/ETqDkS1.png",
                streamUrl = "https://59d7d6f47d7fc.streamlock.net/canale2/canale2/playlist.m3u8",
                category = "Italy,Canale"
            ),
            TvChannel(
                id = 527,
                name = "Canale 21",
                logoUrl = "https://i.imgur.com/mU6Cq89.png",
                streamUrl = "https://0ff9dd7fe9b64bc08a5fc4ed525454c3.msvdn.net/live/S38994111/B7j0ql4XaZtE/playlist.m3u8",
                category = "Italy,Canale"
            ),
            TvChannel(
                id = 528,
                name = "Canale 21 Extra",
                logoUrl = "https://i.imgur.com/cDAsrBl.png",
                streamUrl = "https://0ff9dd7fe9b64bc08a5fc4ed525454c3.msvdn.net/live/S42170132/sT6C3LFaD1iA/playlist.m3u8",
                category = "Italy,Canale"
            ),
            TvChannel(
                id = 529,
                name = "Canale 7",
                logoUrl = "https://i.imgur.com/9cuOLCn.png",
                streamUrl = "http://wms.shared.streamshow.it:80/canale7/canale7/playlist.m3u8",
                category = "Italy,Canale"
            ),
            TvChannel(
                id = 530,
                name = "Canale 74 Sicilia",
                logoUrl = "https://i.imgur.com/18JIVgu.png",
                streamUrl = "https://stream.cp.ets-sistemi.it:1936/canale74/canale74/playlist.m3u8",
                category = "Italy,Canale"
            ),
            TvChannel(
                id = 531,
                name = "Canale 8 Campania",
                logoUrl = "https://i.imgur.com/ElAS2WC.png",
                streamUrl = "https://59d7d6f47d7fc.streamlock.net/canale8/canale8/playlist.m3u8",
                category = "Italy,Canale"
            ),
            TvChannel(
                id = 532,
                name = "Canale Italia",
                logoUrl = "https://i.imgur.com/QlwLRyh.png",
                streamUrl = "https://ovp-live.akamaized.net/ac115_live/canale1.smil/playlist.m3u8",
                category = "Italy,Canale"
            ),
            TvChannel(
                id = 533,
                name = "Carina Tv",
                logoUrl = "https://i.imgur.com/FMGcm6I.jpg",
                streamUrl = "https://samson.streamerr.co:8081/carinatv/index.m3u8",
                category = "Italy,Carina"
            ),
            TvChannel(
                id = 534,
                name = "Casa Italia 53",
                logoUrl = "https://i.imgur.com/O4GQVCk.png",
                streamUrl = "https://ovp-live.akamaized.net/ac115_live/canale3.smil/playlist.m3u8",
                category = "Italy,Casa"
            ),
            TvChannel(
                id = 535,
                name = "Casa Sanremo Tv",
                logoUrl = "https://i.imgur.com/WL3SFTs.png",
                streamUrl = "https://router.xdevel.com/video0s975911-633/stream/playlist_dvr.m3u8",
                category = "Italy,Casa"
            ),
            TvChannel(
                id = 536,
                name = "Castrovillari Tv",
                logoUrl = "https://i.imgur.com/V0kjYNG.png",
                streamUrl = "http://msh0062.stream.seeweb.it/live/flv:stream00.sdp/playlist.m3u8",
                category = "Italy,Castrovillari"
            ),
            TvChannel(
                id = 537,
                name = "Cittaceleste Tv",
                logoUrl = "https://i.imgur.com/9RVriNK.jpeg",
                streamUrl = "https://sportitaliaamd.akamaized.net/live/Cittaceleste/hls/A990687F506536598442FC5CD12C97CB78873FBA/index.m3u8",
                category = "Italy,Cittaceleste"
            ),
            TvChannel(
                id = 538,
                name = "Company Tv",
                logoUrl = "https://i.imgur.com/IbabUDd.png",
                streamUrl = "https://company.fluid.stream/CompanyTV/smil:Company_ALL.smil/playlist.m3u8",
                category = "Italy,Company"
            ),
            TvChannel(
                id = 539,
                name = "Cremona 1",
                logoUrl = "https://i.imgur.com/a5d0F01.jpg",
                streamUrl = "https://cdn2.streamshow.it/cloud-cremona1/cremona1/playlist.m3u8",
                category = "Italy,Cremona"
            ),
            TvChannel(
                id = 540,
                name = "Cusano Italia Tv",
                logoUrl = "https://i.imgur.com/9F1sVjZ.png",
                streamUrl = "https://router.xdevel.com/video0s975363-69/stream/playlist.m3u8",
                category = "Italy,Cusano"
            ),
            TvChannel(
                id = 541,
                name = "Cusano News 7",
                logoUrl = "https://i.imgur.com/L49Ie1Q.png",
                streamUrl = "https://router.xdevel.com/video1s975363-1596/stream/playlist.m3u8",
                category = "Italy,Cusano"
            ),
            TvChannel(
                id = 542,
                name = "Delta Tv",
                logoUrl = "https://i.imgur.com/mfwVXt7.png",
                streamUrl = "http://hbbtv-server.zivoli.it:8080/hls/deltatv/deltatv/index.m3u8",
                category = "Italy,Delta"
            ),
            TvChannel(
                id = 543,
                name = "Deluxe 139",
                logoUrl = "https://i.imgur.com/kRexw3w.png",
                streamUrl = "https://59d7d6f47d7fc.streamlock.net/pierstyle/pierstyle/playlist.m3u8",
                category = "Italy,Deluxe"
            ),
            TvChannel(
                id = 544,
                name = "Di.Tv 80",
                logoUrl = "https://i.imgur.com/kHxTsJw.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/ditv80/ditv80/playlist.m3u8",
                category = "Italy,Di.Tv"
            ),
            TvChannel(
                id = 545,
                name = "Di.Tv 92",
                logoUrl = "https://i.imgur.com/g3SI08H.png",
                streamUrl = "https://media2021.rtvweb.com/di_tv_92/live/playlist.m3u8",
                category = "Italy,Di.Tv"
            ),
            TvChannel(
                id = 546,
                name = "Digital Tv7 Benevento",
                logoUrl = "https://i.imgur.com/NaQkklP.png",
                streamUrl = "http://streaming.senecadot.com/live/flv:tv7.sdp/playlist.m3u8",
                category = "Italy,Digital"
            ),
            TvChannel(
                id = 547,
                name = "Donna Shopping Tv",
                logoUrl = "https://i.imgur.com/oLDvx2T.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/fascinotv/fascinotv/playlist.m3u8",
                category = "Italy,Donna"
            ),
            TvChannel(
                id = 548,
                name = "E'live Brescia Tv",
                logoUrl = "https://i.imgur.com/bZ3B7pi.png",
                streamUrl = "https://59d7d6f47d7fc.streamlock.net/elivebresciatv/elivebresciatv/playlist.m3u8",
                category = "Italy,E'live"
            ),
            TvChannel(
                id = 549,
                name = "Easy Tv Canale 190",
                logoUrl = "https://i.imgur.com/LKrVuRR.jpg",
                streamUrl = "https://diretta.arcapuglia.it:8080/live/easytv/index.m3u8",
                category = "Italy,Easy"
            ),
            TvChannel(
                id = 550,
                name = "Entella Tv",
                logoUrl = "https://i.imgur.com/1VPXKrW.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net:443/EntellaTV/EntellaTV/playlist.m3u8",
                category = "Italy,Entella"
            ),
            TvChannel(
                id = 551,
                name = "EQUtv",
                logoUrl = "https://i.imgur.com/x9Wdz7h.png",
                streamUrl = "https://ippicabetflag-live.morescreens.com/IPPICA_1_003/304p.m3u8",
                category = "Italy,EQUtv"
            ),
            TvChannel(
                id = 552,
                name = "Equos Tv",
                logoUrl = "https://i.imgur.com/YwyfNDF.png",
                streamUrl = "https://dacastmmd.mmdlive.lldns.net/dacastmmd/2824fb123d5e44b797232c7abf8195da/playlist.m3u8",
                category = "Italy,Equos"
            ),
            TvChannel(
                id = 553,
                name = "Espansione Tv",
                logoUrl = "https://i.imgur.com/mm9HKpD.png",
                streamUrl = "https://srvx1.selftv.video/espansione/smil:live.smil/playlist.m3u8",
                category = "Italy,Espansione"
            ),
            TvChannel(
                id = 554,
                name = "Esperia Tv",
                logoUrl = "https://patbuweb.com/tivustream/chanlogoz/ita/esperiatv.png",
                streamUrl = "https://59d7d6f47d7fc.streamlock.net/esperiatv/esperiatv/playlist.m3u8",
                category = "Italy,Esperia"
            ),
            TvChannel(
                id = 555,
                name = "Etna Espresso Channel",
                logoUrl = "https://i.imgur.com/hMUxytZ.png",
                streamUrl = "https://5db313b643fd8.streamlock.net/Etnachannelponte/Etnachannelponte/playlist.m3u8",
                category = "Italy,Etna"
            ),
            TvChannel(
                id = 556,
                name = "è Tv Marche",
                logoUrl = "https://i.imgur.com/vxgbFnR.png",
                streamUrl = "https://live.ipstream.it/etvmarche/etvmarche.stream/playlist.m3u8",
                category = "Italy,è"
            ),
            TvChannel(
                id = 557,
                name = "è Tv Rete7",
                logoUrl = "https://i.imgur.com/FXFzJhM.png",
                streamUrl = "https://live.ipstream.it/etv/etv.stream/playlist.m3u8",
                category = "Italy,è"
            ),
            TvChannel(
                id = 558,
                name = "è Tv Umbria",
                logoUrl = "https://i.imgur.com/DASRCe2.png",
                streamUrl = "https://live.ipstream.it/etvumbria/etvumbria.stream/playlist.m3u8",
                category = "Italy,è"
            ),
            TvChannel(
                id = 559,
                name = "Euro Tv",
                logoUrl = "https://i.imgur.com/HCl5Zbu.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/eurotv/eurotv/playlist.m3u8",
                category = "Italy,Euro"
            ),
            TvChannel(
                id = 560,
                name = "Extra Tv",
                logoUrl = "https://i.imgur.com/KCBurST.png",
                streamUrl = "https://rst2.saiuzwebnetwork.it:8081/extratvlive/index.m3u8",
                category = "Italy,Extra"
            ),
            TvChannel(
                id = 561,
                name = "FM Tv Marche",
                logoUrl = "https://i.imgur.com/yY01NhL.jpg",
                streamUrl = "https://bbtv.intvstream.net:3988/hybrid/play.m3u8",
                category = "Italy,FM"
            ),
            TvChannel(
                id = 562,
                name = "Fano Tv",
                logoUrl = "https://i.imgur.com/orqEzJ6.png",
                streamUrl = "https://diretta.arcapuglia.it:8080/live/fanotv/index.m3u8",
                category = "Italy,Fano"
            ),
            TvChannel(
                id = 563,
                name = "Fascino Tv",
                logoUrl = "https://i.imgur.com/4XYYY5B.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/canale157/canale157/playlist.m3u8",
                category = "Italy,Fascino"
            ),
            TvChannel(
                id = 564,
                name = "GRP Televisione",
                logoUrl = "https://i.imgur.com/1zNPpVE.png",
                streamUrl = "https://webstream.multistream.it/memfs/a3195c96-f884-4c74-924f-2648814fc0b5.m3u8",
                category = "Italy,GRP"
            ),
            TvChannel(
                id = 565,
                name = "GarganoTv",
                logoUrl = "https://i.imgur.com/OlJohKK.png",
                streamUrl = "https://cdn80-ger.azotosolutions.com:8443/cdnedge3/smil:live3.smil/playlist.m3u8",
                category = "Italy,GarganoTv"
            ),
            TvChannel(
                id = 566,
                name = "Giornale Radio Tv",
                logoUrl = "https://i.imgur.com/TMtvCLL.jpg",
                streamUrl = "https://5f204aff97bee.streamlock.net/GR_tv/livestream/playlist.m3u8",
                category = "Italy,Giornale"
            ),
            TvChannel(
                id = 567,
                name = "Giovanni Paolo Tv",
                logoUrl = "https://i.imgur.com/GH5eJE6.png",
                streamUrl = "https://media2021.rtvweb.com/giovannipaolotv/web/chunklist_w663456797.m3u8",
                category = "Italy,Giovanni"
            ),
            TvChannel(
                id = 568,
                name = "Globus Television",
                logoUrl = "https://i.imgur.com/yUTYqCv.png",
                streamUrl = "https://cdn.cubws.com/live/globus.m3u8",
                category = "Italy,Globus"
            ),
            TvChannel(
                id = 569,
                name = "Gold Tv",
                logoUrl = "https://i.imgur.com/3rVi4kD.png",
                streamUrl = "https://streaming.softwarecreation.it/GoldTv/GoldTv/playlist.m3u8",
                category = "Italy,Gold"
            ),
            TvChannel(
                id = 570,
                name = "GO-TV Channel",
                logoUrl = "https://i.imgur.com/xgjrAAn.png",
                streamUrl = "https://6zklxkbbdw9b-hls-live.mariatvcdn.it/msmotor/2f759512164fc6fe4acbed6a5648993a.sdp/playlist.m3u8",
                category = "Italy,GO-TV"
            ),
            TvChannel(
                id = 571,
                name = "GS Channel",
                logoUrl = "https://i.imgur.com/ExfxCRv.png",
                streamUrl = "https://rst.saiuzwebnetwork.it:8081/retereggio/index.m3u8",
                category = "Italy,GS"
            ),
            TvChannel(
                id = 572,
                name = "Icaro Tv Rimini",
                logoUrl = "https://i.imgur.com/z05VSSN.png",
                streamUrl = "https://59d7d6f47d7fc.streamlock.net/icarotv/icarotv/playlist.m3u8",
                category = "Italy,Icaro"
            ),
            TvChannel(
                id = 573,
                name = "Idea Plus",
                logoUrl = "https://i.imgur.com/2edmxYF.png",
                streamUrl = "https://rst.saiuzwebnetwork.it:19360/teleidea/teleidea.m3u8",
                category = "Italy,Idea"
            ),
            TvChannel(
                id = 574,
                name = "il61",
                logoUrl = "https://i.imgur.com/1rUZsBv.png",
                streamUrl = "https://5a1178b42cc03.streamlock.net/travel/travel/playlist.m3u8",
                category = "Italy,il61"
            ),
            TvChannel(
                id = 575,
                name = "Italia 2 Tv",
                logoUrl = "https://i.imgur.com/ISbxfY0.png",
                streamUrl = "https://59d7d6f47d7fc.streamlock.net/italia2/italia2/playlist.m3u8",
                category = "Italy,Italia"
            ),
            TvChannel(
                id = 576,
                name = "Italia 7",
                logoUrl = "https://i.imgur.com/YBXkY4w.png",
                streamUrl = "https://streaming.softwarecreation.it/Italia7/Italia7/playlist.m3u8",
                category = "Italy,Italia"
            ),
            TvChannel(
                id = 577,
                name = "Italia 8 Prestige",
                logoUrl = "https://i.imgur.com/uDxWI4a.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/italia8prestige/italia8prestige/playlist.m3u8",
                category = "Italy,Italia"
            ),
            TvChannel(
                id = 578,
                name = "Italia Channel",
                logoUrl = "https://i.imgur.com/zuuKXGv.png",
                streamUrl = "https://stream1.aswifi.it/italiachannel/stream/index.m3u8",
                category = "Italy,Italia"
            ),
            TvChannel(
                id = 579,
                name = "Iunior Tv",
                logoUrl = "https://i.imgur.com/9jeNlLE.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/iuniortv/iuniortv/playlist.m3u8",
                category = "Italy,Iunior"
            ),
            TvChannel(
                id = 580,
                name = "L'Altro Corriere Tv",
                logoUrl = "https://i.imgur.com/dgj79J3.png",
                streamUrl = "https://stream.cp.ets-sistemi.it:1936/laltrocorriere-tv/laltrocorriere-tv/playlist.m3u8",
                category = "Italy,L'Altro"
            ),
            TvChannel(
                id = 581,
                name = "La Nuova Tv",
                logoUrl = "https://i.imgur.com/wg8FhdN.png",
                streamUrl = "https://5dcabf026b188.streamlock.net/lanuovatvbas/livestream/playlist.m3u8",
                category = "Italy,La"
            ),
            TvChannel(
                id = 582,
                name = "La Tenda Tv",
                logoUrl = "https://i.imgur.com/XnBp0hT.png",
                streamUrl = "https://2-fss-1.streamhoster.com/pl_148/206202-2980948-1/playlist.m3u8",
                category = "Italy,La"
            ),
            TvChannel(
                id = 583,
                name = "La tr3 Marsala",
                logoUrl = "https://i.imgur.com/XlxpfEx.png",
                streamUrl = "https://tsw.streamingwebtv24.it:1936/eslife1/eslife1/playlist.m3u8",
                category = "Italy,La"
            ),
            TvChannel(
                id = 584,
                name = "LaC News 24",
                logoUrl = "https://i.imgur.com/02vCECa.png",
                streamUrl = "https://f5842579ff984c1c98d63b8d789673eb.msvdn.net/live/S27391994/HVvPMzy/playlist.m3u8",
                category = "Italy,LaC"
            ),
            TvChannel(
                id = 585,
                name = "LaC Tv Calabria",
                logoUrl = "https://i.imgur.com/2Ef6crS.png",
                streamUrl = "https://f5842579ff984c1c98d63b8d789673eb.msvdn.net/live/S47282891/JWjL3xqPf4bX/playlist.m3u8",
                category = "Italy,LaC"
            ),
            TvChannel(
                id = 586,
                name = "Lab Tv",
                logoUrl = "https://i.imgur.com/OpRS6Fl.png",
                streamUrl = "https://customer-yzibk50951uq418a.cloudflarestream.com/a58893dee4b9922a75e41b4ec4243f84/manifest/video.m3u8",
                category = "Italy,Lab"
            ),
            TvChannel(
                id = 587,
                name = "Lazio Tv",
                logoUrl = "https://i.imgur.com/DAj5Uwb.png",
                streamUrl = "https://streaming.softwarecreation.it/LazioTv/LazioTv/playlist.m3u8",
                category = "Italy,Lazio"
            ),
            TvChannel(
                id = 588,
                name = "Le Cronache Lucane Tv",
                logoUrl = "https://i.imgur.com/EBY3IZL.jpg",
                streamUrl = "http://stucazz.com:8888/hls/cronache.m3u8",
                category = "Italy,Le"
            ),
            TvChannel(
                id = 589,
                name = "Lira Tv",
                logoUrl = "https://i.imgur.com/S0ReVEo.png",
                streamUrl = "https://a928c0678d284da5b383f29ecc5dfeec.msvdn.net/live/S57315730/8kTBWibNteJA/playlist.m3u8",
                category = "Italy,Lira"
            ),
            TvChannel(
                id = 590,
                name = "Lombardia Tv",
                logoUrl = "https://i.imgur.com/aksVy9f.jpg",
                streamUrl = "https://5db313b643fd8.streamlock.net/lmbiatv/lmbiatv/playlist.m3u8",
                category = "Italy,Lombardia"
            ),
            TvChannel(
                id = 591,
                name = "Love in Venice",
                logoUrl = "https://i.imgur.com/lLBzzce.png",
                streamUrl = "http://59d7d6f47d7fc.streamlock.net/loveinvenice/loveinvenice/playlist.m3u8",
                category = "Italy,Love"
            ),
            TvChannel(
                id = 592,
                name = "Lucania Tv",
                logoUrl = "https://i.imgur.com/wuUNVR5.png",
                streamUrl = "https://cdn15.streamshow.it/cloud-lucaniatv/lucaniatv/playlist.m3u8",
                category = "Italy,Lucania"
            ),
            TvChannel(
                id = 593,
                name = "Made in BO",
                logoUrl = "https://i.imgur.com/WFnrMS0.png",
                streamUrl = "https://srvx1.selftv.video/dmchannel/live/playlist.m3u8",
                category = "Italy,Made"
            ),
            TvChannel(
                id = 594,
                name = "Maria Vision",
                logoUrl = "https://i.imgur.com/fdx5YXi.png",
                streamUrl = "https://1601580044.rsc.cdn77.org/live/_jcn_/amls:CHANNEL_2/playlist.m3u8",
                category = "Italy,Maria"
            ),
            TvChannel(
                id = 595,
                name = "Matrix Tv",
                logoUrl = "https://i.imgur.com/m1HeXrn.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/GrandeItalia/GrandeItalia/playlist.m3u8",
                category = "Italy,Matrix"
            ),
            TvChannel(
                id = 596,
                name = "Mediterranea Tv",
                logoUrl = "https://i.imgur.com/GUTOqRt.png",
                streamUrl = "https://stream1.aswifi.it/mediterraneatv/live/index.m3u8",
                category = "Italy,Mediterranea"
            ),
            TvChannel(
                id = 597,
                name = "Medjugorje Italia Tv",
                logoUrl = "https://i.imgur.com/hkZScXf.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/medjugorjeitaliatv/medjugorjeitaliatv/playlist.m3u8",
                category = "Italy,Medjugorje"
            ),
            TvChannel(
                id = 598,
                name = "Medjugorje Tv Puglia",
                logoUrl = "https://i.imgur.com/IWBeddh.png",
                streamUrl = "https://diretta.arcapuglia.it:8080/live/medjugorietv/index.m3u8",
                category = "Italy,Medjugorje"
            ),
            TvChannel(
                id = 599,
                name = "Minformo Tv",
                logoUrl = "https://i.imgur.com/VJNtnZM.jpg",
                streamUrl = "https://5db313b643fd8.streamlock.net:443/MinformoTV/MinformoTV/playlist.m3u8",
                category = "Italy,Minformo"
            ),
            TvChannel(
                id = 600,
                name = "Motori Tv",
                logoUrl = "https://i.imgur.com/NWXQKbl.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/servizio01/servizio01/playlist.m3u8",
                category = "Italy,Motori"
            ),
            TvChannel(
                id = 601,
                name = "NTI Canale 271",
                logoUrl = "https://i.imgur.com/zlmcUe0.jpg",
                streamUrl = "https://www.ntimedia.it/video/S0B/S0B_master.m3u8",
                category = "Italy,NTI"
            ),
            TvChannel(
                id = 602,
                name = "Nuova TV Nazionale",
                logoUrl = "https://i.imgur.com/QWlRuXg.png",
                streamUrl = "https://stream4.xdevel.com/video0s975955-782/stream/playlist.m3u8",
                category = "Italy,Nuova"
            ),
            TvChannel(
                id = 603,
                name = "Nuova Tv 1",
                logoUrl = "https://i.imgur.com/1yqTZhR.png",
                streamUrl = "https://nuovatv.net:8443/tv/stream.m3u8",
                category = "Italy,Nuova"
            ),
            TvChannel(
                id = 604,
                name = "Nuova Tv 2",
                logoUrl = "https://i.imgur.com/0vauyV3.png",
                streamUrl = "https://nuovatv.net:8443/tv2/stream.m3u8",
                category = "Italy,Nuova"
            ),
            TvChannel(
                id = 605,
                name = "Nuvola Tv",
                logoUrl = "https://i.imgur.com/EDGez2x.png",
                streamUrl = "https://stream.nuvola.tv:8181/memfs/4aaa6328-1879-4ebf-b18a-498146d0c61c.m3u8",
                category = "Italy,Nuvola"
            ),
            TvChannel(
                id = 606,
                name = "Odeon 24",
                logoUrl = "https://i.imgur.com/M1tVBuH.png",
                streamUrl = "https://streaming.softwarecreation.it/Odeon/Odeon/manifest.m3u8",
                category = "Italy,Odeon"
            ),
            TvChannel(
                id = 607,
                name = "Ofanto Tv",
                logoUrl = "https://i.imgur.com/UCgATWn.png",
                streamUrl = "https://videostream.isgm.it:3276/live/tvofantolive.m3u8",
                category = "Italy,Ofanto"
            ),
            TvChannel(
                id = 608,
                name = "Onda Novara Tv",
                logoUrl = "https://i.imgur.com/Qoh9CFy.png",
                streamUrl = "https://585b674743bbb.streamlock.net/9006/9006/playlist.m3u8",
                category = "Italy,Onda"
            ),
            TvChannel(
                id = 609,
                name = "Onda Tv Sicilia",
                logoUrl = "https://i.imgur.com/0c5Y6lr.png",
                streamUrl = "https://5926fc9c7c5b2.streamlock.net/9040/9040/playlist.m3u8",
                category = "Italy,Onda"
            ),
            TvChannel(
                id = 610,
                name = "Onda Web Radio",
                logoUrl = "https://i.imgur.com/3hTvrC8.jpg",
                streamUrl = "http://178.33.224.197:1935/ondaradioweb/ondaradioweb/playlist.m3u8",
                category = "Italy,Onda"
            ),
            TvChannel(
                id = 611,
                name = "Ora Tv",
                logoUrl = "https://i.imgur.com/clWVrvE.png",
                streamUrl = "https://5db313b643fd8.streamlock.net/OraTv/OraTv/playlist.m3u8",
                category = "Italy,Ora"
            ),
            TvChannel(
                id = 612,
                name = "Orler Tv",
                logoUrl = "https://i.imgur.com/dBkxD8e.png",
                streamUrl = "https://w1.mediastreaming.it/orlertv/livestream/playlist.m3u8",
                category = "Italy,Orler"
            ),
            TvChannel(
                id = 613,
                name = "Otto Channel",
                logoUrl = "https://i.imgur.com/HRonD2N.png",
                streamUrl = "https://ottop-live-meride.akamaized.net/hls/live/2039996/ch1/playlist.m3u8",
                category = "Italy,Otto"
            ),
            TvChannel(
                id = 614,
                name = "Padre Pio Tv",
                logoUrl = "https://i.imgur.com/7ajxEPH.png",
                streamUrl = "https://600f07e114306.streamlock.net/PadrePioTV/livestream/playlist.m3u8",
                category = "Italy,Padre"
            ),
            TvChannel(
                id = 615,
                name = "Paradise Tv",
                logoUrl = "https://i.imgur.com/okIBfIb.jpg",
                streamUrl = "https://tsw.streamingwebtv24.it:1936/paradisetv/paradisetv/playlist.m3u8",
                category = "Italy,Paradise"
            ),
            TvChannel(
                id = 616,
                name = "Parole di Vita",
                logoUrl = "https://i.imgur.com/M9mIiZD.png",
                streamUrl = "https://64b16f23efbee.streamlock.net/paroledivita/paroledivita/playlist.m3u8",
                category = "Italy,Parole"
            ),
            TvChannel(
                id = 617,
                name = "Partenope Tv",
                logoUrl = "https://i.imgur.com/FtuWkj1.png",
                streamUrl = "https://diretta.arcapuglia.it:8080/live/partenope/index.m3u8",
                category = "Italy,Partenope"
            ),
            TvChannel(
                id = 618,
                name = "Peer Tv Alto Adige",
                logoUrl = "https://www.peer.biz/peertv-iptv/peer-tv-alto-adige.png",
                streamUrl = "https://iptv.peer.biz/live/peertv-it.m3u8",
                category = "Italy,Peer"
            ),
            TvChannel(
                id = 619,
                name = "Peer TV Südtirol",
                logoUrl = "https://www.peer.biz/peertv-iptv/peer-tv-suedtirol.png",
                streamUrl = "https://iptv.peer.biz/live/peertv.m3u8",
                category = "Italy,Peer"
            ),
            TvChannel(
                id = 620,
                name = "Pop Tv",
                logoUrl = "https://i.imgur.com/TeolCu9.png",
                streamUrl = "https://stream1.aswifi.it/poptelevision/live/index.m3u8",
                category = "Italy,Pop"
            ),
            TvChannel(
                id = 621,
                name = "Prima Tv Napoli",
                logoUrl = "https://i.imgur.com/yPuQeEy.jpg",
                streamUrl = "https://57068da1deb21.streamlock.net/primatvnapoli/primatvnapoli/playlist.m3u8",
                category = "Italy,Prima"
            ),
            TvChannel(
                id = 622,
                name = "Prima Tv Sicilia",
                logoUrl = "https://i.imgur.com/br45JER.png",
                streamUrl = "https://5db313b643fd8.streamlock.net/PrimaTV/PrimaTV/playlist.m3u8",
                category = "Italy,Prima"
            ),
            TvChannel(
                id = 623,
                name = "PrimaFREE",
                logoUrl = "https://i.imgur.com/YrSSmOL.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/primafree/primafree/playlist.m3u8",
                category = "Italy,PrimaFREE"
            ),
            TvChannel(
                id = 624,
                name = "Primantenna Torino",
                logoUrl = "https://i.imgur.com/sqEcPFs.gif",
                streamUrl = "https://5f22d76e220e1.streamlock.net/primantenna14/primantenna14/playlist.m3u8",
                category = "Italy,Primantenna"
            ),
            TvChannel(
                id = 625,
                name = "Primocanale",
                logoUrl = "https://i.imgur.com/xWF1A1U.png",
                streamUrl = "https://msh0203.stream.seeweb.it/live/flv:stream2.sdp/playlist.m3u8",
                category = "Italy,Primocanale"
            ),
            TvChannel(
                id = 626,
                name = "Promovideo Tv",
                logoUrl = "https://i.imgur.com/MwK9HVG.png",
                streamUrl = "https://media2021.rtvweb.com/promovideo_web/promovideo/playlist.m3u8",
                category = "Italy,Promovideo"
            ),
            TvChannel(
                id = 627,
                name = "Quarto Canale Flegreo",
                logoUrl = "https://i.imgur.com/8RKY3Du.png",
                streamUrl = "http://live.mariatvcdn.com/dialogos/171e41deedf405f10c7dd6311387fb43.sdp/playlist.m3u8",
                category = "Italy,Quarto"
            ),
            TvChannel(
                id = 628,
                name = "Radio 3M InBlu",
                logoUrl = "https://i.imgur.com/d7O7Uqa.png",
                streamUrl = "https://stream.mariatvcdn.com/telemistrettaradio/900bfcc0f9012ea272584fd5ff5281b8.sdp/playlist.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 629,
                name = "Radio 51 Tv",
                logoUrl = "https://uaznao.com/wp-content/uploads/2023/03/radio51.png",
                streamUrl = "https://59d7d6f47d7fc.streamlock.net/canale51/canale51/chunklist_w1193883900.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 630,
                name = "Radio Birikina Tv",
                logoUrl = "https://uaznao.com/wp-content/uploads/2023/03/radiobirikina.png",
                streamUrl = "https://tvd-bk.fluid.stream/RadioBirikinaTV/livestream/chunklist_w84398277.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 631,
                name = "Radio Bruno Tv",
                logoUrl = "https://i.imgur.com/y4vKE83.png",
                streamUrl = "https://router.xdevel.com/video0s975758-473/stream/playlist.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 632,
                name = "Radio Ibiza",
                logoUrl = "https://i.imgur.com/uu0DHY5.png",
                streamUrl = "https://str48.fluid.stream/RadioIbizaTV/livestream/playlist.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 633,
                name = "Radio Immagine Tv",
                logoUrl = "https://i.imgur.com/iQlXRAB.png",
                streamUrl = "https://media.velcom.it:8081/RadioImmagineTV/index.fmp4.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 634,
                name = "Radio Italia Cina Tv",
                logoUrl = "https://i.imgur.com/QGkyrO3.png",
                streamUrl = "https://585b674743bbb.streamlock.net/9054/9054/playlist.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 635,
                name = "Radio Libertà",
                logoUrl = "https://i.imgur.com/HRvPlf5.png",
                streamUrl = "https://router.xdevel.com/video0s975360-67/stream/playlist_dvr.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 636,
                name = "Radio m2o Tv",
                logoUrl = "https://i.imgur.com/wl30UFj.png",
                streamUrl = "https://4c4b867c89244861ac216426883d1ad0.msvdn.net/live/S62628868/uhdWBlkC1AoO/playlist.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 637,
                name = "Radio Monte Kronio Tv (R.M.K.)",
                logoUrl = "https://i.imgur.com/t0I2Shi.jpg",
                streamUrl = "https://648026e87a75e.streamlock.net/rmktv/rmktv/playlist.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 638,
                name = "Radio Norba Tv",
                logoUrl = "https://i.imgur.com/qftBPM9.png",
                streamUrl = "https://router.xdevel.com/video0s975885-462/stream/playlist_dvr.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 639,
                name = "Radio Piter Pan Tv",
                logoUrl = "https://uaznao.com/wp-content/uploads/2023/03/radiopiterpan.png",
                streamUrl = "https://tvd-piter.fluid.stream/RadioPiterpanTV/livestream/chunklist_w1866496033.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 640,
                name = "Radio Radio Tv",
                logoUrl = "https://i.imgur.com/iKuRg2b.png",
                streamUrl = "https://200912.global.ssl.fastly.net/646b335e2291a2022444bb7c/live_22f84390fe1411ed919df3da85a483cc/rewind-14400.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 641,
                name = "Radio Radiosa Tv",
                logoUrl = "https://i.imgur.com/8kqyxvz.png",
                streamUrl = "https://stream7.zivoli.it/radiosatv/radiosatv/playlist.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 642,
                name = "Radio Roma",
                logoUrl = "https://i.imgur.com/RKvfStm.png",
                streamUrl = "https://585b674743bbb.streamlock.net/9044/9044/playlist.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 643,
                name = "Radio Roma Television",
                logoUrl = "https://i.imgur.com/RKvfStm.png",
                streamUrl = "https://5926fc9c7c5b2.streamlock.net/rtfeunawfu/rtfeunawfu/playlist.m3u8",
                category = "Italy,Radio"
            ),
            TvChannel(
                id = 644,
                name = "Ran Friul",
                logoUrl = "https://i.imgur.com/Qs5eQPM.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/RanFriul/RanFriul/playlist.m3u8",
                category = "Italy,Ran"
            ),
            TvChannel(
                id = 645,
                name = "RDE Tv",
                logoUrl = "https://i.imgur.com/NiwPlrr.png",
                streamUrl = "https://rst2.saiuzwebnetwork.it:8081/rdetrieste/index.m3u8",
                category = "Italy,RDE"
            ),
            TvChannel(
                id = 646,
                name = "Reggio Tv",
                logoUrl = "https://i.imgur.com/merrg2C.png",
                streamUrl = "https://cdn10.streamshow.it/cloud-reggiotv/reggiotv/playlist.m3u8",
                category = "Italy,Reggio"
            ),
            TvChannel(
                id = 647,
                name = "Rei Tv",
                logoUrl = "https://i.imgur.com/YNRWFOo.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/reitv/reitv/playlist.m3u8",
                category = "Italy,Rei"
            ),
            TvChannel(
                id = 648,
                name = "Rete 55",
                logoUrl = "https://i.imgur.com/EsZn2cj.png",
                streamUrl = "https://live1.giocabet.tv/stream/6/index.m3u8",
                category = "Italy,Rete"
            ),
            TvChannel(
                id = 649,
                name = "Rete 8 Vga",
                logoUrl = "https://i.imgur.com/3wF2AJX.jpg",
                streamUrl = "https://604e46ac2bdee.streamlock.net:1936/rete8_1/rete8_1/playlist.m3u8",
                category = "Italy,Rete"
            ),
            TvChannel(
                id = 650,
                name = "Rete 8",
                logoUrl = "https://i.imgur.com/bGsjPRh.png",
                streamUrl = "https://64b16f23efbee.streamlock.net/rete8/rete8/playlist.m3u8",
                category = "Italy,Rete"
            ),
            TvChannel(
                id = 651,
                name = "Rete 8 Sport",
                logoUrl = "https://i.imgur.com/uUAjWlF.png",
                streamUrl = "https://64b16f23efbee.streamlock.net/rete8sport/rete8sport/playlist.m3u8",
                category = "Italy,Rete"
            ),
            TvChannel(
                id = 652,
                name = "Rete Biella Tv",
                logoUrl = "https://i.imgur.com/e2ryHx7.png",
                streamUrl = "https://sb.top-ix.org/retebiella/streaming/playlist.m3u8",
                category = "Italy,Rete"
            ),
            TvChannel(
                id = 653,
                name = "Rete Mia",
                logoUrl = "https://i.imgur.com/kCJ621Q.png",
                streamUrl = "https://5db313b643fd8.streamlock.net/rete/rete/playlist.m3u8",
                category = "Italy,Rete"
            ),
            TvChannel(
                id = 654,
                name = "Rete Oro Tv",
                logoUrl = "https://i.imgur.com/OCxGtwA.png",
                streamUrl = "https://5926fc9c7c5b2.streamlock.net/9094/9094/playlist.m3u8",
                category = "Italy,Rete"
            ),
            TvChannel(
                id = 655,
                name = "Rete Sole",
                logoUrl = "https://i.imgur.com/u0ezKgE.png",
                streamUrl = "https://5926fc9c7c5b2.streamlock.net/9332/9332/playlist.m3u8",
                category = "Italy,Rete"
            ),
            TvChannel(
                id = 656,
                name = "Rete Tv Italia",
                logoUrl = "https://i.imgur.com/lXGWoV9.png",
                streamUrl = "https://57068da1deb21.streamlock.net/retetvitalia/retetvitalia/playlist.m3u8",
                category = "Italy,Rete"
            ),
            TvChannel(
                id = 657,
                name = "Rete Veneta",
                logoUrl = "https://i.imgur.com/cInhQFp.png",
                streamUrl = "http://wms.shared.streamshow.it/reteveneta/reteveneta/playlist.m3u8",
                category = "Italy,Rete"
            ),
            TvChannel(
                id = 658,
                name = "Roma Tv 82",
                logoUrl = "https://i.imgur.com/C3UnlFF.png",
                streamUrl = "https://streaming.softwarecreation.it/RomaCH71/RomaCH71/playlist.m3u8",
                category = "Italy,Roma"
            ),
            TvChannel(
                id = 659,
                name = "Rossini Tv",
                logoUrl = "https://i.imgur.com/K0Em0nx.jpg",
                streamUrl = "https://stream.rossinitv.it/memfs/3bca4ad7-adfc-4610-ac0e-e826743ddc57.m3u8",
                category = "Italy,Rossini"
            ),
            TvChannel(
                id = 660,
                name = "RTC Quarta Rete",
                logoUrl = "https://i.imgur.com/rFGA6qL.png",
                streamUrl = "https://msh0232.stream.seeweb.it/live/stream00.sdp/playlist.m3u8",
                category = "Italy,RTC"
            ),
            TvChannel(
                id = 661,
                name = "RTC Telecalabria",
                logoUrl = "https://i.imgur.com/tTYLcuh.jpg",
                streamUrl = "https://w1.mediastreaming.it/calabriachannel/livestream/playlist.m3u8",
                category = "Italy,RTC"
            ),
            TvChannel(
                id = 662,
                name = "RTI Calabria",
                logoUrl = "https://i.imgur.com/hVzEvmo.jpg",
                streamUrl = "https://stream.ets-sistemi.it:8081/rticalabria/index.m3u8",
                category = "Italy,RTI"
            ),
            TvChannel(
                id = 663,
                name = "RTL 102.5 + Plus",
                logoUrl = "https://i.imgur.com/mPqDtCO.png",
                streamUrl = "https://streamcdng14-dd782ed59e2a4e86aabf6fc508674b59.msvdn.net/live/S82929343/cAcsSu4Wecc5/chunklist_b5256000.m3u8",
                category = "Italy,RTL"
            ),
            TvChannel(
                id = 664,
                name = "RTM Manduria",
                logoUrl = "https://i.imgur.com/WwzU0EP.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/rtm/rtm/playlist.m3u8",
                category = "Italy,RTM"
            ),
            TvChannel(
                id = 665,
                name = "RTR99 Tv",
                logoUrl = "https://i.imgur.com/mkO95pD.png",
                streamUrl = "https://5e73cf528f404.streamlock.net/RTR99TV/livestream/playlist.m3u8",
                category = "Italy,RTR99"
            ),
            TvChannel(
                id = 666,
                name = "Rtp Tv",
                logoUrl = "https://i.imgur.com/I1hYI0C.png",
                streamUrl = "http://flash2.xdevel.com/rtptv/rtptv/playlist.m3u8",
                category = "Italy,Rtp"
            ),
            TvChannel(
                id = 667,
                name = "Rttr",
                logoUrl = "https://i.imgur.com/z7xMArA.png",
                streamUrl = "https://5f204aff97bee.streamlock.net/RTTRlive/livestream/playlist.m3u8",
                category = "Italy,Rttr"
            ),
            TvChannel(
                id = 668,
                name = "Rtv 38 Toscana",
                logoUrl = "https://i.imgur.com/xqlhJqK.png",
                streamUrl = "https://845d8509d2cb4f249dd0b2ae5755b6c2.msvdn.net/rtv38/rtv38_live_main/mainabr/playlist_dvr.m3u8",
                category = "Italy,Rtv"
            ),
            TvChannel(
                id = 669,
                name = "SL 48 Tv",
                logoUrl = "https://i.imgur.com/b58oouu.jpg",
                streamUrl = "http://media.velcom.it:1935/sl48/sl48/playlist.m3u8",
                category = "Italy,SL"
            ),
            TvChannel(
                id = 670,
                name = "ST Europe Channel",
                logoUrl = "https://i.imgur.com/QpPgSfr.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/steuropetv/steuropetv/playlist.m3u8",
                category = "Italy,ST"
            ),
            TvChannel(
                id = 671,
                name = "Sardegna 1",
                logoUrl = "https://i.imgur.com/YNEW2h2.png",
                streamUrl = "https://7e1cc2454f2242afabe05cc0a2f483cd.msvdn.net/live/S30721796/ZS3Xu8mn5f0J/playlist.m3u8",
                category = "Italy,Sardegna"
            ),
            TvChannel(
                id = 672,
                name = "Set Tv Cilento",
                logoUrl = "https://i.imgur.com/qN5D1jD.png",
                streamUrl = "https://stream1.aswifi.it/settv/live/index.m3u8",
                category = "Italy,Set"
            ),
            TvChannel(
                id = 673,
                name = "Sesta Rete",
                logoUrl = "https://i.imgur.com/0B0S2gI.png",
                streamUrl = "https://stream10.xdevel.com/video0s977089-1792/stream/playlist.m3u8",
                category = "Italy,Sesta"
            ),
            TvChannel(
                id = 674,
                name = "Sicilia 24 Tv",
                logoUrl = "https://i.imgur.com/GhQX36O.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/sicilia24/sicilia24/playlist.m3u8",
                category = "Italy,Sicilia"
            ),
            TvChannel(
                id = 675,
                name = "Sicilia Tv",
                logoUrl = "https://i.imgur.com/I5FoThW.png",
                streamUrl = "https://stream9.xdevel.com/video0s976441-1226/stream/playlist.m3u8",
                category = "Italy,Sicilia"
            ),
            TvChannel(
                id = 676,
                name = "Sienatv",
                logoUrl = "https://i.imgur.com/gcysky4.png",
                streamUrl = "https://router.xdevel.com/video0s976727-1441/stream/playlist.m3u8",
                category = "Italy,Sienatv"
            ),
            TvChannel(
                id = 677,
                name = "Sophia Tv",
                logoUrl = "https://i.imgur.com/fiLNK3b.jpg",
                streamUrl = "https://bild-und-ton.stream/sophiatv-it/smil:sophia-tv-it.smil/playlist.m3u8",
                category = "Italy,Sophia"
            ),
            TvChannel(
                id = 678,
                name = "Stiletv",
                logoUrl = "https://i.imgur.com/ZP3cJi7.png",
                streamUrl = "https://proxy.media.convergenze.it/stiletv/streams/oQOFd7JglHjO1631525551097.m3u8",
                category = "Italy,Stiletv"
            ),
            TvChannel(
                id = 679,
                name = "Super J Tv",
                logoUrl = "https://i.imgur.com/5oy5Nuu.png",
                streamUrl = "https://59d39900ebfb8.streamlock.net/SuperJtv/SuperJtv/playlist.m3u8",
                category = "Italy,Super"
            ),
            TvChannel(
                id = 680,
                name = "Super Six",
                logoUrl = "https://i.imgur.com/kHSuyub.png",
                streamUrl = "https://5db313b643fd8.streamlock.net/SUPERSIXLombardia/SUPERSIXLombardia/playlist.m3u8",
                category = "Italy,Super"
            ),
            TvChannel(
                id = 681,
                name = "Supertv",
                logoUrl = "https://i.imgur.com/7gUZcEh.png",
                streamUrl = "http://wms.shared.streamshow.it:1935/supertv/supertv/live.m3u8",
                category = "Italy,Supertv"
            ),
            TvChannel(
                id = 682,
                name = "T9",
                logoUrl = "https://i.imgur.com/XzL05Py.png",
                streamUrl = "https://streaming.softwarecreation.it/tnove/tnove/playlist.m3u8",
                category = "Italy,T9"
            ),
            TvChannel(
                id = 683,
                name = "TRC Santeramo",
                logoUrl = "https://i.imgur.com/VbYdS8P.jpg",
                streamUrl = "https://stream7.livinlive.it/trc/trc/playlist.m3u8",
                category = "Italy,TRC"
            ),
            TvChannel(
                id = 684,
                name = "TRL Tele Radio Leo",
                logoUrl = "https://i.imgur.com/qAagkJT.png",
                streamUrl = "https://5db313b643fd8.streamlock.net/TRL/TRL/playlist.m3u8",
                category = "Italy,TRL"
            ),
            TvChannel(
                id = 685,
                name = "TSD Tv Arezzo(Tele San Domenico)",
                logoUrl = "https://i.imgur.com/WQ8eQXc.png",
                streamUrl = "https://stream.mariatvcdn.com/tsd/7c59373bfdb38201b9215ff86f0ce6af.sdp/playlist.m3u8",
                category = "Italy,TSD"
            ),
            TvChannel(
                id = 686,
                name = "TVL (TV Libera Pistoia)",
                logoUrl = "https://i.imgur.com/07geF0L.png",
                streamUrl = "http://live.mariatvcdn.com/mariatvcdn/70564e1c6884c007c76f0c128d679eed.sdp/mono.m3u8",
                category = "Italy,TVL"
            ),
            TvChannel(
                id = 687,
                name = "Tcf Tv",
                logoUrl = "https://i.imgur.com/fiylFs2.jpg",
                streamUrl = "https://stream10.xdevel.com/video1s977294-1864/stream/playlist.m3u8",
                category = "Italy,Tcf"
            ),
            TvChannel(
                id = 688,
                name = "Tci",
                logoUrl = "https://i.imgur.com/lCZTaKs.jpg",
                streamUrl = "https://tbn-jw.cdn.vustreams.com/live/tci/live.isml/2b7d53c5-b504-4d26-b25f-a70deb8d0faf.m3u8",
                category = "Italy,Tci"
            ),
            TvChannel(
                id = 689,
                name = "Teatro Tv",
                logoUrl = "https://i.imgur.com/UsvffQL.png",
                streamUrl = "https://m.iostream.it/hls/teatrotv/teatrotv.m3u8",
                category = "Italy,Teatro"
            ),
            TvChannel(
                id = 690,
                name = "Tele A",
                logoUrl = "https://i.imgur.com/l7Za9KH.jpg",
                streamUrl = "https://lostream.it/hls/telea/video.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 691,
                name = "Tele Abruzzo Tv",
                logoUrl = "https://i.imgur.com/koB8J4b.jpg",
                streamUrl = "http://uk4.streamingpulse.com:1935/TeleabruzzoTV/TeleabruzzoTV/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 692,
                name = "Tele Acras",
                logoUrl = "https://i.imgur.com/90Lsv8q.png",
                streamUrl = "https://5db313b643fd8.streamlock.net/teleacras/teleacras/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 693,
                name = "Tele Arena",
                logoUrl = "https://i.imgur.com/9hsoWMO.png",
                streamUrl = "https://5e73cf528f404.streamlock.net/TeleArena/TeleArena.stream/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 694,
                name = "Tele Bari",
                logoUrl = "https://i.imgur.com/HYSz4rx.png",
                streamUrl = "https://w1.mediastreaming.it/telebari/livestream/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 695,
                name = "Tele Belluno",
                logoUrl = "https://i.imgur.com/YiM2Z7E.png",
                streamUrl = "https://live.mariatvcdn.com/telebelluno/a3b80388da9801906adf885282e73bc3.sdp/mono.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 696,
                name = "Tele Boario",
                logoUrl = "https://i.imgur.com/LlLD3L6.png",
                streamUrl = "https://stream7.xdevel.com/video0s976425-1244/stream/playlist_dvr.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 697,
                name = "Tele Bruzzano",
                logoUrl = "https://i.imgur.com/7TWbCDt.jpg",
                streamUrl = "https://playerssl.telemia.tv/fileadmin/hls/Telebruzzano/telebruzzano_mediachunks.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 698,
                name = "Tele Chiara",
                logoUrl = "https://i.imgur.com/Q5XpnXR.png",
                streamUrl = "http://fms.tvavicenza.it:1935/telechiara/diretta/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 699,
                name = "Telecolor",
                logoUrl = "https://i.imgur.com/urUG78J.png",
                streamUrl = "https://1aadf145546f475282c5b4e658c0ac4b.msvdn.net/live/324149/hlbAWtl/playlist.m3u8",
                category = "Italy,Telecolor"
            ),
            TvChannel(
                id = 700,
                name = "Tele Cupole",
                logoUrl = "https://i.imgur.com/ZmUI9zb.png",
                streamUrl = "https://nrvideo1.newradio.it/euhsbdamnx/euhsbdamnx/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 701,
                name = "Tele Estense",
                logoUrl = "https://i.imgur.com/X7i7DWo.png",
                streamUrl = "https://5e73cf528f404.streamlock.net/TeleEstense/livestream/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 702,
                name = "Tele Foggia",
                logoUrl = "https://i.imgur.com/M7tqBu9.jpg",
                streamUrl = "https://59d7d6f47d7fc.streamlock.net/telefoggia/telefoggia/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 703,
                name = "Tele Friuli",
                logoUrl = "https://i.imgur.com/AoQxZxD.png",
                streamUrl = "https://5757bf2aa08e42248fb9b9d620f5d900.msvdn.net/live/S11646715/pE3ax0lT0rBd/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 704,
                name = "Tele Ischia",
                logoUrl = "https://i.imgur.com/vihHVQn.jpg",
                streamUrl = "https://rst.saiuzwebnetwork.it:8081/teleischia/index.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 705,
                name = "Tele Jonio",
                logoUrl = "https://i.imgur.com/qJeDV8R.png",
                streamUrl = "http://59d7d6f47d7fc.streamlock.net/telejonio/telejonio/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 706,
                name = "Tele Liberta' HD",
                logoUrl = "https://i.imgur.com/XzAB5k7.jpg",
                streamUrl = "https://streaming.liberta.it/hls/liberta.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 707,
                name = "Tele Liguria Sud",
                logoUrl = "https://i.imgur.com/BeLAYJ6.jpg",
                streamUrl = "https://64b16f23efbee.streamlock.net/teleliguriasud/teleliguriasud/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 708,
                name = "Tele Mantova",
                logoUrl = "https://i.imgur.com/bkSPcs4.png",
                streamUrl = "https://5ce9406b73c33.streamlock.net/TeleMantova/livestream/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 709,
                name = "Tele Mia",
                logoUrl = "https://i.imgur.com/SdXpCwL.png",
                streamUrl = "https://playerssl.telemia.tv/fileadmin/hls/TelemiaHD/telemia85_mediachunks.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 710,
                name = "Tele Mia Extra",
                logoUrl = "https://i.imgur.com/upzBG32.png",
                streamUrl = "https://playerssl.telemia.tv/fileadmin/hls/TelemiaExtra/telemiaextra_mediachunks.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 711,
                name = "Tele Mistretta",
                logoUrl = "https://i.imgur.com/OJ3zUS0.png",
                streamUrl = "https://live.mariatvcdn.com/telemistretta/8fbcd205ada81b295ee6c211c3a80dde.sdp/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 712,
                name = "Tele Molise",
                logoUrl = "https://i.imgur.com/u5VD0x9.png",
                streamUrl = "http://185.202.128.1:1935/Telemolise4K/Telemolise4K/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 713,
                name = "Tele Nord Genova",
                logoUrl = "https://i.imgur.com/I6yegEK.png",
                streamUrl = "https://64b16f23efbee.streamlock.net/telenord/telenord/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 714,
                name = "Tele Nostra",
                logoUrl = "https://i.imgur.com/FACahKZ.png",
                streamUrl = "https://13574-8.b.cdn12.com/hls/f099fa8883.ulive/_c/master.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 715,
                name = "Tele Occidente",
                logoUrl = "https://i.imgur.com/3aOiWKa.png",
                streamUrl = "https://stream9.xdevel.com/video0s976532-1292/stream/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 716,
                name = "Tele Oltre",
                logoUrl = "https://i.imgur.com/PxtJAxs.png",
                streamUrl = "http://1se1.troydesign.eu/np_teleoltre/_definst_/channel1_np_teleoltre/playlist.m3u8?ext=.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 717,
                name = "Tele One",
                logoUrl = "https://i.imgur.com/9trB6mj.png",
                streamUrl = "https://648026e87a75e.streamlock.net/teleone/teleone/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 718,
                name = "Tele Pace Trento",
                logoUrl = "https://i.imgur.com/o5sQCpF.png",
                streamUrl = "https://5a1178b42cc03.streamlock.net/telepacetrento/telepacetrento/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 719,
                name = "Tele Pavia",
                logoUrl = "https://i.imgur.com/YVodo4T.png",
                streamUrl = "http://wms.shared.streamshow.it:1935/telepavia/telepavia/live.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 720,
                name = "Tele Pegaso",
                logoUrl = "https://i.imgur.com/FQkM8Vd.png",
                streamUrl = "https://flash2.xdevel.com/telepegasocanale812/telepegasocanale812/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 721,
                name = "Tele Piadena",
                logoUrl = "https://i.imgur.com/VqaPuQN.png",
                streamUrl = "https://stream3.xdevel.com/video0s975441-151/stream/playlist_dvr.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 722,
                name = "Tele Pordenone",
                logoUrl = "https://i.imgur.com/dbYwXwg.png",
                streamUrl = "https://video.wifi4all.it/telepn/telepn.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 723,
                name = "Tele Quattro Trieste",
                logoUrl = "https://i.imgur.com/MFxQxve.png",
                streamUrl = "http://wms.shared.streamshow.it/telequattro/telequattro/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 724,
                name = "Tele Radio Ercolano",
                logoUrl = "https://i.imgur.com/YPuoy8N.jpg",
                streamUrl = "https://rst.saiuzwebnetwork.it:19360/teleradioercolano-1/teleradioercolano-1.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 725,
                name = "Tele Radio Pace 1",
                logoUrl = "https://i.imgur.com/ORkkXM8.png",
                streamUrl = "https://jk3lz2bwlw79-hls-live.mariatvcdn.it/teleradiopace1/efcc8fc46cab26315ce3f5845d76008f.sdp/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 726,
                name = "Tele Radio Pace 2",
                logoUrl = "https://i.imgur.com/U4E3eEr.png",
                streamUrl = "https://zkpywrbgdbeg-hls-live.mariatvcdn.it/teleradiopace2/254c9b5c52a73a94ef0f6169cbd05dc2.sdp/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 727,
                name = "Tele Radio Pace 3",
                logoUrl = "https://i.imgur.com/pTqIxFD.png",
                streamUrl = "https://932y4273djv8-hls-live.mariatvcdn.it/teleradiopace3/d2274c22e9ee09eb2eda01ed0496f8f5.sdp/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 728,
                name = "Tele Radio Pace 4",
                logoUrl = "https://i.imgur.com/KPHHsN5.png",
                streamUrl = "https://j78dpr7nyq5r-hls-live.mariatvcdn.it/teleradiopace4/13d74f2cfe921bfbc262697203d47d8f.sdp/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 729,
                name = "Tele Radio Orte",
                logoUrl = "https://i.imgur.com/uX2uxvN.png",
                streamUrl = "https://flash2.xdevel.com/ortetv/ortetv/index.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 730,
                name = "Tele Radio Sciacca",
                logoUrl = "https://i.imgur.com/suhz5mE.png",
                streamUrl = "https://5db313b643fd8.streamlock.net/teleradiosciaccatv/teleradiosciaccatv/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 731,
                name = "Tele Sirio",
                logoUrl = "https://i.imgur.com/mDN6QX1.png",
                streamUrl = "https://www.telesirio.it/live/stream.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 732,
                name = "Tele Spazio Messina",
                logoUrl = "https://i.imgur.com/Io5w6lT.png",
                streamUrl = "https://rtm.cyberspazio.cloud:5443/LiveApp/streams/049229794390395765037801.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 733,
                name = "Tele Sud Puglia",
                logoUrl = "https://i.imgur.com/fqTLtvs.png",
                streamUrl = "https://64b16f23efbee.streamlock.net/telesud/telesud/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 734,
                name = "Tele Tricolore",
                logoUrl = "https://i.imgur.com/A2XouAd.png",
                streamUrl = "https://59d7d6f47d7fc.streamlock.net/rs2/rs2/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 735,
                name = "Tele Tutto",
                logoUrl = "https://i.imgur.com/sZxMP7g.png",
                streamUrl = "https://600f07e114306.streamlock.net/TT_TELETUTTO/smil:TT.smil/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 736,
                name = "Tele Tutto 2",
                logoUrl = "https://i.imgur.com/mxXbMaw.png",
                streamUrl = "https://600f07e114306.streamlock.net/TT2_TELETUTTO/livestream/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 737,
                name = "Tele Tutto 24",
                logoUrl = "https://i.imgur.com/weKWQgx.png",
                streamUrl = "https://600f07e114306.streamlock.net/TT24_TELETUTTO/livestream/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 738,
                name = "Tele Venezia",
                logoUrl = "https://i.imgur.com/SavGpCE.jpg",
                streamUrl = "https://59d7d6f47d7fc.streamlock.net/televenezia/televenezia/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 739,
                name = "Tele Video Agrigento (T.V.A.)",
                logoUrl = "https://i.imgur.com/AaPr63E.png",
                streamUrl = "https://59d7d6f47d7fc.streamlock.net/tva/tva/playlist.m3u8",
                category = "Italy,Tele"
            ),
            TvChannel(
                id = 740,
                name = "Tele8 Tv",
                logoUrl = "https://i.imgur.com/dM0HE6O.png",
                streamUrl = "https://57068da1deb21.streamlock.net/teletv8/teletv8/playlist.m3u8",
                category = "Italy,Tele8"
            ),
            TvChannel(
                id = 741,
                name = "TeleAmbiente",
                logoUrl = "https://i.imgur.com/jxZcQhU.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/teleambiente2024/teleambiente2024/playlist.m3u8",
                category = "Italy,TeleAmbiente"
            ),
            TvChannel(
                id = 742,
                name = "Telecampione",
                logoUrl = "https://i.imgur.com/LhjwmME.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/telecampione/telecampione/playlist.m3u8",
                category = "Italy,Telecampione"
            ),
            TvChannel(
                id = 743,
                name = "Telecittà Padova",
                logoUrl = "https://i.imgur.com/xvVgEaA.jpg",
                streamUrl = "https://tango.wifi4all.it/telecitta/telecitta.m3u8",
                category = "Italy,Telecittà"
            ),
            TvChannel(
                id = 744,
                name = "Telecity Lombardia",
                logoUrl = "https://i.imgur.com/ECvJ3ZD.jpeg",
                streamUrl = "https://64b16f23efbee.streamlock.net/telecitylombardia/telecitylombardia/playlist.m3u8",
                category = "Italy,Telecity"
            ),
            TvChannel(
                id = 745,
                name = "Telecity Piemonte",
                logoUrl = "https://i.imgur.com/CrlzHjv.jpeg",
                streamUrl = "https://64b16f23efbee.streamlock.net/telecitypiemonte/telecitypiemonte/playlist.m3u8",
                category = "Italy,Telecity"
            ),
            TvChannel(
                id = 746,
                name = "Telecity Valle D'Aosta",
                logoUrl = "https://i.imgur.com/T9EsAOX.jpeg",
                streamUrl = "https://64b16f23efbee.streamlock.net/telecityvda/telecityvda/playlist.m3u8",
                category = "Italy,Telecity"
            ),
            TvChannel(
                id = 747,
                name = "TeleCostaSmeralda",
                logoUrl = "https://i.imgur.com/QLqkbss.png",
                streamUrl = "https://7e1cc2454f2242afabe05cc0a2f483cd.msvdn.net/tcs_live/tcs/tcs/playlist.m3u8",
                category = "Italy,TeleCostaSmeralda"
            ),
            TvChannel(
                id = 748,
                name = "Telegenova",
                logoUrl = "https://i.imgur.com/D6HC0So.png",
                streamUrl = "https://64b16f23efbee.streamlock.net/telegenova/telegenova/playlist.m3u8",
                category = "Italy,Telegenova"
            ),
            TvChannel(
                id = 749,
                name = "Teleiblea",
                logoUrl = "https://i.imgur.com/n1THygZ.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/teleiblea/teleiblea/playlist.m3u8",
                category = "Italy,Teleiblea"
            ),
            TvChannel(
                id = 750,
                name = "Teleitalia 41",
                logoUrl = "https://i.imgur.com/lTuPSOn.png",
                streamUrl = "https://streaming.softwarecreation.it/teleitalia/teleitalia/playlist.m3u8",
                category = "Italy,Teleitalia"
            ),
            TvChannel(
                id = 751,
                name = "Telejato",
                logoUrl = "https://i.imgur.com/r3Dqzdj.png",
                streamUrl = "https://telejato.liberotratto.com/hls/stream.m3u8",
                category = "Italy,Telejato"
            ),
            TvChannel(
                id = 752,
                name = "Teleleonessa",
                logoUrl = "https://i.imgur.com/jq3etlV.png",
                streamUrl = "http://wms.shared.streamshow.it:1935/teleleonessa/teleleonessa/live.m3u8",
                category = "Italy,Teleleonessa"
            ),
            TvChannel(
                id = 753,
                name = "TeleMajg",
                logoUrl = "https://i.imgur.com/9tefonp.jpg",
                streamUrl = "https://59d7d6f47d7fc.streamlock.net/telemajg/telemajg/playlist.m3u8",
                category = "Italy,TeleMajg"
            ),
            TvChannel(
                id = 754,
                name = "Telenova",
                logoUrl = "https://i.imgur.com/x41IkJK.png",
                streamUrl = "https://64b16f23efbee.streamlock.net/telenova/telenova/playlist.m3u8",
                category = "Italy,Telenova"
            ),
            TvChannel(
                id = 755,
                name = "Telerama",
                logoUrl = "https://i.imgur.com/enfqUlI.jpg",
                streamUrl = "https://58d921499d3d3.streamlock.net/TeleRama/livestream/playlist.m3u8",
                category = "Italy,Telerama"
            ),
            TvChannel(
                id = 756,
                name = "TeleRegione",
                logoUrl = "https://i.imgur.com/i5WozUP.png",
                streamUrl = "https://streaming.softwarecreation.it/TR118/TR118/playlist.m3u8",
                category = "Italy,TeleRegione"
            ),
            TvChannel(
                id = 757,
                name = "TeleRegione Color",
                logoUrl = "https://i.imgur.com/vi5Nf3S.png",
                streamUrl = "https://live.antennasudwebtv.it:9443/hls/vodtele.m3u8",
                category = "Italy,TeleRegione"
            ),
            TvChannel(
                id = 758,
                name = "TeleRegione Live",
                logoUrl = "https://i.imgur.com/DBrt8L3.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/galluralive/galluralive/playlist.m3u8",
                category = "Italy,TeleRegione"
            ),
            TvChannel(
                id = 759,
                name = "TeleRent 7Gold",
                logoUrl = "https://i.imgur.com/YZadq0M.png",
                streamUrl = "https://router.xdevel.com/video0s86-21/stream/playlist.m3u8",
                category = "Italy,TeleRent"
            ),
            TvChannel(
                id = 760,
                name = "Telereporter",
                logoUrl = "https://i.imgur.com/WIo6SPc.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/telereporter/telereporter/playlist.m3u8",
                category = "Italy,Telereporter"
            ),
            TvChannel(
                id = 761,
                name = "Teleromagna",
                logoUrl = "https://i.imgur.com/4jWadI8.png",
                streamUrl = "https://livetr.teleromagna.it/teleromagna/live/playlist.m3u8",
                category = "Italy,Teleromagna"
            ),
            TvChannel(
                id = 762,
                name = "Teleromagna 24",
                logoUrl = "https://i.imgur.com/Bpml478.png",
                streamUrl = "https://livetr.teleromagna.it/mia/live/playlist.m3u8",
                category = "Italy,Teleromagna"
            ),
            TvChannel(
                id = 763,
                name = "TeleRomaDue",
                logoUrl = "https://i.imgur.com/78hA7ma.png",
                streamUrl = "https://57068da1deb21.streamlock.net/teletibur/teletibur/playlist.m3u8",
                category = "Italy,TeleRomaDue"
            ),
            TvChannel(
                id = 764,
                name = "Telesud Trapani",
                logoUrl = "https://i.imgur.com/tpZvU1P.png",
                streamUrl = "https://64b16f23efbee.streamlock.net/telesudtrapani/telesudtrapani/playlist.m3u8",
                category = "Italy,Telesud"
            ),
            TvChannel(
                id = 765,
                name = "Teleuniverso",
                logoUrl = "https://i.imgur.com/u8E9wBb.png",
                streamUrl = "https://stream.ets-sistemi.it:19360/teleuniverso/teleuniverso.m3u8",
                category = "Italy,Teleuniverso"
            ),
            TvChannel(
                id = 766,
                name = "Televallo Trapani",
                logoUrl = "https://i.imgur.com/P6zuiRH.png",
                streamUrl = "https://64b16f23efbee.streamlock.net/televallo/televallo/playlist.m3u8",
                category = "Italy,Televallo"
            ),
            TvChannel(
                id = 767,
                name = "TG Norba 24",
                logoUrl = "https://i.imgur.com/9MhrrJK.png",
                streamUrl = "https://router.xdevel.com/video0s976570-1326/stream/playlist_dvr.m3u8",
                category = "Italy,TG"
            ),
            TvChannel(
                id = 768,
                name = "TLT Molise",
                logoUrl = "https://i.imgur.com/wgwD7gh.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/tltmolise/tltmolise/playlist.m3u8",
                category = "Italy,TLT"
            ),
            TvChannel(
                id = 769,
                name = "Top Calcio 24",
                logoUrl = "https://i.imgur.com/DnVPKPE.png",
                streamUrl = "https://sportitaliaamd.akamaized.net/live/Telelombardia/hls/1CCCD0BCA2F9C979BC0632230F8E31EAEA41562B/index.m3u8",
                category = "Italy,Top"
            ),
            TvChannel(
                id = 770,
                name = "Tremedia Tv",
                logoUrl = "https://i.imgur.com/dqRV1ff.jpeg",
                streamUrl = "https://stream4.xdevel.com/video0s976062-1263/stream/playlist_dvr.m3u8",
                category = "Italy,Tremedia"
            ),
            TvChannel(
                id = 771,
                name = "Trentino Tv",
                logoUrl = "https://i.imgur.com/ROKOCR2.png",
                streamUrl = "https://5e73cf528f404.streamlock.net/TrentinoTV/livestream/playlist.m3u8",
                category = "Italy,Trentino"
            ),
            TvChannel(
                id = 772,
                name = "Tuscia Sabina 2000Tv",
                logoUrl = "https://i.imgur.com/Tq5nEAy.png",
                streamUrl = "http://ts2000tv.streaming.nextware.it:8081/ts2000tv/ts2000tv/chunks.m3u8",
                category = "Italy,Tuscia"
            ),
            TvChannel(
                id = 773,
                name = "Tv 12",
                logoUrl = "https://i.imgur.com/oxv08pJ.png",
                streamUrl = "https://59d7d6f47d7fc.streamlock.net/tv12/tv12/playlist.m3u8",
                category = "Italy,Tv"
            ),
            TvChannel(
                id = 774,
                name = "Tv Campi Flegrei",
                logoUrl = "https://i.imgur.com/9d2K1nX.png",
                streamUrl = "https://tv.radiosaiuz.com:3836/live/campiflegreilive.m3u8",
                category = "Italy,Tv"
            ),
            TvChannel(
                id = 775,
                name = "Tv Luna Napoli",
                logoUrl = "https://i.imgur.com/jxhuoyE.png",
                streamUrl = "https://diretta.arcapuglia.it:8080/live/teleluna/index.m3u8",
                category = "Italy,Tv"
            ),
            TvChannel(
                id = 776,
                name = "Tv Prato",
                logoUrl = "https://i.imgur.com/zDeVpZd.png",
                streamUrl = "https://live.mariatvcdn.com/tvprato/2db0dd5674586686a867ec52c3aa8e06.sdp/mono.m3u8",
                category = "Italy,Tv"
            ),
            TvChannel(
                id = 777,
                name = "Tv Qui Modena",
                logoUrl = "https://i.imgur.com/4bOYlfg.png",
                streamUrl = "https://59d7d6f47d7fc.streamlock.net/tvqui/tvqui/playlist.m3u8",
                category = "Italy,Tv"
            ),
            TvChannel(
                id = 778,
                name = "Tv Yes",
                logoUrl = "https://i.imgur.com/1wsO8U7.png",
                streamUrl = "https://stream1.aswifi.it/radioyes/live/index.m3u8",
                category = "Italy,Tv"
            ),
            TvChannel(
                id = 779,
                name = "Tva Vicenza",
                logoUrl = "https://i.imgur.com/FtFuPCC.png",
                streamUrl = "http://fms.tvavicenza.it:1935/live/diretta_1/playlist.m3u8",
                category = "Italy,Tva"
            ),
            TvChannel(
                id = 780,
                name = "Tvm Palermo",
                logoUrl = "https://i.imgur.com/uqOdAXB.png",
                streamUrl = "https://stream2.xdevel.com/video1s86-22/stream/playlist_dvr.m3u8",
                category = "Italy,Tvm"
            ),
            TvChannel(
                id = 781,
                name = "Tvr Xenon",
                logoUrl = "https://i.imgur.com/kLzW1Pf.jpg",
                streamUrl = "https://cdn107-ita.azotosolutions.com:8443/cdnedge4/smil:live4.smil/playlist.m3u8",
                category = "Italy,Tvr"
            ),
            TvChannel(
                id = 782,
                name = "Tvrs Tv",
                logoUrl = "https://i.imgur.com/6p7hTmY.jpg",
                streamUrl = "https://cdn8.streamshow.it/cloud-tvrs/tvrs/playlist.m3u8",
                category = "Italy,Tvrs"
            ),
            TvChannel(
                id = 783,
                name = "Umbria+ TRT",
                logoUrl = "https://i.imgur.com/CU6BBgs.png",
                streamUrl = "https://diretta.teleterni.it:8080//show/show.m3u8",
                category = "Italy,Umbria+"
            ),
            TvChannel(
                id = 784,
                name = "Umbria Tv",
                logoUrl = "https://i.imgur.com/XKCVEmK.png",
                streamUrl = "https://umbriatv.stream.rubidia.it:8083/live/umbriatv/playlist.m3u8",
                category = "Italy,Umbria"
            ),
            TvChannel(
                id = 785,
                name = "Uno Tv",
                logoUrl = "https://i.imgur.com/4PNbqqL.png",
                streamUrl = "https://stream1.aswifi.it/unotv/live/index.m3u8",
                category = "Italy,Uno"
            ),
            TvChannel(
                id = 786,
                name = "Uno4 Tv",
                logoUrl = "https://i.imgur.com/wfPPpBA.png",
                streamUrl = "https://cdn.uno4.it/index.m3u8",
                category = "Italy,Uno4"
            ),
            TvChannel(
                id = 787,
                name = "Vera Tv",
                logoUrl = "https://i.imgur.com/djMvkvN.png",
                streamUrl = "http://wms.shared.streamshow.it/veratv/veratv/playlist.m3u8",
                category = "Italy,Vera"
            ),
            TvChannel(
                id = 788,
                name = "VB33",
                logoUrl = "https://i.imgur.com/ygDuIxU.png",
                streamUrl = "https://live.ipstream.it/vb33/vb33.stream/playlist.m3u8",
                category = "Italy,VB33"
            ),
            TvChannel(
                id = 789,
                name = "Video Calabria",
                logoUrl = "https://i.imgur.com/Bc2AvIm.png",
                streamUrl = "https://a8a02dd9a49a4fc9810743615c65ab73.msvdn.net/live/S76734991/i6NeNqLYaspb/playlist.m3u8",
                category = "Italy,Video"
            ),
            TvChannel(
                id = 790,
                name = "Video Mediterraneo",
                logoUrl = "https://i.imgur.com/hJHC3uQ.png",
                streamUrl = "https://bfbe5f347ac4424faf719dda285bc39e.msvdn.net/live/S54897858/4gWO7tTzpK3N/playlist.m3u8",
                category = "Italy,Video"
            ),
            TvChannel(
                id = 791,
                name = "Video Nola",
                logoUrl = "https://i.imgur.com/M5z5UoD.jpg",
                streamUrl = "https://videonola.aswifi.it/videonolaabr/live.m3u8",
                category = "Italy,Video"
            ),
            TvChannel(
                id = 792,
                name = "Video Regione Sicilia",
                logoUrl = "https://i.imgur.com/0szwnR3.png",
                streamUrl = "https://57214cb172d84f8cb311b91513952b03.msvdn.net/live/S40896499/ZuaLjACsWIL4/playlist.m3u8",
                category = "Italy,Video"
            ),
            TvChannel(
                id = 793,
                name = "Video Star Tv Sicilia",
                logoUrl = "https://i.imgur.com/1ddJVZ7.jpg",
                streamUrl = "https://stream9.xdevel.com/video0s976556-1321/stream/playlist.m3u8",
                category = "Italy,Video"
            ),
            TvChannel(
                id = 794,
                name = "Video Touring Tv",
                logoUrl = "https://i.imgur.com/E2Feao5.jpg",
                streamUrl = "https://streamingvideo.auranex.cloud/memfs/70baacad-47c6-41a6-aee0-86530c31e080.m3u8",
                category = "Italy,Video"
            ),
            TvChannel(
                id = 795,
                name = "Videolina",
                logoUrl = "https://i.imgur.com/bnDZJwd.gif",
                streamUrl = "http://livestreaming.videolina.it/live/Videolina/playlist.m3u8",
                category = "Italy,Videolina"
            ),
            TvChannel(
                id = 796,
                name = "Videonovara",
                logoUrl = "https://i.imgur.com/NnO7E5I.png",
                streamUrl = "https://ed04.top-ix.org/avtvlive/videonovara/streaming/playlist.m3u8",
                category = "Italy,Videonovara"
            ),
            TvChannel(
                id = 797,
                name = "Videotelecarnia",
                logoUrl = "https://i.imgur.com/r4K9JHW.png",
                streamUrl = "https://rst2.saiuzwebnetwork.it:8081/vtccarnia/index.m3u8",
                category = "Italy,Videotelecarnia"
            ),
            TvChannel(
                id = 798,
                name = "Vintage Radio Tv",
                logoUrl = "https://i.imgur.com/n3LtBNT.jpg",
                streamUrl = "https://5f22d76e220e1.streamlock.net/vintageradiotv/vintageradiotv/playlist.m3u8",
                category = "Italy,Vintage"
            ),
            TvChannel(
                id = 799,
                name = "Vuemme Tv",
                logoUrl = "https://i.imgur.com/x5A0xU6.png",
                streamUrl = "https://5db313b643fd8.streamlock.net/Vuemme/Vuemme/playlist.m3u8",
                category = "Italy,Vuemme"
            ),
            TvChannel(
                id = 800,
                name = "Webcom Tv",
                logoUrl = "https://i.imgur.com/KLwj2vj.png",
                streamUrl = "https://www.webcomiptv.it/mistserver/passionelotto/index.m3u8",
                category = "Italy,Webcom"
            ),
            TvChannel(
                id = 801,
                name = "WLTV",
                logoUrl = "https://i.imgur.com/aL8jKtU.png",
                streamUrl = "https://5db313b643fd8.streamlock.net/WLTV/WLTV/playlist.m3u8",
                category = "Italy,WLTV"
            ),
            TvChannel(
                id = 802,
                name = "Yvii Tv",
                logoUrl = "https://i.imgur.com/yP5AvDo.png",
                streamUrl = "https://stream.wired-shop.com/hls/yviitv.m3u8",
                category = "Italy,Yvii"
            ),
            TvChannel(
                id = 803,
                name = "Zerouno Tv Music",
                logoUrl = "https://i.imgur.com/r74lqW8.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/zerounotvmusic/zerounotvmusic/playlist.m3u8",
                category = "Italy,Zerouno"
            ),
            TvChannel(
                id = 804,
                name = "Zerouno Tv News",
                logoUrl = "https://i.imgur.com/vxRzyjE.png",
                streamUrl = "https://5f22d76e220e1.streamlock.net/01news/01news/playlist.m3u8",
                category = "Italy,Zerouno"
            ),
            TvChannel(
                id = 805,
                name = "ショップチャンネル",
                logoUrl = "https://i.imgur.com/CCMAF7W.png",
                streamUrl = "https://stream3.shopch.jp/HLS/master.m3u8",
                category = "日本"
            ),
            TvChannel(
                id = 806,
                name = "QVC",
                logoUrl = "https://i.imgur.com/FznYA39.png",
                streamUrl = "https://cdn-live1.qvc.jp/iPhone/1501/1501.m3u8",
                category = "日本"
            ),
            TvChannel(
                id = 807,
                name = "NHK WORLD JAPAN",
                logoUrl = "https://i.imgur.com/Mhw1Ihk.png",
                streamUrl = "https://master.nhkworld.jp/nhkworld-tv/playlist/live.m3u8",
                category = "日本"
            ),
            TvChannel(
                id = 808,
                name = "ウェザーニュースLiVE",
                logoUrl = "https://channel.rakuten.co.jp/service/img/logo/chlogo-with-number/106_whethernews.png",
                streamUrl = "https://rch01e-alive-hls.akamaized.net/38fb45b25cdb05a1/out/v1/4e907bfabc684a1dae10df8431a84d21/index.m3u8",
                category = "日本"
            ),
            TvChannel(
                id = 809,
                name = "TOKYO MX チャンネル",
                logoUrl = "https://channel.rakuten.co.jp/service/img/logo/chlogo-with-number/108_mx.png",
                streamUrl = "https://cdn-uw2-prod.tsv2.amagi.tv/linear/amg01287-rakutentvjapan-tokyomx-cmaf-rakutenjp/playlist.m3u8",
                category = "日本"
            ),
            TvChannel(
                id = 810,
                name = "EBS 1 Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e2/EBS_1TV_Logo.svg/512px-EBS_1TV_Logo.svg.png",
                streamUrl = "https://ebsonair.ebs.co.kr/ebs1familypc/familypc1m/playlist.m3u8",
                category = "Korea,EBS"
            ),
            TvChannel(
                id = 811,
                name = "EBS 2 Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/db/EBS_2TV_Logo.svg/512px-EBS_2TV_Logo.svg.png",
                streamUrl = "https://ebsonair.ebs.co.kr/ebs2familypc/familypc1m/playlist.m3u8",
                category = "Korea,EBS"
            ),
            TvChannel(
                id = 812,
                name = "MBC TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/63/Munhwa_Broadcasting_Company.svg/512px-Munhwa_Broadcasting_Company.svg.png",
                streamUrl = "http://123.254.72.24:1935/tvlive/livestream2/playlist.m3u8",
                category = "Korea,MBC"
            ),
            TvChannel(
                id = 813,
                name = "SBS TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/90/SBS_Korea_Logo_%28Word_Only%29.svg/512px-SBS_Korea_Logo_%28Word_Only%29.svg.png",
                streamUrl = "https://allanf181.github.io/adaptive-streams/streams/kr/SBSTV.m3u8",
                category = "Korea,SBS"
            ),
            TvChannel(
                id = 814,
                name = "KNN TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/KNN_logo.svg/512px-KNN_logo.svg.png",
                streamUrl = "http://211.220.195.200:1935/live/mp4:KnnTV.sdp/playlist.m3u8",
                category = "Korea,KNN"
            ),
            TvChannel(
                id = 815,
                name = "KBC TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/32/KBC_Gwangju_Broadcasting_logo.svg/512px-KBC_Gwangju_Broadcasting_logo.svg.png",
                streamUrl = "http://119.200.131.11:1935/KBCTV/tv/playlist.m3u8",
                category = "Korea,KBC"
            ),
            TvChannel(
                id = 816,
                name = "TJB TV",
                logoUrl = "https://i.imgur.com/q9Nx801.png",
                streamUrl = "http://1.245.74.5:1935/live/tv/.m3u8",
                category = "Korea,TJB"
            ),
            TvChannel(
                id = 817,
                name = "UBC TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/c/c3/Ubc_logo2.svg/512px-Ubc_logo2.svg.png",
                streamUrl = "http://59.23.231.102:1935/live/UBCstream/playlist.m3u8",
                category = "Korea,UBC"
            ),
            TvChannel(
                id = 818,
                name = "JTV TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Jtv_logo.svg/512px-Jtv_logo.svg.png",
                streamUrl = "https://61ff3340258d2.streamlock.net/jtv_live/myStream/playlist.m3u8",
                category = "Korea,JTV"
            ),
            TvChannel(
                id = 819,
                name = "CJB TV",
                logoUrl = "https://i.imgur.com/MvxdZhX.png",
                streamUrl = "http://1.222.207.80:1935/live/cjbtv/playlist.m3u8",
                category = "Korea,CJB"
            ),
            TvChannel(
                id = 820,
                name = "JIBS TV",
                logoUrl = "https://i.imgur.com/RVWpBoz.png",
                streamUrl = "http://123.140.197.22/stream/1/play.m3u8",
                category = "Korea,JIBS"
            ),
            TvChannel(
                id = 821,
                name = "OBS TV",
                logoUrl = "https://i.imgur.com/oWB3ApR.png",
                streamUrl = "https://allanf181.github.io/adaptive-streams/streams/kr/OBSGyeonginTV.m3u8",
                category = "Korea,OBS"
            ),
            TvChannel(
                id = 822,
                name = "Arirang",
                logoUrl = "https://i.imgur.com/RuHZ6Dx.png",
                streamUrl = "http://amdlive.ctnd.com.edgesuite.net/arirang_1ch/smil:arirang_1ch.smil/playlist.m3u8",
                category = "Korea,Arirang"
            ),
            TvChannel(
                id = 823,
                name = "EBS Kids Ⓢ",
                logoUrl = "https://i.imgur.com/62oo8Bx.png",
                streamUrl = "https://ebsonair.ebs.co.kr/ebsufamilypc/familypc1m/playlist.m3u8",
                category = "Korea,EBS"
            ),
            TvChannel(
                id = 824,
                name = "EBS Plus 1 Ⓢ",
                logoUrl = "https://i.imgur.com/ImUHRG2.png",
                streamUrl = "https://ebsonair.ebs.co.kr/plus1familypc/familypc1m/playlist.m3u8",
                category = "Korea,EBS"
            ),
            TvChannel(
                id = 825,
                name = "EBS Plus 2 Ⓢ",
                logoUrl = "https://i.imgur.com/mgFRZFq.png",
                streamUrl = "https://ebsonair.ebs.co.kr/plus2familypc/familypc1m/playlist.m3u8",
                category = "Korea,EBS"
            ),
            TvChannel(
                id = 826,
                name = "EBS English Ⓢ",
                logoUrl = "https://i.imgur.com/qceaIf7.png",
                streamUrl = "https://ebsonair.ebs.co.kr/plus3familypc/familypc1m/playlist.m3u8",
                category = "Korea,EBS"
            ),
            TvChannel(
                id = 827,
                name = "RTK 1",
                logoUrl = "https://i.imgur.com/KTcWcO6.png",
                streamUrl = "https://ub1doy938d.gjirafa.net/live/Gfsqdsr7FewrYClU3ACEGZvCHktt2wse/zykxzq.m3u8",
                category = "Kosovo,RTK"
            ),
            TvChannel(
                id = 828,
                name = "RTK 2",
                logoUrl = "https://i.imgur.com/g6k6xyO.png",
                streamUrl = "https://ub1doy938d.gjirafa.net/live/Gfsqdsr7FewrYClU3ACEGZvCHktt2wse/zykxz0.m3u8",
                category = "Kosovo,RTK"
            ),
            TvChannel(
                id = 829,
                name = "RTK 3",
                logoUrl = "https://i.imgur.com/Ut9VcT3.png",
                streamUrl = "https://ub1doy938d.gjirafa.net/live/Gfsqdsr7FewrYClU3ACEGZvCHktt2wse/zykxzk.m3u8",
                category = "Kosovo,RTK"
            ),
            TvChannel(
                id = 830,
                name = "RTK 4",
                logoUrl = "https://i.imgur.com/Urm4XDR.png",
                streamUrl = "https://ub1doy938d.gjirafa.net/live/Gfsqdsr7FewrYClU3ACEGZvCHktt2wse/zykxgt.m3u8",
                category = "Kosovo,RTK"
            ),
            TvChannel(
                id = 831,
                name = "ReTV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/lv/thumb/d/db/ReTV_Logo_2022.svg/320px-ReTV_Logo_2022.svg.png",
                streamUrl = "https://retv2132.bstrm.net/slive/_definst_/retv_retv_channel_5k7_42787_default_891_hls.smil/playlist.m3u8",
                category = "Latvia,ReTV"
            ),
            TvChannel(
                id = 832,
                name = "TV Jūrmala",
                logoUrl = "https://i.imgur.com/tQHkHD0.png",
                streamUrl = "https://air.star.lv/TV_Jurmala_multistream/index.m3u8",
                category = "Latvia,TV"
            ),
            TvChannel(
                id = 833,
                name = "Vidusdaugavas Televīzija",
                logoUrl = "https://i.imgur.com/L5U3PQR.png",
                streamUrl = "https://straume.vdtv.lv/vdtv2/index.m3u8",
                category = "Latvia,Vidusdaugavas"
            ),
            TvChannel(
                id = 834,
                name = "LTV1",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4a/LTV1_%282022%29.svg/768px-LTV1_%282022%29.svg.png",
                streamUrl = "https://ltvlive3167.bstrm.net/slive/_definst_/ltvlive_dzsv1_8wg_43518_default_1710_hls.smil/playlist.m3u8",
                category = "Latvia,LTV1"
            ),
            TvChannel(
                id = 835,
                name = "LTV7 Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f2/LTV7_Logo_2021.svg/768px-LTV7_Logo_2021.svg.png",
                streamUrl = "https://ltvlive3167.bstrm.net/slive/_definst_/ltvlive_event_3_pg2_44004_default_2306_hls.smil/playlist.m3u8",
                category = "Latvia,LTV7"
            ),
            TvChannel(
                id = 836,
                name = "LRT TV",
                logoUrl = "https://i.imgur.com/FL2ZuGC.png",
                streamUrl = "https://stream-syncwords.lrt.lt/out/v1/channel-group-lrt-portal-prod-01/channel-lrt-portal-prod-01/endpoint-lrt-portal-prod-01/index.m3u8",
                category = "Lithuania,LRT"
            ),
            TvChannel(
                id = 837,
                name = "LRT Lituanica",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d2/LRT_Lituanica_Logo_2022.svg/640px-LRT_Lituanica_Logo_2022.svg.png",
                streamUrl = "https://stream-live.lrt.lt/lituanica/master.m3u8",
                category = "Lithuania,LRT"
            ),
            TvChannel(
                id = 838,
                name = "Lietuvos Rytas TV",
                logoUrl = "https://i.imgur.com/5wpxVI0.png",
                streamUrl = "https://live.lietuvosryto.tv/live/hls/eteris.m3u8",
                category = "Lithuania,Lietuvos"
            ),
            TvChannel(
                id = 839,
                name = "Delfi TV",
                logoUrl = "https://i.imgur.com/IFoHP5M.png",
                streamUrl = "https://s1.dcdn.lt/live/televizija/playlist.m3u8",
                category = "Lithuania,Delfi"
            ),
            TvChannel(
                id = 840,
                name = "RTL Télé Lëtzebuerg",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c0/RTL_Luxembourg_2023.svg/640px-RTL_Luxembourg_2023.svg.png",
                streamUrl = "https://live-edge.rtl.lu/channel1/smil:channel1/playlist.m3u8",
                category = "Luxembourg,RTL"
            ),
            TvChannel(
                id = 841,
                name = "RTL Zwee",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/RTL_Zwee_2023.svg/1024px-RTL_Zwee_2023.svg.png",
                streamUrl = "https://live-edge.rtl.lu/channel2/smil:channel2/playlist.m3u8",
                category = "Luxembourg,RTL"
            ),
            TvChannel(
                id = 842,
                name = "Chamber TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Logo_of_the_Chamber_of_Deputies_of_Luxembourg.svg/2560px-Logo_of_the_Chamber_of_Deputies_of_Luxembourg.svg.png",
                streamUrl = "https://media02.webtvlive.eu/chd-edge/_definst_/smil:chamber_tv_hd.smil/playlist.m3u8",
                category = "Luxembourg,Chamber"
            ),
            TvChannel(
                id = 843,
                name = "TDM Ou Mun",
                logoUrl = "https://upload.wikimedia.org/wikipedia/zh/9/9b/TDM_Ou_Mun.png",
                streamUrl = "https://live4.tdm.com.mo/ch1/ch1.live/playlist.m3u8",
                category = "Macau,TDM"
            ),
            TvChannel(
                id = 844,
                name = "Canal Macau",
                logoUrl = "https://i.imgur.com/oFPUZ97.png",
                streamUrl = "https://live4.tdm.com.mo/ch2/ch2.live/playlist.m3u8",
                category = "Macau,Canal"
            ),
            TvChannel(
                id = 845,
                name = "TDM Sport",
                logoUrl = "https://upload.wikimedia.org/wikipedia/zh/9/9b/TDM_Ou_Mun.png",
                streamUrl = "https://live4.tdm.com.mo/ch4/sport_ch4.live/playlist.m3u8",
                category = "Macau,TDM"
            ),
            TvChannel(
                id = 846,
                name = "TDM Information",
                logoUrl = "https://upload.wikimedia.org/wikipedia/zh/9/9b/TDM_Ou_Mun.png",
                streamUrl = "https://live4.tdm.com.mo/ch5/info_ch5.live/playlist.m3u8",
                category = "Macau,TDM"
            ),
            TvChannel(
                id = 847,
                name = "TDM Entertainment",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/6/6c/TDM_Entertainment.png",
                streamUrl = "https://live4.tdm.com.mo/ch6/hd_ch6.live/playlist.m3u8",
                category = "Macau,TDM"
            ),
            TvChannel(
                id = 848,
                name = "TDM Ou Mun-Macau",
                logoUrl = "https://upload.wikimedia.org/wikipedia/zh/8/87/TDM_Ou_Mun_Macau_logo.png",
                streamUrl = "https://live4.tdm.com.mo/ch3/ch3.live/playlist.m3u8",
                category = "Macau,TDM"
            ),
            TvChannel(
                id = 849,
                name = "ONE TV",
                logoUrl = "https://i.imgur.com/Ym1L7No.png",
                streamUrl = "https://2-fss-2.streamhoster.com/pl_124/201830-1293592-1/playlist.m3u8",
                category = "Malta,ONE"
            ),
            TvChannel(
                id = 850,
                name = "Smash TV",
                logoUrl = "https://i.imgur.com/ZKF0fG3.png",
                streamUrl = "http://a3.smashmalta.com/hls/smash/smash.m3u8",
                category = "Malta,Smash"
            ),
            TvChannel(
                id = 851,
                name = "Alcarria TV",
                logoUrl = "https://i.imgur.com/zNSuxVZ.jpg",
                streamUrl = "http://cls.alcarria.tv/live/alcarriatv-livestream.m3u8",
                category = "Mexico,Alcarria"
            ),
            TvChannel(
                id = 852,
                name = "Hipodromo de las Americas",
                logoUrl = "https://i.imgur.com/wc8MlGw.png",
                streamUrl = "http://wms30.tecnoxia.com/soelvi/abr_soelvi/playlist.m3u8",
                category = "Mexico,Hipodromo"
            ),
            TvChannel(
                id = 853,
                name = "MVM NoticiasⓈ",
                logoUrl = "https://i.imgur.com/dhLXN9n.png",
                streamUrl = "http://dcunilive21-lh.akamaihd.net/i/dclive_1@59479/index_1_av-p.m3u8",
                category = "Mexico,MVM"
            ),
            TvChannel(
                id = 854,
                name = "RCG 3 Saltillo",
                logoUrl = "https://i.imgur.com/NefH5qZ.png",
                streamUrl = "http://wowzacontrol.com:1936/stream56/stream56/playlist.m3u8",
                category = "Mexico,RCG"
            ),
            TvChannel(
                id = 855,
                name = "TeleFormula",
                logoUrl = "https://i.imgur.com/jR6taXt.png",
                streamUrl = "https://wms60.tecnoxia.com/radiof/abr_radioftele/playlist.m3u8",
                category = "Mexico,TeleFormula"
            ),
            TvChannel(
                id = 856,
                name = "NRT 4 Monclova",
                logoUrl = "https://i.imgur.com/IudKE0n.png",
                streamUrl = "https://59e88b197fb16.streamlock.net:4443/live/canal4/playlist.m3u8",
                category = "Mexico,NRT"
            ),
            TvChannel(
                id = 857,
                name = "Las Estrellas",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/4/41/Las_Estrellas.svg",
                streamUrl = "https://linear-416.frequency.stream/416/hls/master/playlist.m3u8",
                category = "Mexico,Las"
            ),
            TvChannel(
                id = 858,
                name = "Publika TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/b/b7/Publika_logo_%282017%29.png",
                streamUrl = "https://livebeta.publika.md/LIVE/P/6810.m3u8",
                category = "Moldova,Publika"
            ),
            TvChannel(
                id = 859,
                name = "Vocea Basarabiei",
                logoUrl = "https://i.imgur.com/irP8QLs.png",
                streamUrl = "https://storage.voceabasarabiei.md/hls/vocea_basarabiei.m3u8",
                category = "Moldova,Vocea"
            ),
            TvChannel(
                id = 860,
                name = "Canal 2 Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/ro/7/7f/Logo_Canal_2.png",
                streamUrl = "https://livebeta.publika.md/LIVE/2/index.m3u8",
                category = "Moldova,Canal"
            ),
            TvChannel(
                id = 861,
                name = "Canal 3 Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d5/Canal_3.svg/640px-Canal_3.svg.png",
                streamUrl = "https://livebeta.publika.md/LIVE/3/index.m3u8",
                category = "Moldova,Canal"
            ),
            TvChannel(
                id = 862,
                name = "Prime Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/7/70/Prime.png",
                streamUrl = "https://livebeta.publika.md/LIVE/1/600.m3u8",
                category = "Moldova,Prime"
            ),
            TvChannel(
                id = 863,
                name = "TVR Moldova Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e4/TVR_Moldova_Logo_2022.svg/640px-TVR_Moldova_Logo_2022.svg.png",
                streamUrl = "https://mn-nl.mncdn.com/tvrmoldova_new/smil:tvrmoldova_new.smil/chunklist_b6096000.m3u8",
                category = "Moldova,TVR"
            ),
            TvChannel(
                id = 864,
                name = "Sor TV",
                logoUrl = "https://i.imgur.com/BcfZgD8.png",
                streamUrl = "http://188.237.212.16:8888/live/cameraFeed.m3u8",
                category = "Moldova,Sor"
            ),
            TvChannel(
                id = 865,
                name = "Bălți TV",
                logoUrl = "https://i.imgur.com/S1vEqZp.png",
                streamUrl = "https://hls.btv.md/hls/live2.m3u8",
                category = "Moldova,Bălți"
            ),
            TvChannel(
                id = 866,
                name = "TV Monaco",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/TVMonaco_2023.svg/320px-TVMonaco_2023.svg.png",
                streamUrl = "https://production-fast-mcrtv.content.okast.tv/channels/2116dc08-1959-465d-857f-3619daefb66b/b702b2b9-aebd-436c-be69-2118f56f3d86/2027/media.m3u8",
                category = "Monaco,TV"
            ),
            TvChannel(
                id = 867,
                name = "MonacoInfo",
                logoUrl = "https://www.lyngsat.com/logo/tv/mm/monaco_info.png",
                streamUrl = "https://webtvmonacoinfo.mc/live/prod_720/index.m3u8",
                category = "Monaco,MonacoInfo"
            ),
            TvChannel(
                id = 868,
                name = "TVCG 1",
                logoUrl = "https://i.imgur.com/QORHrXu.png",
                streamUrl = "http://cdn3.bcdn.rs:1935/cg1/smil:cg1.smil/playlist.m3u8",
                category = "Montenegro,TVCG"
            ),
            TvChannel(
                id = 869,
                name = "TVCG 2",
                logoUrl = "https://i.imgur.com/WECmUKH.png",
                streamUrl = "http://cdn3.bcdn.rs:1935/cg2/smil:cg2.smil/playlist.m3u8",
                category = "Montenegro,TVCG"
            ),
            TvChannel(
                id = 870,
                name = "TVCG 3",
                logoUrl = "https://i.imgur.com/XC7zVog.png",
                streamUrl = "https://parlament.rtcg.me:1936/pr/smil:parlament.smil/playlist.m3u8",
                category = "Montenegro,TVCG"
            ),
            TvChannel(
                id = 871,
                name = "TVCG MNE",
                logoUrl = "https://i.imgur.com/pf8VEwf.png",
                streamUrl = "http://cdn3.bcdn.rs:1935/cgsat/smil:cgsat.smil/playlist.m3u8",
                category = "Montenegro,TVCG"
            ),
            TvChannel(
                id = 872,
                name = "NPO 1 Ⓖ",
                logoUrl = "https://i.imgur.com/pUBy4Pb.png",
                streamUrl = "http://resolver.streaming.api.nos.nl/livestream?url=/live/npo/tvlive/npo1/npo1.isml/.m3u8",
                category = "Netherlands,NPO"
            ),
            TvChannel(
                id = 873,
                name = "NPO 2 Ⓖ",
                logoUrl = "https://i.imgur.com/Vl2G1H3.png",
                streamUrl = "http://resolver.streaming.api.nos.nl/livestream?url=/live/npo/tvlive/npo2/npo2.isml/.m3u8",
                category = "Netherlands,NPO"
            ),
            TvChannel(
                id = 874,
                name = "NPO 3 Ⓖ",
                logoUrl = "https://i.imgur.com/dVB4Pqc.png",
                streamUrl = "http://resolver.streaming.api.nos.nl/livestream?url=/live/npo/tvlive/npo3/npo3.isml/.m3u8",
                category = "Netherlands,NPO"
            ),
            TvChannel(
                id = 875,
                name = "Omrop Fryslân",
                logoUrl = "https://i.imgur.com/0VKJLAK.png",
                streamUrl = "https://d3pvma9xb2775h.cloudfront.net/live/omropfryslan/f8f68bd5/playlist.m3u8",
                category = "Netherlands,Omrop"
            ),
            TvChannel(
                id = 876,
                name = "RTV Noord",
                logoUrl = "https://i.imgur.com/pO5Mexa.png",
                streamUrl = "https://media.rtvnoord.nl/live/rtvnoord/tv/playlist.m3u8",
                category = "Netherlands,RTV"
            ),
            TvChannel(
                id = 877,
                name = "RTV Drenthe",
                logoUrl = "https://i.imgur.com/GaGqM4z.png",
                streamUrl = "https://cdn.rtvdrenthe.nl/live/rtvdrenthe/tv/1080p/prog_index.m3u8",
                category = "Netherlands,RTV"
            ),
            TvChannel(
                id = 878,
                name = "RTV Oost",
                logoUrl = "https://i.imgur.com/8ropV29.png",
                streamUrl = "https://d34cg2bnc08ruf.cloudfront.net/live/rtvoost/tv/index.m3u8",
                category = "Netherlands,RTV"
            ),
            TvChannel(
                id = 879,
                name = "Omroep Gelderland",
                logoUrl = "https://i.imgur.com/TPlyvUQ.png",
                streamUrl = "https://d2od87akyl46nm.cloudfront.net/live/omroepgelderland/tvgelderland-hls/index.m3u8",
                category = "Netherlands,Omroep"
            ),
            TvChannel(
                id = 880,
                name = "RTV Utrecht",
                logoUrl = "https://i.imgur.com/c0I24N6.png",
                streamUrl = "http://media.rtvutrecht.nl/live/rtvutrecht/rtvutrecht/index.m3u8",
                category = "Netherlands,RTV"
            ),
            TvChannel(
                id = 881,
                name = "Omroep Flevoland Ⓢ",
                logoUrl = "https://i.imgur.com/d1CmTcI.png",
                streamUrl = "https://d5ms27yy6exnf.cloudfront.net/live/omroepflevoland/tv/index.m3u8",
                category = "Netherlands,Omroep"
            ),
            TvChannel(
                id = 882,
                name = "NH Nieuws",
                logoUrl = "https://i.imgur.com/SQPVOwn.png",
                streamUrl = "https://rrr.sz.xlcdn.com/?account=nhnieuws&file=live&type=live&service=wowza&protocol=https&output=playlist.m3u8",
                category = "Netherlands,NH"
            ),
            TvChannel(
                id = 883,
                name = "RTV Rijnmond",
                logoUrl = "https://i.imgur.com/TNhUVEm.png",
                streamUrl = "https://dcur8bjarl5c2.cloudfront.net/live/rijnmond/tv/index.m3u8",
                category = "Netherlands,RTV"
            ),
            TvChannel(
                id = 884,
                name = "Omroep West",
                logoUrl = "https://i.imgur.com/aax1HPH.png",
                streamUrl = "https://d1axml5ozykh3g.cloudfront.net/live/omroepwest/tv/index.m3u8",
                category = "Netherlands,Omroep"
            ),
            TvChannel(
                id = 885,
                name = "Omroep Zeeland",
                logoUrl = "https://i.imgur.com/8aLDyUI.png",
                streamUrl = "http://d3isaxd2t6q8zm.cloudfront.net/live/omroepzeeland/tv/index.m3u8",
                category = "Netherlands,Omroep"
            ),
            TvChannel(
                id = 886,
                name = "Omroep Brabant",
                logoUrl = "https://i.imgur.com/Jv7IjHJ.png",
                streamUrl = "http://d3slqp9xhts6qb.cloudfront.net/live/omroepbrabant/tv/index.m3u8",
                category = "Netherlands,Omroep"
            ),
            TvChannel(
                id = 887,
                name = "L1 Ⓢ",
                logoUrl = "https://i.imgur.com/Jyhn1iP.png",
                streamUrl = "http://d34pj260kw1xmk.cloudfront.net/live/l1/tv/index.m3u8",
                category = "Netherlands,L1"
            ),
            TvChannel(
                id = 888,
                name = "Телевизија Здравкин",
                logoUrl = "https://i.imgur.com/kSmcAER.png",
                streamUrl = "http://zdravkin.hugo.mk:1935/live/zdravkin/playlist.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 889,
                name = "Орбис",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/6/6f/Orbis-logo.png",
                streamUrl = "http://tvorbis.hugo.mk:1935/live/orbistv/index.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 890,
                name = "M»Net",
                logoUrl = "https://i.imgur.com/JWHcGMX.png",
                streamUrl = "http://ares.mnet.mk/hls/mnet.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 891,
                name = "Канал 8",
                logoUrl = "https://i.imgur.com/5skC7be.png",
                streamUrl = "http://kanal8.hugo.mk:1935/live/kanal8/index.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 892,
                name = "ТВ СВЕТ",
                logoUrl = "https://i.imgur.com/R79xT60.png",
                streamUrl = "http://tvsvet.hugo.mk:1936/live/tvsvet/stream/3.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 893,
                name = "M»Net Sport",
                logoUrl = "https://i.imgur.com/q3DV2gP.png",
                streamUrl = "http://ares.mnet.mk/hls/mnet-sport.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 894,
                name = "M»Net Info",
                logoUrl = "https://i.imgur.com/O26HEyC.png",
                streamUrl = "http://ares.mnet.mk/hls/mnet-info.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 895,
                name = "TV 24 Ⓖ",
                logoUrl = "https://i.imgur.com/MFKeNZx.png",
                streamUrl = "https://hls.telekabel.com.mk:1936/live/11/playlist.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 896,
                name = "ТВ НОВА 12",
                logoUrl = "https://i.imgur.com/Qo3Hj3t.png",
                streamUrl = "http://151.236.247.171:8080/nova/index.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 897,
                name = "Вардар тв",
                logoUrl = "https://i.imgur.com/c2JHg9R.png",
                streamUrl = "https://streaming.iptv.mk/fcvardar/index.fmp4.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 898,
                name = "ЗОНА М1 ТВ",
                logoUrl = "https://i.imgur.com/yTR7A0d.png",
                streamUrl = "https://zonam1.ddns.net/hls/stream1.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 899,
                name = "Наша ТВ",
                logoUrl = "https://i.imgur.com/EOLrXvB.png",
                streamUrl = "https://stream.nasatv.com.mk/hls/nasatv_live.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 900,
                name = "Cool TV",
                logoUrl = "https://i.imgur.com/2tFrjUz.png",
                streamUrl = "https://stream.nasatv.com.mk/cooltv/hls/cooltv_live.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 901,
                name = "Folk TV",
                logoUrl = "https://i.imgur.com/4b9aZ9P.png",
                streamUrl = "https://stream.nasatv.com.mk/folktv/hls/folktv_live.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 902,
                name = "Jazz TV",
                logoUrl = "https://i.imgur.com/4U6Ju5G.png",
                streamUrl = "https://stream.nasatv.com.mk/jazztv/hls/jazztv_live.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 903,
                name = "Love TV",
                logoUrl = "https://i.imgur.com/B8iaejQ.png",
                streamUrl = "https://stream.nasatv.com.mk/lovetv/hls/lovetv_live.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 904,
                name = "Rock TV",
                logoUrl = "https://i.imgur.com/Y9miDQo.png",
                streamUrl = "https://stream.nasatv.com.mk/rocktv/hls/rocktv_live.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 905,
                name = "Стар Фолк",
                logoUrl = "https://i.imgur.com/7RstQYI.png",
                streamUrl = "https://live.muzickatv.mk/live/StarMusic.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 906,
                name = "Sky Folk",
                logoUrl = "https://i.imgur.com/xRw4Hmu.png",
                streamUrl = "https://skyfolk.mk/live.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 907,
                name = "Хуго 2",
                logoUrl = "https://i.imgur.com/yb3xjOZ.png",
                streamUrl = "http://fta.hugo.mk:1935/live/tvhugo/stream/2.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 908,
                name = "Folk Club TV",
                logoUrl = "https://i.imgur.com/vkGFSl8.png",
                streamUrl = "http://tv1.intv.mk:1935/live2/folkklub/index.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 909,
                name = "INTV",
                logoUrl = "https://i.imgur.com/K7BSjqY.png",
                streamUrl = "http://tv1.intv.mk:1935/live/intv/index.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 910,
                name = "Macedonian Documentary Channel",
                logoUrl = "https://i.imgur.com/uYyG2oA.png",
                streamUrl = "https://giganet.mk/hls/macdoc.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 911,
                name = "Вистел",
                logoUrl = "https://i.imgur.com/MbM0E6L.png",
                streamUrl = "https://live.vtv.mk/live/vtv/chunks.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 912,
                name = "Стар Плус",
                logoUrl = "https://i.imgur.com/01yz22a.png",
                streamUrl = "https://live.muzickatv.mk/live/StarMusic2.m3u8",
                category = "North"
            ),
            TvChannel(
                id = 913,
                name = "NRK1 Ⓖ",
                logoUrl = "https://i.imgur.com/9tj8ds7.png",
                streamUrl = "https://nrk-nrk1.akamaized.net/21/0/hls/nrk_1/playlist.m3u8",
                category = "Norway,NRK1"
            ),
            TvChannel(
                id = 914,
                name = "NRK2 Ⓖ",
                logoUrl = "https://i.imgur.com/SiAdoK9.png",
                streamUrl = "https://nrk-nrk2.akamaized.net/22/0/hls/nrk_2/playlist.m3u8",
                category = "Norway,NRK2"
            ),
            TvChannel(
                id = 915,
                name = "NRK3 Ⓖ",
                logoUrl = "https://i.imgur.com/TNhV2I7.png",
                streamUrl = "https://nrk-nrk3.akamaized.net/23/0/hls/nrk_3/playlist.m3u8",
                category = "Norway,NRK3"
            ),
            TvChannel(
                id = 916,
                name = "NRK Super Ⓖ",
                logoUrl = "https://i.imgur.com/xIATe2T.png",
                streamUrl = "https://nrk-nrksuper.akamaized.net/23/0/hls/nrk_super/playlist.m3u8",
                category = "Norway,NRK"
            ),
            TvChannel(
                id = 917,
                name = "TV 2 Sport 1",
                logoUrl = "https://i.imgur.com/asKHqNZ.png",
                streamUrl = "https://ws31-hls-live.akamaized.net/out/u/1416253.m3u8",
                category = "Norway,TV"
            ),
            TvChannel(
                id = 918,
                name = "TV 2 Nyheter",
                logoUrl = "https://i.imgur.com/kkKoY6s.png",
                streamUrl = "https://ws15-hls-live.akamaized.net/out/u/1153546.m3u8",
                category = "Norway,TV"
            ),
            TvChannel(
                id = 919,
                name = "Frikanalen",
                logoUrl = "https://i.imgur.com/rY3Owxl.png",
                streamUrl = "https://frikanalen.no/stream/index.m3u8",
                category = "Norway,Frikanalen"
            ),
            TvChannel(
                id = 920,
                name = "Kanal 10 Norge",
                logoUrl = "https://i.imgur.com/2fOcZfK.png",
                streamUrl = "https://player-api.new.livestream.com/accounts/29308686/events/10787545/broadcasts/235454817.secure.m3u8",
                category = "Norway,Kanal"
            ),
            TvChannel(
                id = 921,
                name = "Unicanal",
                logoUrl = "https://i.imgur.com/brlepuX.png",
                streamUrl = "http://45.55.127.106/live/unicanal.m3u8",
                category = "Paraguay,Unicanal"
            ),
            TvChannel(
                id = 922,
                name = "Trece",
                logoUrl = "https://i.imgur.com/9kcYqk2.png",
                streamUrl = "https://stream.rpc.com.py/live/trece_src.m3u8",
                category = "Paraguay,Trece"
            ),
            TvChannel(
                id = 923,
                name = "ABC TV",
                logoUrl = "https://i.imgur.com/tBdgllD.png",
                streamUrl = "https://d2e809bgs49c6y.cloudfront.net/live/d87c2b7b-9ecf-4e6e-b63b-b32772bd7851/live.isml/d87c2b7b-9ecf-4e6e-b63b-b32772bd7851.m3u8",
                category = "Paraguay,ABC"
            ),
            TvChannel(
                id = 924,
                name = "Panamericana TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/2/26/Panamericana_tv_2009.png",
                streamUrl = "https://cdnhd.iblups.com/hls/ptv2.m3u8",
                category = "Peru,Panamericana"
            ),
            TvChannel(
                id = 925,
                name = "ATV+ Noticias",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/f/f4/Atv_noticias_logo.png",
                streamUrl = "https://dysmuyxh5vstv.cloudfront.net/hls/atv2.m3u8",
                category = "Peru,ATV+"
            ),
            TvChannel(
                id = 926,
                name = "Karibeña TV",
                logoUrl = "https://i.pinimg.com/280x280_RS/11/85/b6/1185b667fe3f80d7072359d7ce7ce52d.jpg",
                streamUrl = "https://cu.onliv3.com/livevd/user1.m3u8",
                category = "Peru,Karibeña"
            ),
            TvChannel(
                id = 927,
                name = "Top Latino TV",
                logoUrl = "https://static.mytuner.mobi/media/tvos_radios/fTmfsKeREm.png",
                streamUrl = "https://5cefcbf58ba2e.streamlock.net:543/tltvweb/latintv.stream/playlist.m3u8",
                category = "Peru,Top"
            ),
            TvChannel(
                id = 928,
                name = "TVP2",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/27/TVP2_logo.svg/640px-TVP2_logo.svg.png",
                streamUrl = "https://strims.top/tv/tvp2.m3u8",
                category = "Poland,TVP2"
            ),
            TvChannel(
                id = 929,
                name = "4fun.tv Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/4fun.tv_Logo_%282017%29.svg/640px-4fun.tv_Logo_%282017%29.svg.png",
                streamUrl = "https://stream.4fun.tv:8888/hls/4f_high/index.m3u8",
                category = "Poland,4fun.tv"
            ),
            TvChannel(
                id = 930,
                name = "TV Republika",
                logoUrl = "https://i.imgur.com/ljpK6dZ.png",
                streamUrl = "https://redir.cache.orange.pl/jupiter/o1-cl7/ssl/live/tvrepublika/live.m3u8",
                category = "Poland,TV"
            ),
            TvChannel(
                id = 931,
                name = "RTP1",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/RTP1_-_Logo_2016.svg/640px-RTP1_-_Logo_2016.svg.png",
                streamUrl = "https://streaming-live.rtp.pt/liverepeater/smil:rtp1HD.smil/playlist.m3u8",
                category = "Portugal,RTP1"
            ),
            TvChannel(
                id = 932,
                name = "RTP2 Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/4/4d/Rtp2_2016_logo.png",
                streamUrl = "https://streaming-live.rtp.pt/liverepeater/rtp2HD.smil/playlist.m3u8",
                category = "Portugal,RTP2"
            ),
            TvChannel(
                id = 933,
                name = "SIC",
                logoUrl = "https://i.imgur.com/SPMqiDG.png",
                streamUrl = "https://d1zx6l1dn8vaj5.cloudfront.net/out/v1/b89cc37caa6d418eb423cf092a2ef970/index.m3u8",
                category = "Portugal,SIC"
            ),
            TvChannel(
                id = 934,
                name = "RTP Açores",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/aa/RTP_A%C3%A7ores_%282016%29.svg/640px-RTP_A%C3%A7ores_%282016%29.svg.png",
                streamUrl = "https://streaming-live.rtp.pt/liverepeater/smil:rtpacoresHD.smil/playlist.m3u8",
                category = "Portugal,RTP"
            ),
            TvChannel(
                id = 935,
                name = "RTP Madeira Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/a/ac/RTP_Madeira_2016.png",
                streamUrl = "https://streaming-live.rtp.pt/liverepeater/smil:rtpmadeira.smil/playlist.m3u8",
                category = "Portugal,RTP"
            ),
            TvChannel(
                id = 936,
                name = "Porto Canal Ⓢ",
                logoUrl = "https://i.imgur.com/wsyvP2H.png",
                streamUrl = "https://streamer-a01.videos.sapo.pt/live/portocanal/playlist.m3u8",
                category = "Portugal,Porto"
            ),
            TvChannel(
                id = 937,
                name = "ADtv Ⓢ",
                logoUrl = "https://i.imgur.com/FvlcU3z.png",
                streamUrl = "https://playout172.livextend.cloud/liveiframe/_definst_/ngrp:liveartvabr_abr/playlist.m3u8",
                category = "Portugal,ADtv"
            ),
            TvChannel(
                id = 938,
                name = "Qatar Television",
                logoUrl = "https://i.imgur.com/N5RB4sp.png",
                streamUrl = "https://qatartv.akamaized.net/hls/live/2026573/qtv1/master.m3u8",
                category = "Qatar,Qatar"
            ),
            TvChannel(
                id = 939,
                name = "Qatar Television 2",
                logoUrl = "https://i.imgur.com/iWJxDUm.png",
                streamUrl = "https://qatartv.akamaized.net/hls/live/2026573/qtv1/master.m3u8",
                category = "Qatar,Qatar"
            ),
            TvChannel(
                id = 940,
                name = "Al Rayyan",
                logoUrl = "https://i.imgur.com/Ts3RjTV.png",
                streamUrl = "https://alrayyancdn.vidgyor.com/pub-noalrayy3pwz0l/liveabr/playlist_dvr.m3u8",
                category = "Qatar,Al"
            ),
            TvChannel(
                id = 941,
                name = "Al Rayyan Old TV",
                logoUrl = "https://i.imgur.com/4qB5iN0.png",
                streamUrl = "https://alrayyancdn.vidgyor.com/pub-nooldraybinbdh/liveabr/playlist_dvr.m3u8",
                category = "Qatar,Al"
            ),
            TvChannel(
                id = 942,
                name = "Al Jazeera Mubasher",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/9/90/Al_Jazeera_Mubasher_logo.png",
                streamUrl = "https://live-hls-web-ajm.getaj.net/AJM/index.m3u8",
                category = "Qatar,Al"
            ),
            TvChannel(
                id = 943,
                name = "Al Araby TV",
                logoUrl = "https://i.imgur.com/YMqWEe4.png",
                streamUrl = "https://alaraby.cdn.octivid.com/alaraby/smil:alaraby.stream.smil/chunklist.m3u8",
                category = "Qatar,Al"
            ),
            TvChannel(
                id = 944,
                name = "TVR 1 Ⓖ",
                logoUrl = "https://i.imgur.com/CKQ7mpB.png",
                streamUrl = "https://mn-nl.mncdn.com/tvr1_hd_live/smil:tvr1_hd_live.smil/playlist.m3u8",
                category = "Romania,TVR"
            ),
            TvChannel(
                id = 945,
                name = "TVR 2 Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/4/4c/TVR_2_2022_logo.png",
                streamUrl = "https://mn-nl.mncdn.com/tvr2_test/smil:tvr2_test.smil/playlist.m3u8",
                category = "Romania,TVR"
            ),
            TvChannel(
                id = 946,
                name = "TVR 3 Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/0/0d/TVR3_2022.png",
                streamUrl = "https://mn-nl.mncdn.com/tvr3_test/smil:tvr3_test.smil/playlist.m3u8",
                category = "Romania,TVR"
            ),
            TvChannel(
                id = 947,
                name = "TVR Info Ⓖ",
                logoUrl = "https://i.imgur.com/7oE7ThR.png",
                streamUrl = "https://mn-nl.mncdn.com/tvrinfo/tvrinfo_mjuypp/playlist.m3u8",
                category = "Romania,TVR"
            ),
            TvChannel(
                id = 948,
                name = "TVR International Ⓖ",
                logoUrl = "https://i.imgur.com/AlW8jyl.png",
                streamUrl = "https://mn-nl.mncdn.com/tvri_test/smil:tvri_test.smil/playlist.m3u8",
                category = "Romania,TVR"
            ),
            TvChannel(
                id = 949,
                name = "Prima TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/Prima_TV_%28Rumaenien%29_Logo.svg/512px-Prima_TV_%28Rumaenien%29_Logo.svg.png",
                streamUrl = "https://stream1.1616.ro:1945/prima/livestream/playlist.m3u8",
                category = "Romania,Prima"
            ),
            TvChannel(
                id = 950,
                name = "România TV Ⓖ",
                logoUrl = "https://i.imgur.com/ZIfEp5I.png",
                streamUrl = "https://livestream.romaniatv.net/clients/romaniatv/playlist.m3u8",
                category = "Romania,România"
            ),
            TvChannel(
                id = 951,
                name = "Telestar1",
                logoUrl = "https://i.imgur.com/UZQjEsd.png",
                streamUrl = "http://89.47.97.15/telestar/telestar.m3u8",
                category = "Romania,Telestar1"
            ),
            TvChannel(
                id = 952,
                name = "TVR Cluj Ⓖ",
                logoUrl = "https://i.imgur.com/8DqsGHO.png",
                streamUrl = "https://mn-nl.mncdn.com/tvrcluj_new/smil:tvrcluj_new.smil/index.m3u8",
                category = "Romania,TVR"
            ),
            TvChannel(
                id = 953,
                name = "TVR Craiova Ⓖ",
                logoUrl = "https://i.imgur.com/vxWbQiy.png",
                streamUrl = "https://mn-nl.mncdn.com/tvrcraiova_new/smil:tvrcraiova_new.smil/index.m3u8",
                category = "Romania,TVR"
            ),
            TvChannel(
                id = 954,
                name = "TVR Iași Ⓖ",
                logoUrl = "https://i.imgur.com/Kxkihds.png",
                streamUrl = "https://mn-nl.mncdn.com/tvriasi_new/smil:tvriasi_new.smil/index.m3u8",
                category = "Romania,TVR"
            ),
            TvChannel(
                id = 955,
                name = "TVR Timișoara Ⓖ",
                logoUrl = "https://i.imgur.com/Db3DV6H.png",
                streamUrl = "https://mn-nl.mncdn.com/tvrtimisoara_new/smil:tvrtimisoara_new.smil/index.m3u8",
                category = "Romania,TVR"
            ),
            TvChannel(
                id = 956,
                name = "TVR Tîrgu-Mureș Ⓖ",
                logoUrl = "https://i.imgur.com/9Hptdqj.png",
                streamUrl = "https://mn-nl.mncdn.com/tvrtgmures_new/smil:tvrtgmures_new.smil/index.m3u8",
                category = "Romania,TVR"
            ),
            TvChannel(
                id = 957,
                name = "Матч ТВ Ⓢ",
                logoUrl = "https://i.imgur.com/kFdooR4.png",
                streamUrl = "https://streaming.televizor-24-tochka.ru/live/6.m3u8",
                category = "Russia,Матч"
            ),
            TvChannel(
                id = 958,
                name = "НТВ Ⓢ",
                logoUrl = "https://i.imgur.com/DtQX5P2.png",
                streamUrl = "http://ott-cdn.ucom.am/s17/index.m3u8",
                category = "Russia,НТВ"
            ),
            TvChannel(
                id = 959,
                name = "Пятый канал Ⓢ",
                logoUrl = "https://i.imgur.com/u8Q69D9.png",
                streamUrl = "https://streaming.televizor-24-tochka.ru/live/8.m3u8",
                category = "Russia,Пятый"
            ),
            TvChannel(
                id = 960,
                name = "Карусель Ⓢ",
                logoUrl = "https://i.imgur.com/4fFMlVq.png",
                streamUrl = "https://streaming102.interskytech.com/live/232.m3u8",
                category = "Russia,Карусель"
            ),
            TvChannel(
                id = 961,
                name = "ОТР Ⓢ",
                logoUrl = "https://i.imgur.com/QyZvT3e.png",
                streamUrl = "https://streaming.televizor-24-tochka.ru/live/12.m3u8",
                category = "Russia,ОТР"
            ),
            TvChannel(
                id = 962,
                name = "ТВ Центр Ⓢ",
                logoUrl = "https://i.imgur.com/ZP0D6Rd.png",
                streamUrl = "http://ott-cdn.ucom.am/s54/index.m3u8",
                category = "Russia,ТВ"
            ),
            TvChannel(
                id = 963,
                name = "Рен ТВ Ⓢ",
                logoUrl = "https://i.imgur.com/18TAzYV.png",
                streamUrl = "https://streaming.televizor-24-tochka.ru/live/14.m3u8",
                category = "Russia,Рен"
            ),
            TvChannel(
                id = 964,
                name = "Спас Ⓢ",
                logoUrl = "https://i.imgur.com/A6Cqsom.jpeg",
                streamUrl = "https://spas.mediacdn.ru/cdn/spas/playlist.m3u8",
                category = "Russia,Спас"
            ),
            TvChannel(
                id = 965,
                name = "СТС Ⓢ",
                logoUrl = "https://i.imgur.com/y9bpqUD.png",
                streamUrl = "http://ott-cdn.ucom.am/s52/04.m3u8",
                category = "Russia,СТС"
            ),
            TvChannel(
                id = 966,
                name = "Домашний Ⓢ",
                logoUrl = "https://i.imgur.com/e8wlMIt.png",
                streamUrl = "http://ott-cdn.ucom.am/s88/index.m3u8",
                category = "Russia,Домашний"
            ),
            TvChannel(
                id = 967,
                name = "ТВ-3 Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/%D0%A2V3_logo_2023.svg/556px-%D0%A2V3_logo_2023.svg.png",
                streamUrl = "https://streaming.televizor-24-tochka.ru/live/18.m3u8",
                category = "Russia,ТВ-3"
            ),
            TvChannel(
                id = 968,
                name = "Пятница! Ⓢ",
                logoUrl = "https://i.imgur.com/rS11zVB.png",
                streamUrl = "https://streaming.televizor-24-tochka.ru/live/19.m3u8",
                category = "Russia,Пятница!"
            ),
            TvChannel(
                id = 969,
                name = "Звезда",
                logoUrl = "https://i.imgur.com/c0L0ncA.png",
                streamUrl = "https://tvchannelstream1.tvzvezda.ru/cdn/tvzvezda/playlist.m3u8",
                category = "Russia,Звезда"
            ),
            TvChannel(
                id = 970,
                name = "Мир",
                logoUrl = "https://i.imgur.com/L2slsbG.png",
                streamUrl = "http://hls.mirtv.cdnvideo.ru/mirtv-parampublish/mirtv_2500/playlist.m3u8",
                category = "Russia,Мир"
            ),
            TvChannel(
                id = 971,
                name = "ТНТ Ⓢ",
                logoUrl = "https://i.imgur.com/1WqIPOB.png",
                streamUrl = "http://ott-cdn.ucom.am/s19/index.m3u8",
                category = "Russia,ТНТ"
            ),
            TvChannel(
                id = 972,
                name = "Муз-ТВ Ⓢ",
                logoUrl = "https://i.imgur.com/Ml3qqOF.png",
                streamUrl = "https://streaming102.interskytech.com/live/618.m3u8",
                category = "Russia,Муз-ТВ"
            ),
            TvChannel(
                id = 973,
                name = "РБК",
                logoUrl = "https://i.imgur.com/P2Qii5B.png",
                streamUrl = "http://92.50.128.180/utv/1358/index.m3u8",
                category = "Russia,РБК"
            ),
            TvChannel(
                id = 974,
                name = "RT Д Русский Ⓖ",
                logoUrl = "https://i.imgur.com/v5fpEBo.png",
                streamUrl = "https://hls.rt.com/hls/rtdru.m3u8",
                category = "Russia,RT"
            ),
            TvChannel(
                id = 975,
                name = "CGTN Pусский",
                logoUrl = "https://i.imgur.com/fMsJYzl.png",
                streamUrl = "https://news.cgtn.com/resource/live/russian/cgtn-r.m3u8",
                category = "Russia,CGTN"
            ),
            TvChannel(
                id = 976,
                name = "Архыз 24",
                logoUrl = "https://i.imgur.com/mve0sSS.png",
                streamUrl = "https://live.mediacdn.ru/sr1/arhis24/playlist_hdhigh.m3u8",
                category = "Russia,Архыз"
            ),
            TvChannel(
                id = 977,
                name = "Астрахан 24",
                logoUrl = "https://i.imgur.com/9WcnjQN.png",
                streamUrl = "https://streaming.astrakhan.ru/astrakhan24/playlist.m3u8",
                category = "Russia,Астрахан"
            ),
            TvChannel(
                id = 978,
                name = "Башкортостан 24",
                logoUrl = "https://i.imgur.com/FQhWs1M.png",
                streamUrl = "https://vgtrkregion-reg.cdnvideo.ru/vgtrk/ufa/russia1-hd/index.m3u8",
                category = "Russia,Башкортостан"
            ),
            TvChannel(
                id = 979,
                name = "Белгород 24",
                logoUrl = "https://i.imgur.com/EEirvyx.png",
                streamUrl = "http://belnovosti.cdn.easyhoster.ru:8080/stream.m3u8",
                category = "Russia,Белгород"
            ),
            TvChannel(
                id = 980,
                name = "Ветта 24",
                logoUrl = "https://i.imgur.com/zKH1b5k.png",
                streamUrl = "http://serv24.vintera.tv:8081/vetta/vetta_office/playlist.m3u8",
                category = "Russia,Ветта"
            ),
            TvChannel(
                id = 981,
                name = "Волгоград 24 Ⓢ",
                logoUrl = "https://i.imgur.com/gFMnaU5.png",
                streamUrl = "https://vgtrkregion-reg.cdnvideo.ru/vgtrk/volgograd/russia1-hd/index.m3u8",
                category = "Russia,Волгоград"
            ),
            TvChannel(
                id = 982,
                name = "Запад 24",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/f/f8/Zapad_24.jpg",
                streamUrl = "https://vgtrkregion-reg.cdnvideo.ru/vgtrk/kaliningrad/russia1-hd/index.m3u8",
                category = "Russia,Запад"
            ),
            TvChannel(
                id = 983,
                name = "Крым 24",
                logoUrl = "https://i.imgur.com/k4C0uvp.png",
                streamUrl = "https://cdn.1tvcrimea.ru/24tvcrimea.m3u8",
                category = "Russia,Крым"
            ),
            TvChannel(
                id = 984,
                name = "Луганск 24",
                logoUrl = "https://i.imgur.com/YnLFQnt.png",
                streamUrl = "https://tv.gtrklnr.ru/hls/Lugansk24.m3u8",
                category = "Russia,Луганск"
            ),
            TvChannel(
                id = 985,
                name = "Мир Белогорья",
                logoUrl = "https://i.imgur.com/CCNAg7R.png",
                streamUrl = "http://mirbelogorya.ru:8080/mirbelogorya/index.m3u8",
                category = "Russia,Мир"
            ),
            TvChannel(
                id = 986,
                name = "Нижний Новгород 24",
                logoUrl = "https://i.imgur.com/ZWgPVIC.png",
                streamUrl = "https://live-vestinn.cdnvideo.ru/vestinn/nn24-khl/playlist.m3u8",
                category = "Russia,Нижний"
            ),
            TvChannel(
                id = 987,
                name = "Самара 24",
                logoUrl = "https://i.imgur.com/Xg7Xzna.png",
                streamUrl = "https://vgtrkregion.cdnvideo.ru/vgtrk/samara/regionHD/playlist.m3u8",
                category = "Russia,Самара"
            ),
            TvChannel(
                id = 988,
                name = "Сибирь 24",
                logoUrl = "https://i.imgur.com/WxU6QUB.png",
                streamUrl = "https://vgtrkregion-reg.cdnvideo.ru/vgtrk/novosibirsk/russia1-hd/index.m3u8",
                category = "Russia,Сибирь"
            ),
            TvChannel(
                id = 989,
                name = "Тольятти 24",
                logoUrl = "https://i.imgur.com/5jVKopE.png",
                streamUrl = "https://tvtogliatti24.ru/hls/live1080/index.m3u8",
                category = "Russia,Тольятти"
            ),
            TvChannel(
                id = 990,
                name = "Урал 24",
                logoUrl = "https://i.imgur.com/EaxyGh0.png",
                streamUrl = "https://vgtrkregion-reg.cdnvideo.ru/vgtrk/chelyabinsk/russia1-hd/index.m3u8",
                category = "Russia,Урал"
            ),
            TvChannel(
                id = 991,
                name = "Якутия 24",
                logoUrl = "https://i.imgur.com/2BAQklm.png",
                streamUrl = "https://live-saha.cdnvideo.ru/saha2/yak24rtmp_live.smil/playlist.m3u8",
                category = "Russia,Якутия"
            ),
            TvChannel(
                id = 992,
                name = "360 Новости",
                logoUrl = "https://i.imgur.com/YXDeX8q.png",
                streamUrl = "https://live-vgtrksmotrim.cdnvideo.ru/vgtrksmotrim/smotrim-live-03-srt.smil/playlist.m3u8",
                category = "Russia,360"
            ),
            TvChannel(
                id = 993,
                name = "Небеса ТВ7 Ⓢ",
                logoUrl = "https://www.nebesatv7.com/wp-content/themes/tv7-theme/assets/img/logo_nebesa_short.png",
                streamUrl = "https://vod.tv7.fi/tv7-ru/tv7-ru.smil/playlist.m3u8",
                category = "Russia,Небеса"
            ),
            TvChannel(
                id = 994,
                name = "Север",
                logoUrl = "https://i.imgur.com/sTOQLYl.png",
                streamUrl = "https://live.mediacdn.ru/sr1/sever/playlist.m3u8",
                category = "Russia,Север"
            ),
            TvChannel(
                id = 995,
                name = "Смотрим: Мелодрамы",
                logoUrl = "https://cdn-st1.smotrim.ru/vh/pictures/r/456/967/6.png",
                streamUrl = "https://live-vgtrksmotrim.cdnvideo.ru/vgtrksmotrim/smotrim-live-02.smil/playlist.m3u8",
                category = "Russia,Смотрим:"
            ),
            TvChannel(
                id = 996,
                name = "Смотрим: Тайны",
                logoUrl = "https://cdn-st3.smotrim.ru/vh/pictures/r/456/396/2.png",
                streamUrl = "https://live-vgtrksmotrim.cdnvideo.ru/vgtrksmotrim/smotrim-live-07.smil/playlist.m3u8",
                category = "Russia,Смотрим:"
            ),
            TvChannel(
                id = 997,
                name = "Смотрим: Честный Детектив",
                logoUrl = "https://cdn-st3.smotrim.ru/vh/pictures/r/444/241/8.png",
                streamUrl = "https://live-vgtrksmotrim.cdnvideo.ru/vgtrksmotrim/smotrim-live-01.smil/playlist.m3u8",
                category = "Russia,Смотрим:"
            ),
            TvChannel(
                id = 998,
                name = "Ю Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/ru/a/ac/%D0%9B%D0%BE%D0%B3%D0%BE%D1%82%D0%B8%D0%BF_%D1%82%D0%B5%D0%BB%D0%B5%D0%BA%D0%B0%D0%BD%D0%B0%D0%BB%D0%B0_%C2%AB%D0%AE%C2%BB_%28%D1%81_3_%D1%81%D0%B5%D0%BD%D1%82%D1%8F%D0%B1%D1%80%D1%8F_2018_%D0%B3%D0%BE%D0%B4%D0%B0%29.png",
                streamUrl = "https://strm.yandex.ru/kal/utv/utv0.m3u8",
                category = "Russia,Ю"
            ),
            TvChannel(
                id = 999,
                name = "Матч! Планета Ⓢ",
                logoUrl = "https://i.imgur.com/vhyMb9D.png",
                streamUrl = "http://212.0.211.102:9999/play/a00b/index.m3u8",
                category = "Russia,Матч!"
            ),
            TvChannel(
                id = 1000,
                name = "San Marino Rtv",
                logoUrl = "https://i.imgur.com/lJpOlLv.png",
                streamUrl = "https://d2hrvno5bw6tg2.cloudfront.net/smrtv-ch01/_definst_/smil:ch-01.smil/chunklist_b2192000_slita.m3u8",
                category = "San"
            ),
            TvChannel(
                id = 1001,
                name = "San Marino Rtv Sport",
                logoUrl = "https://i.imgur.com/PGm944g.png",
                streamUrl = "https://d2hrvno5bw6tg2.cloudfront.net/smrtv-ch02/_definst_/smil:ch-02.smil/chunklist_b1692000_slita.m3u8",
                category = "San"
            ),
            TvChannel(
                id = 1002,
                name = "Al Saudiya",
                logoUrl = "https://i.imgur.com/GRQTndk.png",
                streamUrl = "https://shd-gcp-live.edgenextcdn.net/live/bitmovin-saudi-tv/2ad66056b51fd8c1b624854623112e43/index.m3u8",
                category = "Saudi"
            ),
            TvChannel(
                id = 1003,
                name = "SBC Saudi Arabia",
                logoUrl = "https://i.imgur.com/9JSQglj.png",
                streamUrl = "https://shd-gcp-live.edgenextcdn.net/live/bitmovin-sbc/90e09c0c28db26435799b4a14892a167/index.m3u8",
                category = "Saudi"
            ),
            TvChannel(
                id = 1004,
                name = "Al Ekhbariya",
                logoUrl = "https://i.imgur.com/WcRlHQm.png",
                streamUrl = "https://shd-gcp-live.edgenextcdn.net/live/bitmovin-al-ekhbaria/297b3ef1cd0633ad9cfba7473a686a06/index.m3u8",
                category = "Saudi"
            ),
            TvChannel(
                id = 1005,
                name = "Al Saudiya Alaan",
                logoUrl = "https://i.imgur.com/sEOjApe.png",
                streamUrl = "https://shd-gcp-live.edgenextcdn.net/live/bitmovin-ksa-now/71ed3aa814c643306c0a8bc4fcc7d17f/index.m3u8",
                category = "Saudi"
            ),
            TvChannel(
                id = 1006,
                name = "Al Quran Al Kareem TV",
                logoUrl = "https://i.imgur.com/A2fJysM.png",
                streamUrl = "https://al-ekhbaria-prod-dub.shahid.net/out/v1/9885cab0a3ec4008b53bae57a27ca76b/index.m3u8",
                category = "Saudi"
            ),
            TvChannel(
                id = 1007,
                name = "Rotana Cinema KSA",
                logoUrl = "https://i.imgur.com/pGgp38I.png",
                streamUrl = "https://bcovlive-a.akamaihd.net/9527a892aeaf43019fd9eeb77ad1516e/eu-central-1/6057955906001/playlist.m3u8",
                category = "Saudi"
            ),
            TvChannel(
                id = 1008,
                name = "Rotana Classic",
                logoUrl = "https://i.imgur.com/pMMUvkt.png",
                streamUrl = "https://bcovlive-a.akamaihd.net/0debf5648e584e5fb795c3611c5c0252/eu-central-1/6057955906001/playlist.m3u8",
                category = "Saudi"
            ),
            TvChannel(
                id = 1009,
                name = "RTS 1",
                logoUrl = "https://i.imgur.com/S1pKHSR.png",
                streamUrl = "https://webtvstream.bhtelecom.ba/rts1.m3u8",
                category = "Serbia,RTS"
            ),
            TvChannel(
                id = 1010,
                name = "RTS 2",
                logoUrl = "https://i.imgur.com/jltAf5h.png",
                streamUrl = "https://webtvstream.bhtelecom.ba/rts2.m3u8",
                category = "Serbia,RTS"
            ),
            TvChannel(
                id = 1011,
                name = "Euronews Serbia",
                logoUrl = "https://i.imgur.com/b24QKcq.png",
                streamUrl = "https://d1ei8ofhgfmkac.cloudfront.net/app-19518-1306/ngrp:QoZfNjsg_all/playlist.m3u8",
                category = "Serbia,Euronews"
            ),
            TvChannel(
                id = 1012,
                name = "JOJ",
                logoUrl = "https://i.imgur.com/5BAWD0z.png",
                streamUrl = "https://live.cdn.joj.sk/live/andromeda/joj-1080.m3u8",
                category = "Slovakia,JOJ"
            ),
            TvChannel(
                id = 1013,
                name = "JOJ Plus",
                logoUrl = "https://i.imgur.com/fKPliTj.png",
                streamUrl = "https://live.cdn.joj.sk/live/andromeda/plus-1080.m3u8",
                category = "Slovakia,JOJ"
            ),
            TvChannel(
                id = 1014,
                name = "WAU",
                logoUrl = "https://i.imgur.com/wO5ifff.png",
                streamUrl = "https://live.cdn.joj.sk/live/andromeda/wau-1080.m3u8",
                category = "Slovakia,WAU"
            ),
            TvChannel(
                id = 1015,
                name = "JOJ 24",
                logoUrl = "https://i.imgur.com/owEVXRE.png",
                streamUrl = "https://live.cdn.joj.sk/live/andromeda/joj_news-1080.m3u8",
                category = "Slovakia,JOJ"
            ),
            TvChannel(
                id = 1016,
                name = "JOJ Šport",
                logoUrl = "https://i.imgur.com/QWEY2a5.png",
                streamUrl = "https://live.cdn.joj.sk/live/andromeda/joj_sport-1080.m3u8",
                category = "Slovakia,JOJ"
            ),
            TvChannel(
                id = 1017,
                name = "Senzi",
                logoUrl = "https://i.imgur.com/W82dwzf.png",
                streamUrl = "http://lb.streaming.sk/senzi/stream/playlist.m3u8",
                category = "Slovakia,Senzi"
            ),
            TvChannel(
                id = 1018,
                name = "Folx Slovenija",
                logoUrl = "https://i.imgur.com/RK1IASU.png",
                streamUrl = "https://cdne.folxplay.tv/folx-trz/streams/ch-5/master.m3u8",
                category = "Slovenia,Folx"
            ),
            TvChannel(
                id = 1019,
                name = "Dacwa TV Ⓢ",
                logoUrl = "https://i.imgur.com/rMqrLzV.png",
                streamUrl = "https://ap02.iqplay.tv:8082/iqb8002/d13w1/playlist.m3u8",
                category = "Somalia,Dacwa"
            ),
            TvChannel(
                id = 1020,
                name = "MM Somali TV Ⓢ",
                logoUrl = "https://www.lyngsat.com/logo/tv/mm/mm-somali-tv-so.png",
                streamUrl = "https://cdn.mediavisionuk.com:9000/MMTV/index.m3u8",
                category = "Somalia,MM"
            ),
            TvChannel(
                id = 1021,
                name = "Puntland TV Ⓢ",
                logoUrl = "https://i.imgur.com/C8EvQUo.png",
                streamUrl = "http://cdn.mediavisionuae.com:1935/live/putlandtv2.stream/playlist.m3u8",
                category = "Somalia,Puntland"
            ),
            TvChannel(
                id = 1022,
                name = "Saab TV Ⓢ",
                logoUrl = "https://i.imgur.com/JEC1J89.png",
                streamUrl = "https://ap02.iqplay.tv:8082/iqb8002/s03btv/playlist.m3u8",
                category = "Somalia,Saab"
            ),
            TvChannel(
                id = 1023,
                name = "SBC Somalia Ⓢ",
                logoUrl = "https://i.imgur.com/VLhgTIA.png",
                streamUrl = "http://cdn.mediavisionuae.com:1935/live/sbctv.stream/playlist.m3u8",
                category = "Somalia,SBC"
            ),
            TvChannel(
                id = 1024,
                name = "SNTV Daljir Ⓢ",
                logoUrl = "https://i.imgur.com/Re3ur88.png",
                streamUrl = "https://ap02.iqplay.tv:8082/iqb8002/s2tve/playlist.m3u8",
                category = "Somalia,SNTV"
            ),
            TvChannel(
                id = 1025,
                name = "Somali Cable TV Ⓢ",
                logoUrl = "https://i.imgur.com/iPkaCts.png",
                streamUrl = "https://ap02.iqplay.tv:8082/iqb8002/somc131/playlist.m3u8",
                category = "Somalia,Somali"
            ),
            TvChannel(
                id = 1026,
                name = "Somali National TV Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/d/d6/SNTV_REBRANDED_LOGO.png",
                streamUrl = "https://ap02.iqplay.tv:8082/iqb8002/s4ne/playlist.m3u8",
                category = "Somalia,Somali"
            ),
            TvChannel(
                id = 1027,
                name = "La 1",
                logoUrl = "https://i.imgur.com/NbesiPn.png",
                streamUrl = "https://dh6vo1bovy43s.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-x3gcl32l5ffq2/La_1_ES.m3u8",
                category = "Spain,La"
            ),
            TvChannel(
                id = 1028,
                name = "La 2",
                logoUrl = "https://i.imgur.com/DmuTwDw.png",
                streamUrl = "https://di2qeq48iv8ps.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-o8u23e6v7vptv/La_2_ES.m3u8",
                category = "Spain,La"
            ),
            TvChannel(
                id = 1029,
                name = "Antena 3",
                logoUrl = "https://i.imgur.com/j3SP4BS.png",
                streamUrl = "http://185.189.225.150:85/Antena3HD/index.m3u8",
                category = "Spain,Antena"
            ),
            TvChannel(
                id = 1030,
                name = "Cuatro",
                logoUrl = "https://i.imgur.com/zROxNap.png",
                streamUrl = "http://185.189.225.150:85/CuatroHD/index.m3u8",
                category = "Spain,Cuatro"
            ),
            TvChannel(
                id = 1031,
                name = "Telecinco",
                logoUrl = "https://i.imgur.com/JECsKdk.png",
                streamUrl = "http://185.189.225.150:85/TeleCincoHD/index.m3u8",
                category = "Spain,Telecinco"
            ),
            TvChannel(
                id = 1032,
                name = "La Sexta",
                logoUrl = "https://i.imgur.com/b59MxgM.png",
                streamUrl = "http://185.189.225.150:85/LaSexta/index.m3u8",
                category = "Spain,La"
            ),
            TvChannel(
                id = 1033,
                name = "24h",
                logoUrl = "https://i.imgur.com/ZKR2jKr.png",
                streamUrl = "https://d3pfmk89wc0vm9.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-nlow3qkp9tmdm/24H_ES.m3u8",
                category = "Spain,24h"
            ),
            TvChannel(
                id = 1034,
                name = "tdp",
                logoUrl = "https://i.imgur.com/HliegRJ.png",
                streamUrl = "https://rtvelivestream.akamaized.net/rtvesec/tdp/tdp_main.m3u8",
                category = "Spain,tdp"
            ),
            TvChannel(
                id = 1035,
                name = "clan",
                logoUrl = "https://i.imgur.com/38xIfQ3.png",
                streamUrl = "https://rtvelivestream.akamaized.net/rtvesec/clan/clan_main_dvr.m3u8",
                category = "Spain,clan"
            ),
            TvChannel(
                id = 1036,
                name = "TVE Internacional Europe-Asia",
                logoUrl = "https://i.imgur.com/ow1HArj.png",
                streamUrl = "https://rtvelivestream.akamaized.net/rtvesec/int/tvei_eu_main_dvr.m3u8",
                category = "Spain,TVE"
            ),
            TvChannel(
                id = 1037,
                name = "Neox Ⓢ",
                logoUrl = "https://raw.githubusercontent.com/tv-logo/tv-logos/refs/heads/main/countries/spain/neox-es.png",
                streamUrl = "http://185.189.225.150:85/neox/index.m3u8",
                category = "Spain,Neox"
            ),
            TvChannel(
                id = 1038,
                name = "Nova Ⓢ",
                logoUrl = "https://raw.githubusercontent.com/tv-logo/tv-logos/refs/heads/main/countries/spain/nova-es.png",
                streamUrl = "http://185.189.225.150:85/nova/index.m3u8",
                category = "Spain,Nova"
            ),
            TvChannel(
                id = 1039,
                name = "Mega Ⓢ",
                logoUrl = "https://i.imgur.com/Udrt2eK.png",
                streamUrl = "http://185.189.225.150:85/mega/index.m3u8",
                category = "Spain,Mega"
            ),
            TvChannel(
                id = 1040,
                name = "Atreseries Ⓢ",
                logoUrl = "https://raw.githubusercontent.com/tv-logo/tv-logos/refs/heads/main/countries/spain/atreseries-es.png",
                streamUrl = "http://181.78.109.48:8000/play/a00l/index.m3u8",
                category = "Spain,Atreseries"
            ),
            TvChannel(
                id = 1041,
                name = "FDF",
                logoUrl = "https://raw.githubusercontent.com/tv-logo/tv-logos/refs/heads/main/countries/spain/fdf-es.png",
                streamUrl = "http://185.189.225.150:85/fdf/index.m3u8",
                category = "Spain,FDF"
            ),
            TvChannel(
                id = 1042,
                name = "Divinity Ⓖ",
                logoUrl = "https://i.imgur.com/o7fvEr6.png",
                streamUrl = "https://directos.divinity.es/orilinear04/live/linear04/main/main.isml/main-audio_spa=128000-video=1500000.m3u8",
                category = "Spain,Divinity"
            ),
            TvChannel(
                id = 1043,
                name = "Energy Ⓖ",
                logoUrl = "https://raw.githubusercontent.com/tv-logo/tv-logos/refs/heads/main/countries/spain/energy-es.png",
                streamUrl = "https://directos.energytv.es/orilinear03/live/linear03/main/main.isml/main-audio_spa=128000-video=1500000.m3u8",
                category = "Spain,Energy"
            ),
            TvChannel(
                id = 1044,
                name = "Boing",
                logoUrl = "https://i.imgur.com/nUYuCAP.png",
                streamUrl = "http://185.189.225.150:85/boing/index.m3u8",
                category = "Spain,Boing"
            ),
            TvChannel(
                id = 1045,
                name = "Be Mad Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Be_Mad_TV.svg/512px-Be_Mad_TV.svg.png",
                streamUrl = "https://directos.bemad.es/orilinear02/live/linear02/main/main.isml/main-audio_spa=128000-video=1500000.m3u8",
                category = "Spain,Be"
            ),
            TvChannel(
                id = 1046,
                name = "Paramount Network",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9f/Paramount_Network.svg/512px-Paramount_Network.svg.png",
                streamUrl = "http://185.189.225.150:85/Paramount/index.m3u8",
                category = "Spain,Paramount"
            ),
            TvChannel(
                id = 1047,
                name = "RNE para todos",
                logoUrl = "https://graph.facebook.com/radionacionalrne/picture?width=200&height=200",
                streamUrl = "https://rtvelivestream.akamaized.net/rtvesec/rne/rne_para_todos_main.m3u8",
                category = "Spain,RNE"
            ),
            TvChannel(
                id = 1048,
                name = "euronews",
                logoUrl = "https://raw.githubusercontent.com/tv-logo/tv-logos/refs/heads/main/countries/international/euro-news-int.png",
                streamUrl = "https://euronews-live-spa-es.fast.rakuten.tv/v1/master/0547f18649bd788bec7b67b746e47670f558b6b2/production-LiveChannel-6571/bitok/eyJzdGlkIjoiMDA0YjY0NTMtYjY2MC00ZTZkLTlkNzEtMTk3YTM3ZDZhZWIxIiwibWt0IjoiZXMiLCJjaCI6NjU3MSwicHRmIjoxfQ==/26034/euronews-es.m3u8",
                category = "Spain,euronews"
            ),
            TvChannel(
                id = 1049,
                name = "El País",
                logoUrl = "https://graph.facebook.com/elpais/picture?width=200&height=200",
                streamUrl = "https://d2xqbi89ghm9hh.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-79fx3huimw4xc-ssai-prd/fast-channel-el-pais.m3u8",
                category = "Spain,El"
            ),
            TvChannel(
                id = 1050,
                name = "Negocios",
                logoUrl = "https://pbs.twimg.com/profile_images/1321367703731523584/bNMmbetI_200x200.jpg",
                streamUrl = "https://streaming013.gestec-video.com/hls/negociostv.m3u8",
                category = "Spain,Negocios"
            ),
            TvChannel(
                id = 1051,
                name = "Squirrel",
                logoUrl = "https://i.imgur.com/urF0kYA.png",
                streamUrl = "https://tsw.streamingwebtv24.it:1936/inteccdn1/inteccdn1/playlist.m3u8",
                category = "Spain,Squirrel"
            ),
            TvChannel(
                id = 1052,
                name = "BOM Cine",
                logoUrl = "https://i.imgur.com/cqhofMU.png",
                streamUrl = "https://tsw.streamingwebtv24.it:1936/inteccdn3/inteccdn3/playlist.m3u8",
                category = "Spain,BOM"
            ),
            TvChannel(
                id = 1053,
                name = "Telemadrid",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/71/TeleMadrid.svg/512px-TeleMadrid.svg.png",
                streamUrl = "https://telemadrid-23-secure2.akamaized.net/master.m3u8",
                category = "Spain,Telemadrid"
            ),
            TvChannel(
                id = 1054,
                name = "La Otra",
                logoUrl = "https://i.imgur.com/W1UZyXH.png",
                streamUrl = "https://laotra-1-23-secure2.akamaized.net/master.m3u8",
                category = "Spain,La"
            ),
            TvChannel(
                id = 1055,
                name = "Canal Sur Andalucía",
                logoUrl = "https://i.imgur.com/WcVOXPr.png",
                streamUrl = "https://d35x6iaiw8f75z.cloudfront.net/v1/master/3722c60a815c199d9c0ef36c5b73da68a62b09d1/cc-kbwsl0jk1bvoo/canal_sur_andalucia_es.m3u8",
                category = "Spain,Canal"
            ),
            TvChannel(
                id = 1056,
                name = "La 8 Mediterráneo",
                logoUrl = "https://graph.facebook.com/la8mediterraneo/picture?width=200&height=200",
                streamUrl = "https://streaming004.gestec-video.com/hls/8TV.m3u8",
                category = "Spain,La"
            ),
            TvChannel(
                id = 1057,
                name = "Canal Extremadura",
                logoUrl = "https://i.imgur.com/xBeywIA.png",
                streamUrl = "https://cdnapisec.kaltura.com/p/5581662/sp/558166200/playManifest/entryId/1_1u7ssdy3/protocol/https/format/applehttp/flavorIds/1_8xbndriw/a.m3u8",
                category = "Spain,Canal"
            ),
            TvChannel(
                id = 1058,
                name = "Aragón TV Ⓢ",
                logoUrl = "https://i.imgur.com/8H3Q07b.png",
                streamUrl = "https://cartv.streaming.aranova.es/hls/live/aragontv_canal1.m3u8",
                category = "Spain,Aragón"
            ),
            TvChannel(
                id = 1059,
                name = "ETB1",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f4/ETB1_2022_logo.svg/512px-ETB1_2022_logo.svg.png",
                streamUrl = "https://multimedia.eitb.eus/live-content/etb1hd-hls/master.m3u8",
                category = "Spain,ETB1"
            ),
            TvChannel(
                id = 1060,
                name = "ETB2",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c4/ETB2_2022_logo.svg/512px-ETB2_2022_logo.svg.png",
                streamUrl = "https://multimedia.eitb.eus/live-content/etb2hd-hls/master.m3u8",
                category = "Spain,ETB2"
            ),
            TvChannel(
                id = 1061,
                name = "TV3",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cb/TV3.svg/300px-TV3.svg.png",
                streamUrl = "http://185.189.225.150:85/tv3/index.m3u8",
                category = "Spain,TV3"
            ),
            TvChannel(
                id = 1062,
                name = "TV3Cat Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/97/TV3CAT.svg/512px-TV3CAT.svg.png",
                streamUrl = "https://directes3-tv-int.3catdirectes.cat/live-content/tvi-hls/master.m3u8",
                category = "Spain,TV3Cat"
            ),
            TvChannel(
                id = 1063,
                name = "3/24",
                logoUrl = "https://raw.githubusercontent.com/tv-logo/tv-logos/refs/heads/main/countries/spain/3-24-es.png",
                streamUrl = "https://directes-tv-int.3catdirectes.cat/live-origin/canal324-hls/master.m3u8",
                category = "Spain,3/24"
            ),
            TvChannel(
                id = 1064,
                name = "Bon Dia",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/4/4f/Logo_Bon_Dia_TV.png",
                streamUrl = "https://directes-tv-int.3catdirectes.cat/live-content/bondia-hls/master.m3u8",
                category = "Spain,Bon"
            ),
            TvChannel(
                id = 1065,
                name = "SX3 Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/SX3_logo.svg/2880px-SX3_logo.svg.png",
                streamUrl = "https://directes-tv-cat.3catdirectes.cat/live-content/super3-hls/master.m3u8",
                category = "Spain,SX3"
            ),
            TvChannel(
                id = 1066,
                name = "El 33 Ⓖ",
                logoUrl = "https://raw.githubusercontent.com/tv-logo/tv-logos/refs/heads/main/countries/spain/el-33-es.png",
                streamUrl = "https://directes-tv-cat.3catdirectes.cat/live-origin/c33-super3-hls/master.m3u8",
                category = "Spain,El"
            ),
            TvChannel(
                id = 1067,
                name = "Esport3 Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9a/Esport3.svg/1200px-Esport3.svg.png",
                streamUrl = "https://directes-tv-es.3catdirectes.cat/live-origin/esport3-hls/master.m3u8",
                category = "Spain,Esport3"
            ),
            TvChannel(
                id = 1068,
                name = "Canal TE24",
                logoUrl = "https://i.ibb.co/3ynghbW/logox2.png",
                streamUrl = "https://ingest1-video.streaming-pro.com/esportsteABR/etestream/playlist.m3u8",
                category = "Spain,Canal"
            ),
            TvChannel(
                id = 1069,
                name = "À Punt TV",
                logoUrl = "https://i.imgur.com/M88LoNl.png",
                streamUrl = "https://bcovlive-a.akamaihd.net/8499d938ef904e39b58a4adec2ddeada/eu-west-1/6057955885001/playlist_dvr.m3u8",
                category = "Spain,À"
            ),
            TvChannel(
                id = 1070,
                name = "7 Región de Murcia Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/26/La_7_logo.svg/150px-La_7_logo.svg.png",
                streamUrl = "https://rtv-murcia-live.globalmest.com/principal/smil:principal.smil/playlist.m3u8",
                category = "Spain,7"
            ),
            TvChannel(
                id = 1071,
                name = "Canal 4 Tenerife",
                logoUrl = "https://i.imgur.com/Egymir4.png",
                streamUrl = "https://videoserver.tmcreativos.com:19360/ccxwhsfcnq/ccxwhsfcnq.m3u8",
                category = "Spain,Canal"
            ),
            TvChannel(
                id = 1072,
                name = "Televisión Melilla",
                logoUrl = "https://raw.githubusercontent.com/tv-logo/tv-logos/refs/heads/main/countries/spain/television-melilla-es.png",
                streamUrl = "https://tvmelilla-hls-rm-lw.flumotion.com/playlist.m3u8",
                category = "Spain,Televisión"
            ),
            TvChannel(
                id = 1073,
                name = "La 1 (Catalunya)",
                logoUrl = "https://i.imgur.com/NbesiPn.png",
                streamUrl = "https://rtvelivestream-clnx.rtve.es/rtvesec/cat/la1_cat_main_dvr.m3u8",
                category = "Spain,La"
            ),
            TvChannel(
                id = 1074,
                name = "La 1 (Canarias)",
                logoUrl = "https://i.imgur.com/NbesiPn.png",
                streamUrl = "https://rtvelivestream-clnx.rtve.es/rtvesec/can/la1_can_main_720.m3u8",
                category = "Spain,La"
            ),
            TvChannel(
                id = 1075,
                name = "La 2 (Catalunya)",
                logoUrl = "https://i.imgur.com/DmuTwDw.png",
                streamUrl = "https://rtvelivestream.akamaized.net/rtvesec/cat/la2_cat_main_dvr.m3u8",
                category = "Spain,La"
            ),
            TvChannel(
                id = 1076,
                name = "La 2 (Canarias)",
                logoUrl = "https://i.imgur.com/DmuTwDw.png",
                streamUrl = "https://ztnr.rtve.es/ztnr/5468585.m3u8",
                category = "Spain,La"
            ),
            TvChannel(
                id = 1077,
                name = "HQM Arabic",
                logoUrl = "https://hqm.es/wp-content/uploads/arabic-hqm-logo.png",
                streamUrl = "https://livelist01.yowi.tv/lista/39596c72840d27b213caf4e58c39599a6f2ed203/master.m3u8",
                category = "Spain"
            ),
            TvChannel(
                id = 1078,
                name = "HQM Baladas",
                logoUrl = "https://hqm.es/wp-content/uploads/baladas-hqm-logo.png",
                streamUrl = "https://livelist01.yowi.tv/lista/5d7d2c21e0ec7a8a99fd1fdbc52cbdc0782f77fc/master.m3u8",
                category = "Spain"
            ),
            TvChannel(
                id = 1079,
                name = "HQM Blues",
                logoUrl = "https://hqm.es/wp-content/uploads/blues-hqm-logo.png",
                streamUrl = "https://livelist01.yowi.tv/lista/81c601f370e44dc566113fd752204be5f5f53b61/master.m3u8",
                category = "Spain"
            ),
            TvChannel(
                id = 1080,
                name = "HQM Chill Out",
                logoUrl = "https://hqm.es/wp-content/uploads/chill-out-hqm-logo.png",
                streamUrl = "https://livelist01.yowi.tv/lista/183a351ddb0e57af6d735256226e6033c32219ab/master.m3u8",
                category = "Spain"
            ),
            TvChannel(
                id = 1081,
                name = "HQM Classic",
                logoUrl = "https://hqm.es/wp-content/uploads/classic-hqm-logo.png",
                streamUrl = "https://livelist01.yowi.tv/lista/f04129475945936b248aa723de56519ea2ff10fc/master.m3u8",
                category = "Spain"
            ),
            TvChannel(
                id = 1082,
                name = "HQM Dance",
                logoUrl = "https://hqm.es/wp-content/uploads/dance-hqm-logo.png",
                streamUrl = "https://livelist01.yowi.tv/lista/57cf2f51b07ff21988a7a6f0270a66d41086d4a4/master.m3u8",
                category = "Spain"
            ),
            TvChannel(
                id = 1083,
                name = "HQM Folk",
                logoUrl = "https://hqm.es/wp-content/uploads/folk-hqm-logo.png",
                streamUrl = "https://livelist01.yowi.tv/lista/9f5310c179e8e840188d183be235f755b18cf703/master.m3u8",
                category = "Spain"
            ),
            TvChannel(
                id = 1084,
                name = "HQM Gym",
                logoUrl = "https://hqm.es/wp-content/uploads/gym-hqm-logo.png",
                streamUrl = "https://livelist01.yowi.tv/lista/abb87f329d0ed03072b1930e9636a53e8076c8d5/master.m3u8",
                category = "Spain"
            ),
            TvChannel(
                id = 1085,
                name = "HQM Hip Hop",
                logoUrl = "https://hqm.es/wp-content/uploads/hip-hop-hqm-logo.png",
                streamUrl = "https://livelist01.yowi.tv/lista/8327abc87895df4c76db1155435fdca6a3607bbd/master.m3u8",
                category = "Spain"
            ),
            TvChannel(
                id = 1086,
                name = "HQM Hits",
                logoUrl = "https://hqm.es/wp-content/uploads/hits-hqm-logo.png",
                streamUrl = "https://livelist01.yowi.tv/lista/5e2db2017a8fd03f73b40ede363d1a586db4e9a6/master.m3u8",
                category = "Spain"
            ),
            TvChannel(
                id = 1087,
                name = "HQM Jazz",
                logoUrl = "https://hqm.es/wp-content/uploads/jazz-hqm-logo.png",
                streamUrl = "https://livelist01.yowi.tv/lista/f204aa5b3f0691e69851b54b7746ef09ede26f6a/master.m3u8",
                category = "Spain"
            ),
            TvChannel(
                id = 1088,
                name = "HQM Kids",
                logoUrl = "https://hqm.es/wp-content/uploads/kids-hqm-logo.png",
                streamUrl = "https://livelist01.yowi.tv/lista/e4bc12dafe33c3ceb3e382e3acc0ec2c012cf7fd/master.m3u8",
                category = "Spain"
            ),
            TvChannel(
                id = 1089,
                name = "HQM Latin",
                logoUrl = "https://hqm.es/wp-content/uploads/latin-hqm-logo.png",
                streamUrl = "https://livelist01.yowi.tv/lista/9a4da7871ec57b4b63ed49597a13d09869172be0/master.m3u8",
                category = "Spain"
            ),
            TvChannel(
                id = 1090,
                name = "HQM Pop",
                logoUrl = "https://hqm.es/wp-content/uploads/pop-hqm-logo.png",
                streamUrl = "https://livelist01.yowi.tv/lista/eb2fa68a058a701fa5bd2c80f6c8a6075896f71d/master.m3u8",
                category = "Spain"
            ),
            TvChannel(
                id = 1091,
                name = "HQM Relax",
                logoUrl = "https://hqm.es/wp-content/uploads/relax-hqm-logo.png",
                streamUrl = "https://livelist01.yowi.tv/lista/dc1b71c6fda2e687050facaa7242062cbf5a7f2a/master.m3u8",
                category = "Spain"
            ),
            TvChannel(
                id = 1092,
                name = "HQM Remember",
                logoUrl = "https://hqm.es/wp-content/uploads/remember-hqm-logo.png",
                streamUrl = "https://livelist01.yowi.tv/lista/57c98e2e295a0b69b52dc5f84edc4b1b68783ba2/master.m3u8",
                category = "Spain"
            ),
            TvChannel(
                id = 1093,
                name = "HQM Rock",
                logoUrl = "https://hqm.es/wp-content/uploads/rock-hqm-logo.png",
                streamUrl = "https://livelist01.yowi.tv/lista/0d6c7ccfac89946bfd41ae34c527e8d94734065c/master.m3u8",
                category = "Spain"
            ),
            TvChannel(
                id = 1094,
                name = "HQM Spanish",
                logoUrl = "https://hqm.es/wp-content/uploads/spanish-hqm-logo.png",
                streamUrl = "https://livelist01.yowi.tv/lista/8635ae40f8d1a32eccd63d1f58b55662c9c98f9f/master.m3u8",
                category = "Spain"
            ),
            TvChannel(
                id = 1095,
                name = "ATG Live Ⓢ",
                logoUrl = "https://i.imgur.com/bPWFXkL.png",
                streamUrl = "https://httpcache0-00688-cacheliveedge0.dna.qbrick.com/00688-cacheliveedge0/out/u/atg_sdi_1_free.m3u8",
                category = "Sweden,ATG"
            ),
            TvChannel(
                id = 1096,
                name = "Expressen TV",
                logoUrl = "https://i.imgur.com/8EjMSr7.png",
                streamUrl = "https://cdn0-03837-liveedge0.dna.ip-only.net/03837-liveedge0/smil:03837-tx2/playlist.m3u8",
                category = "Sweden,Expressen"
            ),
            TvChannel(
                id = 1097,
                name = "Kanal 10 Sverige",
                logoUrl = "https://i.imgur.com/vlh699v.png",
                streamUrl = "https://rrr.sz.xlcdn.com/?account=cn_kanal10media&file=live_transcoded&type=live&service=wowza&protocol=https&output=playlist.m3u8",
                category = "Sweden,Kanal"
            ),
            TvChannel(
                id = 1098,
                name = "Di TV",
                logoUrl = "https://i.imgur.com/zApTDWn.png",
                streamUrl = "https://cdn0-03837-liveedge0.dna.ip-only.net/03837-liveedge0/smil:03837-tx4/playlist.m3u8",
                category = "Sweden,Di"
            ),
            TvChannel(
                id = 1099,
                name = "Öppna Kanalen Stockholm Ⓢ",
                logoUrl = "https://i.imgur.com/GWlstv5.png",
                streamUrl = "https://edg03-prd-se-ixn.solidtango.com/edge/451iw2h/playlist.m3u8",
                category = "Sweden,Öppna"
            ),
            TvChannel(
                id = 1100,
                name = "Öppna Kanalen Malmö Ⓢ",
                logoUrl = "https://i.imgur.com/sjw8dsM.jpg",
                streamUrl = "https://edg01-prd-de-ixn.solidtango.com/edge/_8ynhbua3_/8ynhbua3/manifest.m3u8",
                category = "Sweden,Öppna"
            ),
            TvChannel(
                id = 1101,
                name = "Västmanlands TV",
                logoUrl = "https://i.imgur.com/EXBaQ88.jpg",
                streamUrl = "https://edg01-prd-se-dcs.solidtango.com/edge/lo9yf4l5/playlist.m3u8",
                category = "Sweden,Västmanlands"
            ),
            TvChannel(
                id = 1102,
                name = "Sundskanalen",
                logoUrl = "https://i.imgur.com/8uT0p3q.jpg",
                streamUrl = "https://stream.sundskanalen.se/live/view/index.m3u8",
                category = "Sweden,Sundskanalen"
            ),
            TvChannel(
                id = 1103,
                name = "Öppna Kanalen Skövde",
                logoUrl = "https://i.imgur.com/1LkYbaQ.png",
                streamUrl = "https://edg01-prd-de-ixn.solidtango.com/edge/_c6697zkv_/c6697zkv/manifest.m3u8",
                category = "Sweden,Öppna"
            ),
            TvChannel(
                id = 1104,
                name = "Aryen TV",
                logoUrl = "https://i.imgur.com/qUg7edz.png",
                streamUrl = "https://aryen.tv/live/tv/playlist.m3u8",
                category = "Sweden,Aryen"
            ),
            TvChannel(
                id = 1105,
                name = "Suryoyo Sat",
                logoUrl = "https://i.imgur.com/naCNjaB.png",
                streamUrl = "https://player-api.new.livestream.com/accounts/10187302/events/6785118/broadcasts/237816618.secure.m3u8",
                category = "Sweden,Suryoyo"
            ),
            TvChannel(
                id = 1106,
                name = "TVO",
                logoUrl = "https://i.imgur.com/5QFZ05B.png",
                streamUrl = "https://cdnapisec.kaltura.com/p/1719221/sp/171922100/playManifest/entryId/1_t5h46v64/format/applehttp/protocol/https/a.m3u8",
                category = "Switzerland,TVO"
            ),
            TvChannel(
                id = 1107,
                name = "TVM 3",
                logoUrl = "https://i.imgur.com/3v6iZE6.png",
                streamUrl = "http://livevideo.infomaniak.com/streaming/livecast/tvm3/playlist.m3u8",
                category = "Switzerland,TVM"
            ),
            TvChannel(
                id = 1108,
                name = "RSI La 1",
                logoUrl = "https://i.imgur.com/j8ogbli.png",
                streamUrl = "http://190.2.155.162/RSI1/index.m3u8",
                category = "Switzerland,RSI"
            ),
            TvChannel(
                id = 1109,
                name = "RSI La 2",
                logoUrl = "https://i.imgur.com/vm62h3t.png",
                streamUrl = "http://190.2.155.162/RSI2/index.m3u8",
                category = "Switzerland,RSI"
            ),
            TvChannel(
                id = 1110,
                name = "Teleticino",
                logoUrl = "https://i.imgur.com/zm2ruqz.png",
                streamUrl = "https://vstream-cdn.ch/hls/teleticino_720p/index.m3u8",
                category = "Switzerland,Teleticino"
            ),
            TvChannel(
                id = 1111,
                name = "TRT 1",
                logoUrl = "https://i.imgur.com/j786OLG.png",
                streamUrl = "https://tv-trt1.medya.trt.com.tr/master.m3u8",
                category = "Turkey,TRT"
            ),
            TvChannel(
                id = 1112,
                name = "TRT 2 Ⓖ",
                logoUrl = "https://i.imgur.com/lNWrOE2.png",
                streamUrl = "https://tv-trt2.medya.trt.com.tr/master.m3u8",
                category = "Turkey,TRT"
            ),
            TvChannel(
                id = 1113,
                name = "TRT Haber",
                logoUrl = "https://i.imgur.com/OVfo8Ab.png",
                streamUrl = "https://tv-trthaber.medya.trt.com.tr/master.m3u8",
                category = "Turkey,TRT"
            ),
            TvChannel(
                id = 1114,
                name = "TRT Spor Ⓖ",
                logoUrl = "https://i.imgur.com/N2wGZyf.png",
                streamUrl = "https://tv-trtspor1.medya.trt.com.tr/master.m3u8",
                category = "Turkey,TRT"
            ),
            TvChannel(
                id = 1115,
                name = "TRT Spor 2 Ⓖ",
                logoUrl = "https://i.imgur.com/ysKteM8.png",
                streamUrl = "https://tv-trtspor2.medya.trt.com.tr/master.m3u8",
                category = "Turkey,TRT"
            ),
            TvChannel(
                id = 1116,
                name = "TRT Çocuk",
                logoUrl = "https://i.imgur.com/QLFmD6d.png",
                streamUrl = "https://tv-trtcocuk.medya.trt.com.tr/master.m3u8",
                category = "Turkey,TRT"
            ),
            TvChannel(
                id = 1117,
                name = "TRT Müzik",
                logoUrl = "https://i.imgur.com/fIVFCEd.png",
                streamUrl = "https://tv-trtmuzik.medya.trt.com.tr/master.m3u8",
                category = "Turkey,TRT"
            ),
            TvChannel(
                id = 1118,
                name = "TRT Belgesel",
                logoUrl = "https://i.imgur.com/MGO87pe.png",
                streamUrl = "https://tv-trtbelgesel.medya.trt.com.tr/master.m3u8",
                category = "Turkey,TRT"
            ),
            TvChannel(
                id = 1119,
                name = "TRT Avaz",
                logoUrl = "https://i.imgur.com/VhTwXu5.png",
                streamUrl = "https://tv-trtavaz.medya.trt.com.tr/master.m3u8",
                category = "Turkey,TRT"
            ),
            TvChannel(
                id = 1120,
                name = "TRT Kurdî",
                logoUrl = "https://i.imgur.com/6BpymfB.png",
                streamUrl = "https://tv-trtkurdi.medya.trt.com.tr/master.m3u8",
                category = "Turkey,TRT"
            ),
            TvChannel(
                id = 1121,
                name = "TRT Arabi",
                logoUrl = "https://i.imgur.com/yyhWOZs.png",
                streamUrl = "https://tv-trtarabi.medya.trt.com.tr/master.m3u8",
                category = "Turkey,TRT"
            ),
            TvChannel(
                id = 1122,
                name = "TRT World",
                logoUrl = "https://i.imgur.com/JEA2xpv.png",
                streamUrl = "https://tv-trtworld.medya.trt.com.tr/master.m3u8",
                category = "Turkey,TRT"
            ),
            TvChannel(
                id = 1123,
                name = "TRT Türk",
                logoUrl = "https://i.imgur.com/OSTOQNw.png",
                streamUrl = "https://tv-trtturk.medya.trt.com.tr/master.m3u8",
                category = "Turkey,TRT"
            ),
            TvChannel(
                id = 1124,
                name = "TRT EBA Ilkokul",
                logoUrl = "https://i.imgur.com/wDvZfk8.png",
                streamUrl = "https://tv-e-okul00.medya.trt.com.tr/master.m3u8",
                category = "Turkey,TRT"
            ),
            TvChannel(
                id = 1125,
                name = "TRT EBA Ortaokul",
                logoUrl = "https://i.imgur.com/yfPTvRx.png",
                streamUrl = "https://tv-e-okul01.medya.trt.com.tr/master.m3u8",
                category = "Turkey,TRT"
            ),
            TvChannel(
                id = 1126,
                name = "TRT EBA Lise",
                logoUrl = "https://i.imgur.com/IebUZx1.png",
                streamUrl = "https://tv-e-okul02.medya.trt.com.tr/master.m3u8",
                category = "Turkey,TRT"
            ),
            TvChannel(
                id = 1127,
                name = "BBC One Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/BBC_One_logo_2021.svg/640px-BBC_One_logo_2021.svg.png",
                streamUrl = "https://vs-hls-pushb-uk-live.akamaized.net/x=4/i=urn:bbc:pips:service:bbc_one_yorks/iptv_hd_abr_v1.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1128,
                name = "BBC Two Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/15/BBC_Two_logo_2021.svg/640px-BBC_Two_logo_2021.svg.png",
                streamUrl = "https://vs-hls-push-uk-live.akamaized.net/x=4/i=urn:bbc:pips:service:bbc_two_hd/iptv_hd_abr_v1.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1129,
                name = "Channel 4 Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/9/9b/Channel_4_%28On_Demand%29_2023.svg/569px-Channel_4_%28On_Demand%29_2023.svg.png",
                streamUrl = "http://176.65.146.105:8011/play/a07w/index.m3u8",
                category = "UK,Channel"
            ),
            TvChannel(
                id = 1130,
                name = "S4C Ⓖ",
                logoUrl = "https://i.imgur.com/vrcbnBv.png",
                streamUrl = "https://live-uk.s4c-cdn.co.uk/out/v1/a0134f1fd5a2461b9422b574566d4442/live_uk.m3u8",
                category = "UK,S4C"
            ),
            TvChannel(
                id = 1131,
                name = "BBC Alba Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e3/BBC_Alba_2021.svg/640px-BBC_Alba_2021.svg.png",
                streamUrl = "https://vs-hls-pushb-uk-live.akamaized.net/x=4/i=urn:bbc:pips:service:bbc_alba/iptv_hd_abr_v1.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1132,
                name = "BBC Four Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/BBC_Four_logo_2021.svg/640px-BBC_Four_logo_2021.svg.png",
                streamUrl = "https://vs-hls-pushb-uk-live.akamaized.net/x=4/i=urn:bbc:pips:service:bbc_four_hd/iptv_hd_abr_v1.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1133,
                name = "BBC Scotland Ⓢ Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/BBC_Scotland_2021_%28channel%29.svg/640px-BBC_Scotland_2021_%28channel%29.svg.png",
                streamUrl = "https://vs-hls-pushb-uk-live.akamaized.net/x=4/i=urn:bbc:pips:service:bbc_scotland_hd/pc_hd_abr_v2.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1134,
                name = "E4 Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/0/06/E4_logo_2018.svg/552px-E4_logo_2018.svg.png",
                streamUrl = "http://176.65.146.105:8011/play/E4HD/index.m3u8",
                category = "UK,E4"
            ),
            TvChannel(
                id = 1135,
                name = "Film4 Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/5/53/Film4_logo_2018.svg/805px-Film4_logo_2018.svg.png",
                streamUrl = "http://176.65.146.105:8011/play/FILM4SD/index.m3u8",
                category = "UK,Film4"
            ),
            TvChannel(
                id = 1136,
                name = "QVC UK Ⓢ",
                logoUrl = "https://i.imgur.com/6TWUVrh.png",
                streamUrl = "https://qvcuk-live.akamaized.net/hls/live/2097112/qvc/3/3.m3u8",
                category = "UK,QVC"
            ),
            TvChannel(
                id = 1137,
                name = "TJC",
                logoUrl = "https://i.imgur.com/fk5rEje.png",
                streamUrl = "https://cdn-shop-lc-01.akamaized.net/Content/HLS_HLS/Live/channel(TJCOTT)/index.m3u8",
                category = "UK,TJC"
            ),
            TvChannel(
                id = 1138,
                name = "BBC Three Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/76/BBC_Three_2022.svg/640px-BBC_Three_2022.svg.png",
                streamUrl = "https://vs-hls-pushb-uk-live.akamaized.net/x=4/i=urn:bbc:pips:service:bbc_three_hd/iptv_hd_abr_v1.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1139,
                name = "GemsTV Ⓢ",
                logoUrl = "https://i.imgur.com/IR2sTag.png",
                streamUrl = "http://57d6b85685bb8.streamlock.net:1935/abrgemporiaukgfx/livestream_360p/index.m3u8",
                category = "UK,GemsTV"
            ),
            TvChannel(
                id = 1140,
                name = "4seven Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/5/5e/4seven_logo_2018.svg/568px-4seven_logo_2018.svg.png",
                streamUrl = "http://176.65.146.170:8013/play/a06p/index.m3u8",
                category = "UK,4seven"
            ),
            TvChannel(
                id = 1141,
                name = "Ideal World",
                logoUrl = "https://i.imgur.com/su6GH7i.png",
                streamUrl = "https://ythls.armelin.one/channel/UCJbgGTpBWuC87VFIKTTO4RQ.m3u8",
                category = "UK,Ideal"
            ),
            TvChannel(
                id = 1142,
                name = "Blaze Ⓖ",
                logoUrl = "https://i.imgur.com/6UcPWP9.png",
                streamUrl = "https://live.blaze.tv/live7/blaze/bitrate1.isml/live.m3u8",
                category = "UK,Blaze"
            ),
            TvChannel(
                id = 1143,
                name = "Jewellery Maker",
                logoUrl = "https://i.imgur.com/O7SdkBh.png",
                streamUrl = "https://lo2-1.gemporia.com/abrjewellerymaker/smil:livestream.smil/playlist.m3u8",
                category = "UK,Jewellery"
            ),
            TvChannel(
                id = 1144,
                name = "Hobby Maker",
                logoUrl = "https://i.imgur.com/VWHp5Tl.png",
                streamUrl = "https://lo2-1.gemporia.com/abrhobbymakerukgfx/smil:livestreamFullHD.smil/playlist.m3u8",
                category = "UK,Hobby"
            ),
            TvChannel(
                id = 1145,
                name = "PBS America",
                logoUrl = "https://i.imgur.com/J4zE5z9.jpg",
                streamUrl = "https://pbs-samsunguk.amagi.tv/playlist.m3u8",
                category = "UK,PBS"
            ),
            TvChannel(
                id = 1146,
                name = "Create & Craft",
                logoUrl = "https://i.imgur.com/n65sk4L.png",
                streamUrl = "https://live-hochanda.simplestreamcdn.com/live2/hochanda/bitrate1.isml/live.m3u8",
                category = "UK,Create"
            ),
            TvChannel(
                id = 1147,
                name = "CBBC Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/35/CBBC_%282023%29.svg/640px-CBBC_%282023%29.svg.png",
                streamUrl = "https://vs-hls-pushb-uk-live.akamaized.net/x=4/i=urn:bbc:pips:service:cbbc_hd/t=3840/v=pv14/b=5070016/main.m3u8",
                category = "UK,CBBC"
            ),
            TvChannel(
                id = 1148,
                name = "CBeebies Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b5/CBeebies_2023.svg/640px-CBeebies_2023.svg.png",
                streamUrl = "https://vs-hls-pushb-uk-live.akamaized.net/x=4/i=urn:bbc:pips:service:cbeebies_hd/t=3840/v=pv14/b=5070016/main.m3u8",
                category = "UK,CBeebies"
            ),
            TvChannel(
                id = 1149,
                name = "BBC Parliament Ⓢ Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/99/BBC_Parliament_2022.svg/640px-BBC_Parliament_2022.svg.png",
                streamUrl = "https://vs-hls-pushb-uk-live.akamaized.net/x=4/i=urn:bbc:pips:service:bbc_parliament/pc_hd_abr_v2.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1150,
                name = "Sky News",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/5/57/Sky_News_logo.svg/1024px-Sky_News_logo.svg.png",
                streamUrl = "https://linear021-gb-hls1-prd-ak.cdn.skycdp.com/Content/HLS_001_hd/Live/channel(skynews)/index_mob.m3u8",
                category = "UK,Sky"
            ),
            TvChannel(
                id = 1151,
                name = "GB News",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/3/35/GB_News_Logo.svg/640px-GB_News_Logo.svg.png",
                streamUrl = "https://live-gbnews.simplestreamcdn.com/live5/gbnews/bitrate1.isml/manifest.m3u8",
                category = "UK,GB"
            ),
            TvChannel(
                id = 1152,
                name = "TalkTV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/8/83/TalkTV_logo.png",
                streamUrl = "https://live-talktv-ssai.simplestreamcdn.com/v1/master/82267e84b9e5053b3fd0ade12cb1a146df74169a/talktv-live/index.m3u8",
                category = "UK,TalkTV"
            ),
            TvChannel(
                id = 1153,
                name = "Arise News",
                logoUrl = "https://i.imgur.com/B5IXKIb.png",
                streamUrl = "https://liveedge-arisenews.visioncdn.com/live-hls/arisenews/arisenews/arisenews_web/master.m3u8",
                category = "UK,Arise"
            ),
            TvChannel(
                id = 1154,
                name = "France 24",
                logoUrl = "https://i.imgur.com/61MSiq9.png",
                streamUrl = "https://ythls.armelin.one/channel/UCQfwfsi5VrQ8yKZ-UWmAEFg.m3u8",
                category = "UK,France"
            ),
            TvChannel(
                id = 1155,
                name = "Bloomberg TV",
                logoUrl = "https://d2n0069hmnqmmx.cloudfront.net/epgdata/1.0/newchanlogos/512/512/skychb1074.png",
                streamUrl = "https://bloomberg.com/media-manifest/streams/eu.m3u8",
                category = "UK,Bloomberg"
            ),
            TvChannel(
                id = 1156,
                name = "NHK World Japan",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/NHK_World-Japan_TV.svg/512px-NHK_World-Japan_TV.svg.png",
                streamUrl = "https://nhkwlive-ojp.akamaized.net/hls/live/2003459/nhkwlive-ojp-en/index_4M.m3u8",
                category = "UK,NHK"
            ),
            TvChannel(
                id = 1157,
                name = "Arirang World",
                logoUrl = "https://raw.githubusercontent.com/tv-logo/tv-logos/67cfa9368d2d135744732a3aed3baecb3fadcf13/countries/international/arirang-int.png",
                streamUrl = "http://amdlive.ctnd.com.edgesuite.net/arirang_1ch/smil:arirang_1ch.smil/chunklist_b2256000_sleng.m3u8",
                category = "UK,Arirang"
            ),
            TvChannel(
                id = 1158,
                name = "TRT World",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/27/TRT_World.svg/512px-TRT_World.svg.png",
                streamUrl = "https://api.trtworld.com/livestream/v1/WcM3Oa2LHD9iUjWDSRUI335NkMWVTUV351H56dqC/master.m3u8",
                category = "UK,TRT"
            ),
            TvChannel(
                id = 1159,
                name = "SportyStuff TV",
                logoUrl = "https://i.imgur.com/uIgxHSY.png",
                streamUrl = "https://cdn.rtmp1.vodhosting.com/hls/SportyStuffTV.m3u8",
                category = "UK,SportyStuff"
            ),
            TvChannel(
                id = 1160,
                name = "QVC Beauty Ⓢ",
                logoUrl = "https://i.imgur.com/ZBHtqk1.png",
                streamUrl = "https://qvcuk-live.akamaized.net/hls/live/2097112/qby/3/3.m3u8",
                category = "UK,QVC"
            ),
            TvChannel(
                id = 1161,
                name = "QVC Extra Ⓢ",
                logoUrl = "https://i.imgur.com/TIe5T9Z.png",
                streamUrl = "https://qvcuk-live.akamaized.net/hls/live/2097112/qex/3/3.m3u8",
                category = "UK,QVC"
            ),
            TvChannel(
                id = 1162,
                name = "QVC Style Ⓢ",
                logoUrl = "https://i.imgur.com/6HZlLL3.png",
                streamUrl = "https://qvcuk-live.akamaized.net/hls/live/2097112/qst/3/3.m3u8",
                category = "UK,QVC"
            ),
            TvChannel(
                id = 1163,
                name = "Now 70s",
                logoUrl = "https://i.imgur.com/qiCCX5X.png",
                streamUrl = "https://lightning-now70s-samsungnz.amagi.tv/playlist.m3u8",
                category = "UK,Now"
            ),
            TvChannel(
                id = 1164,
                name = "Now 80s",
                logoUrl = "https://i.imgur.com/8paz37m.png",
                streamUrl = "https://lightning-now80s-samsunguk.amagi.tv/playlist.m3u8",
                category = "UK,Now"
            ),
            TvChannel(
                id = 1165,
                name = "Now Rock",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/8/89/NOW_Rock_logo.png",
                streamUrl = "https://lightning-now90s-samsungnz.amagi.tv/playlist.m3u8",
                category = "UK,Now"
            ),
            TvChannel(
                id = 1166,
                name = "BBC World News Ⓢ",
                logoUrl = "https://i.imgur.com/joD38lo.png",
                streamUrl = "http://ott-cdn.ucom.am/s24/index.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1167,
                name = "BBC Radio 1",
                logoUrl = "https://experiencersinternational.github.io/tvsetup/tvg-ico/bbcrd1-epg.png",
                streamUrl = "http://as-hls-ww-live.akamaized.net/pool_904/live/ww/bbc_radio_one/bbc_radio_one.isml/bbc_radio_one-audio%3d96000.norewind.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1168,
                name = "BBC Radio 2",
                logoUrl = "https://experiencersinternational.github.io/tvsetup/tvg-ico/bbcrd2-epg.png",
                streamUrl = "http://as-hls-ww-live.akamaized.net/pool_904/live/ww/bbc_radio_two/bbc_radio_two.isml/bbc_radio_two-audio%3d96000.norewind.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1169,
                name = "BBC Radio 3",
                logoUrl = "https://experiencersinternational.github.io/tvsetup/tvg-ico/bbcrd3-epg.png",
                streamUrl = "http://as-hls-ww-live.akamaized.net/pool_904/live/ww/bbc_radio_three/bbc_radio_three.isml/bbc_radio_three-audio%3d96000.norewind.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1170,
                name = "BBC Radio 4",
                logoUrl = "https://experiencersinternational.github.io/tvsetup/tvg-ico/bbcrd4-epg.png",
                streamUrl = "http://as-hls-ww-live.akamaized.net/pool_904/live/ww/bbc_radio_fourfm/bbc_radio_fourfm.isml/bbc_radio_fourfm-audio%3d96000.norewind.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1171,
                name = "BBC Radio 5 Live",
                logoUrl = "https://experiencersinternational.github.io/tvsetup/tvg-ico/bbcrd5l-epg.png",
                streamUrl = "http://as-hls-ww-live.akamaized.net/pool_904/live/ww/bbc_radio_five_live/bbc_radio_five_live.isml/bbc_radio_five_live-audio%3d96000.norewind.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1172,
                name = "BBC Radio 6 Music",
                logoUrl = "https://experiencersinternational.github.io/tvsetup/tvg-ico/bbcrd6-epg.png",
                streamUrl = "http://as-hls-ww-live.akamaized.net/pool_904/live/ww/bbc_6music/bbc_6music.isml/bbc_6music-audio%3d96000.norewind.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1173,
                name = "BBC Radio 1Xtra",
                logoUrl = "https://experiencersinternational.github.io/tvsetup/tvg-ico/bbcrd1x-epg.png",
                streamUrl = "http://as-hls-ww-live.akamaized.net/pool_904/live/ww/bbc_1xtra/bbc_1xtra.isml/bbc_1xtra-audio%3d96000.norewind.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1174,
                name = "BBC Radio 4 Extra",
                logoUrl = "https://experiencersinternational.github.io/tvsetup/tvg-ico/bbcrd4x-epg.png",
                streamUrl = "http://as-hls-ww-live.akamaized.net/pool_904/live/ww/bbc_radio_four_extra/bbc_radio_four_extra.isml/bbc_radio_four_extra-audio%3d96000.norewind.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1175,
                name = "BBC Radio 5 Sports Extra",
                logoUrl = "https://experiencersinternational.github.io/tvsetup/tvg-ico/bbcrd5s-epg.png",
                streamUrl = "http://as-hls-uk-live.akamaized.net/pool_904/live/uk/bbc_radio_five_live_sports_extra/bbc_radio_five_live_sports_extra.isml/bbc_radio_five_live_sports_extra-audio%3d96000.norewind.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1176,
                name = "BBC Asian Network",
                logoUrl = "https://experiencersinternational.github.io/tvsetup/tvg-ico/bbcasiannet-epg.png",
                streamUrl = "http://as-hls-ww-live.akamaized.net/pool_904/live/ww/bbc_asian_network/bbc_asian_network.isml/bbc_asian_network-audio%3d96000.norewind.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1177,
                name = "BBC World Service",
                logoUrl = "https://experiencersinternational.github.io/tvsetup/tvg-ico/bbcws-epg.png",
                streamUrl = "http://a.files.bbci.co.uk/media/live/manifesto/audio/simulcast/hls/nonuk/sbr_low/ak/bbc_world_service.m3u8",
                category = "UK,BBC"
            ),
            TvChannel(
                id = 1178,
                name = "Pershyi",
                logoUrl = "https://i.imgur.com/osTQLED.png",
                streamUrl = "https://ext.cdn.nashnet.tv/228.0.2.45/index.m3u8",
                category = "Ukraine,Pershyi"
            ),
            TvChannel(
                id = 1179,
                name = "Suspilne Kultura",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/27/Suspilne_Kultura_%282022%29.svg/640px-Suspilne_Kultura_%282022%29.svg.png",
                streamUrl = "https://ext.cdn.nashnet.tv/228.0.0.141/index.m3u8",
                category = "Ukraine,Suspilne"
            ),
            TvChannel(
                id = 1180,
                name = "Suspilne Sport",
                logoUrl = "https://i.imgur.com/16IhU0M.png",
                streamUrl = "http://cdnua05.hls.tv/934/hls/8743361621b245838bee193c9ec28322/4856/stream.m3u8",
                category = "Ukraine,Suspilne"
            ),
            TvChannel(
                id = 1181,
                name = "ICTV2",
                logoUrl = "https://i.imgur.com/J4zlRGv.png",
                streamUrl = "http://cdnua05.hls.tv/919/hls/8743361621b245838bee193c9ec28322/4835/stream.m3u8",
                category = "Ukraine,ICTV2"
            ),
            TvChannel(
                id = 1182,
                name = "ICTV Serialy",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/7/7a/ICTV_Serialy_%282024%29.png",
                streamUrl = "http://cdnua05.hls.tv/624/hls/f62d71219200da4130d13b21d23fb23c/4896/stream.m3u8",
                category = "Ukraine,ICTV"
            ),
            TvChannel(
                id = 1183,
                name = "M2",
                logoUrl = "https://i.imgur.com/AfcBWCg.png",
                streamUrl = "https://live.m2.tv/hls3/stream.m3u8",
                category = "Ukraine,M2"
            ),
            TvChannel(
                id = 1184,
                name = "NTN",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d1/NTNUA_logo_%282013%29.svg/640px-NTNUA_logo_%282013%29.svg.png",
                streamUrl = "https://edge3.iptv.macc.com.ua/img/ntn_3/index.m3u8",
                category = "Ukraine,NTN"
            ),
            TvChannel(
                id = 1185,
                name = "XSport Ⓢ",
                logoUrl = "https://i.imgur.com/CHDcfrT.png",
                streamUrl = "http://cdnua05.hls.tv/946/hls/8743361621b245838bee193c9ec28322/3999/stream.m3u8",
                category = "Ukraine,XSport"
            ),
            TvChannel(
                id = 1186,
                name = "Суспільне Київ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Suspilne_Kyiv_%282022%29.svg/640px-Suspilne_Kyiv_%282022%29.svg.png",
                streamUrl = "https://ext.cdn.nashnet.tv/228.0.0.41/index.m3u8",
                category = "Ukraine,Суспільне"
            ),
            TvChannel(
                id = 1187,
                name = "Суспільне Дніпро",
                logoUrl = "https://i.imgur.com/nMHd9FK.png",
                streamUrl = "http://cdnua05.hls.tv/648/hls/f62d71219200da4130d13b21d23fb23c/4786/stream.m3u8",
                category = "Ukraine,Суспільне"
            ),
            TvChannel(
                id = 1188,
                name = "Суспільне Житомир Ⓢ",
                logoUrl = "https://i.imgur.com/lCv1Xaq.png",
                streamUrl = "http://cdnua05.hls.tv/647/hls/f62d71219200da4130d13b21d23fb23c/4787/stream.m3u8",
                category = "Ukraine,Суспільне"
            ),
            TvChannel(
                id = 1189,
                name = "Суспільне Одеса Ⓢ",
                logoUrl = "https://i.imgur.com/giTdUK9.png",
                streamUrl = "http://cdnua05.hls.tv/653/hls/f62d71219200da4130d13b21d23fb23c/4833/stream.m3u8",
                category = "Ukraine,Суспільне"
            ),
            TvChannel(
                id = 1190,
                name = "Суспільне Суми Ⓢ",
                logoUrl = "https://i.imgur.com/U5GQiUz.png",
                streamUrl = "http://cdnua05.hls.tv/630/hls/f62d71219200da4130d13b21d23fb23c/4798/stream.m3u8",
                category = "Ukraine,Суспільне"
            ),
            TvChannel(
                id = 1191,
                name = "Суспільне Хмельницький Ⓢ",
                logoUrl = "https://i.imgur.com/uoTcTbU.png",
                streamUrl = "http://cdnua05.hls.tv/632/hls/f62d71219200da4130d13b21d23fb23c/4800/stream.m3u8",
                category = "Ukraine,Суспільне"
            ),
            TvChannel(
                id = 1192,
                name = "Суспільне Чернівці Ⓢ",
                logoUrl = "https://i.imgur.com/mzYRGp2.png",
                streamUrl = "http://cdnua05.hls.tv/643/hls/f62d71219200da4130d13b21d23fb23c/4790/stream.m3u8",
                category = "Ukraine,Суспільне"
            ),
            TvChannel(
                id = 1193,
                name = "Суспільне Kropyvnytskyi",
                logoUrl = "https://i.imgur.com/zzYJr87.png",
                streamUrl = "http://cdnua05.hls.tv/650/hls/f62d71219200da4130d13b21d23fb23c/4788/stream.m3u8",
                category = "Ukraine,Суспільне"
            ),
            TvChannel(
                id = 1194,
                name = "Суспільне Луцьк",
                logoUrl = "https://i.imgur.com/QIin5nZ.png",
                streamUrl = "http://193.107.168.98:7006/play/508/index.m3u8",
                category = "Ukraine,Суспільне"
            ),
            TvChannel(
                id = 1195,
                name = "Суспільне Крим",
                logoUrl = "https://i.imgur.com/m7znCes.png",
                streamUrl = "https://ext.cdn.nashnet.tv/228.0.0.71/index.m3u8",
                category = "Ukraine,Суспільне"
            ),
            TvChannel(
                id = 1196,
                name = "Суспільне Тернопіль",
                logoUrl = "https://i.imgur.com/rxXyCY7.png",
                streamUrl = "http://cdnua05.hls.tv/647/hls/f62d71219200da4130d13b21d23fb23c/4789/stream.m3u8",
                category = "Ukraine,Суспільне"
            ),
            TvChannel(
                id = 1197,
                name = "Суспільне Чернігів",
                logoUrl = "https://i.imgur.com/WBChVXj.png",
                streamUrl = "http://cdnua05.hls.tv/645/hls/f62d71219200da4130d13b21d23fb23c/4791/stream.m3u8",
                category = "Ukraine,Суспільне"
            ),
            TvChannel(
                id = 1198,
                name = "FREEДOM",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f2/FreeDOMUAlogo.svg/512px-FreeDOMUAlogo.svg.png",
                streamUrl = "http://95.67.106.242/mobile-app/main/freedom/master.m3u8",
                category = "Ukraine,FREEДOM"
            ),
            TvChannel(
                id = 1199,
                name = "24 Kanal",
                logoUrl = "https://pbs.twimg.com/profile_images/1498285886714298374/EMSJzC-0_400x400.jpg",
                streamUrl = "http://streamvideol1.luxnet.ua/news24/smil:news24.stream.smil/playlist.m3u8",
                category = "Ukraine,24"
            ),
            TvChannel(
                id = 1200,
                name = "Дніпро TV",
                logoUrl = "https://i.imgur.com/mbPTVh1.png",
                streamUrl = "http://vcdn1.produck.company:1935/out/dtv/playlist.m3u8",
                category = "Ukraine,Дніпро"
            ),
            TvChannel(
                id = 1201,
                name = "ГІТ",
                logoUrl = "https://i.imgur.com/b5K5Uwh.png",
                streamUrl = "https://stream.uagit.tv/gittv.m3u8",
                category = "Ukraine,ГІТ"
            ),
            TvChannel(
                id = 1202,
                name = "Telekanal RAI",
                logoUrl = "https://i.imgur.com/Ouv51WB.png",
                streamUrl = "https://stream.rai.ua/rai/stream.m3u8",
                category = "Ukraine,Telekanal"
            ),
            TvChannel(
                id = 1203,
                name = "Тернопіль 1",
                logoUrl = "https://i.imgur.com/f5EtIzV.png",
                streamUrl = "https://ott.columbus.te.ua/ternopil1/index.m3u8",
                category = "Ukraine,Тернопіль"
            ),
            TvChannel(
                id = 1204,
                name = "CK 1",
                logoUrl = "https://i.imgur.com/XiXwr5h.png",
                streamUrl = "http://cdn10.live-tv.od.ua:8081/sk1zt/sk1zt-abr/playlist.m3u8",
                category = "Ukraine,CK"
            ),
            TvChannel(
                id = 1205,
                name = "Pervyy gorodskoy Odessa",
                logoUrl = "https://i.imgur.com/8qdc6aO.png",
                streamUrl = "https://live.1tv.od.ua/stream1/channel1/playlist.m3u8",
                category = "Ukraine,Pervyy"
            ),
            TvChannel(
                id = 1206,
                name = "Київ 24",
                logoUrl = "https://i.imgur.com/TVuCY4N.png",
                streamUrl = "https://ext.cdn.nashnet.tv/228.0.0.15/index.m3u8",
                category = "Ukraine,Київ"
            ),
            TvChannel(
                id = 1207,
                name = "ITV",
                logoUrl = "https://i.imgur.com/NZdT9yJ.png",
                streamUrl = "http://cdn10.live-tv.od.ua:8081/itvrv/abr/playlist.m3u8",
                category = "Ukraine,ITV"
            ),
            TvChannel(
                id = 1208,
                name = "NTK TV",
                logoUrl = "https://www.ntktv.ua/bitrix/templates/ntk_copy/images/logo.png",
                streamUrl = "http://stream.ntktv.ua/s/ntk/ntk.m3u8",
                category = "Ukraine,NTK"
            ),
            TvChannel(
                id = 1209,
                name = "TVA",
                logoUrl = "https://i.imgur.com/PtTQ5lv.png",
                streamUrl = "https://hls.cdn.ua/tva.ua_live/livestream/chunklist_.m3u8",
                category = "Ukraine,TVA"
            ),
            TvChannel(
                id = 1210,
                name = "TV7+",
                logoUrl = "https://i.imgur.com/PcTDZ8e.png",
                streamUrl = "https://tv7plus.com/hls/tv7_site.m3u8",
                category = "Ukraine,TV7+"
            ),
            TvChannel(
                id = 1211,
                name = "Cartoon Network Arabic",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/b/bb/Cartoon_Network_Arabic_logo.png",
                streamUrl = "https://shls-cartoon-net-prod-dub.shahid.net/out/v1/dc4aa87372374325a66be458f29eab0f/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1212,
                name = "Al Arabiya Business",
                logoUrl = "https://i.imgur.com/eEV4r6J.jpg",
                streamUrl = "https://live.alarabiya.net/alarabiapublish/aswaaq.smil/playlist.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1213,
                name = "MBC 1",
                logoUrl = "https://i.imgur.com/CiA3plN.png",
                streamUrl = "https://mbc1-enc.edgenextcdn.net/out/v1/0965e4d7deae49179172426cbfb3bc5e/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1214,
                name = "MBC 2 Ⓢ",
                logoUrl = "https://i.imgur.com/n9mSHuP.png",
                streamUrl = "http://37.122.156.107:4000/play/a07g/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1215,
                name = "MBC 3",
                logoUrl = "https://i.imgur.com/PVt8OPN.png",
                streamUrl = "https://shls-mbc3-prod-dub.shahid.net/out/v1/d5bbe570e1514d3d9a142657d33d85e6/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1216,
                name = "MBC 4",
                logoUrl = "https://i.imgur.com/BcXASJp.png",
                streamUrl = "https://mbc4-prod-dub-ak.akamaized.net/out/v1/c08681f81775496ab4afa2bac7ae7638/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1217,
                name = "MBC 5",
                logoUrl = "https://i.imgur.com/fRWaDyF.png",
                streamUrl = "https://shls-mbc5-prod-dub.shahid.net/out/v1/2720564b6a4641658fdfb6884b160da2/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1218,
                name = "MBC Action Ⓢ",
                logoUrl = "https://i.imgur.com/OWZAghw.png",
                streamUrl = "http://37.122.156.107:4000/play/a07h/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1219,
                name = "MBC Bollywood",
                logoUrl = "https://i.imgur.com/TTAGFHG.png",
                streamUrl = "https://shls-mbcbollywood-prod-dub.shahid.net/out/v1/a79c9d7ef2a64a54a64d5c4567b3462a/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1220,
                name = "MBC Drama",
                logoUrl = "https://i.imgur.com/g5PWnqp.png",
                streamUrl = "https://mbc1-enc.edgenextcdn.net/out/v1/b0b3a0e6750d4408bb86d703d5feffd1/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1221,
                name = "MBC Max Ⓢ",
                logoUrl = "https://i.imgur.com/A02CptP.png",
                streamUrl = "http://37.122.156.107:4000/play/a07i/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1222,
                name = "MBC Persia",
                logoUrl = "https://i.imgur.com/4FXiyjn.png",
                streamUrl = "https://shls-mbcpersia-prod-dub.shahid.net/out/v1/bdc7cd0d990e4c54808632a52c396946/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1223,
                name = "Wanasah",
                logoUrl = "https://i.imgur.com/nLtiXNf.png",
                streamUrl = "https://shls-wanasah-prod-dub.shahid.net/out/v1/c84ef3128e564b74a6a796e8b6287de6/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1224,
                name = "Sky News Arabia",
                logoUrl = "https://i.imgur.com/SvjU4h6.png",
                streamUrl = "https://stream.skynewsarabia.com/hls/sna.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1225,
                name = "Baynounah TV",
                logoUrl = "https://static.wikia.nocookie.net/logopedia/images/6/60/Baynounah_tv_2023.png",
                streamUrl = "https://vo-live.cdb.cdn.orange.com/Content/Channel/Baynounah/HLS/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1226,
                name = "Ajman TV",
                logoUrl = "https://pbs.twimg.com/profile_images/1085187553563561990/KRKuK_iW_400x400.jpg",
                streamUrl = "https://dacastmmd.mmdlive.lldns.net/dacastmmd/8eb0e912b49142d7a01d779c9374aba9/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1227,
                name = "Al Aan TV",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/7/76/Al_Aan_TV_new_Logo.png",
                streamUrl = "https://shls-live-ak.akamaized.net/out/v1/dfbdea4c1bf149629764e58c6ff314c8/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1228,
                name = "Abu Dhabi TV",
                logoUrl = "https://i.imgur.com/7cNke07.png",
                streamUrl = "http://admdn2.cdn.mangomolo.com/adtv/smil:adtv.stream.smil/chunklist.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1229,
                name = "Abu Dhabi Sports 1",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7a/Abu_Dhabi_Sports_logo_2023.svg/2560px-Abu_Dhabi_Sports_logo_2023.svg.png",
                streamUrl = "https://vo-live.cdb.cdn.orange.com/Content/Channel/AbuDhabiSportsChannel1/HLS/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1230,
                name = "Abu Dhabi Sports 2",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7a/Abu_Dhabi_Sports_logo_2023.svg/2560px-Abu_Dhabi_Sports_logo_2023.svg.png",
                streamUrl = "https://vo-live.cdb.cdn.orange.com/Content/Channel/AbuDhabiSportsChannel2/HLS/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1231,
                name = "National Geographic Abu Dhabi",
                logoUrl = "https://i.imgur.com/fNA00VF.png",
                streamUrl = "https://admdn2.cdn.mangomolo.com/nagtv/smil:nagtv.stream.smil/playlist.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1232,
                name = "Ajman TV",
                logoUrl = "https://www.lyngsat.com/logo/tv/aa/ajman-tv-ae.png",
                streamUrl = "https://dacastmmd.mmdlive.lldns.net/dacastmmd/8eb0e912b49142d7a01d779c9374aba9/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1233,
                name = "Dubai TV",
                logoUrl = "https://i.imgur.com/wZMkKF7.png",
                streamUrl = "https://dmisxthvll.cdn.mgmlcdn.com/dubaitvht/smil:dubaitv.stream.smil/playlist.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1234,
                name = "Dubai One",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/7/7d/Dubaione-logo.png",
                streamUrl = "https://dminnvll.cdn.mangomolo.com/dubaione/smil:dubaione.stream.smil/playlist.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1235,
                name = "Dubai Sports 1",
                logoUrl = "https://www.lyngsat.com/logo/tv/dd/dubai-sports-ae.png",
                streamUrl = "https://dmitnthfr.cdn.mgmlcdn.com/dubaisports/smil:dubaisports.stream.smil/chunklist.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1236,
                name = "Dubai Sports 2",
                logoUrl = "https://www.lyngsat.com/logo/tv/dd/dubai-sports-ae.png",
                streamUrl = "https://dmitwlvvll.cdn.mangomolo.com/dubaisportshd/smil:dubaisportshd.smil/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1237,
                name = "Dubai Sports 3",
                logoUrl = "https://www.lyngsat.com/logo/tv/dd/dubai-sports-ae.png",
                streamUrl = "https://dmitwlvvll.cdn.mangomolo.com/dubaisportshd5/smil:dubaisportshd5.smil/index.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1238,
                name = "Dubai Racing 1",
                logoUrl = "https://www.lyngsat.com/logo/tv/dd/dubai-racing-ae.png",
                streamUrl = "https://dmisvthvll.cdn.mgmlcdn.com/events/smil:events.stream.smil/playlist.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1239,
                name = "Dubai Racing 2",
                logoUrl = "https://www.lyngsat.com/logo/tv/dd/dubai-racing-ae.png",
                streamUrl = "https://dmithrvll.cdn.mangomolo.com/dubairacing/smil:dubairacing.smil/playlist.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1240,
                name = "Dubai Racing 3",
                logoUrl = "https://www.lyngsat.com/logo/tv/dd/dubai-racing-ae.png",
                streamUrl = "https://dmithrvll.cdn.mangomolo.com/dubaimubasher/smil:dubaimubasher.smil/playlist.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1241,
                name = "Dubai Zaman",
                logoUrl = "https://www.lyngsat.com/logo/tv/dd/dubai-zaman-ae.png",
                streamUrl = "https://dmiffthvll.cdn.mangomolo.com/dubaizaman/smil:dubaizaman.stream.smil/playlist.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1242,
                name = "Sama Dubai",
                logoUrl = "https://i.imgur.com/bF6I3N1.jpg",
                streamUrl = "https://dmieigthvll.cdn.mgmlcdn.com/samadubaiht/smil:samadubai.stream.smil/playlist.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1243,
                name = "Noor Dubai",
                logoUrl = "https://i.imgur.com/DLe7ZuM.png",
                streamUrl = "https://dmiffthvll.cdn.mangomolo.com/noordubaitv/smil:noordubaitv.smil/playlist.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1244,
                name = "Sharjah TV",
                logoUrl = "https://www.lyngsat.com/logo/tv/ss/sharjah-tv-ae.png",
                streamUrl = "https://svs.itworkscdn.net/smc1live/smc1.smil/playlist.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1245,
                name = "Sharjah Sports",
                logoUrl = "https://i.imgur.com/IaRaabJ.jpg",
                streamUrl = "https://svs.itworkscdn.net/smc4sportslive/smc4.smil/playlist.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1246,
                name = "Al Wousta",
                logoUrl = "https://i5.satexpat.com/cha/ae/al-wousta-95x90.gif",
                streamUrl = "https://svs.itworkscdn.net/alwoustalive/alwoustatv.smil/playlist.m3u8",
                category = "United"
            ),
            TvChannel(
                id = 1247,
                name = "Buzzr Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d6/Buzzr_logo.svg/768px-Buzzr_logo.svg.png",
                streamUrl = "https://buzzrota-ono.amagi.tv/playlist1080.m3u8",
                category = "USA,Buzzr"
            ),
            TvChannel(
                id = 1248,
                name = "Retro TV",
                logoUrl = "https://i.imgur.com/PNTYOgg.png",
                streamUrl = "https://bcovlive-a.akamaihd.net/5e531be3ed6c41229b2af2d9bffba88d/us-east-1/6183977686001/profile_1/chunklist.m3u8",
                category = "USA,Retro"
            ),
            TvChannel(
                id = 1249,
                name = "Stadium",
                logoUrl = "https://i.imgur.com/6ae9E8d.png",
                streamUrl = "https://bcovlive-a.akamaihd.net/e64d564b9275484f85981d8c146fb915/us-east-1/5994000126001/profile_1/976f34cf5a614518b7b539cbf9812080/chunklist_ssaiV.m3u8",
                category = "USA,Stadium"
            ),
            TvChannel(
                id = 1250,
                name = "Heartland",
                logoUrl = "https://i.imgur.com/a67bbag.png",
                streamUrl = "https://bcovlive-a.akamaihd.net/1ad942d15d9643bea6d199b729e79e48/us-east-1/6183977686001/profile_1/chunklist.m3u8",
                category = "USA,Heartland"
            ),
            TvChannel(
                id = 1251,
                name = "Rev'n",
                logoUrl = "https://i.imgur.com/VUhqVgG.png",
                streamUrl = "https://bcovlive-a.akamaihd.net/a71236fdda1747999843bd3d55bdd6fa/us-east-1/6183977686001/profile_1/chunklist.m3u8",
                category = "USA,Rev'n"
            ),
            TvChannel(
                id = 1252,
                name = "CNN",
                logoUrl = "https://i.imgur.com/vyrc1I1.png",
                streamUrl = "https://tve-live-lln.warnermediacdn.com/hls/live/586495/cnngo/cnn_slate/VIDEO_0_3564000.m3u8",
                category = "USA,CNN"
            ),
            TvChannel(
                id = 1253,
                name = "Bloomberg",
                logoUrl = "https://i.imgur.com/VnCcH73.png",
                streamUrl = "https://bloomberg.com/media-manifest/streams/us.m3u8",
                category = "USA,Bloomberg"
            ),
            TvChannel(
                id = 1254,
                name = "ABC News",
                logoUrl = "https://i.imgur.com/7sJLzKi.png",
                streamUrl = "https://content.uplynk.com/channel/3324f2467c414329b3b0cc5cd987b6be.m3u8",
                category = "USA,ABC"
            ),
            TvChannel(
                id = 1255,
                name = "CBS News",
                logoUrl = "https://i.imgur.com/nki2HDQ.png",
                streamUrl = "https://cbsnews.akamaized.net/hls/live/2020607/cbsnlineup_8/master.m3u8",
                category = "USA,CBS"
            ),
            TvChannel(
                id = 1256,
                name = "NBC News",
                logoUrl = "https://i.imgur.com/v48mMRT.png",
                streamUrl = "http://dai2.xumo.com/xumocdn/p=roku/amagi_hls_data_xumo1212A-xumo-nbcnewsnow/CDN/playlist.m3u8",
                category = "USA,NBC"
            ),
            TvChannel(
                id = 1257,
                name = "Reuters TV",
                logoUrl = "https://i.imgur.com/AbvCnoH.png",
                streamUrl = "https://reuters-reutersnow-1-eu.rakuten.wurl.tv/playlist.m3u8",
                category = "USA,Reuters"
            ),
            TvChannel(
                id = 1258,
                name = "NASA TV Public",
                logoUrl = "https://i.imgur.com/rmyfoOI.png",
                streamUrl = "https://ntv1.akamaized.net/hls/live/2014075/NASA-NTV1-HLS/master_2000.m3u8",
                category = "USA,NASA"
            ),
            TvChannel(
                id = 1259,
                name = "NASA TV Media",
                logoUrl = "https://i.imgur.com/rmyfoOI.png",
                streamUrl = "https://ntv2.akamaized.net/hls/live/2013923/NASA-NTV2-HLS/master.m3u8",
                category = "USA,NASA"
            ),
            TvChannel(
                id = 1260,
                name = "Docurama",
                logoUrl = "https://i.imgur.com/bNg8mze.png",
                streamUrl = "https://cinedigm.vo.llnwd.net/conssui/amagi_hls_data_xumo1234A-docuramaA/CDN/master.m3u8",
                category = "USA,Docurama"
            ),
            TvChannel(
                id = 1261,
                name = "Drybar Comedy",
                logoUrl = "https://i.imgur.com/EldlmTp.png",
                streamUrl = "https://drybar-drybarcomedy-1-ca.samsung.wurl.com/manifest/playlist.m3u8",
                category = "USA,Drybar"
            ),
            TvChannel(
                id = 1262,
                name = "Music Channel",
                logoUrl = "http://media.boni-records.com/logo.png",
                streamUrl = "http://media.boni-records.com/index.m3u8",
                category = "USA,Music"
            ),
            TvChannel(
                id = 1263,
                name = "Bumblebee TV Aurora Live",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5c953819932c837b49397345/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1264,
                name = "Bumblebee TV AutoMoto",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node01.powr.com/live/5bf220fad5eeee0f5a40941a/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1265,
                name = "BumblebeeTV Beaches Live",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5c95396f932c837b49397360/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1266,
                name = "Bumblebee TV Classics 2",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node05.powr.com/live/60f881602da3a5575eceb854/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1267,
                name = "Bumblebee TV CoronaVirus.Gov",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5e7559e8a46b495a2283c5e8/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1268,
                name = "Bumblebee TV Country Boy Kids Video.us",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5bf225aed5eeee0f5a4094bd/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1269,
                name = "Bumblebee TV Cute Zone",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5bf22518d5eeee0f5a409486/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1270,
                name = "Bumblebee TV Epic M",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5bf22225d5eeee0f5a40941d/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1271,
                name = "Bumblebee TV FGTV",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5e2624990145130f25474620/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1272,
                name = "Bumblebee TV Forest Live",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5c953836932c837b49397355/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1273,
                name = "Bumblebee TV Fun Zone",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5e2625030145130f25474622/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1274,
                name = "Bumblebee TV Giggle Zone",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5bf22526d5eeee0f5a4094b8/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1275,
                name = "Bumblebee TV Lake Live",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5c95385c932c837b49397356/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1276,
                name = "Bumblebee TV Lego Toons",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5bf22549d5eeee0f5a4094ba/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1277,
                name = "Bumblebee TV Lets Play Minecraft",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5e2625700145130f25474624/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1278,
                name = "Bumblebee TV LifeBae",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5bf22681932c8304fc453418/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1279,
                name = "Bumblebee TV Master Builder",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5bf2256ed5eeee0f5a4094bb/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1280,
                name = "Bumblebee TV Mountain Live",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5c95387b932c837b49397357/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1281,
                name = "Bumblebee TV Now You Know",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node01.powr.com/live/5b284f40d5eeee07522b775e/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1282,
                name = "Bumblebee TV Recoil TV",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5c7dff0f932c8368bdbfd5fd/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1283,
                name = "Bumblebee TV Rivers Live",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5c95388f932c837b4939735a/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1284,
                name = "Bumblebee TV Smosh",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5e2625af5748670f12a3bee9/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1285,
                name = "Bumblebee TV Sunset Live",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5c9538a5932c837b4939735b/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1286,
                name = "Bumblebee TV Thinknoodles",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node04.powr.com/live/5afc8Bumblebee+TV10e932c833522744733/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1287,
                name = "Bumblebee TV Toy Zone",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5bf22491932c8304fc4533e4/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1288,
                name = "Bumblebee TV Trinity Beyond",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5e2626030145130f25474626/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1289,
                name = "Bumblebee TV Tropics Live",
                logoUrl = "",
                streamUrl = "https://stitcheraws.unreel.me/wse-node02.powr.com/live/5c9538b9932c837b4939735c/playlist.m3u8",
                category = "Usa"
            ),
            TvChannel(
                id = 1290,
                name = "Venevisión",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/Logotipo_de_Venevisi%C3%B3n.svg/641px-Logotipo_de_Venevisi%C3%B3n.svg.png",
                streamUrl = "https://venevision.akamaized.net/hls/live/2098814/VENEVISION/master.m3u8",
                category = "Venezuela,Venevisión"
            ),
            TvChannel(
                id = 1291,
                name = "Televen",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/c/c0/Televen_logo.PNG",
                streamUrl = "https://setp-televen-ssai-mslv4-open.akamaized.net/hls/live/2107128/televen/index.m3u8",
                category = "Venezuela,Televen"
            ),
            TvChannel(
                id = 1292,
                name = "Globovisión",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/4/47/Globovisi%C3%B3n_logo_2013.png",
                streamUrl = "https://vcp5.myplaytv.com/globovision/globovision/playlist.m3u8",
                category = "Venezuela,Globovisión"
            ),
            TvChannel(
                id = 1293,
                name = "Vale TV Ⓢ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/9/98/Logo_de_VALE_TV.png",
                streamUrl = "https://vcp2.myplaytv.com/valetv/valetv/playlist.m3u8",
                category = "Venezuela,Vale"
            ),
            TvChannel(
                id = 1294,
                name = "Telesur",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/8/82/TeleSUR.png",
                streamUrl = "https://raw.githubusercontent.com/BellezaEmporium/IPTV_Exception/master/channels/ve/telesur.m3u8",
                category = "Venezuela,Telesur"
            ),
            TvChannel(
                id = 1295,
                name = "Latina TV",
                logoUrl = "https://intervenhosting.net/imagenes/latinatv.jpg",
                streamUrl = "https://streamtv.latinamedios.com:3413/live/latinatvlive.m3u8",
                category = "Venezuela,Latina"
            ),
            TvChannel(
                id = 1296,
                name = "Al Jazeera Documentary Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/e/e6/Al_Jazeera_Doc.png",
                streamUrl = "https://live-hls-web-ajd.getaj.net/AJD/index.m3u8",
                category = "Documentaries"
            ),
            TvChannel(
                id = 1297,
                name = "CGTN Documentary English Ⓢ",
                logoUrl = "https://i.imgur.com/JHv0WxM.png",
                streamUrl = "https://news.cgtn.com/resource/live/document/cgtn-doc.m3u8",
                category = "Documentaries"
            ),
            TvChannel(
                id = 1298,
                name = "RT Documentary English Ⓖ",
                logoUrl = "https://i.imgur.com/ZEi1Wgn.png",
                streamUrl = "https://rt-rtd.rttv.com/dvr/rtdoc/playlist.m3u8",
                category = "Documentaries"
            ),
            TvChannel(
                id = 1299,
                name = "Peer TV South Tyrol",
                logoUrl = "https://www.peer.biz/peertv-iptv/peer-tv-south-tyrol.png",
                streamUrl = "https://iptv.peer.biz/live/peertv-en.m3u8",
                category = "Documentaries"
            ),
            TvChannel(
                id = 1300,
                name = "FilmRise Movies",
                logoUrl = "https://i.imgur.com/jGzMaRD.png",
                streamUrl = "http://dai2.xumo.com/xumocdn/p=roku/amagi_hls_data_xumo1212A-filmrisefreemovies/CDN/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1301,
                name = "FilmRise Sci-Fi",
                logoUrl = "https://i.imgur.com/FcN1OKo.png",
                streamUrl = "http://dai2.xumo.com/xumocdn/p=roku/amagi_hls_data_xumo1212A-rokufilmrisesci-fi/CDN/master.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1302,
                name = "Film Detective",
                logoUrl = "https://i.imgur.com/4aFLH9g.png",
                streamUrl = "https://dai.google.com/linear/hls/event/OYH9J7rZSK2fabKXWAYcfA/master.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1303,
                name = "Al Jazeera العربية",
                logoUrl = "https://i.imgur.com/BB93NQP.png",
                streamUrl = "https://live-hls-web-aja.getaj.net/AJA/index.m3u8",
                category = "News"
            ),
            TvChannel(
                id = 1304,
                name = "Al Arabiya العربية",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e0/Al-Arabiya_new_logo.svg/640px-Al-Arabiya_new_logo.svg.png",
                streamUrl = "https://live.alarabiya.net/alarabiapublish/alarabiya.smil/playlist.m3u8",
                category = "News"
            ),
            TvChannel(
                id = 1305,
                name = "DW العربية",
                logoUrl = "https://i.imgur.com/A1xzjOI.png",
                streamUrl = "https://dwamdstream103.akamaized.net/hls/live/2015526/dwstream103/index.m3u8",
                category = "News"
            ),
            TvChannel(
                id = 1306,
                name = "CGTN العربية",
                logoUrl = "https://i.imgur.com/fMsJYzl.png",
                streamUrl = "https://news.cgtn.com/resource/live/arabic/cgtn-a.m3u8",
                category = "News"
            ),
            TvChannel(
                id = 1307,
                name = "Sky News العربية",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/5/57/Sky_News_logo.svg/512px-Sky_News_logo.svg.png",
                streamUrl = "https://stream.skynewsarabia.com/hls/sna.m3u8",
                category = "News"
            ),
            TvChannel(
                id = 1308,
                name = "RT العربية",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/Russia-today-logo.svg/512px-Russia-today-logo.svg.png",
                streamUrl = "https://rt-arb.rttv.com/dvr/rtarab/playlist.m3u8",
                category = "News"
            ),
            TvChannel(
                id = 1309,
                name = "Sahara 24 صحراء",
                logoUrl = "https://imgur.com/a/7szNpBp",
                streamUrl = "https://65.108.206.29/sahara24-live/video.m3u8",
                category = "News"
            ),
            TvChannel(
                id = 1310,
                name = "Sky News (UK)",
                logoUrl = "https://d2n0069hmnqmmx.cloudfront.net/epgdata/1.0/newchanlogos/512/512/skychb1404.png",
                streamUrl = "https://ythls.armelin.one/channel/UCoMdktPbSTixAyNGwb-UYkQ.m3u8",
                category = "News,Sky"
            ),
            TvChannel(
                id = 1311,
                name = "DW",
                logoUrl = "https://i.imgur.com/A1xzjOI.png",
                streamUrl = "https://dwamdstream102.akamaized.net/hls/live/2015525/dwstream102/index.m3u8",
                category = "News,DW"
            ),
            TvChannel(
                id = 1312,
                name = "Al Jazeera",
                logoUrl = "https://i.imgur.com/BB93NQP.png",
                streamUrl = "https://live-hls-apps-aje-fa.getaj.net/AJE/index.m3u8",
                category = "News,Al"
            ),
            TvChannel(
                id = 1313,
                name = "CGTN",
                logoUrl = "https://i.imgur.com/fMsJYzl.png",
                streamUrl = "https://news.cgtn.com/resource/live/english/cgtn-news.m3u8",
                category = "News,CGTN"
            ),
            TvChannel(
                id = 1314,
                name = "BBC News Ⓖ",
                logoUrl = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/united-kingdom/bbc-news-uk.png",
                streamUrl = "https://vs-hls-push-uk.live.fastly.md.bbci.co.uk/x=4/i=urn:bbc:pips:service:bbc_news_channel_hd/iptv_hd_abr_v1.m3u8",
                category = "News,BBC"
            ),
            TvChannel(
                id = 1315,
                name = "NBC News NOW",
                logoUrl = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/united-kingdom/nbc-news-now-uk.png",
                streamUrl = "https://dai2.xumo.com/amagi_hls_data_xumo1212A-xumo-nbcnewsnow/CDN/master.m3u8",
                category = "News,NBC"
            ),
            TvChannel(
                id = 1316,
                name = "Reuters",
                logoUrl = "https://i.imgur.com/6eQ2nCJ.png",
                streamUrl = "https://reuters-reutersnow-1-nl.samsung.wurl.tv/playlist.m3u8",
                category = "News,Reuters"
            ),
            TvChannel(
                id = 1317,
                name = "The Guardian",
                logoUrl = "https://i.imgur.com/o9AYq9V.png",
                streamUrl = "https://rakuten-guardian-1-ie.samsung.wurl.tv/playlist.m3u8",
                category = "News,The"
            ),
            TvChannel(
                id = 1318,
                name = "CBS News",
                logoUrl = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/united-states/cbs-news-us.png",
                streamUrl = "https://dai.google.com/linear/hls/event/Sid4xiTQTkCT1SLu6rjUSQ/master.m3u8",
                category = "News,CBS"
            ),
            TvChannel(
                id = 1319,
                name = "ABC News Live",
                logoUrl = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/united-states/abc-news-live-hz-us.png",
                streamUrl = "https://lnc-abc-news.tubi.video/index.m3u8",
                category = "News,ABC"
            ),
            TvChannel(
                id = 1320,
                name = "LiveNOW from FOX",
                logoUrl = "https://i.imgur.com/1JnyzHv.png",
                streamUrl = "https://lnc-fox-live-now.tubi.video/index.m3u8",
                category = "News,LiveNOW"
            ),
            TvChannel(
                id = 1321,
                name = "CBC News Network",
                logoUrl = "https://i.imgur.com/SjTdhvJ.png",
                streamUrl = "https://dai2.xumo.com/amagi_hls_data_xumo1212A-redboxcbcnews/CDN/playlist.m3u8",
                category = "News,CBC"
            ),
            TvChannel(
                id = 1322,
                name = "Ticker News",
                logoUrl = "https://i.imgur.com/z7M0QxV.png",
                streamUrl = "https://cdn-uw2-prod.tsv2.amagi.tv/linear/amg01486-tickernews-tickernewsweb-ono/playlist.m3u8",
                category = "News,Ticker"
            ),
            TvChannel(
                id = 1323,
                name = "India Today",
                logoUrl = "https://i.imgur.com/koFYddE.png",
                streamUrl = "https://indiatodaylive.akamaized.net/hls/live/2014320/indiatoday/indiatodaylive/playlist.m3u8",
                category = "News,India"
            ),
            TvChannel(
                id = 1324,
                name = "Channel News Asia",
                logoUrl = "https://i.imgur.com/xWglicB.png",
                streamUrl = "https://ythls.armelin.one/channel/UC83jt4dlz1Gjl58fzQrrKZg.m3u8",
                category = "News,Channel"
            ),
            TvChannel(
                id = 1325,
                name = "NDTV 24x7",
                logoUrl = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/india/ndtv-24x7-in.png",
                streamUrl = "https://ythls.armelin.one/channel/UCZFMm1mMw0F81Z37aaEzTUA.m3u8",
                category = "News,NDTV"
            ),
            TvChannel(
                id = 1326,
                name = "TRT World",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/27/TRT_World.svg/512px-TRT_World.svg.png",
                streamUrl = "https://ythls.armelin.one/channel/UC7fWeaHhqgM4Ry-RMpM2YYw.m3u8",
                category = "News,TRT"
            ),
            TvChannel(
                id = 1327,
                name = "NHK World Japan",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/NHK_World-Japan_TV.svg/512px-NHK_World-Japan_TV.svg.png",
                streamUrl = "https://ythls.armelin.one/channel/UCSPEjw8F2nQDtmUKPFNF7_A.m3u8",
                category = "News,NHK"
            ),
            TvChannel(
                id = 1328,
                name = "DD India",
                logoUrl = "https://i.imgur.com/45uptR8.png",
                streamUrl = "https://ythls.armelin.one/channel/UCGDQNvybfDDeGTf4GtigXaw.m3u8",
                category = "News,DD"
            ),
            TvChannel(
                id = 1329,
                name = "WION",
                logoUrl = "https://i.imgur.com/Wc5Z3iS.png",
                streamUrl = "https://ythls.armelin.one/channel/UC_gUM8rL-Lrg6O3adPW9K1g.m3u8",
                category = "News,WION"
            ),
            TvChannel(
                id = 1330,
                name = "Taiwan+",
                logoUrl = "https://i.imgur.com/SfcZyqm.png",
                streamUrl = "https://ythls.armelin.one/channel/UC7c6rvyAZLpKGk8ttVnpnLA.m3u8",
                category = "News,Taiwan+"
            ),
            TvChannel(
                id = 1331,
                name = "Metro Globe Network",
                logoUrl = "https://i.imgur.com/aiiinzg.png",
                streamUrl = "https://edge.medcom.id/live-edge/smil:mgnch.smil/playlist.m3u8",
                category = "News,Metro"
            ),
            TvChannel(
                id = 1332,
                name = "i24 News",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/79/LOGO_i24NEWS.png/512px-LOGO_i24NEWS.png",
                streamUrl = "https://bcovlive-a.akamaihd.net/6e3dd61ac4c34d6f8fb9698b565b9f50/eu-central-1/5377161796001/playlist-all_dvr.m3u8",
                category = "News,i24"
            ),
            TvChannel(
                id = 1333,
                name = "Scripps News",
                logoUrl = "https://i.imgur.com/UfN6aAi.png",
                streamUrl = "https://content.uplynk.com/channel/4bb4901b934c4e029fd4c1abfc766c37.m3u8",
                category = "News,Scripps"
            ),
            TvChannel(
                id = 1334,
                name = "USA Today",
                logoUrl = "https://i.imgur.com/37K0AZX.png",
                streamUrl = "https://lnc-usa-today.tubi.video/playlist.m3u8",
                category = "News,USA"
            ),
            TvChannel(
                id = 1335,
                name = "Sky News Now (AU)",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/1/10/Sky_News_Australia_logo_-_2019.svg/512px-Sky_News_Australia_logo_-_2019.svg.png",
                streamUrl = "https://i.mjh.nz/sky-news-now.m3u8",
                category = "News,Sky"
            ),
            TvChannel(
                id = 1336,
                name = "Global News",
                logoUrl = "https://i.imgur.com/xk1QOhW.png",
                streamUrl = "https://live.corusdigitaldev.com/groupd/live/49a91e7f-1023-430f-8d66-561055f3d0f7/live.isml/.m3u8",
                category = "News,Global"
            ),
            TvChannel(
                id = 1337,
                name = "Russia Today Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/Russia-today-logo.svg/512px-Russia-today-logo.svg.png",
                streamUrl = "https://rt-glb.rttv.com/live/rtnews/playlist.m3u8",
                category = "News,Russia"
            ),
            TvChannel(
                id = 1338,
                name = "CNN",
                logoUrl = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/united-states/cnn-us.png",
                streamUrl = "https://raw.githubusercontent.com/Alstruit/adaptive-streams/alstruit-10_23_us/streams/us/CNNUSA.us.m3u8",
                category = "News,CNN"
            ),
            TvChannel(
                id = 1339,
                name = "CNN International",
                logoUrl = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/united-states/cnn-us.png",
                streamUrl = "https://turnerlive.warnermediacdn.com/hls/live/586495/cnngo/cnn_slate/VIDEO_0_3564000.m3u8",
                category = "News,CNN"
            ),
            TvChannel(
                id = 1340,
                name = "GB News",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/thumb/3/35/GB_News_Logo.svg/512px-GB_News_Logo.svg.png",
                streamUrl = "https://ythls.armelin.one/channel/UC0vn8ISa4LKMunLbzaXLnOQ.m3u8",
                category = "News,GB"
            ),
            TvChannel(
                id = 1341,
                name = "TalkTV",
                logoUrl = "https://i.imgur.com/KxHWpQB.png",
                streamUrl = "https://live-talktv-ssai.simplestreamcdn.com/v1/master/82267e84b9e5053b3fd0ade12cb1a146df74169a/talktv-live/index.m3u8",
                category = "News,TalkTV"
            ),
            TvChannel(
                id = 1342,
                name = "Joy News",
                logoUrl = "https://i.imgur.com/kGuMNmR.png",
                streamUrl = "https://ythls.armelin.one/channel/UChd1DEecCRlxaa0-hvPACCw.m3u8",
                category = "News,Joy"
            ),
            TvChannel(
                id = 1343,
                name = "SABC News",
                logoUrl = "https://i.imgur.com/H9q3Q9d.png",
                streamUrl = "https://sabconetanw.cdn.mangomolo.com/news/smil:news.stream.smil/chunklist_b250000_t64MjQwcA==.m3u8",
                category = "News,SABC"
            ),
            TvChannel(
                id = 1344,
                name = "Bloomberg TV+",
                logoUrl = "https://i.imgur.com/xGlToly.png",
                streamUrl = "https://bloomberg.com/media-manifest/streams/phoenix-us.m3u8",
                category = "Business,Bloomberg"
            ),
            TvChannel(
                id = 1345,
                name = "Bloomberg Television (US)",
                logoUrl = "https://i.imgur.com/OuogLHx.png",
                streamUrl = "https://bloomberg.com/media-manifest/streams/us.m3u8",
                category = "Business,Bloomberg"
            ),
            TvChannel(
                id = 1346,
                name = "Bloomberg Television (Europe)",
                logoUrl = "https://i.imgur.com/OuogLHx.png",
                streamUrl = "https://bloomberg.com/media-manifest/streams/eu.m3u8",
                category = "Business,Bloomberg"
            ),
            TvChannel(
                id = 1347,
                name = "Yahoo! Finance",
                logoUrl = "https://i.imgur.com/43oHsHL.png",
                streamUrl = "https://d1ewctnvcwvvvu.cloudfront.net/playlist.m3u8",
                category = "Business,Yahoo!"
            ),
            TvChannel(
                id = 1348,
                name = "CNBC Europe",
                logoUrl = "https://d2n0069hmnqmmx.cloudfront.net/epgdata/1.0/newchanlogos/512/512/skychb1088.png",
                streamUrl = "https://amg01079-nbcuuk-amg01079c1-samsung-es-1261.playouts.now.amagi.tv/playlist/amg01079-nbcuukfast-cnbcpe-samsunges/playlist.m3u8",
                category = "Business,CNBC"
            ),
            TvChannel(
                id = 1349,
                name = "CNBC Indonesia",
                logoUrl = "https://i.imgur.com/bUfeG7Y.png",
                streamUrl = "https://live.cnbcindonesia.com/livecnbc/smil:cnbctv.smil/master.m3u8",
                category = "Business,CNBC"
            ),
            TvChannel(
                id = 1350,
                name = "Ausbiz",
                logoUrl = "https://i.imgur.com/8vGGdB0.png",
                streamUrl = "https://d9quh89lh7dtw.cloudfront.net/public-output/index.m3u8",
                category = "Business,Ausbiz"
            ),
            TvChannel(
                id = 1351,
                name = "Moconomy",
                logoUrl = "https://i.imgur.com/GvqbLZB.png",
                streamUrl = "https://amogonetworx-moconomy-2-us.tcl.wurl.tv/playlist.m3u8",
                category = "Business,Moconomy"
            ),
            TvChannel(
                id = 1352,
                name = "AccuWeather NOW",
                logoUrl = "https://i.imgur.com/M8wbVYK.png",
                streamUrl = "https://cdn-ue1-prod.tsv2.amagi.tv/linear/amg00684-accuweather-accuweather-plex/playlist.m3u8",
                category = "Weather,AccuWeather"
            ),
            TvChannel(
                id = 1353,
                name = "Fox Weather",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/Fox_Weather_logo.svg/512px-Fox_Weather_logo.svg.png",
                streamUrl = "https://lnc-fox-weather.tubi.video/index.m3u8",
                category = "Weather,Fox"
            ),
            TvChannel(
                id = 1354,
                name = "KTCA-DT5",
                logoUrl = "https://upload.wikimedia.org/wikipedia/en/b/be/Twin_Cities_Public_Television_logo_%28PBS%29.png",
                streamUrl = "https://api.new.livestream.com/accounts/12638076/events/8488790/live.m3u8",
                category = "Weather,KTCA-DT5"
            ),
            TvChannel(
                id = 1355,
                name = "Sky News Weather Ⓖ",
                logoUrl = "https://pbs.twimg.com/profile_images/1604994875459518464/lGt2wEqM_400x400.jpg",
                streamUrl = "https://distro001-gb-hls1-prd.delivery.skycdp.com/easel_cdn/ngrp:weather_loop.stream_all/playlist.m3u8",
                category = "Weather,Sky"
            ),
            TvChannel(
                id = 1356,
                name = "DW Español Ⓢ",
                logoUrl = "https://i.imgur.com/A1xzjOI.png",
                streamUrl = "https://dwamdstream104.akamaized.net/hls/live/2015530/dwstream104/stream04/streamPlaylist.m3u8",
                category = "News"
            ),
            TvChannel(
                id = 1357,
                name = "CGTN Español",
                logoUrl = "https://i.imgur.com/fMsJYzl.png",
                streamUrl = "https://news.cgtn.com/resource/live/espanol/cgtn-e.m3u8",
                category = "News"
            ),
            TvChannel(
                id = 1358,
                name = "RT Español Ⓖ",
                logoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/Russia-today-logo.svg/512px-Russia-today-logo.svg.png",
                streamUrl = "https://rt-esp.rttv.com/dvr/rtesp/playlist.m3u8",
                category = "News"
            ),
            TvChannel(
                id = 1359,
                name = "RTVE 24H",
                logoUrl = "https://i.imgur.com/WTDKOoM.png",
                streamUrl = "https://ztnr.rtve.es/ztnr/1694255.m3u8",
                category = "News"
            ),
            TvChannel(
                id = 1360,
                name = "Sportitalia LIVE24",
                logoUrl = "https://i.imgur.com/hu56Ya5.png",
                streamUrl = "https://di-g7ij0rwh.vo.lswcdn.net/sportitalia/silive24.smil/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1361,
                name = "Sport2U",
                logoUrl = "https://i.imgur.com/WW0lNk1.png",
                streamUrl = "https://stream9.xdevel.com/video0s976916-1685/stream/playlist_dvr.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1362,
                name = "Stories – Rakuten TV",
                logoUrl = "https://i.imgur.com/tMcUvjI.jpg",
                streamUrl = "https://rakuten-spotlight-6-eu.rakuten.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1363,
                name = "Classico – Rakuten TV",
                logoUrl = "https://i.imgur.com/ytN6jfl.jpeg",
                streamUrl = "https://rakuten-classico-1-eu.rakuten.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1364,
                name = "Thriller – Rakuten TV",
                logoUrl = "https://i.imgur.com/jJTnBNk.jpeg",
                streamUrl = "https://rakuten-thriller-6-eu.rakuten.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1365,
                name = "Commedia – Rakuten TV",
                logoUrl = "https://i.imgur.com/EKKXdIU.jpg",
                streamUrl = "https://rakuten-comedymovies-6-eu.rakuten.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1366,
                name = "Documentari – Rakuten TV",
                logoUrl = "https://i.imgur.com/rAHBiO8.jpg",
                streamUrl = "https://rakuten-documentaries-6-eu.rakuten.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1367,
                name = "Family – Rakuten TV",
                logoUrl = "https://i.imgur.com/BCC123A.jpg",
                streamUrl = "https://rakuten-family-6-eu.rakuten.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1368,
                name = "Romance – Rakuten TV",
                logoUrl = "https://i.imgur.com/TiXrzJZ.jpeg",
                streamUrl = "https://rakuten-romance-6-eu.rakuten.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1369,
                name = "Film Top – Rakuten TV",
                logoUrl = "https://i.imgur.com/OfD9hM9.jpeg",
                streamUrl = "https://rakuten-topfree-6-eu.rakuten.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1370,
                name = "Drammatico – Rakuten TV",
                logoUrl = "https://i.imgur.com/Nx3JzZK.jpg",
                streamUrl = "https://rakuten-tvshows-6-eu.rakuten.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1371,
                name = "Film d'azione – Rakuten TV",
                logoUrl = "https://i.imgur.com/KDmDQM6.jpg",
                streamUrl = "https://rakuten-actionmovies-6-eu.rakuten.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1372,
                name = "Euronews in diretta",
                logoUrl = "https://i.imgur.com/DUUxsO7.jpeg",
                streamUrl = "https://rakuten-euronews-3-it.samsung.wurl.com/manifest/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1373,
                name = "FailArmy",
                logoUrl = "https://i.imgur.com/WupT16d.jpg",
                streamUrl = "https://failarmy-international-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1374,
                name = "The Pet Collective",
                logoUrl = "https://i.imgur.com/daTU44g.jpeg",
                streamUrl = "https://the-pet-collective-international-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1375,
                name = "Canale Europa",
                logoUrl = "https://i.imgur.com/Zw2ZIfz.jpg",
                streamUrl = "https://canaleeuropa-canaleeuropa-1-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1376,
                name = "People Are Awesome",
                logoUrl = "https://i.imgur.com/xwz9zKk.jpeg",
                streamUrl = "https://jukin-peopleareawesome-2-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1377,
                name = "Yamato Animation",
                logoUrl = "https://i.imgur.com/rOl7HfS.png",
                streamUrl = "https://yamatovideo-yamatoanimation-1-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1378,
                name = "BBC Doctor Who",
                logoUrl = "https://i.imgur.com/J2B9FjO.jpg",
                streamUrl = "https://bbceu-doctorwho-1-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1379,
                name = "BBC Drama",
                logoUrl = "https://i.imgur.com/hY1M4hL.jpg",
                streamUrl = "https://bbceu-bbcdrama-2-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1380,
                name = "Televisa Telenovelas",
                logoUrl = "https://i.imgur.com/GaJIRN3.jpg",
                streamUrl = "https://televisa-televisa-1-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1381,
                name = "Humanity Documentari",
                logoUrl = "https://i.imgur.com/4gwdyar.png",
                streamUrl = "https://cdn-ue1-prod.tsv2.amagi.tv/linear/amg00712-alchimie-humanitydocit-samsungit/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1382,
                name = "The Boat Show",
                logoUrl = "https://i.imgur.com/cPTLian.png",
                streamUrl = "https://d46c0ebf9ef94053848fdd7b1f2f6b90.mediatailor.eu-central-1.amazonaws.com/v1/master/81bfcafb76f9c947b24574657a9ce7fe14ad75c0/live-prod/4bdea6cd-80c1-11eb-908d-533d39655269/0/master.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1383,
                name = "Fashion TV",
                logoUrl = "https://i.imgur.com/KT3zgc1.png",
                streamUrl = "https://fashiontv-fashiontv-3-it.samsung.wurl.com/manifest/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1384,
                name = "Motor1TV",
                logoUrl = "https://i.imgur.com/UERYhO1.png",
                streamUrl = "https://motorsportnetwork-motor1tv-1-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1385,
                name = "SportOutdoor.tv",
                logoUrl = "https://i.imgur.com/fwOuEBl.png",
                streamUrl = "https://gto2000-sportoutdoortv-1-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1386,
                name = "Italian Fishing TV",
                logoUrl = "https://i.imgur.com/Q0jHCdC.png",
                streamUrl = "https://itftv-italianfishingtv-1-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1387,
                name = "FUEL TV",
                logoUrl = "https://i.imgur.com/4Lzo6M4.png",
                streamUrl = "https://fueltv-fueltv-6-it.samsung.wurl.com/manifest/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1388,
                name = "Teletubbies",
                logoUrl = "https://i.imgur.com/tSw1oON.jpeg",
                streamUrl = "https://dhx-teletubbies-2-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1389,
                name = "duckTV",
                logoUrl = "https://i.imgur.com/BKoAJZV.jpeg",
                streamUrl = "https://mmm-ducktv-2-it.samsung.wurl.com/manifest/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1390,
                name = "SuperToons TV",
                logoUrl = "https://i.imgur.com/A6vCYsC.png",
                streamUrl = "https://kedoo-supertoonstv-4-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1391,
                name = "Planeta Junior",
                logoUrl = "https://i.imgur.com/F71WMja.jpg",
                streamUrl = "https://deaplaneta-planetakidz-2-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1392,
                name = "MONDO TV KIDS",
                logoUrl = "https://i.imgur.com/DMqKFIM.png",
                streamUrl = "https://mondotv-mondotvkids-1-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1393,
                name = "Vevo Pop",
                logoUrl = "https://i.imgur.com/DPqMpQC.png",
                streamUrl = "https://601820eb2b971a000104f40a-samsung.eu.ssai.zype.com/601820eb2b971a000104f40a_samsung_eu/manifest.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1394,
                name = "Radio Italia Trend",
                logoUrl = "https://i.imgur.com/ecpfn3e.png",
                streamUrl = "https://radioitalia-samsungitaly.amagi.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1395,
                name = "Clubbing TV",
                logoUrl = "https://i.imgur.com/D1IuAqu.jpg",
                streamUrl = "https://clubbingtv-samsunguk.amagi.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1396,
                name = "Deluxe Lounge HD",
                logoUrl = "https://i.imgur.com/LzIsXym.png",
                streamUrl = "https://d46c0ebf9ef94053848fdd7b1f2f6b90.mediatailor.eu-central-1.amazonaws.com/v1/master/81bfcafb76f9c947b24574657a9ce7fe14ad75c0/live-prod/9f58b8c3-80c1-11eb-908d-533d39655269/0/master.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1397,
                name = "Trace Latina Ⓖ",
                logoUrl = "https://i.imgur.com/GHbz8wd.png",
                streamUrl = "https://cdn-ue1-prod.tsv2.amagi.tv/linear/amg01131-tracetv-tracelatinait-samsungit/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1398,
                name = "Trace Urban Ⓖ",
                logoUrl = "https://i.imgur.com/PAx9qj8.png",
                streamUrl = "https://cdn-ue1-prod.tsv2.amagi.tv/linear/amg01131-tracetv-traceurbanit-samsungit/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1399,
                name = "Full Moon",
                logoUrl = "https://i.imgur.com/0xT7bZP.jpg",
                streamUrl = "https://minerva-fullmoon-1-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1400,
                name = "Cinema Segreto",
                logoUrl = "https://i.imgur.com/pID3ZGx.png",
                streamUrl = "https://minerva-cinemasegreto-1-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1401,
                name = "Bizzarro Movies",
                logoUrl = "https://i.imgur.com/EbDLnZB.png",
                streamUrl = "https://minerva-bizzarromovies-1-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1402,
                name = "CGtv",
                logoUrl = "https://i.imgur.com/6rsLtY7.png",
                streamUrl = "https://cgentertainment-cgtv-1-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1403,
                name = "WP",
                logoUrl = "https://i.imgur.com/W5I5yY0.png",
                streamUrl = "https://minerva-wp-1-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1404,
                name = "Risate all'italiana",
                logoUrl = "https://i.imgur.com/LCN66Z1.png",
                streamUrl = "https://minerva-risateallitaliana-1-it.samsung.wurl.tv/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1405,
                name = "Shorts Ⓖ",
                logoUrl = "https://i.imgur.com/GwM7RHV.jpg",
                streamUrl = "https://cdn-ue1-prod.tsv2.amagi.tv/linear/amg00784-shortsinternati-shortstv-fast-italy-samsungit/playlist.m3u8",
                category = "VOD"
            ),
            TvChannel(
                id = 1406,
                name = "Sofy.tv",
                logoUrl = "https://i.imgur.com/fsJFJeZ.png",
                streamUrl = "https://sofytv-samsungit.amagi.tv/playlist.m3u8",
                category = "VOD"
            )
        )
    }

    private fun getTvChannels(): List<TvChannel> {
        return listOf(
            TvChannel(
                id = 1,
                name = "Amar Bangla",
                logoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYxgzAohREFZtBKn-T6cIiMMRQ0SZORhHoYA&s",
                streamUrl = "http://115.187.41.216:8080/hls/amarbangla/index.m3u8",
                category = "Entertainment"
            ),
            TvChannel(
                id = 2,
                name = "Amar Digital",
                logoUrl = "https://yt3.googleusercontent.com/ytc/AIdro_mF09sq2C17-S7RNo_0Bg4jfZHAPF9JtLHc1YDgzxvWPA=s900-c-k-c0x00ffffff-no-rj",
                streamUrl = "http://115.187.41.216:8080/hls/amardigital/index.m3u8",
                category = "Sports"
            ),
            TvChannel(
                id = 3,
                name = "Montv Bangla",
                logoUrl = "https://jiotvimages.cdn.jio.com/dare_images/images/channel/c455ca0e9fe90ef63458716120b5abd1.png",
                streamUrl = "http://115.187.41.216:8080/hls/montvbangla/index.m3u8",
                category = "Entertainment"
            ),
            TvChannel(
                id = 4,
                name = "Bhakti Bangla",
                logoUrl = "https://static.wikia.nocookie.net/etv-gspn-bangla/images/f/fe/Bangla_Bhakti_logo_%282020%29.png/revision/latest?cb=20230510105504",
                streamUrl = "http://115.187.41.216:8080/hls/bhaktibangla/index.m3u8",
                category = "Religious"
            ),
            TvChannel(
                id = 5,
                name = "Salam Bangla",
                logoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRR0UPvb6_mWHmiqn49ztVC4pmroDSl06d-0g&s",
                streamUrl = "http://115.187.41.216:8080/hls/salambangla/index.m3u8",
                category = "Movies"
            ),
            TvChannel(
                id = 6,
                name = "Digital Fashion",
                logoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5fZsVfjAkwpyK_oetMMtvZAFBCdMnCtzbbA&s",
                streamUrl = "http://115.187.41.216:8080/hls/digitalfashion/index.m3u8",
                category = "Lifestyle"
            ),
            TvChannel(
                id = 7,
                name = "Sananda TV",
                logoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIVSW63OS-YIryP3InB1Bt3QrDxYPYAK9u0A&s",
                streamUrl = "http://115.187.41.216:8080/hls/sanandatv/index.m3u8",
                category = "Entertainment"
            ),
            TvChannel(
                id = 8,
                name = "BBC News",
                logoUrl = "https://m.media-amazon.com/images/M/MV5BNGYwNDlmZDgtMDg1Yi00N2JmLTk0NzQtNWVkN2NiMTQxY2RlXkEyXkFqcGc@._V1_.jpg",
                streamUrl = "https://cdn4.skygo.mn/live/disk1/BBC_News/HLSv3-FTA/BBC_News.m3u8",
                category = "News"
            ),
            TvChannel(
                id = 9,
                name = "Ekhon Kolkata",
                logoUrl = "https://i.ytimg.com/vi/JnC6n7ddxMU/hqdefault.jpg",
                streamUrl = "rtmp://live.dataplayer.in:1935/live/ekhonkolkata",
                category = "News"
            ),
            TvChannel(
                id = 10,
                name = "Inception",
                logoUrl = "http://iptv.yogayog.net/banners/inception/Inception-LeonardoDiCaprio-ChristopherNolan-HollywoodSciFiMoviePoster_66029b94-50ae-494c-b11d-60a3d91268b5.jpg",
                streamUrl = "http://192.168.1.8:8080/vod/vod_inception/inception.mp4",
                category = "Movies"
            ),
            TvChannel(
                id = 11,
                name = "Dhurandar",
                logoUrl = "http://iptv.yogayog.net/banners/dhurandar/dhurandhar1763462432_2.jpeg",
                streamUrl = "http://iptv.yogayog.net/vod/vod_dhurandar/dh1.mp4",
                category = "Movies"
            ),
            TvChannel(
                id = 12,
                name = "Dhurandar",
                logoUrl = "http://iptv.yogayog.net/banners/dhurandar/dhurandhar1763462432_2.jpeg",
                streamUrl = "https://abplivetv.pc.cdn.bitgravity.com/httppush/abp_livetv/abp_ananda/master.m3u8",
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