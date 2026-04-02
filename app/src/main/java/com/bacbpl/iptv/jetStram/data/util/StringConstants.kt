///*
// * Copyright 2023 Google LLC
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * https://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.bacbpl.iptv.data.util
//
//object StringConstants {
//    object Movie {
//        const val StatusReleased = "Released"
//        const val BudgetDefault = "$10M"
//        const val WorldWideGrossDefault = "$20M"
//
//        object Reviewer {
//            const val FreshTomatoes = "Fresh Tomatoes"
//            const val FreshTomatoesImageUrl = ""
//            const val ReviewerName = "Rater"
//            const val ImageUrl = ""
//            const val DefaultCount = "1.8M"
//            const val DefaultRating = "9.2"
//        }
//    }
//
//    object Assets {
//        const val Top250Movies = "movies.json"
//        const val MostPopularMovies = "movies.json"
//        const val InTheaters = "movies.json"
//        const val MostPopularTVShows = "movies.json"
//        const val MovieCategories = "movieCategories.json"
//        const val MovieCast = "movieCast.json"
//    }
//
//    object Exceptions {
//        const val UnknownException = "Unknown Exception!"
//        const val InvalidCategoryId = "Invalid category ID!"
//    }
//
//    object Composable {
//        object ContentDescription {
//            fun moviePoster(movieName: String) = "Movie poster of $movieName"
//            fun image(imageName: String) = "image of $imageName"
//            const val MoviesCarousel = "Movies Carousel"
//            const val UserAvatar = "User Profile Button"
//            const val DashboardSearchButton = "Dashboard Search Button"
//            const val BrandLogoImage = "Brand Logo Image"
//            const val FilterSelected = "Filter Selected"
//            fun reviewerName(name: String) = "$name's logo"
//        }
//
//        const val CategoryDetailsFailureSubject = "category details"
//        const val MoviesFailureSubject = "movies"
//        const val MovieDetailsFailureSubject = "movie details"
//        const val HomeScreenTrendingTitle = "Trending"
//        const val HomeScreenNowPlayingMoviesTitle = "Now Playing Movies"
//        const val PopularFilmsThisWeekTitle = "Popular films this week"
//        const val BingeWatchDramasTitle = "Bingewatch dramas"
//        fun movieDetailsScreenSimilarTo(name: String) = "Similar to $name"
//        fun reviewCount(count: String) = "$count reviews"
//
//        object Placeholders {
//            const val AboutSectionTitle = "BACBPL"
//            const val AboutSectionDescription = "Vision and Mission\n" +
//                    "\n" +
//                    "Our Vision is to gain the leadership position in the industry as an integrated service provider by being the only choice of the consumer, through catering superior content, quality and services by using advanced technology as an edge. BACPL maintains high standards of social responsibility, as we firmly believe that we should contribute to the society in which we operate. We aim to provide our society all probable technological solutions to support them in their growth and development."
//            const val AboutSectionAppVersionTitle = "Application Version"
//            const val LanguageSectionTitle = "Language"
//            val LanguageSectionItems = listOf(
//                "English (US)",
//                "English (UK)",
//                "Français",
//                "Española",
//                "हिंदी"
//            )
//            const val SearchHistorySectionTitle = "Search history"
//            const val SearchHistoryClearAll = "Clear All"
//            val SampleSearchHistory = listOf(
//                "The Light Knight",
//                "Iceberg",
//                "Jungle Gump",
//                "The Devilfather",
//                "Space Wars",
//                "The Lion Queen"
//            )
//            const val SubtitlesSectionTitle = "Settings"
//            const val SubtitlesSectionSubtitlesItem = "Subtitles"
//            const val SubtitlesSectionLanguageItem = "Subtitles Language"
//            const val SubtitlesSectionLanguageValue = "English"
//            const val AccountsSelectionSwitchAccountsTitle = "Name"
//            const val AccountsSelectionSwitchAccountsEmailAddress= "Email"
//            const val AccountsSelectionSwitchAccountsMobile = "Mobile"
//
//            const val AccountsSelectionSwitchAccountsEmail = "jack@jetstream.com"
//            const val AccountsSelectionLogOut = "Log out"
//            const val AccountsSelectionChangePasswordTitle = "Change password"
//            const val AccountsSelectionChangePasswordValue = "••••••••••••••"
//            const val AccountsSelectionAddNewAccountTitle = "Add new account"
//            const val AccountsSelectionViewSubscriptionsTitle = "View subscriptions"
//            const val AccountsSelectionDeleteAccountTitle = "Delete account"
//            const val HelpAndSupportSectionTitle = "Help and Support"
//            const val HelpAndSupportSectionListItemIconDescription = "select section"
//            const val HelpAndSupportSectionFAQItem = "FAQ's"
//            const val HelpAndSupportSectionPrivacyItem = "Privacy Policy"
//            const val HelpAndSupportSectionContactItem = "Contact us on"
//            const val HelpAndSupportSectionContactValue = "helpdesk@bacbpl.in"
//        }
//
//        const val VideoPlayerControlPlaylistButton = "Playlist Button"
//        const val VideoPlayerControlClosedCaptionsButton = "Playlist Button"
//        const val VideoPlayerControlSettingsButton = "Playlist Button"
//        const val VideoPlayerControlPlayPauseButton = "Playlist Button"
//        const val VideoPlayerControlForward = "Fast forward 10 seconds"
//        const val VideoPlayerControlSkipNextButton = "Skip to the next movie"
//        const val VideoPlayerControlSkipPreviousButton = "Skip to the previous movie"
//        const val VideoPlayerControlRepeatAll = "Repeat all movies"
//        const val VideoPlayerControlRepeatOne = "Repeat movie"
//        const val VideoPlayerControlRepeatNone = "No repeat"
//        const val VideoPlayerControlRepeatButton = "Repeat Button"
//    }
//}
// com/bacbpl/iptv/data/util/StringConstants.kt
package com.bacbpl.iptv.jetStram.data.util

import androidx.annotation.StringRes
import com.bacbpl.iptv.R

object StringConstants {

    // Resource IDs for strings instead of hardcoded strings
    object Movie {
        @StringRes
        val StatusReleased = R.string.status_released

        @StringRes
        val BudgetDefault = R.string.budget_default

        @StringRes
        val WorldWideGrossDefault = R.string.worldwide_gross_default

        object Reviewer {
            @StringRes
            val FreshTomatoes = R.string.fresh_tomatoes

            @StringRes
            val FreshTomatoesImageUrl = R.string.fresh_tomatoes_image_url

            @StringRes
            val ReviewerName = R.string.rater_name

            @StringRes
            val ImageUrl = R.string.rater_image_url

            @StringRes
            val DefaultCount = R.string.default_count

            @StringRes
            val DefaultRating = R.string.default_rating
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
        @StringRes
        val UnknownException = R.string.unknown_exception

        @StringRes
        val InvalidCategoryId = R.string.invalid_category_id
    }

    object Composable {
        object ContentDescription {
            fun moviePoster(movieName: String): Int = R.string.movie_poster_content_description
            fun image(imageName: String): Int = R.string.image_content_description
            val MoviesCarousel = R.string.movies_carousel
            val UserAvatar = R.string.user_avatar
            val DashboardSearchButton = R.string.dashboard_search_button
            val BrandLogoImage = R.string.brand_logo_image
            val FilterSelected = R.string.filter_selected
            fun reviewerName(name: String): Int = R.string.reviewer_name_logo
        }

        val CategoryDetailsFailureSubject = R.string.category_details_failure
        val MoviesFailureSubject = R.string.movies_failure
        val MovieDetailsFailureSubject = R.string.movie_details_failure
        val HomeScreenTrendingTitle = R.string.home_screen_trending
        val HomeScreenNowPlayingMoviesTitle = R.string.now_playing_movies
        val PopularFilmsThisWeekTitle = R.string.popular_films_this_week
        val BingeWatchDramasTitle = R.string.binge_watch_dramas

        fun movieDetailsScreenSimilarTo(name: String): Int = R.string.similar_to
        fun reviewCount(count: String): Int = R.string.review_count

        object Placeholders {
            val AboutSectionTitle = R.string.about_section_title
            val AboutSectionDescription = R.string.about_section_description
            val AboutSectionAppVersionTitle = R.string.app_version_title
            val LanguageSectionTitle = R.string.language_section_title
            val LanguageSectionItems = listOf(
                R.string.language_english_us,
                R.string.language_english_uk,
                R.string.language_french,
                R.string.language_spanish,
                R.string.language_hindi
            )
            val SearchHistorySectionTitle = R.string.search_history_title
            val SearchHistoryClearAll = R.string.search_history_clear_all
            val SampleSearchHistory = listOf(
                R.string.sample_search_1,
                R.string.sample_search_2,
                R.string.sample_search_3,
                R.string.sample_search_4,
                R.string.sample_search_5,
                R.string.sample_search_6
            )
            val SubtitlesSectionTitle = R.string.subtitles_settings
            val SubtitlesSectionSubtitlesItem = R.string.subtitles
            val SubtitlesSectionLanguageItem = R.string.subtitles_language
            val SubtitlesSectionLanguageValue = R.string.english
            val AccountsSelectionSwitchAccountsTitle = R.string.name
            val AccountsSelectionSwitchAccountsEmailAddress = R.string.email
            val AccountsSelectionSwitchAccountsMobile = R.string.mobile
            val AccountsSelectionSwitchAccountsEmail = R.string.default_email
            val AccountsSelectionLogOut = R.string.log_out
            val AccountsSelectionChangePasswordTitle = R.string.change_password
            val AccountsSelectionChangePasswordValue = R.string.password_placeholder
            val AccountsSelectionAddNewAccountTitle = R.string.add_new_account
            val AccountsSelectionViewSubscriptionsTitle = R.string.view_subscriptions
            val AccountsSelectionDeleteAccountTitle = R.string.delete_account
            val HelpAndSupportSectionTitle = R.string.help_and_support
            val HelpAndSupportSectionListItemIconDescription = R.string.select_section
            val HelpAndSupportSectionFAQItem = R.string.faq
            val HelpAndSupportSectionPrivacyItem = R.string.privacy_policy
            val HelpAndSupportSectionContactItem = R.string.contact_us
            val HelpAndSupportSectionContactValue = R.string.contact_email
        }

        val VideoPlayerControlPlaylistButton = R.string.playlist_button
        val VideoPlayerControlClosedCaptionsButton = R.string.closed_captions_button
        val VideoPlayerControlSettingsButton = R.string.settings_button
        val VideoPlayerControlPlayPauseButton = R.string.play_pause_button
        val VideoPlayerControlForward = R.string.fast_forward
        val VideoPlayerControlSkipNextButton = R.string.skip_next
        val VideoPlayerControlSkipPreviousButton = R.string.skip_previous
        val VideoPlayerControlRepeatAll = R.string.repeat_all
        val VideoPlayerControlRepeatOne = R.string.repeat_one
        val VideoPlayerControlRepeatNone = R.string.repeat_none
        val VideoPlayerControlRepeatButton = R.string.repeat_button
    }
}