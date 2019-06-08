package com.example.groupfinder.base_classes

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val retrofitInstance = Retrofit.Builder()
            .baseUrl("http://hsconexao.com.br:5001/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    
    fun apiService() : ApiService = retrofitInstance.create(ApiService::class.java)
    
}