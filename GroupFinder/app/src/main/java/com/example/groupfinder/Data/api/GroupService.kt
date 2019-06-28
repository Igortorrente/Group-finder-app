package com.example.groupfinder.Data.api

import com.example.groupfinder.Data.entities.UserGroups
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

// Contains all calls to the Group Service of the Utils
// Retrofit Library handles converting JSON input and output to objects

interface GroupService {
    @POST("/groups/register")
    fun groupRegister(@Body groupArgument: ApiGroupArgument): Deferred<Response<JsonObject>>

    @GET("/groups/{id}")
    fun groupData(@Path("id") id: Int): Deferred<Response<UserGroups>>

    @PUT("/groups/update")
    fun groupUpdate(@Body groupUpdArgument: ApiGroupUpdArgument): Deferred<Response<JsonObject>>

    @GET("/groups")
    fun groupFindAll(): Deferred<Response<List<UserGroups>>>

    @GET("/groups/subject/{sub}")
    fun groupFindBySubject(@Path("sub") sub: String): Deferred<Response<List<UserGroups>>>

    @POST("/groups/enroll")
    fun groupEnroll(@Body enrollArgument: ApiEnrollArgument): Deferred<Response<JsonObject>>

    @POST("/groups/unenroll")
    fun groupUnenroll(@Body enrollArgument: ApiEnrollArgument): Deferred<Response<JsonObject>>
}