package com.bacbpl.iptv.di

import com.bacbpl.iptv.jetStram.data.network.ApiService
import com.bacbpl.iptv.jetStram.data.network.RetrofitClient
import com.bacbpl.iptv.jetStram.data.repositories.ApiCategoryDataSource
import com.bacbpl.iptv.jetStram.data.repositories.ApiMovieDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService = RetrofitClient.instance

    @Provides
    @Singleton
    fun provideApiMovieDataSource(apiService: ApiService): ApiMovieDataSource {
        return ApiMovieDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideApiCategoryDataSource(apiService: ApiService): ApiCategoryDataSource {
        return ApiCategoryDataSource(apiService)
    }
}