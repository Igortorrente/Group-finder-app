package com.example.groupfinder.Data.api

import com.example.groupfinder.Data.entities.Content
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface ContentService {
    @POST("/conteudo/add")
    fun contentRegister(@Body content: ContentGroupArg): Deferred<Response<JsonObject>>

    @GET("/conteudo/get/{id}")
    fun contentData(@Path("id") id: Int): Deferred<Response<Content>>

    @GET("/conteudo/all")
    fun contentFindAll(): Deferred<Response<List<Content>>>

    @DELETE("/conteudo/remove/{id}")
    fun contentDelete(@Path("id") id: Int): Deferred<Response<JsonObject>>

    @PUT("/conteudo/update/{id}")
    fun contentUpdate(@Path("id") id: Int, @Body newContent: ContentArg): Deferred<Response<JsonObject>>
}