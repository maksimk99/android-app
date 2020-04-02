package com.brstu.lab.android

import retrofit2.http.GET

interface SearchApi {
    @GET("/list")
    suspend fun getCarSaleList(): SearchResult
}