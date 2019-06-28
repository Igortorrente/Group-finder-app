package com.example.groupfinder.Data.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val retrofitUserInstance = Retrofit.Builder()
            .baseUrl("http://hsconexao.com.br:5001/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val retrofitGroupInstance = Retrofit.Builder()
        .baseUrl("http://hsconexao.com.br:5002/")
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitContentInstance = Retrofit.Builder()
        .baseUrl("http://hsconexao.com.br:5003/")
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    fun userService() : UserService = retrofitUserInstance.create(UserService::class.java)
    fun groupService() : GroupService = retrofitGroupInstance.create(GroupService::class.java)
    fun contentService() : ContentService = retrofitGroupInstance.create(ContentService::class.java)
    
}