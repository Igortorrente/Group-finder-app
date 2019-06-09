package com.example.groupfinder.Data.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val retrofitInstance = Retrofit.Builder()
            .baseUrl("http://177.220.85.247:5001/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    
    fun apiService() : ApiService = retrofitInstance.create(ApiService::class.java)
    
}