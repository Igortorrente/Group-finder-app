package com.example.groupfinder.base_classes

import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Contains all calls to the API
// Retrofit Library handles converting JSON input and output to objects
interface ApiService {

    // Call generic type defines the expected response type after deserialization
    @POST("user/auth")
    fun userAuth(@Body user: ApiUser): Call<JsonObject>

    @POST("user/register")
    fun userRegister(@Body user: ApiUser): Call<JsonObject>

    @GET("user/{ra}")
    fun userData(@Path("ra") ra: Int): Deferred<userData>

    @GET("user/{ra}/groups")
    fun userGroups(@Path("ra") ra: Int): Deferred<List<UserMeetings>>
}