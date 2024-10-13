package com.example.fetchtakehomeproject.data.datasource

import com.example.fetchtakehomeproject.data.model.DataItem
import retrofit2.Response

import retrofit2.http.GET

const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

interface RemoteItemDataSource {

    @GET("/hiring.json")
    suspend fun getRemoteItems(): Response<List<DataItem>>
}