package com.bacbpl.iptv.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "http://iptv.yogayog.net/"

    private var authToken: String? = null

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Add Bearer Token interceptor with logging
    private val authInterceptor = okhttp3.Interceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()

        // Add Bearer token if available
        authToken?.let {
            println("=== Adding Auth Token to Request ===")
            println("Token: ${it.take(20)}...") // Log first 20 chars only for security
            requestBuilder.header("Authorization", "Bearer $it")
        } ?: println("=== No Auth Token Available for Request ===")

        requestBuilder.method(original.method, original.body)
        val request = requestBuilder.build()
        chain.proceed(request)
    }

    // Client with auth interceptor
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    // Function to update auth token
    fun setAuthToken(token: String?) {
        authToken = token
        println("=== Auth Token Set ===")
        println("Token Present: ${token != null}")
    }

    fun getAuthToken(): String? = authToken
}