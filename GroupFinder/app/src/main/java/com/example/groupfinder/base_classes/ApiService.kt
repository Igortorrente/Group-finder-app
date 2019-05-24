package com.example.groupfinder.base_classes

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Contains all calls to the API
interface ApiService {

    // Call generic type defines the expected response type after deserialization
    @POST("user/auth")
    fun userAuth(@Body user: ApiUser): Call<JsonObject>
}