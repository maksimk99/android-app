package com.brstu.lab.android

import android.app.Application
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class TestApplication : Application() {

    val BASE_URL = "https://android-lab-server.herokuapp.com"
    var searchService: SearchApi? = null

    override fun onCreate() {
        super.onCreate()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val retrofit = Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        searchService = retrofit.create(SearchApi::class.java)
    }
}