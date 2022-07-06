package com.example.sample_android_app.networking

import com.example.sample_android_app.models.responses.ItemsResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ItemsApi {

    @GET("default/dynamodb-writer")
    suspend fun getItems(): Response<ItemsResponse>

    companion object {
        operator fun invoke(): ItemsApi {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://ey3f2y0nre.execute-api.us-east-1.amazonaws.com/")
                .build()
                .create(ItemsApi::class.java)
        }
    }
}