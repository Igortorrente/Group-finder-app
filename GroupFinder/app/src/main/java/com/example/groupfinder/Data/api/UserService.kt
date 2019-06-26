package com.example.groupfinder.Data.api

import com.example.groupfinder.Data.entities.UserData
import com.example.groupfinder.Data.entities.UserGroups
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Contains all calls to the User Service of the API
// Retrofit Library handles converting JSON input and output to objects
interface UserService {

    // Call generic type defines the expected response type after deserialization
    @POST("user/auth")
    fun userAuth(@Body user: UserData): Deferred<Response<JsonObject>>

    @POST("user/register")
    fun userRegister(@Body user: UserData): Deferred<Response<JsonObject>>

    @GET("user/{ra}")
    fun userData(@Path("ra") ra: Int): Deferred<UserData>

    @GET("user/{ra}")
    fun userDataResponse(@Path("ra") ra: Int): Deferred<Response<UserData>>

    @GET("user/{ra}/groups")
    fun userGroupsResponse(@Path("ra") ra: Int): Deferred<Response<List<UserGroups>>>

    @GET("user/{ra}/groups")
    fun userGroups(@Path("ra") ra: Int): Deferred<List<UserGroups>>
}