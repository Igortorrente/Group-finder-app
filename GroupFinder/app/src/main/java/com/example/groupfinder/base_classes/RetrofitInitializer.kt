package com.example.groupfinder.base_classes

import retrofit2.Retrofit

class RetrofitInitializer {

    private val retrofitInstance = Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    
    fun apiService() : ApiService = retrofitInstance.create(ApiService::class.java)
    
}