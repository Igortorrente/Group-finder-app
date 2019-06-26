package com.example.groupfinder.Data.api

import com.example.groupfinder.Data.entities.UserGroups
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Contains all calls to the Group Service of the API
// Retrofit Library handles converting JSON input and output to objects

interface GroupService {
    @POST("/groups/register")
    fun groupRegister(@Body groupArgument: ApiGroupArgument): Deferred<Response<JsonObject>>

    @GET("/groups/{id}")
    fun groupData(@Path("id") id: Int): Deferred<UserGroups>

    @GET("/groups/disciplina/{id}")
    fun groupFindBySubject(@Path("id") subId: Int): Deferred<List<UserGroups>>
}