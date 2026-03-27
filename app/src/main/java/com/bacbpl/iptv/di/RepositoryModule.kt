//package com.bacbpl.iptv.di
//
//
//import com.bacbpl.iptv.jetStram.data.repositories.MovieRepository
//import com.bacbpl.iptv.jetStram.data.repositories.MovieRepositoryImpl
//import dagger.Binds
//import dagger.Module
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//abstract class RepositoryModule {
//
//    @Binds
//    @Singleton
//    abstract fun bindMovieRepository(
//        movieRepositoryImpl: MovieRepositoryImpl
//    ): MovieRepository
//}

package com.bacbpl.iptv.di

import com.bacbpl.iptv.jetStram.data.network.ApiService
import com.bacbpl.iptv.jetStram.data.repositories.ChannelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideChannelRepository(
        apiService: ApiService
    ): ChannelRepository {
        return ChannelRepository(apiService)
    }
}