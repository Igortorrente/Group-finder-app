package com.example.groupfinder.base_classes

// Contains all calls to the API
interface ApiService {

    // Call generic type defines the expected response type after deserialization
    @POST("user/auth")
    fun userAuth(@Body ra: Int, senha: String): Call<String>
}