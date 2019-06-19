package com.example.groupfinder.Data.api

import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Contains all calls to the API
// Retrofit Library handles converting JSON input and output to objects
interface ApiService {

    // Call generic type defines the expected response type after deserialization
    @POST("user/auth")
    fun userAuth(@Body user: userData): Deferred<Response<JsonObject>>

    @POST("user/register")
    fun userRegister(@Body user: userData): Deferred<Response<JsonObject>>

    @GET("user/{ra}")
    fun userData(@Path("ra") ra: Int): Deferred<userData>

    @GET("user/{ra}")
    fun userDataResponse(@Path("ra") ra: Int): Deferred<Response<userData>>

    @GET("user/{ra}/groups")
    fun userGroupsResponse(@Path("ra") ra: Int): Deferred<Response<List<UserMeetings>>>

    @GET("user/{ra}/groups")
    fun userGroups(@Path("ra") ra: Int): Deferred<List<UserMeetings>>

    @POST("/groups/register")
    fun groupRegister(@Body groupArgument: ApiGroupArgument): Deferred<Response<JsonObject>>

    @GET("/groups/{id}")
    fun groupData(@Path("id") id: Int): Deferred<UserMeetings>

    @GET("/groups/disciplina/{id}")
    fun groupFindBySubject(@Path("id") subId: Int): Deferred<List<UserMeetings>>
}