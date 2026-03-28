/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bacbpl.iptv.data.util

object StringConstants {
    object Movie {
        const val StatusReleased = "Released"
        const val BudgetDefault = "$10M"
        const val WorldWideGrossDefault = "$20M"

        object Reviewer {
            const val FreshTomatoes = "Fresh Tomatoes"
            const val FreshTomatoesImageUrl = ""
            const val ReviewerName = "Rater"
            const val ImageUrl = ""
            const val DefaultCount = "1.8M"
            const val DefaultRating = "9.2"
        }
    }

    object Assets {
        const val Top250Movies = "movies.json"
        const val MostPopularMovies = "movies.json"
        const val InTheaters = "movies.json"
        const val MostPopularTVShows = "movies.json"
        const val MovieCategories = "movieCategories.json"
        const val MovieCast = "movieCast.json"
    }

    object Exceptions {
        const val UnknownException = "Unknown Exception!"
        const val InvalidCategoryId = "Invalid category ID!"
    }

    object Composable {
        object ContentDescription {
            fun moviePoster(movieName: String) = "Movie poster of $movieName"
            fun image(imageName: String) = "image of $imageName"
            const val MoviesCarousel = "Movies Carousel"
            const val UserAvatar = "User Profile Button"
            const val DashboardSearchButton = "Dashboard Search Button"
            const val BrandLogoImage = "Brand Logo Image"
            const val FilterSelected = "Filter Selected"
            fun reviewerName(name: String) = "$name's logo"
        }

        const val CategoryDetailsFailureSubject = "category details"
        const val MoviesFailureSubject = "movies"
        const val MovieDetailsFailureSubject = "movie details"
        const val HomeScreenTrendingTitle = "Trending"
        const val HomeScreenNowPlayingMoviesTitle = "Now Playing Movies"
        const val PopularFilmsThisWeekTitle = "Popular films this week"
        const val BingeWatchDramasTitle = "Bingewatch dramas"
        fun movieDetailsScreenSimilarTo(name: String) = "Similar to $name"
        fun reviewCount(count: String) = "$count reviews"

        object Placeholders {
            const val AboutSectionTitle = "BACBPL"
            const val AboutSectionDescription = "Vision and Mission\n" +
                    "\n" +
                    "Our Vision is to gain the leadership position in the industry as an integrated service provider by being the only choice of the consumer, through catering superior content, quality and services by using advanced technology as an edge. BACPL maintains high standards of social responsibility, as we firmly believe that we should contribute to the society in which we operate. We aim to provide our society all probable technological solutions to support them in their growth and development."
            const val AboutSectionAppVersionTitle = "Application Version"
            const val LanguageSectionTitle = "Language"
            val LanguageSectionItems = listOf(
                "English (US)",
                "English (UK)",
                "Français",
                "Española",
                "हिंदी"
            )
            const val SearchHistorySectionTitle = "Search history"
            const val SearchHistoryClearAll = "Clear All"
            val SampleSearchHistory = listOf(
                "The Light Knight",
                "Iceberg",
                "Jungle Gump",
                "The Devilfather",
                "Space Wars",
                "The Lion Queen"
            )
            const val SubtitlesSectionTitle = "Settings"
            const val SubtitlesSectionSubtitlesItem = "Subtitles"
            const val SubtitlesSectionLanguageItem = "Subtitles Language"
            const val SubtitlesSectionLanguageValue = "English"
            const val AccountsSelectionSwitchAccountsTitle = "Name"
            const val AccountsSelectionSwitchAccountsEmailAddress= "Email"
            const val AccountsSelectionSwitchAccountsMobile = "Mobile"

            const val AccountsSelectionSwitchAccountsEmail = "jack@jetstream.com"
            const val AccountsSelectionLogOut = "Log out"
            const val AccountsSelectionChangePasswordTitle = "Change password"
            const val AccountsSelectionChangePasswordValue = "••••••••••••••"
            const val AccountsSelectionAddNewAccountTitle = "Add new account"
            const val AccountsSelectionViewSubscriptionsTitle = "View subscriptions"
            const val AccountsSelectionDeleteAccountTitle = "Delete account"
            const val HelpAndSupportSectionTitle = "Help and Support"
            const val HelpAndSupportSectionListItemIconDescription = "select section"
            const val HelpAndSupportSectionFAQItem = "FAQ's"
            const val HelpAndSupportSectionPrivacyItem = "Privacy Policy"
            const val HelpAndSupportSectionContactItem = "Contact us on"
            const val HelpAndSupportSectionContactValue = "helpdesk@bacbpl.in"
        }

        const val VideoPlayerControlPlaylistButton = "Playlist Button"
        const val VideoPlayerControlClosedCaptionsButton = "Playlist Button"
        const val VideoPlayerControlSettingsButton = "Playlist Button"
        const val VideoPlayerControlPlayPauseButton = "Playlist Button"
        const val VideoPlayerControlForward = "Fast forward 10 seconds"
        const val VideoPlayerControlSkipNextButton = "Skip to the next movie"
        const val VideoPlayerControlSkipPreviousButton = "Skip to the previous movie"
        const val VideoPlayerControlRepeatAll = "Repeat all movies"
        const val VideoPlayerControlRepeatOne = "Repeat movie"
        const val VideoPlayerControlRepeatNone = "No repeat"
        const val VideoPlayerControlRepeatButton = "Repeat Button"
    }
}
