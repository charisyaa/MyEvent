package com.example.myevent.data.retrofit

import com.example.myevent.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getActiveEvents(@Query("active") active: Int): Call<EventResponse>
}