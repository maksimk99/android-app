package com.brstu.lab.android

import android.app.Application
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class TestApplication : Application() {

    val IP_ADDRESS = "192.168.43.125"
    var searchService: SearchApi? = null

    override fun onCreate() {
        super.onCreate()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val retrofit = Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl("http://$IP_ADDRESS:9999")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        searchService = retrofit.create(SearchApi::class.java)
    }
}